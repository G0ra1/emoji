package com.netwisd.base.portal.util;

import cn.hutool.core.util.StrUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author dsy
 * @version 1.0.0
 * @title SHA1
 * @description TODO
 * @date 2022/10/11 10:14
 */

public class SHA {
    public static String SHA1(String decript) throws Exception{
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
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
    public static Map<String, String> getMapData(String getStr) {
        String[] strs = getStr.split("&");
        HashMap<String, String> dataMap = new HashMap<>(16);
        for (int i = 0; i < strs.length; i++) {
            String[] str = strs[i].split("=");
            dataMap.put(str[0], str[1]);
        }
        return dataMap;
    }

    public static String getSortedStr(Map<String, String> unSortedStr) {
        String sortedStr = unSortedStr
                .entrySet()
                .stream()
                .filter(entry -> !StrUtil.isEmpty(entry.getValue()))
                .sorted(Map.Entry.comparingByKey()).map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        return sortedStr;
    }
}
