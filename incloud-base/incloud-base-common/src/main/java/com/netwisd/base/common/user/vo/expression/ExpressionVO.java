package com.netwisd.base.common.user.vo.expression;

import com.netwisd.base.common.constants.ExpreParamVarGenericsEnum;
import com.netwisd.base.common.constants.ExpreReturnGenericsEnum;
import lombok.Data;
import springfox.documentation.service.Operation;

import java.util.List;

@Data
public class ExpressionVO {

    /**
     * 请求路径
     */
    private String path;

    /**
     * 请求方式
     */
    private String method;

    /**
     * 表达式名称
     */
    private String expressionVal;

    /**
     * 表达式描述
     */
    private String expressionName;

    /**
     * 表达式返回值
     */
    private Integer resultType;

    /**
     * 返回类型泛型
     */
    private Integer paramVarGenerics;

    /**
     * 表达式对应的参数
     */
    private List<ExpressionParameterVO> paramterList;

    public static ExpressionVO buildExpression(String path, Operation operation, String resultType) {
        ExpressionVO expressionVO = new ExpressionVO();
        expressionVO.setMethod(operation.getMethod().name());
        expressionVO.setExpressionVal(operation.getSummary());
        expressionVO.setExpressionName(operation.getNotes());
        expressionVO.setPath(path);
        expressionVO.setResultType(ExpreReturnGenericsEnum.getCode(resultType));
        expressionVO.setParamVarGenerics(ExpreParamVarGenericsEnum.getCode(operation.getPosition()));
        return expressionVO;
    }

}
