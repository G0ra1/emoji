package com.netwisd.base.common.constants;

/**
 * 参数类型
 */
public enum ExpreParamVarGenericsEnum {

    BOOLEAN_EXPRE(1, 1, "Boolean"),
    STRING_EXPRE(2, 2, "String"),
    INTEGER_EXPRE(3, 3, "Integer"),
    LONG_EXPRE(4, 4, "Long"),
    DOUBLE_EXPRE(5, 5, "Double");

    public Integer code;

    public Integer position;

    public String message;

    ExpreParamVarGenericsEnum(int code, Integer position, String message) {
        this.code = code;
        this.message = message;
        this.position = position;
    }

    public static Integer getCode(Integer position) {
        for (ExpreParamVarGenericsEnum value : ExpreParamVarGenericsEnum.values()) {
            if (value.position == position) {
                return value.code;
            }
        }
        return null;
    }

    public static Integer getCode(String message) {
        for (ExpreParamVarGenericsEnum value : ExpreParamVarGenericsEnum.values()) {
            if (value.message.equals(message)) {
                return value.code;
            }
        }
        return null;
    }

}
