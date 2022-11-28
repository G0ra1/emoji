package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentAdjSysjointsSon;
import com.netwisd.base.portal.mapper.PortalContentAdjSysjointsSonMapper;
import com.netwisd.base.portal.service.PortalContentAdjSysjointsSonService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentAdjSysjointsSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjSysjointsSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 调整 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:39:52
 */
@Service
@Slf4j
public class PortalContentAdjSysjointsSonServiceImpl extends ServiceImpl<PortalContentAdjSysjointsSonMapper, PortalContentAdjSysjointsSon> implements PortalContentAdjSysjointsSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentAdjSysjointsSonMapper portalContentAdjSysjointsSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentAdjSysjointsSonDto
    * @return
    */
    @Override
    public Page list(PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto) {
        LambdaQueryWrapper<PortalContentAdjSysjointsSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentAdjSysjointsSon> page = portalContentAdjSysjointsSonMapper.selectPage(portalContentAdjSysjointsSonDto.getPage(),queryWrapper);
        Page<PortalContentAdjSysjointsSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjSysjointsSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentAdjSysjointsSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto) {
        Page<PortalContentAdjSysjointsSonVo> pageVo = portalContentAdjSysjointsSonMapper.getPageList(portalContentAdjSysjointsSonDto.getPage(),portalContentAdjSysjointsSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentAdjSysjointsSonVo get(Long id) {
        PortalContentAdjSysjointsSon portalContentAdjSysjointsSon = super.getById(id);
        PortalContentAdjSysjointsSonVo portalContentAdjSysjointsSonVo = null;
        if(portalContentAdjSysjointsSon !=null){
            portalContentAdjSysjointsSonVo = dozerMapper.map(portalContentAdjSysjointsSon,PortalContentAdjSysjointsSonVo.class);
        }
        log.debug("查询成功");
        return portalContentAdjSysjointsSonVo;
    }

    /**
    * 保存实体
    * @param portalContentAdjSysjointsSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto) {
        PortalContentAdjSysjointsSon portalContentAdjSysjointsSon = dozerMapper.map(portalContentAdjSysjointsSonDto,PortalContentAdjSysjointsSon.class);
        boolean result = super.save(portalContentAdjSysjointsSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentAdjSysjointsSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto) {
        portalContentAdjSysjointsSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentAdjSysjointsSon portalContentAdjSysjointsSon = dozerMapper.map(portalContentAdjSysjointsSonDto,PortalContentAdjSysjointsSon.class);
        boolean result = super.updateById(portalContentAdjSysjointsSon);
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
