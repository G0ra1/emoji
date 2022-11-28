package com.netwisd.base.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.portal.entity.PortalAppMsg;
import com.netwisd.base.portal.mapper.PortalAppMsgMapper;
import com.netwisd.base.portal.service.PortalAppMsgService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalAppMsgDto;
import com.netwisd.base.portal.vo.PortalAppMsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 应用市场app信息表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-12-23 15:07:50
 */
@Service
@Slf4j
public class PortalAppMsgServiceImpl extends ServiceImpl<PortalAppMsgMapper, PortalAppMsg> implements PortalAppMsgService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalAppMsgMapper portalAppMsgMapper;

    /**
    * 单表简单查询操作
    * @param portalAppMsgDto
    * @return
    */
    @Override
    public Page list(PortalAppMsgDto portalAppMsgDto) {
        LambdaQueryWrapper<PortalAppMsg> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<PortalAppMsg> page = portalAppMsgMapper.selectPage(portalAppMsgDto.getPage(),queryWrapper);
        Page<PortalAppMsgVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalAppMsgVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalAppMsgDto
    * @return
    */
    @Override
    public Page lists(PortalAppMsgDto portalAppMsgDto) {
        Page<PortalAppMsgVo> pageVo = portalAppMsgMapper.getPageList(portalAppMsgDto.getPage(),portalAppMsgDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalAppMsgVo get(Long id) {
        PortalAppMsg portalAppMsg = super.getById(id);
        PortalAppMsgVo portalAppMsgVo = null;
        if(portalAppMsg !=null){
            portalAppMsgVo = dozerMapper.map(portalAppMsg,PortalAppMsgVo.class);
        }
        log.debug("查询成功");
        return portalAppMsgVo;
    }

    /**
    * 保存实体
    * @param portalAppMsgDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalAppMsgDto portalAppMsgDto) {
        PortalAppMsg portalAppMsg = dozerMapper.map(portalAppMsgDto,PortalAppMsg.class);
        boolean result = super.save(portalAppMsg);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param portalAppMsgDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalAppMsgDto portalAppMsgDto) {
        portalAppMsgDto.setUpdateTime(LocalDateTime.now());
        PortalAppMsg portalAppMsg = dozerMapper.map(portalAppMsgDto,PortalAppMsg.class);
        boolean result = super.updateById(portalAppMsg);
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
    public String getState(PortalAppMsgDto portalAppMsgDto) {
        LambdaQueryWrapper<PortalAppMsg> appMsgLambdaQueryWrapper = new LambdaQueryWrapper<>();
        appMsgLambdaQueryWrapper.eq(StringUtils.isNotEmpty(portalAppMsgDto.getBundleId()),PortalAppMsg::getBundleId,portalAppMsgDto.getBundleId());
        appMsgLambdaQueryWrapper.eq(StringUtils.isNotEmpty(portalAppMsgDto.getPlatform()),PortalAppMsg::getPlatform,portalAppMsgDto.getPlatform());
        appMsgLambdaQueryWrapper.eq(StringUtils.isNotEmpty(portalAppMsgDto.getMarketName()),PortalAppMsg::getMarketName,portalAppMsgDto.getMarketName());
        appMsgLambdaQueryWrapper.eq(StringUtils.isNotEmpty(portalAppMsgDto.getVersion()),PortalAppMsg::getVersion,portalAppMsgDto.getVersion());
        PortalAppMsg portalAppMsg = portalAppMsgMapper.selectOne(appMsgLambdaQueryWrapper);
        return portalAppMsg.getState();
    }
}
