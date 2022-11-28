package com.netwisd.base.wf.constants;

public enum FieldsPowerCodeEnum {

    READONLY("readonly ", "只读"),
    EDIT("edit", "编辑"),
    REQUIRED ("required", "必填"),
    HIDE("hide", "不可见");

    public String code;
    public String message;

    FieldsPowerCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    public static String getMessage(String code){
        for (FieldsPowerCodeEnum value : FieldsPowerCodeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
