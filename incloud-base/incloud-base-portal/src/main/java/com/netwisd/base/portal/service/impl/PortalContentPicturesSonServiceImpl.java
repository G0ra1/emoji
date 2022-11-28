package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentPicturesSon;
import com.netwisd.base.portal.mapper.PortalContentPicturesSonMapper;
import com.netwisd.base.portal.service.PortalContentPicturesSonService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentPicturesSonDto;
import com.netwisd.base.portal.vo.PortalContentPicturesSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 08:55:53
 */
@Service
@Slf4j
public class PortalContentPicturesSonServiceImpl extends ServiceImpl<PortalContentPicturesSonMapper, PortalContentPicturesSon> implements PortalContentPicturesSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentPicturesSonMapper portalContentPicturesSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentPicturesSonDto
    * @return
    */
    @Override
    public Page list(PortalContentPicturesSonDto portalContentPicturesSonDto) {
        LambdaQueryWrapper<PortalContentPicturesSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentPicturesSon> page = portalContentPicturesSonMapper.selectPage(portalContentPicturesSonDto.getPage(),queryWrapper);
        Page<PortalContentPicturesSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentPicturesSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentPicturesSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentPicturesSonDto portalContentPicturesSonDto) {
        Page<PortalContentPicturesSonVo> pageVo = portalContentPicturesSonMapper.getPageList(portalContentPicturesSonDto.getPage(),portalContentPicturesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentPicturesSonVo get(Long id) {
        PortalContentPicturesSon portalContentPicturesSon = super.getById(id);
        PortalContentPicturesSonVo portalContentPicturesSonVo = null;
        if(portalContentPicturesSon !=null){
            portalContentPicturesSonVo = dozerMapper.map(portalContentPicturesSon,PortalContentPicturesSonVo.class);
        }
        log.debug("查询成功");
        return portalContentPicturesSonVo;
    }

    /**
    * 保存实体
    * @param portalContentPicturesSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentPicturesSonDto portalContentPicturesSonDto) {
        PortalContentPicturesSon portalContentPicturesSon = dozerMapper.map(portalContentPicturesSonDto,PortalContentPicturesSon.class);
        boolean result = super.save(portalContentPicturesSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentPicturesSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentPicturesSonDto portalContentPicturesSonDto) {
        portalContentPicturesSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentPicturesSon portalContentPicturesSon = dozerMapper.map(portalContentPicturesSonDto,PortalContentPicturesSon.class);
        boolean result = super.updateById(portalContentPicturesSon);
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
