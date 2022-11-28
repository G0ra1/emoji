package com.netwisd.base.portal.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.MessageTmpEnum;
import com.netwisd.base.common.constants.OtherSysEnum;
import com.netwisd.base.common.constants.YesNo;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.common.msg.dto.MessageReceiverUserDto;
import com.netwisd.base.common.msg.vo.MessageVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.portal.dto.ApiTaskDto;
import com.netwisd.base.portal.dto.PortalContentTodoTasksDto;
import com.netwisd.base.portal.entity.PortalContentMydraftTasks;
import com.netwisd.base.portal.entity.PortalContentTodoTasks;
import com.netwisd.base.portal.entity.PortalContentUnreadTasks;
import com.netwisd.base.portal.fegin.MsgClient;
import com.netwisd.base.portal.mapper.PortalContentUnreadTasksMapper;
import com.netwisd.base.portal.service.PortalContentUnreadTasksService;
import com.netwisd.base.portal.vo.PortalContentTodoTasksVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentUnreadTasksDto;
import com.netwisd.base.portal.vo.PortalContentUnreadTasksVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 任务集成类-待阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 17:09:00
 */
@Service
@Slf4j
public class PortalContentUnreadTasksServiceImpl extends ServiceImpl<PortalContentUnreadTasksMapper, PortalContentUnreadTasks> implements PortalContentUnreadTasksService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentUnreadTasksMapper portalContentUnreadTasksMapper;

    @Value("${portal.gepsTaskUrl}")
    private String gepsTaskUrl;

    @Value("${portal.oaTaskUrl}")
    private String oaTaskUrl;

    @Autowired
    private MsgClient msgClient;

    /**
    * 单表简单查询操作
    * @param portalContentUnreadTasksDto
    * @return
    */
    @Override
    public Page list(PortalContentUnreadTasksDto portalContentUnreadTasksDto) {
        //获取当前登录人
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String idCard = loginAppUser.getIdCard();
        LambdaQueryWrapper<PortalContentUnreadTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != portalContentUnreadTasksDto.getStarterIdCard(), PortalContentUnreadTasks::getStarterIdCard,portalContentUnreadTasksDto.getStarterIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getStarterName()),PortalContentUnreadTasks::getStarterName,portalContentUnreadTasksDto.getStarterName());
        queryWrapper.eq(null != portalContentUnreadTasksDto.getStarterDeptId(),PortalContentUnreadTasks::getStarterDeptId,portalContentUnreadTasksDto.getStarterDeptId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getStarterDeptName()),PortalContentUnreadTasks::getStarterDeptName,portalContentUnreadTasksDto.getStarterDeptName());
        queryWrapper.eq(null != portalContentUnreadTasksDto.getStarterOrgId(),PortalContentUnreadTasks::getStarterOrgId,portalContentUnreadTasksDto.getStarterOrgId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getStarterOrgName()),PortalContentUnreadTasks::getStarterOrgName,portalContentUnreadTasksDto.getStarterOrgName());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentUnreadTasksDto.getSAcceptTime())&&ObjectUtil.isNotNull(portalContentUnreadTasksDto.getEAcceptTime()), PortalContentUnreadTasks::getApplyTime, portalContentUnreadTasksDto.getSAcceptTime(),portalContentUnreadTasksDto.getEAcceptTime());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getProcinsName()),PortalContentUnreadTasks::getProcinsName,portalContentUnreadTasksDto.getProcinsName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getCurrentNodeName()),PortalContentUnreadTasks::getCurrentNodeName,portalContentUnreadTasksDto.getCurrentNodeName());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentUnreadTasksDto.getBizKey()),PortalContentUnreadTasks::getBizKey,portalContentUnreadTasksDto.getBizKey());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentUnreadTasksDto.getSysBizCode()),PortalContentUnreadTasks::getSysBizCode,portalContentUnreadTasksDto.getSysBizCode());
        queryWrapper.eq(null != portalContentUnreadTasksDto.getOwnnerIdCard(),PortalContentUnreadTasks::getOwnnerIdCard,portalContentUnreadTasksDto.getOwnnerIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getOwnnerName()),PortalContentUnreadTasks::getOwnnerName,portalContentUnreadTasksDto.getOwnnerName());
        queryWrapper.eq(null != portalContentUnreadTasksDto.getAssigneeIdCard(),PortalContentUnreadTasks::getAssigneeIdCard,portalContentUnreadTasksDto.getAssigneeIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getAssigneeName()),PortalContentUnreadTasks::getAssigneeName,portalContentUnreadTasksDto.getAssigneeName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getSysBizClassify()),PortalContentUnreadTasks::getSysBizClassify,portalContentUnreadTasksDto.getSysBizClassify());
        queryWrapper.eq(PortalContentUnreadTasks::getAssigneeIdCard,idCard);
        queryWrapper.orderByDesc(PortalContentUnreadTasks::getCreateTime);
        Page<PortalContentUnreadTasks> page = portalContentUnreadTasksMapper.selectPage(portalContentUnreadTasksDto.getPage(),queryWrapper);
        List<PortalContentUnreadTasks> list = page.getRecords();
        this.setUrlPrefix(list);
        Page<PortalContentUnreadTasksVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentUnreadTasksVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 动态设置url 跳转前缀
     * @param list
     */
    public void setUrlPrefix(List<PortalContentUnreadTasks> list) {
        if(CollectionUtil.isNotEmpty(list)) {
            for (PortalContentUnreadTasks portalContentUnreadTasks : list) {
                if(OtherSysEnum.GEPS.code.equals(portalContentUnreadTasks.getSysBizCode())) {
                    portalContentUnreadTasks.setSysPcBizUrl(gepsTaskUrl + portalContentUnreadTasks.getSysPcBizUrl());
                    portalContentUnreadTasks.setSysAppBizUrl(gepsTaskUrl + portalContentUnreadTasks.getSysAppBizUrl());
                }
                if(OtherSysEnum.OA.code.equals(portalContentUnreadTasks.getSysBizCode())) {
                    portalContentUnreadTasks.setSysPcBizUrl(oaTaskUrl + portalContentUnreadTasks.getSysPcBizUrl());
                    portalContentUnreadTasks.setSysAppBizUrl(oaTaskUrl + portalContentUnreadTasks.getSysAppBizUrl());
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
    * 自定义查询操作
    * @param portalContentUnreadTasksDto
    * @return
    */
    @Override
    public List<PortalContentUnreadTasksVo> lists(PortalContentUnreadTasksDto portalContentUnreadTasksDto) {
        LambdaQueryWrapper<PortalContentUnreadTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != portalContentUnreadTasksDto.getStarterIdCard(), PortalContentUnreadTasks::getStarterIdCard,portalContentUnreadTasksDto.getStarterIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getStarterName()),PortalContentUnreadTasks::getStarterName,portalContentUnreadTasksDto.getStarterName());
        queryWrapper.eq(null != portalContentUnreadTasksDto.getStarterDeptId(),PortalContentUnreadTasks::getStarterDeptId,portalContentUnreadTasksDto.getStarterDeptId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getStarterDeptName()),PortalContentUnreadTasks::getStarterDeptName,portalContentUnreadTasksDto.getStarterDeptName());
        queryWrapper.eq(null != portalContentUnreadTasksDto.getStarterOrgId(),PortalContentUnreadTasks::getStarterOrgId,portalContentUnreadTasksDto.getStarterOrgId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getStarterOrgName()),PortalContentUnreadTasks::getStarterOrgName,portalContentUnreadTasksDto.getStarterOrgName());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentUnreadTasksDto.getSAcceptTime())&&ObjectUtil.isNotNull(portalContentUnreadTasksDto.getEAcceptTime()), PortalContentUnreadTasks::getApplyTime, portalContentUnreadTasksDto.getSAcceptTime(),portalContentUnreadTasksDto.getEAcceptTime());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getProcinsName()),PortalContentUnreadTasks::getProcinsName,portalContentUnreadTasksDto.getProcinsName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getCurrentNodeName()),PortalContentUnreadTasks::getCurrentNodeName,portalContentUnreadTasksDto.getCurrentNodeName());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentUnreadTasksDto.getBizKey()),PortalContentUnreadTasks::getBizKey,portalContentUnreadTasksDto.getBizKey());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentUnreadTasksDto.getSysBizCode()),PortalContentUnreadTasks::getSysBizCode,portalContentUnreadTasksDto.getSysBizCode());
        queryWrapper.eq(null != portalContentUnreadTasksDto.getOwnnerIdCard(),PortalContentUnreadTasks::getOwnnerIdCard,portalContentUnreadTasksDto.getOwnnerIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getOwnnerName()),PortalContentUnreadTasks::getOwnnerName,portalContentUnreadTasksDto.getOwnnerName());
        queryWrapper.eq(null != portalContentUnreadTasksDto.getAssigneeIdCard(),PortalContentUnreadTasks::getAssigneeIdCard,portalContentUnreadTasksDto.getAssigneeIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getAssigneeName()),PortalContentUnreadTasks::getAssigneeName,portalContentUnreadTasksDto.getAssigneeName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentUnreadTasksDto.getSysBizClassify()),PortalContentUnreadTasks::getSysBizClassify,portalContentUnreadTasksDto.getSysBizClassify());
        queryWrapper.orderByDesc(PortalContentUnreadTasks::getAcceptTime);
        List<PortalContentUnreadTasks> list = portalContentUnreadTasksMapper.selectList(queryWrapper);
        this.setUrlPrefix(list);
        List<PortalContentUnreadTasksVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, PortalContentUnreadTasksVo.class);
        }
        return listVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentUnreadTasksVo get(Long id) {
        PortalContentUnreadTasks portalContentUnreadTasks = super.getById(id);
        PortalContentUnreadTasksVo portalContentUnreadTasksVo = null;
        if(portalContentUnreadTasks !=null){
            portalContentUnreadTasksVo = dozerMapper.map(portalContentUnreadTasks,PortalContentUnreadTasksVo.class);
        }
        log.debug("查询成功");
        return portalContentUnreadTasksVo;
    }

    /**
    * 保存实体
    * @param portalContentUnreadTasksDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentUnreadTasksDto portalContentUnreadTasksDto) {
        PortalContentUnreadTasks portalContentUnreadTasks = dozerMapper.map(portalContentUnreadTasksDto,PortalContentUnreadTasks.class);
        boolean result = super.save(portalContentUnreadTasks);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    @Override
    public Boolean saveList(List<PortalContentUnreadTasksDto> portalContentUnreadTasksDtos) {
        log.debug("推送待阅数据：=============================start");
        checkPortalContentUnreadTasksDtos(portalContentUnreadTasksDtos);
        List<PortalContentUnreadTasks> addResultList = new ArrayList<>();
        List<PortalContentUnreadTasks> upResultList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(portalContentUnreadTasksDtos)) {
            for (PortalContentUnreadTasksDto portalContentUnreadTasksDto : portalContentUnreadTasksDtos) {
                ApiTaskDto apiTaskDto = new ApiTaskDto();
                apiTaskDto.setSysBizId(portalContentUnreadTasksDto.getSysBizId());
                apiTaskDto.setSysBizCode(portalContentUnreadTasksDto.getSysBizCode());
                PortalContentUnreadTasks portalContentUnreadTasks = this.getBySysBizIdAndCode(apiTaskDto);
                //如果存在之前的消息 先删除
                if(null != portalContentUnreadTasks) {
                    if(null != portalContentUnreadTasksDto.getMsgId() && 0L != portalContentUnreadTasksDto.getMsgId()) {
                        msgClient.delMsg(String.valueOf(portalContentUnreadTasksDto.getMsgId()));
                    }
                }
                //发送消息
                this.sendMsg(portalContentUnreadTasksDto);
                if(null != portalContentUnreadTasks) {
                    portalContentUnreadTasksDto.setId(portalContentUnreadTasks.getId());//把数据库中的id 赋值给dto
                    PortalContentUnreadTasks _portalContentUnreadTasks = dozerMapper.map(portalContentUnreadTasksDto,PortalContentUnreadTasks.class);
                    upResultList.add(_portalContentUnreadTasks);
                } else {
                    PortalContentUnreadTasks _portalContentUnreadTasks = dozerMapper.map(portalContentUnreadTasksDto,PortalContentUnreadTasks.class);
                    addResultList.add(_portalContentUnreadTasks);
                }
            }
        }
        if(CollectionUtil.isNotEmpty(addResultList)) {
            this.saveBatch(addResultList);
        }
        if(CollectionUtil.isNotEmpty(upResultList)) {
            this.updateBatchById(upResultList);
        }
        log.debug("推送待阅数据：=============================end");
        return true;
    }

    public void sendMsg(PortalContentUnreadTasksDto portalContentUnreadTasksDto) {
        try {
            //发送消息
            MessageDto messageDto = new MessageDto();
            messageDto.setTmpCode(MessageTmpEnum.UNREADTASK.getCode());
            JSONObject object = new JSONObject();
            object.set("procinsName",portalContentUnreadTasksDto.getProcinsName());
            messageDto.setMsgParams(object.toString());
            messageDto.setMsgPcUrl(portalContentUnreadTasksDto.getSysPcBizUrl());
            messageDto.setMsgH5Url(portalContentUnreadTasksDto.getSysAppBizUrl());
            messageDto.setIsSystemSend(YesNo.YES.code);
            messageDto.setMsgSource(portalContentUnreadTasksDto.getSysBizCode());
            List<MessageReceiverUserDto> messageReceiverUserDtos = new ArrayList<>();
            MessageReceiverUserDto messageReceiverUserDto = new MessageReceiverUserDto();
            messageReceiverUserDto.setReceiverIdcard(portalContentUnreadTasksDto.getAssigneeIdCard());
            messageReceiverUserDto.setReceiverUserName(portalContentUnreadTasksDto.getAssigneeName());
            messageReceiverUserDtos.add(messageReceiverUserDto);
            messageDto.setReceiverUserList(messageReceiverUserDtos);
            this.setUrlPrefixMsg(messageDto);
            List<MessageVo> rsultMsg = msgClient.sendTmpMsg(messageDto);
            if(CollectionUtil.isNotEmpty(rsultMsg)) {
                portalContentUnreadTasksDto.setMsgId(rsultMsg.get(0).getId());
            }
        }catch (Exception e) {
            log.debug("集中待阅发送消息失败！" + e.getMessage());
        }
    }

    public void checkPortalContentUnreadTasksDtos(List<PortalContentUnreadTasksDto> portalContentUnreadTasksDtos) {
        if(CollectionUtil.isNotEmpty(portalContentUnreadTasksDtos)) {
            for (PortalContentUnreadTasksDto portalContentUnreadTasksDto : portalContentUnreadTasksDtos) {
//                if(StringUtils.isBlank(portalContentUnreadTasksDto.getStarterIdCard())) {
//                    throw new IncloudException("起草人id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentUnreadTasksDto.getStarterName())) {
//                    throw new IncloudException("起草人名称不能为空。");
//                }
//                if(null == portalContentUnreadTasksDto.getStarterDeptId() || 0L == portalContentUnreadTasksDto.getStarterDeptId()) {
//                    throw new IncloudException("起草人部门id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentUnreadTasksDto.getStarterDeptName())) {
//                    throw new IncloudException("起草人部门名称不能为空。");
//                }
//                if(null == portalContentUnreadTasksDto.getStarterOrgId() || 0L == portalContentUnreadTasksDto.getStarterOrgId()) {
//                    throw new IncloudException("起草人机构id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentUnreadTasksDto.getStarterOrgName())) {
//                    throw new IncloudException("起草人机构名称不能为空。");
//                }
//                if(null == portalContentUnreadTasksDto.getApplyTime()) {
//                    throw new IncloudException("申请时间不能为空。");
//                }
                if(StringUtils.isBlank(portalContentUnreadTasksDto.getProcinsName())) {
                    throw new IncloudException("流程实例名称/标题不能为空。");
                }
//                if(StringUtils.isBlank(portalContentUnreadTasksDto.getCurrentNodeName())) {
//                    throw new IncloudException("当前节点名称不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentUnreadTasksDto.getBizKey())) {
//                    throw new IncloudException("业务单据号/流水号不能为空。");
//                }
                if(StringUtils.isBlank(portalContentUnreadTasksDto.getSysPcBizUrl())) {
                    throw new IncloudException("PC业务系统表单详情页面URL不能为空。");
                }

                if(StringUtils.isBlank(portalContentUnreadTasksDto.getSysAppBizUrl())) {
                    throw new IncloudException("APP业务系统表单详情页面URL不能为空。");
                }

                if(StringUtils.isBlank(portalContentUnreadTasksDto.getSysBizCode())) {
                    throw new IncloudException("业务系统CODE不能为空。");
                }
                if(StringUtils.isBlank(portalContentUnreadTasksDto.getSysBizId())) {
                    throw new IncloudException("业务系统ID不能为空。");
                }

//                if(null == portalContentUnreadTasksDto.getAssigneeId() || 0L == portalContentUnreadTasksDto.getAssigneeId()) {
//                    throw new IncloudException("任务办理人ID不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentUnreadTasksDto.getAssigneeName())) {
//                    throw new IncloudException("任务办理人名称不能为空。");
//                }
                if(StringUtils.isBlank(portalContentUnreadTasksDto.getSysBizClassify())) {
                    throw new IncloudException("数据的系统业务类型不能为空。");
                }
            }
        }
    }

    /**
    * 修改实体
    * @param portalContentUnreadTasksDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentUnreadTasksDto portalContentUnreadTasksDto) {
        portalContentUnreadTasksDto.setUpdateTime(LocalDateTime.now());
        PortalContentUnreadTasks portalContentUnreadTasks = dozerMapper.map(portalContentUnreadTasksDto,PortalContentUnreadTasks.class);
        boolean result = super.updateById(portalContentUnreadTasks);
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
    public Boolean delBySysBizIdAndCode(ApiTaskDto apiTaskDto) {
        if(StringUtils.isBlank(apiTaskDto.getSysBizId())) {
            throw new IncloudException("sysBizId 不能为空！");
        }

        if(StringUtils.isBlank(apiTaskDto.getSysBizCode())) {
            throw new IncloudException("sysBizCode 不能为空！");
        }
        log.debug("推送集中待办【待阅】数据：=============================start。参数sysBizId：" + apiTaskDto.getSysBizCode() + ",sysBizCode:" + apiTaskDto.getSysBizCode());
        PortalContentUnreadTasks portalContentUnreadTasks = this.getBySysBizIdAndCode(apiTaskDto);
        if(null != portalContentUnreadTasks) {
            msgClient.delMsg(String.valueOf(portalContentUnreadTasks.getMsgId()));
        }
        LambdaQueryWrapper<PortalContentUnreadTasks> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(PortalContentUnreadTasks::getSysBizId,apiTaskDto.getSysBizId());
        delWrapper.eq(PortalContentUnreadTasks::getSysBizCode,apiTaskDto.getSysBizCode());
        int line = portalContentUnreadTasksMapper.delete(delWrapper);
        if(line > 0) {
            log.debug("推送删除我发起的任务数据：=============================end。返回：" + true);
            return true;
        } else {
            log.debug("推送删除我发起的任务数据：=============================end。返回：" + false);
            return false;
        }
    }


    @Override
    public PortalContentUnreadTasks getBySysBizIdAndCode(ApiTaskDto apiTaskDto) {
        if(StringUtils.isBlank(apiTaskDto.getSysBizId())) {
            throw new IncloudException("sysBizId 不能为空！");
        }

        if(StringUtils.isBlank(apiTaskDto.getSysBizCode())) {
            throw new IncloudException("sysBizCode 不能为空！");
        }
        LambdaQueryWrapper<PortalContentUnreadTasks> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(PortalContentUnreadTasks::getSysBizId,apiTaskDto.getSysBizId());
        delWrapper.eq(PortalContentUnreadTasks::getSysBizCode,apiTaskDto.getSysBizCode());
        PortalContentUnreadTasks portalContentUnreadTasks = portalContentUnreadTasksMapper.selectOne(delWrapper);
        return portalContentUnreadTasks;
    }
}
