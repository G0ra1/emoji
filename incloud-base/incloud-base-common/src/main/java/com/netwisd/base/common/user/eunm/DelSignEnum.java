package com.netwisd.base.common.user.eunm;

/**
 * 是否删除
 */
public enum DelSignEnum {

    UNDELETE(0, "未删除"),
    DELETE(1, "已删除");

    public Integer code;

    public String message;

    DelSignEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

}
