package com.netwisd.base.oauth.feign;

import com.netwisd.base.common.user.LoginAppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient("incloud-base-mdm")
public interface MdmClient {

    /**
     * 根据登录名获取用户
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/mdmUser/anon-internal/findByUsername", params = "username")
    LoginAppUser findByUsername(@RequestParam("username") String username);

    /**
     * 根据身份证获取登录名
     *
     * @param identityNumber
     * @return
     */
    @GetMapping(value = "/users-anon/internalfindUserIdByIdentityNumber", params = "identityNumber")
    Map<String, String> findUserIdByIdentityNumber(@RequestParam("identityNumber") String identityNumber);
}
