package com.netwisd.base.portal.config;

import cn.hutool.core.util.ObjectUtil;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.common.core.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.Map;

/**
 * @Description: 数据库备份拦截器
 * @Author: zouliming@netwisd.com
 * @Date: 2020/3/13 7:03 下午
 */
@Configuration
@Slf4j
@WebFilter(filterName = "requestParamFilter", urlPatterns = {"/*"})
public class RequestParamFilter implements Filter {

    /**
     * 后台打印过滤请求的信息
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        BodyCachingHttpServletRequestWrapper requestWrapper = null;
        BodyCachingHttpServletResponseWrapper responseWrapper = null;
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String requestUrl = request.getRequestURL().toString();
            Enumeration<String> headerNames = request.getHeaderNames();
            log.info("request url: {}",requestUrl);
            log.info("method type: {}",request.getMethod());
            headerNames.asIterator().forEachRemaining(key -> {
                log.info("header info：{} : {}",key,request.getHeader(key));
            });
            Map<String, String[]> parameterMap = request.getParameterMap();
            log.info("parameter info：{} ", JacksonUtil.toJSONString(parameterMap));
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            if(ObjectUtil.isNotEmpty(loginAppUser)){
                log.info("loginUser info：{} ", loginAppUser.getUserName());
            }
            requestWrapper = new BodyCachingHttpServletRequestWrapper(request);
            responseWrapper = new BodyCachingHttpServletResponseWrapper((HttpServletResponse) servletResponse);
            log.info("请求的body体为: {} ",new String(requestWrapper.getBody()));
        }catch (Exception e){
            e.printStackTrace();
            log.error("Body体获取失败！");
        }finally {
            LocalDateTime stime = LocalDateTime.now();
            filterChain.doFilter(requestWrapper, responseWrapper);
            LocalDateTime etime = LocalDateTime.now();
            log.info("接口响应时间为：{} ms。",java.time.Duration.between(stime,etime).toMillis());
            //log.info("响应的结果为: {} ", JSONUtil.formatJsonStr(new String(responseWrapper.getBody())));
            //log.info("响应的结果为: {} ", new String(responseWrapper.getBody(),"UTF-8"));
        }
    }
}