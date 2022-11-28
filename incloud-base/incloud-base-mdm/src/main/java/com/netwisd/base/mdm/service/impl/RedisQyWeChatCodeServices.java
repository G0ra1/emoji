package com.netwisd.base.mdm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.mdm.constants.RedisKeyEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

/**
 * redis存储企业微信Token
 *
 * @author 云数网讯 XHL
 */
@Service
public class RedisQyWeChatCodeServices {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Value("${qywechat.corpid}")
    private String corpid;

    @Value("${qywechat.corpsecret}")
    private String corpsecret;

    @Value("${qywechat.tokenurl}")
    private String tokenurl;

    /**
     * 获取access_token
     *
     * @throws Exception Exception
     * @return String access_token
     */
    public String getAccessToken() throws Exception {
        // 从redis拿access_token
        String qyWcToken = (String) redisTemplate.boundHashOps("HashKey").get(RedisKeyEnum.ACCESS_TOKEN);
        // 如果redis里没有，则重新调http获取，以及存放到redis里
        if (StringUtils.isEmpty(qyWcToken)) {
            // 通过http获取最新access_token，且存到redis
            return getAccessTokenByHttp();
        } else {
            // 如果redis里有，则先校验access_token失效有效时间是否快到了
            //String[] strs = qyWcToken.split(",");
            // 从redis里拿出来的失效日期
            //String strValidTime = strs[1];
            // 调用字符串转日期的工具类，得到失效日期
            String _tokenValid = (String) redisTemplate.boundHashOps("HashKey").get(RedisKeyEnum.ACCESS_TOKEN_VALID);
            Date objValidTime = DateUtil.strToDate(_tokenValid);

            // 当前时间+有效时间低于规定时间(秒)默认600秒
            long currTm = System.currentTimeMillis();

            long currentTime = System.currentTimeMillis() ;
            currentTime +=110*60*1000; //判断110分钟 小十分钟
            Date objNow = new Date(currentTime);
            if (objNow.getTime() >= objValidTime.getTime()) {
                // 通过http获取最新access_token，且存到redis
                return getAccessTokenByHttp();
            } else {
                return qyWcToken; // 直接返回access_token
            }
        }
    }

    /**
     * 获取最新access_token，且存到redis
     *
     * @return String access_token
     */
    public String getAccessTokenByHttp() throws Exception{
        // 拼接获取access_token的url
        String strUrl = tokenurl + "?corpid=" + corpid + "&corpsecret=" + corpsecret;
        // 调用get方法获取
        HttpClient client =  HttpClientBuilder.create().build();
        HttpGet httpGet=new HttpGet(strUrl);
        HttpResponse response = client.execute(httpGet);
        String strResult = EntityUtils.toString(response.getEntity());
        if (!StringUtils.isEmpty(strResult)) {
            // 把响应报文转成json对象
            JSONObject objJsonObject = (JSONObject) JSONObject.parse(strResult);
            if (null != objJsonObject) {
                // 出错返回码，为0表示成功，非0表示调用失败
                int iErrorCode = objJsonObject.getIntValue("errorCode");
                if (iErrorCode == 0) {
                    // 计算出失效日期
                    int iExpiresIn = objJsonObject.getIntValue("expires_in");
                    Date strValidDate = DateUtil.addDateMinute(new Date(), iExpiresIn);
                    // 把access_token和时间拼接，以逗号隔开，存放到redis里
                    String strAccessToken = objJsonObject.getString("access_token");
                    //String strAccessTokenAndTime = strAccessToken + "," + strValidDate;
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(RedisKeyEnum.ACCESS_TOKEN,strAccessToken);
                    hashMap.put(RedisKeyEnum.ACCESS_TOKEN_VALID,DateUtil.formatDateTime(strValidDate));
                    // 设置到redis里，目前的存活时间为120分钟(7200秒)
                    redisTemplate.boundHashOps("HashKey").putAll(hashMap);
                    return strAccessToken;
                }
            }
        }
        return null;
    }

    /**
     * 拼装redis中key的前缀
     *
     * @param code
     */
    private String codeKey(String code) {
        return "oauth2:codes:" + code;
    }

    /**
     * 获取精确到秒的时间戳
     * @param date
     * @return
     */
    public static int getSecondTimestampTwo(Date date){
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime()/1000);
        return Integer.valueOf(timestamp);
    }
}
