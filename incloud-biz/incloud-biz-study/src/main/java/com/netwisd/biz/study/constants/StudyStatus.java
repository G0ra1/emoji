package com.netwisd.biz.study.constants;

/**
 * @Description:
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/12/6 10:07
 */
public enum StudyStatus {

    NO_TAKE_EFFECT(0, "未生效"),
    TAKE_EFFECT(1, "已生效"),
    IN_UPDATE(2, "调整中");

    public Integer code;
    public String message;

    StudyStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (StudyStatus value : StudyStatus.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
