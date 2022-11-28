package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.starter.dto.WfEngineDto;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/9 15:52
 */
public interface WfEngineSubmitService {
    Boolean submit(WfEngineDto.HandleDto handleDto);
}
