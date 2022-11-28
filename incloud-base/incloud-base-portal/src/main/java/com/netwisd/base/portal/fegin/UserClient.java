package com.netwisd.base.portal.fegin;

import com.netwisd.base.common.user.vo.DataModulePermissionVo;
import com.netwisd.base.common.user.vo.DeptVO;
import com.netwisd.base.common.user.vo.EmplVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/21 11:12 上午
 */
@FeignClient(value = "incloud-base-user")
public interface UserClient {
//    @ApiOperation(value = "获取指定ID的用户", notes = "获取指定ID的用户")
//    @GetMapping(value = "/api/back/getEmplByUserId")
//    public List<EmplVO> getEmplByUserId(@RequestParam(value = "userId") String userId);

    @ApiOperation(value = "获取用户的所有部门", notes = "获取用户的所有部门")
    @GetMapping(value = "/api/userAllDept")
    public List<DeptVO> getUserAllDept(@RequestParam(value = "userId") String userId, @RequestParam(value = "flag") String flag, @RequestParam(value = "mechanismId") String mechanismId);

    @ApiOperation(value = "通过手机号查找用户", notes = "通过手机号查找用户")
    @GetMapping(value = "/api/back/getSelectUsers")
    public EmplVO getSelectUser(@RequestParam(value = "welinkMobilePhone") String welinkMobilePhone);

    @ApiOperation(value = "获取指定ID的用户", notes = "获取指定ID的用户")
    @GetMapping(value = "/api/empl/getEmplByUserId")
    public List<EmplVO> getEmplByUserId(@RequestParam(value = "userId") String userId);

    @ApiOperation(value = "获取指定ID的部门", notes = "获取指定ID的部门")
    @GetMapping(value = "/api/dept/getDeptByDeptId")
    public List<DeptVO> getDeptByDeptId(@RequestParam(value = "deptId") String deptId);

    @ApiOperation(value = "根据组织ID获取组织详情", notes = "根据组织ID获取组织详情")
    @GetMapping(value = "/api/getDeptInfoById")
    public DeptVO getDeptInfoById(@RequestParam(value = "deptId") String deptId);



    @ApiOperation(value = "通过第三方数据id或成本中心字段查组织", notes = "通过第三方数据id或成本中心字段查组织")
    @GetMapping(value = "/depts/getDeptByThirdPartyIdOrKostl")
    public DeptVO getDeptByThirdPartyIdOrKostl(@RequestParam(value = "thirdPartyId", required = false) String thirdPartyId,
                                                     @RequestParam(value = "kostl", required = false) String kostl);

    @ApiOperation(value = "通过第三方数据id或成本中心字段查用户", notes = "通过第三方数据id或成本中心字段查用户")
    @GetMapping(value = "/empls/getUserByThirdPartyIdOrKostl")
    public EmplVO getUserByThirdPartyIdOrKostl(@RequestParam(value = "thirdPartyId", required = false) String thirdPartyId,
                                                     @RequestParam(value = "kostl", required = false) String kostl);

    //获取当前登录人的数据模块
    @GetMapping(value = "/api/getDataModulePermission")
    public DataModulePermissionVo getDataModulePermissionByLogin(@RequestParam(value = "dataModuleType", required = false) String dataModuleType);

    /**
     * 获取所有下级
     *
     * @param deptId
     * @param deptType
     * @param level
     * @return
     */
    @GetMapping(value = "api/getChildDept")
    List<DeptVO> getChildDept(@RequestParam(value = "deptId") String deptId, @RequestParam(value = "deptType", required = false) String deptType, @RequestParam(value = "level", required = false) String level, @RequestParam(value = "isSelf", required = false, defaultValue = "true") boolean isSelf);


}
