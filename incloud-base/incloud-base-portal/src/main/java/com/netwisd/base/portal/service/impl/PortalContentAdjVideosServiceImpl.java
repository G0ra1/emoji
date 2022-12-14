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
 * @author ???????????? cuiran@netwisd.com@netwisd.com
 * @Description ?????????????????????-????????? ????????????...
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
     * ????????????????????????
     *
     * @param portalContentAdjVideosDto
     * @return
     */
    @Override
    public Page list(PortalContentAdjVideosDto portalContentAdjVideosDto) {
        LambdaQueryWrapper<PortalContentAdjVideos> queryWrapper = new LambdaQueryWrapper<>();
        //????????????????????????????????????????????????

        Page<PortalContentAdjVideos> page = portalContentAdjVideosMapper.selectPage(portalContentAdjVideosDto.getPage(), queryWrapper);
        Page<PortalContentAdjVideosVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjVideosVo.class);
        log.debug("????????????:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * ?????????????????????
     *
     * @param portalContentAdjVideosDto
     * @return
     */
    @Override
    public List<PortalContentAdjVideosVo> lists(PortalContentAdjVideosDto portalContentAdjVideosDto) {
        LambdaQueryWrapper<PortalContentAdjVideos> queryWrapper = new LambdaQueryWrapper<>();
        //????????????????????????????????????????????????
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjVideosDto.getPortalId()), PortalContentAdjVideos::getPortalId, portalContentAdjVideosDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjVideosDto.getPartId()), PortalContentAdjVideos::getPartId, portalContentAdjVideosDto.getPartId());

        List<PortalContentAdjVideos> portalContentAdjVideosList = super.list(queryWrapper);
        List<PortalContentAdjVideosVo> portalContentAdjVideosVos = DozerUtils.mapList(dozerMapper, portalContentAdjVideosList, PortalContentAdjVideosVo.class);
        log.debug("????????????:" + portalContentAdjVideosVos.size());
        return portalContentAdjVideosVos;
    }

    /**
     * ??????ID????????????
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
        //????????????
        List<PortalContentAdjVideosSon> adjVideosSon = this.getAdjVideosSon(id);
        portalContentAdjVideosVo.setPortalContentAdjVideosSonList(adjVideosSon);
        log.debug("????????????");
        return portalContentAdjVideosVo;
    }

    /**
     * ????????????
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
            log.debug("???????????????-???????????????????????????");
            //????????????
            List<PortalContentAdjVideosSon> portalContentAdjVideosSonList = portalContentAdjVideosDto.getPortalContentAdjVideosSonList();
            if (CollectionUtils.isNotEmpty(portalContentAdjVideosSonList)) {
                portalContentAdjVideosSonList.forEach(d -> {
                    d.setHits(0L);
                    d.setLinkVideosId(portalContentAdjVideos.getId());
                });
                boolean b = portalContentAdjVideosSonService.saveBatch(portalContentAdjVideosSonList);
                if (b) {
                    log.debug("???????????????-???????????????????????????");
                }
            }
        }
        return result;
    }

    /**
     * ????????????
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
            log.debug("???????????????-???????????????????????????");
            //????????????
            List<PortalContentAdjVideosSon> portalContentAdjVideosSons = portalContentAdjVideosDto.getPortalContentAdjVideosSonList();
            if (CollectionUtils.isNotEmpty(portalContentAdjVideosSons)) {
                //?????????
                LambdaQueryWrapper<PortalContentAdjVideosSon> picturesSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
                picturesSonLambdaQueryWrapper.eq(PortalContentAdjVideosSon::getLinkVideosId, portalContentAdjVideosDto.getId());
                portalContentAdjVideosSonMapper.delete(picturesSonLambdaQueryWrapper);
                //?????????
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
     * ??????ID??????
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if (result) {
            log.debug("????????????");
            //????????????
            LambdaQueryWrapper<PortalContentAdjVideosSon> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(PortalContentAdjVideosSon::getLinkVideosId, id);
            boolean remove = portalContentAdjVideosSonService.remove(queryWrapper);
            if (remove) {
                log.debug("???????????????-??????????????? ????????????");
            }
        }
        return result;
    }


    /**
     * ????????????
     *
     * @param procViewDto
     * @return
     */
    @Override
    public PortalContentAdjVideosVo procView(ProcViewDto procViewDto) {

        PortalContentAdjVideos portalContentAdjVideos = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
        PortalContentAdjVideosVo portalContentAdjVideosVo = dozerMapper.map(portalContentAdjVideos, PortalContentAdjVideosVo.class);
        //????????????
        List<PortalContentAdjVideosSon> adjVideosSon = this.getAdjVideosSon(portalContentAdjVideos.getId());
        portalContentAdjVideosVo.setPortalContentAdjVideosSonList(adjVideosSon);
        return portalContentAdjVideosVo;
    }

    /**
     * ????????????
     *
     * @param portalContentAdjVideosDto
     */
    @Override
    @Transactional
    public void procBizSubmit(PortalContentAdjVideosDto portalContentAdjVideosDto) {
        try {
            log.debug("?????????????????????procSubmit?????????" + portalContentAdjVideosDto.toString());
            //???????????? id ??????????????????????????????
            PortalContentAdjVideos portalContentAdjVideos = this.queryByProcinsId(portalContentAdjVideosDto.getCamundaProcinsId());
            if (ObjectUtils.isEmpty(portalContentAdjVideos)) {
                throw new IncloudException("???????????????????????????????????????????????????????????????");
            }
            //??????????????????????????????????????????
            if ((portalContentAdjVideos.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
                //????????????
                portalContentAdjVideosDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());

            }

            this.update(portalContentAdjVideosDto);
        } catch (Exception e) {
            throw new IncloudException(e.getMessage());
        }
    }

    /**
     * ????????????
     *
     * @param portalContentAdjVideosDto
     * @return
     */
    @Override
    @Transactional
    public Result procSave(PortalContentAdjVideosDto portalContentAdjVideosDto) {
        log.info("?????????????????????procSave?????????" + portalContentAdjVideosDto.toString());
        if (StringUtils.isBlank(portalContentAdjVideosDto.getCamundaProcinsId())) {
            String maxBizKey = portalContentAdjVideosMapper.getMaxBizKey();
            log.info("?????????????????????????????????maxBizKey:{}", maxBizKey);
            WfEngineDto.StartDto startDto = portalContentAdjVideosDto.getStartDto();
            log.info("????????????????????????????????????????????????{}", startDto);
            if (ObjectUtils.isEmpty(startDto)) {
                startDto.setReason(portalContentAdjVideosDto.getReason());
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJ-VIDEOS-" + DateUtil.formatDate(new Date(), "yyyyMMdd") + "001");
                } else {
                    String bizKey = "ADJ-VIDEOS-" + Long.valueOf(maxBizKey) + 1;
                    log.info("?????????code??????{}", bizKey);
                    startDto.setBizKey(bizKey);
                }
            } else {
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJ-BANNERS-" + DateUtil.formatDate(new Date(), "yyyyMMdd") + "001");
                } else {
                    String bizKey = "ADJ-BANNERS-" + Long.valueOf(maxBizKey) + 1;
                    log.info("?????????code??????{}", bizKey);
                    startDto.setBizKey(bizKey);
                }
            }

            EngineVo engineVo = wfService.startEngine(portalContentAdjVideosDto);
            wfService.setWfDto(portalContentAdjVideosDto, engineVo);
            this.save(portalContentAdjVideosDto);
            return Result.success(engineVo);
        } else {
            //??????????????????
            this.update(portalContentAdjVideosDto);
            return Result.success();
        }
    }

    /**
     * ??????camundaProcinsId?????????
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
     * ????????????
     *
     * @param camundaProcinsId
     * @return
     */
    @Override
    public Result procDel(String camundaProcinsId) {
        PortalContentAdjVideos portalContentAdjVideos = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjVideos)) {
            throw new IncloudException("???????????????????????????");
        }
        super.removeById(portalContentAdjVideos.getId());
        //????????????
        LambdaQueryWrapper<PortalContentAdjVideosSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjVideosSon::getLinkVideosId, portalContentAdjVideos.getId());
        portalContentAdjVideosSonService.remove(queryWrapper);
        return Result.success();
    }

    /**
     * ????????????
     *
     * @param camundaProcinsId
     * @return
     */
    @Override
    public Result procStop(String camundaProcinsId) {
        PortalContentAdjVideos portalContentAdjVideos = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjVideos)) {
            throw new IncloudException("???????????????????????????");
        }
        if (portalContentAdjVideos.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
            throw new IncloudException("???????????????????????????????????????");
        }
        portalContentAdjVideos.setAuditStatus(AuditStateEnum.TERMINATION.getType());
        portalContentAdjVideosMapper.updateById(portalContentAdjVideos);
        return Result.success();
    }

    /**
     * ??????????????????
     *
     * @param camundaProcinsId
     * @return
     */
    @Override
    public Result auditSucceed(String camundaProcinsId) {
        PortalContentAdjVideos portalContentAdjVideos = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjVideos)) {
            throw new IncloudException("???????????????????????????");
        }
        portalContentAdjVideos.setUpdateTime(LocalDateTime.now());
        portalContentAdjVideos.setPassTime(LocalDateTime.now());
        portalContentAdjVideos.setAuditStatus(AuditStateEnum.COMPLETE.getType());
        portalContentAdjVideosMapper.updateById(portalContentAdjVideos);


        //?????????????????????????????????id???????????????????????????
        PortalContentVideos portalContentVideos = portalContentVideosMapper.selectById(portalContentAdjVideos.getLinkVideosId());
        //?????????????????????id????????????
        LambdaQueryWrapper<PortalContentVideosSon> portalContentVideosSonLambdaQueryWrapper = new LambdaQueryWrapper<>();
        portalContentVideosSonLambdaQueryWrapper.eq(PortalContentVideosSon::getLinkVideosId, portalContentVideos.getId());
        List<PortalContentVideosSon> portalContentVideosSons = portalContentVideosSonMapper.selectList(portalContentVideosSonLambdaQueryWrapper);

        //???????????????????????????????????????
        PortalContentHisVideos portalContentHisVideos = dozerMapper.map(portalContentVideos, PortalContentHisVideos.class);
        portalContentHisVideosMapper.insert(portalContentHisVideos);
        //??????????????????????????????????????????????????????
        List<PortalContentHisVideosSon> portalContentHisVideosSons = DozerUtils.mapList(dozerMapper, portalContentVideosSons, PortalContentHisVideosSon.class);
        portalContentHisVideosSons.forEach(d -> {
            d.setLinkVideosId(portalContentHisVideos.getId());
        });
        portalContentHisVideosSonService.saveBatch(portalContentHisVideosSons);


        //???????????????????????????????????????
        portalContentVideos.setId(portalContentAdjVideos.getLinkVideosId());
        portalContentVideos.setPortalId(portalContentAdjVideos.getPortalId());
        portalContentVideos.setPortalName(portalContentAdjVideos.getPortalName());
        portalContentVideos.setPartId(portalContentAdjVideos.getPartId());
        portalContentVideos.setPartName(portalContentAdjVideos.getPartName());
        portalContentVideos.setPartTypeId(portalContentAdjVideos.getPartTypeId());
        portalContentVideos.setPartTypeName(portalContentAdjVideos.getPartTypeName());
        portalContentVideos.setRemark(portalContentAdjVideos.getRemark());
        portalContentVideosMapper.updateById(portalContentVideos);

        //???????????????????????????
        List<PortalContentAdjVideosSon> portalContentAdjVideosSons = this.getAdjVideosSon(portalContentVideos.getId());
        portalContentAdjVideosSons.forEach(AdjVideosSon -> {
            //???????????????id???????????????id
            AdjVideosSon.setLinkVideosId(portalContentVideos.getId());
            //???????????????????????????????????????????????????????????????????????????????????????????????????
            portalContentVideosSons.forEach(videosSon -> {
                if (ObjectUtils.isNotEmpty(AdjVideosSon.getLinkVideosSonId()) && AdjVideosSon.getLinkVideosSonId().equals(videosSon.getId())) {
                    AdjVideosSon.setHits(videosSon.getHits());
                }
            });
        });
        //????????????????????????????????????
        portalContentVideosSonMapper.delete(portalContentVideosSonLambdaQueryWrapper);
        //????????????????????????????????????????????????
        List<PortalContentVideosSon> portalContentVideosSonList = DozerUtils.mapList(dozerMapper, portalContentAdjVideosSons, PortalContentVideosSon.class);
        portalContentVideosSonService.saveBatch(portalContentVideosSonList);

        //????????????????????? ?????????????????? todo ??????????????????
        Map<String, Object> eventData = new HashMap<>();
        eventData.put(EventConstants.BannerToHtmlFileHandle, portalContentAdjVideos);
        portalPublisher.publish(eventData);
        return Result.success();
    }

    /**
     * ??????????????????
     *
     * @param LinkVideosId
     * @return
     */
    public List<PortalContentAdjVideosSon> getAdjVideosSon(Long LinkVideosId) {
        LambdaQueryWrapper<PortalContentAdjVideosSon> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjVideosSon::getLinkVideosId, LinkVideosId);
        List<PortalContentAdjVideosSon> portalContentAdjVideosSons = portalContentAdjVideosSonMapper.selectList(queryWrapper);
        if (portalContentAdjVideosSons.size() < 1) {
            throw new IncloudException("????????????????????????");
        }
        return portalContentAdjVideosSons;
    }
}
