package com.netwisd.biz.asset.constants;

import lombok.Getter;

@Getter
public enum DayBookType {

    REGISTER(0,"购置登记"),
    ACCEPTANCE(1,"购置验收"),
    STORAGE(2,"购置入库"),
    ENTRY(3,"财务入账"),
    ACCEPT(4,"领用申请"),
    REFUND(5,"领用退还"),
    DELIVERY(6,"出库申请"),
    WITHDRAWAL(7,"退库"),
    BORROW(8,"借用申请"),
    REMAND(9,"借用归还"),
    CHANGE(10,"信息变更"),
    DISTRIBUTE(11,"任务派发"),
    SIGN(12,"派发签收");


    public Integer code;
    public String name;

    DayBookType(Integer code,String name){
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer code){
        for (DayBookType value : DayBookType.values()) {
            if(value.code == code){
                return value.name;
            }
        }
        return null;
    }

}
