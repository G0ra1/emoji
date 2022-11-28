package com.netwisd.base.msg.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.netwisd.base.msg.dto.ProducerDto;
import com.netwisd.base.msg.service.ProducerService;
import com.netwisd.base.msg.vo.ProducerVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.log.annotation.SysLog;
import com.netwisd.common.log.constant.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/producer")
@AllArgsConstructor
@Api(value = "Producer", tags = "Producer Controller")
public class MqProducerController {

    private ProducerService producerService;

    @SysLog(value = "分页查询")
    @PostMapping("/page")
    @ApiOperation(value = "Producer分页查询")
    public Result<IPage> page(@RequestBody ProducerDto producerDto) {
        return Result.success(producerService.queryPageList(producerDto));
    }

    @SysLog(value = "Producer新增", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @PostMapping("")
    @ApiOperation(value = "Producer新增")
    public Result saveProducer(@RequestBody ProducerDto producerDto) {
        return Result.success(producerService.saveProducer(producerDto));
    }

    @SysLog(value = "Producer获取详情")
    @GetMapping("/detailId/{id}")
    @ApiOperation(value = "Producer获取详情")
    public Result<ProducerVo> getProduer(@PathVariable(name = "id") String id) {
        return Result.success(producerService.getProduer(id));
    }

    @SysLog(value = "Producer修改状态", operateType = CommonConstant.OPERATE_TYPE_EDIT)
    @ApiOperation(value = "Producer修改状态")
    @GetMapping("/changeStatus")
    public Result changeStatus(@RequestParam("mqId") String mqId) {
        return Result.success(producerService.changeStatus(mqId));
    }

    @SysLog(value = "Producer删除", operateType = CommonConstant.OPERATE_TYPE_DEL)
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除")
    public Result delDictType(@PathVariable(name = "id") String id) {
        return Result.success(producerService.removeProducer(id));
    }

    @SysLog(value = "Producer编辑", operateType = CommonConstant.OPERATE_TYPE_EDIT)
    @PutMapping("")
    @ApiOperation(value = "Producer编辑")
    public Result editSysJob(@RequestBody ProducerDto producerDto) {
        return Result.success(producerService.upProducer(producerDto));
    }

    @SysLog(value = "Producer发送", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @GetMapping("/send/{id}")
    @ApiOperation(value = "Producer发送")
    public Result send(@PathVariable("id") String id) {
        return Result.success(producerService.send(id));
    }

}
