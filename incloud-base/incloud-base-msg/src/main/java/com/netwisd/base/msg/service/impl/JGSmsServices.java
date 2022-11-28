package com.netwisd.base.msg.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.util.RegularUtil;
import com.netwisd.base.msg.fegin.BaseMdmClient;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;

/**
 * 使用极光发送短信
 *
 * @author 云数网讯 XHL
 */
@Service
@Slf4j
public class JGSmsServices {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private BaseMdmClient baseMdmClient;

    @Value("${jiguang.masterSecret}")
    public String masterSecret;

    @Value("${jiguang.appKey}")
    public String appKey;

    //初始化发短信客户端
    private SMSClient smsClient = null;
    /**
     * 验证发送的验证码
     *
     * @throws Exception Exception
     * @return String access_token
     */
    public String verificationCode(String phoneNumber, String code) throws Exception {
        log.debug("验证发送的验证码》》》》》》》》》》》》》》》》》》{}；{}：",phoneNumber,code);
        if(!RegularUtil.isMobilePhone(phoneNumber)) {
            throw new IncloudException("手机号格式不正确！");
        }
        if(StringUtils.isBlank(code)) {
            throw new IncloudException("验证码不能为空！");
        }
        String codeAndTime = (String) redisTemplate.boundHashOps("HashKey").get(phoneNumber);
        // 根据手机号 没有找到验证码信息
        if (StringUtils.isEmpty(codeAndTime)) {
            throw new IncloudException("没有找到验证码信息，或者已过期，请重新获取验证码");
        } else {
            log.debug("验证发送的验证码redis缓存数据为：codeAndTime");
            // 如果redis里有，则先校验验证码 在验证失效有效时间是否到了
            String[] strs = codeAndTime.split("#");
            String _code = strs[0];
            if(!code.equals(_code)) {
                throw  new IncloudException("验证码不正确！");
            }
            // 从redis里拿出来的失效日期
            String strValidTime = strs[1];
            // 调用字符串转日期的工具类，得到失效日期
            //Date objValidTime = DateUtil.strToDate(strValidTime);
            long currentTime = System.currentTimeMillis() ;
            Date objNow = new Date(currentTime);
            if (objNow.getTime() >= Long.valueOf(strValidTime)) {
                throw  new IncloudException("验证码已过期！");
            } else {
                return "true";
            }

        }
    }

    /**
     * 获取精确到毫秒的时间戳
     * @param date
     * @return
     */
    public static Long getSecondTimes(Date date){
        if (null == date) {
            return 0L;
        }
        String timestamp = String.valueOf(date.getTime());
        return Long.valueOf(timestamp);
    }

    /**
     * 发送模板短信-验证码
     *
     * @param phoneNumber
     * create 2019/12/26 by kingyifan
     */
    public boolean sendSMSCode(String phoneNumber) {
        if(StringUtils.isBlank(phoneNumber) || phoneNumber.length() != 11) {
            throw new IncloudException("输入的手机号不正确！");
        }
        if(null == baseMdmClient.findByPhoneForMsg(phoneNumber)) {
            throw new IncloudException("不存在的手机号信息！");
        }
        log.debug("发送短信验证码》》》》》》》》》》》》》》》》》》：" + phoneNumber);
        try {//构建发送短信
            if(null == smsClient) {
                smsClient = new SMSClient(masterSecret, appKey);
            }
            String code = IdGenerator.getTimestampFor6Bit();
            log.debug("生成短信验证码》》》》》》》》》》》》》》》》》》：" + code);
            SMSPayload payload = SMSPayload.newBuilder()
                    .setMobileNumber(phoneNumber) // 手机号码
                    .setTempId(203952)            // 短信模板ID 需要自行申请 模板id为：1的则自带验证码模板id
                    .addTempPara("code", code)  // key模板参数value：参数值  您的手机验证码：{{code}}，有效期5分钟，请勿泄露。如非本人操作，请忽略此短信。谢谢！
                    //.setSignId(xxxx)// 签名id 需要自行申请审核。个人也可以申请 不设置默认签名
                    .build();
            //发送短信 会返回msg_id
            SendSMSResult res = smsClient.sendTemplateSMS(payload);
            log.debug("调用极光返回值：》》》》》》》》》》》》》》》》》》：" + res.getMessageId());
            //缓存到redis key 手机号 val 验证码:过期时间
            HashMap<String, String> hashMap = new HashMap<>();
            Date strValidDate = DateUtil.addDateMinute(new Date(), 5);//5分钟
            hashMap.put(phoneNumber,code+"#" + getSecondTimes(strValidDate));
            redisTemplate.boundHashOps("HashKey").putAll(hashMap);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * SHA1加密
     *
     * @param strSrc 明文
     * @return 加密之后的密文
     */
    public static String encrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");// 将此换成SHA-1、SHA-512、SHA-384等参数
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    /**
     * byte数组转换为16进制字符串
     *
     * @param bts 数据源
     * @return 16进制字符串
     */
    private static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * 延签判断是否是极光回调
     *
     * @param signature
     * @param nonce
     * @param timestamp
     * @return create kingyifan by  on 2019.12.26
     */
    public Boolean checkSign(String signature, String nonce, String timestamp) {
        //加密进行比对
        String str = String.format("appKey=%s&appMasterSecret=%s&nonce=%s×tamp=%s",
                appKey, masterSecret, nonce, timestamp);
        String new_signature = encrypt(str);
        if (signature.equals(new_signature)) {
            return true;
        }
        return false;
    }
}
