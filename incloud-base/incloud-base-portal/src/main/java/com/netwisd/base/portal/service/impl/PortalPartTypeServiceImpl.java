package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalPartType;
import com.netwisd.base.portal.mapper.PortalPartTypeMapper;
import com.netwisd.base.portal.service.PortalPartTypeService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalPartTypeDto;
import com.netwisd.base.portal.vo.PortalPartTypeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 栏目类型字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-12 17:20:18
 */
@Service
@Slf4j
public class PortalPartTypeServiceImpl extends ServiceImpl<PortalPartTypeMapper, PortalPartType> implements PortalPartTypeService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalPartTypeMapper portalPartTypeMapper;

    /**
    * 单表简单查询操作
    * @param portalPartTypeDto
    * @return
    */
    @Override
    public Page list(PortalPartTypeDto portalPartTypeDto) {
        LambdaQueryWrapper<PortalPartType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(PortalPartType::getPartTypeName,portalPartTypeDto.getPartTypeName());
        Page<PortalPartType> page = portalPartTypeMapper.selectPage(portalPartTypeDto.getPage(),queryWrapper);
        Page<PortalPartTypeVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalPartTypeVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 不分页集合查询
    * @param
    * @return
    */
    @Override
    public List<PortalPartTypeVo> lists() {
        List<PortalPartType> portalPartTypes = portalPartTypeMapper.selectList(null);
        List<PortalPartTypeVo> portalPartTypeVos = DozerUtils.mapList(dozerMapper, portalPartTypes, PortalPartTypeVo.class);
        log.debug("查询条数:"+portalPartTypes.size());
        return portalPartTypeVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalPartTypeVo get(Long id) {
        PortalPartType portalPartType = super.getById(id);
        PortalPartTypeVo portalPartTypeVo = null;
        if(portalPartType !=null){
            portalPartTypeVo = dozerMapper.map(portalPartType,PortalPartTypeVo.class);
        }
        log.debug("查询成功");
        return portalPartTypeVo;
    }

    /**
    * 保存实体
    * @param portalPartTypeDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalPartTypeDto portalPartTypeDto) {
        PortalPartType portalPartType = dozerMapper.map(portalPartTypeDto,PortalPartType.class);
        boolean result = super.save(portalPartType);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalPartTypeDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalPartTypeDto portalPartTypeDto) {
        PortalPartType portalPartType = dozerMapper.map(portalPartTypeDto,PortalPartType.class);
        portalPartType.setUpdateTime(LocalDateTime.now());
        boolean result = super.updateById(portalPartType);
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
}
