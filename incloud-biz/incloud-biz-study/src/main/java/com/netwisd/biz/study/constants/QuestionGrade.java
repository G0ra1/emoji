package com.netwisd.biz.study.constants;

public enum QuestionGrade
{
    EASY(0, "容易"),
    MODERATE(1, "中等"),
    HARD(2, "难");

    public Integer code;
    public String message;

    QuestionGrade(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (PaperStatusEnum value : PaperStatusEnum.values()) {
            if(value.code.equals(code)){
                return value.message;
            }
        }
        return null;
    }
}
