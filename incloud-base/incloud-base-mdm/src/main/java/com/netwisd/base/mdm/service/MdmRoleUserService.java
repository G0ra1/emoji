package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmRoleUserDto;
import com.netwisd.base.common.mdm.vo.MdmRoleUserVo;
import com.netwisd.base.mdm.entity.MdmRole;
import com.netwisd.base.mdm.entity.MdmRoleUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.entity.MdmUser;

import java.util.List;

/**
 * @Description 角色与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:44:05
 */
public interface MdmRoleUserService extends IService<MdmRoleUser> {
    Page list(MdmRoleUserDto mdmRoleUserDto);
    Page lists(MdmRoleUserDto mdmRoleUserDto);
    MdmRoleUserVo get(Long id);
    Boolean save(MdmRoleUserDto mdmRoleUserDto);
    Boolean update(MdmRoleUserDto mdmRoleUserDto);
    Boolean delete(Long id);

    //根据人员id修改 相关user 相关的信息
    void updateRoleUserInfoByUserId(MdmUser mdmUser);

    //根据角色id 修改关系表中的角色信息
    void updateRoleUserInfoByRoleId(MdmRole mdmRole);
    
    //根据角色id查询角色人员关系
    List<MdmRoleUserVo> getRoleUserByRoleId(Long roleId);

    //根据角色id查询角色人员关系
    List<MdmRoleUserVo> getRoleUserByRoleIds(List<String> roleIds);
    
    //根据角色id 删除角色和人员关系
    Boolean deleteByRoleId(Long id);

    //根据用户id 删除角色和人员关系
    Boolean deleteByUserId(List<String> ids);

    //根据用户id查询用户的角色信息
    List<MdmRoleUserVo> getRoleUserByUserId(Long userId);
}
