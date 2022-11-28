package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.wf.eunm.AuditStateEnum;
import com.netwisd.base.portal.entity.*;
import com.netwisd.base.portal.mapper.PortalContentHisPicnewsMapper;
import com.netwisd.base.portal.mapper.PortalContentPicnewsMapper;
import com.netwisd.base.wf.starter.dto.BizInfoDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.service.impl.WfProcServiceImpl;
import com.netwisd.base.portal.mapper.PortalContentAdjPicnewsMapper;
import com.netwisd.base.portal.service.PortalContentAdjPicnewsService;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentAdjPicnewsDto;
import com.netwisd.base.portal.vo.PortalContentAdjPicnewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.common.core.util.Result;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Description 调整 图片新闻类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-31 04:25:00
 */
@Service
@Slf4j
public class PortalContentAdjPicnewsServiceImpl extends WfProcServiceImpl<PortalContentAdjPicnewsMapper, PortalContentAdjPicnews,PortalContentAdjPicnewsDto,PortalContentAdjPicnewsVo> implements PortalContentAdjPicnewsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentAdjPicnewsMapper portalContentAdjPicnewsMapper;

    @Autowired
    private PortalContentPicnewsMapper portalContentPicnewsMapper;

    @Autowired
    private PortalContentHisPicnewsMapper portalContentHisPicnewsMapper;

    @Autowired
    private WfService wfService;

    /**
    * 单表简单查询操作
    * @param portalContentAdjPicnewsDto
    * @return
    */
    @Override
    public Page list(PortalContentAdjPicnewsDto portalContentAdjPicnewsDto) {
        LambdaQueryWrapper<PortalContentAdjPicnews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjPicnewsDto.getPortalId()), PortalContentAdjPicnews::getPortalId,portalContentAdjPicnewsDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjPicnewsDto.getPartId()),PortalContentAdjPicnews::getPartId,portalContentAdjPicnewsDto.getPartId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjPicnewsDto.getPartTypeId()),PortalContentAdjPicnews::getPartTypeId,portalContentAdjPicnewsDto.getPartTypeId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentAdjPicnewsDto.getTitle()),PortalContentAdjPicnews::getTitle,portalContentAdjPicnewsDto.getTitle());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjPicnewsDto.getAuditStatus()),PortalContentAdjPicnews::getAuditStatus,portalContentAdjPicnewsDto.getAuditStatus());
        Page<PortalContentAdjPicnews> page = portalContentAdjPicnewsMapper.selectPage(portalContentAdjPicnewsDto.getPage(),queryWrapper);
        Page<PortalContentAdjPicnewsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjPicnewsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentAdjPicnewsDto
    * @return
    */
    @Override
    public Page lists(PortalContentAdjPicnewsDto portalContentAdjPicnewsDto) {
        Page<PortalContentAdjPicnewsVo> pageVo = portalContentAdjPicnewsMapper.getPageList(portalContentAdjPicnewsDto.getPage(),portalContentAdjPicnewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentAdjPicnewsVo get(Long id) {
        PortalContentAdjPicnews portalContentAdjPicnews = super.getById(id);
        PortalContentAdjPicnewsVo portalContentAdjPicnewsVo = null;
        if(portalContentAdjPicnews !=null){
            portalContentAdjPicnewsVo = dozerMapper.map(portalContentAdjPicnews,PortalContentAdjPicnewsVo.class);
        }
        log.debug("查询成功");
        return portalContentAdjPicnewsVo;
    }

    /**
    * 保存实体
    * @param portalContentAdjPicnewsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentAdjPicnewsDto portalContentAdjPicnewsDto) {
        //设置起草状态
        portalContentAdjPicnewsDto.setAuditStatus(AuditStateEnum.STARTING.getType());
        PortalContentAdjPicnews portalContentAdjPicnews = dozerMapper.map(portalContentAdjPicnewsDto,PortalContentAdjPicnews.class);
        boolean result = super.save(portalContentAdjPicnews);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentAdjPicnewsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentAdjPicnewsDto portalContentAdjPicnewsDto) {
        portalContentAdjPicnewsDto.setUpdateTime(LocalDateTime.now());
        PortalContentAdjPicnews portalContentAdjPicnews = dozerMapper.map(portalContentAdjPicnewsDto,PortalContentAdjPicnews.class);
        boolean result = super.updateById(portalContentAdjPicnews);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    @Override
    public PortalContentAdjPicnewsVo procView(ProcViewDto procViewDto) {
        PortalContentAdjPicnews portalContentAdjPicnews = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
        return dozerMapper.map(portalContentAdjPicnews, PortalContentAdjPicnewsVo.class);
    }

    @Override
    @Transactional
    public Result procSave(PortalContentAdjPicnewsDto portalContentAdjPicnewsDto) {
        log.info("调整图片新闻类内容发布procSave参数：" + portalContentAdjPicnewsDto.toString());
        if(StringUtils.isBlank(portalContentAdjPicnewsDto.getCamundaProcinsId())) {
            String maxBizKey = portalContentAdjPicnewsMapper.getMaxBizKey();
            log.info("调整图片新闻类内容发布，最大的maxBizKey:{}",maxBizKey);
            WfEngineDto.StartDto startDto = portalContentAdjPicnewsDto.getStartDto();
            log.info("调整图片新闻类内容发布，启动工作流参数：{}",startDto);
            if (ObjectUtils.isEmpty(startDto)) {
                startDto.setReason(portalContentAdjPicnewsDto.getReason());
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJPICNEWS-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
                }else {
                    String bizKey = "ADJPICNEWS-"+Long.valueOf(maxBizKey)+1;
                    log.info("生成的code为：{}",bizKey);
                    startDto.setBizKey(bizKey);
                }
            }else {
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJPICNEWS-"+DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
                } else {
                    String bizKey = "ADJPICNEWS-"+Long.valueOf(maxBizKey)+1;
                    log.info("生成的code为：{}",bizKey);
                    startDto.setBizKey(bizKey);
                }
            }
            EngineVo engineVo = wfService.startEngine(portalContentAdjPicnewsDto);
            wfService.setWfDto(portalContentAdjPicnewsDto,engineVo);
            this.save(portalContentAdjPicnewsDto);
            return Result.success(engineVo);
        } else {
            //修改业务信息
            this.update(portalContentAdjPicnewsDto);
            return Result.success();
        }
    }

    @Override
    @Transactional
    public void procBizSubmit(PortalContentAdjPicnewsDto portalContentAdjPicnewsDto) {
        try {
            log.debug("图片新闻类内容发布procSubmit参数：" + portalContentAdjPicnewsDto.toString());
            //验重title，同一个栏目下只能有一个不重复标题
            LambdaQueryWrapper<PortalContentAdjPicnews> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PortalContentAdjPicnews::getPartId,portalContentAdjPicnewsDto.getPartId());
            queryWrapper.eq(PortalContentAdjPicnews::getTitle,portalContentAdjPicnewsDto.getTitle());
            List<PortalContentAdjPicnews> titleSize = portalContentAdjPicnewsMapper.selectList(queryWrapper);
            if (titleSize.size() > 1) {
                throw new IncloudException("一个栏目下只能有一个不重复标题！");
            }
            //根据流程 id 查询出具体的业务信息
            PortalContentAdjPicnews portalContentAdjPicnews = this.queryByProcinsId(portalContentAdjPicnewsDto.getCamundaProcinsId());
            if(ObjectUtils.isEmpty(portalContentAdjPicnews)){
                throw new IncloudException("没有查询出具体的新闻通告类内容申请信息。");
            }
            //如果流程状态是起草，修改状态
            if((portalContentAdjPicnews.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
                //修改状态
                portalContentAdjPicnewsDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());
            }
            this.update(portalContentAdjPicnewsDto);
        }catch (Exception e) {
            throw new IncloudException(e.getMessage());
        }
    }

    @Override
    public Result procDel(String camundaProcinsId) {
        PortalContentAdjPicnews portalContentAdjPicnews = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjPicnews)) {
            throw new IncloudException("没有找到对应信息！");
        }
        super.removeById(portalContentAdjPicnews.getId());
        return Result.success();
    }

    @Override
    public Result procStop(String camundaProcinsId) {
        PortalContentAdjPicnews portalContentAdjPicnews = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjPicnews)) {
            throw new IncloudException("没有找到对应信息！");
        }
        if (portalContentAdjPicnews.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
            throw new IncloudException("流程已完成审批，不能终止！");
        }
        portalContentAdjPicnews.setAuditStatus(AuditStateEnum.TERMINATION.getType());
        portalContentAdjPicnewsMapper.updateById(portalContentAdjPicnews);
        return Result.success();
    }

    @Override
    public Result auditSucceed(String camundaProcinsId) {
        PortalContentAdjPicnews portalContentAdjPicnews = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjPicnews)) {
            throw new IncloudException("没有找到对应信息！");
        }
        //修改调整表信息
        portalContentAdjPicnews.setUpdateTime(LocalDateTime.now());
        portalContentAdjPicnews.setPassTime(LocalDateTime.now());
        portalContentAdjPicnews.setAuditStatus(AuditStateEnum.COMPLETE.getType());
        portalContentAdjPicnewsMapper.updateById(portalContentAdjPicnews);

        //将主表内容添加到历史记录表中
        PortalContentPicnews portalContentPicnews = portalContentPicnewsMapper.selectById(portalContentAdjPicnews.getLinkPicnewsId());
        PortalContentHisPicnews portalContentHisPicnews = dozerMapper.map(portalContentPicnews, PortalContentHisPicnews.class);
        portalContentHisPicnewsMapper.insert(portalContentHisPicnews);

        //把调整的内容覆盖到主表中
        portalContentPicnews.setId(portalContentAdjPicnews.getLinkPicnewsId());
        portalContentPicnews.setPortalId(portalContentAdjPicnews.getPortalId());
        portalContentPicnews.setPortalName(portalContentAdjPicnews.getPortalName());
        portalContentPicnews.setPartId(portalContentAdjPicnews.getPartId());
        portalContentPicnews.setPartName(portalContentAdjPicnews.getPartName());
        portalContentPicnews.setPartTypeId(portalContentAdjPicnews.getPartTypeId());
        portalContentPicnews.setPartTypeName(portalContentAdjPicnews.getPartTypeName());
        portalContentPicnews.setTitle(portalContentAdjPicnews.getTitle());
        portalContentPicnews.setContentUrl(portalContentAdjPicnews.getContentUrl());
        portalContentPicnews.setDescription(portalContentAdjPicnews.getDescription());
        portalContentPicnews.setRemark(portalContentAdjPicnews.getRemark());
        portalContentPicnews.setUeditorContent(portalContentAdjPicnews.getUeditorContent());
        portalContentPicnewsMapper.updateById(portalContentPicnews);

        return Result.success();
    }

    //根据camundaProcinsId查内容
    public PortalContentAdjPicnews queryByProcinsId(String camundaProcinsId){
        LambdaQueryWrapper<PortalContentAdjPicnews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjPicnews::getCamundaProcinsId,camundaProcinsId);
        return portalContentAdjPicnewsMapper.selectOne(queryWrapper);
    }
}
