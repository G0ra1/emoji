package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentSysjointsSon;
import com.netwisd.base.portal.mapper.PortalContentSysjointsSonMapper;
import com.netwisd.base.portal.service.PortalContentSysjointsSonService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentSysjointsSonDto;
import com.netwisd.base.portal.vo.PortalContentSysjointsSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 10:19:41
 */
@Service
@Slf4j
public class PortalContentSysjointsSonServiceImpl extends ServiceImpl<PortalContentSysjointsSonMapper, PortalContentSysjointsSon> implements PortalContentSysjointsSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentSysjointsSonMapper portalContentSysjointsSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentSysjointsSonDto
    * @return
    */
    @Override
    public Page list(PortalContentSysjointsSonDto portalContentSysjointsSonDto) {
        LambdaQueryWrapper<PortalContentSysjointsSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentSysjointsSon> page = portalContentSysjointsSonMapper.selectPage(portalContentSysjointsSonDto.getPage(),queryWrapper);
        Page<PortalContentSysjointsSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentSysjointsSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentSysjointsSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentSysjointsSonDto portalContentSysjointsSonDto) {
        Page<PortalContentSysjointsSonVo> pageVo = portalContentSysjointsSonMapper.getPageList(portalContentSysjointsSonDto.getPage(),portalContentSysjointsSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentSysjointsSonVo get(Long id) {
        PortalContentSysjointsSon portalContentSysjointsSon = super.getById(id);
        PortalContentSysjointsSonVo portalContentSysjointsSonVo = null;
        if(portalContentSysjointsSon !=null){
            portalContentSysjointsSonVo = dozerMapper.map(portalContentSysjointsSon,PortalContentSysjointsSonVo.class);
        }
        log.debug("查询成功");
        return portalContentSysjointsSonVo;
    }

    /**
    * 保存实体
    * @param portalContentSysjointsSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentSysjointsSonDto portalContentSysjointsSonDto) {
        PortalContentSysjointsSon portalContentSysjointsSon = dozerMapper.map(portalContentSysjointsSonDto,PortalContentSysjointsSon.class);
        boolean result = super.save(portalContentSysjointsSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentSysjointsSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentSysjointsSonDto portalContentSysjointsSonDto) {
        portalContentSysjointsSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentSysjointsSon portalContentSysjointsSon = dozerMapper.map(portalContentSysjointsSonDto,PortalContentSysjointsSon.class);
        boolean result = super.updateById(portalContentSysjointsSon);
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
