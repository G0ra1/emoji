package com.netwisd.base.socket.service;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;

import javax.websocket.RemoteEndpoint;

public interface IService {

    default void syncSendMsg(WebSocketSession session, String message) {
        if (ObjectUtil.isNotNull(session) && session.isOpen()) {
            try {
                StandardWebSocketSession standardWebSocketSession = (StandardWebSocketSession) session;
                RemoteEndpoint.Async asyncRemote = standardWebSocketSession.getNativeSession().getAsyncRemote();
                asyncRemote.setSendTimeout(5000);
                asyncRemote.sendText(message);
                //session.sendMessage(new TextMessage(json));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*default void syncSendMsg(WebSocketSession session, String message) {
       {
            synchronized (session) {
                try {
                    StandardWebSocketSession standardWebSocketSession = null;
                    log.info("syncSendMsg, sessionId:{}",session.getId());
                    if(session instanceof StandardWebSocketSession){
                        standardWebSocketSession = (StandardWebSocketSession)session;
                    }
                    RemoteEndpoint.Async asyncRemote = standardWebSocketSession.getNativeSession().getAsyncRemote();
                    asyncRemote.setSendTimeout(5000);
                    asyncRemote.sendText(message);
                    //session.sendMessage(new TextMessage(message));
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("session关闭，sessionId为：{}",session.getId());
                    session.close();
                }
            }
        }
    }*/

}
