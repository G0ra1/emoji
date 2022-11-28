package com.netwisd.base.mdm.service;


import com.netwisd.base.common.mdm.vo.MdmUserVo;

import java.util.List;

/**
 * @Description 提供工作流表达式
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-25 10:48:50
 */
public interface UserExpressionService {

    /**
     * 根据岗位获取人
     * @param isMaster 是否主岗
     * @param postIds  岗位ids
     * @return
     */
    List<MdmUserVo> getUserByPostIds(String isMaster, String postIds);

    /**
     * 根据机构获取人
     * @param orgIds 机构id
     * @return
     */
    List<MdmUserVo> getUserByOrgIds(String orgIds);

    /**
     * 根据部门获取人
     * @param deptIds 部门id
     * @return
     */
    List<MdmUserVo> getUserByDeptIds(String deptIds);

    /**
     * 根据用户ID获取人
     * @param userIds
     * @return
     */
    List<MdmUserVo> getUserByUserIds(String userIds);

    /**
     * 根据角色ID获取人
     * @param roleIds
     * @return
     */
    List<MdmUserVo> getUserByRoleIds(String roleIds);


    /**
     * 根据部门id和岗位编码获取人
     * @param deptId 部门id
     * @param postCode 岗位code
     * @return
     */
    List<MdmUserVo> getUserByDeptIdAndPostCode(String deptId, String postCode);

    /**
     * 根据职务获取人
     * @param isMaster 是否主职
     * @param dutyIds  职位ids
     * @return
     */
    List<MdmUserVo> getUserByDutyIds(Integer isMaster, String dutyIds);

    /**
     * 根据用户ID获取人
     * @param starterId 申请人id
     * @param postCode  岗位code
     * @return
     */
    List<MdmUserVo> getDeptUserByStarterIdAndPostCode(Long starterId, String postCode);

}
