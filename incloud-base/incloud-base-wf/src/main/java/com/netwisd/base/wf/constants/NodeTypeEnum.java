package com.netwisd.base.wf.constants;

public enum NodeTypeEnum {

    STARTEVENT(0, "开始节点"),
    USERTASK(1, "用户节点"),
    SERVICETASK(2, "服务节点"),
    ENDEVENT(3, "结束节点"),
    EXCLUSIVEGATEWAY(4, "排它网关"),
    MULTIINSTANCETASK(5, "会签多任务节点"),//nodedef 里面用户节点包含多实例节点(有字段区分)
    SUBPROCESS(6, "内嵌子流程"),
    MULTIINSTANCESUBPROCESS(7, "内嵌子流程多实例节点"),
    CALLACTIVITY(8, "外部子流程"),
    MULTIINSTANCECALLACTIVITYS(9, "外部子流程多实例节点"),
    PARALLELGATEWAY(10, "并行网关"),
    INCLUSIVEGATEWAY(11, "合并网关"),;

    public Integer code;
    public String message;

    NodeTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (NodeTypeEnum value : NodeTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
