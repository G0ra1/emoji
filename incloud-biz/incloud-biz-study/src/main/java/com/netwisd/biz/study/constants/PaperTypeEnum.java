package com.netwisd.biz.study.constants;

/**
 * 试卷类型
 */
public enum PaperTypeEnum {
    LXT("0", "练习题"),
    KST("1", "考试题");


    public String code;
    public String message;

    PaperTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    public static String getMessage(String code){
        for (PaperTypeEnum value : PaperTypeEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
