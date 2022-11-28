package com.netwisd.base.dict.controller.receive;

import com.netwisd.base.dict.dto.DictReceiveDto;
import com.netwisd.base.dict.service.DictTreeService;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.log.annotation.SysLog;
import com.netwisd.common.log.constant.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/receive")
@Api(value = "receive", tags = "字典接受Controller")
public class DictReceiveController {

    private final DictTreeService dictTreeService;

    @SysLog(value = "接受外部推送树形字典数据", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @PostMapping("/tree")
    @ApiOperation(value = "接受树形字典数据")
    public Result receiveTree(@Validation @RequestBody List<DictReceiveDto> dictReceiveDtoList) {
        return Result.success(dictTreeService.receiveTree(dictReceiveDtoList));
    }

    @SysLog(value = "删除树形字典", operateType = CommonConstant.OPERATE_TYPE_DEL)
    @DeleteMapping("/tree")
    @ApiOperation(value = "删除字典数据")
    public Result delReceiveTree(@RequestBody List<DictReceiveDto> dictReceiveDtoList) {
        return Result.success(dictTreeService.delReceiveTree(dictReceiveDtoList));
    }

}
