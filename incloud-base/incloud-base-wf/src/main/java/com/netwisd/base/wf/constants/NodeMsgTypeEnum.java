package com.netwisd.base.wf.constants;

public enum NodeMsgTypeEnum {

    STARTEVENT(0, "开始节点"),
    USERTASK(1, "用户节点"),
    SERVICETASK(2, "服务节点"),
    ENDEVENT(3, "结束节点"),
    EXCLUSIVEGATEWAY(4, "排它网关"),
    SEQU(5, "流程序列流");

    public Integer code;
    public String message;

    NodeMsgTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (NodeMsgTypeEnum value : NodeMsgTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
