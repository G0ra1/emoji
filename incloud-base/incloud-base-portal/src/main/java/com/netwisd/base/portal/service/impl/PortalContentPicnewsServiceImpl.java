package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.portal.config.PortalPublisher;
import com.netwisd.base.portal.constants.EventConstants;
import com.netwisd.base.portal.entity.PortalContentFiles;
import com.netwisd.base.wf.starter.dto.BizInfoDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.service.impl.WfProcServiceImpl;
import com.netwisd.base.portal.entity.PortalContentPicnews;
import com.netwisd.base.portal.mapper.PortalContentPicnewsMapper;
import com.netwisd.base.portal.service.PortalContentPicnewsService;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentPicnewsDto;
import com.netwisd.base.portal.vo.PortalContentPicnewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.common.core.util.Result;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description 图片新闻类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-20 10:09:51
 */
@Service
@Slf4j
public class PortalContentPicnewsServiceImpl extends ServiceImpl<PortalContentPicnewsMapper, PortalContentPicnews> implements PortalContentPicnewsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentPicnewsMapper portalContentPicnewsMapper;

    @Autowired
    private WfService wfService;

    @Autowired
    PortalPublisher portalPublisher;

    /**
    * 单表简单查询操作
    * @param portalContentPicnewsDto
    * @return
    */
    @Override
    public Page list(PortalContentPicnewsDto portalContentPicnewsDto) {
        LambdaQueryWrapper<PortalContentPicnews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentPicnewsDto.getPortalId()),PortalContentPicnews::getPortalId,portalContentPicnewsDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentPicnewsDto.getPartId()),PortalContentPicnews::getPartId,portalContentPicnewsDto.getPartId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentPicnewsDto.getPartTypeId()),PortalContentPicnews::getPartTypeId,portalContentPicnewsDto.getPartTypeId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentPicnewsDto.getTitle()),PortalContentPicnews::getTitle,portalContentPicnewsDto.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(portalContentPicnewsDto.getPartCode()), PortalContentPicnews::getPartCode,portalContentPicnewsDto.getPartCode());
