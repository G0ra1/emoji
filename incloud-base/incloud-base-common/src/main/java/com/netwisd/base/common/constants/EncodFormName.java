package com.netwisd.base.common.constants;

public enum EncodFormName implements IEnum<String> {

    WF_FORM_NAME("wf", "工作流表单"),
    QT_FORM_NAME("qt", "其他表单"),
    ;



    private String code;
    private String message;

    EncodFormName(String code, String message) {
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
