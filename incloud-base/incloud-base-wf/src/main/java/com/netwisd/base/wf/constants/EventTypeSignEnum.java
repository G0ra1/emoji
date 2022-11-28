package com.netwisd.base.wf.constants;

public enum EventTypeSignEnum {

    PROC_DEF_EVENT(0, "流程定义事件"),
    SEQU_DEF_EVENT(1, "序列流定义事件"),
    NODE_DEF_EVENT(2, "节点定义事件"),
    GATEWAY_DEF_EVENT(3, "网关定义事件"),
    SUB_PROC_DEF_EVENT(4, "子流程-流程定义事件"),
    SEQUENCE_FLOW_DEF_EVENT(5, "序列流定义事件"),
    CALLACTIVITY_DEF_EVENT(6, "外部子流程定义事件"),
    ;

    public Integer code;
    public String message;

    EventTypeSignEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (EventTypeSignEnum value : EventTypeSignEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
