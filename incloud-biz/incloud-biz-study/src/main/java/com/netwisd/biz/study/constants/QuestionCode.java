package com.netwisd.biz.study.constants;

/**
 * 题目类型枚举
 */
public enum QuestionCode {

    SINGLE_CHOICE(0, "单选题"),
    MULTIPLE_CHOICE(1, "多选题"),
    COMPLETION(2, "填空题"),
    JUDGMENT(3, "判断题"),
    SHORT_ANSWER(4, "简答题");


    public Integer code;
    public String message;

    QuestionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (QuestionCode value : QuestionCode.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
