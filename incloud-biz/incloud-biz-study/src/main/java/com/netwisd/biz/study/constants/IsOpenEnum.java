package com.netwisd.biz.study.constants;

/**
 * @Description:
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/12/13 17:43
 */
public enum IsOpenEnum {

    NO_OPEN(0, "不公开"),
    YES_OPEN(1, "公开"),
    IN_APPLY(2,"审核中");

    public Integer code;
    public String message;

    IsOpenEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (IsOpenEnum value : IsOpenEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
