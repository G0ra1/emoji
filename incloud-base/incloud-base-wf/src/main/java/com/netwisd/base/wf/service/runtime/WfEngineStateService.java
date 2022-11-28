package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.starter.dto.WfEngineDto;
/**
 * @author zouliming@netwisd.com
 * @description 流程状态操作相关的
 * @date 2021/12/6 14:47
 */

public interface WfEngineStateService {
    Boolean suspendProcess(WfEngineDto.StateDto stateDto);
    Boolean endProcess(WfEngineDto.StateDto stateDto);
    Boolean activateProcess(WfEngineDto.StateDto stateDto);
    Boolean deleteProcess(WfEngineDto.StateDto stateDto);
    Boolean deleteOnlyProcess(WfEngineDto.StateDto stateDto);
}
