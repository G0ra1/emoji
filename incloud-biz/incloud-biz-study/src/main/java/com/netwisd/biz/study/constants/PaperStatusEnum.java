package com.netwisd.biz.study.constants;

/**
 * 人员考试表-试卷状态
 * 0已答题(学员答题完成点保存时)；1已交卷（选中提交时）；2已阅卷（老师阅卷保存时）
 */
public enum PaperStatusEnum {
    QUESTIONS_ANSWER(0, "已答题"),
    SUBMITTED_PAPERS(1, "已交卷"),
    MARKED_PAPERS(2, "已阅卷");

    public Integer code;
    public String message;

    PaperStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (PaperStatusEnum value : PaperStatusEnum.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
