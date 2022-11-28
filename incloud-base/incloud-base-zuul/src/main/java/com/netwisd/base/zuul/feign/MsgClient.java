package com.netwisd.base.zuul.feign;

import com.netwisd.base.common.mdm.dto.MdmUserDeviceDto;
import com.netwisd.base.common.mdm.vo.MdmUserDeviceVo;
import com.netwisd.common.core.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("incloud-base-msg")
public interface MsgClient {
    /**
     * 获取默认门户首页
     */
    @GetMapping(path = "/api/verificationCode")
    Result verificationCode(@RequestParam String phoneNumber, @RequestParam String code);
}
