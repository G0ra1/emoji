package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.wf.eunm.AuditStateEnum;
import com.netwisd.base.portal.dto.PortalContentAdjSysjointsSonDto;
import com.netwisd.base.portal.entity.*;
import com.netwisd.base.portal.mapper.*;
import com.netwisd.base.portal.service.PortalContentAdjSysjointsSonService;
import com.netwisd.base.portal.service.PortalContentHisSysjointsSonService;
import com.netwisd.base.portal.service.PortalContentSysjointsSonService;
import com.netwisd.base.portal.vo.PortalContentAdjSysjointsSonVo;
import com.netwisd.base.wf.starter.dto.BizInfoDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.service.impl.WfProcServiceImpl;
import com.netwisd.base.portal.service.PortalContentAdjSysjointsService;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentAdjSysjointsDto;
import com.netwisd.base.portal.vo.PortalContentAdjSysjointsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.common.core.util.Result;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description 调整 系统集成类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:39:14
 */
@Service
@Slf4j
public class PortalContentAdjSysjointsServiceImpl extends WfProcServiceImpl<PortalContentAdjSysjointsMapper, PortalContentAdjSysjoints,PortalContentAdjSysjointsDto,PortalContentAdjSysjointsVo> implements PortalContentAdjSysjointsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentAdjSysjointsMapper portalContentAdjSysjointsMapper;
    
    @Autowired
    private PortalContentAdjSysjointsSonMapper adjSysjointsSonMapper;
    
    @Autowired
    private PortalContentAdjSysjointsSonService adjSysjointsSonService;
    
    @Autowired
    private PortalContentHisSysjointsMapper hisSysjointsMapper;
    
    @Autowired
    private PortalContentHisSysjointsSonMapper hisSysjointsSonMapper;
    
    @Autowired
    private PortalContentHisSysjointsSonService hisSysjointsSonService;

    @Autowired
    private PortalContentSysjointsMapper sysjointsMapper;

    @Autowired
    private PortalContentSysjointsSonMapper sysjointsSonMapper;

    @Autowired
    private PortalContentSysjointsSonService sysjointsSonService;
    
    @Autowired
    private WfService wfService;

    /**
    * 列表查询
    * @param portalContentAdjSysjointsDto
    * @return
    */
    @Override
    public Page list(PortalContentAdjSysjointsDto portalContentAdjSysjointsDto) {
        LambdaQueryWrapper<PortalContentAdjSysjoints> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjSysjointsDto.getPortalId()), PortalContentAdjSysjoints::getPortalId,portalContentAdjSysjointsDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjSysjointsDto.getPartId()),PortalContentAdjSysjoints::getPartId,portalContentAdjSysjointsDto.getPartId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjSysjointsDto.getPartTypeId()),PortalContentAdjSysjoints::getPartTypeId,portalContentAdjSysjointsDto.getPartTypeId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjSysjointsDto.getAuditStatus()),PortalContentAdjSysjoints::getAuditStatus,portalContentAdjSysjointsDto.getAuditStatus());
        Page<PortalContentAdjSysjoints> page = portalContentAdjSysjointsMapper.selectPage(portalContentAdjSysjointsDto.getPage(),queryWrapper);
        Page<PortalContentAdjSysjointsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjSysjointsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        //查询子表信息
        List<PortalContentAdjSysjointsVo> records = pageVo.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            records.forEach(d->{
                List<PortalContentAdjSysjointsSon> adjSysjointsSons = this.findSons(d.getId());
                if (CollectionUtils.isNotEmpty(adjSysjointsSons)) {
                    List<PortalContentAdjSysjointsSonVo> adjSysjointsSonVos = DozerUtils.mapList(dozerMapper, adjSysjointsSons, PortalContentAdjSysjointsSonVo.class);
                    d.setAdjSysjointsSons(adjSysjointsSonVos);
                }
            });
            pageVo.setRecords(records);
        }
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentAdjSysjointsDto
    * @return
    */
    @Override
    public Page lists(PortalContentAdjSysjointsDto portalContentAdjSysjointsDto) {
        Page<PortalContentAdjSysjointsVo> pageVo = portalContentAdjSysjointsMapper.getPageList(portalContentAdjSysjointsDto.getPage(),portalContentAdjSysjointsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentAdjSysjointsVo get(Long id) {
        PortalContentAdjSysjoints portalContentAdjSysjoints = super.getById(id);
        PortalContentAdjSysjointsVo portalContentAdjSysjointsVo = null;
        if(portalContentAdjSysjoints !=null){
            portalContentAdjSysjointsVo = dozerMapper.map(portalContentAdjSysjoints,PortalContentAdjSysjointsVo.class);
            //查询子表
            List<PortalContentAdjSysjointsSon> adjSysjointsSons = this.findSons(id);
            if (CollectionUtils.isNotEmpty(adjSysjointsSons)) {
                List<PortalContentAdjSysjointsSonVo> adjSysjointsSonVos = DozerUtils.mapList(dozerMapper, adjSysjointsSons, PortalContentAdjSysjointsSonVo.class);
                portalContentAdjSysjointsVo.setAdjSysjointsSons(adjSysjointsSonVos);
            }
        }
        log.debug("查询成功");
        return portalContentAdjSysjointsVo;
    }

    /**
    * 保存实体
    * @param portalContentAdjSysjointsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentAdjSysjointsDto portalContentAdjSysjointsDto) {
        portalContentAdjSysjointsDto.setAuditStatus(AuditStateEnum.STARTING.getType());
        PortalContentAdjSysjoints portalContentAdjSysjoints = dozerMapper.map(portalContentAdjSysjointsDto,PortalContentAdjSysjoints.class);
        boolean result = super.save(portalContentAdjSysjoints);
        if(result){
            log.debug("保存成功");
        }
        //新增子表
        List<PortalContentAdjSysjointsSonDto> adjSysjointsSonDtos = portalContentAdjSysjointsDto.getAdjSysjointsSons();
        if (CollectionUtils.isNotEmpty(adjSysjointsSonDtos)) {
            List<PortalContentAdjSysjointsSon> adjSysjointsSons = DozerUtils.mapList(dozerMapper, adjSysjointsSonDtos, PortalContentAdjSysjointsSon.class);
            adjSysjointsSons.forEach(d->{
                d.setLinkSysjointsId(portalContentAdjSysjoints.getId());
                if (ObjectUtils.isEmpty(d.getLinkSysjointsSonId())) {
                    d.setHits(0L);
                }
            });
            adjSysjointsSonService.saveBatch(adjSysjointsSons);
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentAdjSysjointsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentAdjSysjointsDto portalContentAdjSysjointsDto) {
        portalContentAdjSysjointsDto.setUpdateTime(LocalDateTime.now());
        PortalContentAdjSysjoints portalContentAdjSysjoints = dozerMapper.map(portalContentAdjSysjointsDto,PortalContentAdjSysjoints.class);
        boolean result = super.updateById(portalContentAdjSysjoints);
        if(result){
            log.debug("修改成功");
        }
        //修改子表
        //先删除
        LambdaQueryWrapper<PortalContentAdjSysjointsSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjSysjointsSon::getLinkSysjointsId,portalContentAdjSysjoints.getId());
        adjSysjointsSonMapper.delete(queryWrapper);
        //再新增
        List<PortalContentAdjSysjointsSonDto> adjSysjointsSonDtos = portalContentAdjSysjointsDto.getAdjSysjointsSons();
        if (CollectionUtils.isNotEmpty(adjSysjointsSonDtos)) {
            List<PortalContentAdjSysjointsSon> adjSysjointsSons = DozerUtils.mapList(dozerMapper, adjSysjointsSonDtos, PortalContentAdjSysjointsSon.class);
            adjSysjointsSons.forEach(d->{
                d.setLinkSysjointsId(portalContentAdjSysjoints.getId());
            });
            adjSysjointsSonService.saveBatch(adjSysjointsSons);
        }
        return result;
    }

    @Override
    @Transactional
    public Result procDel(String camundaProcinsId) {
        PortalContentAdjSysjoints adjSysjoints = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isNotEmpty(adjSysjoints)) {
            throw new IncloudException("未找到对应相关信息！");
        }
        super.removeById(adjSysjoints.getId());
        //删除子表
        LambdaQueryWrapper<PortalContentAdjSysjointsSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjSysjointsSon::getLinkSysjointsId,adjSysjoints.getId());
        adjSysjointsSonMapper.delete(queryWrapper);
        return Result.success();
    }

    @Override
    public Result procStop(String camundaProcinsId) {
        PortalContentAdjSysjoints adjSysjoints = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(adjSysjoints)) {
            throw new IncloudException("没有找到对应信息！");
        }
        if (adjSysjoints.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
            throw new IncloudException("流程已完成审批，不能终止！");
        }
        adjSysjoints.setAuditStatus(AuditStateEnum.TERMINATION.getType());
        portalContentAdjSysjointsMapper.updateById(adjSysjoints);
        return Result.success();
    }

    @Override
    @Transactional
    public Result auditSucceed(String camundaProcinsId) {
        PortalContentAdjSysjoints adjSysjoints = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(adjSysjoints)) {
            throw new IncloudException("没有找到对应信息！");
        }
        //修改调整表信息
        adjSysjoints.setUpdateTime(LocalDateTime.now());
        adjSysjoints.setPassTime(LocalDateTime.now());
        adjSysjoints.setAuditStatus(AuditStateEnum.COMPLETE.getType());
        portalContentAdjSysjointsMapper.updateById(adjSysjoints);

        //将主表内容添加到历史表中
        //查主表信息
        PortalContentSysjoints sysjoints = sysjointsMapper.selectById(adjSysjoints.getLinkMainSysjointsId());
        //查子表信息
        LambdaQueryWrapper<PortalContentSysjointsSon> sysjointsSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysjointsSonLambdaQueryWrapper.eq(PortalContentSysjointsSon::getLinkSysjointsId,sysjoints.getId());
        List<PortalContentSysjointsSon> sysjointsSons = sysjointsSonMapper.selectList(sysjointsSonLambdaQueryWrapper);
        //将主表信息添加到历史表中
        PortalContentHisSysjoints hisSysjoints = dozerMapper.map(sysjoints, PortalContentHisSysjoints.class);
        hisSysjoints.setLinkMainSysjointsId(hisSysjoints.getId());
        hisSysjoints.setId(null);
        hisSysjointsMapper.insert(hisSysjoints);
        //将子表信息添加到历史表中
        List<PortalContentHisSysjointsSon> hisSysjointsSons = DozerUtils.mapList(dozerMapper, sysjointsSons, PortalContentHisSysjointsSon.class);
        hisSysjointsSons.forEach(d->{
            d.setId(null);
            d.setLinkSysjointsId(hisSysjoints.getId());
        });
        hisSysjointsSonService.saveBatch(hisSysjointsSons);

        //将调整表信息覆盖到主表中
        //主表覆盖
        sysjoints.setId(adjSysjoints.getLinkMainSysjointsId());
        sysjoints.setPortalId(adjSysjoints.getPortalId());
        sysjoints.setPortalName(adjSysjoints.getPortalName());
        sysjoints.setPartId(adjSysjoints.getPartId());
        sysjoints.setPartName(adjSysjoints.getPartName());
        sysjoints.setPartTypeId(adjSysjoints.getPartTypeId());
        sysjoints.setPartTypeName(adjSysjoints.getPartTypeName());
        sysjoints.setRemark(adjSysjoints.getRemark());
        sysjointsMapper.updateById(sysjoints);
        //子表覆盖
        //查询调整表的子表信息
        List<PortalContentAdjSysjointsSon> adjSysjointsSons = this.findSons(adjSysjoints.getId());
        adjSysjointsSons.forEach(newSon->{
            //把关联主表id改成主表的id
            newSon.setLinkSysjointsId(sysjoints.getId());
            //假如调整表中子表的数据只是修改，把调整表中的子表的点击量换成最新的
            sysjointsSons.forEach(oldSon->{
                if (ObjectUtils.isNotEmpty(newSon.getLinkSysjointsSonId()) && newSon.getLinkSysjointsSonId().equals(oldSon.getId())) {
                    newSon.setHits(oldSon.getHits());
                }
            });
        });
        //删除主表中的子表数据
        sysjointsSonMapper.delete(sysjointsSonLambdaQueryWrapper);
        //将调整表中的子表信息覆盖到主表中
        List<PortalContentSysjointsSon> newSysjointsSons = DozerUtils.mapList(dozerMapper, adjSysjointsSons, PortalContentSysjointsSon.class);
        sysjointsSonService.saveBatch(newSysjointsSons);
        return Result.success();
    }

    @Override
    public PortalContentAdjSysjointsVo procView(ProcViewDto procViewDto) {
        PortalContentAdjSysjoints adjSysjoints = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
        if (ObjectUtils.isNotEmpty(adjSysjoints)) {
            PortalContentAdjSysjointsVo adjSysjointsVo = dozerMapper.map(adjSysjoints, PortalContentAdjSysjointsVo.class);
            List<PortalContentAdjSysjointsSon> adjSysjointsSons = this.findSons(adjSysjointsVo.getId());
            if (CollectionUtils.isNotEmpty(adjSysjointsSons)) {
                List<PortalContentAdjSysjointsSonVo> adjSysjointsSonVos = DozerUtils.mapList(dozerMapper, adjSysjointsSons, PortalContentAdjSysjointsSonVo.class);
                adjSysjointsVo.setAdjSysjointsSons(adjSysjointsSonVos);
            }
            return adjSysjointsVo;
        }
        return null;
    }

    @Override
    @Transactional
    public void procBizSubmit(PortalContentAdjSysjointsDto portalContentAdjSysjointsDto) {
        try {
            log.debug("调整 系统集成类内容发布procSubmit参数：" + portalContentAdjSysjointsDto.toString());
            //根据流程 id 查询出具体的业务信息
            PortalContentAdjSysjoints adjSysjoints = this.queryByProcinsId(portalContentAdjSysjointsDto.getCamundaProcinsId());
            if(ObjectUtils.isEmpty(adjSysjoints)){
                throw new IncloudException("没有查询出具体的调整系统集成类内容发布信息。");
            }
            //如果流程状态是起草，修改状态
            if((adjSysjoints.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
                //修改状态
                portalContentAdjSysjointsDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());
            }
            this.update(portalContentAdjSysjointsDto);
        }catch (Exception e) {
            throw new IncloudException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result procSave(PortalContentAdjSysjointsDto portalContentAdjSysjointsDto) {
        log.info("调整系统集成类内容发布procSave参数：" + portalContentAdjSysjointsDto.toString());
        if(StringUtils.isBlank(portalContentAdjSysjointsDto.getCamundaProcinsId())) {
            String maxBizKey = portalContentAdjSysjointsMapper.getMaxBizKey();
            log.info("调整系统集成类内容发布，最大的maxBizKey:{}",maxBizKey);
            WfEngineDto.StartDto startDto = portalContentAdjSysjointsDto.getStartDto();
            log.info("调整系统集成类内容发布，启动工作流参数：{}",startDto);
            if (ObjectUtils.isEmpty(startDto)) {
                startDto.setReason(portalContentAdjSysjointsDto.getReason());
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJSYSJOINTS-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
                }else {
                    String bizKey = "ADJSYSJOINTS-"+Long.valueOf(maxBizKey)+1;
                    log.info("生成的code为：{}",bizKey);
                    startDto.setBizKey(bizKey);
                }
            }else {
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJSYSJOINTS-"+DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
                } else {
                    String bizKey = "ADJSYSJOINTS-"+Long.valueOf(maxBizKey)+1;
                    log.info("生成的code为：{}",bizKey);
                    startDto.setBizKey(bizKey);
                }
            }
            EngineVo engineVo = wfService.startEngine(portalContentAdjSysjointsDto);
            wfService.setWfDto(portalContentAdjSysjointsDto,engineVo);
            this.save(portalContentAdjSysjointsDto);
            return Result.success(engineVo);
        } else {
            //修改业务信息
            this.update(portalContentAdjSysjointsDto);
            return Result.success();
        }
    }
    
    public PortalContentAdjSysjoints queryByProcinsId(String camundaProcinsId){
        LambdaQueryWrapper<PortalContentAdjSysjoints> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjSysjoints::getCamundaProcinsId,camundaProcinsId);
        return portalContentAdjSysjointsMapper.selectOne(queryWrapper);
    }
    
    public List<PortalContentAdjSysjointsSon> findSons(Long linkSysjointsId){
        LambdaQueryWrapper<PortalContentAdjSysjointsSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjSysjointsSon::getLinkSysjointsId,linkSysjointsId);
        return adjSysjointsSonMapper.selectList(queryWrapper);
    }
}
