package com.netwisd.base.msg.fegin;

import com.netwisd.base.common.mdm.vo.MdmUserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "incloud-base-mdm")
public interface BaseMdmClient {

    //根据身份证号获取用户信息
    @GetMapping(path = "/mdmUser/getUserByIdCards")
    List<MdmUserVo> getUserByIdCards(@RequestParam("idCards") String idCards);

    //根据身份证号获取用户信息
    @GetMapping(path = "/api/findByPhone")
    MdmUserVo findByPhone(@RequestParam("phone") String phone);

    //根据身份证号获取用户信息
    @GetMapping(path = "/api/findByPhoneForMsg")
    MdmUserVo findByPhoneForMsg(@RequestParam("phone") String phone);

}
