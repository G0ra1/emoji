package com.netwisd.biz.study.constants;

public enum UseRangeEnum {
    GONGKAI(1, "公开"),
    SIYOU(2, "私有");

    public Integer code;
    public String message;

    UseRangeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (UseRangeEnum value : UseRangeEnum.values()) {
            if(value.code.equals(code) ){
                return value.message;
            }
        }
        return null;
    }
}
