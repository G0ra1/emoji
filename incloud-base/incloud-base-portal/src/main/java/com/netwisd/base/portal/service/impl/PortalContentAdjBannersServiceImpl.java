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
 * @Description banner类内容-调整表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-30 00:02:49
 */
@Service
@Slf4j
public class PortalContentAdjBannersServiceImpl extends WfProcServiceImpl<PortalContentAdjBannersMapper, PortalContentAdjBanners,PortalContentAdjBannersDto,PortalContentAdjBannersVo> implements PortalContentAdjBannersService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired//调整表
    private PortalContentAdjBannersMapper portalContentAdjBannersMapper;
    @Autowired
    private WfService wfService;

    @Autowired
    PortalPublisher portalPublisher;
    @Autowired//主表
    private PortalContentBannersMapper portalContentBannersMapper;
    @Autowired//主表
    private PortalContentBannersService portalContentBannersService;
    @Autowired//历史表
    private PortalContentHisBannersMapper portalContentHisBannersMapper;
    /**
    * 单表简单查询操作
    * @param portalContentAdjBannersDto
    * @return
    */
    @Override
    public Page list(PortalContentAdjBannersDto portalContentAdjBannersDto) {
        log.info("banner类内容-调整表list参数：" + portalContentAdjBannersDto.toString());
        LambdaQueryWrapper<PortalContentAdjBanners> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.like(StringUtils.isNotBlank(portalContentAdjBannersDto.getTitle()), PortalContentAdjBanners::getTitle, portalContentAdjBannersDto.getTitle());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjBannersDto.getPortalId()), PortalContentAdjBanners::getPortalId, portalContentAdjBannersDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjBannersDto.getPartId()), PortalContentAdjBanners::getPartId, portalContentAdjBannersDto.getPartId());

        Page<PortalContentAdjBanners> page = portalContentAdjBannersMapper.selectPage(portalContentAdjBannersDto.getPage(),queryWrapper);
        Page<PortalContentAdjBannersVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentAdjBannersVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentAdjBannersDto
    * @return
    */
    @Override
    public List<PortalContentAdjBannersVo> lists(PortalContentAdjBannersDto portalContentAdjBannersDto) {
        log.info("banner类内容-调整表lists参数：" + portalContentAdjBannersDto.toString());
        LambdaQueryWrapper<PortalContentAdjBanners> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(StringUtils.isNotBlank(portalContentAdjBannersDto.getTitle()), PortalContentAdjBanners::getTitle, portalContentAdjBannersDto.getTitle());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjBannersDto.getPortalId()), PortalContentAdjBanners::getPortalId, portalContentAdjBannersDto.getPortalId());
        queryWrapper.eq(ObjectUtils.isNotEmpty(portalContentAdjBannersDto.getPartId()), PortalContentAdjBanners::getPartId, portalContentAdjBannersDto.getPartId());

         List<PortalContentAdjBanners> portalContentAdjBannersList = super.list(queryWrapper);
        List<PortalContentAdjBannersVo> portalContentAdjBannersVos = DozerUtils.mapList(dozerMapper, portalContentAdjBannersList, PortalContentAdjBannersVo.class);
        log.debug("查询条数:"+portalContentAdjBannersList.size());
        return portalContentAdjBannersVos;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentAdjBannersVo get(Long id) {
        log.info("banner类内容-调整表get参数：" + id);
        PortalContentAdjBanners portalContentAdjBanners = super.getById(id);
        PortalContentAdjBannersVo portalContentAdjBannersVo = null;
        if(portalContentAdjBanners !=null){
            portalContentAdjBannersVo = dozerMapper.map(portalContentAdjBanners,PortalContentAdjBannersVo.class);
        }
        log.debug("查询成功");
        return portalContentAdjBannersVo;
    }

    /**
    * 保存实体
    * @param portalContentAdjBannersDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentAdjBannersDto portalContentAdjBannersDto) {
        log.info("banner类内容-调整表save参数：" + portalContentAdjBannersDto.toString());
         LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();//todo 因没有开启登录权限所以获取登录信息先停用
        PortalContentAdjBanners portalContentAdjBanners = dozerMapper.map(portalContentAdjBannersDto,PortalContentAdjBanners.class);
        /* if(ObjectUtils.isNotEmpty(loginAppUser.getId())){
            portalContentAdjBanners.setApplyUserId(Long.parseLong(loginAppUser.getId()));
        }
        if (ObjectUtils.isNotEmpty(loginAppUser.getUsername())){
            portalContentAdjBanners.setApplyUserName(loginAppUser.getUsername());
        }*/
        portalContentAdjBanners.setHits(0l);//给点击量赋值0
        portalContentAdjBanners.setAuditStatus(AuditStateEnum.STARTING.getType());
        boolean result = super.save(portalContentAdjBanners);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalContentAdjBannersDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentAdjBannersDto portalContentAdjBannersDto) {
        log.info("banner类内容-调整表update参数：" + portalContentAdjBannersDto.toString());
        portalContentAdjBannersDto.setUpdateTime(LocalDateTime.now());
        PortalContentAdjBanners portalContentAdjBanners = dozerMapper.map(portalContentAdjBannersDto,PortalContentAdjBanners.class);
        portalContentAdjBanners.setUpdateTime(LocalDateTime.now());
        boolean result = super.updateById(portalContentAdjBanners);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        log.info("banner类内容-调整表delete参数：" + id);
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    /**
     * 流程删除
     *
     * @param camundaProcinsId
     * @return
     */
    @Override
    public Result procDel(String camundaProcinsId) {
        log.info("banner类内容-调整表delete参数：" + camundaProcinsId);
        PortalContentAdjBanners portalContentAdjBanners = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjBanners)) {
            throw new IncloudException("没有找到对应信息！");
        }
        super.removeById(portalContentAdjBanners.getId());
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
        log.info("banner类内容-调整表procStop参数：" + camundaProcinsId);
        PortalContentAdjBanners portalContentAdjBanners = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjBanners)) {
            throw new IncloudException("没有找到对应信息！");
        }
        if (portalContentAdjBanners.getAuditStatus().equals(AuditStateEnum.COMPLETE.getType())) {
            throw new IncloudException("流程已完成审批，不能终止！");
        }
        portalContentAdjBanners.setAuditStatus(AuditStateEnum.TERMINATION.getType());
        portalContentAdjBannersMapper.updateById(portalContentAdjBanners);
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
        log.info("banner类内容-调整表auditSucceed参数：" + camundaProcinsId);
        PortalContentAdjBanners portalContentAdjBanners = this.queryByProcinsId(camundaProcinsId);
        if (ObjectUtils.isEmpty(portalContentAdjBanners)) {
            throw new IncloudException("没有找到对应信息！");
        }
        portalContentAdjBanners.setPassTime(LocalDateTime.now());
        portalContentAdjBanners.setPassTime(LocalDateTime.now());
        portalContentAdjBanners.setAuditStatus(AuditStateEnum.COMPLETE.getType());
        portalContentAdjBannersMapper.updateById(portalContentAdjBanners);

        //主表数据赋值给历史表
        PortalContentBanners portalContentBanners = portalContentBannersMapper.selectById(portalContentAdjBanners.getLinkBannerId());
        PortalContentHisBanners portalContentHisBanners = dozerMapper.map(portalContentBanners, PortalContentHisBanners.class);
        portalContentHisBannersMapper.insert(portalContentHisBanners);

        //把调整的内容覆盖到主表中
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
        //审批完成后通知 生成静态文件
        Map<String, Object> eventData = new HashMap<>();
        eventData.put(EventConstants.BannerToHtmlFileHandle, portalContentAdjBanners);
        portalPublisher.publish(eventData);
        return Result.success();
    }

    /**
     * 流程展示
     *
     * @param procViewDto
     * @return
     */
    @Override
    public PortalContentAdjBannersVo procView(ProcViewDto procViewDto) {
        log.info("banner类内容-调整表procView参数：" + procViewDto.toString());
        PortalContentAdjBanners portalContentAdjBanners = this.queryByProcinsId(procViewDto.getCamundaProcinsId());
        return dozerMapper.map(portalContentAdjBanners, PortalContentAdjBannersVo.class);
    }

    /**
     * 流程提交
     *
     * @param portalContentAdjBannersDto
     */
    @Override
    @Transactional
    public void procBizSubmit(PortalContentAdjBannersDto portalContentAdjBannersDto) {
        try {
            log.debug("首页banner轮播图内容管理-调整表procSubmit参数：" + portalContentAdjBannersDto.toString());
            //根据流程 id 查询出具体的业务信息
            PortalContentAdjBanners portalContentAdjBanners = this.queryByProcinsId(portalContentAdjBannersDto.getCamundaProcinsId());
            if (ObjectUtils.isEmpty(portalContentAdjBanners)) {
                throw new IncloudException("没有查询出具体的首页banner轮播图内容管理-调整表的申请信息。");
            }
            //如果流程状态是起草，修改状态
            if ((portalContentAdjBanners.getAuditStatus().equals(AuditStateEnum.STARTING.getType()))) {
                //修改状态
                portalContentAdjBannersDto.setAuditStatus(AuditStateEnum.SUBMIT.getType());

            }
            PortalContentBannersDto portalContentBannersDto = new PortalContentBannersDto();
            portalContentBannersDto.setTitle(portalContentAdjBannersDto.getTitle());
            portalContentBannersDto.setPartId(portalContentAdjBannersDto.getPartId());
            List<PortalContentBannersVo> lists = portalContentBannersService.lists(portalContentBannersDto);
            if (lists.size() > 1) {//通过所属栏目id和标题 去查询是否存在如果存在那么就不可以执行后边操作
                throw new IncloudException("该标题文章已经在<" + lists.get(0).getPartName() + ">下存在");
            }

            this.update(portalContentAdjBannersDto);
        } catch (Exception e) {
            throw new IncloudException(e.getMessage());
        }
    }

    /**
     * 流程保存
     *
     * @param portalContentAdjBannersDto
     * @return
     */
    @Override
    @Transactional
    public Result procSave(PortalContentAdjBannersDto portalContentAdjBannersDto) {
        log.info("首页banner轮播图内容管理-调整表procSave参数：" + portalContentAdjBannersDto.toString());
        if (StringUtils.isBlank(portalContentAdjBannersDto.getCamundaProcinsId())) {
            String maxBizKey = portalContentAdjBannersMapper.getMaxBizKey();
            log.info("首页banner轮播图内容管理-调整表，最大的maxBizKey:{}", maxBizKey);
            WfEngineDto.StartDto startDto = portalContentAdjBannersDto.getStartDto();
            log.info("首页banner轮播图内容管理-调整表，启动工作流参数：{}", startDto);
            if (ObjectUtils.isEmpty(startDto)) {
                startDto.setReason(portalContentAdjBannersDto.getReason());
                if (StringUtils.isBlank(maxBizKey)) {
                    startDto.setBizKey("ADJ-BANNERS-" + DateUtil.formatDate(new Date(), "yyyyMMdd") + "001");
                } else {
                    String bizKey = "ADJ-BANNERS-" + Long.valueOf(maxBizKey) + 1;
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

            EngineVo engineVo = wfService.startEngine(portalContentAdjBannersDto);
            wfService.setWfDto(portalContentAdjBannersDto, engineVo);
            this.save(portalContentAdjBannersDto);
            return Result.success(engineVo);
        } else {
            //修改业务信息
            this.update(portalContentAdjBannersDto);
            return Result.success();
        }
    }

    /**
     * 根据camundaProcinsId查内容
     *
     * @param camundaProcinsId
     * @return
     */
    public PortalContentAdjBanners queryByProcinsId(String camundaProcinsId) {
        log.info("首页banner轮播图内容管理-调整表queryByProcinsId参数：" + camundaProcinsId);
        LambdaQueryWrapper<PortalContentAdjBanners> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalContentAdjBanners::getCamundaProcinsId, camundaProcinsId);
        return portalContentAdjBannersMapper.selectOne(queryWrapper);
    }

}
