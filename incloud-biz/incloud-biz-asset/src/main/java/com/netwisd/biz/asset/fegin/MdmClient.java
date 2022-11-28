package com.netwisd.biz.asset.fegin;

import com.netwisd.base.common.mdm.vo.MdmOrgAllVo;
import com.netwisd.base.common.mdm.vo.MdmOrgVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("incloud-base-mdm")
public interface MdmClient {
    /**
     * 根据机构ID 或者部门ID 查询其下面的所有子机构或者子部门
     * @param id
     * @param orgType 类型
     * @return
     */
//    @ApiOperation(value = "根据机构ID 或者部门ID 查询其下面的所有子机构或者子部门", notes = "根据机构ID 或者部门ID 查询其下面的所有子机构或者子部门")
//    @GetMapping("/api/getDeptOrOrgById")
//    public List<MdmOrgAllVo> getDeptOrOrgById(@RequestParam Long id, @RequestParam Integer orgType);


    /**
     * 根据机构ID 或者部门ID 查询其下面的所有子机构或者子部门
     * @param id
     * @param orgType 类型
     * @return
     */
    @ApiOperation(value = "根据机构ID 或者部门ID 查询其下面的所有子机构或者子部门", notes = "根据机构ID 或者部门ID 查询其下面的所有子机构或者子部门")
    @GetMapping("/api/org/get")
    public MdmOrgVo getOrgOrDept(@RequestParam Long id);

}
