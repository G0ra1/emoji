package com.netwisd.base.msg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.common.msg.vo.MessageVo;
import com.netwisd.base.msg.entity.Message;

import java.util.List;

public interface MessageApiService extends IService<Message> {

    List<MessageVo> apiSend(MessageDto messageDto);

    List<MessageVo> receiveSend(MessageDto messageDto);
}
