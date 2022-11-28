package com.netwisd.base.wf.constants;

public enum WfMsgSwitchEnum {


    on("on", "开"),
    off("off", "关");

    public String code;
    public String message;

    WfMsgSwitchEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    public static String getMessage(String code){
        for (WfMsgSwitchEnum value : WfMsgSwitchEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
