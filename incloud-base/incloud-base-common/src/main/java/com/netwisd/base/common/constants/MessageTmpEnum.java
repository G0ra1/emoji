package com.netwisd.base.common.constants;

public enum MessageTmpEnum implements IEnum<String> {

    TODOTASK("todoTask", "待办消息"),
    UNREADTASK("unreadTask", "待办消息"),
    URGE("urge", "催办"),
    APPLYTASK("applyTask", "申请人消息"),
    ;

    private String code;
    private String message;

    MessageTmpEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
