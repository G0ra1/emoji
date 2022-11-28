package com.netwisd.base.zuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * spring security配置
 *
 * @author 云数网讯
 */
@EnableOAuth2Sso
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CorsFilter corsFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //http.headers().frameOptions().sameOrigin();
        //解决frame调用
        http.headers().frameOptions().disable();
        http.cors();
        http.addFilter(corsFilter);
        //http.addFilterBefore(new MySqlBackUpFilter(), ChannelProcessingFilter.class);
        //单个用户只能创建一个session，第一次登录后，下次再登录会踢出第一次的，会让第一个会话超时
        //http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
    }
}