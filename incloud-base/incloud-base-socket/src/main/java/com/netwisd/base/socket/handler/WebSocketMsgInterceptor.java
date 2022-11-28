package com.netwisd.base.socket.handler;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/15 3:40 下午
 */
@Slf4j
@Component
public class WebSocketMsgInterceptor implements HandshakeInterceptor {

    /**
     * 握手前
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param map
     * @return
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, org.springframework.web.socket.WebSocketHandler webSocketHandler, Map<String, Object> map) {
        log.debug("MsgBeforeHandshake握手前！");
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) serverHttpRequest;
            // 从前端请求中获取当前socket请求中的token参数，来获取当前人员信息，并存储到map中，之后请求会被转发到后置的hander处理器中；
            String token = servletServerHttpRequest.getServletRequest().getParameter("token");
            String userId = servletServerHttpRequest.getServletRequest().getParameter("userId");
            map.put("token", StrUtil.isEmpty(token) ? "" : token);
            map.put("userId", StrUtil.isEmpty(userId) ? "" : userId);
            return true;
        } else {
            log.info("用户登录信息已失效！");
            return false;
        }
    }

    /**
     * 握手后
     *
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @param webSocketHandler
     * @param e
     */
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, org.springframework.web.socket.WebSocketHandler webSocketHandler, Exception e) {
        log.debug("MsgAfterHandshake握手后！");
    }

}