//        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentPicnewsDto.getAuditStatus()),PortalContentPicnews::getAuditStatus,portalContentPicnewsDto.getAuditStatus());
        queryWrapper.orderByDesc(PortalContentPicnews::getCreateTime);
        Page<PortalContentPicnews> page = portalContentPicnewsMapper.selectPage(portalContentPicnewsDto.getPage(),queryWrapper);
        Page<PortalContentPicnewsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentPicnewsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentPicnewsDto
    * @return
    */
    @Override
    public Page lists(PortalContentPicnewsDto portalContentPicnewsDto) {
        Page<PortalContentPicnewsVo> pageVo = portalContentPicnewsMapper.getPageList(portalContentPicnewsDto.getPage(),portalContentPicnewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentPicnewsVo get(Long id) {
        PortalContentPicnews portalContentPicnews = super.getById(id);
        PortalContentPicnewsVo portalContentPicnewsVo = null;
        if(portalContentPicnews !=null){
            portalContentPicnewsVo = dozerMapper.map(portalContentPicnews,PortalContentPicnewsVo.class);
        }
        log.debug("查询成功");
        return portalContentPicnewsVo;
    }

    /**
    * 保存实体
    * @param portalContentPicnewsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentPicnewsDto portalContentPicnewsDto) {
        portalContentPicnewsDto.setHits(0L);
//        portalContentPicnewsDto.setAuditStatus(AuditStateEnum.STARTING.getType());
        PortalContentPicnews portalContentPicnews = dozerMapper.map(portalContentPicnewsDto,PortalContentPicnews.class);
        boolean result = super.save(portalContentPicnews);
        if(result){
            log.debug("保存成功");
        }
        //生成静态文件
        Map<String,Object> eventData = new HashMap<>();
        eventData.put(EventConstants.PicnewsToHtmlFileHandle,portalContentPicnews);
        portalPublisher.publish(eventData);
        return result;
    }

    /**
    * 修改实体
    * @param portalContentPicnewsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentPicnewsDto portalContentPicnewsDto) {
        portalContentPicnewsDto.setUpdateTime(LocalDateTime.now());
        PortalContentPicnews portalContentPicnews = dozerMapper.map(portalContentPicnewsDto,PortalContentPicnews.class);
        boolean result = super.updateById(portalContentPicnews);
        if(result){
            log.debug("修改成功");
        }
        //生成静态文件
        Map<String,Object> eventData = new HashMap<>();
        eventData.put(EventConstants.PicnewsToHtmlFileHandle,portalContentPicnews);
        portalPublisher.publish(eventData);
        return result;
    }

    @Override
    public Boolean delete(String ids) {
        if (StringUtils.isBlank(ids)) {
            throw new IncloudException("请选择要删除的选项");
        }
        List<String> idsList = Arrays.asList(ids.split(","));
        LambdaQueryWrapper<PortalContentPicnews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PortalContentPicnews::getId,idsList);
        int delete = portalContentPicnewsMapper.delete(queryWrapper);
        if (delete > 0) {
            return true;
        }
        throw new IncloudException("删除失败");
    }

    @Override
    public Boolean addHits(Long id) {
        LambdaQueryWrapper<PortalContentPicnews> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentPicnews::getId,id);
        PortalContentPicnews portalContentPicnews = portalContentPicnewsMapper.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(portalContentPicnews)) {
            throw new IncloudException("未找到相关信息,点击量无法增加");
        }
        portalContentPicnews.setHits(portalContentPicnews.getHits()+1);
        portalContentPicnewsMapper.updateById(portalContentPicnews);
        return true;
    }

//    @Override
//    public PortalContentPicnewsVo procView(ProcViewDto procViewDto) {
//        PortalContentPicnews portalContentPicnews = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
//        PortalContentPicnewsVo contentPicnewsVo = dozerMapper.map(portalContentPicnews, PortalContentPicnewsVo.class);
//        return contentPicnewsVo;
//    }
//
//    @Override
//    @Transactional
//    public Result procSave(PortalContentPicnewsDto portalContentPicnewsDto) {
//        log.info("图片新闻类内容发布procSave参数：" + portalContentPicnewsDto.toString());
//        if(StringUtils.isBlank(portalContentPicnewsDto.getCamundaProcinsId())) {
//            String maxBizKey = portalContentPicnewsMapper.getMaxBizKey();
//            log.info("图片新闻类内容发布，最大的maxBizKey:{}",maxBizKey);
//            WfEngineDto wfEngine = portalContentPicnewsDto.getWfEngine();
//            log.info("图片新闻类内容发布，启动工作流参数：{}",wfEngine);
//            BizInfoDto bizInfoDto = wfEngine.getBizInfoDto();
//            if (ObjectUtils.isEmpty(bizInfoDto)) {
//                bizInfoDto = new BizInfoDto();
//                bizInfoDto.setReason(portalContentPicnewsDto.getReason());
//                if (StringUtils.isBlank(maxBizKey)) {
//                    bizInfoDto.setBizKey("PICNEWS-"+ DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
//                }else {
//                    String bizKey = "PICNEWS-"+Long.valueOf(maxBizKey)+1;
//                    log.info("生成的code为：{}",bizKey);
//                    bizInfoDto.setBizKey(bizKey);
//                }
//            }else {
//                if (StringUtils.isBlank(maxBizKey)) {
//                    bizInfoDto.setBizKey("PICNEWS-"+DateUtil.formatDate(new Date(),"yyyyMMdd")+"001");
//                } else {
//                    String bizKey = "PICNEWS-"+Long.valueOf(maxBizKey)+1;
//                    log.info("生成的code为：{}",bizKey);
//                    bizInfoDto.setBizKey(bizKey);
//                }
//            }
//            EngineVo engineVo = wfService.startEngine(portalContentPicnewsDto);
//            wfService.setWfDto(portalContentPicnewsDto,engineVo);
//            this.save(portalContentPicnewsDto);
//            return Result.success(engineVo);
//        } else {
//            //修改业务信息
//            this.update(portalContentPicnewsDto);
//            return Result.success();
//        }
//    }
//
//    @Override
//    @Transactional
//    public void procBizSubmit(PortalContentPicnewsDto portalContentPicnewsDto) {
//        try {
//            log.debug("图片新闻类内容发布procSubmit参数：" + portalContentPicnewsDto.toString());
//            //验重title，同一个栏目下只能有一个不重复标题
//            LambdaQueryWrapper<PortalContentPicnews> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(PortalContentPicnews::getPartId,portalContentPicnewsDto.getPartId());
//            queryWrapper.eq(PortalContentPicnews::getTitle,portalContentPicnewsDto.getTitle());
//            List<PortalContentPicnews> titleSize = portalContentPicnewsMapper.selectList(queryWrapper);
//            if (titleSize.size() > 1) {
//                throw new IncloudException("一个栏目下只能有一个不重复标题！");
//            }
//            //根据流程 id 查询出具体的业务信息
//            PortalContentPicnews portalContentPicnews = this.queryByProcinsId(portalContentPicnewsDto.getCamundaProcinsId());
//            if(ObjectUtils.isEmpty(portalContentPicnews)){
//                throw new IncloudException("没有查询出具体的图片新闻类内容发布信息。");
//            }
//            //如果流程状态是起草，修改状态
//            if((portalContentPicnews.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
//                //修改状态
//                portalContentPicnewsDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());
//            }
//            this.update(portalContentPicnewsDto);
//        }catch (Exception e) {
//            throw new IncloudException(e.getMessage());
//        }
//    }
//
//    @Override
//    public Result procDel(String camundaProcinsId) {
//        PortalContentPicnews portalContentPicnews = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(portalContentPicnews)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        super.removeById(portalContentPicnews.getId());
//        return Result.success();
//    }
//
//    @Override
//    public Result procStop(String camundaProcinsId) {
//        PortalContentPicnews portalContentPicnews = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(portalContentPicnews)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        if (portalContentPicnews.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
//            throw new IncloudException("流程已完成审批，不能终止！");
//        }
//        portalContentPicnews.setAuditStatus(AuditStateEnum.TERMINATION.getType());
//        portalContentPicnewsMapper.updateById(portalContentPicnews);
//        return Result.success();
//    }
//
//    @Override
//    public Result auditSucceed(String camundaProcinsId) {
//        PortalContentPicnews portalContentPicnews = this.queryByProcinsId(camundaProcinsId);
//        if (ObjectUtils.isEmpty(portalContentPicnews)) {
//            throw new IncloudException("没有找到对应信息！");
//        }
//        portalContentPicnews.setUpdateTime(LocalDateTime.now());
//        portalContentPicnews.setPassTime(LocalDateTime.now());
//        portalContentPicnews.setAuditStatus(AuditStateEnum.COMPLETE.getType());
//        portalContentPicnewsMapper.updateById(portalContentPicnews);
//        //审批完成后通知 生成静态文件
//        Map<String,Object> eventData = new HashMap<>();
//        eventData.put(EventConstants.PicnewsToHtmlFileHandle,portalContentPicnews);
//        portalPublisher.publish(eventData);
//        return Result.success();
//    }
//
//    //根据camundaProcinsId查内容
//    public PortalContentPicnews queryByProcinsId(String camundaProcinsId){
//        LambdaQueryWrapper<PortalContentPicnews> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(PortalContentPicnews::getCamundaProcinsId,camundaProcinsId);
//        return portalContentPicnewsMapper.selectOne(queryWrapper);
//    }
}
