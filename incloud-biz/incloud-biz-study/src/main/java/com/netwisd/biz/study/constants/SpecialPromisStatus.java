package com.netwisd.biz.study.constants;

/**
 * 专题权限状态
 *
 * @Description:
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/12/13 17:43
 */
public enum SpecialPromisStatus {

    NEED_APPLY(0, "需申请"),
    NO_NEED(1, "无需申请"),
    APPLY_ING(2, "申请中");

    public Integer code;
    public String message;

    SpecialPromisStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code) {
        for (SpecialPromisStatus value : SpecialPromisStatus.values()) {
            if (value.code == code) {
                return value.message;
            }
        }
        return null;
    }
}
