package com.netwisd.base.portal.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.portal.config.PortalPublisher;
import com.netwisd.base.portal.entity.*;
import com.netwisd.base.portal.mapper.PortalContentVideosSonMapper;
import com.netwisd.base.portal.service.PortalContentVideosSonService;
import com.netwisd.base.portal.vo.PortalContentPicturesSonVo;
import com.netwisd.base.portal.vo.PortalContentVideosSonVo;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.portal.mapper.PortalContentVideosMapper;
import com.netwisd.base.portal.service.PortalContentVideosService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentVideosDto;
import com.netwisd.base.portal.vo.PortalContentVideosVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @Description 视频类内容发布 功能描述...
 * @date 2021-08-23 14:08:28
 */
@Service
@Slf4j
public class PortalContentVideosServiceImpl extends ServiceImpl<PortalContentVideosMapper, PortalContentVideos> implements PortalContentVideosService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfService wfService;

    @Autowired
    PortalPublisher portalPublisher;

    @Autowired
    private PortalContentVideosMapper portalContentVideosMapper;

    @Autowired
    private PortalContentVideosSonMapper portalContentVideosSonMapper;
    @Autowired
    private PortalContentVideosSonService portalContentVideosSonService;

    /**
     * 单表简单查询操作
     *
     * @param videosDto
     * @return
     */
    @Override
    public Page list(PortalContentVideosDto videosDto) {
        log.info("视频类内容发布list查询参数：" + videosDto.toString());
        LambdaQueryWrapper<PortalContentVideos> videosWrapper = new LambdaQueryWrapper<>();
        videosWrapper.eq(ObjectUtils.isNotEmpty(videosDto.getPortalId()),PortalContentVideos::getPortalId,videosDto.getPortalId());
        videosWrapper.eq(StringUtils.isNotBlank(videosDto.getPortalName()),PortalContentVideos::getPortalName,videosDto.getPortalName());
        videosWrapper.eq(ObjectUtils.isNotEmpty(videosDto.getPartId()),PortalContentVideos::getPartId,videosDto.getPartId());
        videosWrapper.eq(StringUtils.isNotBlank(videosDto.getPartName()),PortalContentVideos::getPartName,videosDto.getPartName());
        videosWrapper.eq(ObjectUtils.isNotEmpty(videosDto.getPartTypeId()),PortalContentVideos::getPartTypeId,videosDto.getPartTypeId());
        videosWrapper.eq(StringUtils.isNotBlank(videosDto.getPartTypeName()),PortalContentVideos::getPartTypeName,videosDto.getPartTypeName());
        videosWrapper.orderByDesc(PortalContentVideos::getCreateTime);
        Page page = portalContentVideosMapper.selectPage(videosDto.getPage(), videosWrapper);
        Page pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentVideosVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        List<PortalContentVideosVo> records = pageVo.getRecords();
        if (ObjectUtils.isNotEmpty(records)) {
            records.forEach(d->{
                List<PortalContentVideosSon> videosSonList = getVideosSon(d.getId());
                if (ObjectUtils.isNotEmpty(videosSonList)) {
                    List<PortalContentVideosSonVo> videosSonVoList = DozerUtils.mapList(dozerMapper, videosSonList, PortalContentVideosSonVo.class);
                    d.setPortalContentVideosSonList(videosSonVoList);
                }
            });
            pageVo.setRecords(records);
        }

        return pageVo;
    }

    /**
     * 自定义查询操作
     *
     * @param portalContentVideosDto
     * @return
     */
    @Override
    public List<PortalContentVideosVo> lists(PortalContentVideosDto portalContentVideosDto) {
        LambdaQueryWrapper<PortalContentVideos> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentVideosDto.getPortalId()), PortalContentVideos::getPortalId, portalContentVideosDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentVideosDto.getPartId()), PortalContentVideos::getPartId, portalContentVideosDto.getPartId());

        List<PortalContentVideos> portalContentVideosList = super.list(queryWrapper);
        List<PortalContentVideosVo> portalContentVideosVos = DozerUtils.mapList(dozerMapper, portalContentVideosList, PortalContentVideosVo.class);
        log.debug("查询条数:" + portalContentVideosVos.size());
        return portalContentVideosVos;
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public PortalContentVideosVo get(Long id) {
        log.info("视频类内容-查详情参数："+id);
        PortalContentVideos portalContentVideos = super.getById(id);
        PortalContentVideosVo portalContentVideosVo = null;
        if (portalContentVideos != null) {
            portalContentVideosVo = dozerMapper.map(portalContentVideos, PortalContentVideosVo.class);
            log.info("主表数据："+portalContentVideosVo.toString());
        }

        //获取子表信息
        List<PortalContentVideosSon> videosSon = this.getVideosSon(id);
        if (CollectionUtils.isNotEmpty(videosSon)) {
            List<PortalContentVideosSonVo> videoSonVos = DozerUtils.mapList(dozerMapper, videosSon, PortalContentVideosSonVo.class);
            portalContentVideosVo.setPortalContentVideosSonList(videoSonVos);
        }
        log.debug("查询成功");
        return portalContentVideosVo;
    }

    /**
     * 保存实体
     *
     * @param portalContentVideosDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(PortalContentVideosDto portalContentVideosDto) {
        log.info("视频类内容-新增参数："+portalContentVideosDto.toString());
        Boolean result = false;
        PortalContentVideos portalContentVideos = dozerMapper.map(portalContentVideosDto, PortalContentVideos.class);
        //保存子表
        List<PortalContentVideosSon> portalContentVideosSonList = null;
        if (CollectionUtils.isNotEmpty(portalContentVideosDto.getPortalContentVideosSonList())) {
            portalContentVideosSonList = portalContentVideosDto.getPortalContentVideosSonList();
            portalContentVideosSonList.forEach(d -> {
                //初始化子表的点击量为 0； 同时设置关联字段为主表id
                d.setHits(0L);
                d.setLinkVideosId(portalContentVideos.getId());
            });
            portalContentVideosSonService.saveBatch(portalContentVideosSonList);
        }
        /*boolean result = portalContentVideosMapper.save(portalContentVideos);*/
        int insert = portalContentVideosMapper.insert(portalContentVideos);
        if (insert > 0) {
            log.debug("保存成功");
            result = true;
        }
        return result;
    }

    /**
     * 修改实体
     *
     * @param portalContentVideosDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PortalContentVideosDto portalContentVideosDto) {
        portalContentVideosDto.setUpdateTime(LocalDateTime.now());
        PortalContentVideos portalContentVideos = dozerMapper.map(portalContentVideosDto, PortalContentVideos.class);
        boolean result = super.updateById(portalContentVideos);
        if (result) {
            log.debug("修改成功");
        }
        //修改子表
        List<PortalContentVideosSon> portalContentVideosSonList = portalContentVideosDto.getPortalContentVideosSonList();
        if (CollectionUtils.isNotEmpty(portalContentVideosSonList)) {
            //先删除
            LambdaQueryWrapper<PortalContentVideosSon> picturesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
            picturesSonLambdaQueryWrapper.eq(PortalContentVideosSon::getLinkVideosId, portalContentVideos.getId());
            portalContentVideosSonMapper.delete(picturesSonLambdaQueryWrapper);
            //再新增
            portalContentVideosSonList.forEach(d -> {
                d.setHits(0L);
                d.setLinkVideosId(portalContentVideosDto.getId());
            });
            portalContentVideosSonService.saveBatch(portalContentVideosSonList);
        }
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
        List<String> idsList = Arrays.asList(ids.split(","));
        if (idsList.size()<0){
            throw new IncloudException("请选择要删除的信息");
        }
        int i = portalContentVideosMapper.deleteBatchIds(idsList);
        if (i>0) {
            log.debug("主表删除成功");
            LambdaQueryWrapper<PortalContentVideosSon> picturesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
            picturesSonLambdaQueryWrapper.in(PortalContentVideosSon::getLinkVideosId, idsList);
            //主表删除成功后 删除子表信息
            int delete = portalContentVideosSonMapper.delete(picturesSonLambdaQueryWrapper);
            if (delete > 0) {
                log.debug("子表删除成功");
                return  true;
            }
        }
        return false;
    }

    @Override
    public Page listForShow(PortalContentVideosDto portalContentVideosDto) {
        LambdaQueryWrapper<PortalContentVideos> videosWrapper = new LambdaQueryWrapper<>();
        videosWrapper.eq(StringUtils.isNotBlank(portalContentVideosDto.getPartCode()), PortalContentVideos::getPartCode,portalContentVideosDto.getPartCode());
        videosWrapper.eq(ObjectUtils.isNotEmpty(portalContentVideosDto.getPortalId()), PortalContentVideos::getPortalId,portalContentVideosDto.getPortalId());
        List<PortalContentVideos> videosList = portalContentVideosMapper.selectList(videosWrapper);
        if (CollectionUtils.isNotEmpty(videosList)) {
            List<Long> videosIds = new ArrayList<>();
            videosList.forEach(videos->{
                videosIds.add(videos.getId());
            });
            LambdaQueryWrapper<PortalContentVideosSon> videosSonWrapper = new LambdaQueryWrapper<>();
            videosSonWrapper.like(StringUtils.isNotBlank(portalContentVideosDto.getTitle()),PortalContentVideosSon::getTitle,portalContentVideosDto.getTitle());
            videosSonWrapper.in(PortalContentVideosSon::getLinkVideosId,videosIds);
            videosSonWrapper.orderByDesc(PortalContentVideosSon::getCreateTime);
            Page<PortalContentVideosSon> page = portalContentVideosSonMapper.selectPage(portalContentVideosDto.getPage(), videosSonWrapper);
            Page<PortalContentVideosSonVo> videosSonVoPage = DozerUtils.mapPage(dozerMapper, page, PortalContentVideosSonVo.class);
            List<PortalContentVideosSonVo> videosSonVoList = videosSonVoPage.getRecords();
            if (CollectionUtils.isNotEmpty(videosSonVoList)) {
                videosSonVoList.forEach(videosSonVo->{
                    videosWrapper.clear();
                    videosWrapper.eq(PortalContentVideos::getId,videosSonVo.getLinkVideosId());
                    PortalContentVideos videos = portalContentVideosMapper.selectOne(videosWrapper);
                    videosSonVo.setPortalId(videos.getPortalId());
                    videosSonVo.setPortalName(videos.getPortalName());
                    videosSonVo.setPartId(videos.getPartId());
                    videosSonVo.setPartCode(videos.getPartCode());
                    videosSonVo.setPartName(videos.getPartName());
                    videosSonVo.setPartTypeId(videos.getPartTypeId());
                    videosSonVo.setPartTypeName(videos.getPartTypeName());
                });
                videosSonVoPage.setRecords(videosSonVoList);
            }
            return videosSonVoPage;
        }
        return null;
    }

    /**
     * 获取子表信息
     *
     * @param LinkVideosId
     * @return
     */
    public List<PortalContentVideosSon> getVideosSon(Long LinkVideosId) {
        LambdaQueryWrapper<PortalContentVideosSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentVideosSon::getLinkVideosId, LinkVideosId);
        return portalContentVideosSonMapper.selectList(queryWrapper);
    }
}
