package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.wf.eunm.AuditStateEnum;
import com.netwisd.base.portal.config.PortalPublisher;
import com.netwisd.base.portal.constants.EventConstants;
import com.netwisd.base.portal.entity.PortalContentNews;
import com.netwisd.base.wf.starter.dto.BizInfoDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.portal.entity.PortalContentBanners;
import com.netwisd.base.portal.mapper.PortalContentBannersMapper;
import com.netwisd.base.portal.service.PortalContentBannersService;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentBannersDto;
import com.netwisd.base.portal.vo.PortalContentBannersVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.common.core.util.Result;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @Description banner类内容发布 功能描述...
 * @date 2021-08-19 15:32:39
 */
@Service
@Slf4j
public class PortalContentBannersServiceImpl extends ServiceImpl<PortalContentBannersMapper, PortalContentBanners> implements PortalContentBannersService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentBannersMapper portalContentBannersMapper;

    @Autowired
    private WfService wfService;

    @Autowired
    PortalPublisher portalPublisher;

    /**
     * 单表简单查询操作
     *
     * @param portalContentBannersDto
     * @return
     */
    @Override
    public Page list(PortalContentBannersDto portalContentBannersDto) {
        log.info("首页banner轮播图内容管理list参数：" + portalContentBannersDto.toString());
        LambdaQueryWrapper<PortalContentBanners> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.like(StringUtils.isNotBlank(portalContentBannersDto.getTitle()), PortalContentBanners::getTitle, portalContentBannersDto.getTitle());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentBannersDto.getPortalId()), PortalContentBanners::getPortalId, portalContentBannersDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentBannersDto.getPartId()), PortalContentBanners::getPartId, portalContentBannersDto.getPartId());
        queryWrapper.like(ObjectUtils.isNotEmpty(portalContentBannersDto.getPartCode()),PortalContentBanners::getPartCode,portalContentBannersDto.getPartCode());
        queryWrapper.orderByDesc(PortalContentBanners::getCreateTime);
        Page<PortalContentBanners> page = portalContentBannersMapper.selectPage(portalContentBannersDto.getPage(), queryWrapper);
        Page<PortalContentBannersVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentBannersVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     *
     * @param portalContentBannersDto
     * @return
     */
    @Override
    public List<PortalContentBannersVo> lists(PortalContentBannersDto portalContentBannersDto) {
        log.info("首页banner轮播图内容管理lists参数：" + portalContentBannersDto.toString());
        LambdaQueryWrapper<PortalContentBanners> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(StringUtils.isNotBlank(portalContentBannersDto.getTitle()), PortalContentBanners::getTitle, portalContentBannersDto.getTitle());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentBannersDto.getPortalId()), PortalContentBanners::getPortalId, portalContentBannersDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentBannersDto.getPartId()), PortalContentBanners::getPartId, portalContentBannersDto.getPartId());
        queryWrapper.like(ObjectUtils.isNotEmpty(portalContentBannersDto.getPartCode()),PortalContentBanners::getPartCode,portalContentBannersDto.getPartCode());
        queryWrapper.orderByDesc(PortalContentBanners::getCreateTime);
        List<PortalContentBanners> portalContentBannersList = super.list(queryWrapper);
        List<PortalContentBannersVo> portalContentBannersVos = DozerUtils.mapList(dozerMapper, portalContentBannersList, PortalContentBannersVo.class);
        log.debug("查询条数:" + portalContentBannersVos.size());
        return portalContentBannersVos;
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public PortalContentBannersVo get(Long id) {
        log.info("首页banner轮播图内容管理get参数：" + id);
        PortalContentBanners portalContentBanners = super.getById(id);
        PortalContentBannersVo portalContentBannersVo = null;
        if (portalContentBanners != null) {
            portalContentBannersVo = dozerMapper.map(portalContentBanners, PortalContentBannersVo.class);
        }
        log.debug("查询成功");
        return portalContentBannersVo;
    }

    /**
     * 保存实体
     *
     * @param portalContentBannersDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(PortalContentBannersDto portalContentBannersDto) {
        log.info("首页banner轮播图内容管理save参数：" + portalContentBannersDto.toString());
        PortalContentBanners portalContentBanners = dozerMapper.map(portalContentBannersDto, PortalContentBanners.class);
        portalContentBanners.setHits(0l);//给点击量赋值0
       /* portalContentBanners.setAuditStatus(AuditStateEnum.STARTING.getType());*/ // todo 取消流程--先注释掉
        boolean result = super.save(portalContentBanners);
        if (result) {
            log.debug("保存成功");
        }
        //生成静态文件
        Map<String,Object> eventData = new HashMap<>();
        eventData.put(EventConstants.BannerToHtmlFileHandle,portalContentBanners);
        portalPublisher.publish(eventData);
        return result;
    }

    /**
     * 修改实体
     *
     * @param portalContentBannersDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PortalContentBannersDto portalContentBannersDto) {
        log.info("首页banner轮播图内容管理update参数：" + portalContentBannersDto.toString());
        PortalContentBanners portalContentBanners = dozerMapper.map(portalContentBannersDto, PortalContentBanners.class);
        portalContentBanners.setUpdateTime(LocalDateTime.now());
        boolean result = super.updateById(portalContentBanners);
        if (result) {
            log.debug("修改成功");
        }
        //生成静态文件
        Map<String,Object> eventData = new HashMap<>();
        eventData.put(EventConstants.BannerToHtmlFileHandle,portalContentBanners);
        portalPublisher.publish(eventData);
        return result;
    }

    /**
     * 通过ID删除
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public Boolean delete(String ids) {
        log.info("首页banner轮播图内容管理delete参数：" + ids);
        List<String> idsList = Arrays.asList(ids.split(","));
        if (idsList.size()<0){
            throw new IncloudException("请选择要删除的信息");
        }
        int i = portalContentBannersMapper.deleteBatchIds(idsList);
        if (i>0){
            log.debug("删除成功");
            return true;
        }
        return false;
    }



    /**
     * 通过id修改点击量
     *
     * @param id
     * @return
     */

    public Boolean upHits(Long id) {
        log.info("首页banner轮播图内容管理upHits参数：" + id);
        LambdaQueryWrapper<PortalContentBanners> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), PortalContentBanners::getId, id);
        PortalContentBanners portalContentBanners = portalContentBannersMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(portalContentBanners)) {
            throw new IncloudException("未查询到相关数据");
        }
        Long hits = portalContentBanners.getHits();
        PortalContentBannersDto portalContentBannersDto = new PortalContentBannersDto();
        portalContentBannersDto.setId(id);
        portalContentBannersDto.setHits(hits + 1);
        Boolean update = this.update(portalContentBannersDto);
        return update;
    }
}
