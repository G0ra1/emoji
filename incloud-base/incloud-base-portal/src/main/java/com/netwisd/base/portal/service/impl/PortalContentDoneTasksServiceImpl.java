package com.netwisd.base.portal.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.constants.OtherSysEnum;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.portal.dto.ApiTaskDto;
import com.netwisd.base.portal.entity.PortalContentDoneTasks;
import com.netwisd.base.portal.entity.PortalContentTodoTasks;
import com.netwisd.base.portal.mapper.PortalContentDoneTasksMapper;
import com.netwisd.base.portal.service.PortalContentDoneTasksService;
import com.netwisd.base.portal.service.PortalContentTodoTasksService;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentDoneTasksDto;
import com.netwisd.base.portal.vo.PortalContentDoneTasksVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 任务集成类内容-已办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-27 18:17:26
 */
@Service
@Slf4j
public class PortalContentDoneTasksServiceImpl extends ServiceImpl<PortalContentDoneTasksMapper, PortalContentDoneTasks> implements PortalContentDoneTasksService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentDoneTasksMapper portalContentDoneTasksMapper;

    @Autowired
    private PortalContentTodoTasksService portalContentTodoTasksService;

    @Value("${portal.gepsTaskUrl}")
    private String gepsTaskUrl;

    @Value("${portal.oaTaskUrl}")
    private String oaTaskUrl;

    /**
    * 单表简单查询操作
    * @param portalContentDoneTasksDto
    * @return
    */
    @Override
    public Page list(PortalContentDoneTasksDto portalContentDoneTasksDto) {
        //获取当前登录人
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String idCard = loginAppUser.getIdCard();
        LambdaQueryWrapper<PortalContentDoneTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != portalContentDoneTasksDto.getStarterIdCard(),PortalContentDoneTasks::getStarterIdCard,portalContentDoneTasksDto.getStarterIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getStarterName()),PortalContentDoneTasks::getStarterName,portalContentDoneTasksDto.getStarterName());
        queryWrapper.eq(null != portalContentDoneTasksDto.getStarterDeptId(),PortalContentDoneTasks::getStarterDeptId,portalContentDoneTasksDto.getStarterDeptId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getStarterDeptName()),PortalContentDoneTasks::getStarterDeptName,portalContentDoneTasksDto.getStarterDeptName());
        queryWrapper.eq(null != portalContentDoneTasksDto.getStarterOrgId(),PortalContentDoneTasks::getStarterOrgId,portalContentDoneTasksDto.getStarterOrgId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getStarterOrgName()),PortalContentDoneTasks::getStarterOrgName,portalContentDoneTasksDto.getStarterOrgName());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentDoneTasksDto.getSApplyTime())&&ObjectUtil.isNotNull(portalContentDoneTasksDto.getEApplyTime()), PortalContentDoneTasks::getApplyTime, portalContentDoneTasksDto.getSApplyTime(),portalContentDoneTasksDto.getEApplyTime());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getProcinsName()),PortalContentDoneTasks::getProcinsName,portalContentDoneTasksDto.getProcinsName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getCurrentNodeName()),PortalContentDoneTasks::getCurrentNodeName,portalContentDoneTasksDto.getCurrentNodeName());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentDoneTasksDto.getBizKey()),PortalContentDoneTasks::getBizKey,portalContentDoneTasksDto.getBizKey());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentDoneTasksDto.getSCliamTime())&&ObjectUtil.isNotNull(portalContentDoneTasksDto.getECliamTime()), PortalContentDoneTasks::getCliamTime, portalContentDoneTasksDto.getSCliamTime(),portalContentDoneTasksDto.getECliamTime());
        queryWrapper.eq(null != portalContentDoneTasksDto.getTaskState() && 0 != portalContentDoneTasksDto.getTaskState(),PortalContentDoneTasks::getTaskState,portalContentDoneTasksDto.getTaskState());

        queryWrapper.eq(StringUtils.isNotBlank(portalContentDoneTasksDto.getSysBizCode()),PortalContentDoneTasks::getSysBizCode,portalContentDoneTasksDto.getSysBizCode());
        queryWrapper.eq(null != portalContentDoneTasksDto.getOwnnerIdCard(),PortalContentDoneTasks::getOwnnerIdCard,portalContentDoneTasksDto.getOwnnerIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getOwnnerName()),PortalContentDoneTasks::getOwnnerName,portalContentDoneTasksDto.getOwnnerName());
        queryWrapper.eq(null != portalContentDoneTasksDto.getAssigneeIdCard(),PortalContentDoneTasks::getAssigneeIdCard,portalContentDoneTasksDto.getAssigneeIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getAssigneeName()),PortalContentDoneTasks::getAssigneeName,portalContentDoneTasksDto.getAssigneeName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getSysBizClassify()), PortalContentDoneTasks::getSysBizClassify,portalContentDoneTasksDto.getSysBizClassify());
        queryWrapper.eq(PortalContentDoneTasks::getAssigneeIdCard,idCard);
        queryWrapper.orderByDesc(PortalContentDoneTasks::getCreateTime);
        Page<PortalContentDoneTasks> page = portalContentDoneTasksMapper.selectPage(portalContentDoneTasksDto.getPage(),queryWrapper);
        List<PortalContentDoneTasks> list = page.getRecords();
        this.setUrlPrefix(list);
        Page<PortalContentDoneTasksVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentDoneTasksVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 动态设置url 跳转前缀
     * @param list
     */
    public void setUrlPrefix(List<PortalContentDoneTasks> list) {
        if(CollectionUtil.isNotEmpty(list)) {
            for (PortalContentDoneTasks portalContentDoneTasks : list) {
                if(OtherSysEnum.GEPS.code.equals(portalContentDoneTasks.getSysBizCode())) {
                    portalContentDoneTasks.setSysPcBizUrl(gepsTaskUrl + portalContentDoneTasks.getSysPcBizUrl());
                    portalContentDoneTasks.setSysAppBizUrl(gepsTaskUrl + portalContentDoneTasks.getSysAppBizUrl());
                }
                if(OtherSysEnum.OA.code.equals(portalContentDoneTasks.getSysBizCode())) {
                    portalContentDoneTasks.setSysPcBizUrl(oaTaskUrl + portalContentDoneTasks.getSysPcBizUrl());
                    portalContentDoneTasks.setSysAppBizUrl(oaTaskUrl + portalContentDoneTasks.getSysAppBizUrl());
                }
            }
        }
    }

    /**
    * 自定义查询操作
    * @param portalContentDoneTasksDto
    * @return
    */
    @Override
    public List<PortalContentDoneTasksVo> lists(PortalContentDoneTasksDto portalContentDoneTasksDto) {
        //获取当前登录人
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String idCard = loginAppUser.getIdCard();
        LambdaQueryWrapper<PortalContentDoneTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != portalContentDoneTasksDto.getStarterIdCard(),PortalContentDoneTasks::getStarterIdCard,portalContentDoneTasksDto.getStarterIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getStarterName()),PortalContentDoneTasks::getStarterName,portalContentDoneTasksDto.getStarterName());
        queryWrapper.eq(null != portalContentDoneTasksDto.getStarterDeptId(),PortalContentDoneTasks::getStarterDeptId,portalContentDoneTasksDto.getStarterDeptId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getStarterDeptName()),PortalContentDoneTasks::getStarterDeptName,portalContentDoneTasksDto.getStarterDeptName());
        queryWrapper.eq(null != portalContentDoneTasksDto.getStarterOrgId(),PortalContentDoneTasks::getStarterOrgId,portalContentDoneTasksDto.getStarterOrgId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getStarterOrgName()),PortalContentDoneTasks::getStarterOrgName,portalContentDoneTasksDto.getStarterOrgName());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentDoneTasksDto.getSApplyTime())&&ObjectUtil.isNotNull(portalContentDoneTasksDto.getEApplyTime()), PortalContentDoneTasks::getApplyTime, portalContentDoneTasksDto.getSApplyTime(),portalContentDoneTasksDto.getEApplyTime());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getProcinsName()),PortalContentDoneTasks::getProcinsName,portalContentDoneTasksDto.getProcinsName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getCurrentNodeName()),PortalContentDoneTasks::getCurrentNodeName,portalContentDoneTasksDto.getCurrentNodeName());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentDoneTasksDto.getBizKey()),PortalContentDoneTasks::getBizKey,portalContentDoneTasksDto.getBizKey());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentDoneTasksDto.getSCliamTime())&&ObjectUtil.isNotNull(portalContentDoneTasksDto.getECliamTime()), PortalContentDoneTasks::getCliamTime, portalContentDoneTasksDto.getSCliamTime(),portalContentDoneTasksDto.getECliamTime());
        queryWrapper.eq(null != portalContentDoneTasksDto.getTaskState() && 0 != portalContentDoneTasksDto.getTaskState(),PortalContentDoneTasks::getTaskState,portalContentDoneTasksDto.getTaskState());

        queryWrapper.eq(StringUtils.isNotBlank(portalContentDoneTasksDto.getSysBizCode()),PortalContentDoneTasks::getSysBizCode,portalContentDoneTasksDto.getSysBizCode());
        queryWrapper.eq(null != portalContentDoneTasksDto.getOwnnerIdCard(),PortalContentDoneTasks::getOwnnerIdCard,portalContentDoneTasksDto.getOwnnerIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getOwnnerName()),PortalContentDoneTasks::getOwnnerName,portalContentDoneTasksDto.getOwnnerName());
        queryWrapper.eq(null != portalContentDoneTasksDto.getAssigneeIdCard(),PortalContentDoneTasks::getAssigneeIdCard,portalContentDoneTasksDto.getAssigneeIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getAssigneeName()),PortalContentDoneTasks::getAssigneeName,portalContentDoneTasksDto.getAssigneeName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentDoneTasksDto.getSysBizClassify()), PortalContentDoneTasks::getSysBizClassify,portalContentDoneTasksDto.getSysBizClassify());
        queryWrapper.eq(PortalContentDoneTasks::getAssigneeIdCard,idCard);
        queryWrapper.orderByDesc(PortalContentDoneTasks::getHandleStartTime);
        List<PortalContentDoneTasks> list = portalContentDoneTasksMapper.selectList(queryWrapper);
        this.setUrlPrefix(list);
        List<PortalContentDoneTasksVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, PortalContentDoneTasksVo.class);
        }
        return listVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentDoneTasksVo get(Long id) {
        PortalContentDoneTasks portalContentDoneTasks = super.getById(id);
        PortalContentDoneTasksVo portalContentDoneTasksVo = null;
        if(portalContentDoneTasks !=null){
            portalContentDoneTasksVo = dozerMapper.map(portalContentDoneTasks,PortalContentDoneTasksVo.class);
        }
        log.debug("查询成功");
        return portalContentDoneTasksVo;
    }

    /**
    * 保存实体
    * @param portalContentDoneTasksDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentDoneTasksDto portalContentDoneTasksDto) {
        PortalContentDoneTasks portalContentDoneTasks = dozerMapper.map(portalContentDoneTasksDto,PortalContentDoneTasks.class);
        boolean result = super.save(portalContentDoneTasks);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean saveList(List<PortalContentDoneTasksDto> portalContentDoneTasksDtos) {
        log.debug("推送已办数据：=============================start");
        this.checkportalContentDoneTasksDtos(portalContentDoneTasksDtos);
        List<PortalContentDoneTasks> addResultList = new ArrayList<>();
        List<PortalContentDoneTasks> upResultList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(portalContentDoneTasksDtos)) {
            for (PortalContentDoneTasksDto portalContentDoneTasksDto : portalContentDoneTasksDtos) {
                //先删除一下待办
                ApiTaskDto delApiTaskDto = new ApiTaskDto();
                delApiTaskDto.setSysBizId(portalContentDoneTasksDto.getSysBizId());
                delApiTaskDto.setSysBizCode(portalContentDoneTasksDto.getSysBizCode());
                portalContentTodoTasksService.delBySysBizIdAndCode(delApiTaskDto);
                ApiTaskDto apiTaskDto = new ApiTaskDto();
                apiTaskDto.setSysBizId(portalContentDoneTasksDto.getSysBizId());
                apiTaskDto.setSysBizCode(portalContentDoneTasksDto.getSysBizCode());
                PortalContentDoneTasks portalContentDoneTasks = this.getBySysBizIdAndCode(apiTaskDto);
                if(null != portalContentDoneTasks) {
                    portalContentDoneTasksDto.setId(portalContentDoneTasks.getId());//把数据库中的id 赋值给dto
                    PortalContentDoneTasks portalContentTask = dozerMapper.map(portalContentDoneTasksDto,PortalContentDoneTasks.class);
                    portalContentTask.setAssigneeIdCard(portalContentTask.getAssigneeIdCard().trim());
                    upResultList.add(portalContentTask);
                } else {
                    PortalContentDoneTasks _portalContentDoneTasks = dozerMapper.map(portalContentDoneTasksDto,PortalContentDoneTasks.class);
                    _portalContentDoneTasks.setAssigneeIdCard(_portalContentDoneTasks.getAssigneeIdCard().trim());
                    addResultList.add(_portalContentDoneTasks);
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

    public void checkportalContentDoneTasksDtos(List<PortalContentDoneTasksDto> portalContentDoneTasksDtos) {
        if(CollectionUtil.isNotEmpty(portalContentDoneTasksDtos)) {
            for (PortalContentDoneTasksDto portalContentDoneTasksDto : portalContentDoneTasksDtos) {
//                if(StringUtils.isBlank(portalContentDoneTasksDto.getStarterIdCard())) {
//                    throw new IncloudException("起草人id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentDoneTasksDto.getStarterName())) {
//                    throw new IncloudException("起草人名称不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentDoneTasksDto.getStarterDeptId())) {
//                    throw new IncloudException("起草人部门id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentDoneTasksDto.getStarterDeptName())) {
//                    throw new IncloudException("起草人部门名称不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentDoneTasksDto.getStarterOrgId())) {
//                    throw new IncloudException("起草人机构id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentDoneTasksDto.getStarterOrgName())) {
//                    throw new IncloudException("起草人机构名称不能为空。");
//                }
//                if(null == portalContentDoneTasksDto.getApplyTime()) {
//                    throw new IncloudException("申请时间不能为空。");
//                }
                if(StringUtils.isBlank(portalContentDoneTasksDto.getProcinsName())) {
                    throw new IncloudException("流程实例名称/标题不能为空。");
                }
//                if(StringUtils.isBlank(portalContentDoneTasksDto.getCurrentNodeName())) {
//                    throw new IncloudException("当前节点名称不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentDoneTasksDto.getBizKey())) {
//                    throw new IncloudException("业务单据号/流水号不能为空。");
//                }
//                if(null == portalContentDoneTasksDto.getTaskState() || 0L == portalContentDoneTasksDto.getTaskState()) {
//                    throw new IncloudException("任务状态不能为空。");
//                }
                if(StringUtils.isBlank(portalContentDoneTasksDto.getSysPcBizUrl())) {
                    throw new IncloudException("PC业务系统表单详情页面URL不能为空。");
                }
                if(StringUtils.isBlank(portalContentDoneTasksDto.getSysAppBizUrl())) {
                    throw new IncloudException("APP业务系统表单详情页面URL不能为空。");
                }

                if(StringUtils.isBlank(portalContentDoneTasksDto.getSysBizCode())) {
                    throw new IncloudException("业务系统CODE不能为空。");
                }
                if(StringUtils.isBlank(portalContentDoneTasksDto.getSysBizId())) {
                    throw new IncloudException("业务系统ID不能为空。");
                }

                if(StringUtils.isBlank(portalContentDoneTasksDto.getAssigneeIdCard())) {
                    throw new IncloudException("任务办理人ID不能为空。");
                }

                if(StringUtils.isBlank(portalContentDoneTasksDto.getAssigneeName())) {
                    throw new IncloudException("任务办理人名称不能为空。");
                }
//                if(StringUtils.isBlank(portalContentDoneTasksDto.getNextAssigneeId())) {
//                    throw new IncloudException("下一节点任务办理人ID不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentDoneTasksDto.getNextAssigneeName())) {
//                    throw new IncloudException("下一节点任务办理人不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentDoneTasksDto.getNextNodeName())) {
//                    throw new IncloudException("下一节点名称不能为空。");
//                }
                if(StringUtils.isBlank(portalContentDoneTasksDto.getSysBizClassify())) {
                    throw new IncloudException("数据的系统业务类型不能为空。");
                }
            }
        }
    }


    /**
    * 修改实体
    * @param portalContentDoneTasksDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentDoneTasksDto portalContentDoneTasksDto) {
        portalContentDoneTasksDto.setUpdateTime(LocalDateTime.now());
        PortalContentDoneTasks portalContentDoneTasks = dozerMapper.map(portalContentDoneTasksDto,PortalContentDoneTasks.class);
        boolean result = super.updateById(portalContentDoneTasks);
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
        log.debug("推送删除已办数据：=============================start。参数sysBizId：" + apiTaskDto.getSysBizId() + ",sysBizCode:" + apiTaskDto.getSysBizCode());
        LambdaQueryWrapper<PortalContentDoneTasks> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(PortalContentDoneTasks::getSysBizId,apiTaskDto.getSysBizId());
        delWrapper.eq(PortalContentDoneTasks::getSysBizCode,apiTaskDto.getSysBizCode());
        int line = portalContentDoneTasksMapper.delete(delWrapper);
        if(line > 0) {
            log.debug("推送删除已办数据：=============================end。返回：" + true);
            return true;
        } else {
            log.debug("推送删除已办数据：=============================end。返回：" + false);
            return false;
        }
    }

    @Override
    public PortalContentDoneTasks getBySysBizIdAndCode(ApiTaskDto apiTaskDto) {
        if(StringUtils.isBlank(apiTaskDto.getSysBizId())) {
            throw new IncloudException("sysBizId 不能为空！");
        }

        if(StringUtils.isBlank(apiTaskDto.getSysBizCode())) {
            throw new IncloudException("sysBizCode 不能为空！");
        }
        LambdaQueryWrapper<PortalContentDoneTasks> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(PortalContentDoneTasks::getSysBizId,apiTaskDto.getSysBizId());
        delWrapper.eq(PortalContentDoneTasks::getSysBizCode,apiTaskDto.getSysBizCode());
        PortalContentDoneTasks portalContentDoneTasks = portalContentDoneTasksMapper.selectOne(delWrapper);
        return portalContentDoneTasks;
    }
}
