package com.netwisd.base.common.user.eunm;

public enum EmploymentEnum {

    PRACTICE_WORKER(1, "实习工"),
    TEMPORARY_WORKER(2, "临时工"),
    FORMAL_WORKER(3, "正式工");

    public Integer code;

    public String message;

    EmploymentEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public static String getMsgByCode(Integer code) {
        for (EmploymentEnum responseEnum : EmploymentEnum.values()) {
            if (responseEnum.getCode() == code) {
                return responseEnum.message;
            }
        }
        return null;
    }

}
