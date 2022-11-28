package com.netwisd.biz.mdm.constants;

public enum ContractType {

    PURCHASE(0, "采购合同"),
    SALE(1, "销售合同");

    public Integer code;
    public String message;

    ContractType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code){
        for (ContractType value : ContractType.values()) {
            if(value.code == code){
                return value.message;
            }
        }
        return null;
    }
}
