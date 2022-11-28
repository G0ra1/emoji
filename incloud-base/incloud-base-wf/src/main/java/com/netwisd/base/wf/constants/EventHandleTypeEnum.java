package com.netwisd.base.wf.constants;

public enum EventHandleTypeEnum {

    UPDATE_MSG(0, "修改事件"),
    DELETE_MSG(1, "删除事件");

    public Integer code;
    public String message;

    EventHandleTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (EventHandleTypeEnum value : EventHandleTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
