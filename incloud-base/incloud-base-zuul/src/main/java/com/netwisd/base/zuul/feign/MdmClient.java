package com.netwisd.base.zuul.feign;

import com.netwisd.base.common.mdm.dto.MdmUserDeviceDto;
import com.netwisd.base.common.mdm.vo.MdmUserDeviceVo;
import com.netwisd.base.common.portal.vo.PortalPortalVo;
import com.netwisd.common.core.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("incloud-base-mdm")
public interface MdmClient {
    /**
     * 获取默认门户首页
     */
    @GetMapping(path = "/api/verificationCode")
    Result verificationCode(@RequestParam String phoneNumber, @RequestParam String code);

    /**
     * 保存用户信息
     */
    @PostMapping(path = "/userDevice/save")
    Boolean saveUserDevice(@RequestBody MdmUserDeviceDto mdmUserDeviceDto);


    /**
     * 根据用户信息获取设备信息
     */
    @GetMapping(path = "/userDevice/{id}")
    MdmUserDeviceVo getUserDevice(@PathVariable("id") Long id);

}
