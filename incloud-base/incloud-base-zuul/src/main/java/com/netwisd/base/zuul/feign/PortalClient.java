package com.netwisd.base.zuul.feign;

import com.netwisd.base.common.portal.vo.PortalPortalVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("incloud-base-portal")
public interface PortalClient {
    /**
     * 获取默认门户首页
     */
    @GetMapping(path = "/portalPortal/getDefaultPortal")
    PortalPortalVo getDefaultPortal();

}
