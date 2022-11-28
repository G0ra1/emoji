package com.netwisd.base.wf.starter.feign;

import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/21 11:12 上午
 */
@FeignClient(value = "incloud-base-wf")
public interface WfClient {

    @ApiOperation(value = "启动流程", notes = "启动流程")
    @RequestMapping(value = "/wfEngine/startProcess", method = RequestMethod.POST)
    @SneakyThrows
    Result<EngineVo> startProcess(@RequestBody WfEngineDto.StartDto startDto);


    /*@ApiOperation(value = "保存业务数据", notes = "保存业务数据")
    @RequestMapping(value = "/wfEngine/save", method = RequestMethod.POST)
    @SneakyThrows
    Result<Boolean> save(@RequestBody WfEngineDto wfEngineDto);*/

    @ApiOperation(value = "工作流提交操作", notes = "工作流提交操作")
    @RequestMapping(value = "/wfEngine/submit", method = RequestMethod.POST)
    @SneakyThrows
    Result<Boolean> submit(@RequestBody WfEngineDto.HandleDto handleDto);

    @ApiOperation(value = "流程加签",  notes = "流程加签")
    @PutMapping("/wfEngine/countersign")
    @SneakyThrows
    Result<Boolean> insertNode(@Validation @RequestBody WfEngineDto.HandleDto handleDto);

    @ApiOperation(value = "工作流提交前事件", notes = "工作流提交前事件")
    @RequestMapping(value = "/wfEngine/submitBefore/{taskId}", method = RequestMethod.GET)
    @SneakyThrows
    Result<Boolean> submitBefore(@Validation @PathVariable String taskId);

}
