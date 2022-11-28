package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentHisPictures;
import com.netwisd.base.portal.mapper.PortalContentHisPicturesMapper;
import com.netwisd.base.portal.service.PortalContentHisPicturesService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentHisPicturesDto;
import com.netwisd.base.portal.vo.PortalContentHisPicturesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 历史 图片轮播类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:14:36
 */
@Service
@Slf4j
public class PortalContentHisPicturesServiceImpl extends ServiceImpl<PortalContentHisPicturesMapper, PortalContentHisPictures> implements PortalContentHisPicturesService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentHisPicturesMapper portalContentHisPicturesMapper;

    /**
    * 单表简单查询操作
    * @param portalContentHisPicturesDto
    * @return
    */
    @Override
    public Page list(PortalContentHisPicturesDto portalContentHisPicturesDto) {
        LambdaQueryWrapper<PortalContentHisPictures> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentHisPictures> page = portalContentHisPicturesMapper.selectPage(portalContentHisPicturesDto.getPage(),queryWrapper);
        Page<PortalContentHisPicturesVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentHisPicturesVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentHisPicturesDto
    * @return
    */
    @Override
    public Page lists(PortalContentHisPicturesDto portalContentHisPicturesDto) {
        Page<PortalContentHisPicturesVo> pageVo = portalContentHisPicturesMapper.getPageList(portalContentHisPicturesDto.getPage(),portalContentHisPicturesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentHisPicturesVo get(Long id) {
        PortalContentHisPictures portalContentHisPictures = super.getById(id);
        PortalContentHisPicturesVo portalContentHisPicturesVo = null;
        if(portalContentHisPictures !=null){
            portalContentHisPicturesVo = dozerMapper.map(portalContentHisPictures,PortalContentHisPicturesVo.class);
        }
        log.debug("查询成功");
        return portalContentHisPicturesVo;
    }

    /**
    * 保存实体
    * @param portalContentHisPicturesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentHisPicturesDto portalContentHisPicturesDto) {
        PortalContentHisPictures portalContentHisPictures = dozerMapper.map(portalContentHisPicturesDto,PortalContentHisPictures.class);
        boolean result = super.save(portalContentHisPictures);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentHisPicturesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentHisPicturesDto portalContentHisPicturesDto) {
        portalContentHisPicturesDto.setUpdateTime(LocalDateTime.now());
        PortalContentHisPictures portalContentHisPictures = dozerMapper.map(portalContentHisPicturesDto,PortalContentHisPictures.class);
        boolean result = super.updateById(portalContentHisPictures);
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
