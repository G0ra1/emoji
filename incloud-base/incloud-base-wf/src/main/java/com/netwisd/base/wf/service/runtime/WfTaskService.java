package com.netwisd.base.wf.service.runtime;

import org.camunda.bpm.engine.task.Task;

/**
 * @author zouliming@netwisd.com
 * @Description: camunda的taskService包装
 * @date 2021/12/3 11:21
 */
public interface WfTaskService {

    Task getAndCheck(String taskId);

    Task getAndCheckClaim(String taskId);
}
