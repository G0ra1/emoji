package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.wf.eunm.AuditStateEnum;
import com.netwisd.base.portal.dto.PortalContentAdjPicturesSonDto;
import com.netwisd.base.portal.entity.*;
import com.netwisd.base.portal.mapper.*;
import com.netwisd.base.portal.service.PortalContentAdjPicturesSonService;
import com.netwisd.base.portal.service.PortalContentHisPicturesSonService;
import com.netwisd.base.portal.service.PortalContentPicturesSonService;
import com.netwisd.base.portal.vo.PortalContentAdjPicturesSonVo;
import com.netwisd.base.wf.starter.dto.BizInfoDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.service.impl.WfProcServiceImpl;
import com.netwisd.base.portal.service.PortalContentAdjPicturesService;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentAdjPicturesDto;
import com.netwisd.base.portal.vo.PortalContentAdjPicturesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.common.core.util.Result;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description 调整 图片轮播类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:15:30
 */
@Service
@Slf4j
public class PortalContentAdjPicturesServiceImpl extends WfProcServiceImpl<PortalContentAdjPicturesMapper, PortalContentAdjPictures,PortalContentAdjPicturesDto,PortalContentAdjPicturesVo> implements PortalContentAdjPicturesService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentAdjPicturesMapper portalContentAdjPicturesMapper;

    @Autowired
    private PortalContentAdjPicturesSonMapper adjPicturesSonMapper;

    @Autowired
    private PortalContentAdjPicturesSonService adjPicturesSonService;

    @Autowired
    private PortalContentHisPicturesMapper hisPicturesMapper;

    @Autowired
    private PortalContentHisPicturesSonMapper hisPicturesSonMapper;

    @Autowired
    private PortalContentHisPicturesSonService hisPicturesSonService;

    @Autowired
    private PortalContentPicturesMapper picturesMapper;

    @Autowired
    private PortalContentPicturesSonMapper picturesSonMapper;

    @Autowired
    private PortalContentPicturesSonService picturesSonService;

    @Autowired
    private WfService wfService;

    /**
    * 单表简单查询操作
    * @param portalContentAdjPicturesDto
    * @return
    */
    @Override
    public Page list(PortalContentAdjPicturesDto portalContentAdjPicturesDto) {
        LambdaQueryWrapper<PortalContentAdjPictures> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjPicturesDto.getPortalId()), PortalContentAdjPictures::getPortalId,portalContentAdjPicturesDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjPicturesDto.getPartId()),PortalContentAdjPictures::getPartId,portalContentAdjPicturesDto.getPartId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjPicturesDto.getPartTypeId()),PortalContentAdjPictures::getPartTypeId,portalContentAdjPicturesDto.getPartTypeId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjPicturesDto.getAuditStatus()),PortalContentAdjPictures::getAuditStatus,portalContentAdjPicturesDto.getAuditStatus());
        Page<PortalContentAdjPictures> page = portalContentAdjPicturesMapper.selectPage(portalContentAdjPicturesDto.getPage(),queryWrapper);
        Page<PortalContentAdjPicturesVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjPicturesVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentAdjPicturesDto
    * @return
    */
    @Override
    public Page lists(PortalContentAdjPicturesDto portalContentAdjPicturesDto) {
        Page<PortalContentAdjPicturesVo> pageVo = portalContentAdjPicturesMapper.getPageList(portalContentAdjPicturesDto.getPage(),portalContentAdjPicturesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentAdjPicturesVo get(Long id) {
        PortalContentAdjPictures portalContentAdjPictures = super.getById(id);
        PortalContentAdjPicturesVo portalContentAdjPicturesVo = null;
        if(portalContentAdjPictures !=null){
            portalContentAdjPicturesVo = dozerMapper.map(portalContentAdjPictures,PortalContentAdjPicturesVo.class);
        }
        log.debug("查询成功");

        //查询子表
        List<PortalContentAdjPicturesSon> adjPicturesSons = this.findSons(id);
        if (CollectionUtils.isNotEmpty(adjPicturesSons)) {
            List<PortalContentAdjPicturesSonVo> adjPicturesSonVos = DozerUtils.mapList(dozerMapper, adjPicturesSons, PortalContentAdjPicturesSonVo.class);
            portalContentAdjPicturesVo.setPicturesSons(adjPicturesSonVos);
        }
        return portalContentAdjPicturesVo;
    }

    /**
    * 保存实体
    * @param portalContentAdjPicturesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentAdjPicturesDto portalContentAdjPicturesDto) {
        portalContentAdjPicturesDto.setAuditStatus(AuditStateEnum.STARTING.getType());
        PortalContentAdjPictures portalContentAdjPictures = dozerMapper.map(portalContentAdjPicturesDto,PortalContentAdjPictures.class);
        boolean result = super.save(portalContentAdjPictures);
        if(result){
            log.debug("保存成功");
        }
        //保存子表
        List<PortalContentAdjPicturesSonDto> adjPicturesSonDtos = portalContentAdjPicturesDto.getPicturesSons();
        if (CollectionUtils.isNotEmpty(adjPicturesSonDtos)) {
            List<PortalContentAdjPicturesSon> adjPicturesSons = DozerUtils.mapList(dozerMapper, adjPicturesSonDtos, PortalContentAdjPicturesSon.class);
            adjPicturesSons.forEach(d->{
                d.setLinkPicturesId(portalContentAdjPictures.getId());
            });
            adjPicturesSonService.saveBatch(adjPicturesSons);
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentAdjPicturesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentAdjPicturesDto portalContentAdjPicturesDto) {
        portalContentAdjPicturesDto.setUpdateTime(LocalDateTime.now());
        PortalContentAdjPictures portalContentAdjPictures = dozerMapper.map(portalContentAdjPicturesDto,PortalContentAdjPictures.class);
        boolean result = super.updateById(portalContentAdjPictures);
        if(result){
            log.debug("修改成功");
        }
        //修改子表
        //先删除
        LambdaQueryWrapper<PortalContentAdjPicturesSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjPicturesSon::getLinkPicturesId,portalContentAdjPictures.getId());
        adjPicturesSonMapper.delete(queryWrapper);
        //再新增
        List<PortalContentAdjPicturesSonDto> adjPicturesSonDtos = portalContentAdjPicturesDto.getPicturesSons();
        if (CollectionUtils.isNotEmpty(adjPicturesSonDtos)) {
            List<PortalContentAdjPicturesSon> adjPicturesSons = DozerUtils.mapList(dozerMapper, adjPicturesSonDtos, PortalContentAdjPicturesSon.class);
            adjPicturesSons.forEach(d->{
                d.setLinkPicturesId(portalContentAdjPictures.getId());
            });
            adjPicturesSonService.saveBatch(adjPicturesSons);
        }
        return result;
    }

    @Override
    public Result procDel(String camundaProcinsId) {
        PortalContentAdjPictures adjPictures = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isNotEmpty(adjPictures)) {
            throw new IncloudException("未找到对应相关信息！");
        }
        super.removeById(adjPictures.getId());
        //删除子表
        LambdaQueryWrapper<PortalContentAdjPicturesSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjPicturesSon::getLinkPicturesId,adjPictures.getId());
        adjPicturesSonMapper.delete(queryWrapper);
        return Result.success();
    }

    @Override
    public Result procStop(String camundaProcinsId) {
        PortalContentAdjPictures adjPictures = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(adjPictures)) {
            throw new IncloudException("没有找到对应信息！");
        }
        if (adjPictures.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
            throw new IncloudException("流程已完成审批，不能终止！");
        }
        adjPictures.setAuditStatus(AuditStateEnum.TERMINATION.getType());
        portalContentAdjPicturesMapper.updateById(adjPictures);
        return Result.success();
    }

    @Override
    public Result auditSucceed(String camundaProcinsId) {
        PortalContentAdjPictures adjPictures = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(adjPictures)) {
            throw new IncloudException("没有找到对应信息！");
        }
        //修改调整表信息
        adjPictures.setUpdateTime(LocalDateTime.now());
        adjPictures.setPassTime(LocalDateTime.now());
        adjPictures.setAuditStatus(AuditStateEnum.COMPLETE.getType());
        portalContentAdjPicturesMapper.updateById(adjPictures);

        //将主表内容添加到历史表中
        //查主表信息
        PortalContentPictures pictures = picturesMapper.selectById(adjPictures.getLinkMainPicturesId());
        //查子表信息
        LambdaQueryWrapper<PortalContentPicturesSon> picturesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
        picturesSonLambdaQueryWrapper.eq(PortalContentPicturesSon::getLinkPicturesId,pictures.getId());
        List<PortalContentPicturesSon> picturesSons = picturesSonMapper.selectList(picturesSonLambdaQueryWrapper);
        //将主表信息添加到历史表中
        PortalContentHisPictures hisPictures = dozerMapper.map(pictures, PortalContentHisPictures.class);
        hisPicturesMapper.insert(hisPictures);
        //将子表信息添加到历史表中
        List<PortalContentHisPicturesSon> hisPicturesSons = DozerUtils.mapList(dozerMapper, picturesSons, PortalContentHisPicturesSon.class);
        hisPicturesSons.forEach(d->{
            d.setLinkPicturesId(hisPictures.getId());
        });
        hisPicturesSonService.saveBatch(hisPicturesSons);

        //将调整表信息覆盖到主表中
        //主表覆盖
        pictures.setId(adjPictures.getLinkMainPicturesId());
        pictures.setPortalId(adjPictures.getPortalId());
        pictures.setPortalName(adjPictures.getPortalName());
        pictures.setPartId(adjPictures.getPartId());
        pictures.setPartName(adjPictures.getPartName());
        pictures.setPartTypeId(adjPictures.getPartTypeId());
        pictures.setPartTypeName(adjPictures.getPartTypeName());
        pictures.setRemark(adjPictures.getRemark());
        picturesMapper.updateById(pictures);
        //子表覆盖
        //查询调整表的子表信息
        LambdaQueryWrapper<PortalContentAdjPicturesSon> adjPicturesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adjPicturesSonLambdaQueryWrapper.eq(PortalContentAdjPicturesSon::getLinkPicturesId,adjPictures.getId());
        List<PortalContentAdjPicturesSon> adjPicturesSons = adjPicturesSonMapper.selectList(adjPicturesSonLambdaQueryWrapper);
        adjPicturesSons.forEach(newSon->{
            //把关联主表id改成主表的id
            newSon.setLinkPicturesId(pictures.getId());
            //假如调整表中子表的数据只是修改，把调整表中的子表的点击量换成最新的
            picturesSons.forEach(oldSon->{
                if (ObjectUtils.isNotEmpty(newSon.getLinkPicturesSonId()) && newSon.getLinkPicturesSonId().equals(oldSon.getId())) {
                    newSon.setHits(oldSon.getHits());
                }
            });
        });
        //删除主表中的子表数据
        picturesSonMapper.delete(picturesSonLambdaQueryWrapper);
        //将调整表中的子表信息覆盖到主表中
        List<PortalContentPicturesSon> newFilesSons = DozerUtils.mapList(dozerMapper, adjPicturesSons, PortalContentPicturesSon.class);
        picturesSonService.saveBatch(newFilesSons);
        return Result.success();
    }

    @Override
    public PortalContentAdjPicturesVo procView(ProcViewDto procViewDto) {
        PortalContentAdjPictures adjPictures = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
        if (ObjectUtils.isNotEmpty(adjPictures)) {
            PortalContentAdjPicturesVo adjPicturesVo = dozerMapper.map(adjPictures, PortalContentAdjPicturesVo.class);
            List<PortalContentAdjPicturesSon> adjPicturesSons = this.findSons(adjPicturesVo.getId());
            if (CollectionUtils.isNotEmpty(adjPicturesSons)) {
                List<PortalContentAdjPicturesSonVo> adjPicturesSonVos = DozerUtils.mapList(dozerMapper, adjPicturesSons, PortalContentAdjPicturesSonVo.class);
                adjPicturesVo.setPicturesSons(adjPicturesSonVos);
            }
            return adjPicturesVo;
        }
        return null;
    }

    @Override
    @Transactional
    public void procBizSubmit(PortalContentAdjPicturesDto portalContentAdjPicturesDto) {
        try {
            log.debug("调整 图片轮播类内容发布procSubmit参数：" + portalContentAdjPicturesDto.toString());
            //根据流程 id 查询出具体的业务信息
            PortalContentAdjPictures adjPictures = this.queryByProcinsId(portalContentAdjPicturesDto.getCamundaProcinsId());
            if(ObjectUtils.isEmpty(adjPictures)){
                throw new IncloudException("没有查询出具体的调整图片轮播类内容发布信息。");
            }
            //如果流程状态是起草，修改状态
            if((adjPictures.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
                //修改状态
                portalContentAdjPicturesDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());
            }
            this.update(portalContentAdjPicturesDto);
        }catch (Exception e) {
            throw new IncloudException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result procSave(PortalContentAdjPicturesDto portalContentAdjPicturesDto) {
        log.info("调整图片轮播类内容发布procSave参数：" + portalContentAdjPicturesDto.toString());
        if(StringUtils.isBlank(portalContentAdjPicturesDto.getCamundaProcinsId())) {
            String maxBizKey = portalContentAdjPicturesMapper.getMaxBizKey();
            log.info("调整图片轮播类内容发布，最大的maxBizKey:{}",maxBizKey);
            WfEngineDto.StartDto startDto = portalContentAdjPicturesDto.getStartDto();
            log.info("调整图片轮播类内容发布，启动工作流参数：{}",startDto);
            if (ObjectUtils.isEmpty(startDto)) {
                startDto.setReason(portalContentAdjPicturesDto.getReason());
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJPICTURES-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
                }else {
                    String bizKey = "ADJPICTURES-"+Long.valueOf(maxBizKey)+1;
                    log.info("生成的code为：{}",bizKey);
                    startDto.setBizKey(bizKey);
                }
            }else {
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJPICTURES-"+DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
                } else {
                    String bizKey = "ADJPICTURES-"+Long.valueOf(maxBizKey)+1;
                    log.info("生成的code为：{}",bizKey);
                    startDto.setBizKey(bizKey);
                }
            }
            EngineVo engineVo = wfService.startEngine(portalContentAdjPicturesDto);
            wfService.setWfDto(portalContentAdjPicturesDto,engineVo);
            this.save(portalContentAdjPicturesDto);
            return Result.success(engineVo);
        } else {
            //修改业务信息
            this.update(portalContentAdjPicturesDto);
            return Result.success();
        }
    }

    //查询子表
    public List<PortalContentAdjPicturesSon> findSons(Long linkPicturesId){
        LambdaQueryWrapper<PortalContentAdjPicturesSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjPicturesSon::getLinkPicturesId,linkPicturesId);
        return adjPicturesSonMapper.selectList(queryWrapper);
    }

    //通过camundaProcinsId查主表
    public PortalContentAdjPictures queryByProcinsId(String camundaProcinsId){
        LambdaQueryWrapper<PortalContentAdjPictures> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjPictures::getCamundaProcinsId,camundaProcinsId);
        return portalContentAdjPicturesMapper.selectOne(queryWrapper);
    }
}
