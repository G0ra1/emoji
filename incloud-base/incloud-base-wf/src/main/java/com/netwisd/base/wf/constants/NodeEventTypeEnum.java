package com.netwisd.base.wf.constants;

public enum NodeEventTypeEnum {

    PROCDEF("0", "流程定义"),
    USERTASK_SINGLE("1", "用户任务-单实例"),
    MULTIINSTANCETASK("2", "用户任务-多实例"),
    SEQU("3", "序列流"),
    START("4", "开始"),
    END("5", "结束"),
    GATEWAY("7", "网关"),
    ALL("8", "全部"),
    USERTASK("12", "用户任务"), //包含1 2
//    ALL_PROCDEF(13, "流程定义-子流程"), //4.0版本取消实现
//    SUB_PROCDEF(14, "流程定义-内嵌子流程"), //4.0版本取消实现
    CALLACTIVITY("15", "流程定义-外嵌流程");

    public String code;
    public String message;

    NodeEventTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (NodeEventTypeEnum value : NodeEventTypeEnum.values()) {
            if(value.code.equals(code)){
                return value.message;
            }
        }
        return null;
    }
}
