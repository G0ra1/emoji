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
public enum WfTaskTypeEnum {
    TODO_DRAFT_TASK("todoDraftTask"),
    TODO_TASK("todoTask"),
    DONE_TASK("doneTask");
    String type;
}
