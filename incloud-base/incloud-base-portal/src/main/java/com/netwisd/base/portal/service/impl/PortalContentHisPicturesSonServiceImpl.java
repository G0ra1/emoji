package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentHisPicturesSon;
import com.netwisd.base.portal.mapper.PortalContentHisPicturesSonMapper;
import com.netwisd.base.portal.service.PortalContentHisPicturesSonService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentHisPicturesSonDto;
import com.netwisd.base.portal.vo.PortalContentHisPicturesSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 历史 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:15:02
 */
@Service
@Slf4j
public class PortalContentHisPicturesSonServiceImpl extends ServiceImpl<PortalContentHisPicturesSonMapper, PortalContentHisPicturesSon> implements PortalContentHisPicturesSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentHisPicturesSonMapper portalContentHisPicturesSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentHisPicturesSonDto
    * @return
    */
    @Override
    public Page list(PortalContentHisPicturesSonDto portalContentHisPicturesSonDto) {
        LambdaQueryWrapper<PortalContentHisPicturesSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentHisPicturesSon> page = portalContentHisPicturesSonMapper.selectPage(portalContentHisPicturesSonDto.getPage(),queryWrapper);
        Page<PortalContentHisPicturesSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentHisPicturesSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentHisPicturesSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentHisPicturesSonDto portalContentHisPicturesSonDto) {
        Page<PortalContentHisPicturesSonVo> pageVo = portalContentHisPicturesSonMapper.getPageList(portalContentHisPicturesSonDto.getPage(),portalContentHisPicturesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentHisPicturesSonVo get(Long id) {
        PortalContentHisPicturesSon portalContentHisPicturesSon = super.getById(id);
        PortalContentHisPicturesSonVo portalContentHisPicturesSonVo = null;
        if(portalContentHisPicturesSon !=null){
            portalContentHisPicturesSonVo = dozerMapper.map(portalContentHisPicturesSon,PortalContentHisPicturesSonVo.class);
        }
        log.debug("查询成功");
        return portalContentHisPicturesSonVo;
    }

    /**
    * 保存实体
    * @param portalContentHisPicturesSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentHisPicturesSonDto portalContentHisPicturesSonDto) {
        PortalContentHisPicturesSon portalContentHisPicturesSon = dozerMapper.map(portalContentHisPicturesSonDto,PortalContentHisPicturesSon.class);
        boolean result = super.save(portalContentHisPicturesSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentHisPicturesSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentHisPicturesSonDto portalContentHisPicturesSonDto) {
        portalContentHisPicturesSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentHisPicturesSon portalContentHisPicturesSon = dozerMapper.map(portalContentHisPicturesSonDto,PortalContentHisPicturesSon.class);
        boolean result = super.updateById(portalContentHisPicturesSon);
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
