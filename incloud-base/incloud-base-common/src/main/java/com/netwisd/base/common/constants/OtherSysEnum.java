package com.netwisd.base.common.constants;

/**
 * @Description:
 * @Author: XHL
 * @Date: 2021/11/16 1:48 下午
 */
public enum OtherSysEnum {

    GEPS("GEPS","广联达工作流"),
    OA("OA","致远OA");

    public String code;
    public String message;

    OtherSysEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public static String getMessage(String code) {
        if (code != null) {
            for (OtherSysEnum value : OtherSysEnum.values()) {
                if (value.code .equals( code)) return value.message;
            }
        }
        return null;
    }
}
