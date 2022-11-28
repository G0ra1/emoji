package com.netwisd.biz.study.constants;

/**
 * @Description:
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/12/12 10:34
 */
public enum PaperUserStatus {
    NO_ANSWER(0, "未答卷"),
    NO_MARK(1, "未判分"),
    YES_MARK(2, "已判分");

    public Integer code;
    public String message;

    PaperUserStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (PaperUserStatus value : PaperUserStatus.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
