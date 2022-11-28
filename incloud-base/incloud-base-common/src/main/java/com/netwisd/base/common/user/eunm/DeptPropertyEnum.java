package com.netwisd.base.common.user.eunm;

/**
 * 部门属性
 */
public enum DeptPropertyEnum {

    MANAGEMENTDEPT(1, "管理部"),
    PRODUCE(2, "生产部");

    public Integer code;

    public String message;

    DeptPropertyEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public static String getMsgByCode(Integer code) {
        for (DeptPropertyEnum responseEnum : DeptPropertyEnum.values()) {
            if (responseEnum.getCode() == code) {
                return responseEnum.message;
            }
        }
        return null;
    }
}
