package com.netwisd.base.wf.constants;

/**
 * 事件返回类型
 */
public enum EventResutTypeEnum {
    RESUT_STANDARD(0, "标准(内置)"),
    RESUT_BOOLEAN(1, "Boolean"),
    RESUT_STRING(2, "String"),
    RESUT_INTEGER(3, "Integer"),
    RESUT_LONG(4, "Long"),
    RESUT_DOUBLE(5, "Double"),
    RESUT_LIST(6, "List");
    public Integer code;
    public String message;

    EventResutTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (EventResutTypeEnum value : EventResutTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
