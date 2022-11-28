package com.netwisd.biz.asset.constants;

import lombok.Getter;

/**
 * 物资-资产任务类型
 */
@Getter
public enum TaskType {

    ACCEPT(0,"领用");

    public Integer code;
    public String type;

    TaskType(Integer code,String type){
        this.code = code;
        this.type = type;
    }
}
