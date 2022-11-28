package com.netwisd.base.common.constants;

/**
 * 表达式 参数类型
 */
public enum ExpreParamVarTypeEnum {

    USER_EXPRE("user","人员"),
    ROLE_EXPRE("role","角色"),
    POST_EXPRE("post","岗位"),
    DUTY_EXPRE("duty","职务"),
    DEPT_EXPRE("dept","部门"),
    MECHANISM_EXPRE("mechanism","机构"),
    BUILTINROLE_EXPRE("builtinrole","资源组"),
    BOOLEAN_EXPRE("boolean", "Boolean"),
    STRING_EXPRE("string", "String"),
    INTEGER_EXPRE("integer", "Integer"),
    LONG_EXPRE("long", "Long"),
    DOUBLE_EXPRE("double", "Double"),
    DATE_EXPRE("date", "Date"),
    LIST_EXPRE("list", "List");

    public String code;

    public String message;

    ExpreParamVarTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static String getCode(String message) {
        for (ExpreParamVarTypeEnum value : ExpreParamVarTypeEnum.values()) {
            if (value.message.equals(message)) {
                return value.code;
            }
        }
        return null;
    }

}
