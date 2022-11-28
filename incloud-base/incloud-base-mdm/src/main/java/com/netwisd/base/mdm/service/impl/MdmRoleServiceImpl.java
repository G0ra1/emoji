package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.mdm.dto.MdmRoleDto;
import com.netwisd.base.common.mdm.vo.MdmRolePostVo;
import com.netwisd.base.common.mdm.vo.MdmRoleUserVo;
import com.netwisd.base.common.mdm.vo.MdmRoleVo;
import com.netwisd.base.mdm.constants.EventConstants;
import com.netwisd.base.mdm.constants.NeoNodeEnum;
import com.netwisd.base.mdm.dto.MdmRolePostUserDto;
import com.netwisd.base.mdm.entity.*;
import com.netwisd.base.mdm.event.MdmPublisher;
import com.netwisd.base.mdm.mapper.MdmRoleMapper;
import com.netwisd.base.mdm.service.*;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 角色 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 18:05:58
 */
@Service
@Slf4j
public class MdmRoleServiceImpl extends ServiceImpl<MdmRoleMapper, MdmRole> implements MdmRoleService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmRoleMapper mdmRoleMapper;

    @Autowired
    private MdmUserService mdmUserService;

    @Autowired
    private MdmPostService mdmPostService;

    @Autowired
    private MdmRoleUserService mdmRoleUserService;

    @Autowired
    private MdmRolePostService mdmRolePostService;

    @Autowired
    private MdmPublisher mdmPublisher;

    @Autowired
    private  MdmRoleSysService mdmRoleSysService;

    @Autowired
    private NeoService neoService;

    /**
    * 单表简单查询操作
    * @param mdmRoleDto
    * @return
    */
    @Override
    public Page list(MdmRoleDto mdmRoleDto) {
        LambdaQueryWrapper<MdmRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(mdmRoleDto.getRoleCode()),MdmRole::getRoleCode,mdmRoleDto.getRoleCode());
        queryWrapper.like(StringUtils.isNotBlank(mdmRoleDto.getRoleName()),MdmRole::getRoleName,mdmRoleDto.getRoleName());
        queryWrapper.eq(null != mdmRoleDto.getRoleType(),MdmRole::getRoleType,mdmRoleDto.getRoleType());
        Page<MdmRole> page = mdmRoleMapper.selectPage(mdmRoleDto.getPage(),queryWrapper);
        Page<MdmRoleVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmRoleVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param mdmRoleDto
    * @return
    */
    @Override
    public Page lists(MdmRoleDto mdmRoleDto) {
        Page<MdmRoleVo> pageVo = mdmRoleMapper.getPageList(mdmRoleDto.getPage(),mdmRoleDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmRoleVo get(Long id) {
        MdmRole mdmRole = super.getById(id);
        MdmRoleVo mdmRoleVo = null;
        if(mdmRole !=null){
            mdmRoleVo = dozerMapper.map(mdmRole,MdmRoleVo.class);
        }
        log.debug("查询成功");
        return mdmRoleVo;
    }

    /**
    * 保存实体
    * @param mdmRoleDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmRoleDto mdmRoleDto) {
        MdmRole mdmRole = dozerMapper.map(mdmRoleDto,MdmRole.class);
        mdmRole.setSort(getMaxSort());
        LambdaQueryWrapper<MdmRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRole::getRoleCode,mdmRoleDto.getRoleCode());
        List<MdmRole> list = mdmRoleMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(list)) {
            throw new IncloudException("角色CODE重复。");
        }
        boolean result = super.save(mdmRole);
        //保存角色子系统关系表
        //String sysStr = mdmRoleDto.getSysId();
        //this.saveRoleSys(mdmRole,sysStr);
        if(result){
            //保存neo4j角色节点
            //neoService.saveRoleNode(mdmRole);
           log.debug("保存成功");
        }
        return result;
    }

    /**
     * 保存角色子系统关系表
     * @param mdmRole
     * @param sysStr
     */
    public void saveRoleSys(MdmRole mdmRole,String sysStr) {
        if(StringUtils.isNotBlank(sysStr)) {
            List<String> streamStr = Stream.of(sysStr.split(",")).collect(Collectors.toList());
            List<MdmRoleSys> resultList = new ArrayList<>();
            for (String s : streamStr) {
                MdmRoleSys mdmRoleSys = new MdmRoleSys();
                mdmRoleSys.setRoleCode(mdmRole.getRoleCode());
                mdmRoleSys.setRoleId(mdmRole.getId());
                mdmRoleSys.setSysId(Long.valueOf(s));
                resultList.add(mdmRoleSys);
            }
            if(CollectionUtil.isNotEmpty(resultList)) {
                mdmRoleSysService.saveBatch(resultList);
            }
        }
    }

    /**
    * 修改实体
    * @param mdmRoleDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmRoleDto mdmRoleDto) {
        mdmRoleDto.setUpdateTime(LocalDateTime.now());
        LambdaQueryWrapper<MdmRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRole::getRoleCode,mdmRoleDto.getRoleCode());
        List<MdmRole> list = mdmRoleMapper.selectList(queryWrapper);
        if(CollectionUtil.isNotEmpty(list) && list.size() > 1) {
            throw new IncloudException("系统CODE重复。");
        }
        MdmRole mdmRole = dozerMapper.map(mdmRoleDto,MdmRole.class);
        boolean result = super.updateById(mdmRole);
        if(result){
            log.debug("修改成功");
        }
        //先删除关系
        mdmRoleSysService.deleteByRoleId(mdmRole.getId());
        //保存角色子系统关系表
        //String sysStr = mdmRoleDto.getSysId();
        //this.saveRoleSys(mdmRole,sysStr);
        //修改角色 同时修改岗位人员关系表
        List<MdmRole> mdmRoleList = new ArrayList<>();
        mdmRoleList.add(mdmRole);
        mdmPublisher.publish(EventConstants.UpdateUserEvent,mdmRoleList);
        return result;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(String id) {
        if(StringUtils.isNotBlank(id)) {
            List<String> streamStr = Stream.of(id.split(",")).collect(Collectors.toList());
            boolean result = super.removeByIds(streamStr);
            if(result){
                log.debug("删除成功");
            }
            for (String s : streamStr) {
                //删除和子系统关系
                mdmRoleSysService.deleteByRoleId(Long.valueOf(s));
                //删除角色人员关系
                mdmRoleUserService.deleteByRoleId(Long.valueOf(s));
                //删除 角色和岗位
                mdmRolePostService.deleteByRoleId(Long.valueOf(s));
            }
            //删除neo4j 关系
            //neoService.delNodeByMid(NeoNodeEnum.ROLE.code,streamStr);
            return result;
        } else {
            throw new IncloudException("删除角色的id不能为空！");
        }

    }

    public Integer getMaxSort() {
        LambdaQueryWrapper<MdmRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(MdmRole::getSort);
        queryWrapper.last("limit 1");
        MdmRole mdmRole = mdmRoleMapper.selectOne(queryWrapper);
        if(null  == mdmRole) {
            return 0;
        }
        return mdmRole.getSort();
    }

    @Override
    @Transactional
    public boolean setRolePostUserRel(MdmRolePostUserDto mdmRolePostUserDto) {
        MdmRole mdmRole = new MdmRole();
        mdmRole.setId(mdmRolePostUserDto.getRoleId());
        //删除角色人员关系
        mdmRoleUserService.deleteByRoleId(mdmRolePostUserDto.getRoleId());
        mdmRolePostService.deleteByRoleId(mdmRolePostUserDto.getRoleId());
        //neoService.delRolePostRel(mdmRole);
        //neoService.delRoleUserRel(mdmRole);
        //按照人员ids 查询人员的相关信息
        List<String> userIdsList = Stream.of(mdmRolePostUserDto.getUserIds().split(",")).collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(userIdsList)) {
            List<MdmUser> userList = mdmUserService.getByIds(userIdsList);
            List<MdmRoleUser> resultRoleUsers = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(userList)) {
                for (MdmUser mdmUser : userList) {
                    MdmRoleUser mdmRoleUser =  new MdmRoleUser();
                    mdmRoleUser.setRoleCode(mdmRolePostUserDto.getRoleCode());
                    mdmRoleUser.setRoleId(mdmRolePostUserDto.getRoleId());
                    mdmRoleUser.setRoleName(mdmRolePostUserDto.getRoleName());
                    mdmRoleUser.setUserId(mdmUser.getId());
                    mdmRoleUser.setUserName(mdmUser.getUserName());
                    mdmRoleUser.setUserNameCh(mdmUser.getUserNameCh());
                    resultRoleUsers.add(mdmRoleUser);
                }
            }
            if(CollectionUtil.isNotEmpty(resultRoleUsers)) {
                mdmRoleUserService.saveBatch(resultRoleUsers);
                //neo4j
                //neoService.mergeRoleUserRel(mdmRole,resultRoleUsers);
            }
        }

        //按照postIds 查询岗位相关信息
        List<String> postIdsList = Stream.of(mdmRolePostUserDto.getPostIds().split(",")).collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(postIdsList)) {
            List<MdmPost> postList = mdmPostService.getByIds(postIdsList);
            List<MdmRolePost> resultRolePosts = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(postList)) {
                for (MdmPost mdmPost : postList) {
                    MdmRolePost mdmRolePost = new MdmRolePost();
                    mdmRolePost.setPostCode(mdmPost.getPostCode());
                    mdmRolePost.setPostId(mdmPost.getId());
                    mdmRolePost.setPostName(mdmPost.getPostName());
                    mdmRolePost.setRoleCode(mdmRolePostUserDto.getRoleCode());
                    mdmRolePost.setRoleId(mdmRolePostUserDto.getRoleId());
                    mdmRolePost.setRoleName(mdmRolePostUserDto.getRoleName());
                    resultRolePosts.add(mdmRolePost);
                }
            }
            if(CollectionUtil.isNotEmpty(resultRolePosts)) {
                mdmRolePostService.saveBatch(resultRolePosts);
                //neo4j
                //neoService.mergeRolePostRel(mdmRole,resultRolePosts);
            }
        }
        log.info("角色、岗位、人员关系保存成功！");
        return true;
    }

    @Override
    public MdmRoleVo getRolePostUserRel(Long roleId) {
        if(null == roleId || 0L == roleId) {
            throw new IncloudException("角色id不能为空！");
        }
        //查询角色信息
        MdmRole mdmRole = super.getById(roleId);
        if(null == mdmRole) {
            throw new IncloudException("不存在该角色信息！");
        }
        MdmRoleVo mdmRoleVo = dozerMapper.map(mdmRole,MdmRoleVo.class);
        //查询角色岗位信息
        List<MdmRolePostVo> rolePosts = mdmRolePostService.getRolePostByRoleId(roleId);
        mdmRoleVo.setRolePosts(rolePosts);
        //查询角色人员信息
        List<MdmRoleUserVo> roleUsers =  mdmRoleUserService.getRoleUserByRoleId(roleId);
        mdmRoleVo.setRoleUsers(roleUsers);
        return mdmRoleVo;
    }

    @Override
    public MdmRoleVo getByCode(String code) {
        if(StringUtils.isBlank(code)) {
            throw new IncloudException("角色code不能为空！");
        }
        LambdaQueryWrapper<MdmRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRole::getRoleCode,code);
        MdmRole mdmRole = mdmRoleMapper.selectOne(queryWrapper);
        MdmRoleVo mdmRoleVo = null;
        if(mdmRole !=null){
            mdmRoleVo = dozerMapper.map(mdmRole,MdmRoleVo.class);
        }
        log.debug("查询成功");
        return mdmRoleVo;
    }
}
