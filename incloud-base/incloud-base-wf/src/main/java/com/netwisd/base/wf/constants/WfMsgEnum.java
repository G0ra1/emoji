package com.netwisd.base.wf.constants;

public enum WfMsgEnum {


    todo("todo", "到达当前节点消息提醒"),
    apply("apply", "办理完成后申请人节点消息提醒");

    public String code;
    public String message;

    WfMsgEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    public static String getMessage(String code){
        for (WfMsgEnum value : WfMsgEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
