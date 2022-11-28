package com.netwisd.base.wf.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 表单字段或是手动输入
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/30 11:32 上午
 */
@AllArgsConstructor
@Getter
public enum ExpreParamSourceEnum {

    FORM_FIELD("form_field","表单字段"),
    HAND_FIELD("hand_field","手动输入"),
    ORM_FIELD("orm_field","变量映射"),
    ;

    private String type;
    private String name;

    private static Map map = new HashMap();
    public static Map map(){
        for (ExpreParamSourceEnum var : ExpreParamSourceEnum.values()){
            map.put(var.type,var.name);
        }
        return map;
    }
}
