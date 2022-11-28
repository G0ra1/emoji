package com.netwisd.base.common.user.eunm;

/**
 * 部门属性
 */
public enum PostTypeEnum {
    POSITION(0, "职位"),
    JOB(1, "岗位");

    public Integer code;
    public String message;

    PostTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }


    public static String getMsgByCode(Integer code) {
        for (PostTypeEnum responseEnum : PostTypeEnum.values()) {
            if (responseEnum.getCode() == code) {
                return responseEnum.message;
            }
        }
        return null;
    }
}
