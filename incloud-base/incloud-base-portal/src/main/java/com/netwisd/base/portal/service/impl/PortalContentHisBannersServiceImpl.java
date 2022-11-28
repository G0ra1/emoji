package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.entity.PortalContentBanners;
import com.netwisd.base.portal.entity.PortalContentHisBanners;
import com.netwisd.base.portal.mapper.PortalContentHisBannersMapper;
import com.netwisd.base.portal.service.PortalContentHisBannersService;
import com.netwisd.base.portal.vo.PortalContentBannersVo;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentHisBannersDto;
import com.netwisd.base.portal.vo.PortalContentHisBannersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description banner类内容-历史表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-30 03:09:59
 */
@Service
@Slf4j
public class PortalContentHisBannersServiceImpl extends ServiceImpl<PortalContentHisBannersMapper, PortalContentHisBanners> implements PortalContentHisBannersService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentHisBannersMapper portalContentHisBannersMapper;

    /**
    * 单表简单查询操作
    * @param portalContentHisBannersDto
    * @return
    */
    @Override
    public Page list(PortalContentHisBannersDto portalContentHisBannersDto) {

        LambdaQueryWrapper<PortalContentHisBanners> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.like(StringUtils.isNotBlank(portalContentHisBannersDto.getTitle()), PortalContentHisBanners::getTitle, portalContentHisBannersDto.getTitle());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentHisBannersDto.getPortalId()), PortalContentHisBanners::getPortalId, portalContentHisBannersDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentHisBannersDto.getPartId()), PortalContentHisBanners::getPartId, portalContentHisBannersDto.getPartId());

        Page<PortalContentHisBanners> page = portalContentHisBannersMapper.selectPage(portalContentHisBannersDto.getPage(),queryWrapper);
        Page<PortalContentHisBannersVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentHisBannersVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentHisBannersDto
    * @return
    */
    @Override
    public List<PortalContentHisBannersVo> lists(PortalContentHisBannersDto portalContentHisBannersDto) {
        log.info("首页banner轮播图内容管理lists参数：" + portalContentHisBannersDto.toString());
        LambdaQueryWrapper<PortalContentHisBanners> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(portalContentHisBannersDto.getTitle()), PortalContentHisBanners::getTitle, portalContentHisBannersDto.getTitle());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentHisBannersDto.getPortalId()), PortalContentHisBanners::getPortalId, portalContentHisBannersDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentHisBannersDto.getPartId()), PortalContentHisBanners::getPartId, portalContentHisBannersDto.getPartId());
          List<PortalContentHisBanners> portalContentHisBanners = super.list(queryWrapper);
        List<PortalContentHisBannersVo> portalContentHisBannersVoList = DozerUtils.mapList(dozerMapper, portalContentHisBanners,PortalContentHisBannersVo.class);

        log.debug("查询条数:"+portalContentHisBannersVoList.size());
        return portalContentHisBannersVoList;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentHisBannersVo get(Long id) {
        log.info("首页banner轮播图内容管理get参数：" + id);
        PortalContentHisBanners portalContentHisBanners = super.getById(id);
        PortalContentHisBannersVo portalContentHisBannersVo = null;
        if(portalContentHisBanners !=null){
            portalContentHisBannersVo = dozerMapper.map(portalContentHisBanners,PortalContentHisBannersVo.class);
        }
        log.debug("查询成功");
        return portalContentHisBannersVo;
    }

    /**
    * 保存实体
    * @param portalContentHisBannersDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentHisBannersDto portalContentHisBannersDto) {
        log.info("首页banner轮播图内容管理save参数：" + portalContentHisBannersDto.toString());
        PortalContentHisBanners portalContentHisBanners = dozerMapper.map(portalContentHisBannersDto,PortalContentHisBanners.class);
        boolean result = super.save(portalContentHisBanners);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentHisBannersDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentHisBannersDto portalContentHisBannersDto) {
        log.info("首页banner轮播图内容管理update参数：" + portalContentHisBannersDto.toString());
        portalContentHisBannersDto.setUpdateTime(LocalDateTime.now());
        PortalContentHisBanners portalContentHisBanners = dozerMapper.map(portalContentHisBannersDto,PortalContentHisBanners.class);
        boolean result = super.updateById(portalContentHisBanners);
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
        log.info("首页banner轮播图内容管理delete参数：" + id);
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }
}
