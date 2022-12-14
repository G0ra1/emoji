package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.DateUtil;
import com.netwisd.base.common.wf.eunm.AuditStateEnum;
import com.netwisd.base.portal.config.PortalPublisher;
import com.netwisd.base.portal.constants.EventConstants;
import com.netwisd.base.portal.dto.PortalContentBannersDto;
import com.netwisd.base.portal.entity.*;
import com.netwisd.base.portal.mapper.PortalContentBannersMapper;
import com.netwisd.base.portal.mapper.PortalContentHisBannersMapper;
import com.netwisd.base.portal.service.PortalContentBannersService;
import com.netwisd.base.portal.vo.PortalContentBannersVo;
import com.netwisd.base.wf.starter.dto.BizInfoDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.service.WfService;
import com.netwisd.base.wf.starter.service.impl.WfProcServiceImpl;
import com.netwisd.base.portal.mapper.PortalContentAdjBannersMapper;
import com.netwisd.base.portal.service.PortalContentAdjBannersService;
import com.netwisd.base.wf.starter.vo.EngineVo;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentAdjBannersDto;
import com.netwisd.base.portal.vo.PortalContentAdjBannersVo;
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
 * @Description banner?????????-????????? ????????????...
 * @author ???????????? cuiran@netwisd.com@netwisd.com
 * @date 2021-08-30 00:02:49
 */
