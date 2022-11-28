package com.netwisd.biz.study.feign;

import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.study.dto.StudyUserDto;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description:
 * @Author: limengzheng@netwisd.com
 * @Date: 2021/11/24 10:03
 */
@FeignClient(value = "incloud-base-mdm")
public interface MdmClient {

    @PostMapping(path = "/mdmUser/saveStudyUser")
    public MdmUserVo saveStudyUser(MdmUserDto mdmUserDto);


    @DeleteMapping(path = "/mdmUser/deleteStudyUser/{id}")
    public Boolean deleteStudyUser(@PathVariable String id);

    @PutMapping(path = "/mdmUser/updateStudyUser")
    public Boolean updateStudyUser(MdmUserDto mdmUserDto);

    @PostMapping(path = "/mdmUser/regStudyUser")
    public MdmUserVo regStudyUser(MdmUserDto mdmUserDto);

    //查询组织
    @GetMapping(path = "/mdmOrg/kids/{id}")
    public Result<List> kids(@PathVariable Long id);

    /**
     * 根据OrgId查询出下面所有层级的人员
     *
     * @param id 机构id
     * @return
     */
    @ApiOperation(value = "根据OrgId查询出下面所有层级的人员", notes = "根据OrgId查询出下面所有层级的人员")
    @GetMapping("/getUserByOrgId/{id}")
    public Result getUserByOrgId(@PathVariable String id);

    /**
     * 根据deptId 精确查询下面所有人员
     *
     * @param deptId 机构id
     * @return
     */
    @ApiOperation(value = "根据deptId 精确查询下面所有人员", notes = "根据deptId 精确查询下面所有人员")
    @GetMapping("/getUserByDeptId/{deptId}")
    public Result getUserByDeptId(@PathVariable String deptId);

    /**
     * 批量新增在线学习人员
     *
     * @param studyUserDtoList 在线学习人员集合
     * @return
     */
    @PostMapping("/mdmUserForStudy/saveStudyUserBatch")
    Result<Boolean> saveStudyUserBatch(@RequestBody List<StudyUserDto> studyUserDtoList);

    /**
     * 验证验证码
     *
     * @return Result
     */
    @ApiOperation(value = "验证验证码", notes = "验证验证码")
    @GetMapping("/api/verificationCode")
    Result verificationCode(@RequestParam(value = "phoneNumber") String phoneNumber, @RequestParam(value = "code") String code) throws Exception;

    /**
     * 通过人员id返回在线学习角色
     *
     * @param id id
     * @return
     */
    @ApiOperation(value = "通过人员id返回在线学习角色", notes = "通过人员id返回在线学习角色")
    @GetMapping("/mdmUserForStudy/getStudyUserRole/{id}")
    Integer getStudyUserRole(@PathVariable(value = "id") Long id);

    /**
     * 根据用户ID获取用户信息
     * @param id
     * @return
     */
    @GetMapping("/mdmUser/{id}")
    public Result<MdmUserVo> getUserInfoById(@PathVariable("id") Long id);

}
