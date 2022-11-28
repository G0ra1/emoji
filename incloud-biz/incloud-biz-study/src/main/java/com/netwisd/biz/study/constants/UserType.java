package com.netwisd.biz.study.constants;

/**
 * @Description: 在线学习角色枚举
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/12/13 17:38
 */
public enum UserType {
    ADMIN(0, "管理员"),
    TEACHER(1, "讲师"),
    STUDENT(2, "学员");

    public Integer code;
    public String message;

    UserType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (UserType value : UserType.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
