package com.netwisd.base.zuul.controller;

import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.netwisd.base.common.constants.LoginEnum;
import com.netwisd.base.common.constants.SystemClientInfo;
import com.netwisd.base.common.mdm.dto.MdmUserDeviceDto;
import com.netwisd.base.common.portal.vo.PortalPortalVo;
import com.netwisd.base.common.util.AesUtil;
import com.netwisd.base.common.util.RegularUtil;
import com.netwisd.base.zuul.feign.MdmClient;
import com.netwisd.base.zuul.feign.MsgClient;
import com.netwisd.base.zuul.feign.Oauth2Client;
import com.netwisd.base.zuul.feign.PortalClient;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.log.constant.CommonConstant;
import com.netwisd.common.log.entity.SystemLog;
import com.netwisd.common.log.event.SysLogEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 登陆、刷新token、退出
 *
 * @author 云数网讯
 */
@Slf4j
@RestController
public class TokenController {

    @Autowired
    private Oauth2Client oauth2Client;

    @Autowired
    private PortalClient portalClient;

    @Autowired
    private MdmClient mdmClient;

    @Autowired
    private MsgClient msgClient;

    private final String _verifyCode = "-1"; //不验证验证码

    private final String defaultPassword = "FwQUezqwBTGWN5gVlmqy/g=="; //AES加密后的密码Netwisd  $2a$10$eKsL5twOYgNePPllW5nze.rXHSTu8qFeW2zMQj.yzKqUT81vaoraa

    /**
     * 系统登陆<br>
     * 根据用户名登录<br>
     * 采用oauth2密码模式获取access_token和refresh_token
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/sys/login")
    public Map<String, Object> login(String username, String password, String verifyCode, HttpServletRequest request){
//                                     @RequestHeader(required = false, value = "deviceName") String deviceName,
//                                     @RequestHeader(required = false, value = "deviceModel") String deviceModel,
//                                     @RequestHeader(required = false, value = "deviceType") String deviceType,
//                                     @RequestHeader(required = false, value = "deviceFlag") String deviceFlag)
        Long startTime = System.currentTimeMillis();
        username = checkUserName(username,verifyCode);
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        parameters.put("username", username);
        if(StringUtils.isNotBlank(password)) {
            password = password.replace(" ", "+"); //处理一下AES加密有+号的问题
        }
        String decrypetPasword = AesUtil.aesDecrypt(StringUtils.isBlank(verifyCode)?password:defaultPassword);
        log.info("账户名:{},密码:{},解密后的:{}", username, password, decrypetPasword);
        parameters.put("password", decrypetPasword);
        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);
        saveLoginLog(request, username, startTime, "用户名密码登陆");
        log.info("Login>>>Response:{}", tokenInfo);
        //获取门户首页ID
        try {
            Map map = (Map) tokenInfo.get("data");
            if (null != map) {
                PortalPortalVo portalPortalVo = portalClient.getDefaultPortal();
                map.put("portalInfo", portalPortalVo);
                log.debug("Login>>>获取默认首页成功！id为：" + portalPortalVo.getId());
            }
        } catch (Exception e) {
            log.debug("Login>>>获取默认首页失败！");
        }
        //绑定的设备信息
        //        try {
        //            Map map = (Map) tokenInfo.get("data");
        //            Map userMap = (Map)map.get("loginUser");
        //            String userId = (String)userMap.get("id");
        //            saveDeviceInfo(userId,deviceName,deviceModel,deviceType,deviceFlag);
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //            log.error("Login>>>保存人员设备信息异常：" + e.getMessage());
        //        }
        return tokenInfo;
    }

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Value("${spring.application.name}")
    private String appName;

    /**
     * 登陆日志
     *
     * @param username
     */
    private void saveLoginLog(HttpServletRequest request, String username, Long startTime, String remark) {
        log.info("{}登陆", username);
        // 异步
        CompletableFuture.runAsync(() -> {
            try {
                SystemLog systemLog = new SystemLog();
                //日志内容
                systemLog.setContent(remark);
                //日志类型：操作日志，登录日志，退出日志
                systemLog.setLogType(CommonConstant.LOG_TYPE_LOGIN);
                //操作类型:增、删、改、查、导出、导入
                systemLog.setOperateType(CommonConstant.OPERATE_TYPE_QUERY);
                //应用名称
                systemLog.setAppName(appName);
                //IP 网关获取到,放入到Header中
                systemLog.setRemoteAddr(ServletUtil.getClientIP(request));
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

    /**
     * 系统刷新refresh_token
     *
     * @param refresh_token
     * @return
     */
    @PostMapping("/sys/refresh_token")
    public Map<String, Object> refresh_token(String refresh_token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "refresh_token");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        parameters.put("refresh_token", refresh_token);
        return oauth2Client.postAccessToken(parameters);
    }

    /**
     * 退出
     *
     * @param access_token
     */
    @GetMapping("/sys/logout")
    public void logout(String access_token, @RequestHeader(required = false, value = "Authorization") String token) {
        if (StringUtils.isBlank(access_token)) {
            if (StringUtils.isNoneBlank(token)) {
                access_token = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
            }
        }
        oauth2Client.removeToken(access_token);
    }

    /**
     * 根据token获取用户
     *
     */
    @GetMapping("/sys/authentication")
    public void authentication(@RequestHeader(required = false, value = "Authorization") String token) {
        oauth2Client.authentication();
    }

    /**
     * 验证用户是使用手机号登录、邮箱登录、身份证号登录、以及用户名登录
     * @param username
     * @return
     */
    public String checkUserName(String username,String verifyCode) {
        if(StringUtils.isBlank(verifyCode)) {
            if(StringUtils.isBlank(username)) {
                throw new IncloudException("用户名不能为空！");
            }
            if(RegularUtil.isEmail(username)) {
                username = LoginEnum.EMAIL.code + username;
            } else if(RegularUtil.isIDNumber(username)) {
                username = LoginEnum.IDNUMBER.code + username;
            } else if(RegularUtil.isMobilePhone(username)) {
                username = LoginEnum.PHONE.code + username;
            }
            //默认用户名
            return username;
        } else {
            //如果是使用的验证码方式先验证一下手机号
            if(!RegularUtil.isMobilePhone(username)) {
                throw new IncloudException("手机号格式不正确！");
            }
            if(_verifyCode.equals(verifyCode)) { //如果-1直接跳过
                username = LoginEnum.VERIFYCODE.code + username;
                return username;
            } else {
                //先验证验证码
                Result result = msgClient.verificationCode(username,verifyCode);
                if(result.getCode() == 200) {
                    username = LoginEnum.VERIFYCODE.code + username;
                    return username;
                } else {
                    throw new IncloudException(result.getMsg());
                }
            }
        }
    }

    public void saveDeviceInfo(String userId, String deviceName,String deviceModel,String deviceType,String deviceFlag) {
        MdmUserDeviceDto mdmUserDeviceDto = new MdmUserDeviceDto();
        if(StringUtils.isNotBlank(userId) &&StringUtils.isNotBlank(deviceName) && StringUtils.isNotBlank(deviceModel) && StringUtils.isNotBlank(deviceType) && StringUtils.isNotBlank(deviceFlag)) {
            mdmUserDeviceDto.setUserId(Long.valueOf(userId));
            mdmUserDeviceDto.setDeviceName(deviceName);
            mdmUserDeviceDto.setDeviceModel(deviceModel);
            mdmUserDeviceDto.setDeviceType(deviceType);
            mdmUserDeviceDto.setDeviceFlag(deviceFlag);
            mdmClient.saveUserDevice(mdmUserDeviceDto);
        }
    }
}
