package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.wf.eunm.AuditStateEnum;
import com.netwisd.base.portal.dto.PortalContentFilesSonDto;
import com.netwisd.base.portal.entity.*;
import com.netwisd.base.portal.mapper.PortalContentFilesSonMapper;
import com.netwisd.base.portal.service.PortalContentFilesSonService;
import com.netwisd.base.portal.vo.PortalContentFilesSonVo;
import com.netwisd.base.portal.vo.PortalContentPicturesSonVo;
import com.netwisd.base.wf.starter.dto.BizInfoDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.service.impl.WfProcServiceImpl;
import com.netwisd.base.portal.mapper.PortalContentFilesMapper;
import com.netwisd.base.portal.service.PortalContentFilesService;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentFilesDto;
import com.netwisd.base.portal.vo.PortalContentFilesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.common.core.util.Result;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-18 14:46:53
 */
@Service
@Slf4j
public class PortalContentFilesServiceImpl extends ServiceImpl<PortalContentFilesMapper, PortalContentFiles> implements PortalContentFilesService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentFilesMapper portalContentFilesMapper;

//    @Autowired
//    private WfService wfService;

    @Autowired
    private PortalContentFilesSonMapper portalContentFilesSonMapper;

    @Autowired
    private PortalContentFilesSonService portalContentFilesSonService;

    /**
    * 单表简单查询操作
    * @param portalContentFilesDto
    * @return
    */
    @Override
    public Page list(PortalContentFilesDto portalContentFilesDto) {
        LambdaQueryWrapper<PortalContentFiles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentFilesDto.getPortalId()),PortalContentFiles::getPortalId,portalContentFilesDto.getPortalId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentFilesDto.getPortalName()),PortalContentFiles::getPortalName,portalContentFilesDto.getPortalName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentFilesDto.getPartId()),PortalContentFiles::getPartId,portalContentFilesDto.getPartId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentFilesDto.getPartName()),PortalContentFiles::getPartName,portalContentFilesDto.getPartName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentFilesDto.getPartTypeId()),PortalContentFiles::getPartTypeId,portalContentFilesDto.getPartTypeId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentFilesDto.getPartTypeName()),PortalContentFiles::getPartTypeName,portalContentFilesDto.getPartTypeName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentFilesDto.getPartCode()),PortalContentFiles::getPartCode,portalContentFilesDto.getPartCode());
        queryWrapper.orderByDesc(PortalContentFiles::getCreateTime);
        Page<PortalContentFiles> page = portalContentFilesMapper.selectPage(portalContentFilesDto.getPage(),queryWrapper);
        Page<PortalContentFilesVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentFilesVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        //查询子表
        List<PortalContentFilesVo> records = pageVo.getRecords();
        if (ObjectUtils.isNotEmpty(records)) {
            records.forEach(d->{
                LambdaQueryWrapper<PortalContentFilesSon> filesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
                filesSonLambdaQueryWrapper.eq(PortalContentFilesSon::getLinkFilesId,d.getId());
                List<PortalContentFilesSon> portalContentFilesSons = portalContentFilesSonMapper.selectList(filesSonLambdaQueryWrapper);
                if (ObjectUtils.isNotEmpty(portalContentFilesSons)) {
                    List<PortalContentFilesSonVo> portalContentFilesSonVos = DozerUtils.mapList(dozerMapper, portalContentFilesSons, PortalContentFilesSonVo.class);
                    d.setFilesSons(portalContentFilesSonVos);
                }
            });
            pageVo.setRecords(records);
        }
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentFilesDto
    * @return
    */
    @Override
    public Page lists(PortalContentFilesDto portalContentFilesDto) {
        Page<PortalContentFilesVo> pageVo = portalContentFilesMapper.getPageList(portalContentFilesDto.getPage(),portalContentFilesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentFilesVo get(Long id) {
        PortalContentFiles portalContentFiles = super.getById(id);
        PortalContentFilesVo portalContentFilesVo = null;
        if(portalContentFiles !=null){
            portalContentFilesVo = dozerMapper.map(portalContentFiles,PortalContentFilesVo.class);
        }
        //子表查询
        LambdaQueryWrapper<PortalContentFilesSon> filesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filesSonLambdaQueryWrapper.eq(PortalContentFilesSon::getLinkFilesId,id);
        List<PortalContentFilesSon> portalContentFilesSons = portalContentFilesSonMapper.selectList(filesSonLambdaQueryWrapper);
        if (CollectionUtils.isNotEmpty(portalContentFilesSons)) {
            List<PortalContentFilesSonVo> portalContentFilesSonVos = DozerUtils.mapList(dozerMapper, portalContentFilesSons, PortalContentFilesSonVo.class);
            portalContentFilesVo.setFilesSons(portalContentFilesSonVos);
        }
        log.debug("查询成功");
        return portalContentFilesVo;
    }

    /**
    * 保存实体
    * @param portalContentFilesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentFilesDto portalContentFilesDto) {
//        portalContentFilesDto.setAuditStatus(AuditStateEnum.STARTING.getType());
        PortalContentFiles portalContentFiles = dozerMapper.map(portalContentFilesDto,PortalContentFiles.class);
        boolean result = super.save(portalContentFiles);
        if(result){
            log.debug("保存成功");
        }
        //子表新增
        List<PortalContentFilesSonDto> filesSonDtos = portalContentFilesDto.getFilesSons();
        if (CollectionUtils.isNotEmpty(filesSonDtos)) {
            List<PortalContentFilesSon> portalContentFilesSons = DozerUtils.mapList(dozerMapper, filesSonDtos, PortalContentFilesSon.class);
            portalContentFilesSons.forEach(d->{
                d.setLinkFilesId(portalContentFiles.getId());
                d.setHits(0L);
            });
            portalContentFilesSonService.saveBatch(portalContentFilesSons);
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentFilesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentFilesDto portalContentFilesDto) {
        portalContentFilesDto.setUpdateTime(LocalDateTime.now());
        PortalContentFiles portalContentFiles = dozerMapper.map(portalContentFilesDto,PortalContentFiles.class);
        boolean result = super.updateById(portalContentFiles);
        if(result){
            log.debug("修改成功");
        }
        //修改子表
        List<PortalContentFilesSonDto> filesSonDtos = portalContentFilesDto.getFilesSons();
        if (CollectionUtils.isNotEmpty(filesSonDtos)) {
            //先删除
            LambdaQueryWrapper<PortalContentFilesSon> portalContentFilesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
            portalContentFilesSonLambdaQueryWrapper.eq(PortalContentFilesSon::getLinkFilesId,portalContentFiles.getId());
            portalContentFilesSonMapper.delete(portalContentFilesSonLambdaQueryWrapper);
            //后新增
            List<PortalContentFilesSon> portalContentFilesSons = DozerUtils.mapList(dozerMapper, filesSonDtos, PortalContentFilesSon.class);
            portalContentFilesSons.forEach(d->{
                d.setLinkFilesId(portalContentFiles.getId());
                d.setHits(0L);
            });
            portalContentFilesSonService.saveBatch(portalContentFilesSons);
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean delete(String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new IncloudException("请选择要删除的选项");
        }
        //删除主表
        List<String> idsList = Arrays.asList(ids.split(","));
        LambdaQueryWrapper<PortalContentFiles> filesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filesLambdaQueryWrapper.in(PortalContentFiles::getId,idsList);
        portalContentFilesMapper.delete(filesLambdaQueryWrapper);
        //删除子表
        idsList.forEach(d->{
            LambdaQueryWrapper<PortalContentFilesSon> filesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
            filesSonLambdaQueryWrapper.eq(PortalContentFilesSon::getLinkFilesId,d);
            portalContentFilesSonMapper.delete(filesSonLambdaQueryWrapper);
        });
        return true;
    }

    @Override
    public Page listForShow(PortalContentFilesDto portalContentFilesDto) {
        LambdaQueryWrapper<PortalContentFiles> filesWrapper = new LambdaQueryWrapper<>();
        filesWrapper.eq(StringUtils.isNotBlank(portalContentFilesDto.getPartCode()), PortalContentFiles::getPartCode,portalContentFilesDto.getPartCode());
        filesWrapper.eq(ObjectUtils.isNotEmpty(portalContentFilesDto.getPortalId()), PortalContentFiles::getPortalId,portalContentFilesDto.getPortalId());
        List<PortalContentFiles> filesList = portalContentFilesMapper.selectList(filesWrapper);
        if (CollectionUtils.isNotEmpty(filesList)) {
            List<Long> filesIds = new ArrayList<>();
            filesList.forEach(files->{
                filesIds.add(files.getId());
            });
            LambdaQueryWrapper<PortalContentFilesSon> filesSonWrapper = new LambdaQueryWrapper<>();
            filesSonWrapper.like(StringUtils.isNotBlank(portalContentFilesDto.getFileName()),PortalContentFilesSon::getFileName,portalContentFilesDto.getFileName());
            filesSonWrapper.in(PortalContentFilesSon::getLinkFilesId,filesIds);
            filesSonWrapper.orderByDesc(PortalContentFilesSon::getCreateTime);
            Page<PortalContentFilesSon> page = portalContentFilesSonMapper.selectPage(portalContentFilesDto.getPage(), filesSonWrapper);
            Page<PortalContentFilesSonVo> filesSonVoPage = DozerUtils.mapPage(dozerMapper, page, PortalContentFilesSonVo.class);
            List<PortalContentFilesSonVo> filesSonVoList = filesSonVoPage.getRecords();
            if (CollectionUtils.isNotEmpty(filesSonVoList)) {
                filesSonVoList.forEach(filesSonVo->{
                    filesWrapper.clear();
                    filesWrapper.eq(PortalContentFiles::getId,filesSonVo.getLinkFilesId());
                    PortalContentFiles files = portalContentFilesMapper.selectOne(filesWrapper);
                    filesSonVo.setPortalId(files.getPortalId());
                    filesSonVo.setPortalName(files.getPortalName());
                    filesSonVo.setPartId(files.getPartId());
                    filesSonVo.setPartCode(files.getPartCode());
                    filesSonVo.setPartName(files.getPartName());
                    filesSonVo.setPartTypeId(files.getPartTypeId());
                    filesSonVo.setPartTypeName(files.getPartTypeName());
                });
                filesSonVoPage.setRecords(filesSonVoList);
            }
            return filesSonVoPage;
        }
        return null;
    }

//    @Override
//    public Result procDel(String camundaProcinsId) {
//        PortalContentFiles portalContentFiles = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(portalContentFiles)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        super.removeById(portalContentFiles.getId());
//        //删除子表
//        LambdaQueryWrapper<PortalContentFilesSon> portalContentFilesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        portalContentFilesSonLambdaQueryWrapper.eq(PortalContentFilesSon::getLinkFilesId,portalContentFiles.getId());
//        portalContentFilesSonMapper.delete(portalContentFilesSonLambdaQueryWrapper);
//        return Result.success();
//    }
//
//    @Override
//    public Result procStop(String camundaProcinsId) {
//        PortalContentFiles portalContentFiles = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(portalContentFiles)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        if (portalContentFiles.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
//            throw new IncloudException("流程已完成审批，不能终止！");
//        }
//        portalContentFiles.setAuditStatus(AuditStateEnum.TERMINATION.getType());
//        portalContentFilesMapper.updateById(portalContentFiles);
//        return Result.success();
//    }
//
//    @Override
//    public Result auditSucceed(String camundaProcinsId) {
//        PortalContentFiles portalContentFiles = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(portalContentFiles)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        portalContentFiles.setUpdateTime(LocalDateTime.now());
//        portalContentFiles.setPassTime(LocalDateTime.now());
//        portalContentFiles.setAuditStatus(AuditStateEnum.COMPLETE.getType());
//        portalContentFilesMapper.updateById(portalContentFiles);
//        return Result.success();
//    }
//
//    @Override
//    public PortalContentFilesVo procView(ProcViewDto procViewDto) {
//        PortalContentFiles portalContentFiles = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
//        PortalContentFilesVo portalContentFilesVo = dozerMapper.map(portalContentFiles, PortalContentFilesVo.class);
//        //子表查询
//        LambdaQueryWrapper<PortalContentFilesSon> portalContentFilesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        portalContentFilesSonLambdaQueryWrapper.eq(PortalContentFilesSon::getLinkFilesId,portalContentFiles.getId());
//        List<PortalContentFilesSon> portalContentFilesSons = portalContentFilesSonMapper.selectList(portalContentFilesSonLambdaQueryWrapper);
//        if (CollectionUtils.isNotEmpty(portalContentFilesSons)) {
//            List<PortalContentFilesSonVo> portalContentFilesSonVos = DozerUtils.mapList(dozerMapper, portalContentFilesSons, PortalContentFilesSonVo.class);
//            portalContentFilesVo.setFilesSons(portalContentFilesSonVos);
//        }
//        return portalContentFilesVo;
//    }
//
//    @Override
//    @Transactional
//    public void procBizSubmit(PortalContentFilesDto portalContentFilesDto) {
//        try {
//            log.debug("新闻通告类内容申请procSubmit参数：" + portalContentFilesDto.toString());
//            //根据流程 id 查询出具体的业务信息
//            PortalContentFiles portalContentFiles = this.queryByProcinsId(portalContentFilesDto.getCamundaProcinsId());
//            if(ObjectUtils.isEmpty(portalContentFiles)){
//                throw new IncloudException("没有查询出具体的新闻通告类内容申请信息。");
//            }
//            //如果流程状态是起草，修改状态
//            if((portalContentFiles.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
//                //修改状态
//                portalContentFilesDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());
//            }
//            this.update(portalContentFilesDto);
//        }catch (Exception e) {
//            throw new IncloudException(e.getMessage());
//        }
//    }
//
//    @Override
//    @Transactional
//    public Result procSave(PortalContentFilesDto portalContentFilesDto) {
//        log.info("文件下载类型内容申请procSave参数：" + portalContentFilesDto.toString());
//        if(StringUtils.isBlank(portalContentFilesDto.getCamundaProcinsId())) {
//            String maxBizKey = portalContentFilesMapper.getMaxBizKey();
//            log.info("文件下载类型内容申请，最大的maxBizKey:{}",maxBizKey);
//            WfEngineDto wfEngine = portalContentFilesDto.getWfEngine();
//            log.info("文件下载类型内容申请，启动工作流参数：{}",wfEngine);
//            BizInfoDto bizInfoDto = wfEngine.getBizInfoDto();
//            if (ObjectUtils.isEmpty(bizInfoDto)) {
//                bizInfoDto = new BizInfoDto();
//                bizInfoDto.setReason(portalContentFilesDto.getReason());
//                if (StringUtils.isBlank(maxBizKey)) {
//                    bizInfoDto.setBizKey("FILES-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
//                }else {
//                    String bizKey = "FILES-"+Long.valueOf(maxBizKey)+1;
//                    log.info("生成的code为：{}",bizKey);
//                    bizInfoDto.setBizKey(bizKey);
//                }
//            }else {
//                if (StringUtils.isBlank(maxBizKey)) {
//                    bizInfoDto.setBizKey("FILES-"+DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
//                } else {
//                    String bizKey = "FILES-"+Long.valueOf(maxBizKey)+1;
//                    log.info("生成的code为：{}",bizKey);
//                    bizInfoDto.setBizKey(bizKey);
//                }
//            }
//            EngineVo engineVo = wfService.startEngine(portalContentFilesDto);
//            wfService.setWfDto(portalContentFilesDto,engineVo);
//            this.save(portalContentFilesDto);
//            return Result.success(engineVo);
//        } else {
//            //修改业务信息
//            this.update(portalContentFilesDto);
//            return Result.success();
//        }
//    }
//
//    //根据camundaProcinsId查询业务信息
//    public PortalContentFiles queryByProcinsId(String camundaProcinsId){
//        LambdaQueryWrapper<PortalContentFiles> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(PortalContentFiles::getCamundaProcinsId,camundaProcinsId);
//        return portalContentFilesMapper.selectOne(queryWrapper);
//    }
}
