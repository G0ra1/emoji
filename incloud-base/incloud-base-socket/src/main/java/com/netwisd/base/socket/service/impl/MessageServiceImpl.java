package com.netwisd.base.socket.service.impl;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.json.JSONUtil;
import cn.jpush.api.push.model.PushPayload;
import com.alibaba.fastjson.JSON;
import com.netwisd.base.socket.util.JiguangPushUtil;
import com.netwisd.base.socket.constant.SocketUserManager;
import com.netwisd.base.socket.dto.PushMsgDto;
import com.netwisd.base.socket.entity.Message;
import com.netwisd.base.socket.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private JiguangPushUtil jiguangPushUtil;

    /**
     * 发送到接收人
     *
     * @param jsonStr
     */
    @Override
    public void sendUserId(String jsonStr) {
        //Message message = JSON.parseObject(jsonStr, "yyyy-MM-dd HH:mm:ss SSS", SerializerFeature.WriteDateUseDateFormat);
        Message message = JSON.parseObject(jsonStr, Message.class);
        //拿到当前用户对应的所有token
        ConcurrentHashSet<String> msgTokenSet = SocketUserManager.getInstance().getMsgTokenSet(message.getReceiverUserId());
        for (String token : msgTokenSet) {
            //拿到当前token对应的session
            WebSocketSession webSocketSession = SocketUserManager.getInstance().getMsgTokenMapUser(token);
            syncSendMsg(webSocketSession, JSONUtil.toJsonStr(message));
        }
        try {
            //极光推送
            PushMsgDto pushMsgDto = new PushMsgDto();
            pushMsgDto.setMsgTitle(message.getMsgTitle());
            pushMsgDto.setMsgContent(message.getMsgContent());
            pushMsgDto.setUnreadCount(message.getUnReadNumber());
            pushMsgDto.setMsgLeadUrl(message.getMsgH5Url());
            pushMsgDto.setMsgSource(message.getMsgSource());
            pushMsgDto.setLoginId(Long.valueOf(message.getReceiverUserId()));
            PushPayload pushPayload = JiguangPushUtil.buildPushMsgAllAlias(pushMsgDto);
            jiguangPushUtil.sendPush(pushPayload);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("推送移动设置消息失败！用户:{}。标题:{}。", message.getReceiverUserId(), message.getMsgTitle());
        }
    }

    /**
     * 发送到所有
     *
     * @param jsonStr
     */
    @Override
    public void sendAll(String jsonStr) {
        SocketUserManager.getInstance().getMsgTokenMapUserAll().forEach((k, v) -> syncSendMsg(v, jsonStr));
    }
}
