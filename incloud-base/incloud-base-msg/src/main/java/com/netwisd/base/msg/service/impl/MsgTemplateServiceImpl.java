package com.netwisd.base.msg.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.common.msg.dto.MessageReceiverUserDto;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.msg.dto.MsgTemplateDto;
import com.netwisd.base.msg.entity.Message;
import com.netwisd.base.msg.entity.MsgTemplate;
import com.netwisd.base.msg.mapper.MessageMapper;
import com.netwisd.base.msg.mapper.MsgTemplateMapper;
import com.netwisd.base.msg.service.MsgTemplateService;
import com.netwisd.base.msg.util.RocketMqSendUtil;
import com.netwisd.base.msg.vo.MsgTemplateVo;
import com.netwisd.common.core.exception.IncloudException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MsgTemplateServiceImpl extends ServiceImpl<MsgTemplateMapper, MsgTemplate> implements MsgTemplateService {

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private Configuration freemarkerConfig;

    @Autowired
    private RocketMqSendUtil rocketMqSendUtil;

    /**
     * 分页查询
     *
     * @param msgTemplateDto
     * @return
     */
    @Override
    public IPage queryPageList(MsgTemplateDto msgTemplateDto) {
        return page(msgTemplateDto.getPage(), Wrappers.<MsgTemplate>lambdaQuery()
                .like(StrUtil.isNotEmpty(msgTemplateDto.getTmpCode()), MsgTemplate::getTmpCode, msgTemplateDto.getTmpCode())
                .like(StrUtil.isNotEmpty(msgTemplateDto.getTmpName()), MsgTemplate::getTmpName, msgTemplateDto.getTmpName())
                .orderByDesc(MsgTemplate::getCreateTime));
    }

    /**
     * 新增
     *
     * @param msgTemplateDto
     * @return
     */
    @Override
    public boolean addMsgTemplate(MsgTemplateDto msgTemplateDto) {
        Optional.of(count(Wrappers.<MsgTemplate>lambdaQuery().eq(MsgTemplate::getTmpCode, msgTemplateDto.getTmpCode()))).filter(x -> {
            if (x > 0) {
                throw new IncloudException("模板Code已经存在");
            }
            return true;
        });
        MsgTemplate msgTemplate = new MsgTemplate();
        msgTemplate.toMsgTemplate(msgTemplateDto);
        return save(msgTemplate);
    }

    /**
     * 修改
     *
     * @param msgTemplateDto
     * @return
     */
    @Override
    public boolean editMsgTemplate(MsgTemplateDto msgTemplateDto) {
        Optional.of(count(Wrappers.<MsgTemplate>lambdaQuery().ne(MsgTemplate::getId, msgTemplateDto.getId()).eq(MsgTemplate::getTmpCode, msgTemplateDto.getTmpCode())))
                .filter(x -> {
                    if (x > 0) {
                        throw new IncloudException("模板Code已经存在");
                    }
                    return true;
                });
        MsgTemplate msgTemplate = Optional.ofNullable(getById(msgTemplateDto.getId())).orElseThrow(() -> new IncloudException("未找到要修改的模板信息"));
        msgTemplate.toMsgTemplate(msgTemplateDto);
        return updateById(msgTemplate);
    }

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    @Override
    public MsgTemplateVo getMsgTemplate(String id) {
        MsgTemplate msgTemplate = getById(id);
        return ObjectUtil.isNull(msgTemplate) ? null : msgTemplate.toMsgTemplateVo();
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    public boolean delMsgTemplate(String ids) {
        return removeByIds(Arrays.stream(ids.split(",")).collect(Collectors.toList()));
    }

    /**
     * 消息模板发送
     *
     * @param messageDto
     */
    @Override
    public void sendTmpMsg(MessageDto messageDto) {
        MsgTemplate msgTemplate = Optional.ofNullable(
                        getOne(Wrappers.<MsgTemplate>lambdaQuery().eq(MsgTemplate::getTmpCode, messageDto.getTmpCode()).last("limit 1")))
                .orElseThrow(() -> new IncloudException("未获取到模板信息"));
        List<MessageReceiverUserDto> receiverUserList = Optional.ofNullable(messageDto.getReceiverUserList()).orElseThrow(() -> new IncloudException("未获取到接收人"));
        //多个接收人，接受消息，不加事务是因为要发MQ，避免导致第一个执行成功发送了MQ，第二个接收人执行失败，第一条消息回滚，但是Socket推送了
        receiverUserList.forEach(receiverUser -> {
            //生成消息记录
            Message message = new Message();
            message.toMessage(messageDto);
            message.setMsgContent(getMsgContent(msgTemplate.getTmpContent(), messageDto.getMsgParams()));
            message.setMsgTitle(StrUtil.isEmpty(messageDto.getMsgTitle()) ? msgTemplate.getTmpTitle() : messageDto.getMsgTitle());
            message.setReceiverUserId(receiverUser.getReceiverUserId());
            message.setReceiverUserName(receiverUser.getReceiverUserName());
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            if (ObjectUtil.isNotNull(loginAppUser) && StrUtil.isNotEmpty(loginAppUser.getUserName())) {
                message.setSendUserId(loginAppUser.getId());
                message.setSendUserName(loginAppUser.getUserNameCh());
            }
            messageMapper.insert(message);
            //发送消息到Socket进行推送
            Map msgMap = dozerMapper.map(message, Map.class);
            msgMap.put("unReadNumber", messageMapper.selectCount(Wrappers.<Message>lambdaQuery().eq(Message::getIsRead, YesNo.NO.getCode())
                    .eq(Message::getReceiverUserId, receiverUser.getReceiverUserId())));
            rocketMqSendUtil.sendMsg(msgMap);
        });
    }

    @Override
    public String getMsgContent(String tmpContent, String tmpParams) {
        //获取模板信息
        StringWriter msgContentWriter = new StringWriter();
        try {
            Map map = JSONUtil.toBean(tmpParams, Map.class);
            Template template = new Template("MessageTemplate", tmpContent, freemarkerConfig);
            template.process(map, msgContentWriter);
        } catch (JSONException e) {
            throw new IncloudException("解析消息参数Json错误");
        } catch (TemplateException | IOException e) {
            throw new IncloudException("模板设置错误");
        }
        return msgContentWriter.toString();
    }


}
