package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CardTypeEnum {
    居民身份证(0, "居民身份证"),
    港澳居民来往内地通信证(1, "港澳居民来往内地通信证"),
    港澳居民居住证(2, "港澳居民居住证"),
    台湾居民来往大陆通行证(3, "台湾居民来往大陆通行证"),
    台湾居民居住证(4, "台湾居民居住证"),
    外国护照(5, "外国护照"),
    外国人永久居留身份证(6, "外国人永久居留身份证"),
    外国人居留证(7, "外国人居留证");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (CardTypeEnum value : CardTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
