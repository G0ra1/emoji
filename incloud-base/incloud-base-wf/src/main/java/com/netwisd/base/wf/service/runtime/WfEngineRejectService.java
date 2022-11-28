package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.constants.WfProcessLogEnum;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import org.camunda.bpm.engine.task.Task;

import java.util.Set;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/13 10:13
 */
public interface WfEngineRejectService {
    Boolean reject(WfEngineDto.HandleDto handleDto);
    Set<String> getRejectVariables(String taskId);
    void rejectTargetActivity(WfEngineDto.HandleDto handleDto, Task task, WfProcessLogEnum wfProcessLogEnum);
}
