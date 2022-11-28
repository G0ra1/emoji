package com.netwisd.base.wf.dto;

import com.netwisd.common.core.anntation.Valid;
import com.netwisd.common.core.data.IValidation;

import java.util.List;

/**
 * @Description: 自定义分级请求参数
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/30 11:02 上午
 */
public class UrmParamsDto implements IValidation {
    @Valid
    private String methodName;
    private List<Object> params;
}
