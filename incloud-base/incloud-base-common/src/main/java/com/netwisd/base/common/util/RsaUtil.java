package com.netwisd.base.common.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

public class RsaUtil {

    private static final String pri = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALrLWoXPU/Ex+gogTHQaWXDCPOzIBQUEUZMowguJW4deLiRfS69x31VxRSr8kxSD/cL8ifhlaQhuo3gS8XfltGTf6/6B2XXjKmrYLTaZaD86CGeG0bFSyDytZlWs0H0aNoK0pdDjrb4f8dO1wMpM6wt7qODvpygjZvBsflAW1fYnAgMBAAECgYAQtID6+Ii0SFjpMOxFcdnP2L8kGdtBEJrPA1UfPQB/ga+0twUIwrFLbd7WslHhAtDd8EHSghc7ltFtupv3sgZI26BXLe1EwiPr/vC9ZAi6IKQDDU2AEdYRzIDigfl8XJCGk3woRXNzqQcJafZw36XpT5k0pAMImYG/oCNTF2KrmQJBAP6otIqOP4DnjeJ6Pojswm3GCReyHUMFO8rS2SqZoGEEbKjl6CZJnogpNl+D85//WT7D1YzutZ8U1i8zTBn3DD0CQQC7xym4M9CB8S2j22af6ueIPuNVjBpyHqgxTHbshzdPf5wB7wFZEtUv+RQZT/oYSr4YnnMRqtYq8G505ePEAv4zAkBgER6CgmT8aN3CiSEcIEy8go+di8i0Jr5GpkHcazXwQ24GTSzFfNI8RWfIoot+WSK+pbvivY5wY7jk93IG/YZ1AkBGzW7epLLe/BhQa17Dt6f7iHLhg8VI+GREymchAk8Jq70gQYVJl79Iqms4rB5J4IzS7ZPHupmscSHE9BWwh8xfAkBEqfjRdOb8fryMD17xFQOj4OC3XxhutM7moBGKEUvmhYEmcK2A0sI/HZq6ZZd+hNNaXmmt7quSDhVvgZZ3jKXw";

    private static final String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6y1qFz1PxMfoKIEx0GllwwjzsyAUFBFGTKMILiVuHXi4kX0uvcd9VcUUq/JMUg/3C/In4ZWkIbqN4EvF35bRk3+v+gdl14ypq2C02mWg/OghnhtGxUsg8rWZVrNB9GjaCtKXQ462+H/HTtcDKTOsLe6jg76coI2bwbH5QFtX2JwIDAQAB";

    private static final RSA rsa = new RSA(pri, null);

    public static String handleSecret(String password) {
        //byte[] aByte = HexUtil.decodeHex(fileDs.getMinioSecretKey());
        byte[] decrypt = rsa.decrypt(password, KeyType.PrivateKey);
        return StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8);
    }

}
