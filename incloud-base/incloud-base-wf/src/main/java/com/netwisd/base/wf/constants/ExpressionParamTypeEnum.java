package com.netwisd.base.wf.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 表达式 参数类型
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/30 11:32 上午
 */
@AllArgsConstructor
@Getter
public enum ExpressionParamTypeEnum {

    EMPL(0,"人员"),
    ROLE(1,"角色"),
    JOB(2,"职务"),
    POST(3,"岗位"),
    DEPT(4,"部门"),
    ORGAN(5,"机构"),
    INNER_ROLE(6,"内置角色");

    private Integer type;
    private String name;

    private static Map map = new HashMap();
    public static Map map(){
        for (ExpressionParamTypeEnum var : ExpressionParamTypeEnum.values()){
            map.put(var.type,var.name);
        }
        return map;
    }
}
