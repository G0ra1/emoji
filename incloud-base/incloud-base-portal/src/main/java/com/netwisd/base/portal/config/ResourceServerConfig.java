package com.netwisd.base.portal.config;

import com.netwisd.base.common.constants.PermitAllUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.http.HttpServletResponse;

/**
 * 资源服务配置
 *
 * @author 云数网讯
 *
 */
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    AuthExceptionEntryPoint authExceptionEntryPoint;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().authorizeRequests().antMatchers(PermitAllUrl.permitAllUrl("/portalAppMsg/**","/portalAppUpdateMsg/**","/portalContentLogin/getLog")).permitAll() // 放开权限的url PermitAllUrl.permitAllUrl()
                .anyRequest().authenticated().and().httpBasic();
        //单个用户只能创建一个session，第一次登录后，下次再登录会踢出第一次的，会让第一个会话超时
        //http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.authenticationEntryPoint(authExceptionEntryPoint);
    }

}
