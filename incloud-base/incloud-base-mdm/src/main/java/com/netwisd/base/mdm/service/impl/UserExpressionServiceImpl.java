package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmRoleUserVo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.mdm.entity.MdmUser;
import com.netwisd.base.mdm.service.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserExpressionServiceImpl implements UserExpressionService {

    @Autowired
    private MdmPostUserService mdmPostUserService;

    @Autowired
    MdmUserService mdmUserService;

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmRoleUserService mdmRoleUserService;

    @Autowired
    private MdmDutyUserService mdmDutyUserService;

    @Override
    public List<MdmUserVo> getUserByPostIds(String isMaster, String postIds) {

        return mdmPostUserService.getUserByPostId(Integer.valueOf(isMaster), postIds);
    }

    @Override
    public List<MdmUserVo> getUserByOrgIds(String orgIds) {
        if(StringUtils.isBlank(orgIds)) {
            throw new IncloudException("机构ID不能为空！");
        }
        List<MdmUserVo> users = mdmUserService.getUserByParentOrgId(orgIds);
        return users;
    }

    @Override
    public List<MdmUserVo> getUserByDeptIds(String deptIds) {
        if(StringUtils.isBlank(deptIds)) {
            throw new IncloudException("部门ID不能为空！");
        }
        List<MdmUserVo> users = mdmUserService.getUserByParentDeptId(deptIds);
        return users;
    }

    @Override
    public List<MdmUserVo> getUserByUserIds(String userIds) {
        if(StringUtils.isBlank(userIds)) {
            throw new IncloudException("用户id不能为空！");
        }
        List<String> ids = Stream.of(userIds.split(",")).collect(Collectors.toList());
        List<MdmUser> users = mdmUserService.getByIds(ids);
        List<MdmUserVo> userVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(users)) {
            userVos = DozerUtils.mapList(dozerMapper, users, MdmUserVo.class);
        }
        return userVos;
    }

    @Override
    public List<MdmUserVo> getUserByRoleIds(String roleIds) {
        if(StringUtils.isBlank(roleIds)) {
            throw new IncloudException("角色id不能为空！");
        }
        List<String> ids = Stream.of(roleIds.split(",")).collect(Collectors.toList());
        List<MdmRoleUserVo> roleUserVos = mdmRoleUserService.getRoleUserByRoleIds(ids);
        List<MdmUserVo> userVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(roleUserVos)) {
           List<String> userIds = roleUserVos.stream().map(m -> String.valueOf(m.getUserId())).collect(Collectors.toList());
            List<MdmUser> users = mdmUserService.getByIds(userIds);
            if(CollectionUtils.isNotEmpty(users)) {
                userVos = DozerUtils.mapList(dozerMapper, users, MdmUserVo.class);
            }
        }
        return userVos;
    }

    @Override
    public List<MdmUserVo> getUserByDeptIdAndPostCode(String deptId, String postCode) {
        if(StringUtils.isBlank(deptId)) {
            throw new IncloudException("部门id不能为空！");
        }
        return mdmPostUserService.getByOrgIdAndPostCode(Long.valueOf(deptId),postCode);
    }

    @Override
    public List<MdmUserVo> getUserByDutyIds(Integer isMaster, String dutyIds) {
        return mdmDutyUserService.getUserByDutyId(isMaster,dutyIds);
    }

    @Override
    public List<MdmUserVo> getDeptUserByStarterIdAndPostCode(Long starterId, String postCode) {
        if(StringUtils.isBlank(postCode)) {
            throw new IncloudException("岗位code不能为空！");
        }
        if(ObjectUtil.isNull(starterId)) {
            throw new IncloudException("用户id不能为空！");
        }
        //获取用户信息
        MdmUser mdmUser = mdmUserService.getById(starterId);
        if(null == mdmUser) {
            throw new IncloudException("不存在的用户信息！");
        }
        //根据 用户部门 和岗位code 获取人员信息
        List<MdmUserVo>  resultList = mdmPostUserService.getByOrgIdAndPostCode(mdmUser.getParentDeptId(),postCode);
        return resultList;
    }
}