@Service
@Slf4j
public class PortalContentAdjBannersServiceImpl extends WfProcServiceImpl<PortalContentAdjBannersMapper, PortalContentAdjBanners,PortalContentAdjBannersDto,PortalContentAdjBannersVo> implements PortalContentAdjBannersService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired//?????????
    private PortalContentAdjBannersMapper portalContentAdjBannersMapper;
    @Autowired
    private WfService wfService;

    @Autowired
    PortalPublisher portalPublisher;
    @Autowired//??????
    private PortalContentBannersMapper portalContentBannersMapper;
    @Autowired//??????
    private PortalContentBannersService portalContentBannersService;
    @Autowired//?????????
    private PortalContentHisBannersMapper portalContentHisBannersMapper;
    /**
    * ????????????????????????
    * @param portalContentAdjBannersDto
    * @return
    */
    @Override
    public Page list(PortalContentAdjBannersDto portalContentAdjBannersDto) {
        log.info("banner?????????-?????????list?????????" + portalContentAdjBannersDto.toString());
        LambdaQueryWrapper<PortalContentAdjBanners> queryWrapper = new LambdaQueryWrapper<>();
        //????????????????????????????????????????????????
        queryWrapper.like(StringUtils.isNotBlank(portalContentAdjBannersDto.getTitle()), PortalContentAdjBanners::getTitle, portalContentAdjBannersDto.getTitle());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjBannersDto.getPortalId()), PortalContentAdjBanners::getPortalId, portalContentAdjBannersDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjBannersDto.getPartId()), PortalContentAdjBanners::getPartId, portalContentAdjBannersDto.getPartId());

        Page<PortalContentAdjBanners> page = portalContentAdjBannersMapper.selectPage(portalContentAdjBannersDto.getPage(),queryWrapper);
        Page<PortalContentAdjBannersVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjBannersVo.class);
        log.debug("????????????:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * ?????????????????????
    * @param portalContentAdjBannersDto
    * @return
    */
    @Override
    public List<PortalContentAdjBannersVo> lists(PortalContentAdjBannersDto portalContentAdjBannersDto) {
        log.info("banner?????????-?????????lists?????????" + portalContentAdjBannersDto.toString());
        LambdaQueryWrapper<PortalContentAdjBanners> queryWrapper = new LambdaQueryWrapper<>();
        //????????????????????????????????????????????????
        queryWrapper.eq(StringUtils.isNotBlank(portalContentAdjBannersDto.getTitle()), PortalContentAdjBanners::getTitle, portalContentAdjBannersDto.getTitle());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjBannersDto.getPortalId()), PortalContentAdjBanners::getPortalId, portalContentAdjBannersDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjBannersDto.getPartId()), PortalContentAdjBanners::getPartId, portalContentAdjBannersDto.getPartId());

         List<PortalContentAdjBanners> portalContentAdjBannersList = super.list(queryWrapper);
        List<PortalContentAdjBannersVo> portalContentAdjBannersVos = DozerUtils.mapList(dozerMapper, portalContentAdjBannersList, PortalContentAdjBannersVo.class);
        log.debug("????????????:"+portalContentAdjBannersList.size());
        return portalContentAdjBannersVos;
    }

    /**
    * ??????ID????????????
    * @param id
    * @return
    */
    @Override
    public PortalContentAdjBannersVo get(Long id) {
        log.info("banner?????????-?????????get?????????" + id);
        PortalContentAdjBanners portalContentAdjBanners = super.getById(id);
        PortalContentAdjBannersVo portalContentAdjBannersVo = null;
        if(portalContentAdjBanners !=null){
            portalContentAdjBannersVo = dozerMapper.map(portalContentAdjBanners,PortalContentAdjBannersVo.class);
        }
        log.debug("????????????");
        return portalContentAdjBannersVo;
    }

    /**
    * ????????????
    * @param portalContentAdjBannersDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentAdjBannersDto portalContentAdjBannersDto) {
        log.info("banner?????????-?????????save?????????" + portalContentAdjBannersDto.toString());
         LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();//todo ????????????????????????????????????????????????????????????
        PortalContentAdjBanners portalContentAdjBanners = dozerMapper.map(portalContentAdjBannersDto,PortalContentAdjBanners.class);
        /* if(ObjectUtils.isNotEmpty(loginAppUser.getId())){
            portalContentAdjBanners.setApplyUserId(Long.parseLong(loginAppUser.getId()));
        }
        if (ObjectUtils.isNotEmpty(loginAppUser.getUsername())){
            portalContentAdjBanners.setApplyUserName(loginAppUser.getUsername());
        }*/
        portalContentAdjBanners.setHits(0l);//??????????????????0
        portalContentAdjBanners.setAuditStatus(AuditStateEnum.STARTING.getType());
        boolean result = super.save(portalContentAdjBanners);
        if(result){
            log.debug("????????????");
        }
        return result;
    }

    /**
    * ????????????
    * @param portalContentAdjBannersDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentAdjBannersDto portalContentAdjBannersDto) {
        log.info("banner?????????-?????????update?????????" + portalContentAdjBannersDto.toString());
        portalContentAdjBannersDto.setUpdateTime(LocalDateTime.now());
        PortalContentAdjBanners portalContentAdjBanners = dozerMapper.map(portalContentAdjBannersDto,PortalContentAdjBanners.class);
        portalContentAdjBanners.setUpdateTime(LocalDateTime.now());
        boolean result = super.updateById(portalContentAdjBanners);
        if(result){
            log.debug("????????????");
        }
        return result;
    }

    /**
    * ??????ID??????
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        log.info("banner?????????-?????????delete?????????" + id);
        boolean result = super.removeById(id);
        if(result){
            log.debug("????????????");
        }
        return result;
    }

    /**
     * ????????????
     *
     * @param camundaProcinsId
     * @return
     */
    @Override
    public Result procDel(String camundaProcinsId) {
        log.info("banner?????????-?????????delete?????????" + camundaProcinsId);
        PortalContentAdjBanners portalContentAdjBanners = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjBanners)) {
            throw new IncloudException("???????????????????????????");
        }
        super.removeById(portalContentAdjBanners.getId());
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
        log.info("banner?????????-?????????procStop?????????" + camundaProcinsId);
        PortalContentAdjBanners portalContentAdjBanners = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjBanners)) {
            throw new IncloudException("???????????????????????????");
        }
        if (portalContentAdjBanners.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
            throw new IncloudException("???????????????????????????????????????");
        }
        portalContentAdjBanners.setAuditStatus(AuditStateEnum.TERMINATION.getType());
        portalContentAdjBannersMapper.updateById(portalContentAdjBanners);
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
        log.info("banner?????????-?????????auditSucceed?????????" + camundaProcinsId);
        PortalContentAdjBanners portalContentAdjBanners = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjBanners)) {
            throw new IncloudException("???????????????????????????");
        }
        portalContentAdjBanners.setPassTime(LocalDateTime.now());
        portalContentAdjBanners.setPassTime(LocalDateTime.now());
        portalContentAdjBanners.setAuditStatus(AuditStateEnum.COMPLETE.getType());
        portalContentAdjBannersMapper.updateById(portalContentAdjBanners);

        //??????????????????????????????
        PortalContentBanners portalContentBanners = portalContentBannersMapper.selectById(portalContentAdjBanners.getLinkBannerId());
        PortalContentHisBanners portalContentHisBanners = dozerMapper.map(portalContentBanners, PortalContentHisBanners.class);
        portalContentHisBannersMapper.insert(portalContentHisBanners);

        //????????????????????????????????????
        portalContentBanners.setId(portalContentAdjBanners.getLinkBannerId());
        portalContentBanners.setPortalId(portalContentAdjBanners.getPortalId());
        portalContentBanners.setPortalName(portalContentAdjBanners.getPortalName());
        portalContentBanners.setPartId(portalContentAdjBanners.getPartId());
        portalContentBanners.setPartName(portalContentAdjBanners.getPartName());
        portalContentBanners.setPartTypeId(portalContentAdjBanners.getPartTypeId());
        portalContentBanners.setPartTypeName(portalContentAdjBanners.getPartTypeName());
        portalContentBanners.setTitle(portalContentAdjBanners.getTitle());
        portalContentBanners.setContentUrl(portalContentAdjBanners.getContentUrl());
        portalContentBanners.setDescription(portalContentAdjBanners.getDescription());
        portalContentBanners.setRemark(portalContentAdjBanners.getRemark());

        portalContentBannersMapper.updateById(portalContentBanners);
        //????????????????????? ??????????????????
        Map<String, Object> eventData = new HashMap<>();
        eventData.put(EventConstants.BannerToHtmlFileHandle, portalContentAdjBanners);
        portalPublisher.publish(eventData);
        return Result.success();
    }

    /**
     * ????????????
     *
     * @param procViewDto
     * @return
     */
    @Override
    public PortalContentAdjBannersVo procView(ProcViewDto procViewDto) {
        log.info("banner?????????-?????????procView?????????" + procViewDto.toString());
        PortalContentAdjBanners portalContentAdjBanners = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
        return dozerMapper.map(portalContentAdjBanners, PortalContentAdjBannersVo.class);
    }

    /**
     * ????????????
     *
     * @param portalContentAdjBannersDto
     */
    @Override
    @Transactional
    public void procBizSubmit(PortalContentAdjBannersDto portalContentAdjBannersDto) {
        try {
            log.debug("??????banner?????????????????????-?????????procSubmit?????????" + portalContentAdjBannersDto.toString());
            //???????????? id ??????????????????????????????
            PortalContentAdjBanners portalContentAdjBanners = this.queryByProcinsId(portalContentAdjBannersDto.getCamundaProcinsId());
            if (ObjectUtils.isEmpty(portalContentAdjBanners)) {
                throw new IncloudException("??????????????????????????????banner?????????????????????-???????????????????????????");
            }
            //??????????????????????????????????????????
            if ((portalContentAdjBanners.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
                //????????????
                portalContentAdjBannersDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());

            }
            PortalContentBannersDto portalContentBannersDto = new PortalContentBannersDto();
            portalContentBannersDto.setTitle(portalContentAdjBannersDto.getTitle());
            portalContentBannersDto.setPartId(portalContentAdjBannersDto.getPartId());
            List<PortalContentBannersVo> lists = portalContentBannersService.lists(portalContentBannersDto);
            if (lists.size() > 1) {//??????????????????id????????? ?????????????????????????????????????????????????????????????????????
                throw new IncloudException("????????????????????????<" + lists.get(0).getPartName() + ">?????????");
            }

            this.update(portalContentAdjBannersDto);
        } catch (Exception e) {
            throw new IncloudException(e.getMessage());
        }
    }

    /**
     * ????????????
     *
     * @param portalContentAdjBannersDto
     * @return
     */
    @Override
    @Transactional
    public Result procSave(PortalContentAdjBannersDto portalContentAdjBannersDto) {
        log.info("??????banner?????????????????????-?????????procSave?????????" + portalContentAdjBannersDto.toString());
        if (StringUtils.isBlank(portalContentAdjBannersDto.getCamundaProcinsId())) {
            String maxBizKey = portalContentAdjBannersMapper.getMaxBizKey();
            log.info("??????banner?????????????????????-?????????????????????maxBizKey:{}", maxBizKey);
            WfEngineDto.StartDto startDto = portalContentAdjBannersDto.getStartDto();
            log.info("??????banner?????????????????????-????????????????????????????????????{}", startDto);
            if (ObjectUtils.isEmpty(startDto)) {
                startDto.setReason(portalContentAdjBannersDto.getReason());
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJ-BANNERS-" + DateUtil.formatDate(new Date(), "yyyyMMdd") + "001");
                } else {
                    String bizKey = "ADJ-BANNERS-" + Long.valueOf(maxBizKey) + 1;
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

            EngineVo engineVo = wfService.startEngine(portalContentAdjBannersDto);
            wfService.setWfDto(portalContentAdjBannersDto, engineVo);
            this.save(portalContentAdjBannersDto);
            return Result.success(engineVo);
        } else {
            //??????????????????
            this.update(portalContentAdjBannersDto);
            return Result.success();
        }
    }

    /**
     * ??????camundaProcinsId?????????
     *
     * @param camundaProcinsId
     * @return
     */
    public PortalContentAdjBanners queryByProcinsId(String camundaProcinsId) {
        log.info("??????banner?????????????????????-?????????queryByProcinsId?????????" + camundaProcinsId);
        LambdaQueryWrapper<PortalContentAdjBanners> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjBanners::getCamundaProcinsId, camundaProcinsId);
        return portalContentAdjBannersMapper.selectOne(queryWrapper);
    }

}
