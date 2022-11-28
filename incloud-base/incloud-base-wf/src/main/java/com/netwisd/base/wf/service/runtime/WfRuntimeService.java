package com.netwisd.base.wf.service.runtime;

import org.camunda.bpm.engine.runtime.ProcessInstance;

import java.util.Map;

/**
 * @author zouliming@netwisd.com
 * @Description: camunda的taskService包装
 * @date 2021/12/3 11:21
 */
public interface WfRuntimeService {

    Object getVariableAndCheck(String executionId,String key);

    ProcessInstance singleResultAndCheck(String processId);

    Map<String,Object> getVariables(String executionId);
}
