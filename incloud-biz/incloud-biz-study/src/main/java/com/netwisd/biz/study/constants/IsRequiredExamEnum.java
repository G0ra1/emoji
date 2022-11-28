package com.netwisd.biz.study.constants;

/**
 * 是否必考
 */
public enum IsRequiredExamEnum {
    NO(0, "否"),
    YES(1, "是");

    public Integer code;
    public String message;

    IsRequiredExamEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (IsRequiredExamEnum value : IsRequiredExamEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
