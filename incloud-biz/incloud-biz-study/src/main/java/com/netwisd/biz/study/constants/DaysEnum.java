package com.netwisd.biz.study.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public enum DaysEnum {
    TWO_DAYS(2,"计划结束后天数");




    public Integer code;
    public String message;


    DaysEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code) {
        for (DaysEnum value : DaysEnum.values()) {
            if (value.code == code) {
                return value.message;
            }
        }
        return null;
    }


}
