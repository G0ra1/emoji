package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.wf.eunm.AuditStateEnum;
import com.netwisd.base.portal.constants.EventConstants;
import com.netwisd.base.portal.entity.PortalContentHisNews;
import com.netwisd.base.portal.entity.PortalContentNews;
import com.netwisd.base.portal.mapper.PortalContentHisNewsMapper;
import com.netwisd.base.portal.mapper.PortalContentNewsMapper;
import com.netwisd.base.wf.starter.dto.BizInfoDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.service.impl.WfProcServiceImpl;
import com.netwisd.base.portal.entity.PortalContentAdjNews;
import com.netwisd.base.portal.mapper.PortalContentAdjNewsMapper;
import com.netwisd.base.portal.service.PortalContentAdjNewsService;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentAdjNewsDto;
import com.netwisd.base.portal.vo.PortalContentAdjNewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.common.core.util.Result;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 调整 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-27 15:26:39
 */
@Service
@Slf4j
public class PortalContentAdjNewsServiceImpl extends WfProcServiceImpl<PortalContentAdjNewsMapper, PortalContentAdjNews,PortalContentAdjNewsDto,PortalContentAdjNewsVo> implements PortalContentAdjNewsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentAdjNewsMapper portalContentAdjNewsMapper;

    @Autowired
    private PortalContentNewsMapper portalContentNewsMapper;

    @Autowired
    private PortalContentHisNewsMapper portalContentHisNewsMapper;

    @Autowired
    private WfService wfService;

    /**
    * 单表简单查询操作
    * @param portalContentAdjNewsDto
    * @return
    */
    @Override
    public Page list(PortalContentAdjNewsDto portalContentAdjNewsDto) {
        LambdaQueryWrapper<PortalContentAdjNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjNewsDto.getPortalId()), PortalContentAdjNews::getPortalId,portalContentAdjNewsDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjNewsDto.getPartId()),PortalContentAdjNews::getPartId,portalContentAdjNewsDto.getPartId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjNewsDto.getPartTypeId()),PortalContentAdjNews::getPartTypeId,portalContentAdjNewsDto.getPartTypeId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentAdjNewsDto.getTitle()),PortalContentAdjNews::getTitle,portalContentAdjNewsDto.getTitle());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjNewsDto.getAuditStatus()),PortalContentAdjNews::getAuditStatus,portalContentAdjNewsDto.getAuditStatus());
        Page<PortalContentAdjNews> page = portalContentAdjNewsMapper.selectPage(portalContentAdjNewsDto.getPage(),queryWrapper);
        Page<PortalContentAdjNewsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjNewsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentAdjNewsDto
    * @return
    */
    @Override
    public Page lists(PortalContentAdjNewsDto portalContentAdjNewsDto) {
        Page<PortalContentAdjNewsVo> pageVo = portalContentAdjNewsMapper.getPageList(portalContentAdjNewsDto.getPage(),portalContentAdjNewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentAdjNewsVo get(Long id) {
        PortalContentAdjNews portalContentAdjNews = super.getById(id);
        PortalContentAdjNewsVo portalContentAdjNewsVo = null;
        if(portalContentAdjNews !=null){
            portalContentAdjNewsVo = dozerMapper.map(portalContentAdjNews,PortalContentAdjNewsVo.class);
        }
        log.debug("查询成功");
        return portalContentAdjNewsVo;
    }

    /**
    * 保存实体
    * @param portalContentAdjNewsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentAdjNewsDto portalContentAdjNewsDto) {
        portalContentAdjNewsDto.setAuditStatus(AuditStateEnum.STARTING.getType());
        PortalContentAdjNews portalContentAdjNews = dozerMapper.map(portalContentAdjNewsDto,PortalContentAdjNews.class);
        boolean result = super.save(portalContentAdjNews);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentAdjNewsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentAdjNewsDto portalContentAdjNewsDto) {
        portalContentAdjNewsDto.setUpdateTime(LocalDateTime.now());
        PortalContentAdjNews portalContentAdjNews = dozerMapper.map(portalContentAdjNewsDto,PortalContentAdjNews.class);
        boolean result = super.updateById(portalContentAdjNews);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    @Override
    public PortalContentAdjNewsVo procView(ProcViewDto procViewDto) {
        PortalContentAdjNews portalContentAdjNews = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
        return dozerMapper.map(portalContentAdjNews,PortalContentAdjNewsVo.class);
    }

    @Override
    @Transactional
    public Result procSave(PortalContentAdjNewsDto portalContentAdjNewsDto) {
        log.info("调整新闻通告类内容申请procSave参数：" + portalContentAdjNewsDto.toString());
        if(StringUtils.isBlank(portalContentAdjNewsDto.getCamundaProcinsId())) {
            String maxBizKey = portalContentAdjNewsMapper.getMaxBizKey();
            log.info("调整新闻通告类内容申请，最大的maxBizKey:{}",maxBizKey);
            WfEngineDto.StartDto startDto = portalContentAdjNewsDto.getStartDto();
            log.info("调整新闻通告类内容申请，启动工作流参数：{}",startDto);
            if (ObjectUtils.isEmpty(startDto)) {
                startDto.setReason(portalContentAdjNewsDto.getReason());
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJNEWS-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
                }else {
                    String bizKey = "ADJNEWS-"+Long.valueOf(maxBizKey)+1;
                    log.info("生成的code为：{}",bizKey);
                    startDto.setBizKey(bizKey);
                }
            }else {
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJNEWS-"+DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
                } else {
                    String bizKey = "ADJNEWS-"+Long.valueOf(maxBizKey)+1;
                    log.info("生成的code为：{}",bizKey);
                    startDto.setBizKey(bizKey);
                }
            }
            EngineVo engineVo = wfService.startEngine(portalContentAdjNewsDto);
            wfService.setWfDto(portalContentAdjNewsDto,engineVo);
            this.save(portalContentAdjNewsDto);
            return Result.success(engineVo);
        } else {
            //修改业务信息
            this.update(portalContentAdjNewsDto);
            return Result.success();
        }
    }

    @Override
    @Transactional
    public void procBizSubmit(PortalContentAdjNewsDto portalContentAdjNewsDto) {
        try {
            log.debug("新闻通告类内容申请procSubmit参数：" + portalContentAdjNewsDto.toString());
            //验重title，同一个栏目下只能有一个不重复标题
            LambdaQueryWrapper<PortalContentAdjNews> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PortalContentAdjNews::getPartId,portalContentAdjNewsDto.getPartId());
            queryWrapper.eq(PortalContentAdjNews::getTitle,portalContentAdjNewsDto.getTitle());
            List<PortalContentAdjNews> titleSize = portalContentAdjNewsMapper.selectList(queryWrapper);
            if (titleSize.size() > 1) {
                throw new IncloudException("一个栏目下只能有一个不重复标题！");
            }
            //根据流程 id 查询出具体的业务信息
            PortalContentAdjNews portalContentAdjNews = this.queryByProcinsId(portalContentAdjNewsDto.getCamundaProcinsId());
            if(ObjectUtils.isEmpty(portalContentAdjNews)){
                throw new IncloudException("没有查询出具体的新闻通告类内容申请信息。");
            }
            //如果流程状态是起草，修改状态
            if((portalContentAdjNews.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
                //修改状态
                portalContentAdjNewsDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());
            }
            this.update(portalContentAdjNewsDto);
        }catch (Exception e) {
            throw new IncloudException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result procDel(String camundaProcinsId) {
        PortalContentAdjNews portalContentAdjNews = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjNews)) {
            throw new IncloudException("没有找到对应信息！");
        }
        super.removeById(portalContentAdjNews.getId());
        return Result.success();
    }

    @Override
    @Transactional
    public Result procStop(String camundaProcinsId) {
        PortalContentAdjNews portalContentAdjNews = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjNews)) {
            throw new IncloudException("没有找到对应信息！");
        }
        if (portalContentAdjNews.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
            throw new IncloudException("流程已完成审批，不能终止！");
        }
        portalContentAdjNews.setAuditStatus(AuditStateEnum.TERMINATION.getType());
        portalContentAdjNewsMapper.updateById(portalContentAdjNews);
        return Result.success();
    }

    @Override
    @Transactional
    public Result auditSucceed(String camundaProcinsId) {
        PortalContentAdjNews portalContentAdjNews = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjNews)) {
            throw new IncloudException("没有找到对应信息！");
        }
        //修改调整表信息
        portalContentAdjNews.setUpdateTime(LocalDateTime.now());
        portalContentAdjNews.setPassTime(LocalDateTime.now());
        portalContentAdjNews.setAuditStatus(AuditStateEnum.COMPLETE.getType());
        portalContentAdjNewsMapper.updateById(portalContentAdjNews);

        //将主表内容添加到历史记录表中
        PortalContentNews portalContentNews = portalContentNewsMapper.selectById(portalContentAdjNews.getLinkNewsId());
        PortalContentHisNews portalContentHisNews = dozerMapper.map(portalContentNews, PortalContentHisNews.class);
        portalContentHisNewsMapper.insert(portalContentHisNews);

        //把调整的内容覆盖到主表中
        portalContentNews.setId(portalContentAdjNews.getLinkNewsId());
        portalContentNews.setPortalId(portalContentAdjNews.getPortalId());
        portalContentNews.setPortalName(portalContentAdjNews.getPortalName());
        portalContentNews.setPartId(portalContentAdjNews.getPartId());
        portalContentNews.setPartName(portalContentAdjNews.getPartName());
        portalContentNews.setPartTypeId(portalContentAdjNews.getPartTypeId());
        portalContentNews.setPartTypeName(portalContentAdjNews.getPartTypeName());
        portalContentNews.setTitle(portalContentAdjNews.getTitle());
        portalContentNews.setContentUrl(portalContentAdjNews.getContentUrl());
        portalContentNews.setDescription(portalContentAdjNews.getDescription());
        portalContentNews.setRemark(portalContentAdjNews.getRemark());
        portalContentNews.setUeditorContent(portalContentAdjNews.getUeditorContent());
        portalContentNewsMapper.updateById(portalContentNews);

        return Result.success();
    }

    //根据camundaProcinsId查内容
    public PortalContentAdjNews queryByProcinsId(String camundaProcinsId){
        LambdaQueryWrapper<PortalContentAdjNews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjNews::getCamundaProcinsId,camundaProcinsId);
        return portalContentAdjNewsMapper.selectOne(queryWrapper);
    }
}
