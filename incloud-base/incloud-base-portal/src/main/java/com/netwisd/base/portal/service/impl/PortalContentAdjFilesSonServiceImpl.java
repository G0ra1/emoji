package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentAdjFilesSon;
import com.netwisd.base.portal.mapper.PortalContentAdjFilesSonMapper;
import com.netwisd.base.portal.service.PortalContentAdjFilesSonService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentAdjFilesSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjFilesSonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 调整 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:23:45
 */
@Service
@Slf4j
public class PortalContentAdjFilesSonServiceImpl extends ServiceImpl<PortalContentAdjFilesSonMapper, PortalContentAdjFilesSon> implements PortalContentAdjFilesSonService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentAdjFilesSonMapper portalContentAdjFilesSonMapper;

    /**
    * 单表简单查询操作
    * @param portalContentAdjFilesSonDto
    * @return
    */
    @Override
    public Page list(PortalContentAdjFilesSonDto portalContentAdjFilesSonDto) {
        LambdaQueryWrapper<PortalContentAdjFilesSon> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentAdjFilesSon> page = portalContentAdjFilesSonMapper.selectPage(portalContentAdjFilesSonDto.getPage(),queryWrapper);
        Page<PortalContentAdjFilesSonVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjFilesSonVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentAdjFilesSonDto
    * @return
    */
    @Override
    public Page lists(PortalContentAdjFilesSonDto portalContentAdjFilesSonDto) {
        Page<PortalContentAdjFilesSonVo> pageVo = portalContentAdjFilesSonMapper.getPageList(portalContentAdjFilesSonDto.getPage(),portalContentAdjFilesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentAdjFilesSonVo get(Long id) {
        PortalContentAdjFilesSon portalContentAdjFilesSon = super.getById(id);
        PortalContentAdjFilesSonVo portalContentAdjFilesSonVo = null;
        if(portalContentAdjFilesSon !=null){
            portalContentAdjFilesSonVo = dozerMapper.map(portalContentAdjFilesSon,PortalContentAdjFilesSonVo.class);
        }
        log.debug("查询成功");
        return portalContentAdjFilesSonVo;
    }

    /**
    * 保存实体
    * @param portalContentAdjFilesSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentAdjFilesSonDto portalContentAdjFilesSonDto) {
        PortalContentAdjFilesSon portalContentAdjFilesSon = dozerMapper.map(portalContentAdjFilesSonDto,PortalContentAdjFilesSon.class);
        boolean result = super.save(portalContentAdjFilesSon);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentAdjFilesSonDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentAdjFilesSonDto portalContentAdjFilesSonDto) {
        portalContentAdjFilesSonDto.setUpdateTime(LocalDateTime.now());
        PortalContentAdjFilesSon portalContentAdjFilesSon = dozerMapper.map(portalContentAdjFilesSonDto,PortalContentAdjFilesSon.class);
        boolean result = super.updateById(portalContentAdjFilesSon);
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
