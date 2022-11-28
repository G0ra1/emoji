package com.netwisd.base.common.constants;

public enum DDLType implements IEnum<String> {

    INSERT("INSERT", "新增"),
    UPDATE("UPDATE", "修改"),
    DELETE("DELETE", "删除");

    private String code;
    private String message;

	DDLType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
