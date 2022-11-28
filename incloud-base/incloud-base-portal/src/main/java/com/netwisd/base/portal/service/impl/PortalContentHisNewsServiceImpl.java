package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentHisNews;
import com.netwisd.base.portal.mapper.PortalContentHisNewsMapper;
import com.netwisd.base.portal.service.PortalContentHisNewsService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentHisNewsDto;
import com.netwisd.base.portal.vo.PortalContentHisNewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 历史 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-29 19:26:33
 */
@Service
@Slf4j
public class PortalContentHisNewsServiceImpl extends ServiceImpl<PortalContentHisNewsMapper, PortalContentHisNews> implements PortalContentHisNewsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentHisNewsMapper portalContentHisNewsMapper;

    /**
    * 单表简单查询操作
    * @param portalContentHisNewsDto
    * @return
    */
    @Override
    public Page list(PortalContentHisNewsDto portalContentHisNewsDto) {
        LambdaQueryWrapper<PortalContentHisNews> queryWrapper = new LambdaQueryWrapper<>();
        Page<PortalContentHisNews> page = portalContentHisNewsMapper.selectPage(portalContentHisNewsDto.getPage(),queryWrapper);
        Page<PortalContentHisNewsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentHisNewsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentHisNewsDto
    * @return
    */
    @Override
    public Page lists(PortalContentHisNewsDto portalContentHisNewsDto) {
        Page<PortalContentHisNewsVo> pageVo = portalContentHisNewsMapper.getPageList(portalContentHisNewsDto.getPage(),portalContentHisNewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentHisNewsVo get(Long id) {
        PortalContentHisNews portalContentHisNews = super.getById(id);
        PortalContentHisNewsVo portalContentHisNewsVo = null;
        if(portalContentHisNews !=null){
            portalContentHisNewsVo = dozerMapper.map(portalContentHisNews,PortalContentHisNewsVo.class);
        }
        log.debug("查询成功");
        return portalContentHisNewsVo;
    }

    /**
    * 保存实体
    * @param portalContentHisNewsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentHisNewsDto portalContentHisNewsDto) {
        PortalContentHisNews portalContentHisNews = dozerMapper.map(portalContentHisNewsDto,PortalContentHisNews.class);
        boolean result = super.save(portalContentHisNews);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentHisNewsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentHisNewsDto portalContentHisNewsDto) {
        portalContentHisNewsDto.setUpdateTime(LocalDateTime.now());
        PortalContentHisNews portalContentHisNews = dozerMapper.map(portalContentHisNewsDto,PortalContentHisNews.class);
        boolean result = super.updateById(portalContentHisNews);
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
