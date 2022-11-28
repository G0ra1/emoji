package com.netwisd.base.wf.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 对应的java转换类型
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/23 10:21 上午
 */
@AllArgsConstructor
@Getter
public enum JavaTypeEnum {
    String,
    INTEGER,
    LONG,
    DOUBLE,
    DATETIME,
    TEXT,
    FILE;
}
