package com.netwisd.biz.study.constants;

/**
 * @Description:
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/12/12 10:01
 */
public enum LessonUserStatus {
    PLAN(0, "培训计划"),
    APPLY(1, "我的报名"),
    COLLECTION(2, "我的收藏");

    public Integer code;
    public String message;

    LessonUserStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (LessonUserStatus value : LessonUserStatus.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
