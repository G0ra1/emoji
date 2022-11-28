package com.netwisd.base.oauth.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.oauth.feign.MdmClient;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 根据用户名获取用户<br>
 * <p>
 * 密码校验请看下面两个类
 *
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private MdmClient mdmClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("AuthUserDetailsService loadUserByUsername username:{}", username);
        LoginAppUser loginAppUser = mdmClient.findByUsername(username);
        if (ObjectUtil.isEmpty(loginAppUser)) {
            throw new IncloudException("用户不存在,或账号密码错误!");
        } else if (!loginAppUser.isEnabled()) {
            throw new IncloudException("【用户已失效，请联系管理员核对身份信息】");
        }
        return loginAppUser;
    }

}
