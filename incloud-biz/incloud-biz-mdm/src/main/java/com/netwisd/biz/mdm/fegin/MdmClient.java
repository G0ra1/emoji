package com.netwisd.biz.mdm.fegin;

import com.netwisd.base.common.mdm.vo.MdmOrgVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.netwisd.common.core.util.Result;
import java.util.List;

@FeignClient(value = "incloud-base-mdm")
public interface MdmClient {

    @PostMapping(path = "/mdmOrg/getOrgByJcOrgIds")
    public Result<List<MdmOrgVo>> getOrgByJcOrgIds(@RequestParam String ids) ;

    @GetMapping(path = "/mdmOrg/{id}")
    public Result<MdmOrgVo> get(@PathVariable("id" )Long id) ;

}
