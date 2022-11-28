package com.netwisd.base.msg.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.msg.entity.Message;
import com.netwisd.base.msg.mapper.MessageMapper;
import com.netwisd.base.msg.service.MessageService;
import com.netwisd.base.common.msg.vo.MessageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    /**
     * 分页查询
     *
     * @param messageDto
     * @return
     */
    @Override
    public IPage queryPageList(MessageDto messageDto, boolean isAll) {
        return page(messageDto.getPage(), Wrappers.<Message>lambdaQuery()
                .like(StrUtil.isNotEmpty(messageDto.getMsgTitle()), Message::getMsgTitle, messageDto.getMsgTitle())
                .like(StrUtil.isNotEmpty(messageDto.getMsgContent()), Message::getMsgContent, messageDto.getMsgContent())
                .eq(ObjectUtil.isNotNull(messageDto.getIsRead()), Message::getIsRead, messageDto.getIsRead())
                .eq(BooleanUtil.isFalse(isAll), Message::getReceiverUserId, ObjectUtil.isNull(AppUserUtil.getLoginAppUser()) ? 0 : AppUserUtil.getLoginAppUser().getId())
                .orderByDesc(Message::getCreateTime));
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public MessageVo getMessage(String id) {
        Message message = getById(id);
        //点击详情把此消息改为已读
        editRead(id);
        return ObjectUtil.isNull(message) ? null : message.toMessageVo();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    public boolean delMessage(String ids) {
        return removeByIds(Arrays.stream(ids.split(",")).collect(Collectors.toList()));
    }

    /**
     * 一键阅读
     *
     * @return
     */
    @Override
    public boolean readAll() {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (ObjectUtil.isNotNull(loginAppUser) && StrUtil.isNotEmpty(loginAppUser.getUserName())) {
            Message message = new Message();
            message.setIsRead(YesNo.YES.code);
            message.setReadTime(LocalDateTime.now());
            return update(message, Wrappers.<Message>lambdaQuery().eq(Message::getReceiverUserId, loginAppUser.getId())
                    .eq(Message::getIsRead, YesNo.NO.code));
        }
        return false;
    }

    /**
     * 更新阅读状态
     *
     * @param ids
     * @return
     */
    @Override
    public boolean editRead(String ids) {
        //更改已读增加接收人条件是因为有消息管理查询的是全部消息，点击查看详情的人不一定是当前消息对应的人
        Message message = new Message();
        message.setIsRead(YesNo.YES.code);
        message.setReadTime(LocalDateTime.now());
        return update(message, Wrappers.<Message>lambdaQuery().in(Message::getId, Arrays.stream(ids.split(",")).collect(Collectors.toList()))
                .eq(Message::getIsRead, YesNo.NO.code)
                .eq(Message::getReceiverUserId, ObjectUtil.isNull(AppUserUtil.getLoginAppUser()) ? 0 : AppUserUtil.getLoginAppUser().getId())
        );
    }

    @Override
    public int getUnreadNumber() {
        Long userId = ObjectUtil.isNull(AppUserUtil.getLoginAppUser()) ? 0 : AppUserUtil.getLoginAppUser().getId();
        return count(Wrappers.<Message>lambdaQuery().eq(Message::getIsRead, YesNo.NO.getCode())
                .eq(Message::getReceiverUserId, userId));
    }
}
