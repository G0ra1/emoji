package com.netwisd.base.msg.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.msg.service.MessageService;
import com.netwisd.base.common.msg.vo.MessageVo;
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
@AllArgsConstructor
@Api(value = "Message", tags = "消息 Controller")
public class MessageController {

    private final MessageService messageService;

    @SysLog(value = "消息分页查询")
    @PostMapping("/msgPage")
    @ApiOperation(value = "消息分页查询")
    public Result<IPage> msgPage(@RequestBody MessageDto messageDto) {
        return Result.success(messageService.queryPageList(messageDto, false));
    }

    @SysLog(value = "全部消息")
    @PostMapping("/msgAllPage")
    @ApiOperation(value = "全部消息")
    public Result<IPage> msgAllPage(@RequestBody MessageDto messageDto) {
        return Result.success(messageService.queryPageList(messageDto, true));
    }

    @SysLog(value = "未读数量")
    @GetMapping("/unreadNumber")
    @ApiOperation(value = "未读数量")
    public Result<Integer> getUnreadNumber() {
        return Result.success(messageService.getUnreadNumber());
    }

    @SysLog(value = "消息获取详情")
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "消息获取详情")
    public Result<MessageVo> getMessage(@PathVariable(name = "id") String id) {
        return Result.success(messageService.getMessage(id));
    }

    @SysLog(value = "消息删除", operateType = CommonConstant.OPERATE_TYPE_DEL)
    @DeleteMapping("/del/{ids}")
    @ApiOperation(value = "消息删除")
    public Result delMsgTemplate(@PathVariable(name = "ids") String ids) {
        return Result.success(messageService.delMessage(ids));
    }

    @SysLog(value = "全部已读", operateType = CommonConstant.OPERATE_TYPE_EDIT)
    @GetMapping("/readAll")
    @ApiOperation(value = "全部已读")
    public Result readAll() {
        return Result.success(messageService.readAll());
    }

    @SysLog(value = "消息读取", operateType = CommonConstant.OPERATE_TYPE_EDIT)
    @PutMapping("/editRead/{ids}")
    @ApiOperation(value = "消息读取")
    public Result editRead(@PathVariable(name = "ids") String ids) {
        return Result.success(messageService.editRead(ids));
    }
}
