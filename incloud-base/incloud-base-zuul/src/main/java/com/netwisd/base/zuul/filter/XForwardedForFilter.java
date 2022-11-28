package com.netwisd.base.zuul.filter;

import cn.hutool.extra.servlet.ServletUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class XForwardedForFilter extends ZuulFilter {

    private static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";

    private static final String ZUUL_HOST_IP = "ZUUL_HOST_IP";

    private static final String ZUUL_HOST_PORT = "ZUUL_HOST_PORT";

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        ctx.getZuulRequestHeaders().put(HTTP_X_FORWARDED_FOR, ServletUtil.getClientIP(request));
        log.info("客户端地址:{},网关真实服务器地址:{}", ServletUtil.getClientIP(request), request.getServerName() + ":" + request.getServerPort());
        ctx.getZuulRequestHeaders().put(ZUUL_HOST_IP, request.getServerName());
        ctx.getZuulRequestHeaders().put(ZUUL_HOST_PORT, String.valueOf(request.getServerPort()));
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }
}
