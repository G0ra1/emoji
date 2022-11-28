package com.netwisd.base.wf.constants;

public enum MyTaskTypeEnum {

    DRAFT("draft", "草稿"),
    TODO("todo", "待办"),
    DONE("done", "已办"),
    MY_DRAFT("my_draft", "我发起的流程"),
    RECEIVE_NOTIFY("receive_notify", "收到的知会"),
    SEND_NOTIFY("send_notify", "发出的知会"),
    FORWARD("forward", "转办"),
    IN_DUPLICATE("in_duplicate", "我收到的传阅"),
    OUT_DUPLICATE("out_duplicate", "我收到的传阅"),
    DELEGATION("delegation", "我委托的待办"),
    ;

    public String code;
    public String message;

    MyTaskTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    public static String getMessage(String code){
        for (MyTaskTypeEnum value : MyTaskTypeEnum.values()) {
            if(value.code.equals(code)){
                return value.message;
            }
        }
        return null;
    }
}
