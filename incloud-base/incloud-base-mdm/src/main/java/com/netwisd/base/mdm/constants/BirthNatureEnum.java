package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BirthNatureEnum {
    农业户口(0, "农业户口"),
    农业户口集体户(1, "农业户口-集体户"),
    非农户口(2, "非农户口"),
    非农户口集体户(3, "非农户口集体户"),
    未落常驻户口(4, "未落常驻户口"),
    其他户口(5, "其他户口");

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
