package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.wf.eunm.AuditStateEnum;
import com.netwisd.base.portal.config.PortalPublisher;
import com.netwisd.base.portal.constants.EventConstants;
import com.netwisd.base.portal.dto.PortalContentPicturesSonDto;
import com.netwisd.base.portal.entity.*;
import com.netwisd.base.portal.mapper.PortalContentPicturesSonMapper;
import com.netwisd.base.portal.service.PortalContentPicturesSonService;
import com.netwisd.base.portal.vo.PortalContentPicturesSonVo;
import com.netwisd.base.wf.starter.dto.BizInfoDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.service.impl.WfProcServiceImpl;
import com.netwisd.base.portal.mapper.PortalContentPicturesMapper;
import com.netwisd.base.portal.service.PortalContentPicturesService;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentPicturesDto;
import com.netwisd.base.portal.vo.PortalContentPicturesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.common.core.util.Result;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description 图片轮播类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 08:55:04
 */
@Service
@Slf4j
public class PortalContentPicturesServiceImpl extends ServiceImpl<PortalContentPicturesMapper, PortalContentPictures> implements PortalContentPicturesService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentPicturesMapper portalContentPicturesMapper;

    @Autowired
    private PortalContentPicturesSonMapper picturesSonMapper;

    @Autowired
    private PortalContentPicturesSonService picturesSonService;

    @Autowired
    PortalPublisher portalPublisher;
    
