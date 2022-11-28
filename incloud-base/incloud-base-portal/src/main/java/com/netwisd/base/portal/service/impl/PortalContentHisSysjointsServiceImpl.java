package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentHisSysjoints;
import com.netwisd.base.portal.mapper.PortalContentHisSysjointsMapper;
import com.netwisd.base.portal.service.PortalContentHisSysjointsService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentHisSysjointsDto;
import com.netwisd.base.portal.vo.PortalContentHisSysjointsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 历史 系统集成类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:40:29
 */
@Service
@Slf4j
public class PortalContentHisSysjointsServiceImpl extends ServiceImpl<PortalContentHisSysjointsMapper, PortalContentHisSysjoints> implements PortalContentHisSysjointsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentHisSysjointsMapper portalContentHisSysjointsMapper;

    /**
    * 单表简单查询操作
    * @param portalContentHisSysjointsDto
    * @return
    */
    @Override
    public Page list(PortalContentHisSysjointsDto portalContentHisSysjointsDto) {
        LambdaQueryWrapper<PortalContentHisSysjoints> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentHisSysjoints> page = portalContentHisSysjointsMapper.selectPage(portalContentHisSysjointsDto.getPage(),queryWrapper);
        Page<PortalContentHisSysjointsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentHisSysjointsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentHisSysjointsDto
    * @return
    */
    @Override
    public Page lists(PortalContentHisSysjointsDto portalContentHisSysjointsDto) {
        Page<PortalContentHisSysjointsVo> pageVo = portalContentHisSysjointsMapper.getPageList(portalContentHisSysjointsDto.getPage(),portalContentHisSysjointsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentHisSysjointsVo get(Long id) {
        PortalContentHisSysjoints portalContentHisSysjoints = super.getById(id);
        PortalContentHisSysjointsVo portalContentHisSysjointsVo = null;
        if(portalContentHisSysjoints !=null){
            portalContentHisSysjointsVo = dozerMapper.map(portalContentHisSysjoints,PortalContentHisSysjointsVo.class);
        }
        log.debug("查询成功");
        return portalContentHisSysjointsVo;
    }

    /**
    * 保存实体
    * @param portalContentHisSysjointsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentHisSysjointsDto portalContentHisSysjointsDto) {
        PortalContentHisSysjoints portalContentHisSysjoints = dozerMapper.map(portalContentHisSysjointsDto,PortalContentHisSysjoints.class);
        boolean result = super.save(portalContentHisSysjoints);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentHisSysjointsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentHisSysjointsDto portalContentHisSysjointsDto) {
        portalContentHisSysjointsDto.setUpdateTime(LocalDateTime.now());
        PortalContentHisSysjoints portalContentHisSysjoints = dozerMapper.map(portalContentHisSysjointsDto,PortalContentHisSysjoints.class);
        boolean result = super.updateById(portalContentHisSysjoints);
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
