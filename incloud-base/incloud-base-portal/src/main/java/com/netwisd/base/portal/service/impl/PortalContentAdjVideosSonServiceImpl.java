package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentAdjVideosSon;
import com.netwisd.base.portal.mapper.PortalContentAdjVideosSonMapper;
import com.netwisd.base.portal.service.PortalContentAdjVideosSonService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentAdjVideosSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjVideosSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description  视频类型内容发布-调整表子表   功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-06 16:15:52
 */
@Service
@Slf4j
public class PortalContentAdjVideosSonServiceImpl extends ServiceImpl<PortalContentAdjVideosSonMapper, PortalContentAdjVideosSon> implements PortalContentAdjVideosSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentAdjVideosSonMapper portalContentAdjVideosSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentAdjVideosSonDto
    * @return
    */
    @Override
    public Page list(PortalContentAdjVideosSonDto portalContentAdjVideosSonDto) {
        LambdaQueryWrapper<PortalContentAdjVideosSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentAdjVideosSon> page = portalContentAdjVideosSonMapper.selectPage(portalContentAdjVideosSonDto.getPage(),queryWrapper);
        Page<PortalContentAdjVideosSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjVideosSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentAdjVideosSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentAdjVideosSonDto portalContentAdjVideosSonDto) {
        Page<PortalContentAdjVideosSonVo> pageVo = portalContentAdjVideosSonMapper.getPageList(portalContentAdjVideosSonDto.getPage(),portalContentAdjVideosSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentAdjVideosSonVo get(Long id) {
        PortalContentAdjVideosSon portalContentAdjVideosSon = super.getById(id);
        PortalContentAdjVideosSonVo portalContentAdjVideosSonVo = null;
        if(portalContentAdjVideosSon !=null){
            portalContentAdjVideosSonVo = dozerMapper.map(portalContentAdjVideosSon,PortalContentAdjVideosSonVo.class);
        }
        log.debug("查询成功");
        return portalContentAdjVideosSonVo;
    }

    /**
    * 保存实体
    * @param portalContentAdjVideosSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentAdjVideosSonDto portalContentAdjVideosSonDto) {
        PortalContentAdjVideosSon portalContentAdjVideosSon = dozerMapper.map(portalContentAdjVideosSonDto,PortalContentAdjVideosSon.class);
        boolean result = super.save(portalContentAdjVideosSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentAdjVideosSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentAdjVideosSonDto portalContentAdjVideosSonDto) {
        portalContentAdjVideosSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentAdjVideosSon portalContentAdjVideosSon = dozerMapper.map(portalContentAdjVideosSonDto,PortalContentAdjVideosSon.class);
        boolean result = super.updateById(portalContentAdjVideosSon);
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
