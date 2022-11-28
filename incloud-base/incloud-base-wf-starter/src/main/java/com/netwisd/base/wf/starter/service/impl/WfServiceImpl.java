package com.netwisd.base.wf.starter.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.starter.constants.SubmitTypeEnum;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.feign.WfClient;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.util.FormatWfEngine;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 一些工作流相关的接口调用
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/30 12:42:00
 */
@Slf4j
public class WfServiceImpl implements WfService {
    @Autowired
    WfClient wfClient;

    @Autowired
    private Mapper dozerMapper;

    public WfServiceImpl(){
        log.info("创建化WfServiceImpl...");
    }

    @Override
    @SneakyThrows
    public EngineVo startEngine(WfDto wfDto){
        /*WfEngineDto.StartDto startDto = wfDto.getStartDto();
        startDto.setApplyTime(null);
        log.debug("启动工作流参数：{}",startDto);
        Result<EngineVo> engineVoResult = wfClient.startProcess(startDto);
        log.debug("工作流启动返回参数：{}",engineVoResult.toString());
        EngineVo engineVo = engineVoResult.getData();
        return engineVo;*/
        return null;
    }

    @Override
    @SneakyThrows
    public void setWfDto(WfDto wfDto,EngineVo vo){
        dozerMapper.map(vo,wfDto);
        if(null != wfDto) {
            vo.setBizData(FormatWfEngine.formatWfEngine(wfDto));
        }
        //.....
    }

    @Override
    @SneakyThrows
    public Result<Boolean> submitEngine(WfDto wfDto) {
        /*WfEngineDto.HandleDto handleDto = wfDto.getHandleDto();
        handleDto.setReason(wfDto.getReason());
        Integer submitType = handleDto.getSubmitType();
        if(ObjectUtil.isEmpty(submitType)){
            throw new IncloudException("submitType参数不能为空！");
        }
        log.info("--------wfService/submitType------：{}",submitType.intValue());
        Result<Boolean> submit =  submitType.intValue() == SubmitTypeEnum.COUNTERSIGN.getType().intValue() ? wfClient.insertNode(handleDto) : wfClient.submit(handleDto);
        return submit;*/
        return null;
    }

    @Override
    public Result<Boolean> submitBefore(String taskId) {
        return wfClient.submitBefore(taskId);
    }
}
