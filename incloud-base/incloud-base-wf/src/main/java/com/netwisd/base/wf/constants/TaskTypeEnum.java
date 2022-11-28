package com.netwisd.base.wf.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 定义任务的类型
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/26 11:11 上午
 */
@AllArgsConstructor
@Getter
public enum TaskTypeEnum {
    STANDARD_TASK("standard"),
    MULTI_TASK("multi");
    String type;
}
