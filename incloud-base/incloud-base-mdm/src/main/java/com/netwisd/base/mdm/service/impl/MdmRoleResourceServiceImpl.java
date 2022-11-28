package com.netwisd.base.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.mdm.entity.*;
import com.netwisd.base.mdm.mapper.MdmRoleResourceMapper;
import com.netwisd.base.mdm.service.MdmRoleResourceService;
import com.netwisd.base.mdm.service.NeoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.mdm.dto.MdmRoleResourceDto;
import com.netwisd.base.mdm.vo.MdmRoleResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 角色与资源关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-17 17:27:09
 */
@Service
@Slf4j
public class MdmRoleResourceServiceImpl extends ServiceImpl<MdmRoleResourceMapper, MdmRoleResource> implements MdmRoleResourceService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private MdmRoleResourceMapper mdmRoleResourceMapper;

    @Autowired
    private NeoService neoService;

    /**
    * 单表简单查询操作
    * @param mdmRoleResourceDto
    * @return
    */
    @Override
    public Page list(MdmRoleResourceDto mdmRoleResourceDto) {
        LambdaQueryWrapper<MdmRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<MdmRoleResource> page = mdmRoleResourceMapper.selectPage(mdmRoleResourceDto.getPage(),queryWrapper);
        Page<MdmRoleResourceVo> pageVo = DozerUtils.mapPage(dozerMapper, page, MdmRoleResourceVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param mdmRoleResourceDto
    * @return
    */
    @Override
    public Page lists(MdmRoleResourceDto mdmRoleResourceDto) {
        Page<MdmRoleResourceVo> pageVo = mdmRoleResourceMapper.getPageList(mdmRoleResourceDto.getPage(),mdmRoleResourceDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public MdmRoleResourceVo get(Long id) {
        MdmRoleResource mdmRoleResource = super.getById(id);
        MdmRoleResourceVo mdmRoleResourceVo = null;
        if(mdmRoleResource !=null){
            mdmRoleResourceVo = dozerMapper.map(mdmRoleResource,MdmRoleResourceVo.class);
        }
        log.debug("查询成功");
        return mdmRoleResourceVo;
    }

    /**
    * 保存实体
    * @param mdmRoleResourceDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(MdmRoleResourceDto mdmRoleResourceDto) {
        boolean result = false;
        this.deleteByRoleId(mdmRoleResourceDto.getRoleId());
        //neo4j 删除所有角色对资源的关系
        MdmRole mdmRole = new MdmRole();
        mdmRole.setId(mdmRoleResourceDto.getRoleId());
        //neoService.delRoleResRel(mdmRole);
        List<MdmRoleResource> mdmRoleResourceList = new ArrayList<>();
        if(StringUtils.isNotBlank(mdmRoleResourceDto.getResourceIds())) {
            List<String> strList = Stream.of(mdmRoleResourceDto.getResourceIds().split(",")).collect(Collectors.toList());
            for (String s : strList) {
                MdmRoleResource mdmRoleResource = new MdmRoleResource();
                mdmRoleResource.setId(IdGenerator.getIdGenerator());
                mdmRoleResource.setRoleId(mdmRoleResourceDto.getRoleId());
                mdmRoleResource.setRoleCode(mdmRoleResourceDto.getRoleCode());
                mdmRoleResource.setResourceId(Long.valueOf(s));
                mdmRoleResourceList.add(mdmRoleResource);
            }
        }
        if(CollectionUtil.isNotEmpty(mdmRoleResourceList)) {
            result = saveBatch(mdmRoleResourceList);
            //neoService.mergeRoleRestRel(mdmRole,mdmRoleResourceList);
            if(result){
                log.debug("保存成功");
            }
        }
        return result;
    }

    /**
    * 修改实体
    * @param mdmRoleResourceDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(MdmRoleResourceDto mdmRoleResourceDto) {
        mdmRoleResourceDto.setUpdateTime(LocalDateTime.now());
        MdmRoleResource mdmRoleResource = dozerMapper.map(mdmRoleResourceDto,MdmRoleResource.class);
        boolean result = super.updateById(mdmRoleResource);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean deleteByRoleId(Long roleId) {
        LambdaQueryWrapper<MdmRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRoleResource::getRoleId,roleId);
        return super.remove(queryWrapper);
    }

    @Override
    @Transactional
    public void deleteByResourceId(Long resourceId) {
        LambdaQueryWrapper<MdmRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRoleResource::getResourceId,resourceId);
        super.remove(queryWrapper);
    }

    @Override
    public List<MdmRoleResourceVo> getByRoleId(Long roleId) {
        LambdaQueryWrapper<MdmRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRoleResource::getRoleId,roleId);
        List<MdmRoleResource> mdmRoleResourceList = mdmRoleResourceMapper.selectList(queryWrapper);
        List<MdmRoleResourceVo> mdmRoleResourceVoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(mdmRoleResourceList)) {
            mdmRoleResourceVoList = DozerUtils.mapList(dozerMapper, mdmRoleResourceList, MdmRoleResourceVo.class);
        }
        return mdmRoleResourceVoList ;
    }

    @Override
    public List<MdmRoleResourceVo> getByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<MdmRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(MdmRoleResource::getRoleId,roleIds);
        List<MdmRoleResource> mdmRoleResourceList = mdmRoleResourceMapper.selectList(queryWrapper);
        List<MdmRoleResourceVo> mdmRoleResourceVoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(mdmRoleResourceList)) {
            mdmRoleResourceVoList = DozerUtils.mapList(dozerMapper, mdmRoleResourceList, MdmRoleResourceVo.class);
        }
        return mdmRoleResourceVoList ;
    }

    @Override
    public List<MdmRoleResourceVo> getByResourceId(Long resourceId) {
        LambdaQueryWrapper<MdmRoleResource> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MdmRoleResource::getResourceId,resourceId);
        List<MdmRoleResource> mdmRoleResourceList = mdmRoleResourceMapper.selectList(queryWrapper);
        List<MdmRoleResourceVo> mdmRoleResourceVoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(mdmRoleResourceList)) {
            mdmRoleResourceVoList = DozerUtils.mapList(dozerMapper, mdmRoleResourceList, MdmRoleResourceVo.class);
        }
        return mdmRoleResourceVoList ;
    }

    @Override
    @Transactional
    public void updateRoleResInfoByRoleId(MdmRole mdmRole) {
        LambdaUpdateWrapper<MdmRoleResource> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(MdmRoleResource::getRoleCode, mdmRole.getRoleCode());
        updateWrapper.eq(MdmRoleResource::getRoleId,mdmRole.getId());
        super.update(updateWrapper);
    }
}
