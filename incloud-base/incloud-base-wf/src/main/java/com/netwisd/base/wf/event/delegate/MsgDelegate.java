package com.netwisd.base.wf.event.delegate;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.netwisd.base.common.constants.MessageTmpEnum;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.common.msg.dto.MessageReceiverUserDto;
import com.netwisd.base.common.vo.wf.WfDoneTaskVo;
import com.netwisd.base.common.vo.wf.WfTodoTaskVo;
import com.netwisd.base.wf.constants.MyTaskTypeEnum;
import com.netwisd.base.wf.constants.WfMsgEnum;
import com.netwisd.base.wf.constants.WfMsgSwitchEnum;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.feign.MsgClient;
import com.netwisd.base.wf.service.runtime.WfDoneTaskService;
import com.netwisd.base.wf.service.runtime.WfProcessLogService;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.base.wf.service.runtime.WfTodoTaskService;
import com.netwisd.base.wf.vo.WfProcessLogVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Description: 消息事件
 * @Author: XHL
 * @Date: 2022/4/26 16:00
 */
@Slf4j
@Component
@Setter
public class MsgDelegate implements TaskListener {

    private Expression msgType;
    //非camunda正常调用时，反射赋值给这个属性，主要原因是我们自己给Expression赋值非常复杂，这个类的实现内容非常多；
    private String msgTypeStr;

    private Expression sendType;
    //非camunda正常调用时，反射赋值给这个属性，主要原因是我们自己给Expression赋值非常复杂，这个类的实现内容非常多；
    private String sendTypeStr;

    private Expression msgSwitch;
    //非camunda正常调用时，反射赋值给这个属性，主要原因是我们自己给Expression赋值非常复杂，这个类的实现内容非常多；
    private String msgSwitchStr;

    @Autowired
    WfDoneTaskService wfDoneTaskService;

    private final String url = "/html/wf/run-skin.html?Action=";

    @Autowired
    MsgClient msgClient;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> incloudCache;

    @SneakyThrows
    @Override
    public void notify(DelegateTask delegateTask) {
        MsgDelegate.ExpressionAndMsgType expressionAndMsgType = check(delegateTask);
        log.debug("=================DEBUG======================");
        log.debug(expressionAndMsgType.getMsgTypeValue());
        log.debug(expressionAndMsgType.getMsgSendTypeValue());
        log.debug(expressionAndMsgType.getMsgSwitchValue());
        //todo如果是申请人节点 第一次不发送
        if(delegateTask.getTaskDefinitionKey().equals("firstNode")) {
            List<WfProcessLogVo> logs = wfProcessLogService.getLogsByInsIdAndNodeId(delegateTask.getProcessInstanceId(),"firstNode");
            if(CollectionUtil.isNotEmpty(logs)) {
                if(logs.size() <= 1) {
                    return;
                }
            }
        }
        //判断是提醒当前办理人并且开启状态
        if(WfMsgEnum.todo.code.equals(expressionAndMsgType.getMsgTypeValue())&&WfMsgSwitchEnum.on.code.equals(expressionAndMsgType.getMsgSwitchValue())) {
            List<WfTodoTaskVo> wfTodoTaskVoList = wfTodoTaskService.getTodoList(delegateTask.getProcessInstanceId());
            // 发送消息
            if(CollectionUtil.isNotEmpty(wfTodoTaskVoList)){
                sendMsgForTodo(wfTodoTaskVoList);
            }else {
                throw new IncloudException("流程实例信息为空！");
            }
        }
        //判断是提醒申请人并且开启状态
        if(WfMsgEnum.apply.code.equals(expressionAndMsgType.getMsgTypeValue())&&WfMsgSwitchEnum.on.code.equals(expressionAndMsgType.getMsgSwitchValue())) {
            //获取到流程实例信息
            WfProcess wfProcess = wfProcessService.get(delegateTask.getProcessInstanceId());
            // 发送消息
            if(null != wfProcess){
                sendMsgForApply(wfProcess);
            }else {
                throw new IncloudException("流程实例信息为空！");
            }
        }
    }

