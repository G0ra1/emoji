package com.netwisd.base.wf.starter.expression;

import lombok.Data;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description: 表达式调用的基本参数实体
 * @Author: zouliming@netwisd.com
 * @Date: 2021/4/22 10:21
 */
@Data
public class ExpressionEntity implements Serializable {
    /**
     * 流程定义id
     */
     String processDefinitionId;
    /**
     * nodeKey
     */
     String taskDefinitionKey;
    /**
     * 流程实例id
     */
     String processInstanceId;
    /**
     * 流程定义版本
     */
     Integer procdefVersion;
    /**
     * 流程分类ID
     */
     Long procdefTypeId;
    /**
     * 流程分类名称
     */
     String procdefTypeName;
    /**
     * 流程定义KEY
     */
     String formKey;
    /**
     * 业务标识，业务根据这个来调用真正的业务实现接口
     */
    String bizTag;
    /**
     * 业务参数值
     */
    Map args;

    /**
     * 业务参数的集合
     */
    List<HandleExpressionParam> argList;

    /**
     * 开始起草人ID
     */
    Long starterId;
}
