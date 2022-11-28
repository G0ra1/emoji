package com.netwisd.base.msg.controller.api;

import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.common.msg.vo.MessageVo;
import com.netwisd.base.msg.service.MessageApiService;
import com.netwisd.base.msg.service.MessageService;
import com.netwisd.base.msg.service.impl.JGSmsServices;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Api(value = "MsgApi", tags = "消息内部调用ApiController")
public class MessageApiController {

    private final MessageApiService messageApiService;

    private final MessageService messageService;

    private final JGSmsServices jGSmsServices;

    @PostMapping("/send")
    public List<MessageVo> sendTmpMsg(@RequestBody MessageDto messageDto) {
        //模板Code或模板内容必传一个，如果没有消息标题使用模板的标题
        return messageApiService.apiSend(messageDto);
    }

    @PutMapping("/upRead/{ids}")
    public boolean upRead(@PathVariable("ids") String ids) {
        return messageService.editRead(ids);
    }

    @DeleteMapping("/del/{ids}")
    public boolean delMsg(@PathVariable("ids") String ids) {
        return messageService.delMessage(ids);
    }

    /**
     * 发送模板短信-验证码
     * @return Result
     */
    @ApiOperation(value = "发送模板短信-验证码", notes = "发送模板短信-验证码")
    @GetMapping("/sendSMSCode")
    public Result sendSMSCode(String phoneNumber) throws Exception {
        return Result.success(jGSmsServices.sendSMSCode(phoneNumber));
    }

    /**
     * 短信验证-验证码
     * @return Result
     */
    @ApiOperation(value = "短信验证-验证码", notes = "短信验证-验证码")
    @GetMapping("/verificationCode")
    public Result verificationCode(String phoneNumber, String code) throws Exception {
        return Result.success(jGSmsServices.verificationCode(phoneNumber,code));
    }

}
