package com.netwisd.base.portal.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.portal.entity.PortalYbzDoneTasks;
import com.netwisd.base.portal.mapper.PortalYbzDoneTasksMapper;
import com.netwisd.base.portal.service.PortalYbzDoneTasksService;
import com.netwisd.base.portal.service.PortalYbzTodoTasksService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalYbzDoneTasksDto;
import com.netwisd.base.portal.vo.PortalYbzDoneTasksVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 集成友报账-已办 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-05-25 08:59:08
 */
@Service
@Slf4j
public class PortalYbzDoneTasksServiceImpl extends ServiceImpl<PortalYbzDoneTasksMapper, PortalYbzDoneTasks> implements PortalYbzDoneTasksService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalYbzDoneTasksMapper portalYbzDoneTasksMapper;

    @Autowired
    private PortalYbzTodoTasksService portalYbzTodoTasksService;

    /**
    * 单表简单查询操作
    * @param portalYbzDoneTasksDto
    * @return
    */
    @Override
    public Page list(PortalYbzDoneTasksDto portalYbzDoneTasksDto) {
        LambdaQueryWrapper<PortalYbzDoneTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(portalYbzDoneTasksDto.getIdCard()), PortalYbzDoneTasks::getIdCard,portalYbzDoneTasksDto.getIdCard());
        queryWrapper.like(StringUtils.isNotEmpty(portalYbzDoneTasksDto.getTitle()),PortalYbzDoneTasks::getTitle,portalYbzDoneTasksDto.getTitle());
        queryWrapper.like(StringUtils.isNotEmpty(portalYbzDoneTasksDto.getContent()),PortalYbzDoneTasks::getContent,portalYbzDoneTasksDto.getContent());
        Page<PortalYbzDoneTasks> page = portalYbzDoneTasksMapper.selectPage(portalYbzDoneTasksDto.getPage(),queryWrapper);
        return DozerUtils.mapPage(dozerMapper, page, PortalYbzDoneTasksVo.class);
    }

    /**
     * 自定义查询操作
     *
     * @param portalYbzDoneTasksDto
     * @return
     */
    @Override
    public List<PortalYbzDoneTasksVo> lists(PortalYbzDoneTasksDto portalYbzDoneTasksDto) {
        LambdaQueryWrapper<PortalYbzDoneTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(portalYbzDoneTasksDto.getIdCard()), PortalYbzDoneTasks::getIdCard,portalYbzDoneTasksDto.getIdCard());
        queryWrapper.like(StringUtils.isNotEmpty(portalYbzDoneTasksDto.getTitle()),PortalYbzDoneTasks::getTitle,portalYbzDoneTasksDto.getTitle());
        queryWrapper.like(StringUtils.isNotEmpty(portalYbzDoneTasksDto.getContent()),PortalYbzDoneTasks::getContent,portalYbzDoneTasksDto.getContent());
        List<PortalYbzDoneTasks> portalYbzDoneTasks = portalYbzDoneTasksMapper.selectList(queryWrapper);
        return DozerUtils.mapList(dozerMapper, portalYbzDoneTasks, PortalYbzDoneTasksVo.class);
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalYbzDoneTasksVo get(Long id) {
        PortalYbzDoneTasks portalYbzDoneTasks = super.getById(id);
        PortalYbzDoneTasksVo portalYbzDoneTasksVo = null;
        if(portalYbzDoneTasks !=null){
            portalYbzDoneTasksVo = dozerMapper.map(portalYbzDoneTasks,PortalYbzDoneTasksVo.class);
        }
        log.debug("查询成功");
        return portalYbzDoneTasksVo;
    }

    @Override
    @Transactional
    public Boolean saveList(List<PortalYbzDoneTasksDto> ybzDoneTasksDtos) {
        log.debug("推送已办数据：=============================start");
        this.checkData(ybzDoneTasksDtos);
        List<PortalYbzDoneTasks> addResultList = new ArrayList<>();
        List<PortalYbzDoneTasks> upResultList = new ArrayList<>();
        for (PortalYbzDoneTasksDto ybzDoneTasksDto : ybzDoneTasksDtos) {
            //先删除一下待办
            portalYbzTodoTasksService.deleteByYbzId(ybzDoneTasksDto.getYbzId());
            PortalYbzDoneTasks ybzDoneTasks = this.getByYbzId(ybzDoneTasksDto.getYbzId());
            if(null != ybzDoneTasks) {
                ybzDoneTasksDto.setId(ybzDoneTasks.getId());//把数据库中的id 赋值给dto
                PortalYbzDoneTasks portalYbzDoneTasks = dozerMapper.map(ybzDoneTasksDto,PortalYbzDoneTasks.class);
                upResultList.add(portalYbzDoneTasks);
            } else {
                PortalYbzDoneTasks portalYbzDoneTasks = dozerMapper.map(ybzDoneTasksDto,PortalYbzDoneTasks.class);
                addResultList.add(portalYbzDoneTasks);
            }
        }
        if(CollectionUtil.isNotEmpty(addResultList)) {
            this.saveBatch(addResultList);
        }
        if(CollectionUtil.isNotEmpty(upResultList)) {
            this.updateBatchById(upResultList);
        }
        log.debug("推送待办数据：=============================end");
        return true;
    }

    private void checkData(List<PortalYbzDoneTasksDto> ybzDoneTasksDtos){
        if (CollectionUtil.isNotEmpty(ybzDoneTasksDtos)) {
            for (PortalYbzDoneTasksDto ybzDoneTasksDto : ybzDoneTasksDtos){
                if (StringUtils.isBlank(ybzDoneTasksDto.getIdCard())) {
                    throw new IncloudException("请传身份号");
                }
                if (ObjectUtils.isEmpty(ybzDoneTasksDto.getPhoneNum())) {
                    throw new IncloudException("请传手机号");
                }
                if (StringUtils.isBlank(ybzDoneTasksDto.getUserNameCh())) {
                    throw new IncloudException("请传用户名称");
                }
                if (StringUtils.isBlank(ybzDoneTasksDto.getTitle())) {
                    throw new IncloudException("请传标题");
                }
                if (StringUtils.isBlank(ybzDoneTasksDto.getContent())) {
                    throw new IncloudException("请传内容");
                }
                if (StringUtils.isBlank(ybzDoneTasksDto.getContentUrl())) {
                    throw new IncloudException("请传内容路径");
                }
                if (StringUtils.isBlank(ybzDoneTasksDto.getYbzId())) {
                    throw new IncloudException("请传友报账唯一标识");
                }
            }
        }else {
            throw new IncloudException("请正常传入数据！");
        }
    }

    private PortalYbzDoneTasks getByYbzId(String ybzId){
        LambdaQueryWrapper<PortalYbzDoneTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalYbzDoneTasks::getYbzId,ybzId);
        return portalYbzDoneTasksMapper.selectOne(queryWrapper);
    }

}
