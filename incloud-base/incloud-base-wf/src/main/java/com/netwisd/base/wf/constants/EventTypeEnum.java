package com.netwisd.base.wf.constants;

public enum EventTypeEnum {

    TASK_EVENT(0, "任务事件"),
    EXE_EVENT(1, "执行事件");

    public Integer code;
    public String message;

    EventTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (EventTypeEnum value : EventTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
