package com.netwisd.base.common.constants;

/**
 * 参数分类
 */
public enum ExpreParamTypeEnum {

    STANDARD_EXPRE("STANDARD_EXPRE", "标准内置"),
    CUSTOMIZE_EXPRE("CUSTOMIZE_EXPRE", "自定义");

    public String code;

    public String message;

    ExpreParamTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getCode(String message) {
        for (ExpreParamTypeEnum value : ExpreParamTypeEnum.values()) {
            if (value.message.equals(message)) {
                return value.code;
            }
        }
        return null;
    }
}
