package com.netwisd.base.wf;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/12/2 7:40 下午
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 小端数据，Byte转换
 *
 */
public class ByteConvert {
    public static void main(String[] args) {
        String url = "https://ybz.yonyoucloud.com/open/token/getAccessToken";
        String clientId = "91046df3-5620-4c0c-b72e-60b0b6a79341";
        String clientSecret = "bf142c5e298241c9b2c0499a523292ef";
        String tenantId = "c4dxejnb";
        Long timeInterval = 1649320519L;
        String sign = SHA1(clientId+clientSecret+tenantId+timeInterval);
        System.out.println(sign);
    }

    /**
     * Java原生SHA1散列
     * @param decript
     * @return
     */
    public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }



    public static final int UNICODE_LEN = 2;


    /**
     * int转换为小端byte[]（高位放在高地址中）
     * @param iValue
     * @return
     */
    public byte[] Int2Bytes_LE(int iValue){
        byte[] rst = new byte[4];
        // 先写int的最后一个字节
        rst[0] = (byte)(iValue & 0xFF);
        // int 倒数第二个字节
        rst[1] = (byte)((iValue & 0xFF00) >> 8 );
        // int 倒数第三个字节
        rst[2] = (byte)((iValue & 0xFF0000) >> 16 );
        // int 第一个字节
        rst[3] = (byte)((iValue & 0xFF000000) >> 24 );
        return rst;
    }


    /**
     * 转换String为byte[]
     * @param str
     * @return
     */
    public byte[] String2Bytes_LE(String str) {
        if(str == null){
            return null;
        }
        char[] chars = str.toCharArray();

        byte[] rst = Chars2Bytes_LE(chars);

        return rst;
    }



    /**
     * 转换字符数组为定长byte[]
     * @param chars              字符数组
     * @return 若指定的定长不足返回null, 否则返回byte数组
     */
    public byte[] Chars2Bytes_LE(char[] chars){
        if(chars == null)
            return null;

        int iCharCount = chars.length;
        byte[] rst = new byte[iCharCount*UNICODE_LEN];
        int i = 0;
        for( i = 0; i < iCharCount; i++){
            rst[i*2] = (byte)(chars[i] & 0xFF);
            rst[i*2 + 1] = (byte)(( chars[i] & 0xFF00 ) >> 8);
        }

        return rst;
    }




    /**
     * 转换byte数组为int（小端）
     * @return
     * @note 数组长度至少为4，按小端方式转换,即传入的bytes是小端的，按这个规律组织成int
     */
    public static int Bytes2Int_LE(byte[] bytes){
        if(bytes.length < 4)
            return -1;
        int iRst = (bytes[0] & 0xFF);
        iRst |= (bytes[1] & 0xFF) << 8;
        iRst |= (bytes[2] & 0xFF) << 16;
        iRst |= (bytes[3] & 0xFF)<< 24;

        return iRst;
    }



    /**
     * 转换byte数组为int（大端）
     * @return
     * @note 数组长度至少为4，按小端方式转换，即传入的bytes是大端的，按这个规律组织成int
     */
    public static int Bytes2Int_BE(byte[] bytes){
        if(bytes.length < 4)
            return -1;
        int iRst = (bytes[0] << 24) & 0xFF;
        iRst |= (bytes[1] << 16) & 0xFF;
        iRst |= (bytes[2] << 8) & 0xFF;
        iRst |= bytes[3] & 0xFF;

        return iRst;
    }



    /**
     * 转换byte数组为Char（小端）
     * @return
     * @note 数组长度至少为2，按小端方式转换
     */
    public char Bytes2Char_LE(byte[] bytes){
        if(bytes.length < 2)
            return (char)-1;
        int iRst = (bytes[0] & 0xFF);
        iRst |= (bytes[1] & 0xFF) << 8;

        return (char)iRst;
    }




    /**
     * 转换byte数组为char（大端）
     * @return
     * @note 数组长度至少为2，按小端方式转换
     */
    public char Bytes2Char_BE(byte[] bytes){
        if(bytes.length < 2)
            return (char)-1;
        int iRst = (bytes[0] << 8) & 0xFF;
        iRst |= bytes[1] & 0xFF;

        return (char)iRst;
    }



}
