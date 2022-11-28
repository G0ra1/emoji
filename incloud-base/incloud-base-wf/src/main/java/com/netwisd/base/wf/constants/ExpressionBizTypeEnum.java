package com.netwisd.base.wf.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 表达式 biz type
 * @Author: XHL@netwisd.com
 * @Date: 2020/6/30 11:32 上午
 */
@AllArgsConstructor
@Getter
public enum ExpressionBizTypeEnum {

    EMPL("empl","人员"),
    ROLE("role","角色"),
    POST("post","岗位"),
    DUTY("duty","职务"),
    DEPT("dept","部门"),
    ORGAN("organ","机构"),
    RESOURCE_GROUP("resourceGroup","资源组"),
    MDM_EXPRESSION("mdmExpression","主数据表达式"),
    INNER_EXPRESSION("innerExpression","工作流内置据表达式");

    private String type;
    private String name;

    private static Map map = new HashMap();
    public static Map map(){
        for (ExpressionBizTypeEnum var : ExpressionBizTypeEnum.values()){
            map.put(var.type,var.name);
        }
        return map;
    }
}
