package com.netwisd.base.wf.constants;

public enum EventBindTypeEnum {

    NONE("", "无"),
    CREATE("create", "创建"),
    ASSIGNMENT("assignment", "签收"),
    REJECTED("rejected", "被驳回"),
    REVOKE("revoke", "撤回"),
    BEFORE_SUBMIT("before_complete", "提交前"),
    COMPLETE("complete", "完成"),
    DELETE("delete", "删除"),
    UPDATE("update", "修改"),
    START("start", "开始"),
    END("end", "结束"),
    TAKE("take", "流转"),
    SUSPEND_PROCESS("suspend_process", "流程挂起"),
    END_PROCESS("end_process", "流程终止"),
    ACTIVATE_PROCESS("activate_process", "流程激活"),
    DELETE_PROCESS("delete_process", "流程删除");

    public String code;
    public String message;

    EventBindTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    public static String getMessage(String code){
        for (EventBindTypeEnum value : EventBindTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
