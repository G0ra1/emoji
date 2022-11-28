package com.netwisd.base.portal.service.impl;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.MessageTmpEnum;
import com.netwisd.base.common.constants.OtherSysEnum;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.common.msg.dto.MessageReceiverUserDto;
import com.netwisd.base.common.msg.vo.MessageVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.portal.dto.ApiTaskDto;
import com.netwisd.base.portal.entity.PortalContentTodoTasks;
import com.netwisd.base.portal.fegin.BaseMdmClient;
import com.netwisd.base.portal.fegin.MsgClient;
import com.netwisd.base.portal.mapper.PortalContentTodoTasksMapper;
import com.netwisd.base.portal.service.*;
import com.netwisd.base.portal.util.RocketMqSendUtil;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentTodoTasksDto;
import com.netwisd.base.portal.vo.PortalContentTodoTasksVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description 任务集成类内容-待办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-27 17:07:47
 */
@Service
@Slf4j
public class PortalContentTodoTasksServiceImpl extends ServiceImpl<PortalContentTodoTasksMapper, PortalContentTodoTasks> implements PortalContentTodoTasksService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentTodoTasksMapper portalContentTodoTasksMapper;

    @Autowired
    private PortalContentDoneTasksService portalContentDoneTasksService;

    @Autowired
    private PortalContentUnreadTasksService portalContentUnreadTasksService;

    @Autowired
    private PortalContentMydraftTasksService portalContentMydraftTasksService;

    @Autowired
    private PortalContentReadTasksService portalContentReadTasksService;

    @Autowired
    private MsgClient msgClient;

    @Autowired
    private BaseMdmClient baseMdmClient;

    @Autowired
    private RocketMqSendUtil rocketMqSendUtil;

    @Value("${portal.gepsTaskUrl}")
    private String gepsTaskUrl;

    @Value("${portal.oaTaskUrl}")
    private String oaTaskUrl;


    /**
    * 单表简单查询操作
    * @param portalContentTodoTasksDto
    * @return
    */
    @Override
    public Page list(PortalContentTodoTasksDto portalContentTodoTasksDto) {
        //获取当前登录人
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String idCard = loginAppUser.getIdCard();
        LambdaQueryWrapper<PortalContentTodoTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != portalContentTodoTasksDto.getStarterIdCard(),PortalContentTodoTasks::getStarterIdCard,portalContentTodoTasksDto.getStarterIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getStarterName()),PortalContentTodoTasks::getStarterName,portalContentTodoTasksDto.getStarterName());
        queryWrapper.eq(null != portalContentTodoTasksDto.getStarterDeptId(),PortalContentTodoTasks::getStarterDeptId,portalContentTodoTasksDto.getStarterDeptId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getStarterDeptName()),PortalContentTodoTasks::getStarterDeptName,portalContentTodoTasksDto.getStarterDeptName());
        queryWrapper.eq(null != portalContentTodoTasksDto.getStarterOrgId(),PortalContentTodoTasks::getStarterOrgId,portalContentTodoTasksDto.getStarterOrgId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getStarterOrgName()),PortalContentTodoTasks::getStarterOrgName,portalContentTodoTasksDto.getStarterOrgName());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentTodoTasksDto.getSApplyTime())&&ObjectUtil.isNotNull(portalContentTodoTasksDto.getEApplyTime()), PortalContentTodoTasks::getApplyTime, portalContentTodoTasksDto.getSApplyTime(),portalContentTodoTasksDto.getEApplyTime());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getProcinsName()),PortalContentTodoTasks::getProcinsName,portalContentTodoTasksDto.getProcinsName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getCurrentNodeName()),PortalContentTodoTasks::getCurrentNodeName,portalContentTodoTasksDto.getCurrentNodeName());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentTodoTasksDto.getBizKey()),PortalContentTodoTasks::getBizKey,portalContentTodoTasksDto.getBizKey());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentTodoTasksDto.getSCliamTime())&&ObjectUtil.isNotNull(portalContentTodoTasksDto.getECliamTime()), PortalContentTodoTasks::getCliamTime, portalContentTodoTasksDto.getSCliamTime(),portalContentTodoTasksDto.getECliamTime());
        queryWrapper.eq(null != portalContentTodoTasksDto.getTaskState() && 0 != portalContentTodoTasksDto.getTaskState(),PortalContentTodoTasks::getTaskState,portalContentTodoTasksDto.getTaskState());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentTodoTasksDto.getSysBizCode()),PortalContentTodoTasks::getSysBizCode,portalContentTodoTasksDto.getSysBizCode());
        queryWrapper.eq(null != portalContentTodoTasksDto.getOwnnerIdCard(),PortalContentTodoTasks::getOwnnerIdCard,portalContentTodoTasksDto.getOwnnerIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getOwnnerName()),PortalContentTodoTasks::getOwnnerName,portalContentTodoTasksDto.getOwnnerName());
        queryWrapper.eq(null != portalContentTodoTasksDto.getAssigneeIdCard(),PortalContentTodoTasks::getAssigneeIdCard,portalContentTodoTasksDto.getAssigneeIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getAssigneeName()),PortalContentTodoTasks::getAssigneeName,portalContentTodoTasksDto.getAssigneeName());
        queryWrapper.eq(null != portalContentTodoTasksDto.getIsDraft() && 0 != portalContentTodoTasksDto.getIsDraft(),PortalContentTodoTasks::getIsDraft,portalContentTodoTasksDto.getIsDraft());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getSysBizClassify()),PortalContentTodoTasks::getSysBizClassify,portalContentTodoTasksDto.getSysBizClassify());
        queryWrapper.eq(PortalContentTodoTasks::getAssigneeIdCard,idCard);
        queryWrapper.orderByDesc(PortalContentTodoTasks::getHandleStartTime);
        Page<PortalContentTodoTasks> page = portalContentTodoTasksMapper.selectPage(portalContentTodoTasksDto.getPage(),queryWrapper);
        List<PortalContentTodoTasks> list = page.getRecords();
        this.setUrlPrefix(list);
        Page<PortalContentTodoTasksVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentTodoTasksVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentTodoTasksDto
    * @return
    */
    @Override
    public List<PortalContentTodoTasksVo> lists(PortalContentTodoTasksDto portalContentTodoTasksDto) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String idCard = loginAppUser.getIdCard();
        LambdaQueryWrapper<PortalContentTodoTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != portalContentTodoTasksDto.getStarterIdCard(),PortalContentTodoTasks::getStarterIdCard,portalContentTodoTasksDto.getStarterIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getStarterName()),PortalContentTodoTasks::getStarterName,portalContentTodoTasksDto.getStarterName());
        queryWrapper.eq(null != portalContentTodoTasksDto.getStarterDeptId(),PortalContentTodoTasks::getStarterDeptId,portalContentTodoTasksDto.getStarterDeptId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getStarterDeptName()),PortalContentTodoTasks::getStarterDeptName,portalContentTodoTasksDto.getStarterDeptName());
        queryWrapper.eq(null != portalContentTodoTasksDto.getStarterOrgId(),PortalContentTodoTasks::getStarterOrgId,portalContentTodoTasksDto.getStarterOrgId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getStarterOrgName()),PortalContentTodoTasks::getStarterOrgName,portalContentTodoTasksDto.getStarterOrgName());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentTodoTasksDto.getSApplyTime())&&ObjectUtil.isNotNull(portalContentTodoTasksDto.getEApplyTime()), PortalContentTodoTasks::getApplyTime, portalContentTodoTasksDto.getSApplyTime(),portalContentTodoTasksDto.getEApplyTime());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getProcinsName()),PortalContentTodoTasks::getProcinsName,portalContentTodoTasksDto.getProcinsName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getCurrentNodeName()),PortalContentTodoTasks::getCurrentNodeName,portalContentTodoTasksDto.getCurrentNodeName());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentTodoTasksDto.getBizKey()),PortalContentTodoTasks::getBizKey,portalContentTodoTasksDto.getBizKey());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentTodoTasksDto.getSCliamTime())&&ObjectUtil.isNotNull(portalContentTodoTasksDto.getECliamTime()), PortalContentTodoTasks::getCliamTime, portalContentTodoTasksDto.getSCliamTime(),portalContentTodoTasksDto.getECliamTime());
        queryWrapper.eq(null != portalContentTodoTasksDto.getTaskState() && 0 != portalContentTodoTasksDto.getTaskState(),PortalContentTodoTasks::getTaskState,portalContentTodoTasksDto.getTaskState());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentTodoTasksDto.getSysBizCode()),PortalContentTodoTasks::getSysBizCode,portalContentTodoTasksDto.getSysBizCode());
        queryWrapper.eq(null != portalContentTodoTasksDto.getOwnnerIdCard(),PortalContentTodoTasks::getOwnnerIdCard,portalContentTodoTasksDto.getOwnnerIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getOwnnerName()),PortalContentTodoTasks::getOwnnerName,portalContentTodoTasksDto.getOwnnerName());
        queryWrapper.eq(null != portalContentTodoTasksDto.getAssigneeIdCard(),PortalContentTodoTasks::getAssigneeIdCard,portalContentTodoTasksDto.getAssigneeIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getAssigneeName()),PortalContentTodoTasks::getAssigneeName,portalContentTodoTasksDto.getAssigneeName());
        queryWrapper.eq(null != portalContentTodoTasksDto.getIsDraft() && 0 != portalContentTodoTasksDto.getIsDraft(),PortalContentTodoTasks::getIsDraft,portalContentTodoTasksDto.getIsDraft());
        queryWrapper.like(StringUtils.isNotBlank(portalContentTodoTasksDto.getSysBizClassify()),PortalContentTodoTasks::getSysBizClassify,portalContentTodoTasksDto.getSysBizClassify());
        queryWrapper.eq(PortalContentTodoTasks::getAssigneeIdCard,idCard);
        queryWrapper.orderByDesc(PortalContentTodoTasks::getCreateTime);
        List<PortalContentTodoTasks> list = portalContentTodoTasksMapper.selectList(queryWrapper);
        this.setUrlPrefix(list);
        List<PortalContentTodoTasksVo> portalContentTasksVos = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            portalContentTasksVos = DozerUtils.mapList(dozerMapper, list, PortalContentTodoTasksVo.class);
        }
        return portalContentTasksVos;
    }

    /**
     * 动态设置url 跳转前缀
     * @param list
     */
    public void setUrlPrefix(List<PortalContentTodoTasks> list) {
        if(CollectionUtil.isNotEmpty(list)) {
            for (PortalContentTodoTasks portalContentTodoTasks : list) {
                if(OtherSysEnum.GEPS.code.equals(portalContentTodoTasks.getSysBizCode())) {
                    portalContentTodoTasks.setSysPcBizUrl(gepsTaskUrl + portalContentTodoTasks.getSysPcBizUrl());
                    portalContentTodoTasks.setSysAppBizUrl(gepsTaskUrl + portalContentTodoTasks.getSysAppBizUrl());
                }
                if(OtherSysEnum.OA.code.equals(portalContentTodoTasks.getSysBizCode())) {
                    portalContentTodoTasks.setSysPcBizUrl(oaTaskUrl + portalContentTodoTasks.getSysPcBizUrl());
                    portalContentTodoTasks.setSysAppBizUrl(oaTaskUrl + portalContentTodoTasks.getSysAppBizUrl());
                }
            }
        }
    }

    /**
     * 动态设置url 跳转前缀 给推送的消息使用
     * @param messageDto
     */
    public void setUrlPrefixMsg(MessageDto messageDto) {
        if(null != messageDto) {
            if(OtherSysEnum.GEPS.code.equals(messageDto.getMsgSource())) {
                messageDto.setMsgPcUrl(gepsTaskUrl + messageDto.getMsgPcUrl());
                messageDto.setMsgH5Url(gepsTaskUrl + messageDto.getMsgH5Url());
            }
            if(OtherSysEnum.OA.code.equals(messageDto.getMsgSource())) {
                messageDto.setMsgPcUrl(oaTaskUrl + messageDto.getMsgPcUrl());
                messageDto.setMsgH5Url(oaTaskUrl + messageDto.getMsgH5Url());
            }
        }
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentTodoTasksVo get(Long id) {
        PortalContentTodoTasks portalContentTodoTasks = super.getById(id);
        PortalContentTodoTasksVo portalContentTodoTasksVo = null;
        if(portalContentTodoTasks !=null){
            portalContentTodoTasksVo = dozerMapper.map(portalContentTodoTasks,PortalContentTodoTasksVo.class);
        }
        log.debug("查询成功");
        return portalContentTodoTasksVo;
    }

    /**
    * 保存实体
    * @param portalContentTodoTasksDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentTodoTasksDto portalContentTodoTasksDto) {
        PortalContentTodoTasks portalContentTodoTasks = dozerMapper.map(portalContentTodoTasksDto,PortalContentTodoTasks.class);
        boolean result = super.save(portalContentTodoTasks);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean saveList(List<PortalContentTodoTasksDto> portalContentTodoTasksDtos) {
        log.debug("推送待办数据：=============================start");
        checkPortalContentTasks(portalContentTodoTasksDtos);
        List<PortalContentTodoTasks> addResultList = new ArrayList<>();
        List<PortalContentTodoTasks> upResultList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(portalContentTodoTasksDtos)) {
            for (PortalContentTodoTasksDto portalContentTaskDto : portalContentTodoTasksDtos) {
                ApiTaskDto apiTaskDto = new ApiTaskDto();
                apiTaskDto.setSysBizId(portalContentTaskDto.getSysBizId());
                apiTaskDto.setSysBizCode(portalContentTaskDto.getSysBizCode());
                PortalContentTodoTasks portalContentTodoTasks = this.getBySysBizIdAndCode(apiTaskDto);
                //如果存在之前的消息 先删除
                if(null != portalContentTodoTasks) {
                    if(null != portalContentTodoTasks.getMsgId() && 0L != portalContentTodoTasks.getMsgId()) {
                        msgClient.delMsg(String.valueOf(portalContentTodoTasks.getMsgId()));
                    }
                }
                //发送消息
                this.sendMsg(portalContentTaskDto);
                this.sendTasksMsg(portalContentTaskDto.getAssigneeIdCard());
                if(null != portalContentTodoTasks) {
                    portalContentTaskDto.setId(portalContentTodoTasks.getId());//把数据库中的id 赋值给dto
                    PortalContentTodoTasks portalContentTask = dozerMapper.map(portalContentTaskDto,PortalContentTodoTasks.class);
                    portalContentTask.setAssigneeIdCard(portalContentTask.getAssigneeIdCard().trim());
                    upResultList.add(portalContentTask);
                } else {
                    PortalContentTodoTasks portalContentTask = dozerMapper.map(portalContentTaskDto,PortalContentTodoTasks.class);
                    portalContentTask.setAssigneeIdCard(portalContentTask.getAssigneeIdCard().trim());
                    addResultList.add(portalContentTask);
                }
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

    //发送消息 右上角弹出框
    public void sendMsg(PortalContentTodoTasksDto portalContentTodoTasksDto) {
        try {
            //发送消息
            MessageDto messageDto = new MessageDto();
            messageDto.setTmpCode(MessageTmpEnum.TODOTASK.getCode());
            JSONObject object = new JSONObject();
            object.set("procinsName",portalContentTodoTasksDto.getProcinsName());
            messageDto.setMsgParams(object.toString());
            messageDto.setMsgPcUrl(portalContentTodoTasksDto.getSysPcBizUrl());
            messageDto.setMsgH5Url(portalContentTodoTasksDto.getSysAppBizUrl());
            messageDto.setIsSystemSend(YesNo.YES.code);
            messageDto.setMsgSource(portalContentTodoTasksDto.getSysBizCode());
            List<MessageReceiverUserDto> messageReceiverUserDtos = new ArrayList<>();
            MessageReceiverUserDto messageReceiverUserDto = new MessageReceiverUserDto();
            messageReceiverUserDto.setReceiverIdcard(portalContentTodoTasksDto.getAssigneeIdCard());
            messageReceiverUserDto.setReceiverUserName(portalContentTodoTasksDto.getAssigneeName());
            messageReceiverUserDtos.add(messageReceiverUserDto);
            messageDto.setReceiverUserList(messageReceiverUserDtos);
            setUrlPrefixMsg(messageDto);
            List<MessageVo> rsultMsg = msgClient.sendTmpMsg(messageDto);
            if(CollectionUtil.isNotEmpty(rsultMsg)) {
                portalContentTodoTasksDto.setMsgId(rsultMsg.get(0).getId());
            }
        }catch (Exception e) {
            log.debug("集中待办发送消息失败！" + e.getMessage());
        }
    }

    //发送消息 刷新待办页面
    public void sendTasksMsg(String idCard){
        List<MdmUserVo> mdmUserVos = baseMdmClient.getUserByIdCards(idCard.trim());
        Map<String,Object> map = new HashMap<>();
        map.put("receiverUserId",mdmUserVos.get(0).getId());
        map.put("isTasks",1);
        rocketMqSendUtil.sendMsg(map);
    }

    public void checkPortalContentTasks(List<PortalContentTodoTasksDto> portalContentTodoTasksDtos) {
        if(CollectionUtil.isNotEmpty(portalContentTodoTasksDtos)) {
            for (PortalContentTodoTasksDto portalContentTask : portalContentTodoTasksDtos) {
//                if(StringUtils.isBlank(portalContentTask.getStarterIdCard())) {
//                    throw new IncloudException("起草人id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentTask.getStarterName())) {
//                    throw new IncloudException("起草人名称不能为空。");
//                }
//                if(null == portalContentTask.getStarterDeptId() || 0L == portalContentTask.getStarterDeptId()) {
//                    throw new IncloudException("起草人部门id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentTask.getStarterDeptName())) {
//                    throw new IncloudException("起草人部门名称不能为空。");
//                }
//                if(null == portalContentTask.getStarterOrgId() || 0L == portalContentTask.getStarterOrgId()) {
//                    throw new IncloudException("起草人机构id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentTask.getStarterOrgName())) {
//                    throw new IncloudException("起草人机构名称不能为空。");
//                }
//                if(null == portalContentTask.getApplyTime()) {
//                    throw new IncloudException("申请时间不能为空。");
//                }
                if(StringUtils.isBlank(portalContentTask.getProcinsName())) {
                    throw new IncloudException("流程实例名称/标题不能为空。");
                }
//                if(StringUtils.isBlank(portalContentTask.getCurrentNodeName())) {
//                    throw new IncloudException("当前节点名称不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentTask.getBizKey())) {
//                    throw new IncloudException("业务单据号/流水号不能为空。");
//                }
//                if(null == portalContentTask.getTaskState() || 0L == portalContentTask.getTaskState()) {
//                    throw new IncloudException("任务状态不能为空。");
//                }
                if(StringUtils.isBlank(portalContentTask.getSysPcBizUrl())) {
                    throw new IncloudException("PC业务系统表单详情页面URL不能为空。");
                }
                if(StringUtils.isBlank(portalContentTask.getSysAppBizUrl())) {
                    throw new IncloudException("手机业务系统表单详情页面URL不能为空。");
                }

                if(StringUtils.isBlank(portalContentTask.getSysBizCode())) {
                    throw new IncloudException("业务系统CODE不能为空。");
                }
                if(StringUtils.isBlank(portalContentTask.getSysBizId())) {
                    throw new IncloudException("业务系统ID不能为空。");
                }

                if(StringUtils.isBlank(portalContentTask.getAssigneeIdCard())) {
                    throw new IncloudException("任务办理人ID不能为空。");
                }
                if(StringUtils.isBlank(portalContentTask.getAssigneeName())) {
                    throw new IncloudException("任务办理人名称不能为空。");
                }
                if(StringUtils.isBlank(portalContentTask.getSysBizClassify())) {
                    throw new IncloudException("数据的系统业务类型不能为空。");
                }
            }
            //判断一组数据中，有没有重复的，重复报错
            long count = portalContentTodoTasksDtos.stream().distinct().count();
            if (portalContentTodoTasksDtos.size() > count) {
                throw new IncloudException("数据集合中有重复的数据！");
            }
        }
    }

    /**
    * 修改实体
    * @param portalContentTodoTasksDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentTodoTasksDto portalContentTodoTasksDto) {
        portalContentTodoTasksDto.setUpdateTime(LocalDateTime.now());
        PortalContentTodoTasks portalContentTodoTasks = dozerMapper.map(portalContentTodoTasksDto,PortalContentTodoTasks.class);
        boolean result = super.updateById(portalContentTodoTasks);
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
    @Transactional
    public Boolean delBySysBizIdAndCode(ApiTaskDto apiTaskDto) {
        if(StringUtils.isBlank(apiTaskDto.getSysBizId())) {
            throw new IncloudException("sysBizId 不能为空！");
        }
        if(StringUtils.isBlank(apiTaskDto.getSysBizCode())) {
            throw new IncloudException("sysBizCode 不能为空！");
        }
        PortalContentTodoTasks portalContentTodoTasks = getBySysBizIdAndCode(apiTaskDto);
        log.debug("推送删除待办数据：=============================start。参数sysBizId：" + apiTaskDto.getSysBizCode() + ",sysBizCode:" + apiTaskDto.getSysBizCode());
        LambdaQueryWrapper<PortalContentTodoTasks> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(PortalContentTodoTasks::getSysBizId,apiTaskDto.getSysBizId());
        delWrapper.eq(PortalContentTodoTasks::getSysBizCode,apiTaskDto.getSysBizCode());
        int line = portalContentTodoTasksMapper.delete(delWrapper);
        if(line > 0) {
            if (ObjectUtil.isNotNull(portalContentTodoTasks.getMsgId())) {
                msgClient.delMsg(String.valueOf(portalContentTodoTasks.getMsgId()));//删除待办消息
            }
            this.sendTasksMsg(portalContentTodoTasks.getAssigneeIdCard());//刷新待办列表消息
            log.debug("推送删除待办数据：=============================end。返回：" + true);
            return true;
        } else {
            log.debug("推送删除待办数据：=============================end。返回：" + false);
            return false;
        }
    }

    @Override
    public PortalContentTodoTasks getBySysBizIdAndCode(ApiTaskDto apiTaskDto) {
        if(StringUtils.isBlank(apiTaskDto.getSysBizId())) {
            throw new IncloudException("sysBizId 不能为空！");
        }

        if(StringUtils.isBlank(apiTaskDto.getSysBizCode())) {
            throw new IncloudException("sysBizCode 不能为空！");
        }
        LambdaQueryWrapper<PortalContentTodoTasks> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(PortalContentTodoTasks::getSysBizId,apiTaskDto.getSysBizId());
        delWrapper.eq(PortalContentTodoTasks::getSysBizCode,apiTaskDto.getSysBizCode());
        PortalContentTodoTasks portalContentTodoTasks = portalContentTodoTasksMapper.selectOne(delWrapper);
        return portalContentTodoTasks;
    }

    @Override
    public Boolean cliamHandle(ApiTaskDto apiTaskDto) {
        if(StringUtils.isBlank(apiTaskDto.getSysBizId())) {
            throw new IncloudException("sysBizId 不能为空！");
        }

        if(StringUtils.isBlank(apiTaskDto.getSysBizCode())) {
            throw new IncloudException("sysBizCode 不能为空！");
        }
        log.debug("推送删除待办数据：=============================start。参数sysBizId：" + apiTaskDto.getSysBizCode() + ",sysBizCode:" + apiTaskDto.getSysBizCode());
        LambdaUpdateWrapper<PortalContentTodoTasks> upWrapper = new LambdaUpdateWrapper<>();
        upWrapper.eq(PortalContentTodoTasks::getSysBizId,apiTaskDto.getSysBizId());
        upWrapper.eq(PortalContentTodoTasks::getSysBizId,apiTaskDto.getSysBizCode());
        upWrapper.set(PortalContentTodoTasks::getCliamTime, LocalDateTime.now());
        boolean boo = this.update(upWrapper);
        log.debug("推送签收待办数据：=============================end。返回：" + boo);
        return boo;
    }

    @Override
    @Transactional
    public Boolean delAllBySysBizIdAndCode(ApiTaskDto apiTaskDto) {
        log.debug("删除所有集中待办数据：=============================start。参数sysBizId：" + apiTaskDto.getSysBizCode() + ",sysBizCode:" + apiTaskDto.getSysBizCode());
        this.delBySysBizIdAndCode(apiTaskDto);
        portalContentDoneTasksService.delBySysBizIdAndCode(apiTaskDto);
        portalContentUnreadTasksService.delBySysBizIdAndCode(apiTaskDto);
        portalContentMydraftTasksService.delBySysBizIdAndCode(apiTaskDto);
        portalContentReadTasksService.delBySysBizIdAndCode(apiTaskDto);
        log.debug("删除所有集中待办数据：=============================end。");
        return true;
    }
}
