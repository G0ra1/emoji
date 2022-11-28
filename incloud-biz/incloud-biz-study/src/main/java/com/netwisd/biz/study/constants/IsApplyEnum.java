package com.netwisd.biz.study.constants;

public enum IsApplyEnum {
    NO(0, "否"),
    YES(1, "是");

    public Integer code;
    public String message;

    IsApplyEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (IsApplyEnum value : IsApplyEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
