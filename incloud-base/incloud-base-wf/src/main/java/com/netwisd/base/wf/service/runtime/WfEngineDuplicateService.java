package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.starter.dto.WfEngineDto;
import org.camunda.bpm.engine.task.Task;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/9 16:41
 */
public interface WfEngineDuplicateService {
    void createDuplicate(WfEngineDto.HandleDto handleDto, Task task);
}
