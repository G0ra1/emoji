package com.netwisd.base.portal.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Base64;


/**
 * DES
 *
 * @Auther wangjb-c
 * @Date 2018/4/23
 */
public class CryptoUtils {

    static final String DEFAULT_ENCODING = "UTF-8";

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    }

    /**
     * HmacSHA1签名算法
     *
     * @param content
     * @param key
     * @return
     */
    public static String HmacSHA1SignatureBase64Encode(String content, String key) throws Exception {
        byte[] contentBytes = content.getBytes(DEFAULT_ENCODING);
        byte[] keyBytes = key.getBytes(DEFAULT_ENCODING);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(keySpec);
        byte[] hmaBytes = mac.doFinal(contentBytes);
        return base64Encode(hmaBytes);
    }

    /**
     * DES加密后，base64编码
     *
     * @param content
     * @param key
     * @return
     * @throws Exception
     */
    public static String DESEncryptBase64Encode(String content, String key) throws Exception {
        byte[] contentBytes = content.getBytes(DEFAULT_ENCODING);
        byte[] keyBytes = key.getBytes(DEFAULT_ENCODING);
        byte[] result = DESEncrypt(contentBytes, keyBytes, keyBytes);
        return base64Encode(result);
    }

    /**
     * DES加密后，base64编码
     *
     * @param content
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static String DESEncryptBase64Encode(String content, String key, String iv) throws Exception {
        byte[] contentBytes = content.getBytes(DEFAULT_ENCODING);
        byte[] keyBytes = key.getBytes(DEFAULT_ENCODING);
        byte[] ivBytes = iv.getBytes(DEFAULT_ENCODING);
        byte[] result = DESEncrypt(contentBytes, keyBytes, ivBytes);
        return base64Encode(result);
    }

    /**
     * base64解码后，DES解密
     *
     * @param content
     * @param key
     * @return
     * @throws Exception
     */
    public static String Base64DecodeDESDecrypt(String content, String key) throws Exception {
        byte[] bytesContent = base64Decode(content);
        byte[] bytesKey = key.getBytes(DEFAULT_ENCODING);
        byte[] result = DESDecrypt(bytesContent, bytesKey, bytesKey);
        return new String(result, DEFAULT_ENCODING);
    }

    /**
     * base64解码后，DES解密
     *
     * @param content
     * @param key
     * @return
     * @throws Exception
     */
    public static String Base64DecodeDESDecrypt(String content, String key, String iv) throws Exception {
        byte[] bytesContent = base64Decode(content);
        byte[] bytesKey = key.getBytes(DEFAULT_ENCODING);
        byte[] ivBytes = iv.getBytes(DEFAULT_ENCODING);
        byte[] result = DESDecrypt(bytesContent, bytesKey, ivBytes);
        return new String(result, DEFAULT_ENCODING);
    }

    /**
     * DES  CBC  PKCS7Padding 加密
     *
     * @param contentBytes
     * @param keyBytes
     * @return
     * @throws Exception
     */
    public static byte[] DESEncrypt(byte[] contentBytes, byte[] keyBytes, byte[] ivBytes) throws Exception {
        DESKeySpec keySpec = new DESKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(keySpec);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS7Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] result = cipher.doFinal(contentBytes);
        return result;
    }


    /**
     * DES  CBC  PKCS7Padding 解密
     *
     * @param contentBytes
     * @param keyBytes
     * @return
     * @throws Exception
     */
    public static byte[] DESDecrypt(byte[] contentBytes, byte[] keyBytes, byte[] ivBytes) throws Exception {
        DESKeySpec keySpec = new DESKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(keySpec);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS7Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
        byte[] result = cipher.doFinal(contentBytes);
        return result;
    }

    /**
     * base64编码
     *
     * @param content
     * @return
     */
    public static String base64Encode(byte[] content)  {
        try {
            return content.length == 0 ? "" : new String(Base64.getEncoder().encode(content), "UTF-8");
        }catch (Exception e){
            System.out.println("2222"+e);
        }
        return null;
    }


    public static String base64Encode(String content) throws UnsupportedEncodingException {
        return Base64Utils.encodeToString(content.getBytes(DEFAULT_ENCODING));
    }

    /**
     * base64解码
     *
     * @param content
     * @return
     */
    public static byte[] base64Decode(String content) {
        return Base64Utils.decodeFromString(content);
    }

    public static String base64DecodeToString(String content) throws UnsupportedEncodingException {
        return new String(Base64Utils.decodeFromString(content), DEFAULT_ENCODING);
    }

    /**
     * url 编码
     *
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String urlEncoding(String url) throws UnsupportedEncodingException {
        return java.net.URLEncoder.encode(url, DEFAULT_ENCODING);
    }

    /**
     * url 解码
     *
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String urlDecoding(String url) throws UnsupportedEncodingException {
        return java.net.URLDecoder.decode(url, DEFAULT_ENCODING);
    }

    /**
     * md5 加密
     *
     * @param content
     * @return
     */
    public static byte[] md5(String content) {
        return DigestUtils.md5(content);
    }

    /**
     * md5加密 base64编码
     *
     * @param content
     * @return
     */
    public static String md5Base64(String content) {
        return base64Encode(md5(content));
    }

    public static String md5ToString(String content) throws UnsupportedEncodingException {
        return org.springframework.util.DigestUtils.md5DigestAsHex(content.getBytes(DEFAULT_ENCODING));
    }

    /*public static void main(String[] args)throws Exception {
        long start =System.currentTimeMillis();
        //https://gbi.glodon.com/static/validation/index.html?userKey=IYdk3R7tup1jh%2bwESa2oajiCOJMY%2fEIZ60qiuvTEppQK36ZaH1xzE%2bwRQDVSYaAPEND42zs7Z%2bQYPv%2bITJWh6Q%3d%3d&source=GEPS_BLEND&tenant_code=jssg
        String key="thirddes";
        String content = "123qwe!@#";
        System.out.println(DESEncryptBase64Encode(content, key));

        String c = "/jI1Yc/hf7BnPbKY3p+bLbcT0Lo03HJ2ip2DRvuakPFnA8e5c8GGrmPNB41eYxtZzajIPgms9dzepGfHJmuUxg==";
        System.out.println(Base64DecodeDESDecrypt(c, "a1234567"));

        String c1 = "Q3TGGsDWe/ZxR7A1anbXj9Y7TsVST872q0WFcins/LueZjSz/WY3qaXKE3lAyuWjinaJLJA7RN8=";
        System.out.println(Base64DecodeDESDecrypt(c1, "a1234567"));

        System.out.println(base64Encode("434449e4e24b4e00a1ca0d623933fae1qiyebi-xjd"));

        System.out.println("434449e4e24b4e00a1ca0d623933fae1qiyebi-xjd".substring(32));

//		String pp="0sIeGXaCUNXKiLOZDxx1cRZULhCGNnuCMwFWJuog8hXieZNqPr2vFFEWL0Ko1w2aEmD3MDYZ22I=";
//		System.out.println(Base64DecodeDESDecrypt(pp, key, key));
//		System.out.println(System.currentTimeMillis()-start);
//
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	String timestamp = sdf.format(new Date());
//    	String key =  "a1234567";
//        String code="user2";
//        String content = String.format("%s;%s;%s", code, timestamp, key);
//        String userKey=DESEncryptBase64Encode(content, key);
//    	System.out.println("userKey:"+userKey);
//    	StringBuffer sb =new StringBuffer("https://grp-test.glodon.com/static/managerTool/index.html?groupCode=EMSETUP#/login?");
//    	sb.append(String.format("userKey=%s",userKey))
//    	.append("&source=bi&tenant_code=bidl");
//    	System.out.println("url:"+sb.toString());
//    	RestTemplate restTemplate = new RestTemplate();
//    	String result = restTemplate.getForObject(sb.toString(), String.class);
//    	System.out.println("rsult:"+result);

//    	System.out.println(md5ToString("password"));
//
//    	System.out.println(md5ToString("123456f"));


    }*/
}
