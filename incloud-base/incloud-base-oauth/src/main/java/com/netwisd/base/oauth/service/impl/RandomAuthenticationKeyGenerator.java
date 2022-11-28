package com.netwisd.base.oauth.service.impl;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

import java.util.UUID;

/**
 * 解决同一username每次登陆access_token都相同的问题<br>
 *
 * @author 云数网讯 zouliming@netwisd.com
 * @see org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator
 * @see org.springframework.security.oauth2.provider.token.TokenStore
 */
public class RandomAuthenticationKeyGenerator implements AuthenticationKeyGenerator {

    @Override
    public String extractKey(OAuth2Authentication authentication) {
        return UUID.randomUUID().toString();
    }
}
