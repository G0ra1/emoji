package com.netwisd.base.oauth.controller;

//import com.netwisd.log.autoconfigure.LogMqClient;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.URLUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.AuthenticationVo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.log.constant.CommonConstant;
import com.netwisd.common.log.entity.SystemLog;
import com.netwisd.common.log.event.SysLogEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping
public class OAuth2Controller {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private ConsumerTokenServices tokenServices;

    @Autowired
    private Mapper dozerMapper;

    /**
     * 登录认证
     *
     * @param principal
     * @param parameters
     * @return
     */
    @PostMapping("/oauth/token")
    public Result<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) {
        try {
            ResponseEntity<OAuth2AccessToken> responseEntity = tokenEndpoint.postAccessToken(principal, parameters);
            responseEntity.getStatusCodeValue();
            return Result.success(responseEntity.getBody());
        } catch (InvalidGrantException e) {
            //ResourceOwnerPasswordTokenGranter.getOAuth2Authentication 验证密码,抛出异常
            log.debug("InvalidGrantException:{}", e);
            throw new IncloudException("账户名或密码错误");
        } catch (InternalAuthenticationServiceException e) {
            //DaoAuthenticationProvider.retrieveUser 验证用户
            log.debug("InternalAuthenticationServiceException:{}", e);
            throw new IncloudException(e.getMessage());
        } catch (Exception e) {
            log.debug("Exception:{}", e);
            throw new IncloudException("服务端异常，请联系管理员");
        }
    }

    /**
     * 注销登陆/退出
     * 移除access_token和refresh_token<br>
     * 2018.06.28 改为用ConsumerTokenServices，该接口的实现类DefaultTokenServices已有相关实现，我们不再重复造轮子
     *
     * @param access_token
     */
    @DeleteMapping(value = "/remove_token", params = "access_token")
    public void removeToken(String access_token, HttpServletRequest request) {
        Long startTime = System.currentTimeMillis();
        boolean flag = tokenServices.revokeToken(access_token);
        if (flag) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            saveLogoutLog(authentication.getName(), "退出", startTime, request);
        }
    }

    /**
     * 当前登陆用户信息<br>
     * <p>
     * security获取当前登录用户的方法是SecurityContextHolder.getContext().getAuthentication()<br>
     * 返回值是接口org.springframework.security.core.Authentication，又继承了Principal<br>
     * 这里的实现类是org.springframework.security.oauth2.provider.OAuth2Authentication<br>
     * <p>
     * 因此这只是一种写法，下面注释掉的三个方法也都一样，这四个方法任选其一即可，也只能选一个，毕竟uri相同，否则启动报错<br>
     * 2018.05.23改为默认用这个方法，好理解一点
     *
     * @return
     */
    @GetMapping("/user-me")
    public Authentication principal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("user-me:{}", authentication.getName());
        return authentication;
    }

    @GetMapping("/authentication")
    public Result authentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("user-me:{}", authentication.getName());
        Object principal = authentication.getPrincipal();
        if(principal instanceof LoginAppUser){
            LoginAppUser loginAppUser = (LoginAppUser) principal;
            MdmUserVo userVo = dozerMapper.map(loginAppUser, MdmUserVo.class);
            AuthenticationVo authenticationVo = new AuthenticationVo(true,userVo);
            return Result.success(authenticationVo);
        }
        return Result.failed("验证用户失败");
    }



    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 退出日志
     *
     * @param username
     */
    private void saveLogoutLog(String username, String remark, Long startTime, HttpServletRequest request) {
        log.info("{}退出", username);
        // 异步
        CompletableFuture.runAsync(() -> {
            try {
                SystemLog systemLog = new SystemLog();
                //日志内容
                systemLog.setContent(remark);
                //日志类型：操作日志，登录日志，退出日志
                systemLog.setLogType(CommonConstant.LOG_TYPE_LOGOUT);
                //操作类型:增、删、改、查、导出、导入
                systemLog.setOperateType(CommonConstant.OPERATE_TYPE_QUERY);
                //应用名称
                systemLog.setAppName("incloud-base-zull");
                //IP 网关获取到,放入到Header中
                systemLog.setRemoteAddr(request.getHeader("HTTP_X_FORWARDED_FOR"));
                //请求URL
                systemLog.setRequestUri(URLUtil.getPath(request.getRequestURI()));
                //请求方法
                systemLog.setRequestType(request.getMethod());
                //浏览器类型
                systemLog.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
                //请求参数
                systemLog.setParams(username);
                systemLog.setCreateUserId(username);
                systemLog.setCreateUserName(username);
                systemLog.setCreateTime(LocalDateTime.now());
                Long endTime = System.currentTimeMillis();
                systemLog.setExecTime(endTime - startTime);
                applicationEventPublisher.publishEvent(new SysLogEvent(systemLog));
            } catch (Exception e) {
                // do nothing
                log.error("e:{}", e);
            }
        });
    }
    //	@GetMapping("/user-me")
    //	public Principal principal(Principal principal) {
    //		log.debug("user-me:{}", principal.getName());
    //		return principal;
    //	}
    //
    //	@GetMapping("/user-me")
    //	public Authentication principal(Authentication authentication) {
    //		log.debug("user-me:{}", authentication.getName());
    //		return authentication;
    //	}
    //
    //	@GetMapping("/user-me")
    //	public OAuth2Authentication principal(OAuth2Authentication authentication) {
    //		log.debug("user-me:{}", authentication.getName());
    //		return authentication;
    //	}

    //	@Autowired
    //	private TokenStore tokenStore;
    //
    //	/**
    //	 * 移除access_token和refresh_token
    //	 *
    //	 * @param access_token
    //	 */
    //	@DeleteMapping(value = "/remove_token", params = "access_token")
    //	public void removeToken(Principal principal, String access_token) {
    //		OAuth2AccessToken accessToken = tokenStore.readAccessToken(access_token);
    //		if (accessToken != null) {
    //			// 移除access_token
    //			tokenStore.removeAccessToken(accessToken);
    //
    //			// 移除refresh_token
    //			if (accessToken.getRefreshToken() != null) {
    //				tokenStore.removeRefreshToken(accessToken.getRefreshToken());
    //			}
    //
    //			saveLogoutLog(principal.getName());
    //		}
    //	}
}
