package com.netwisd.base.common.constants;

/**
 * @Description: 在线学习人员身份（管理员、讲师、学员）
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/11/25 16:35
 */
public enum StudyEnum {
    STUDY_ADMIN(0, "STUDY_ADMIN"),
    STUDY_TEACHER(1, "STUDY_TEACHER"),
    STUDY_STUDENT(2,"STUDY_STUDENT");

    public Integer code;
    public String message;

    StudyEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (StudyEnum value : StudyEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
