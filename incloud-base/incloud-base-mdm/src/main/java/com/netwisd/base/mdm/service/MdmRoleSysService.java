package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.mdm.entity.MdmRoleSys;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmRoleSysDto;
import com.netwisd.base.mdm.vo.MdmRoleSysVo;
/**
 * @Description 角色对应的功能权限子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-23 19:12:14
 */
public interface MdmRoleSysService extends IService<MdmRoleSys> {
    Page list(MdmRoleSysDto mdmRoleSysDto);
    Page lists(MdmRoleSysDto mdmRoleSysDto);
    MdmRoleSysVo get(Long id);
    Boolean save(MdmRoleSysDto mdmRoleSysDto);
    Boolean update(MdmRoleSysDto mdmRoleSysDto);
    Boolean delete(Long id);
    int deleteByRoleId(Long id);
}
