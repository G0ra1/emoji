package com.netwisd.base.common.constants;

public enum ModelPropertyEnum implements IEnum<Integer> {

    SINGLE(1, "common","单体"),//SINGLE
    MASTER_SLAVER(2,"ms", "主从"),//MASTER_SLAVER
    ASSOCIATION(3, "ms","关联"),//ASSOCIATION
    ;

    private Integer code;
    private String msgTmp;
    private String message;

    ModelPropertyEnum(Integer code, String msgTmp, String message) {
        this.code = code;
        this.msgTmp = msgTmp;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
