package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentHisSysjointsSon;
import com.netwisd.base.portal.mapper.PortalContentHisSysjointsSonMapper;
import com.netwisd.base.portal.service.PortalContentHisSysjointsSonService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentHisSysjointsSonDto;
import com.netwisd.base.portal.vo.PortalContentHisSysjointsSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 历史 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:41:03
 */
@Service
@Slf4j
public class PortalContentHisSysjointsSonServiceImpl extends ServiceImpl<PortalContentHisSysjointsSonMapper, PortalContentHisSysjointsSon> implements PortalContentHisSysjointsSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentHisSysjointsSonMapper portalContentHisSysjointsSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentHisSysjointsSonDto
    * @return
    */
    @Override
    public Page list(PortalContentHisSysjointsSonDto portalContentHisSysjointsSonDto) {
        LambdaQueryWrapper<PortalContentHisSysjointsSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentHisSysjointsSon> page = portalContentHisSysjointsSonMapper.selectPage(portalContentHisSysjointsSonDto.getPage(),queryWrapper);
        Page<PortalContentHisSysjointsSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentHisSysjointsSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentHisSysjointsSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentHisSysjointsSonDto portalContentHisSysjointsSonDto) {
        Page<PortalContentHisSysjointsSonVo> pageVo = portalContentHisSysjointsSonMapper.getPageList(portalContentHisSysjointsSonDto.getPage(),portalContentHisSysjointsSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentHisSysjointsSonVo get(Long id) {
        PortalContentHisSysjointsSon portalContentHisSysjointsSon = super.getById(id);
        PortalContentHisSysjointsSonVo portalContentHisSysjointsSonVo = null;
        if(portalContentHisSysjointsSon !=null){
            portalContentHisSysjointsSonVo = dozerMapper.map(portalContentHisSysjointsSon,PortalContentHisSysjointsSonVo.class);
        }
        log.debug("查询成功");
        return portalContentHisSysjointsSonVo;
    }

    /**
    * 保存实体
    * @param portalContentHisSysjointsSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentHisSysjointsSonDto portalContentHisSysjointsSonDto) {
        PortalContentHisSysjointsSon portalContentHisSysjointsSon = dozerMapper.map(portalContentHisSysjointsSonDto,PortalContentHisSysjointsSon.class);
        boolean result = super.save(portalContentHisSysjointsSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentHisSysjointsSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentHisSysjointsSonDto portalContentHisSysjointsSonDto) {
        portalContentHisSysjointsSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentHisSysjointsSon portalContentHisSysjointsSon = dozerMapper.map(portalContentHisSysjointsSonDto,PortalContentHisSysjointsSon.class);
        boolean result = super.updateById(portalContentHisSysjointsSon);
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
