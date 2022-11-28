package com.netwisd.base.wf.constants;

public enum EventMsgTypeEnum {

    EXPRE_MSG(0, "表达式提醒"),
    EVENT_MSG(1, "事件提醒"),
    PROCDEFTYPE_MSG(2, "流程分类提醒"),
    FORM_MSG(3, "表单提醒"),
    BUTTON_MSG(4, "按钮提醒");

    public Integer code;
    public String message;

    EventMsgTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (EventMsgTypeEnum value : EventMsgTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
