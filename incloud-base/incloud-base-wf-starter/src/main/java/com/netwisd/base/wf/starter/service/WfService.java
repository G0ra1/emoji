package com.netwisd.base.wf.starter.service;


import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.util.Result;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/30 12:41:03
 */
public interface WfService<T extends WfDto> {
    EngineVo startEngine(T t);
    void setWfDto(T t,EngineVo vo);
    Result<Boolean> submitEngine(T t);
    Result<Boolean> submitBefore(String taskId);
}
