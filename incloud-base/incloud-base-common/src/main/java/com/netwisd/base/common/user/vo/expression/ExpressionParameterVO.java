package com.netwisd.base.common.user.vo.expression;

import com.netwisd.base.common.constants.ExpreParamTypeEnum;
import com.netwisd.base.common.constants.ExpreParamVarTypeEnum;
import lombok.Data;
import springfox.documentation.service.Parameter;

@Data
public class ExpressionParameterVO {

    /**
     * 参数分类
     */
    private String paramType;

    /**
     * 参数类型
     */
    private String paramVarType;

    /**
     * 参数Id
     */
    private String paramId;

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 参数描述
     */
    private String paramDesc;


    public static ExpressionParameterVO buildParameter(Parameter parameter) {
        ExpressionParameterVO expressionParameterVO = new ExpressionParameterVO();
        expressionParameterVO.setParamType(ExpreParamTypeEnum.getCode(String.valueOf(parameter.getScalarExample())));
        expressionParameterVO.setParamVarType(ExpreParamVarTypeEnum.getCode(parameter.getParamType()));
        expressionParameterVO.setParamId(parameter.getName());
        expressionParameterVO.setParamName(parameter.getDescription());
        expressionParameterVO.setParamDesc(parameter.getDescription());
        return expressionParameterVO;
    }
}
