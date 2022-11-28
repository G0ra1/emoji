package com.netwisd.base.wf.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/5 2:15 下午
 */
@AllArgsConstructor
@Getter
public enum MethodTypeEnum {
    POST("post"),GET("get"),PUT("put"),DELETE("delete");
    String type;
}
