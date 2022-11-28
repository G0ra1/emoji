package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmRoleDto;
import com.netwisd.base.common.mdm.vo.MdmRoleVo;
import com.netwisd.base.mdm.dto.MdmRolePostUserDto;
import com.netwisd.base.mdm.entity.MdmRole;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Description 角色 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 18:05:58
 */
public interface MdmRoleService extends IService<MdmRole> {
    Page list(MdmRoleDto mdmRoleDto);
    Page lists(MdmRoleDto mdmRoleDto);
    MdmRoleVo get(Long id);
    Boolean save(MdmRoleDto mdmRoleDto);
    Boolean update(MdmRoleDto mdmRoleDto);
    Boolean delete(String id);

    /**
     * 设置角色 对应岗位关系、角色对应人员关系
     * @param mdmRolePostUserDto
     * @return
     */
    boolean setRolePostUserRel(MdmRolePostUserDto mdmRolePostUserDto);

    /**
     * 根据角色id 查询角色对应岗位和角色对应人员关系
     * @param roleId
     * @return
     */
    MdmRoleVo getRolePostUserRel(Long roleId);

    //根据code 查询角色
    MdmRoleVo getByCode(String code);

}
