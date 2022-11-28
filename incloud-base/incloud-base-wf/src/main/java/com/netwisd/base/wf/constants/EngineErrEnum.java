package com.netwisd.base.wf.constants;

/**
 * 解析camunda 引擎发布时的异常
 */
public enum EngineErrEnum {

    ENGINE_09005("ENGINE-09005", "请检查网关、节点、线元素元素是否正确！");

    public String code;
    public String message;

    EngineErrEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    public static String getMessage(String code){
        for (EngineErrEnum value : EngineErrEnum.values()) {
            if(value.code.equals(code)){
                return value.message;
            }
        }
        return null;
    }
}
