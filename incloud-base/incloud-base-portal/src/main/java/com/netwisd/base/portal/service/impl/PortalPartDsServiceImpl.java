package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalPartDs;
import com.netwisd.base.portal.mapper.PortalPartDsMapper;
import com.netwisd.base.portal.service.PortalPartDsService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalPartDsDto;
import com.netwisd.base.portal.vo.PortalPartDsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @Description 栏目管理数据源 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-13 20:07:37
 */
@Service
@Slf4j
public class PortalPartDsServiceImpl extends ServiceImpl<PortalPartDsMapper, PortalPartDs> implements PortalPartDsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalPartDsMapper portalPartDsMapper;

    /**
    * 单表简单查询操作
    * @param portalPartDsDto
    * @return
    */
    @Override
    public Page list(PortalPartDsDto portalPartDsDto) {
        PortalPartDs portalPartDs = dozerMapper.map(portalPartDsDto,PortalPartDs.class);
        LambdaQueryWrapper<PortalPartDs> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(portalPartDs);
        Page<PortalPartDs> page = portalPartDsMapper.selectPage(portalPartDsDto.getPage(),queryWrapper);
        Page<PortalPartDsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalPartDsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalPartDsDto
    * @return
    */
    @Override
    public Page lists(PortalPartDsDto portalPartDsDto) {
        Page<PortalPartDsVo> pageVo = portalPartDsMapper.getPageList(portalPartDsDto.getPage(),portalPartDsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalPartDsVo get(Long id) {
        PortalPartDs portalPartDs = super.getById(id);
        PortalPartDsVo portalPartDsVo = null;
        if(portalPartDs !=null){
            portalPartDsVo = dozerMapper.map(portalPartDs,PortalPartDsVo.class);
        }
        log.debug("查询成功");
        return portalPartDsVo;
    }

    /**
    * 保存实体
    * @param portalPartDsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalPartDsDto portalPartDsDto) {
        PortalPartDs portalPartDs = dozerMapper.map(portalPartDsDto,PortalPartDs.class);
        boolean result = super.save(portalPartDs);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalPartDsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalPartDsDto portalPartDsDto) {
        PortalPartDs portalPartDs = dozerMapper.map(portalPartDsDto,PortalPartDs.class);
        //更新修改时间
        portalPartDs.setUpdateTime(LocalDateTime.now());
        boolean result = super.updateById(portalPartDs);
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
