package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.portal.dto.PortalContentSysjointsSonDto;
import com.netwisd.base.portal.entity.*;
import com.netwisd.base.portal.mapper.PortalContentSysjointsSonMapper;
import com.netwisd.base.portal.service.PortalContentSysjointsSonService;
import com.netwisd.base.portal.vo.PortalContentPicturesSonVo;
import com.netwisd.base.portal.vo.PortalContentSysjointsSonVo;
import com.netwisd.base.wf.starter.dto.BizInfoDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.service.impl.WfProcServiceImpl;
import com.netwisd.base.portal.mapper.PortalContentSysjointsMapper;
import com.netwisd.base.portal.service.PortalContentSysjointsService;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentSysjointsDto;
import com.netwisd.base.portal.vo.PortalContentSysjointsVo;
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
 * @Description 系统集成类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 10:18:54
 */
@Service
@Slf4j
public class PortalContentSysjointsServiceImpl extends ServiceImpl<PortalContentSysjointsMapper, PortalContentSysjoints> implements PortalContentSysjointsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentSysjointsMapper portalContentSysjointsMapper;

    @Autowired
    private PortalContentSysjointsSonMapper sysjointsSonMapper;

    @Autowired
    private PortalContentSysjointsSonService sysjointsSonService;

//    @Autowired
//    private WfService wfService;

    /**
    * 单表简单查询操作
    * @param portalContentSysjointsDto
    * @return
    */
    @Override
    public Page list(PortalContentSysjointsDto portalContentSysjointsDto) {
        LambdaQueryWrapper<PortalContentSysjoints> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentSysjointsDto.getPortalId()), PortalContentSysjoints::getPortalId,portalContentSysjointsDto.getPortalId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentSysjointsDto.getPortalName()),PortalContentSysjoints::getPortalName,portalContentSysjointsDto.getPortalName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentSysjointsDto.getPartId()),PortalContentSysjoints::getPartId,portalContentSysjointsDto.getPartId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentSysjointsDto.getPartName()),PortalContentSysjoints::getPartName,portalContentSysjointsDto.getPartName());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentSysjointsDto.getPartTypeId()),PortalContentSysjoints::getPartTypeId,portalContentSysjointsDto.getPartTypeId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentSysjointsDto.getPartTypeName()),PortalContentSysjoints::getPartTypeName,portalContentSysjointsDto.getPartTypeName());
        queryWrapper.orderByDesc(PortalContentSysjoints::getCreateTime);
        queryWrapper.like(StringUtils.isNotBlank(portalContentSysjointsDto.getPartCode()), PortalContentSysjoints::getPartCode,portalContentSysjointsDto.getPartCode());
        Page<PortalContentSysjoints> page = portalContentSysjointsMapper.selectPage(portalContentSysjointsDto.getPage(),queryWrapper);
        Page<PortalContentSysjointsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentSysjointsVo.class);
        List<PortalContentSysjointsVo> records = pageVo.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            records.forEach(d->{
                List<PortalContentSysjointsSon> sysjointsSons = this.findSons(d.getId());
                if (CollectionUtils.isNotEmpty(sysjointsSons)) {
                    List<PortalContentSysjointsSonVo> sysjointsSonVos = DozerUtils.mapList(dozerMapper, sysjointsSons, PortalContentSysjointsSonVo.class);
                    d.setSysjointsSons(sysjointsSonVos);
                }
            });
        }
        pageVo.setRecords(records);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentSysjointsDto
    * @return
    */
    @Override
    public Page lists(PortalContentSysjointsDto portalContentSysjointsDto) {
        Page<PortalContentSysjointsVo> pageVo = portalContentSysjointsMapper.getPageList(portalContentSysjointsDto.getPage(),portalContentSysjointsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentSysjointsVo get(Long id) {
        PortalContentSysjoints portalContentSysjoints = super.getById(id);
        PortalContentSysjointsVo portalContentSysjointsVo = null;
        if(portalContentSysjoints !=null){
            portalContentSysjointsVo = dozerMapper.map(portalContentSysjoints,PortalContentSysjointsVo.class);
        }
        log.debug("查询成功");
        //查询子表
        List<PortalContentSysjointsSon> sysjointsSons = findSons(id);
        if (CollectionUtils.isNotEmpty(sysjointsSons)) {
            List<PortalContentSysjointsSonVo> sysjointsSonVos = DozerUtils.mapList(dozerMapper, sysjointsSons, PortalContentSysjointsSonVo.class);
            portalContentSysjointsVo.setSysjointsSons(sysjointsSonVos);
        }
        return portalContentSysjointsVo;
    }

    /**
    * 保存实体
    * @param portalContentSysjointsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentSysjointsDto portalContentSysjointsDto) {
//        portalContentSysjointsDto.setAuditStatus(AuditStateEnum.STARTING.getType());
        PortalContentSysjoints portalContentSysjoints = dozerMapper.map(portalContentSysjointsDto,PortalContentSysjoints.class);
        boolean result = super.save(portalContentSysjoints);
        if(result){
            log.debug("保存成功");
        }
        //保存子表
        List<PortalContentSysjointsSonDto> sysjointsSonDtos = portalContentSysjointsDto.getSysjointsSons();
        if (CollectionUtils.isNotEmpty(sysjointsSonDtos)) {
            List<PortalContentSysjointsSon> sysjointsSons = DozerUtils.mapList(dozerMapper, sysjointsSonDtos, PortalContentSysjointsSon.class);
            sysjointsSons.forEach(d->{
                d.setLinkSysjointsId(portalContentSysjoints.getId());
                d.setHits(0L);
            });
            sysjointsSonService.saveBatch(sysjointsSons);
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentSysjointsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentSysjointsDto portalContentSysjointsDto) {
        portalContentSysjointsDto.setUpdateTime(LocalDateTime.now());
        PortalContentSysjoints portalContentSysjoints = dozerMapper.map(portalContentSysjointsDto,PortalContentSysjoints.class);
        boolean result = super.updateById(portalContentSysjoints);
        if(result){
            log.debug("修改成功");
        }
        //修改子表
        //先删除
        LambdaQueryWrapper<PortalContentSysjointsSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentSysjointsSon::getLinkSysjointsId,portalContentSysjoints.getId());
        sysjointsSonMapper.delete(queryWrapper);
        //再新增
        List<PortalContentSysjointsSonDto> sysjointsSonDtos = portalContentSysjointsDto.getSysjointsSons();
        if (CollectionUtils.isNotEmpty(sysjointsSonDtos)) {
            List<PortalContentSysjointsSon> sysjointsSons = DozerUtils.mapList(dozerMapper, sysjointsSonDtos, PortalContentSysjointsSon.class);
            sysjointsSons.forEach(d->{
                d.setLinkSysjointsId(portalContentSysjoints.getId());
            });
            sysjointsSonService.saveBatch(sysjointsSons);
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean delete(String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new IncloudException("请选择要删除的对应选项");
        }
        List<String> idsList = Arrays.asList(ids.split(","));
        LambdaQueryWrapper<PortalContentSysjoints> sysjointsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysjointsLambdaQueryWrapper.in(PortalContentSysjoints::getId,idsList);
        portalContentSysjointsMapper.delete(sysjointsLambdaQueryWrapper);
        //删除子表
        idsList.forEach(d->{
            LambdaQueryWrapper<PortalContentSysjointsSon> sysjointsSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
            sysjointsSonLambdaQueryWrapper.eq(PortalContentSysjointsSon::getLinkSysjointsId,d);
            sysjointsSonMapper.delete(sysjointsSonLambdaQueryWrapper);
        });
        return true;
    }

    @Override
    public Page listForShow(PortalContentSysjointsDto portalContentSysjointsDto) {
        LambdaQueryWrapper<PortalContentSysjoints> sysjointsWrapper = new LambdaQueryWrapper<>();
        sysjointsWrapper.eq(StringUtils.isNotBlank(portalContentSysjointsDto.getPartCode()), PortalContentSysjoints::getPartCode,portalContentSysjointsDto.getPartCode());
        sysjointsWrapper.eq(ObjectUtils.isNotEmpty(portalContentSysjointsDto.getPortalId()), PortalContentSysjoints::getPortalId,portalContentSysjointsDto.getPortalId());
        List<PortalContentSysjoints> sysjointsList = portalContentSysjointsMapper.selectList(sysjointsWrapper);
        if (CollectionUtils.isNotEmpty(sysjointsList)) {
            List<Long> sysjointsIds = new ArrayList<>();
            sysjointsList.forEach(sysjoints->{
                sysjointsIds.add(sysjoints.getId());
            });
            LambdaQueryWrapper<PortalContentSysjointsSon> sysjointsSonWrapper = new LambdaQueryWrapper<>();
            sysjointsSonWrapper.in(PortalContentSysjointsSon::getLinkSysjointsId,sysjointsIds);
            sysjointsSonWrapper.isNotNull(portalContentSysjointsDto.getIsMobile() != null&&portalContentSysjointsDto.getIsMobile().equals(YesNo.YES.code),PortalContentSysjointsSon::getMobileSysUrl);
            sysjointsSonWrapper.ne(portalContentSysjointsDto.getIsMobile() != null&&portalContentSysjointsDto.getIsMobile().equals(YesNo.YES.code),PortalContentSysjointsSon::getMobileSysUrl,"");
            sysjointsSonWrapper.isNotNull(ObjectUtils.isEmpty(portalContentSysjointsDto.getIsMobile()),PortalContentSysjointsSon::getSysUrl);
            sysjointsSonWrapper.ne(ObjectUtils.isEmpty(portalContentSysjointsDto.getIsMobile()),PortalContentSysjointsSon::getSysUrl,"");
            sysjointsSonWrapper.orderByDesc(PortalContentSysjointsSon::getCreateTime);
            Page pageMsg = new Page();
            pageMsg.setSize(100);
            Page<PortalContentSysjointsSon> page = sysjointsSonMapper.selectPage(pageMsg, sysjointsSonWrapper);
            Page<PortalContentSysjointsSonVo> sysjointsSonVoPage = DozerUtils.mapPage(dozerMapper, page, PortalContentSysjointsSonVo.class);
            List<PortalContentSysjointsSonVo> sysjointsSonVoList = sysjointsSonVoPage.getRecords();
            if (CollectionUtils.isNotEmpty(sysjointsSonVoList)) {
                sysjointsSonVoList.forEach(sysjointsSonVo->{
                    sysjointsWrapper.clear();
                    sysjointsWrapper.eq(PortalContentSysjoints::getId,sysjointsSonVo.getLinkSysjointsId());
                    PortalContentSysjoints sysjoints = portalContentSysjointsMapper.selectOne(sysjointsWrapper);
                    sysjointsSonVo.setPortalId(sysjoints.getPortalId());
                    sysjointsSonVo.setPortalName(sysjoints.getPortalName());
                    sysjointsSonVo.setPartId(sysjoints.getPartId());
                    sysjointsSonVo.setPartCode(sysjoints.getPartCode());
                    sysjointsSonVo.setPartName(sysjoints.getPartName());
                    sysjointsSonVo.setPartTypeId(sysjoints.getPartTypeId());
                    sysjointsSonVo.setPartTypeName(sysjoints.getPartTypeName());
                });
                sysjointsSonVoPage.setRecords(sysjointsSonVoList);
            }
            return sysjointsSonVoPage;
        }
        return null;
    }

//    @Override
//    public Result procDel(String camundaProcinsId) {
//        PortalContentSysjoints sysjoints = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isNotEmpty(sysjoints)) {
//            //删除子表
//            LambdaQueryWrapper<PortalContentSysjointsSon> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(PortalContentSysjointsSon::getLinkSysjointsId,sysjoints.getId());
//            sysjointsSonMapper.delete(queryWrapper);
//            //删除主表
//            portalContentSysjointsMapper.deleteById(sysjoints);
//        }else {
//            throw new IncloudException("未找到相关信息！");
//        }
//        return Result.success();
//    }
//
//    @Override
//    public Result procStop(String camundaProcinsId) {
//        PortalContentSysjoints sysjoints = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(sysjoints)) {
//            throw new IncloudException("未找到相关信息！");
//        }
//        if (AuditStateEnum.COMPLETE.getType().equals(sysjoints.getAuditStatus())) {
//            throw new IncloudException("流程已完成审批,不能中止！");
//        }
//        sysjoints.setUpdateTime(LocalDateTime.now());
//        sysjoints.setAuditStatus(AuditStateEnum.TERMINATION.getType());
//        portalContentSysjointsMapper.updateById(sysjoints);
//        return Result.success();
//    }
//
//    @Override
//    public Result auditSucceed(String camundaProcinsId) {
//        PortalContentSysjoints sysjoints = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(sysjoints)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        sysjoints.setUpdateTime(LocalDateTime.now());
//        sysjoints.setPassTime(LocalDateTime.now());
//        sysjoints.setAuditStatus(AuditStateEnum.COMPLETE.getType());
//        portalContentSysjointsMapper.updateById(sysjoints);
//        return Result.success();
//    }

//    @Override
//    public PortalContentSysjointsVo procView(ProcViewDto procViewDto) {
//        PortalContentSysjoints sysjoints = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
//        if (ObjectUtils.isEmpty(sysjoints)) {
//            throw new IncloudException("未查到相关信息！");
//        }
//        PortalContentSysjointsVo sysjointsVo = dozerMapper.map(sysjoints, PortalContentSysjointsVo.class);
//        List<PortalContentSysjointsSon> sysjointsSons = this.findSons(sysjoints.getId());
//        if (CollectionUtils.isNotEmpty(sysjointsSons)) {
//            List<PortalContentSysjointsSonVo> sysjointsSonVos = DozerUtils.mapList(dozerMapper, sysjointsSons, PortalContentSysjointsSonVo.class);
//            sysjointsVo.setSysjointsSons(sysjointsSonVos);
//        }
//        return sysjointsVo;
//    }
//
//    @Override
//    @Transactional
//    public void procBizSubmit(PortalContentSysjointsDto portalContentSysjointsDto) {
//        try {
//            log.debug("系统集成类内容发布procSubmit参数：" + portalContentSysjointsDto.toString());
//            //根据流程 id 查询出具体的业务信息
//            PortalContentSysjoints sysjoints = this.queryByProcinsId(portalContentSysjointsDto.getCamundaProcinsId());
//            if(ObjectUtils.isEmpty(sysjoints)){
//                throw new IncloudException("没有查询出具体的系统集成类内容发布信息。");
//            }
//            //如果流程状态是起草，修改状态
//            if((sysjoints.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
//                //修改状态
//                portalContentSysjointsDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());
//            }
//            this.update(portalContentSysjointsDto);
//        }catch (Exception e) {
//            throw new IncloudException(e.getMessage());
//        }
//    }
//
//    @Override
//    @Transactional
//    public Result procSave(PortalContentSysjointsDto portalContentSysjointsDto) {
//        log.info("系统集成类内容发布procSave参数：" + portalContentSysjointsDto.toString());
//        if(StringUtils.isBlank(portalContentSysjointsDto.getCamundaProcinsId())) {
//            String maxBizKey = portalContentSysjointsMapper.getMaxBizKey();
//            log.info("系统集成类内容发布，最大的maxBizKey:{}",maxBizKey);
//            WfEngineDto wfEngine = portalContentSysjointsDto.getWfEngine();
//            log.info("系统集成类内容发布，启动工作流参数：{}",wfEngine);
//            BizInfoDto bizInfoDto = wfEngine.getBizInfoDto();
//            if (ObjectUtils.isEmpty(bizInfoDto)) {
//                bizInfoDto = new BizInfoDto();
//                bizInfoDto.setReason(portalContentSysjointsDto.getReason());
//                if (StringUtils.isBlank(maxBizKey)) {
//                    bizInfoDto.setBizKey("SYSJOINTS-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
//                }else {
//                    String bizKey = "SYSJOINTS-"+Long.valueOf(maxBizKey)+1;
//                    log.info("生成的code为：{}",bizKey);
//                    bizInfoDto.setBizKey(bizKey);
//                }
//            }else {
//                if (StringUtils.isBlank(maxBizKey)) {
//                    bizInfoDto.setBizKey("SYSJOINTS-"+DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
//                } else {
//                    String bizKey = "SYSJOINTS-"+Long.valueOf(maxBizKey)+1;
//                    log.info("生成的code为：{}",bizKey);
//                    bizInfoDto.setBizKey(bizKey);
//                }
//            }
//            EngineVo engineVo = wfService.startEngine(portalContentSysjointsDto);
//            wfService.setWfDto(portalContentSysjointsDto,engineVo);
//            this.save(portalContentSysjointsDto);
//            return Result.success(engineVo);
//        } else {
//            //修改业务信息
//            this.update(portalContentSysjointsDto);
//            return Result.success();
//        }
//    }
//
//    public PortalContentSysjoints queryByProcinsId(String camundaProcinsId){
//        LambdaQueryWrapper<PortalContentSysjoints> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(PortalContentSysjoints::getCamundaProcinsId,camundaProcinsId);
//        return portalContentSysjointsMapper.selectOne(queryWrapper);
//    }

    public List<PortalContentSysjointsSon> findSons(Long linkSysjointsId){
        LambdaQueryWrapper<PortalContentSysjointsSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentSysjointsSon::getLinkSysjointsId,linkSysjointsId);
        return sysjointsSonMapper.selectList(queryWrapper);
    }
}
