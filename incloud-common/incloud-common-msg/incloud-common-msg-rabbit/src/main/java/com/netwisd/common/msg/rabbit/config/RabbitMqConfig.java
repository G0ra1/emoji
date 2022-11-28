package com.netwisd.common.msg.rabbit.config;

import com.netwisd.common.msg.rabbit.handler.ConsumerHandler;
import com.netwisd.common.msg.rabbit.handler.ProducerHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

/**
 * @Description: rabbit的相关配置类
 * @Author: zouliming@netwisd.com
 * @Date: 2020/3/3 6:42 下午
 */
@Configuration
@Slf4j
public class RabbitMqConfig implements RabbitListenerConfigurer {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        ContentTypeDelegatingMessageConverter convert = new ContentTypeDelegatingMessageConverter();
        Jackson2JsonMessageConverter jsonConvert = new Jackson2JsonMessageConverter();
        convert.addDelegate("json", jsonConvert);
        convert.addDelegate("application/json", jsonConvert);
        rabbitTemplate.setMessageConverter(convert);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(rabbitTemplate());
        return rabbitAdmin;
    }

    /**
     * 消息发送至broker以后，确认机制的的回调配置，只是保证mq的broker确认收到信息并回调此方法，但并不意味着消费者肯定消费了消息
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            if(!ack){
                log.error("消息未得到响应，消息ID："+correlationData.getId());
            }
        }
    };

    /**
     * 生产端的return机制，用于校验消息发送失败后的处理
     */
    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText,
                                    String exchange, String routingKey) {
            log.error("消息未发送成功");
        }
    };

    @Bean
    public ProducerHandler producerHandler(){
        ProducerHandler producerHandler = new ProducerHandler();
        return producerHandler;
    }

    @Bean
    public ConsumerHandler consumerHandler(){
        ConsumerHandler consumerHandler = new ConsumerHandler();
        return consumerHandler;
    }

}
