package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.common.core.util.Result;

import java.util.List;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/13 10:08
 */
public interface WfEngineToSbService {
    Result toSb(List<WfEngineDto.ToSbDto> toSbDtos);
}
