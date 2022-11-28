package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmRolePostDto;
import com.netwisd.base.common.mdm.vo.MdmRolePostVo;
import com.netwisd.base.mdm.entity.MdmPost;
import com.netwisd.base.mdm.entity.MdmRole;
import com.netwisd.base.mdm.entity.MdmRolePost;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Description 角色与岗位关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:19:56
 */
public interface MdmRolePostService extends IService<MdmRolePost> {
    Page list(MdmRolePostDto mdmRolePostDto);
    Page lists(MdmRolePostDto mdmRolePostDto);
    MdmRolePostVo get(Long id);
    Boolean save(MdmRolePostDto mdmRolePostDto);
    Boolean update(MdmRolePostDto mdmRolePostDto);
    Boolean delete(Long id);
    //根据人员id修改 相关user 相关的信息
    void updateRolePostInfoByPostId(MdmPost mdmPost);

    //根据角色id 修改关系表中的角色信息
    void updateRolePostInfoByRoleId(MdmRole mdmRole);

    //根据角色id查询角色岗位关系
    List<MdmRolePostVo> getRolePostByRoleId(Long roleId);

    //岗位删除时，删除role
    void deleteByPostId(List id);

    //岗位修改时，同时关系表需要修改
    void updateByPost(List<MdmPost> mdmPostList);

    //根据角色id 删除角色和岗位关系
    Boolean deleteByRoleId(Long roleId);

    /**
     * 通过岗位id查询岗位角色信息
     * @param id
     * @return
     */
    List<MdmRolePostVo> getRolePostByPostId(Long id);

    /**
     * 批量通过岗位id查询岗位角色信息
     * @param ids
     * @return
     */
    List<MdmRolePostVo> getRolePostByPostIds(List<Long> ids);
}
