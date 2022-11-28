package com.netwisd.common.core.util;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/23 4:39 下午
 */
@Data
public class ResultError {
    @ApiModelProperty(value = "返回code")
    private Boolean code = true;


    @ApiModelProperty(value = "返回msg")
    private String msg;
}
