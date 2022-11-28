package com.netwisd.base.msg.controller.receive;

import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.msg.service.MessageApiService;
import com.netwisd.base.msg.service.MessageService;
import com.netwisd.common.core.util.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/receive")
public class MessageReceiveController {

    private final MessageService messageService;

    private final MessageApiService messageApiService;

    @PostMapping
    public Result send(@RequestBody MessageDto messageDto) {
        return Result.success(messageApiService.receiveSend(messageDto));
    }

    @PutMapping("/{ids}")
    public Result upRead(@PathVariable("ids") String ids) {
        return Result.success(messageService.editRead(ids));
    }

    @DeleteMapping("/{ids}")
    public Result delMsg(@PathVariable("ids") String ids) {
        return Result.success(messageService.delMessage(ids));
    }

}
