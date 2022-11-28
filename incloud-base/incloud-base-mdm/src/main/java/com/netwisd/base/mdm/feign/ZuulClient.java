package com.netwisd.base.mdm.feign;

import com.netwisd.base.common.portal.vo.PortalPortalVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("incloud-base-zuul")
public interface ZuulClient {
    /**
     * 登出
     */
    @GetMapping(path = "/sys/logout")
    PortalPortalVo sysLogout();

}
