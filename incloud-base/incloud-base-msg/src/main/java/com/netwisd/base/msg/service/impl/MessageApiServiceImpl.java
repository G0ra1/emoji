package com.netwisd.base.msg.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Lists;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.common.msg.dto.MessageReceiverUserDto;
import com.netwisd.base.common.msg.vo.MessageVo;
import com.netwisd.base.msg.entity.Message;
import com.netwisd.base.msg.entity.MsgTemplate;
import com.netwisd.base.msg.fegin.BaseMdmClient;
import com.netwisd.base.msg.mapper.MessageMapper;
import com.netwisd.base.msg.service.MessageApiService;
import com.netwisd.base.msg.service.MsgTemplateService;
import com.netwisd.base.msg.util.RocketMqSendUtil;
import com.netwisd.common.core.exception.IncloudException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class MessageApiServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageApiService {

    private final MessageMapper messageMapper;

    private final RocketMqSendUtil rocketMqSendUtil;

    private final MsgTemplateService msgTemplateService;

    private final BaseMdmClient baseMdmClient;

    @Autowired
    private Mapper dozerMapper;

    @Override
    @Transactional
    public List<MessageVo> apiSend(MessageDto messageDto) {
        //获取发送人
        Long sendUserId = YesNo.NO.getCode() == messageDto.getIsSystemSend() ? messageDto.getSendUserId() : 0L;
        String sendUserName = YesNo.NO.getCode() == messageDto.getIsSystemSend() ? messageDto.getSendUserName() : "系统管理员";
        return sendMsg(messageDto, sendUserId, sendUserName, getReceiverUserDtoList(messageDto.getReceiverUserList()));
    }

    @Override
    public List<MessageVo> receiveSend(MessageDto messageDto) {
        //获取发送人,未获取到就是系统管理员发送
        Long sendUserId = 0L;
        String sendUserName = "系统管理员";
        if (YesNo.NO.getCode() == messageDto.getIsSystemSend()) {
            try {
                List<MdmUserVo> sendUserList = Optional.ofNullable(baseMdmClient.getUserByIdCards(messageDto.getSendUserIdcard()))
                        .orElseThrow(() -> new IncloudException("未获取到对于的发送人"));
                sendUserId = sendUserList.get(0).getId();
                sendUserName = sendUserList.get(0).getUserNameCh();
            } catch (Exception e) {
                log.error("获取发送人员信息失败:{}", e);
                throw new IncloudException("获取发送人员信息失败");
            }
        }
        return sendMsg(messageDto, sendUserId, sendUserName, getReceiverUserDtoList(messageDto.getReceiverUserList()));
    }

    private List<MessageReceiverUserDto> getReceiverUserDtoList(List<MessageReceiverUserDto> receiverUserDtoList) {
        //获取消息接受人
        Optional.ofNullable(receiverUserDtoList).orElseThrow(() -> new IncloudException("未获取到接收人"));
        String receiverIdcards = receiverUserDtoList.stream().filter(x -> StrUtil.isEmpty(x.getReceiverIdcard()) ? false : true).map(MessageReceiverUserDto::getReceiverIdcard).collect(Collectors.joining(","));
        //身份证号码为空说明是根据userId直接推送
        if (StrUtil.isEmpty(receiverIdcards)) return receiverUserDtoList;
        try {
            List<MdmUserVo> userByIdCards = baseMdmClient.getUserByIdCards(receiverIdcards);
            receiverUserDtoList = userByIdCards.stream().map(x -> {
                MessageReceiverUserDto msgReceiverUser = new MessageReceiverUserDto();
                msgReceiverUser.setReceiverUserId(x.getId());
                msgReceiverUser.setReceiverUserName(x.getUserNameCh());
                return msgReceiverUser;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("获取接收人员信息失败:{}", e);
            throw new IncloudException("获取接收人员信息失败");
        }
        return receiverUserDtoList;
    }

    private List<MessageVo> sendMsg(MessageDto messageDto, Long sendUserId, String sendUserName, List<MessageReceiverUserDto> receiverUserList) {
        Optional.ofNullable(messageDto).filter(x -> {
            // 消息来源
            if (StrUtil.isEmpty(x.getMsgSource())) {
                throw new IncloudException("消息来源为空");
            }
            // 模板Code和模板内容二选一，如果同时存在以消息内容为主
            if (StrUtil.isEmpty(x.getMsgContent()) && StrUtil.isEmpty(x.getTmpCode())) {
                throw new IncloudException("模板Code和模板内容二选一");
            }
            // 模板标题和模板Code二选一，如果同时存在以消息标题为主
            if (StrUtil.isEmpty(x.getMsgTitle()) && StrUtil.isEmpty(x.getTmpCode())) {
                throw new IncloudException("模板Code和模板标题二选一");
            }
            // 验证模板是否存在
            if (StrUtil.isNotEmpty(x.getTmpCode()) && StrUtil.isEmpty(x.getMsgContent())) {
                Optional.ofNullable(msgTemplateService.getOne(Wrappers.<MsgTemplate>lambdaQuery()
                        .eq(MsgTemplate::getTmpCode, x.getTmpCode())
                        .last("limit 1"))
                ).orElseThrow(() -> new IncloudException("未获取到模板信息"));
            }
            return true;
        }).orElseThrow(() -> new IncloudException("获取参数失败"));
        //获取消息标题、内容（内容和模板Code同时存在、内容为主,消息标题同理）
        MsgTemplate msgTemplate = msgTemplateService.getOne(Wrappers.<MsgTemplate>lambdaQuery().eq(MsgTemplate::getTmpCode, messageDto.getTmpCode()).last("limit 1"));
        String content = msgTemplateService.getMsgContent(
                StrUtil.isEmpty(messageDto.getMsgContent()) ? msgTemplate.getTmpContent() : messageDto.getMsgContent(),
                messageDto.getMsgParams());
        String title = StrUtil.isEmpty(messageDto.getMsgTitle()) ? msgTemplate.getTmpTitle() : messageDto.getMsgTitle();
        //生成消息记录
        List<MessageVo> returnResult = Lists.newArrayList();
        for (MessageReceiverUserDto receiverUserDto : receiverUserList) {
            Message message = new Message();
            message.toMessage(messageDto);
            message.setReceiverUserId(receiverUserDto.getReceiverUserId());
            message.setReceiverUserName(receiverUserDto.getReceiverUserName());
            message.setSendUserId(sendUserId);
            message.setSendUserName(sendUserName);
            message.setMsgContent(content);
            message.setMsgTitle(title);
            messageMapper.insert(message);
            returnResult.add(message.toMessageVo());
            //发送消息到Socket进行推送
            Map msgMap = dozerMapper.map(message, Map.class);
            msgMap.put("unReadNumber", count(Wrappers.<Message>lambdaQuery().eq(Message::getIsRead, YesNo.NO.getCode())
                    .eq(Message::getReceiverUserId, receiverUserDto.getReceiverUserId())));
            rocketMqSendUtil.sendMsg(msgMap);
        }
        return returnResult;
    }

}
