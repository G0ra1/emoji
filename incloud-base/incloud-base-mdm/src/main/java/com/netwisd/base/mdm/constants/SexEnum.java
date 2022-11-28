package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SexEnum {
    男(1, "男"),
    女(2, "女");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (SexEnum value : SexEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
