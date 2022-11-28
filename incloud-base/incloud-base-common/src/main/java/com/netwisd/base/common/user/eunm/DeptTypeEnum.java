package com.netwisd.base.common.user.eunm;

public enum DeptTypeEnum {

    MECHANISM(1, "机构"),
    DEPARTMENT(2, "部门"),
    SUBSIDIARY(3, "子公司"),
    GROUP(4, "组"),
    CAUSE(5, "事业部");

    public Integer code;

    public String message;

    DeptTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }
}
