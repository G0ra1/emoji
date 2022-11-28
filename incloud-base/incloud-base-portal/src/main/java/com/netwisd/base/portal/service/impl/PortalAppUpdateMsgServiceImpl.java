package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.portal.entity.PortalAppUpdateMsg;
import com.netwisd.base.portal.mapper.PortalAppUpdateMsgMapper;
import com.netwisd.base.portal.service.PortalAppUpdateMsgService;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalAppUpdateMsgDto;
import com.netwisd.base.portal.vo.PortalAppUpdateMsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

/**
 * @Description app安装包更新记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-01-06 10:16:32
 */
@Service
@Slf4j
public class PortalAppUpdateMsgServiceImpl extends ServiceImpl<PortalAppUpdateMsgMapper, PortalAppUpdateMsg> implements PortalAppUpdateMsgService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalAppUpdateMsgMapper portalAppUpdateMsgMapper;

    /**
    * 单表简单查询操作
    * @param appUpdateMsgDto
    * @return
    */
    @Override
    public Page list(PortalAppUpdateMsgDto appUpdateMsgDto) {
        LambdaQueryWrapper<PortalAppUpdateMsg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(appUpdateMsgDto.getAppId()),PortalAppUpdateMsg::getAppId,appUpdateMsgDto.getAppId());
        queryWrapper.like(StringUtils.isNotEmpty(appUpdateMsgDto.getAppName()),PortalAppUpdateMsg::getAppName,appUpdateMsgDto.getAppName());
        queryWrapper.like(StringUtils.isNotEmpty(appUpdateMsgDto.getAppType()),PortalAppUpdateMsg::getAppType,appUpdateMsgDto.getAppType());
        queryWrapper.like(StringUtils.isNotEmpty(appUpdateMsgDto.getAppVersion()),PortalAppUpdateMsg::getAppVersion,appUpdateMsgDto.getAppVersion());
        queryWrapper.like(StringUtils.isNotEmpty(appUpdateMsgDto.getDownloadUrl()),PortalAppUpdateMsg::getDownloadUrl,appUpdateMsgDto.getDownloadUrl());
        queryWrapper.like(StringUtils.isNotEmpty(appUpdateMsgDto.getUpdateLog()),PortalAppUpdateMsg::getUpdateLog,appUpdateMsgDto.getUpdateLog());
        queryWrapper.eq(null != appUpdateMsgDto.getUpdateInstall(),PortalAppUpdateMsg::getUpdateInstall,appUpdateMsgDto.getUpdateInstall());
        Page<PortalAppUpdateMsg> page = portalAppUpdateMsgMapper.selectPage(appUpdateMsgDto.getPage(),queryWrapper);
        Page<PortalAppUpdateMsgVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalAppUpdateMsgVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalAppUpdateMsgDto
    * @return
    */
    @Override
    public Page lists(PortalAppUpdateMsgDto portalAppUpdateMsgDto) {
        Page<PortalAppUpdateMsgVo> pageVo = portalAppUpdateMsgMapper.getPageList(portalAppUpdateMsgDto.getPage(),portalAppUpdateMsgDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalAppUpdateMsgVo get(Long id) {
        PortalAppUpdateMsg portalAppUpdateMsg = super.getById(id);
        PortalAppUpdateMsgVo portalAppUpdateMsgVo = null;
        if(portalAppUpdateMsg !=null){
            portalAppUpdateMsgVo = dozerMapper.map(portalAppUpdateMsg,PortalAppUpdateMsgVo.class);
        }
        log.debug("查询成功");
        return portalAppUpdateMsgVo;
    }

    /**
    * 保存实体
    * @param portalAppUpdateMsgDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalAppUpdateMsgDto portalAppUpdateMsgDto) {
        if (null == portalAppUpdateMsgDto.getUpdateInstall()) {
            portalAppUpdateMsgDto.setUpdateInstall(YesNo.NO.code);
        }
        PortalAppUpdateMsg portalAppUpdateMsg = dozerMapper.map(portalAppUpdateMsgDto,PortalAppUpdateMsg.class);
        boolean result = super.save(portalAppUpdateMsg);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalAppUpdateMsgDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalAppUpdateMsgDto portalAppUpdateMsgDto) {
        portalAppUpdateMsgDto.setUpdateTime(LocalDateTime.now());
        if (null == portalAppUpdateMsgDto.getUpdateInstall()) {
            portalAppUpdateMsgDto.setUpdateInstall(YesNo.NO.code);
        }
        PortalAppUpdateMsg portalAppUpdateMsg = dozerMapper.map(portalAppUpdateMsgDto,PortalAppUpdateMsg.class);
        boolean result = super.updateById(portalAppUpdateMsg);
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
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    public PortalAppUpdateMsgVo getNewVersion() {
        LambdaQueryWrapper<PortalAppUpdateMsg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(PortalAppUpdateMsg::getCreateTime);
        List<PortalAppUpdateMsg> appUpdateMsgs = portalAppUpdateMsgMapper.selectList(queryWrapper);
        if (appUpdateMsgs.size() > 0) {
            PortalAppUpdateMsg portalAppUpdateMsg = appUpdateMsgs.get(0);
            return dozerMapper.map(portalAppUpdateMsg, PortalAppUpdateMsgVo.class);
        }
        throw new IncloudException("未找到最新app安装包信息！");
    }
}
