package com.netwisd.base.common.util;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Map;

public class AppUserUtil {

    /**
     * 获取登陆的 LoginAppUser
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static LoginAppUser getLoginAppUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
            authentication = oAuth2Auth.getUserAuthentication();

            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                Object principal = authentication.getPrincipal();
                if (principal instanceof LoginAppUser) {
                    return (LoginAppUser) principal;
                }

                Map map = (Map) authenticationToken.getDetails();
                map = (Map) map.get("principal");

                return JSONObject.parseObject(JSONObject.toJSONString(map), LoginAppUser.class);
            }
        }
        return null;
    }

    public static LoginAppUser getLoginAppUserAndCheck() {
        LoginAppUser loginAppUser = getLoginAppUser();
        if(ObjectUtil.isEmpty(loginAppUser)){
            throw new IncloudException("当前未登录用户，请检查配置相关！");
        }
        return loginAppUser;
    }

    /**
     * 获取登录用户的token
     *
     * @return
     */
    public static String getAccessTokenNoBearer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof OAuth2Authentication) {
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                String access_token = details.getTokenValue();
                return access_token;
            }
        }
        return null;
    }


    /**
     * 获取登录用户的token
     *
     * @return
     */
    public static String getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof OAuth2Authentication) {
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                String access_token = details.getTokenValue();
                StringBuffer sb = new StringBuffer();
                sb.append(OAuth2AccessToken.BEARER_TYPE + " " + access_token);
                return sb.toString();
            }
        }
        return null;
    }
}
