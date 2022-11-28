package com.netwisd.base.socket.handler;

import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.socket.constant.SocketUserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/15 3:37 下午
 */
@Slf4j
@Component
public class WebSocketMsgHandler extends AbstractWebSocketHandler {

    /**
     * 连接被创建后
     *
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.debug("Msg连接被创建后,{}", session.getId());
        //存储关系，连接Socket时候，会把userId,token，传输过来
        //token》》》WebSocketSession
        //userId》》》token集合 （是为了,同一个账户不同地方登录，但是要做到多地推送）
        String token = getToken(session);
        String userId = getUserId(session);
        if (StrUtil.isNotEmpty(token) && StrUtil.isNotEmpty(userId)) {
            //token对应的WebSocketSession
            SocketUserManager.getInstance().addMsgTokenMapUser(token, session);
            //userId对应token
            ConcurrentHashSet<String> tokenSet = SocketUserManager.getInstance().getMsgTokenSet(userId);
            tokenSet.add(token);
            SocketUserManager.getInstance().addMsgUserIdMap(userId, tokenSet);
        }
    }

    /**
     * 后端接收
     *
     * @param session
     * @param message
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        log.info("Msg接受信息:{}", message);
        if (message instanceof TextMessage) {
            log.info("接收到TextMessage消息。。。。。。");
        } else if (message instanceof BinaryMessage) {
            log.info("接收二进制文件...");
        } else if (message instanceof PongMessage) {
            log.info("接收Pong...，什么？pong是什么？你知道ping吗？ping一pong，ping一pong，是不是很合拍？");
        } else {
            log.error("未知类型：{}", message);
        }
    }

    /**
     * connect出错
     *
     * @param session
     * @param exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("MsgConnect出错,handleTransportError:{}", exception);
        String token = getToken(session);
        SocketUserManager.getInstance().removeMsgTokenMapUser(token);
        String userId = getUserId(session);
        ConcurrentHashSet<String> tokenSet = SocketUserManager.getInstance().getMsgTokenSet(userId);
        tokenSet.add(token);
        SocketUserManager.getInstance().addMsgUserIdMap(userId, tokenSet);
    }

    /**
     * 关闭连接
     *
     * @param session
     * @param status
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.debug("MsgSocket连接被关闭,sessionId为：{}！", session.getId());
        String token = getToken(session);
        SocketUserManager.getInstance().removeMsgTokenMapUser(token);
        String userId = getUserId(session);
        ConcurrentHashSet<String> tokenSet = SocketUserManager.getInstance().getMsgTokenSet(userId);
        tokenSet.add(token);
        SocketUserManager.getInstance().addMsgUserIdMap(userId, tokenSet);
        try {
            if (session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            log.error("socket连接被关闭！{}", e);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    private String getToken(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        return ObjectUtil.isNotNull(attributes.get("token")) ? String.valueOf(attributes.get("token")) : StrUtil.EMPTY;
    }

    private String getUserId(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        return ObjectUtil.isNotNull(attributes.get("userId")) ? String.valueOf(attributes.get("userId")) : StrUtil.EMPTY;

    }
}
