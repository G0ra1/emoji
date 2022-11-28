package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.wf.eunm.AuditStateEnum;
import com.netwisd.base.portal.config.PortalPublisher;
import com.netwisd.base.portal.constants.EventConstants;
import com.netwisd.base.portal.dto.PortalContentVideosDto;
import com.netwisd.base.portal.entity.*;
import com.netwisd.base.portal.mapper.*;
import com.netwisd.base.portal.service.PortalContentAdjVideosSonService;
import com.netwisd.base.portal.service.PortalContentHisVideosSonService;
import com.netwisd.base.portal.service.PortalContentVideosSonService;
import com.netwisd.base.portal.vo.PortalContentVideosVo;
import com.netwisd.base.wf.starter.dto.BizInfoDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.service.impl.WfProcServiceImpl;
import com.netwisd.base.portal.service.PortalContentAdjVideosService;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentAdjVideosDto;
import com.netwisd.base.portal.vo.PortalContentAdjVideosVo;
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
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @Description 视频类内容发布-调整表 功能描述...
 * @date 2021-08-31 01:42:07
 */
@Service
@Slf4j
public class PortalContentAdjVideosServiceImpl extends WfProcServiceImpl<PortalContentAdjVideosMapper, PortalContentAdjVideos, PortalContentAdjVideosDto, PortalContentAdjVideosVo> implements PortalContentAdjVideosService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentAdjVideosMapper portalContentAdjVideosMapper;
    @Autowired
    private WfService wfService;

    @Autowired
    PortalPublisher portalPublisher;

    @Autowired
    private PortalContentVideosMapper portalContentVideosMapper;

    @Autowired
    private PortalContentVideosSonMapper portalContentVideosSonMapper;

    @Autowired
    private PortalContentHisVideosMapper portalContentHisVideosMapper;

    @Autowired
    private PortalContentHisVideosSonService portalContentHisVideosSonService;

    @Autowired
    private PortalContentAdjVideosSonMapper portalContentAdjVideosSonMapper;
    @Autowired
    private PortalContentAdjVideosSonService portalContentAdjVideosSonService;
    @Autowired
    private PortalContentVideosSonService portalContentVideosSonService;

    /**
     * 单表简单查询操作
     *
     * @param portalContentAdjVideosDto
     * @return
     */
    @Override
    public Page list(PortalContentAdjVideosDto portalContentAdjVideosDto) {
        LambdaQueryWrapper<PortalContentAdjVideos> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalContentAdjVideos> page = portalContentAdjVideosMapper.selectPage(portalContentAdjVideosDto.getPage(), queryWrapper);
        Page<PortalContentAdjVideosVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjVideosVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 自定义查询操作
     *
     * @param portalContentAdjVideosDto
     * @return
     */
    @Override
    public List<PortalContentAdjVideosVo> lists(PortalContentAdjVideosDto portalContentAdjVideosDto) {
        LambdaQueryWrapper<PortalContentAdjVideos> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjVideosDto.getPortalId()), PortalContentAdjVideos::getPortalId, portalContentAdjVideosDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjVideosDto.getPartId()), PortalContentAdjVideos::getPartId, portalContentAdjVideosDto.getPartId());

        List<PortalContentAdjVideos> portalContentAdjVideosList = super.list(queryWrapper);
        List<PortalContentAdjVideosVo> portalContentAdjVideosVos = DozerUtils.mapList(dozerMapper, portalContentAdjVideosList, PortalContentAdjVideosVo.class);
        log.debug("查询条数:" + portalContentAdjVideosVos.size());
        return portalContentAdjVideosVos;
    }

    /**
     * 通过ID查询实体
     *
     * @param id
     * @return
     */
    @Override
    public PortalContentAdjVideosVo get(Long id) {
        PortalContentAdjVideos portalContentAdjVideos = super.getById(id);
        PortalContentAdjVideosVo portalContentAdjVideosVo = null;
        if (portalContentAdjVideos != null) {
            portalContentAdjVideosVo = dozerMapper.map(portalContentAdjVideos, PortalContentAdjVideosVo.class);
        }
        //子表信息
        List<PortalContentAdjVideosSon> adjVideosSon = this.getAdjVideosSon(id);
        portalContentAdjVideosVo.setPortalContentAdjVideosSonList(adjVideosSon);
        log.debug("查询成功");
        return portalContentAdjVideosVo;
    }

    /**
     * 保存实体
     *
     * @param portalContentAdjVideosDto
     * @return
     */
    @Transactional
    @Override
    public Boolean save(PortalContentAdjVideosDto portalContentAdjVideosDto) {
        PortalContentAdjVideos portalContentAdjVideos = dozerMapper.map(portalContentAdjVideosDto, PortalContentAdjVideos.class);
        boolean result = super.save(portalContentAdjVideos);
        if (result) {
            log.debug("视频类内容-调整表主表保存成功");
            //操作子表
            List<PortalContentAdjVideosSon> portalContentAdjVideosSonList = portalContentAdjVideosDto.getPortalContentAdjVideosSonList();
            if (CollectionUtils.isNotEmpty(portalContentAdjVideosSonList)) {
                portalContentAdjVideosSonList.forEach(d -> {
                    d.setHits(0L);
                    d.setLinkVideosId(portalContentAdjVideos.getId());
                });
                boolean b = portalContentAdjVideosSonService.saveBatch(portalContentAdjVideosSonList);
                if (b) {
                    log.debug("视频类内容-调整表子表保存成功");
                }
            }
        }
        return result;
    }

    /**
     * 修改实体
     *
     * @param portalContentAdjVideosDto
     * @return
     */
    @Transactional
    @Override
    public Boolean update(PortalContentAdjVideosDto portalContentAdjVideosDto) {
        portalContentAdjVideosDto.setUpdateTime(LocalDateTime.now());
        PortalContentAdjVideos portalContentAdjVideos = dozerMapper.map(portalContentAdjVideosDto, PortalContentAdjVideos.class);
        boolean result = super.updateById(portalContentAdjVideos);
        if (result) {
            log.debug("视频类内容-历史表主表修改成功");
            //修改子表
            List<PortalContentAdjVideosSon> portalContentAdjVideosSons = portalContentAdjVideosDto.getPortalContentAdjVideosSonList();
            if (CollectionUtils.isNotEmpty(portalContentAdjVideosSons)) {
                //先删除
                LambdaQueryWrapper<PortalContentAdjVideosSon> picturesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
                picturesSonLambdaQueryWrapper.eq(PortalContentAdjVideosSon::getLinkVideosId, portalContentAdjVideosDto.getId());
                portalContentAdjVideosSonMapper.delete(picturesSonLambdaQueryWrapper);
                //再新增
                portalContentAdjVideosSons.forEach(d -> {
                    d.setHits(0L);
                    d.setLinkVideosId(portalContentAdjVideos.getId());
                });
                portalContentAdjVideosSonService.saveBatch(portalContentAdjVideosSons);
            }


        }

        return result;
    }

    /**
     * 通过ID删除
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if (result) {
            log.debug("删除成功");
            //删除子表
            LambdaQueryWrapper<PortalContentAdjVideosSon> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PortalContentAdjVideosSon::getLinkVideosId, id);
            boolean remove = portalContentAdjVideosSonService.remove(queryWrapper);
            if (remove) {
                log.debug("视频类内容-历史表子表 删除成功");
            }
        }
        return result;
    }


    /**
     * 流程展示
     *
     * @param procViewDto
     * @return
     */
    @Override
    public PortalContentAdjVideosVo procView(ProcViewDto procViewDto) {

        PortalContentAdjVideos portalContentAdjVideos = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
        PortalContentAdjVideosVo portalContentAdjVideosVo = dozerMapper.map(portalContentAdjVideos, PortalContentAdjVideosVo.class);
        //子表信息
        List<PortalContentAdjVideosSon> adjVideosSon = this.getAdjVideosSon(portalContentAdjVideos.getId());
        portalContentAdjVideosVo.setPortalContentAdjVideosSonList(adjVideosSon);
        return portalContentAdjVideosVo;
    }

    /**
     * 流程提交
     *
     * @param portalContentAdjVideosDto
     */
    @Override
    @Transactional
    public void procBizSubmit(PortalContentAdjVideosDto portalContentAdjVideosDto) {
        try {
            log.debug("视频类内容发布procSubmit参数：" + portalContentAdjVideosDto.toString());
            //根据流程 id 查询出具体的业务信息
            PortalContentAdjVideos portalContentAdjVideos = this.queryByProcinsId(portalContentAdjVideosDto.getCamundaProcinsId());
            if (ObjectUtils.isEmpty(portalContentAdjVideos)) {
                throw new IncloudException("没有查询出具体的视频类内容发布的申请信息。");
            }
            //如果流程状态是起草，修改状态
            if ((portalContentAdjVideos.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
                //修改状态
                portalContentAdjVideosDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());

            }

            this.update(portalContentAdjVideosDto);
        } catch (Exception e) {
            throw new IncloudException(e.getMessage());
        }
    }

    /**
     * 流程保存
     *
     * @param portalContentAdjVideosDto
     * @return
     */
    @Override
    @Transactional
    public Result procSave(PortalContentAdjVideosDto portalContentAdjVideosDto) {
        log.info("视频类内容发布procSave参数：" + portalContentAdjVideosDto.toString());
        if (StringUtils.isBlank(portalContentAdjVideosDto.getCamundaProcinsId())) {
            String maxBizKey = portalContentAdjVideosMapper.getMaxBizKey();
            log.info("视频类内容发布，最大的maxBizKey:{}", maxBizKey);
            WfEngineDto.StartDto startDto = portalContentAdjVideosDto.getStartDto();
            log.info("视频类内容发布，启动工作流参数：{}", startDto);
            if (ObjectUtils.isEmpty(startDto)) {
                startDto.setReason(portalContentAdjVideosDto.getReason());
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJ-VIDEOS-" + DateUtil.formatDate(new Date(), "yyyyMMdd") + "001");
                } else {
                    String bizKey = "ADJ-VIDEOS-" + Long.valueOf(maxBizKey) + 1;
                    log.info("生成的code为：{}", bizKey);
                    startDto.setBizKey(bizKey);
                }
            } else {
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJ-BANNERS-" + DateUtil.formatDate(new Date(), "yyyyMMdd") + "001");
                } else {
                    String bizKey = "ADJ-BANNERS-" + Long.valueOf(maxBizKey) + 1;
                    log.info("生成的code为：{}", bizKey);
                    startDto.setBizKey(bizKey);
                }
            }

            EngineVo engineVo = wfService.startEngine(portalContentAdjVideosDto);
            wfService.setWfDto(portalContentAdjVideosDto, engineVo);
            this.save(portalContentAdjVideosDto);
            return Result.success(engineVo);
        } else {
            //修改业务信息
            this.update(portalContentAdjVideosDto);
            return Result.success();
        }
    }

    /**
     * 根据camundaProcinsId查内容
     *
     * @param camundaProcinsId
     * @return
     */
    public PortalContentAdjVideos queryByProcinsId(String camundaProcinsId) {
        LambdaQueryWrapper<PortalContentAdjVideos> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjVideos::getCamundaProcinsId, camundaProcinsId);
        return portalContentAdjVideosMapper.selectOne(queryWrapper);
    }

    /**
     * 流程删除
     *
     * @param camundaProcinsId
     * @return
     */
    @Override
    public Result procDel(String camundaProcinsId) {
        PortalContentAdjVideos portalContentAdjVideos = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjVideos)) {
            throw new IncloudException("没有找到对应信息！");
        }
        super.removeById(portalContentAdjVideos.getId());
        //删除子表
        LambdaQueryWrapper<PortalContentAdjVideosSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjVideosSon::getLinkVideosId, portalContentAdjVideos.getId());
        portalContentAdjVideosSonService.remove(queryWrapper);
        return Result.success();
    }

    /**
     * 流程终止
     *
     * @param camundaProcinsId
     * @return
     */
    @Override
    public Result procStop(String camundaProcinsId) {
        PortalContentAdjVideos portalContentAdjVideos = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjVideos)) {
            throw new IncloudException("没有找到对应信息！");
        }
        if (portalContentAdjVideos.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
            throw new IncloudException("流程已完成审批，不能终止！");
        }
        portalContentAdjVideos.setAuditStatus(AuditStateEnum.TERMINATION.getType());
        portalContentAdjVideosMapper.updateById(portalContentAdjVideos);
        return Result.success();
    }

    /**
     * 流程审批结束
     *
     * @param camundaProcinsId
     * @return
     */
    @Override
    public Result auditSucceed(String camundaProcinsId) {
        PortalContentAdjVideos portalContentAdjVideos = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjVideos)) {
            throw new IncloudException("没有找到对应信息！");
        }
        portalContentAdjVideos.setUpdateTime(LocalDateTime.now());
        portalContentAdjVideos.setPassTime(LocalDateTime.now());
        portalContentAdjVideos.setAuditStatus(AuditStateEnum.COMPLETE.getType());
        portalContentAdjVideosMapper.updateById(portalContentAdjVideos);


        //通过调整表的“关联主表id”字段查到主表信息
        PortalContentVideos portalContentVideos = portalContentVideosMapper.selectById(portalContentAdjVideos.getLinkVideosId());
        //通过调整表主表id查询子表
        LambdaQueryWrapper<PortalContentVideosSon> portalContentVideosSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
        portalContentVideosSonLambdaQueryWrapper.eq(PortalContentVideosSon::getLinkVideosId, portalContentVideos.getId());
        List<PortalContentVideosSon> portalContentVideosSons = portalContentVideosSonMapper.selectList(portalContentVideosSonLambdaQueryWrapper);

        //将主表信息覆盖到历史主表中
        PortalContentHisVideos portalContentHisVideos = dozerMapper.map(portalContentVideos, PortalContentHisVideos.class);
        portalContentHisVideosMapper.insert(portalContentHisVideos);
        //将主表的子表信息覆盖再历史表的子表中
        List<PortalContentHisVideosSon> portalContentHisVideosSons = DozerUtils.mapList(dozerMapper, portalContentVideosSons, PortalContentHisVideosSon.class);
        portalContentHisVideosSons.forEach(d -> {
            d.setLinkVideosId(portalContentHisVideos.getId());
        });
        portalContentHisVideosSonService.saveBatch(portalContentHisVideosSons);


        //把调整的主内容覆盖到主表中
        portalContentVideos.setId(portalContentAdjVideos.getLinkVideosId());
        portalContentVideos.setPortalId(portalContentAdjVideos.getPortalId());
        portalContentVideos.setPortalName(portalContentAdjVideos.getPortalName());
        portalContentVideos.setPartId(portalContentAdjVideos.getPartId());
        portalContentVideos.setPartName(portalContentAdjVideos.getPartName());
        portalContentVideos.setPartTypeId(portalContentAdjVideos.getPartTypeId());
        portalContentVideos.setPartTypeName(portalContentAdjVideos.getPartTypeName());
        portalContentVideos.setRemark(portalContentAdjVideos.getRemark());
        portalContentVideosMapper.updateById(portalContentVideos);

        //制造主表子表的信息
        List<PortalContentAdjVideosSon> portalContentAdjVideosSons = this.getAdjVideosSon(portalContentVideos.getId());
        portalContentAdjVideosSons.forEach(AdjVideosSon -> {
            //把关联主表id改成主表的id
            AdjVideosSon.setLinkVideosId(portalContentVideos.getId());
            //假如调整表中子表的数据只是修改，把调整表中的子表的点击量换成最新的
            portalContentVideosSons.forEach(videosSon -> {
                if (ObjectUtils.isNotEmpty(AdjVideosSon.getLinkVideosSonId()) && AdjVideosSon.getLinkVideosSonId().equals(videosSon.getId())) {
                    AdjVideosSon.setHits(videosSon.getHits());
                }
            });
        });
        //先将主表的子表信息先删除
        portalContentVideosSonMapper.delete(portalContentVideosSonLambdaQueryWrapper);
        //将调整表中的子表信息覆盖到主表中
        List<PortalContentVideosSon> portalContentVideosSonList = DozerUtils.mapList(dozerMapper, portalContentAdjVideosSons, PortalContentVideosSon.class);
        portalContentVideosSonService.saveBatch(portalContentVideosSonList);

        //审批完成后通知 生成静态文件 todo 未有子表信息
        Map<String, Object> eventData = new HashMap<>();
        eventData.put(EventConstants.BannerToHtmlFileHandle, portalContentAdjVideos);
        portalPublisher.publish(eventData);
        return Result.success();
    }

    /**
     * 获取子表信息
     *
     * @param LinkVideosId
     * @return
     */
    public List<PortalContentAdjVideosSon> getAdjVideosSon(Long LinkVideosId) {
        LambdaQueryWrapper<PortalContentAdjVideosSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjVideosSon::getLinkVideosId, LinkVideosId);
        List<PortalContentAdjVideosSon> portalContentAdjVideosSons = portalContentAdjVideosSonMapper.selectList(queryWrapper);
        if (portalContentAdjVideosSons.size() < 1) {
            throw new IncloudException("未查询到子表信息");
        }
        return portalContentAdjVideosSons;
    }
}
