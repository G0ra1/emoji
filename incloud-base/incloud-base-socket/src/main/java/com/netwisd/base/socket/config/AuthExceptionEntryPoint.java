package com.netwisd.base.socket.config;

import com.netwisd.common.core.constants.ResultConstants;
import com.netwisd.common.core.util.JacksonUtil;
import com.netwisd.common.core.util.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 自定义的token验证返回
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/26 13:03 下午
 */
@Component
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        Throwable cause = authException.getCause();
        response.setStatus(HttpStatus.OK.value());
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            if (cause instanceof InvalidTokenException) {
                response.setStatus(ResultConstants.SC_UNAUTHORIZED.code);
                response.getWriter().write(JacksonUtil.toJSONString(Result.failed(ResultConstants.SC_UNAUTHORIZED.code, "非法的token，请输入正确的token值！")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
