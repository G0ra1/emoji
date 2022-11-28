package com.netwisd.base.common.constants;

/**
 * 表达式返回泛型
 */
public enum ExpreReturnGenericsEnum {

    //标准内置
    CUSTOMIZE_EXPRE(-1, "UserExpressionVO"),
    BOOLEAN_EXPRE(1, "boolean"),
    STRING_EXPRE(2, "string"),
    INTEGER_EXPRE(3, "integer"),
    LONG_EXPRE(4, "long"),
    DOUBLE_EXPRE(5, "double"),
    LIST_EXPRE(6, "List");

    public Integer code;

    public String message;

    ExpreReturnGenericsEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Integer getCode(String message) {
        for (ExpreReturnGenericsEnum value : ExpreReturnGenericsEnum.values()) {
            if (value.message.equals(message)) {
                return value.code;
            }
        }
        return null;
    }

}
