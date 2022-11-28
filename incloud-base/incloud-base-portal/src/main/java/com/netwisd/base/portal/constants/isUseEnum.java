package com.netwisd.base.portal.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum isUseEnum {

    NO(0, "否"),
    YES(1, "是");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (isUseEnum value : isUseEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
