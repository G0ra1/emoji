package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.vo.WfEventRuntimeVo;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.task.Task;

import java.util.Map;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/9 16:51
 */
public interface WfEngineCommonService {
    void dueDateHandle(String processDefinitionId, Map<String, Object> variables, String camundaNodeDefId);

    void wfDoneTaskHandle(Task task, String targetActivityId);

    String getInstanceIdForActivity(ActivityInstance activityInstance, String activityId);

    /**
     * 调用工作流的自定义事件
     * @param task
     * @param wfEventRuntimeVo
     * @param eventBindType
     */
    void eventListenerInvoke(Task task, WfEventRuntimeVo wfEventRuntimeVo, String eventBindType);
}
