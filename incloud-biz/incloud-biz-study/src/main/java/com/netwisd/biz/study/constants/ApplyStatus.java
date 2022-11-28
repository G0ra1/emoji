package com.netwisd.biz.study.constants;

/**
 * @Description:
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/12/13 17:43
 */
public enum ApplyStatus {

    NO_APPLY(0, "未审核"),
    APPLY_PASS(1, "审核通过"),
    APPLY_NOTPASS(2, "审核不通过"),
    IN_APPLY(3,"审核中");

    public Integer code;
    public String message;

    ApplyStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (ApplyStatus value : ApplyStatus.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
