package com.netwisd.base.common.user.eunm;

/**
 * 是否是主
 */
public enum IsPrimaryEnum {

    PRIMARY(0,"主"),
    SECONDARY(1,"次");

    public Integer code;

    public String message;

    IsPrimaryEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

}
