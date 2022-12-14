package com.netwisd.base.oauth.config;

import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.oauth.service.impl.RandomAuthenticationKeyGenerator;
import com.netwisd.base.oauth.service.impl.RedisAuthorizationCodeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;
import java.util.Map;

/**
 * ?????????????????????
 *
 * @author ???????????? zouliming@netwisd.com
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * ???????????????
     *
     * @see SecurityConfig ???authenticationManagerBean()
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * ??????jwt??????redis<br>
     * ??????redis
     */
    @Value("${access_token.store-jwt:false}")
    private boolean storeWithJwt;

    /**
     * ??????????????????json????????????????????????????????????<br>
     * ??????false
     */
    @Value("${access_token.add-userinfo:false}")
    private boolean addUserInfo;

    @Autowired
    private RedisAuthorizationCodeServices redisAuthorizationCodeServices;

    //@Autowired
    //private RedisClientDetailsService redisClientDetailsService;

    /**
     * ????????????
     */
    @Bean
    public TokenStore tokenStore() {
        if (storeWithJwt) {
            return new JwtTokenStore(accessTokenConverter());
        }
        RedisTokenStore redisTokenStore = new RedisTokenStore(redisConnectionFactory);
        // ????????????username????????????access_token??????????????????
        redisTokenStore.setAuthenticationKeyGenerator(new RandomAuthenticationKeyGenerator());

        return redisTokenStore;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager);
        endpoints.tokenStore(tokenStore());
        // ?????????????????????code??????
//		endpoints.authorizationCodeServices(new JdbcAuthorizationCodeServices(dataSource));
        endpoints.authorizationCodeServices(redisAuthorizationCodeServices);
        if (storeWithJwt) {
            endpoints.accessTokenConverter(accessTokenConverter());
        } else {
            // 2018.07.13 ??????????????????????????????????????????????????????
            endpoints.tokenEnhancer((accessToken, authentication) -> {
                addLoginUserInfo(accessToken, authentication);
                return accessToken;
            });
        }
    }

    /**
     * ????????????????????????????????????????????????json?????????<br>
     * ????????????access_token.add-userinfo??????<br>
     * 2018.07.13
     *
     * @param accessToken
     * @param authentication
     */
    private void addLoginUserInfo(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (!addUserInfo) {
            return;
        }
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken defaultOAuth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
            Authentication userAuthentication = authentication.getUserAuthentication();
            Object principal = userAuthentication.getPrincipal();
            if (principal instanceof LoginAppUser) {
                LoginAppUser loginUser = (LoginAppUser) principal;
                Map<String, Object> map = new HashMap<>(defaultOAuth2AccessToken.getAdditionalInformation()); // ??????????????????
                map.put("loginUser", loginUser); // ????????????????????????
                defaultOAuth2AccessToken.setAdditionalInformation(map);
            }
        }
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients(); // ???????????????????????????
    }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * ?????????client???????????????oauth_client_details??????<br>
     * ?????????????????????redis
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("system").secret(bCryptPasswordEncoder.encode("system"))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("app")
                .accessTokenValiditySeconds(28800);
        //clients.jdbc(dataSource);
        // 2018.06.06????????????????????????????????????redisClientDetailsService???????????????
        //clients.withClientDetails(redisClientDetailsService);
        //redisClientDetailsService.loadAllClientToCache();
    }

    @Autowired
    public UserDetailsService userDetailsService;
    /**
     * jwt??????key??????????????????<br>
     * ???????????????????????????????????????????????????????????????
     */
    @Value("${access_token.jwt-signing-key:leasom}")
    private String signingKey;

    /**
     * Jwt?????????????????????<br>
     * ??????access_token.store-jwt???true?????????
     *
     * @return accessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                OAuth2AccessToken oAuth2AccessToken = super.enhance(accessToken, authentication);
                addLoginUserInfo(oAuth2AccessToken, authentication); // 2018.07.13 ??????????????????????????????????????????????????????
                return oAuth2AccessToken;
            }
        };
        DefaultAccessTokenConverter defaultAccessTokenConverter = (DefaultAccessTokenConverter) jwtAccessTokenConverter
                .getAccessTokenConverter();
        DefaultUserAuthenticationConverter userAuthenticationConverter = new DefaultUserAuthenticationConverter();
        userAuthenticationConverter.setUserDetailsService(userDetailsService);
        defaultAccessTokenConverter.setUserTokenConverter(userAuthenticationConverter);
        // 2018.06.29 ????????????????????????????????????????????????????????????????????????jwt?????????access_token???????????????
        jwtAccessTokenConverter.setSigningKey(signingKey);
        return jwtAccessTokenConverter;
    }

}
