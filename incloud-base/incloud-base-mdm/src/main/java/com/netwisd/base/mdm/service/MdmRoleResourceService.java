package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.mdm.entity.MdmRole;
import com.netwisd.base.mdm.entity.MdmRoleResource;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmRoleResourceDto;
import com.netwisd.base.mdm.entity.MdmUser;
import com.netwisd.base.mdm.vo.MdmRoleResourceVo;

import java.util.List;

/**
 * @Description 角色与资源关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-17 17:27:09
 */
public interface MdmRoleResourceService extends IService<MdmRoleResource> {
    Page list(MdmRoleResourceDto mdmRoleResourceDto);
    Page lists(MdmRoleResourceDto mdmRoleResourceDto);
    MdmRoleResourceVo get(Long id);
    Boolean save(MdmRoleResourceDto mdmRoleResourceDto);
    Boolean update(MdmRoleResourceDto mdmRoleResourceDto);
    Boolean delete(Long id);
    //根据角色id 删除关系表
    Boolean deleteByRoleId(Long roleId);

    //根据资源id 删除关系表
    void deleteByResourceId(Long resourceId);
    /**
     * 根据角色id 查询对应的所有资源
     * @param roleId
     * @return
     */
    List<MdmRoleResourceVo> getByRoleId(Long roleId);

    /**
     * 批量根据角色id 查询对应的所有资源
     * @param roleIds
     * @return
     */
    List<MdmRoleResourceVo> getByRoleIds(List<Long> roleIds);

    /**
     * 根据角色id 查询对应的所有资源
     * @param
     * @return
     */
    List<MdmRoleResourceVo> getByResourceId(Long resourceId);

    /**
     * 根据角色信息 修改角色资源关系表
     * @param mdmRole
     */
    void updateRoleResInfoByRoleId(MdmRole mdmRole);

}
