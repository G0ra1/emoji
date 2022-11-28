package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.starter.dto.WfEngineDto;

/**
 * @author zouliming@netwisd.com
 * @description 加签功能，这个单独拉出来，相当于备份，因为加签实现的并不完美，目前版本用其他功能代替了加签
 * @date 2021/12/9 10:53
 */
public interface WfEngineInsertNodeService {
    Boolean insertNode(WfEngineDto.HandleDto handleDto);
}
