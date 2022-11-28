package com.netwisd.base.common.user.eunm;

/**
 * 启用/禁用状态
 *
 * @Description TODO
 * @Author zhaixiaoliang
 * @Date 2018-12-17 16:40
 */
public enum EnableStateEnum {

    ENABLE(1,"启用"),
    DISABLE(0,"禁用");

    public Integer code;
    public String message;

    EnableStateEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public static String getText(Integer code) {
        for (EnableStateEnum value : EnableStateEnum.values()) {
            if (value.code == code) {
                return value.message;
            }
        }
        return null;
    }
}
