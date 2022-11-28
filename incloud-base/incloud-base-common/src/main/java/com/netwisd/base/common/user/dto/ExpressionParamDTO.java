package com.netwisd.base.common.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ExpressionParamDTO implements Serializable {

    private String url;

    private String expressionName;

    private List<Object> paramList;

    private List<ExpressionParamDTO> expressionParamList;

    private Map<String, Object> map;

}
