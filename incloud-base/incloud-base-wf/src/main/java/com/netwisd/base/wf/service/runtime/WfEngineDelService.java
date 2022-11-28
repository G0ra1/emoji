package com.netwisd.base.wf.service.runtime;

import org.camunda.bpm.engine.task.Task;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/9 14:52
 */
public interface WfEngineDelService {
    /**
     * 根据流程ID删除流程待办
     * @param task
     */
    void delWfTodoTask(Task task);

    /**
     * 根据流程ID删除已办
     * @param tasId
     */
    void delWfDoneTask(String tasId);

    /**
     * 根据流程实例ID，任务ID删除传阅
     * @param processInstanceId
     * @param camundaTaskId
     */
    void delDuplicateByProInsAndTaskId(String processInstanceId,String camundaTaskId);

    /**
     * 根据流程实例ID，任务ID删除待办
     * @param processInstanceId
     * @param taskId
     */
    void delWfTodoTaskByProInsAdnTaskId(String processInstanceId,String taskId);

    /**
     * 根据流程实例ID，节点定义ID删除日志
     * @param processInstanceId
     * @param camundaTaskId
     */
    void delWfProcessLogByProInsAdnTaskId(String processInstanceId,String camundaTaskId,Integer logType);
}
