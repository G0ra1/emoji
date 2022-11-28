package com.netwisd.base.wf.constants;

public enum WfMsgSendTypeEnum {


    INMAIL("inmail", "站内信"),
    EMAIL("email", "邮件"),
    WECHAT("wechat", "微信");

    public String code;
    public String message;

    WfMsgSendTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    public static String getMessage(String code){
        for (WfMsgSendTypeEnum value : WfMsgSendTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
