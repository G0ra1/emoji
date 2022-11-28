package com.netwisd.base.wf.service.impl.runtime;

import com.netwisd.base.wf.service.runtime.WfRuntimeService;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zouliming@netwisd.com
 * @Description:
 * @date 2021/12/3 11:22
 */
@Service
@Slf4j
public class WfRuntimeServiceImpl implements WfRuntimeService {

    @Autowired
    private RuntimeService runtimeService;


    @Override
    public Object getVariableAndCheck(String executionId, String key) {
        return null;
    }

    @Override
    public ProcessInstance singleResultAndCheck(String processId) {
        try {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
            return processInstance;
        }catch (Exception e){
            e.printStackTrace();
            throw new IncloudException("获取流程实例失败！");
        }
    }

    @Override
    public Map<String, Object> getVariables(String executionId) {
        return runtimeService.getVariables(executionId);
    }
}
