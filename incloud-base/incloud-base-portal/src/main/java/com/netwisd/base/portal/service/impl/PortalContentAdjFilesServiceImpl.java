package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.wf.eunm.AuditStateEnum;
import com.netwisd.base.portal.dto.PortalContentAdjFilesSonDto;
import com.netwisd.base.portal.entity.*;
import com.netwisd.base.portal.mapper.*;
import com.netwisd.base.portal.service.PortalContentAdjFilesSonService;
import com.netwisd.base.portal.service.PortalContentFilesSonService;
import com.netwisd.base.portal.service.PortalContentHisFilesSonService;
import com.netwisd.base.portal.vo.PortalContentAdjFilesSonVo;
import com.netwisd.base.wf.starter.dto.BizInfoDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.service.impl.WfProcServiceImpl;
import com.netwisd.base.portal.service.PortalContentAdjFilesService;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentAdjFilesDto;
import com.netwisd.base.portal.vo.PortalContentAdjFilesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.common.core.util.Result;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description 调整 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:20:54
 */
@Service
@Slf4j
public class PortalContentAdjFilesServiceImpl extends WfProcServiceImpl<PortalContentAdjFilesMapper, PortalContentAdjFiles,PortalContentAdjFilesDto,PortalContentAdjFilesVo> implements PortalContentAdjFilesService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentAdjFilesMapper portalContentAdjFilesMapper;

    @Autowired
    private PortalContentAdjFilesSonMapper portalContentAdjFilesSonMapper;

    @Autowired
    private PortalContentAdjFilesSonService portalContentAdjFilesSonService;

    @Autowired
    private PortalContentFilesMapper portalContentFilesMapper;

    @Autowired
    private PortalContentFilesSonMapper portalContentFilesSonMapper;

    @Autowired
    private PortalContentFilesSonService portalContentFilesSonService;

    @Autowired
    private PortalContentHisFilesMapper portalContentHisFilesMapper;

    @Autowired
    private PortalContentHisFilesSonMapper portalContentHisFilesSonMapper;

    @Autowired
    private PortalContentHisFilesSonService portalContentHisFilesSonService;
    
    @Autowired
    private WfService wfService;

    /**
    * 单表简单查询操作
    * @param portalContentAdjFilesDto
    * @return
    */
    @Override
    public Page list(PortalContentAdjFilesDto portalContentAdjFilesDto) {
        LambdaQueryWrapper<PortalContentAdjFiles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjFilesDto.getPortalId()), PortalContentAdjFiles::getPortalId,portalContentAdjFilesDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjFilesDto.getPartId()),PortalContentAdjFiles::getPartId,portalContentAdjFilesDto.getPartId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjFilesDto.getPartTypeId()),PortalContentAdjFiles::getPartTypeId,portalContentAdjFilesDto.getPartTypeId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjFilesDto.getAuditStatus()),PortalContentAdjFiles::getAuditStatus,portalContentAdjFilesDto.getAuditStatus());
        Page<PortalContentAdjFiles> page = portalContentAdjFilesMapper.selectPage(portalContentAdjFilesDto.getPage(),queryWrapper);
        Page<PortalContentAdjFilesVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjFilesVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentAdjFilesDto
    * @return
    */
    @Override
    public Page lists(PortalContentAdjFilesDto portalContentAdjFilesDto) {
        Page<PortalContentAdjFilesVo> pageVo = portalContentAdjFilesMapper.getPageList(portalContentAdjFilesDto.getPage(),portalContentAdjFilesDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentAdjFilesVo get(Long id) {
        PortalContentAdjFiles portalContentAdjFiles = super.getById(id);
        PortalContentAdjFilesVo portalContentAdjFilesVo = null;
        if(portalContentAdjFiles !=null){
            portalContentAdjFilesVo = dozerMapper.map(portalContentAdjFiles,PortalContentAdjFilesVo.class);
        }
        log.debug("查询成功");
        //查询子表信息
        List<PortalContentAdjFilesSon> adjFilesSons = this.findSons(id);
        if (CollectionUtils.isNotEmpty(adjFilesSons)) {
            List<PortalContentAdjFilesSonVo> adjFilesSonVos = DozerUtils.mapList(dozerMapper, adjFilesSons, PortalContentAdjFilesSonVo.class);
            portalContentAdjFilesVo.setFilesSons(adjFilesSonVos);
        }
        return portalContentAdjFilesVo;
    }

    /**
    * 保存实体
    * @param portalContentAdjFilesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentAdjFilesDto portalContentAdjFilesDto) {
        portalContentAdjFilesDto.setAuditStatus(AuditStateEnum.STARTING.getType());
        PortalContentAdjFiles portalContentAdjFiles = dozerMapper.map(portalContentAdjFilesDto,PortalContentAdjFiles.class);
        boolean result = super.save(portalContentAdjFiles);
        if(result){
            log.debug("保存成功");
        }
        //保存子表信息
        List<PortalContentAdjFilesSonDto> adjFilesSonDtos = portalContentAdjFilesDto.getFilesSons();
        if (CollectionUtils.isNotEmpty(adjFilesSonDtos)) {
            adjFilesSonDtos.forEach(d->{
                d.setLinkFilesId(portalContentAdjFiles.getId());
                if (ObjectUtils.isEmpty(d.getLinkFilesSonId())) {
                    d.setHits(0L);
                }
            });
            List<PortalContentAdjFilesSon> adjFilesSons = DozerUtils.mapList(dozerMapper, adjFilesSonDtos, PortalContentAdjFilesSon.class);
            portalContentAdjFilesSonService.saveBatch(adjFilesSons);
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentAdjFilesDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentAdjFilesDto portalContentAdjFilesDto) {
        portalContentAdjFilesDto.setUpdateTime(LocalDateTime.now());
        PortalContentAdjFiles portalContentAdjFiles = dozerMapper.map(portalContentAdjFilesDto,PortalContentAdjFiles.class);
        boolean result = super.updateById(portalContentAdjFiles);
        if(result){
            log.debug("修改成功");
        }
        //修改子表
        //先删除
        List<PortalContentAdjFilesSon> oldAdjFilesSons = this.findSons(portalContentAdjFiles.getId());
        portalContentAdjFilesSonMapper.deleteBatchIds(oldAdjFilesSons);
        //再新增
        List<PortalContentAdjFilesSonDto> adjFilesSonDtos = portalContentAdjFilesDto.getFilesSons();
        if (CollectionUtils.isNotEmpty(adjFilesSonDtos)) {
            adjFilesSonDtos.forEach(d->{
                d.setLinkFilesId(portalContentAdjFiles.getId());
                if (ObjectUtils.isEmpty(d.getLinkFilesSonId())) {
                    d.setHits(0L);
                }
            });
            List<PortalContentAdjFilesSon> newAdjFilesSons = DozerUtils.mapList(dozerMapper, adjFilesSonDtos, PortalContentAdjFilesSon.class);
            portalContentAdjFilesSonService.saveBatch(newAdjFilesSons);
        }
        return result;
    }

    @Override
    @Transactional
    public Result procDel(String camundaProcinsId) {
        PortalContentAdjFiles portalContentAdjFiles = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isNotEmpty(portalContentAdjFiles)) {
            throw new IncloudException("未找到对应相关信息！");
        }
        super.removeById(portalContentAdjFiles.getId());
        //删除子表
        LambdaQueryWrapper<PortalContentAdjFilesSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjFilesSon::getLinkFilesId,portalContentAdjFiles.getId());
        portalContentAdjFilesSonMapper.delete(queryWrapper);
        return Result.success();
    }

    @Override
    public Result procStop(String camundaProcinsId) {
        PortalContentAdjFiles portalContentAdjFiles = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjFiles)) {
            throw new IncloudException("没有找到对应信息！");
        }
        if (portalContentAdjFiles.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
            throw new IncloudException("流程已完成审批，不能终止！");
        }
        portalContentAdjFiles.setAuditStatus(AuditStateEnum.TERMINATION.getType());
        portalContentAdjFilesMapper.updateById(portalContentAdjFiles);
        return Result.success();
    }

    @Override
    @Transactional
    public Result auditSucceed(String camundaProcinsId) {
        PortalContentAdjFiles portalContentAdjFiles = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjFiles)) {
            throw new IncloudException("没有找到对应信息！");
        }
        //修改调整表信息
        portalContentAdjFiles.setUpdateTime(LocalDateTime.now());
        portalContentAdjFiles.setPassTime(LocalDateTime.now());
        portalContentAdjFiles.setAuditStatus(AuditStateEnum.COMPLETE.getType());
        portalContentAdjFilesMapper.updateById(portalContentAdjFiles);

        //将主表内容添加到历史表中
        //查主表信息
        PortalContentFiles portalContentFiles = portalContentFilesMapper.selectById(portalContentAdjFiles.getLinkMainFilesId());
        //查子表信息
        LambdaQueryWrapper<PortalContentFilesSon> filesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filesSonLambdaQueryWrapper.eq(PortalContentFilesSon::getLinkFilesId,portalContentFiles.getId());
        List<PortalContentFilesSon> portalContentFilesSons = portalContentFilesSonMapper.selectList(filesSonLambdaQueryWrapper);
        //将主表信息添加到历史表中
        PortalContentHisFiles contentHisFiles = dozerMapper.map(portalContentFiles, PortalContentHisFiles.class);
        portalContentHisFilesMapper.insert(contentHisFiles);
        //将子表信息添加到历史表中
        List<PortalContentHisFilesSon> portalContentHisFilesSons = DozerUtils.mapList(dozerMapper, portalContentFilesSons, PortalContentHisFilesSon.class);
        portalContentHisFilesSons.forEach(d->{
            d.setLinkFilesId(contentHisFiles.getId());
        });
        portalContentHisFilesSonService.saveBatch(portalContentHisFilesSons);

        //将调整表信息覆盖到主表中
        //主表覆盖
        portalContentFiles.setId(portalContentAdjFiles.getLinkMainFilesId());
        portalContentFiles.setPortalId(portalContentAdjFiles.getPortalId());
        portalContentFiles.setPortalName(portalContentAdjFiles.getPortalName());
        portalContentFiles.setPartId(portalContentAdjFiles.getPartId());
        portalContentFiles.setPartName(portalContentAdjFiles.getPartName());
        portalContentFiles.setPartTypeId(portalContentAdjFiles.getPartTypeId());
        portalContentFiles.setPartTypeName(portalContentAdjFiles.getPartTypeName());
        portalContentFiles.setRemark(portalContentAdjFiles.getRemark());
        portalContentFilesMapper.updateById(portalContentFiles);
        //子表覆盖
        //查询调整表的子表信息
        LambdaQueryWrapper<PortalContentAdjFilesSon> adjFilesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adjFilesSonLambdaQueryWrapper.eq(PortalContentAdjFilesSon::getLinkFilesId,portalContentAdjFiles.getId());
        List<PortalContentAdjFilesSon> adjFilesSons = portalContentAdjFilesSonMapper.selectList(adjFilesSonLambdaQueryWrapper);
        adjFilesSons.forEach(newSon->{
            //把关联主表id改成主表的id
            newSon.setLinkFilesId(portalContentFiles.getId());
            //假如调整表中子表的数据只是修改，把调整表中的子表的点击量换成最新的
            portalContentFilesSons.forEach(oldSon->{
                if (ObjectUtils.isNotEmpty(newSon.getLinkFilesSonId()) && newSon.getLinkFilesSonId().equals(oldSon.getId())) {
                    newSon.setHits(oldSon.getHits());
                }
            });
        });
        //删除主表中的子表数据
        portalContentFilesSonMapper.delete(filesSonLambdaQueryWrapper);
        //将调整表中的子表信息覆盖到主表中
        List<PortalContentFilesSon> newFilesSons = DozerUtils.mapList(dozerMapper, adjFilesSons, PortalContentFilesSon.class);
        portalContentFilesSonService.saveBatch(newFilesSons);
        return Result.success();
    }

    @Override
    public PortalContentAdjFilesVo procView(ProcViewDto procViewDto) {
        PortalContentAdjFiles portalContentAdjFiles = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
        if (ObjectUtils.isNotEmpty(portalContentAdjFiles)) {
            PortalContentAdjFilesVo portalContentAdjFilesVo = dozerMapper.map(portalContentAdjFiles, PortalContentAdjFilesVo.class);
            List<PortalContentAdjFilesSon> adjFilesSons = this.findSons(portalContentAdjFilesVo.getId());
            if (CollectionUtils.isNotEmpty(adjFilesSons)) {
                List<PortalContentAdjFilesSonVo> adjFilesSonVos = DozerUtils.mapList(dozerMapper, adjFilesSons, PortalContentAdjFilesSonVo.class);
                portalContentAdjFilesVo.setFilesSons(adjFilesSonVos);
                return portalContentAdjFilesVo;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void procBizSubmit(PortalContentAdjFilesDto portalContentAdjFilesDto) {
        try {
            log.debug("调整 文件下载类型内容发布procSubmit参数：" + portalContentAdjFilesDto.toString());
            //根据流程 id 查询出具体的业务信息
            PortalContentAdjFiles portalContentAdjFiles = this.queryByProcinsId(portalContentAdjFilesDto.getCamundaProcinsId());
            if(ObjectUtils.isEmpty(portalContentAdjFiles)){
                throw new IncloudException("没有查询出具体的调整文件下载类型内容发布信息。");
            }
            //如果流程状态是起草，修改状态
            if((portalContentAdjFiles.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
                //修改状态
                portalContentAdjFilesDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());
            }
            this.update(portalContentAdjFilesDto);
        }catch (Exception e) {
            throw new IncloudException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result procSave(PortalContentAdjFilesDto portalContentAdjFilesDto) {
        log.info("调整文件下载类型内容发布procSave参数：" + portalContentAdjFilesDto.toString());
        if(StringUtils.isBlank(portalContentAdjFilesDto.getCamundaProcinsId())) {
            String maxBizKey = portalContentAdjFilesMapper.getMaxBizKey();
            log.info("调整文件下载类型内容发布，最大的maxBizKey:{}",maxBizKey);
            WfEngineDto.StartDto startDto = portalContentAdjFilesDto.getStartDto();
            log.info("调整文件下载类型内容发布，启动工作流参数：{}",startDto);
            if (ObjectUtils.isEmpty(startDto)) {
                startDto.setReason(portalContentAdjFilesDto.getReason());
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJFILES-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
                }else {
                    String bizKey = "ADJFILES-"+Long.valueOf(maxBizKey)+1;
                    log.info("生成的code为：{}",bizKey);
                    startDto.setBizKey(bizKey);
                }
            }else {
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJFILES-"+DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
                } else {
                    String bizKey = "ADJFILES-"+Long.valueOf(maxBizKey)+1;
                    log.info("生成的code为：{}",bizKey);
                    startDto.setBizKey(bizKey);
                }
            }
            EngineVo engineVo = wfService.startEngine(portalContentAdjFilesDto);
            wfService.setWfDto(portalContentAdjFilesDto,engineVo);
            this.save(portalContentAdjFilesDto);
            return Result.success(engineVo);
        } else {
            //修改业务信息
            this.update(portalContentAdjFilesDto);
            return Result.success();
        }
    }

    public PortalContentAdjFiles queryByProcinsId(String camundaProcinsId){
        LambdaQueryWrapper<PortalContentAdjFiles> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjFiles::getCamundaProcinsId,camundaProcinsId);
        return portalContentAdjFilesMapper.selectOne(queryWrapper);
    }

    //通过主表id查询子表集合
    public List<PortalContentAdjFilesSon> findSons(Long linkFilesId){
        LambdaQueryWrapper<PortalContentAdjFilesSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjFilesSon::getLinkFilesId,linkFilesId);
        return portalContentAdjFilesSonMapper.selectList(queryWrapper);
    }
}
