package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/13 10:37
 */
public interface WfEngineStartService {
    WfDto startProcess(WfEngineDto.StartDto startDto);
}
