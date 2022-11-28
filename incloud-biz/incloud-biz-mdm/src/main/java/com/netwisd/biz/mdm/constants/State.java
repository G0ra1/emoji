package com.netwisd.biz.mdm.constants;

public enum State {

    NORMAL(0, "正常"),
    DISABLE(1, "停用");

    public Integer code;
    public String message;

    State(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (State value : State.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
