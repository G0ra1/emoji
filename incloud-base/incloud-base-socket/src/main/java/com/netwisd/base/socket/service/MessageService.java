package com.netwisd.base.socket.service;

public interface MessageService extends IService {

    /**
     * 发送到接收人
     *
     * @param jsonStr
     */
    void sendUserId(String jsonStr);

    /**
     * 发送到所有
     *
     * @param jsonStr
     */
    void sendAll(String jsonStr);
}
