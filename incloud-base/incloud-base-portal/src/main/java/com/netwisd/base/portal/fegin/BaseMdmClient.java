package com.netwisd.base.portal.fegin;

import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "incloud-base-mdm")
public interface BaseMdmClient {
    //根据身份证号获取用户信息
    @GetMapping(path = "/mdmUser/getUserByIdCards")
    List<MdmUserVo> getUserByIdCards(@RequestParam("idCards") String idCards);

    //通过id查询用户
    @GetMapping("/mdmUser/{id}" )
    Result<MdmUserVo> get(@PathVariable("id" ) Long id);
}
