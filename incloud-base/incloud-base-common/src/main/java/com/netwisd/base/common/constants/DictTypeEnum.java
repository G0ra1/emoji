package com.netwisd.base.common.constants;

public enum DictTypeEnum implements IEnum<Integer> {

    SYSTEM(1, "系统类"),
    BUSINESS(2, "业务类");

    private Integer code;
    private String message;

    DictTypeEnum(Integer code, String message) {
        this.code = code;
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
