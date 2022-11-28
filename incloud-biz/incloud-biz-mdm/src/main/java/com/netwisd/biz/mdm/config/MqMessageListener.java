package com.netwisd.biz.mdm.config;

import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Component
public class MqMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {

            TextMessage textMsg=(TextMessage)message;
            System.out.println("接收到一个纯文本消息。");
            System.out.println("消息内容是："+textMsg.getText());
            System.out.println("listener         "+message.toString());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
