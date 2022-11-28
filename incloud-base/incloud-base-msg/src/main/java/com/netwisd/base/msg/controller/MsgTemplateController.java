package com.netwisd.base.msg.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.msg.dto.MsgTemplateDto;
import com.netwisd.base.msg.service.MsgTemplateService;
import com.netwisd.base.msg.vo.MsgTemplateVo;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.anntation.IncludeAnntation;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.constants.VarConstants;
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
@RequestMapping("/tmp")
@AllArgsConstructor
@Api(value = "MsgTemplate", tags = "消息模板Controller")
public class MsgTemplateController {

    private final MsgTemplateService templateService;

    @SysLog(value = "消息模板分页查询")
    @PostMapping("/page")
    @ApiOperation(value = "消息模板分页查询")
    public Result<IPage> page(@RequestBody MsgTemplateDto msgTemplateDto) {
        return Result.success(templateService.queryPageList(msgTemplateDto));
    }

    @SysLog(value = "消息模板新增", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @PostMapping
    @ApiOperation(value = "消息模板新增")
    public Result addMsgTemplate(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL), include = @IncludeAnntation(vars = {"tmpCode", "tmpTitle", "tmpName", "tmpContent"}))
                                 @RequestBody MsgTemplateDto msgTemplateDto) {
        return Result.success(templateService.addMsgTemplate(msgTemplateDto));
    }

    @SysLog(value = "消息模板编辑", operateType = CommonConstant.OPERATE_TYPE_EDIT)
    @PutMapping
    @ApiOperation(value = "消息模板编辑")
    public Result editMsgTemplate(@Validation(exclude = @ExcludeAnntation(VarConstants.ALL), include = @IncludeAnntation(vars = {"id"}))
                                  @RequestBody MsgTemplateDto msgTemplateDto) {
        return Result.success(templateService.editMsgTemplate(msgTemplateDto));
    }

    @SysLog(value = "消息模板获取详情")
    @GetMapping("/{id}")
    @ApiOperation(value = "消息模板获取详情")
    public Result<MsgTemplateVo> getMsgTemplate(@PathVariable(name = "id") String id) {
        return Result.success(templateService.getMsgTemplate(id));
    }

    @SysLog(value = "消息模板删除", operateType = CommonConstant.OPERATE_TYPE_DEL)
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "消息模板删除")
    public Result delMsgTemplate(@PathVariable(name = "ids") String ids) {
        return Result.success(templateService.delMsgTemplate(ids));
    }

    @SysLog(value = "模板消息发送", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @PostMapping("/send")
    @ApiOperation(value = "模板消息发送")
    public Result sendTmpMsg(@RequestBody MessageDto messageDto) {
        templateService.sendTmpMsg(messageDto);
        return Result.success(Boolean.TRUE);
    }
}
