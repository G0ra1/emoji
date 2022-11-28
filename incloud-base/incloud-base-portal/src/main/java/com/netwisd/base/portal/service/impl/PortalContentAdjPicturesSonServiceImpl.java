package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentAdjPicturesSon;
import com.netwisd.base.portal.mapper.PortalContentAdjPicturesSonMapper;
import com.netwisd.base.portal.service.PortalContentAdjPicturesSonService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentAdjPicturesSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjPicturesSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 调整 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:13:46
 */
@Service
@Slf4j
public class PortalContentAdjPicturesSonServiceImpl extends ServiceImpl<PortalContentAdjPicturesSonMapper, PortalContentAdjPicturesSon> implements PortalContentAdjPicturesSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentAdjPicturesSonMapper portalContentAdjPicturesSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentAdjPicturesSonDto
    * @return
    */
    @Override
    public Page list(PortalContentAdjPicturesSonDto portalContentAdjPicturesSonDto) {
        LambdaQueryWrapper<PortalContentAdjPicturesSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentAdjPicturesSon> page = portalContentAdjPicturesSonMapper.selectPage(portalContentAdjPicturesSonDto.getPage(),queryWrapper);
        Page<PortalContentAdjPicturesSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjPicturesSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentAdjPicturesSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentAdjPicturesSonDto portalContentAdjPicturesSonDto) {
        Page<PortalContentAdjPicturesSonVo> pageVo = portalContentAdjPicturesSonMapper.getPageList(portalContentAdjPicturesSonDto.getPage(),portalContentAdjPicturesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentAdjPicturesSonVo get(Long id) {
        PortalContentAdjPicturesSon portalContentAdjPicturesSon = super.getById(id);
        PortalContentAdjPicturesSonVo portalContentAdjPicturesSonVo = null;
        if(portalContentAdjPicturesSon !=null){
            portalContentAdjPicturesSonVo = dozerMapper.map(portalContentAdjPicturesSon,PortalContentAdjPicturesSonVo.class);
        }
        log.debug("查询成功");
        return portalContentAdjPicturesSonVo;
    }

    /**
    * 保存实体
    * @param portalContentAdjPicturesSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentAdjPicturesSonDto portalContentAdjPicturesSonDto) {
        PortalContentAdjPicturesSon portalContentAdjPicturesSon = dozerMapper.map(portalContentAdjPicturesSonDto,PortalContentAdjPicturesSon.class);
        boolean result = super.save(portalContentAdjPicturesSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentAdjPicturesSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentAdjPicturesSonDto portalContentAdjPicturesSonDto) {
        portalContentAdjPicturesSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentAdjPicturesSon portalContentAdjPicturesSon = dozerMapper.map(portalContentAdjPicturesSonDto,PortalContentAdjPicturesSon.class);
        boolean result = super.updateById(portalContentAdjPicturesSon);
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
