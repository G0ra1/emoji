package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.constants.BizMethodTypeEnum;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2022/2/8 16:12
 */
public interface WfEngineBizDataService {
    String invoke(WfEngineDto.BizData bizData, WfDto wfDto, BizMethodTypeEnum bizMethodTypeEnum);
}
