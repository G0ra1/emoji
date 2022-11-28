package com.netwisd.base.common.user.eunm;

public enum RoleTypeEnum {

    DEFAULT_ROLE(0, "默认角色");

    public Integer code;
    public String message;

    RoleTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

}
