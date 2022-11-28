package com.netwisd.base.common.constants;

public enum StudyUserTypeEnum {
    ADMIN(0, "管理员"),
    TEACHER(1, "讲师"),
    STUDENT(2, "学员");

    public Integer code;
    public String message;

    StudyUserTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (StudyUserTypeEnum value : StudyUserTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
