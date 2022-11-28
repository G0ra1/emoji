package com.netwisd.base.socket.config;

import com.netwisd.base.socket.handler.WebSocketLogHandler;
import com.netwisd.base.socket.handler.WebSocketLogInterceptor;
import com.netwisd.base.socket.handler.WebSocketMsgHandler;
import com.netwisd.base.socket.handler.WebSocketMsgInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/15 3:32 下午
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /*@Autowired
    private org.springframework.beans.factory.BeanFactory beanFactory;*/

    @Autowired
    private WebSocketLogHandler webSocketLogHandler;

    @Autowired
    private WebSocketLogInterceptor webSocketLogInterceptor;

    @Autowired
    private WebSocketMsgHandler webSocketMsgHandler;

    @Autowired
    private WebSocketMsgInterceptor webSocketMsgInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        /*WebSocketHandler webSocketHandler = beanFactory.getBean(WebSocketHandler.class);*/
        //webSocket-log通道
        webSocketHandlerRegistry
                // 指定处理器和ws路径————ws协议
                .addHandler(webSocketLogHandler, "/ws/log")
                // 指定自定义拦截器
                .addInterceptors(webSocketLogInterceptor)
                // 允许跨域
                .setAllowedOrigins("*");

        //webSocket-msg通道
        webSocketHandlerRegistry
                // 指定处理器和ws路径————ws协议
                .addHandler(webSocketMsgHandler, "/ws/msg")
                // 指定自定义拦截器
                .addInterceptors(webSocketMsgInterceptor)
                // 允许跨域
                .setAllowedOrigins("*");

        //sockJs通道
        webSocketHandlerRegistry
                .addHandler(webSocketLogHandler, "/log/sock-js")
                .addInterceptors(webSocketLogInterceptor)
                .setAllowedOrigins("*")
                // 开启sockJs支持，如果当前前后端对ws支持不对称的情况下，会启用sockjs，他会选择另外几种协议进行通信；
                .withSockJS();

        //sockJs通道
        webSocketHandlerRegistry
                .addHandler(webSocketMsgHandler, "/msg/sock-js")
                .addInterceptors(webSocketMsgInterceptor)
                .setAllowedOrigins("*")
                // 开启sockJs支持，如果当前前后端对ws支持不对称的情况下，会启用sockjs，他会选择另外几种协议进行通信；
                .withSockJS();
    }

    /*@Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        *//*container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);*//*
        container.setAsyncSendTimeout(1000l);
        container.setMaxSessionIdleTimeout(2000l);
        return container;
    }*/
}
