package com.netwisd.base.portal.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.portal.util.CryptoUtils;
import com.netwisd.base.portal.util.SHA;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author dsy
 * @version 1.0.0
 * @title PotalUrlController
 * @description TODO
 * @date 2022/10/9 9:56
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalUrl" )
@Api(value = "portalUrl", tags = "  url地址返回Controller")
@Slf4j
public class PotalUrlController{
    /**
     * @description: 获取轮播图数量
     * @param:
     * @return:
     * @author dsy
     * @date: 2022/10/13 12:29
     */
    @GetMapping("/getBackUrl")
    public Result<String> getBackUrl(@RequestParam("userId") String userId, @RequestParam("phone") String phone) throws Exception {
        String url = "https://gbi.glodon.com/api/passport/trd/ssoLogin?";
        String appKey = "YzA5MWM3NGE4MmEyNGJkOTk3YWIwZTBmNzY0NzdlNDU=";
        String grandType = "login";
        String source = "EBI_MOBILE";
        String timestamp=System.currentTimeMillis()+"";
        // 第三方用户ID truserID
        //String userId = "678";
        //String userId = "789";
        String encryptStr = new StringBuilder()
                //用户id
                .append(userId).append("&")
                //18092057358 手机号
                .append(phone).append("&")
                //时间戳
                .append(timestamp).toString();
        // 企业BI颁发appSecret
        String appSecret = "Mzg2YWUxYzUxYmY2MzcyMDUzZjNkNmYxZTkzZDFlNTI=";
        // DES加密密钥
        String keyStr = appSecret.substring(0, 24);
        // DES加密向量
        String ivStr = appSecret.substring(24, 32);
        // 生成密钥
        DESKeySpec desKeySpec = new DESKeySpec(keyStr.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        // 生成向量
        IvParameterSpec parameterSpec = new IvParameterSpec(ivStr.getBytes(StandardCharsets.UTF_8));
        // DES加密
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
        byte[] securityBytes = cipher.doFinal(encryptStr.getBytes(StandardCharsets.UTF_8));
        // Base64编码
        byte[] encode = Base64.getEncoder().encode(securityBytes);
        String res = new String(encode, StandardCharsets.UTF_8);
        String trdUserId = res;
        System.out.println(timestamp + "-------------------时间戳");
        System.out.println(trdUserId + "-------------------trdUserId");
        // 拼接签名串
        String signatureStr = new StringBuilder()
                .append("trdUserId=").append(trdUserId).append("&")
                .append("appKey=").append(appKey).append("&")
                .append("grandType=").append(grandType).append("&")
                .append("source=").append(source).append("&")
                .append("timestamp=").append(timestamp).toString();
        // 生成签名密钥
        System.out.println("---：" + signatureStr);
        SecretKeySpec keySpec = new SecretKeySpec(appSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
        // 对数据进行签名
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(keySpec);
        byte[] hmaSha1 = mac.doFinal(signatureStr.getBytes(StandardCharsets.UTF_8));
        // Base64编码
        byte[] base64Bytes = Base64.getEncoder().encode(hmaSha1);
        String result = new String(base64Bytes, StandardCharsets.UTF_8);
        // 输出为IS+UcNRcsCx5H9cD0cm1p07gTA8=
        System.out.println(result);
        // URL编码
        String signature = URLEncoder.encode(result, StandardCharsets.UTF_8.name());
        System.out.println("签名：");
        System.out.println(signature);
        String tt = CryptoUtils.HmacSHA1SignatureBase64Encode(signatureStr, appSecret);
        String aa = URLEncoder.encode(tt, "UTF-8");
        String signatureStr11 = new StringBuilder()
                .append("trdUserId=").append(URLEncoder.encode(trdUserId, StandardCharsets.UTF_8.name())).append("&")
                .append("appKey=").append(appKey).append("&")
                .append("grandType=").append(grandType).append("&")
                .append("source=").append(source).append("&")
                .append("timestamp=").append(timestamp)
                .append("&signature=")
                .append(aa).toString();
        JSONObject kk = new JSONObject();
        kk.put("roleIds", new ArrayList() {{
            add("1571791348904886272");
        }});
        kk.put("login_url", "http://10.0.198.20:3030/app/#/login?code=zhzyj");
        kk.put("main_page_url", "http://10.0.198.20:3030/index/#/entryOverview/1570714483314376704");
        kk.put("custom_template_ids", new HashMap() {{
            put("url1", "1028915064198807552");
        }});
        kk.put("companyId", "1571671043821727744");
        System.out.println(kk.toJSONString());
        //拼接路径
        String href = url + signatureStr11;
        //手机号登录     返回11012
        System.out.println(href + "----------------");
        String jsonObject = HttpRequest.post(href)
                .header("Authorization", "Basic WWpnNU16bG1ZVGN3TVRCaU5EYzVORGcxT0dZMFpqZzVNalUzTURrNU5UVT06TkRNME1qSXhPR1F3WVRVd09URm1aamsxTWpNME0yWmlNMlkwWVRBMU1qVT0=")
                .header("Content-Type", "application/json")
                .execute().body();
        System.out.println(jsonObject + "=============登录");
        //判断手机号
        String code = JSON.parseObject(jsonObject).getString("code");
        if (code.equals("0")) {
            String accessToken = JSON.parseObject(jsonObject).getJSONObject("data").getString("access_token");
            //获取模板接口
            String params = "https://gbi.glodon.com/api/enterprise/v4/enterprises/templates/customized?tenantId=1399262695675834368&loginType=WEB";
            String data = HttpRequest.get(params)
                    .header("Content-Type", "application/json")
                    .header("authorization", "bearer " + accessToken)
                    .execute().body();
            System.out.println(data);
            HashMap<String, String> hashMap = JSON.parseObject(data, HashMap.class);
            JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(hashMap.get("data")));
            String val = null;
            //判断jsonArray数组长度  >2取url的路径
            if (jsonArray.size() > 2) {
                val = JSONObject.parseObject(jsonArray.getString(2)).getString("url1");
                String[] split = val.split("\\?");
                System.out.println(split+"split");
                String b = split[1];
                System.out.println(b);
                Map<String, String> mapData = SHA.getMapData(b);
                mapData.put("companyid", "1399263639671095296");
                mapData.put("company_id", "1399263639671095296");
                mapData.put("compnay_id", "1399263639671095296");
                String str = SHA.getSortedStr(mapData);
                System.out.println(split[0] + str);
                String u=split[0]+"?";
                return Result.success(u+str);//返回轮播图的url
            } else {
                return Result.failed("11013",200,"该账号无权限");//没有轮播图返回的地址；
            }
        } else {
            return Result.failed("11012",200,"该账号未注册");//账号未注册；
        }
    }
    /**
     * @description: BI单点
     * @param:
     * @return:
     * @author dsy
     * @date: 2022/10/13 12:29
     */
    @GetMapping("/trpLogin")
    public Result<String> trpLogin(@RequestParam("userId") String userId, @RequestParam("phone") String phone) throws Exception {
        String url = "http://gbi.glodon.com/static/validation/index.html?";
        String appKey = "YzA5MWM3NGE4MmEyNGJkOTk3YWIwZTBmNzY0NzdlNDU=";
        String grandType = "login";
        String source = "EBI_MOBILE";
        String timestamp=System.currentTimeMillis()+"";
        // 第三方用户ID truserID
        //String userId = "678";
        //String userId = "789";
        String encryptStr = new StringBuilder()
                //用户id
                .append(userId).append("&")
                //18092057358 手机号
                .append(phone).append("&")
                //时间戳
                .append(timestamp).toString();
        // 企业BI颁发appSecret
        String appSecret = "Mzg2YWUxYzUxYmY2MzcyMDUzZjNkNmYxZTkzZDFlNTI=";
        // DES加密密钥
        String keyStr = appSecret.substring(0, 24);
        // DES加密向量
        String ivStr = appSecret.substring(24, 32);
        // 生成密钥
        DESKeySpec desKeySpec = new DESKeySpec(keyStr.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        // 生成向量
        IvParameterSpec parameterSpec = new IvParameterSpec(ivStr.getBytes(StandardCharsets.UTF_8));
        // DES加密
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
        byte[] securityBytes = cipher.doFinal(encryptStr.getBytes(StandardCharsets.UTF_8));
        // Base64编码
        byte[] encode = Base64.getEncoder().encode(securityBytes);
        String res = new String(encode, StandardCharsets.UTF_8);
        String trdUserId = res;
        System.out.println(timestamp + "-------------------时间戳");
        System.out.println(trdUserId + "-------------------trdUserId");
        // 拼接签名串
        String signatureStr = new StringBuilder()
                .append("trdUserId=").append(trdUserId).append("&")
                .append("appKey=").append(appKey).append("&")
                .append("grandType=").append(grandType).append("&")
                .append("source=").append(source).append("&")
                .append("timestamp=").append(timestamp).toString();
        // 生成签名密钥
        System.out.println("---：" + signatureStr);
        SecretKeySpec keySpec = new SecretKeySpec(appSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
        // 对数据进行签名
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(keySpec);
        byte[] hmaSha1 = mac.doFinal(signatureStr.getBytes(StandardCharsets.UTF_8));
        // Base64编码
        byte[] base64Bytes = Base64.getEncoder().encode(hmaSha1);
        String result = new String(base64Bytes, StandardCharsets.UTF_8);
        // 输出为IS+UcNRcsCx5H9cD0cm1p07gTA8=
        System.out.println(result);
        // URL编码
        String signature = URLEncoder.encode(result, StandardCharsets.UTF_8.name());
        System.out.println("签名：");
        System.out.println(signature);
        System.out.println("测试输出=======================================");
        String tt = CryptoUtils.HmacSHA1SignatureBase64Encode(signatureStr, appSecret);
        String aa = URLEncoder.encode(tt, "UTF-8");
        String signatureStr11 = new StringBuilder()
                .append("trdUserId=").append(URLEncoder.encode(trdUserId, StandardCharsets.UTF_8.name())).append("&")
                .append("appKey=").append(appKey).append("&")
                .append("grandType=").append(grandType).append("&")
                .append("source=").append(source).append("&")
                .append("timestamp=").append(timestamp)
                .append("&signature=")
                .append(aa).toString();
        JSONObject kk = new JSONObject();
        kk.put("roleIds", new ArrayList() {{
            add("1571791348904886272");
        }});
        kk.put("login_url", "http://10.0.198.20:3030/app/#/login?code=zhzyj");
        kk.put("main_page_url", "http://10.0.198.20:3030/index/#/entryOverview/1570714483314376704");
        kk.put("custom_template_ids", new HashMap() {{
            put("url1", "1028915064198807552");
        }});
        kk.put("companyId", "1571671043821727744");
        //拼接路径
        String href = url + signatureStr11;
        return  Result.success(href);
    }
    /**
     * @description: NC单点
     * @param:
     * @return:
     * @author dsy
     * @date: 2022/10/13 12:29
     */
    @GetMapping("/singOnNC")
    public Result<String> singOnNC(@RequestParam("phone")String  phone)throws Exception{
        String phones="1"+phone;
        String url="http://nc65.cnecc.com:80/portal/ssopt?";
        String clientId = "YNQXNOJNRQFB";
        String clientSecret = "dffd09f3b12b2345b0576ac56378a873";
        String timestamp=System.currentTimeMillis()+"";
        String sign= SHA.SHA1(clientId+clientSecret+timestamp+phones);
        String type="NC";
        String signatureStr = new StringBuilder()
                .append("clientid=").append(clientId).append("&")
                .append("sign=").append(sign).append("&")
                .append("timestamp=").append(timestamp).append("&")
                .append("mobile=").append(phones).append("&")
                .append("type=").append(type).toString();
        String href=url+signatureStr;
        return Result.success(href);
    }
    /**
     * @description: 报销单点
     * @param:
     * @return:
     * @author dsy
     * @date: 2022/10/13 12:29
     */
    @GetMapping("/singOnReimburse")
    public Result<String> singOnReimburse(@RequestParam("phone")String  phone)throws Exception{
        String phones="1"+phone;
        String url="http://nc65.cnecc.com:80/portal/ssopt?";
        String clientId = "YNQXNOJNRQFB";
        String clientSecret = "dffd09f3b12b2345b0576ac56378a873";
        String timestamp=System.currentTimeMillis()+"";
        String sign= SHA.SHA1(clientId+clientSecret+timestamp+phones);
        String signatureStr = new StringBuilder()
                .append("clientid=").append(clientId).append("&")
                .append("sign=").append(sign).append("&")
                .append("timestamp=").append(timestamp).append("&")
                .append("mobile=").append(phones) .toString();
        String href=url+signatureStr;
        return Result.success(href);
    }
    /**
     * @description: NC待办消息数量
     * @param:
     * @return:
     * @author dsy
     * @date: 2022/10/13 12:29
     */
    @GetMapping("/PendingMessagesToNC")
    public  Result<Map> PendingMessagesToNC()throws Exception{
        String url="http://10.255.26.177:8008/MsgService/message/getAllMsgCount?";
        String clientId = "YNQXNOJNRQFB";
        String clientSecret = "dffd09f3b12b2345b0576ac56378a873";
        String timestamp=System.currentTimeMillis()+"";
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String phone = loginAppUser.getPhoneNum();
        String sign= SHA.SHA1(clientId+clientSecret+timestamp+phone);
        String signatureStr = new StringBuilder()
                .append("clientid=").append(clientId).append("&")
                .append("sign=").append(sign).append("&")
                .append("timestamp=").append(timestamp).append("&")
                .append("mobile=").append(phone) .toString();
        String href=url+signatureStr;
        String res = HttpRequest.get(href).execute().body();
        System.out.println(res);
        Map<String, Integer> map = new HashMap<>();
        Integer val=null;
        if(res !=null){
            val=Integer.valueOf(res);
        }
        map.put("taskCount",val);
        return  Result.success(map);
    }
    /**
     * @description: 报销待办消息数量
     * @param:
     * @return:
     * @author dsy
     * @date: 2022/10/13 12:29
     */
    @GetMapping("/PendingMessagesToPORTAL")
    public Result<Map> PendingMessagesToPORTAL()throws Exception{
        String url="http://10.255.26.177:8008/MsgService/message/getMsgCount?";
        String clientId = "YNQXNOJNRQFB";
        String clientSecret = "dffd09f3b12b2345b0576ac56378a873";
        String timestamp=System.currentTimeMillis()+"";
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String phone = loginAppUser.getPhoneNum();
        String sign= SHA.SHA1(clientId+clientSecret+timestamp+phone);
        String signatureStr = new StringBuilder()
                .append("clientid=").append(clientId).append("&")
                .append("sign=").append(sign).append("&")
                .append("timestamp=").append(timestamp).append("&")
                .append("mobile=").append(phone) .toString();
        String href=url+signatureStr;
        String res = HttpRequest.get(href).execute().body();
        System.out.println(href);
        System.out.println(res);
        Map<String, Integer> map = new HashMap<>();
        Integer val=null;
        if(res !=null){
            val=Integer.valueOf(res);
        }
        map.put("taskCount",val);
        return  Result.success(map);
    }
}
