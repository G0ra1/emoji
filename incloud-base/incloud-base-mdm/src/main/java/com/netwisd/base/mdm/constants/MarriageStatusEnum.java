package com.netwisd.base.mdm.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MarriageStatusEnum {
    已婚(1, "已婚"),
    未婚(2, "未婚");

    public Integer code;
    public String message;

    public static String getMessage(Integer code){
        for (MarriageStatusEnum value : MarriageStatusEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
