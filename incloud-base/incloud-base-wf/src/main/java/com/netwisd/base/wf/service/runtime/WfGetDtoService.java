package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.starter.dto.WfDto;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2022/2/24 11:11
 */
public interface WfGetDtoService {
    WfDto returnData(String camundaTaskId, String camundaProcinsId);
}
