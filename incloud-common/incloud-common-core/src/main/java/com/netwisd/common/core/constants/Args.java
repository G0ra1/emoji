package com.netwisd.common.core.constants;

import lombok.AllArgsConstructor;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/3/25 15:24
 */
@AllArgsConstructor
public enum Args {
    NULL( "所有参数值默认为空"),
    NULL_ID("空ID"),
    PAGE("只包含page参数"),
    ALL("包含所有参数");
    public String type;
}
