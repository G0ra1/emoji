package com.netwisd.base.portal.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum isDefault {
    NO(0, "否"),
    YES(1, "是");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (isDefault value : isDefault.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
