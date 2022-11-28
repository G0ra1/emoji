package com.netwisd.base.wf.constants;

public enum ListenerTypeEnum {

    STANDARD(0, "标准(内置)"),
    CUSTOM(1, "自定义");

    public Integer code;
    public String message;

    ListenerTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (ListenerTypeEnum value : ListenerTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
