package com.netwisd.base.socket.handler;

import cn.hutool.core.util.ObjectUtil;
import com.netwisd.base.socket.constant.SocketUserManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/15 3:37 下午
 */
@Slf4j
@Component
public class WebSocketLogHandler extends AbstractWebSocketHandler {

    /**
     * 连接被创建后
     *
     * @param session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        log.debug("Log连接被创建后,{}", session.getId());
        SocketUserManager.getInstance().addLogMapUser(session.getId(), session);
    }

    /**
     * 后端接收
     *
     * @param session
     * @param message
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        log.info("Log接受消息:{}", message);
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            log.info("TextMessage:{}", textMessage.getPayload());
            session.getAttributes().put("cond", ObjectUtil.isNull(textMessage.getPayload()) ? "{}" : textMessage.getPayload());
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
        log.error("LogConnect出错,handleTransportError:{}", exception);
        SocketUserManager.getInstance().removeLogMapUser(session.getId());
    }

    /**
     * 关闭连接
     *
     * @param session
     * @param status
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.debug("LogSocket连接被关闭,sessionId为：{}！", session.getId());
        SocketUserManager.getInstance().removeLogMapUser(session.getId());
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
}