    MsgDelegate.ExpressionAndMsgType check(DelegateTask delegateTask){
        String msgSwitchExp = null;
        try {
            msgSwitchExp = msgSwitch.getValue(delegateTask).toString();
        }catch (Exception e){
            log.error("msgType 获取值失败！");
            if(StrUtil.isEmpty(msgSwitchStr)){
                throw new IncloudException("msgSwitch：事件参数为空或不匹配！");
            }
        }
        if(StrUtil.isEmpty(msgSwitchExp)){
            if(StrUtil.isEmpty(msgSwitchStr)){
                throw new IncloudException("msgSwitch：事件参数为空或不匹配！");
            }
            msgSwitchExp = msgSwitchStr;
        }

        String msgTypeExp = null;
        try {
            msgTypeExp = msgType.getValue(delegateTask).toString();
        }catch (Exception e){
            log.error("msgType 获取值失败！");
            if(StrUtil.isEmpty(msgTypeStr)){
                throw new IncloudException("msgType：事件参数为空或不匹配！");
            }
        }
        if(StrUtil.isEmpty(msgTypeExp)){
            if(StrUtil.isEmpty(msgTypeStr)){
                throw new IncloudException("msgType：事件参数为空或不匹配！");
            }
            msgTypeExp = msgTypeStr;
        }
        String sendTypeExp = null;
        try {
            sendTypeExp = sendType.getValue(delegateTask).toString();
        }catch (Exception e){
            log.error("msgType 获取值失败！");
            if(StrUtil.isEmpty(sendTypeStr)){
                throw new IncloudException("msgSendType：事件参数为空或不匹配！");
            }
        }
        if(StrUtil.isEmpty(sendTypeExp)){
            if(StrUtil.isEmpty(sendTypeStr)){
                throw new IncloudException("msgSendType：事件参数为空或不匹配！");
            }
            sendTypeExp = sendTypeStr;
        }
        log.info("msgType值为:-> {}, msgSendType值为:-> {},msgSwitch值为:-> {}",msgTypeExp,sendTypeExp,msgSwitchExp);
        return new MsgDelegate.ExpressionAndMsgType(msgTypeExp,sendTypeExp,msgSwitchExp);
    }

    @Data
    @AllArgsConstructor
    class ExpressionAndMsgType{
        private String msgTypeValue;
        private String msgSendTypeValue;
        private String msgSwitchValue;
    }

    /**
     * 发送当前办理人消息
     * @param wfTodoTaskVoList
     */
    public void sendMsgForTodo(List<WfTodoTaskVo> wfTodoTaskVoList) {
        try {
            for (WfTodoTaskVo wfTodoTaskVo : wfTodoTaskVoList) {
                //发送消息
                MessageDto messageDto = new MessageDto();
                messageDto.setTmpCode(MessageTmpEnum.TODOTASK.getCode());
                messageDto.setIsSystemSend(YesNo.YES.code);
                messageDto.setMsgSource("WF");
                messageDto.setMsgPcUrl(url + MyTaskTypeEnum.TODO.code + "&id=" + wfTodoTaskVo.id);
                messageDto.setMsgH5Url(url + MyTaskTypeEnum.TODO.code + "&id=" + wfTodoTaskVo.id);
                List<MessageReceiverUserDto> messageReceiverUserDtos = new ArrayList<>();
                List userNames = Arrays.asList(wfTodoTaskVo.getCandidates().split(","));
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
            }
        }catch (Exception e) {
            log.error("待办发送消息失败！" + e.getMessage());
        }
    }

    /**
     * 发送当前申请人消息
     * @param wfProcess
     */
    public void sendMsgForApply(WfProcess wfProcess) {
        try {
            //发送消息
            MessageDto messageDto = new MessageDto();
            messageDto.setTmpCode(MessageTmpEnum.APPLYTASK.getCode());
            messageDto.setIsSystemSend(YesNo.YES.code);
            messageDto.setMsgSource("WF");
            List<MessageReceiverUserDto> messageReceiverUserDtos = new ArrayList<>();
            Map params =new HashMap<>();
            params.put("title",wfProcess.getReason());
            params.put("node",wfProcess.getCurrentActivityName());
            messageDto.setMsgParams(JSONObject.toJSONString(params));
            //根据流程实例信息 查询已办信息
            WfDoneTaskVo firstOneDone = wfDoneTaskService.getFirstOne(wfProcess.getCamundaProcinsId());
            messageDto.setMsgPcUrl(url + MyTaskTypeEnum.DONE.code + "&id=" + firstOneDone.id);
            messageDto.setMsgH5Url(url + MyTaskTypeEnum.DONE.code + "&id=" + firstOneDone.id);
            Long userName = wfProcess.getStarterId();
            if(null != userName) {
                MdmUserVo user = incloudCache.get(userName);
                if(null != user) {
                    MessageReceiverUserDto messageReceiverUserDto = new MessageReceiverUserDto();
                    messageReceiverUserDto.setReceiverUserId(user.getId());
                    messageReceiverUserDto.setReceiverUserName(user.getUserNameCh());
                    messageReceiverUserDto.setReceiverIdcard(user.getIdCard());
                    messageReceiverUserDtos.add(messageReceiverUserDto);
                    messageDto.setReceiverUserList(messageReceiverUserDtos);
                }
                msgClient.sendTmpMsg(messageDto);
            }
        }catch (Exception e) {
            log.error("申请人发送消息失败！" + e.getMessage());
        }
    }

}