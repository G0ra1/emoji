package com.netwisd.biz.study.constants;

/**
 * 授权对象类型
 */
public enum RangeTypeEnum {
    ORG("0", "机构"),
    DEPT("1", "部门"),
    USER("2", "人员");


    public String  code;
    public String message;

    RangeTypeEnum(String  code, String message) {
        this.code = code;
        this.message = message;
    }
    public String  getCode() {
        return code;
    }

    public static String getMessage(String  code){
        for (RangeTypeEnum value : RangeTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
