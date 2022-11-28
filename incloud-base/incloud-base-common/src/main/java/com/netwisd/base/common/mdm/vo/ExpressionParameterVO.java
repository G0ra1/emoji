package com.netwisd.base.common.mdm.vo;

import com.netwisd.base.common.constants.ExpreParamTypeEnum;
import com.netwisd.base.common.constants.ExpreParamVarTypeEnum;
import lombok.Data;
import springfox.documentation.service.Parameter;

@Data
public class ExpressionParameterVO {

    /**
     * 参数分类  内置 或者自定义
     */
    private String paramType;


    /**
     * 参数类型
     */
    private String expreParamVarType;

//    /**
//     * 参数Id
//     */
//    private String paramId;

    /**
     * 参数字段名称
     */
    private String expreParamName;

    /**
     * 参数描述
     */
    private String expreParamDesc;

    private static final String meth = "METH"; //前端判断使用


    public static ExpressionParameterVO buildParameter(Parameter parameter) {
        ExpressionParameterVO expressionParameterVO = new ExpressionParameterVO();
        expressionParameterVO.setParamType(ExpreParamTypeEnum.getCode(String.valueOf(parameter.getScalarExample())));
        expressionParameterVO.setExpreParamVarType(parameter.getParamType()); //ExpreParamVarTypeEnum.getCode()
        expressionParameterVO.setExpreParamName(parameter.getName());
        expressionParameterVO.setExpreParamDesc(parameter.getDescription());
        return expressionParameterVO;
    }

    public static ExpressionParameterVO buildDefaultParameter(String methName) {
        ExpressionParameterVO expressionParameterVO = new ExpressionParameterVO();
        expressionParameterVO.setParamType(ExpreParamTypeEnum.STANDARD_EXPRE.code);
        expressionParameterVO.setExpreParamVarType(ExpreParamVarTypeEnum.STRING_EXPRE.code);
        expressionParameterVO.setExpreParamName(methName);
        expressionParameterVO.setExpreParamDesc(meth);
        return expressionParameterVO;
    }
}
