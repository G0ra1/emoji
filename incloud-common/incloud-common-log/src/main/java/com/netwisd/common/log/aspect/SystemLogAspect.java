package com.netwisd.common.log.aspect;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.common.core.util.SpringUtils;
import com.netwisd.common.log.annotation.SysLog;
import com.netwisd.common.log.constant.ApplicationProperty;
import com.netwisd.common.log.entity.SystemLog;
import com.netwisd.common.log.event.SysLogEvent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Slf4j
@Aspect
public class SystemLogAspect {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Around("@annotation(sysLog)")
    public Object around(ProceedingJoinPoint point, SysLog sysLog) throws Throwable {
        HttpServletRequest request = SpringUtils.getHttpServletRequest();
        ApplicationProperty instance = ApplicationProperty.getInstance();
        String strClassName = point.getTarget().getClass().getName();
        String strMethodName = point.getSignature().getName();
        //log.debug("[类名]:{},[方法]:{}", strClassName, strMethodName);
        //构建日志对象
        SystemLog systemLog = new SystemLog();
        //日志内容
        systemLog.setContent(sysLog.value());
        //日志类型：操作日志，登录日志，退出日志
        systemLog.setLogType(sysLog.logType());
        //操作类型:增、删、改、查、导出、导入
        systemLog.setOperateType(sysLog.operateType());
        //应用名称
        systemLog.setAppName(instance.getAppName());
        //IP 网关获取到,放入到Header中
        systemLog.setRemoteAddr(request.getHeader("HTTP_X_FORWARDED_FOR"));
        //请求URL
        systemLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
        //请求方法
        systemLog.setMethod(strClassName + "." + strMethodName + "()");
        //请求类型 GET POST PUT DELETE
        systemLog.setRequestType(request.getMethod());
        //浏览器类型
        systemLog.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        //请求参数
        systemLog.setParams(getReqestParams(request, point));
        //获取登录用户信息
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (ObjectUtil.isNotNull(loginAppUser) && StrUtil.isNotEmpty(loginAppUser.getUsername())) {
            systemLog.setCreateUserId(loginAppUser.getUsername());
            systemLog.setCreateUserName(loginAppUser.getUsername());
        }
        //创建时间
        systemLog.setCreateTime(LocalDateTime.now());
        // 发送异步日志事件
        Long startTime = System.currentTimeMillis();
        Object obj = point.proceed();
        Long endTime = System.currentTimeMillis();
        systemLog.setExecTime(endTime - startTime);
        applicationEventPublisher.publishEvent(new SysLogEvent(systemLog));
        return obj;
    }

    //获取请求参数
    private String getReqestParams(HttpServletRequest request, JoinPoint joinPoint) {
        String httpMethod = request.getMethod();
        String params = "";
        if ("POST".equals(httpMethod) || "PUT".equals(httpMethod) || "PATCH".equals(httpMethod)) {
            Object[] paramsArray = joinPoint.getArgs();
            // java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
            //  https://my.oschina.net/mengzhang6/blog/2395893
            Object[] arguments = new Object[paramsArray.length];
            for (int i = 0; i < paramsArray.length; i++) {
                if (paramsArray[i] instanceof BindingResult || paramsArray[i] instanceof ServletRequest || paramsArray[i] instanceof ServletResponse || paramsArray[i] instanceof MultipartFile) {
                    //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                    //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                    continue;
                }
                arguments[i] = paramsArray[i];
            }
            //日志数据太长的直接过滤掉
            PropertyFilter profilter = (o, name, value) -> {
                if (value != null && value.toString().length() > 500) {
                    return false;
                }
                return true;
            };
            params = JSONObject.toJSONString(arguments, profilter);
            //日志数据太长的直接过滤掉
        } else {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            // 请求的方法参数值
            Object[] args = joinPoint.getArgs();
            // 请求的方法参数名称
            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
            String[] paramNames = u.getParameterNames(method);
            if (args != null && paramNames != null) {
                for (int i = 0; i < args.length; i++) {
                    params += "  " + paramNames[i] + ": " + args[i];
                }
            }
        }
        return params;
    }

}
