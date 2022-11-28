package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.collection.CollectionUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.constants.MessageTmpEnum;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.common.msg.dto.MessageReceiverUserDto;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.feign.MsgClient;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author XHL
 * @description
 * @date 2022/03/08 16:41
 */
@Service
@Slf4j
public class WfEngineUrgeServiceImpl implements WfEngineUrgeService {

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> incloudCache;

    @Autowired
    Mapper dozerMapper;

    @Autowired
    MsgClient msgClient;

    @Autowired
    WfProcessService wfProcessService;

    @Override
    public boolean handleUrge(String camundaProcInsId) {
        //验证流程
        WfProcess wfProcess = wfProcessService.get(camundaProcInsId);
        if(wfProcess.getState() != WfProcessEnum.RUNNING.getType()) {
            throw new IncloudException("该流程状态为:" + WfProcessEnum.getMessage(wfProcess.getState()) +".不可催办。");
        }
        if(StringUtils.isBlank(wfProcess.getCurrentActivityAssignee())) {
            throw new IncloudException("该流程现在处于未签收状态！");
        }
        sendUrgeMsg(wfProcess);
        return true;
    }

    public void sendUrgeMsg(WfProcess wfProcess) {
        try {
            //发送消息
            MessageDto messageDto = new MessageDto();
            messageDto.setTmpCode(MessageTmpEnum.URGE.getCode());
            messageDto.setIsSystemSend(YesNo.YES.code);
            messageDto.setMsgSource("WF");
            List<MessageReceiverUserDto> messageReceiverUserDtos = new ArrayList<>();
            List userNames = Arrays.asList(wfProcess.getCurrentActivityAssignee().split(","));
            if(CollectionUtil.isNotEmpty(userNames)) {
                List<MdmUserVo> users = incloudCache.gets(userNames);
                if(CollectionUtil.isNotEmpty(users)) {
                    users.forEach(d->{
                        MessageReceiverUserDto messageReceiverUserDto = new MessageReceiverUserDto();
                        messageReceiverUserDto.setReceiverUserId(d.getId());
                        messageReceiverUserDto.setReceiverUserName(d.getUserNameCh());
                        messageReceiverUserDto.setReceiverIdcard(d.getIdCard());
                        messageReceiverUserDtos.add(messageReceiverUserDto);
                    });
                    messageDto.setReceiverUserList(messageReceiverUserDtos);
                }
                msgClient.sendTmpMsg(messageDto);
            }
        }catch (Exception e) {
            log.debug("集中待办发送消息失败！" + e.getMessage());
        }
    }
}
