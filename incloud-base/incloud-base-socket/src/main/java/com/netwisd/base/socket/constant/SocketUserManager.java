package com.netwisd.base.socket.constant;

import cn.hutool.core.collection.ConcurrentHashSet;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/12/8 10:16 上午
 */
public class SocketUserManager {

    /**
     * 出于对 http 与 web socket 请求的差异
     * webSocketSession没有无参构造，不能序列化到redis中；
     * 分页式多实例时，用mq做消息分发到每个实例中，每个实例中的map取广播中的token做为对比；
     * 对比成功的，才去获取到WebSocketSession，然后send
     */
    private static SocketUserManager instance = new SocketUserManager();

    private SocketUserManager() {
    }

    public static SocketUserManager getInstance() {
        return instance;
    }

    //系统应用日志对应的socket连接（key用的WebSocketSession的Id）
    private Map<String, WebSocketSession> logSocketMap = new ConcurrentHashMap<>();
    //消息对应的socket连接（Key用用户登录成功对应的AccessToken）
    private Map<String, WebSocketSession> msgSocketTokenMap = new ConcurrentHashMap<>();
    //消息对应的socket连接（Key用用户登录成功对应的UserId,Value对应的是用户登录成功对应的AssessToken）
    private Map<String, ConcurrentHashSet<String>> msgSocketUserIdMap = new ConcurrentHashMap<>();

    //获取所有应用日志对应的WebSocketSession
    public Map<String, WebSocketSession> getLogMapUserAll() {
        return logSocketMap;
    }

    //增加日志对应的WebSocketSession
    public void addLogMapUser(String key, WebSocketSession value) {
        logSocketMap.put(key, value);
    }

    //删除日志对应的WebSocketSession
    public void removeLogMapUser(String key) {
        logSocketMap.remove(key);
    }

    //拿到所有的WebSocketSession
    public Map<String, WebSocketSession> getMsgTokenMapUserAll() {
        return msgSocketTokenMap;
    }

    //获取token对应的WebSocketSession
    public WebSocketSession getMsgTokenMapUser(String key) {
        return msgSocketTokenMap.get(key);
    }

    //增加token对应的WebSocketSession
    public void addMsgTokenMapUser(String key, WebSocketSession value) {
        msgSocketTokenMap.put(key, value);
    }

    //删除token对应的WebSocketSession
    public void removeMsgTokenMapUser(String key) {
        msgSocketTokenMap.remove(key);
    }

    //增加token对应的userId集合
    public void addMsgUserIdMap(String key, ConcurrentHashSet<String> value) {
        msgSocketUserIdMap.put(key, value);
    }

    //userId对应的token
    public ConcurrentHashSet<String> getMsgTokenSet(String key) {
        return msgSocketUserIdMap.getOrDefault(key, new ConcurrentHashSet<>());
    }


}