//    @Autowired
//    private WfService wfService;

    /**
    * 单表简单查询操作
    * @param portalContentPicturesDto
    * @return
    */
    @Override
    public Page list(PortalContentPicturesDto portalContentPicturesDto) {
        LambdaQueryWrapper<PortalContentPictures> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentPicturesDto.getPortalId()), PortalContentPictures::getPortalId,portalContentPicturesDto.getPortalId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentPicturesDto.getPortalName()),PortalContentPictures::getPortalName,portalContentPicturesDto.getPortalName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentPicturesDto.getPartId()),PortalContentPictures::getPartId,portalContentPicturesDto.getPartId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentPicturesDto.getPartName()),PortalContentPictures::getPartName,portalContentPicturesDto.getPartName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentPicturesDto.getPartTypeId()),PortalContentPictures::getPartTypeId,portalContentPicturesDto.getPartTypeId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentPicturesDto.getPartTypeName()),PortalContentPictures::getPartTypeName,portalContentPicturesDto.getPartTypeName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentPicturesDto.getPartCode()), PortalContentPictures::getPartCode,portalContentPicturesDto.getPartCode());
        queryWrapper.orderByDesc(PortalContentPictures::getCreateTime);
        Page<PortalContentPictures> page = portalContentPicturesMapper.selectPage(portalContentPicturesDto.getPage(),queryWrapper);
        Page<PortalContentPicturesVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentPicturesVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        List<PortalContentPicturesVo> records = pageVo.getRecords();
        if (ObjectUtils.isNotEmpty(records)) {
            records.forEach(d->{
                List<PortalContentPicturesSon> picturesSons = this.findSons(d.getId());
                if (ObjectUtils.isNotEmpty(picturesSons)) {
                    List<PortalContentPicturesSonVo> picturesSonVos = DozerUtils.mapList(dozerMapper, picturesSons, PortalContentPicturesSonVo.class);
                    d.setPicturesSons(picturesSonVos);
                }
            });
            pageVo.setRecords(records);
        }
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentPicturesDto
    * @return
    */
    @Override
    public Page lists(PortalContentPicturesDto portalContentPicturesDto) {
        Page<PortalContentPicturesVo> pageVo = portalContentPicturesMapper.getPageList(portalContentPicturesDto.getPage(),portalContentPicturesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentPicturesVo get(Long id) {
        PortalContentPictures portalContentPictures = super.getById(id);
        PortalContentPicturesVo portalContentPicturesVo = null;
        if(portalContentPictures !=null){
            portalContentPicturesVo = dozerMapper.map(portalContentPictures,PortalContentPicturesVo.class);
        }
        //查询子表
        List<PortalContentPicturesSon> picturesSons = this.findSons(id);
        if (CollectionUtils.isNotEmpty(picturesSons)) {
            List<PortalContentPicturesSonVo> picturesSonVos = DozerUtils.mapList(dozerMapper, picturesSons, PortalContentPicturesSonVo.class);
            portalContentPicturesVo.setPicturesSons(picturesSonVos);
        }
        log.debug("查询成功");
        return portalContentPicturesVo;
    }

    /**
    * 保存实体
    * @param portalContentPicturesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentPicturesDto portalContentPicturesDto) {
//        portalContentPicturesDto.setAuditStatus(AuditStateEnum.STARTING.getType());
        PortalContentPictures portalContentPictures = dozerMapper.map(portalContentPicturesDto,PortalContentPictures.class);
        boolean result = super.save(portalContentPictures);
        if(result){
            log.debug("保存成功");
        }
        //保存子表
        List<PortalContentPicturesSonDto> picturesSonDtos = portalContentPicturesDto.getPicturesSons();
        if (CollectionUtils.isNotEmpty(picturesSonDtos)) {
            picturesSonDtos.forEach(d->{
                d.setHits(0L);
                d.setLinkPicturesId(portalContentPictures.getId());
            });
            List<PortalContentPicturesSon> picturesSons = DozerUtils.mapList(dozerMapper, picturesSonDtos, PortalContentPicturesSon.class);
            picturesSonService.saveBatch(picturesSons);

            //审批完成后通知 生成静态文件
            picturesSons.forEach(picturesSon->{
                Map<String,Object> eventData = new HashMap<>();
                eventData.put(EventConstants.PicturesToHtmlFileHandle,picturesSon);
                portalPublisher.publish(eventData);
            });
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentPicturesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentPicturesDto portalContentPicturesDto) {
        portalContentPicturesDto.setUpdateTime(LocalDateTime.now());
        PortalContentPictures portalContentPictures = dozerMapper.map(portalContentPicturesDto,PortalContentPictures.class);
        boolean result = super.updateById(portalContentPictures);
        if(result){
            log.debug("修改成功");
        }
        //修改子表
        List<PortalContentPicturesSonDto> picturesSonDtos = portalContentPicturesDto.getPicturesSons();
        if (CollectionUtils.isNotEmpty(picturesSonDtos)) {
            //先删除
            LambdaQueryWrapper<PortalContentPicturesSon> picturesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
            picturesSonLambdaQueryWrapper.eq(PortalContentPicturesSon::getLinkPicturesId,portalContentPictures.getId());
            picturesSonMapper.delete(picturesSonLambdaQueryWrapper);
            //再新增
            List<PortalContentPicturesSon> picturesSons = DozerUtils.mapList(dozerMapper, picturesSonDtos, PortalContentPicturesSon.class);
            picturesSons.forEach(d->{
                d.setHits(0L);
                d.setLinkPicturesId(portalContentPictures.getId());
            });
            picturesSonService.saveBatch(picturesSons);

            //审批完成后通知 生成静态文件
            picturesSons.forEach(picturesSon->{
                Map<String,Object> eventData = new HashMap<>();
                eventData.put(EventConstants.PicturesToHtmlFileHandle,picturesSon);
                portalPublisher.publish(eventData);
            });
        }
        return result;
    }

    @Override
    public Boolean delete(String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new IncloudException("请选择要删除的对应选项");
        }
        List<String> idsList = Arrays.asList(ids.split(","));
        LambdaQueryWrapper<PortalContentPictures> picturesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        picturesLambdaQueryWrapper.in(PortalContentPictures::getId,idsList);
        portalContentPicturesMapper.delete(picturesLambdaQueryWrapper);
        //删除子表
        idsList.forEach(d->{
            LambdaQueryWrapper<PortalContentPicturesSon> picturesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
            picturesSonLambdaQueryWrapper.eq(PortalContentPicturesSon::getLinkPicturesId,d);
            picturesSonMapper.delete(picturesSonLambdaQueryWrapper);
        });
        return true;
    }

    @Override
    public Page listForShow(PortalContentPicturesDto portalContentPicturesDto) {
        LambdaQueryWrapper<PortalContentPictures> picturesWrapper = new LambdaQueryWrapper<>();
        picturesWrapper.eq(StringUtils.isNotBlank(portalContentPicturesDto.getPartCode()), PortalContentPictures::getPartCode,portalContentPicturesDto.getPartCode());
        picturesWrapper.eq(ObjectUtils.isNotEmpty(portalContentPicturesDto.getPortalId()), PortalContentPictures::getPortalId,portalContentPicturesDto.getPortalId());
        List<PortalContentPictures> picturesList = portalContentPicturesMapper.selectList(picturesWrapper);
        if (CollectionUtils.isNotEmpty(picturesList)) {
            List<Long> picturesIds = new ArrayList<>();
            picturesList.forEach(pictures->{
                picturesIds.add(pictures.getId());
            });
            LambdaQueryWrapper<PortalContentPicturesSon> picturesSonWrapper = new LambdaQueryWrapper<>();
            picturesSonWrapper.like(StringUtils.isNotBlank(portalContentPicturesDto.getTitle()),PortalContentPicturesSon::getTitle,portalContentPicturesDto.getTitle());
            picturesSonWrapper.in(PortalContentPicturesSon::getLinkPicturesId,picturesIds);
            picturesSonWrapper.orderByDesc(PortalContentPicturesSon::getCreateTime);
            Page<PortalContentPicturesSon> page = picturesSonMapper.selectPage(portalContentPicturesDto.getPage(), picturesSonWrapper);
            Page<PortalContentPicturesSonVo> picturesSonVoPage = DozerUtils.mapPage(dozerMapper, page, PortalContentPicturesSonVo.class);
            List<PortalContentPicturesSonVo> picturesSonVoList = picturesSonVoPage.getRecords();
            if (CollectionUtils.isNotEmpty(picturesSonVoList)) {
                picturesSonVoList.forEach(picturesSonVo->{
                    picturesWrapper.clear();
                    picturesWrapper.eq(PortalContentPictures::getId,picturesSonVo.getLinkPicturesId());
                    PortalContentPictures pictures = portalContentPicturesMapper.selectOne(picturesWrapper);
                    picturesSonVo.setPortalId(pictures.getPortalId());
                    picturesSonVo.setPortalName(pictures.getPortalName());
                    picturesSonVo.setPartId(pictures.getPartId());
                    picturesSonVo.setPartCode(pictures.getPartCode());
                    picturesSonVo.setPartName(pictures.getPartName());
                    picturesSonVo.setPartTypeId(pictures.getPartTypeId());
                    picturesSonVo.setPartTypeName(pictures.getPartTypeName());
                    if(StringUtils.isNotBlank(picturesSonVo.getOutLinkUrl())){
                        picturesSonVo.setContentUrl(picturesSonVo.getOutLinkUrl());
                    }
                });
                picturesSonVoPage.setRecords(picturesSonVoList);
            }
            return picturesSonVoPage;
        }
        return null;
    }

//    @Override
//    public Result procDel(String camundaProcinsId) {
//        PortalContentPictures portalContentPictures = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(portalContentPictures)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        super.removeById(portalContentPictures.getId());
//        //删除子表
//        LambdaQueryWrapper<PortalContentPicturesSon> picturesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        picturesSonLambdaQueryWrapper.eq(PortalContentPicturesSon::getLinkPicturesId,portalContentPictures.getId());
//        picturesSonMapper.delete(picturesSonLambdaQueryWrapper);
//        return Result.success();
//    }
//
//    @Override
//    public Result procStop(String camundaProcinsId) {
//        PortalContentPictures portalContentPictures = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(portalContentPictures)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        if (portalContentPictures.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
//            throw new IncloudException("流程已完成审批，不能终止！");
//        }
//        portalContentPictures.setAuditStatus(AuditStateEnum.TERMINATION.getType());
//        portalContentPicturesMapper.updateById(portalContentPictures);
//        return Result.success();
//    }
//
//    @Override
//    public Result auditSucceed(String camundaProcinsId) {
//        PortalContentPictures portalContentPictures = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(portalContentPictures)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        portalContentPictures.setUpdateTime(LocalDateTime.now());
//        portalContentPictures.setPassTime(LocalDateTime.now());
//        portalContentPictures.setAuditStatus(AuditStateEnum.COMPLETE.getType());
//        portalContentPicturesMapper.updateById(portalContentPictures);
//        return Result.success();
//    }
//
//    @Override
//    public PortalContentPicturesVo procView(ProcViewDto procViewDto) {
//        PortalContentPictures pictures = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
//        if (ObjectUtils.isNotEmpty(pictures)) {
//            PortalContentPicturesVo picturesVo = dozerMapper.map(pictures, PortalContentPicturesVo.class);
//            //查询子表
//            List<PortalContentPicturesSon> picturesSons = this.findSons(picturesVo.getId());
//            if (CollectionUtils.isNotEmpty(picturesSons)) {
//                List<PortalContentPicturesSonVo> picturesSonVos = DozerUtils.mapList(dozerMapper, picturesSons, PortalContentPicturesSonVo.class);
//                picturesVo.setPicturesSons(picturesSonVos);
//            }
//            return picturesVo;
//        }
//        return null;
//    }
//
//    @Override
//    @Transactional
//    public void procBizSubmit(PortalContentPicturesDto portalContentPicturesDto) {
//        try {
//            log.debug("图片轮播类内容发布procSubmit参数：" + portalContentPicturesDto.toString());
//            //根据流程 id 查询出具体的业务信息
//            PortalContentPictures portalContentPictures = this.queryByProcinsId(portalContentPicturesDto.getCamundaProcinsId());
//            if(ObjectUtils.isEmpty(portalContentPictures)){
//                throw new IncloudException("没有查询出具体的图片轮播类内容发布信息。");
//            }
//            //如果流程状态是起草，修改状态
//            if((portalContentPictures.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
//                //修改状态
//                portalContentPicturesDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());
//            }
//            this.update(portalContentPicturesDto);
//        }catch (Exception e) {
//            throw new IncloudException(e.getMessage());
//        }
//    }
//
//    @Override
//    @Transactional
//    public Result procSave(PortalContentPicturesDto portalContentPicturesDto) {
//        log.info("图片轮播类内容发布procSave参数：" + portalContentPicturesDto.toString());
//        if(StringUtils.isBlank(portalContentPicturesDto.getCamundaProcinsId())) {
//            String maxBizKey = portalContentPicturesMapper.getMaxBizKey();
//            log.info("图片轮播类内容发布，最大的maxBizKey:{}",maxBizKey);
//            WfEngineDto wfEngine = portalContentPicturesDto.getWfEngine();
//            log.info("图片轮播类内容发布，启动工作流参数：{}",wfEngine);
//            BizInfoDto bizInfoDto = wfEngine.getBizInfoDto();
//            if (ObjectUtils.isEmpty(bizInfoDto)) {
//                bizInfoDto = new BizInfoDto();
//                bizInfoDto.setReason(portalContentPicturesDto.getReason());
//                if (StringUtils.isBlank(maxBizKey)) {
//                    bizInfoDto.setBizKey("PICTURES-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
//                }else {
//                    String bizKey = "PICTURES-"+Long.valueOf(maxBizKey)+1;
//                    log.info("生成的code为：{}",bizKey);
//                    bizInfoDto.setBizKey(bizKey);
//                }
//            }else {
//                if (StringUtils.isBlank(maxBizKey)) {
//                    bizInfoDto.setBizKey("PICTURES-"+DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
//                } else {
//                    String bizKey = "PICTURES-"+Long.valueOf(maxBizKey)+1;
//                    log.info("生成的code为：{}",bizKey);
//                    bizInfoDto.setBizKey(bizKey);
//                }
//            }
//            EngineVo engineVo = wfService.startEngine(portalContentPicturesDto);
//            wfService.setWfDto(portalContentPicturesDto,engineVo);
//            this.save(portalContentPicturesDto);
//            return Result.success(engineVo);
//        } else {
//            //修改业务信息
//            this.update(portalContentPicturesDto);
//            return Result.success();
//        }
//    }
//
//    //根据camundaProcinsId查询主表信息
//    public PortalContentPictures queryByProcinsId(String camundaProcinsId){
//        LambdaQueryWrapper<PortalContentPictures> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(PortalContentPictures::getCamundaProcinsId,camundaProcinsId);
//        return portalContentPicturesMapper.selectOne(queryWrapper);
//    }

    //查询子表信息
    public List<PortalContentPicturesSon> findSons(Long linkPicturesId){
        LambdaQueryWrapper<PortalContentPicturesSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentPicturesSon::getLinkPicturesId,linkPicturesId);
        return picturesSonMapper.selectList(queryWrapper);
    }
}
