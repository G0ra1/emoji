package com.netwisd.base.portal.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.common.msg.dto.MessageReceiverUserDto;
import com.netwisd.base.common.msg.vo.MessageVo;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.portal.entity.PortalYbzTodoTasks;
import com.netwisd.base.portal.fegin.MsgClient;
import com.netwisd.base.portal.mapper.PortalYbzTodoTasksMapper;
import com.netwisd.base.portal.service.PortalYbzTodoTasksService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalYbzTodoTasksDto;
import com.netwisd.base.portal.vo.PortalYbzTodoTasksVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 集成友报账-待办 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-05-25 08:57:18
 */
@Service
@Slf4j
public class PortalYbzTodoTasksServiceImpl extends ServiceImpl<PortalYbzTodoTasksMapper, PortalYbzTodoTasks> implements PortalYbzTodoTasksService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalYbzTodoTasksMapper portalYbzTodoTasksMapper;

    @Autowired
    private MsgClient msgClient;

    /**
    * 单表简单查询操作
    * @param portalYbzTodoTasksDto
    * @return
    */
    @Override
    public Page list(PortalYbzTodoTasksDto portalYbzTodoTasksDto) {
        LambdaQueryWrapper<PortalYbzTodoTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(portalYbzTodoTasksDto.getIdCard()),PortalYbzTodoTasks::getIdCard,portalYbzTodoTasksDto.getIdCard());
        queryWrapper.like(StringUtils.isNotEmpty(portalYbzTodoTasksDto.getTitle()),PortalYbzTodoTasks::getTitle,portalYbzTodoTasksDto.getTitle());
        queryWrapper.like(StringUtils.isNotEmpty(portalYbzTodoTasksDto.getContent()),PortalYbzTodoTasks::getContent,portalYbzTodoTasksDto.getContent());
        Page<PortalYbzTodoTasks> page = portalYbzTodoTasksMapper.selectPage(portalYbzTodoTasksDto.getPage(),queryWrapper);
        return DozerUtils.mapPage(dozerMapper, page, PortalYbzTodoTasksVo.class);
    }

    /**
     * 自定义查询操作
     *
     * @param portalYbzTodoTasksDto
     * @return
     */
    @Override
    public List<PortalYbzTodoTasksVo> lists(PortalYbzTodoTasksDto portalYbzTodoTasksDto) {
        LambdaQueryWrapper<PortalYbzTodoTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(portalYbzTodoTasksDto.getIdCard()),PortalYbzTodoTasks::getIdCard,portalYbzTodoTasksDto.getIdCard());
        queryWrapper.like(StringUtils.isNotEmpty(portalYbzTodoTasksDto.getTitle()),PortalYbzTodoTasks::getTitle,portalYbzTodoTasksDto.getTitle());
        queryWrapper.like(StringUtils.isNotEmpty(portalYbzTodoTasksDto.getContent()),PortalYbzTodoTasks::getContent,portalYbzTodoTasksDto.getContent());
        List<PortalYbzTodoTasks> portalYbzTodoTasks = portalYbzTodoTasksMapper.selectList(queryWrapper);
        return DozerUtils.mapList(dozerMapper, portalYbzTodoTasks, PortalYbzTodoTasksVo.class);
    }

    @Override
    public PortalYbzTodoTasksVo get(Long id) {
        return dozerMapper.map(this.getById(id),PortalYbzTodoTasksVo.class);
    }

    /**
     * 通过ID删除
     *
     * @param ybzId
     * @return
     */
    @Transactional
    @Override
    public Boolean deleteByYbzId(String ybzId) {
        PortalYbzTodoTasks byYbzId = this.getByYbzId(ybzId);
        LambdaQueryWrapper<PortalYbzTodoTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalYbzTodoTasks::getYbzId,ybzId);
        int line = portalYbzTodoTasksMapper.delete(queryWrapper);
        if (line > 0) {
            if (ObjectUtil.isNotNull(byYbzId.getMsgId())) {
                msgClient.delMsg(String.valueOf(byYbzId.getMsgId()));//删除待办消息
            }
            log.debug("推送友报账删除待办数据：=============================end。返回：" + true);
            return true;
        }else {
            log.debug("推送友报账删除待办数据：=============================end。返回：" + false);
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean saveList(List<PortalYbzTodoTasksDto> ybzTodoTasksDtos) {
        log.debug("友报账批量新增修改：================================start");
        this.checkData(ybzTodoTasksDtos);
        List<PortalYbzTodoTasks> addResultList = new ArrayList<>();
        List<PortalYbzTodoTasks> updateResultList = new ArrayList<>();
        for (PortalYbzTodoTasksDto ybzTodoTasksDto : ybzTodoTasksDtos){
            PortalYbzTodoTasks byYbzId = this.getByYbzId(ybzTodoTasksDto.getYbzId());
            if (ObjectUtils.isNotEmpty(byYbzId)) {
                if(null != ybzTodoTasksDto.getMsgId() && 0L != ybzTodoTasksDto.getMsgId()) {
                    msgClient.delMsg(String.valueOf(ybzTodoTasksDto.getMsgId()));
                }
            }
            this.sendMsg(ybzTodoTasksDto);
            if (ObjectUtils.isNotEmpty(byYbzId)) {
                ybzTodoTasksDto.setId(byYbzId.getId());
                PortalYbzTodoTasks portalYbzTodoTasks = dozerMapper.map(ybzTodoTasksDto,PortalYbzTodoTasks.class);
                updateResultList.add(portalYbzTodoTasks);
            }else {
                PortalYbzTodoTasks portalYbzTodoTasks = dozerMapper.map(ybzTodoTasksDto, PortalYbzTodoTasks.class);
                addResultList.add(portalYbzTodoTasks);
            }
        }
        if (CollectionUtils.isNotEmpty(addResultList)) {
            this.saveBatch(addResultList);
        }
        if (CollectionUtils.isNotEmpty(updateResultList)) {
            this.updateBatchById(updateResultList);
        }
        log.debug("友报账批量新增修改：================================end");
        return true;
    }

    private PortalYbzTodoTasks getByYbzId(String ybzId){
        LambdaQueryWrapper<PortalYbzTodoTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PortalYbzTodoTasks::getYbzId,ybzId);
        return portalYbzTodoTasksMapper.selectOne(queryWrapper);
    }

    private void sendMsg(PortalYbzTodoTasksDto ybzTodoTasksDto){
        try {
            MessageDto messageDto = new MessageDto();
            messageDto.setMsgTitle(ybzTodoTasksDto.getTitle());
            messageDto.setMsgContent(ybzTodoTasksDto.getContent());
            messageDto.setMsgH5Url(ybzTodoTasksDto.getContentUrl());
            messageDto.setIsSystemSend(YesNo.YES.code);
            messageDto.setMsgSource("YBZ");//消息来源 友报账
            List<MessageReceiverUserDto> messageReceiverUserDtos = new ArrayList<>();
            MessageReceiverUserDto messageReceiverUserDto = new MessageReceiverUserDto();
            messageReceiverUserDto.setReceiverIdcard(ybzTodoTasksDto.getIdCard());
            messageReceiverUserDto.setReceiverUserName(ybzTodoTasksDto.getUserNameCh());
            messageReceiverUserDtos.add(messageReceiverUserDto);
            messageDto.setReceiverUserList(messageReceiverUserDtos);
            List<MessageVo> resultMsg = msgClient.sendTmpMsg(messageDto);
            if (CollectionUtils.isNotEmpty(resultMsg)) {
                ybzTodoTasksDto.setMsgId(resultMsg.get(0).getId());
            }
        }catch (Exception e){
            log.debug("友报账待办发送消息失败！"+e.getMessage());
        }
    }

    /**
     * 检查新增传参
     * @param ybzTodoTasksDtos
     */
    private void checkData(List<PortalYbzTodoTasksDto> ybzTodoTasksDtos){
        if (CollectionUtils.isNotEmpty(ybzTodoTasksDtos)) {
            for (PortalYbzTodoTasksDto ybzTodoTasksDto : ybzTodoTasksDtos){
                if (StringUtils.isBlank(ybzTodoTasksDto.getYbzId())) {
                    throw new IncloudException("请传友报账唯一标识");
                }
                if (StringUtils.isBlank(ybzTodoTasksDto.getIdCard())) {
                    throw new IncloudException("请传身份证号！");
                }
                if (ObjectUtils.isEmpty(ybzTodoTasksDto.getPhoneNum())) {
                    throw new IncloudException("请传手机号！");
                }
                if (StringUtils.isBlank(ybzTodoTasksDto.getUserNameCh())) {
                    throw new IncloudException("请传用户名称！");
                }
                if (StringUtils.isBlank(ybzTodoTasksDto.getContent())) {
                    throw new IncloudException("请传内容！");
                }
                if (StringUtils.isBlank(ybzTodoTasksDto.getContentUrl())) {
                    throw new IncloudException("请传内容路径！");
                }
            }
        }else {
            throw new IncloudException("批量新增接口，请传集合参数!");
        }
    }
}
