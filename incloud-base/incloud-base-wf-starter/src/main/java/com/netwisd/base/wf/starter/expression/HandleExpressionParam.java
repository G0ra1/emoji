package com.netwisd.base.wf.starter.expression;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@ApiModel(value = "处理条件表达式参数 DTO")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class HandleExpressionParam implements Serializable {

    private String paramId; //参数名
    private Object paramValue; //参数值
    private String paramType; //参数类型 form_field 表单字段 hand_field 手动输入 orm_field 字段映射
}
