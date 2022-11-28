package com.netwisd.biz.mdm.config;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.annotation.Resource;
import javax.jms.Destination;

@Configuration
@EnableJms
public class ActiveMQConfig {
    @Value("${spring.activemq.user}")
    private String usrName;

    @Value("${spring.activemq.password}")
    private  String password;

    @Value("${spring.activemq.broker-url}")
    private  String brokerUrl;

    @Resource
    private MqMessageListener mqMessageListener;

    @Bean
    public PooledConnectionFactory pooledConnectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(usrName);
        connectionFactory.setPassword(password);
        PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(connectionFactory);
        pooledConnectionFactory.setMaxConnections(1);
        pooledConnectionFactory.setExpiryTimeout(9000);
        return pooledConnectionFactory;
    }

    /**
     * jms异步消息监听管理类
     * @return
     */
//    @Bean
//    public DefaultMessageListenerContainer listenerContainer(){
//        String destinationName = new StringBuilder("send").append("*").toString();
//        DefaultMessageListenerContainer defaultMessageListenerContainer = getListenerContainer(destinationName);
//        return defaultMessageListenerContainer;
//    }

    /**
     * 根据队列名创建异步消息监听管理类
     * @param destinationName
     * @return
     */
    private DefaultMessageListenerContainer getListenerContainer(String destinationName){
        DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(pooledConnectionFactory());
        Destination sendDestination = new ActiveMQQueue(destinationName);
        messageListenerContainer.setDestination(sendDestination);
        messageListenerContainer.setMessageListener(mqMessageListener);
        return messageListenerContainer;
    }
    /**
     * jms模板
     * @return
     */
    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate =  new JmsTemplate(pooledConnectionFactory());
        return jmsTemplate;
    }

}
