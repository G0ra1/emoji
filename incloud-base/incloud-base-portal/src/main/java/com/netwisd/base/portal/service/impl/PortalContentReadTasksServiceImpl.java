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
import com.netwisd.base.portal.dto.PortalContentUnreadTasksDto;
import com.netwisd.base.portal.entity.PortalContentReadTasks;
import com.netwisd.base.portal.entity.PortalContentTodoTasks;
import com.netwisd.base.portal.entity.PortalContentUnreadTasks;
import com.netwisd.base.portal.mapper.PortalContentReadTasksMapper;
import com.netwisd.base.portal.service.PortalContentReadTasksService;
import com.netwisd.base.portal.service.PortalContentUnreadTasksService;
import com.netwisd.base.portal.vo.PortalContentUnreadTasksVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentReadTasksDto;
import com.netwisd.base.portal.vo.PortalContentReadTasksVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 任务集成类-已阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 17:54:38
 */
@Service
@Slf4j
public class PortalContentReadTasksServiceImpl extends ServiceImpl<PortalContentReadTasksMapper, PortalContentReadTasks> implements PortalContentReadTasksService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentReadTasksMapper portalContentReadTasksMapper;

    @Autowired
    private PortalContentUnreadTasksService portalContentUnreadTasksService;

    @Value("${portal.gepsTaskUrl}")
    private String gepsTaskUrl;

    @Value("${portal.oaTaskUrl}")
    private String oaTaskUrl;

    /**
    * 单表简单查询操作
    * @param portalContentReadTasksDto
    * @return
    */
    @Override
    public Page list(PortalContentReadTasksDto portalContentReadTasksDto) {
        //获取当前登录人
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String idCard = loginAppUser.getIdCard();
        LambdaQueryWrapper<PortalContentReadTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != portalContentReadTasksDto.getStarterIdCard(), PortalContentReadTasks::getStarterIdCard,portalContentReadTasksDto.getStarterIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getStarterName()),PortalContentReadTasks::getStarterName,portalContentReadTasksDto.getStarterName());
        queryWrapper.eq(null != portalContentReadTasksDto.getStarterDeptId(),PortalContentReadTasks::getStarterDeptId,portalContentReadTasksDto.getStarterDeptId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getStarterDeptName()),PortalContentReadTasks::getStarterDeptName,portalContentReadTasksDto.getStarterDeptName());
        queryWrapper.eq(null != portalContentReadTasksDto.getStarterOrgId(),PortalContentReadTasks::getStarterOrgId,portalContentReadTasksDto.getStarterOrgId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getStarterOrgName()),PortalContentReadTasks::getStarterOrgName,portalContentReadTasksDto.getStarterOrgName());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentReadTasksDto.getSAcceptTime())&&ObjectUtil.isNotNull(portalContentReadTasksDto.getEAcceptTime()), PortalContentReadTasks::getApplyTime, portalContentReadTasksDto.getSAcceptTime(),portalContentReadTasksDto.getEAcceptTime());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getProcinsName()),PortalContentReadTasks::getProcinsName,portalContentReadTasksDto.getProcinsName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getCurrentNodeName()),PortalContentReadTasks::getCurrentNodeName,portalContentReadTasksDto.getCurrentNodeName());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentReadTasksDto.getBizKey()),PortalContentReadTasks::getBizKey,portalContentReadTasksDto.getBizKey());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentReadTasksDto.getSysBizCode()),PortalContentReadTasks::getSysBizCode,portalContentReadTasksDto.getSysBizCode());
        queryWrapper.eq(null != portalContentReadTasksDto.getOwnnerIdCard(),PortalContentReadTasks::getOwnnerIdCard,portalContentReadTasksDto.getOwnnerIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getOwnnerName()),PortalContentReadTasks::getOwnnerName,portalContentReadTasksDto.getOwnnerName());
        queryWrapper.eq(null != portalContentReadTasksDto.getAssigneeIdCard(),PortalContentReadTasks::getAssigneeIdCard,portalContentReadTasksDto.getAssigneeIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getAssigneeName()),PortalContentReadTasks::getAssigneeName,portalContentReadTasksDto.getAssigneeName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getSysBizClassify()),PortalContentReadTasks::getSysBizClassify,portalContentReadTasksDto.getSysBizClassify());
        queryWrapper.eq(PortalContentReadTasks::getAssigneeIdCard,idCard);
        queryWrapper.orderByDesc(PortalContentReadTasks::getCreateTime);
        Page<PortalContentReadTasks> page = portalContentReadTasksMapper.selectPage(portalContentReadTasksDto.getPage(),queryWrapper);
        List<PortalContentReadTasks> list = page.getRecords();
        this.setUrlPrefix(list);
        Page<PortalContentReadTasksVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentReadTasksVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param portalContentReadTasksDto
    * @return
    */
    @Override
    public List<PortalContentReadTasksVo> lists(PortalContentReadTasksDto portalContentReadTasksDto) {
        LambdaQueryWrapper<PortalContentReadTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != portalContentReadTasksDto.getStarterIdCard(), PortalContentReadTasks::getStarterIdCard,portalContentReadTasksDto.getStarterIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getStarterName()),PortalContentReadTasks::getStarterName,portalContentReadTasksDto.getStarterName());
        queryWrapper.eq(null != portalContentReadTasksDto.getStarterDeptId(),PortalContentReadTasks::getStarterDeptId,portalContentReadTasksDto.getStarterDeptId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getStarterDeptName()),PortalContentReadTasks::getStarterDeptName,portalContentReadTasksDto.getStarterDeptName());
        queryWrapper.eq(null != portalContentReadTasksDto.getStarterOrgId(),PortalContentReadTasks::getStarterOrgId,portalContentReadTasksDto.getStarterOrgId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getStarterOrgName()),PortalContentReadTasks::getStarterOrgName,portalContentReadTasksDto.getStarterOrgName());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentReadTasksDto.getSAcceptTime())&&ObjectUtil.isNotNull(portalContentReadTasksDto.getEAcceptTime()), PortalContentReadTasks::getApplyTime, portalContentReadTasksDto.getSAcceptTime(),portalContentReadTasksDto.getEAcceptTime());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getProcinsName()),PortalContentReadTasks::getProcinsName,portalContentReadTasksDto.getProcinsName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getCurrentNodeName()),PortalContentReadTasks::getCurrentNodeName,portalContentReadTasksDto.getCurrentNodeName());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentReadTasksDto.getBizKey()),PortalContentReadTasks::getBizKey,portalContentReadTasksDto.getBizKey());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentReadTasksDto.getSysBizCode()),PortalContentReadTasks::getSysBizCode,portalContentReadTasksDto.getSysBizCode());
        queryWrapper.eq(null != portalContentReadTasksDto.getOwnnerIdCard(),PortalContentReadTasks::getOwnnerIdCard,portalContentReadTasksDto.getOwnnerIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getOwnnerName()),PortalContentReadTasks::getOwnnerName,portalContentReadTasksDto.getOwnnerName());
        queryWrapper.eq(null != portalContentReadTasksDto.getAssigneeIdCard(),PortalContentReadTasks::getAssigneeIdCard,portalContentReadTasksDto.getAssigneeIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getAssigneeName()),PortalContentReadTasks::getAssigneeName,portalContentReadTasksDto.getAssigneeName());
        queryWrapper.like(StringUtils.isNotBlank(portalContentReadTasksDto.getSysBizClassify()),PortalContentReadTasks::getSysBizClassify,portalContentReadTasksDto.getSysBizClassify());
        queryWrapper.orderByDesc(PortalContentReadTasks::getAcceptTime);
        List<PortalContentReadTasks> list = portalContentReadTasksMapper.selectList(queryWrapper);
        this.setUrlPrefix(list);
        List<PortalContentReadTasksVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, PortalContentReadTasksVo.class);
        }
        return listVo;
    }

    /**
     * 动态设置url 跳转前缀
     * @param list
     */
    public void setUrlPrefix(List<PortalContentReadTasks> list) {
        if(CollectionUtil.isNotEmpty(list)) {
            for (PortalContentReadTasks portalContentReadTasks : list) {
                if(OtherSysEnum.GEPS.code.equals(portalContentReadTasks.getSysBizCode())) {
                    portalContentReadTasks.setSysPcBizUrl(gepsTaskUrl + portalContentReadTasks.getSysPcBizUrl());
                    portalContentReadTasks.setSysAppBizUrl(gepsTaskUrl + portalContentReadTasks.getSysAppBizUrl());
                }
                if(OtherSysEnum.OA.code.equals(portalContentReadTasks.getSysBizCode())) {
                    portalContentReadTasks.setSysPcBizUrl(oaTaskUrl + portalContentReadTasks.getSysPcBizUrl());
                    portalContentReadTasks.setSysAppBizUrl(oaTaskUrl + portalContentReadTasks.getSysAppBizUrl());
                }
            }
        }
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentReadTasksVo get(Long id) {
        PortalContentReadTasks portalContentReadTasks = super.getById(id);
        PortalContentReadTasksVo portalContentReadTasksVo = null;
        if(portalContentReadTasks !=null){
            portalContentReadTasksVo = dozerMapper.map(portalContentReadTasks,PortalContentReadTasksVo.class);
        }
        log.debug("查询成功");
        return portalContentReadTasksVo;
    }

    /**
    * 保存实体
    * @param portalContentReadTasksDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentReadTasksDto portalContentReadTasksDto) {
        PortalContentReadTasks portalContentReadTasks = dozerMapper.map(portalContentReadTasksDto,PortalContentReadTasks.class);
        boolean result = super.save(portalContentReadTasks);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean saveList(List<PortalContentReadTasksDto> portalContentReadTasksDtos) {
        log.debug("推送未阅数据：=============================start");
        checkPortalContentReadTasksDtos(portalContentReadTasksDtos);
        List<PortalContentReadTasks> addResultList = new ArrayList<>();
        List<PortalContentReadTasks> upResultList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(portalContentReadTasksDtos)) {
            for (PortalContentReadTasksDto portalContentReadTasksDto : portalContentReadTasksDtos) {
                //进已阅的同时删除一下 待阅
                ApiTaskDto delApiTaskDto = new ApiTaskDto();
                delApiTaskDto.setSysBizId(portalContentReadTasksDto.getSysBizId());
                delApiTaskDto.setSysBizCode(portalContentReadTasksDto.getSysBizCode());
                portalContentUnreadTasksService.delBySysBizIdAndCode(delApiTaskDto);
                ApiTaskDto apiTaskDto = new ApiTaskDto();
                apiTaskDto.setSysBizId(portalContentReadTasksDto.getSysBizId());
                apiTaskDto.setSysBizCode(portalContentReadTasksDto.getSysBizCode());
                PortalContentReadTasks portalContentReadTasks = this.getBySysBizIdAndCode(apiTaskDto);
                if(null != portalContentReadTasks) {
                    portalContentReadTasksDto.setId(portalContentReadTasks.getId());//把数据库中的id 赋值给dto
                    PortalContentReadTasks _portalContentReadTasks = dozerMapper.map(portalContentReadTasksDto,PortalContentReadTasks.class);
                    upResultList.add(_portalContentReadTasks);
                } else {
                    PortalContentReadTasks _portalContentReadTasks = dozerMapper.map(portalContentReadTasksDto,PortalContentReadTasks.class);
                    addResultList.add(_portalContentReadTasks);
                }
            }
        }
        if(CollectionUtil.isNotEmpty(addResultList)) {
            this.saveBatch(addResultList);
        }
        if(CollectionUtil.isNotEmpty(upResultList)) {
            this.updateBatchById(upResultList);
        }
        log.debug("推送未阅数据：=============================end");
        return true;
    }

    public void checkPortalContentReadTasksDtos(List<PortalContentReadTasksDto> portalContentReadTasksDtos) {
        if(CollectionUtil.isNotEmpty(portalContentReadTasksDtos)) {
            for (PortalContentReadTasksDto portalContentReadTasksDto : portalContentReadTasksDtos) {
//                if(StringUtils.isBlank(portalContentReadTasksDto.getStarterIdCard())) {
//                    throw new IncloudException("起草人id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentReadTasksDto.getStarterName())) {
//                    throw new IncloudException("起草人名称不能为空。");
//                }
//                if(null == portalContentUnreadTasksDto.getStarterDeptId() || 0L == portalContentUnreadTasksDto.getStarterDeptId()) {
//                    throw new IncloudException("起草人部门id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentReadTasksDto.getStarterDeptName())) {
//                    throw new IncloudException("起草人部门名称不能为空。");
//                }
//                if(null == portalContentUnreadTasksDto.getStarterOrgId() || 0L == portalContentUnreadTasksDto.getStarterOrgId()) {
//                    throw new IncloudException("起草人机构id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentUnreadTasksDto.getStarterOrgName())) {
//                    throw new IncloudException("起草人机构名称不能为空。");
//                }
//                if(null == portalContentReadTasksDto.getApplyTime()) {
//                    throw new IncloudException("申请时间不能为空。");
//                }
                if(StringUtils.isBlank(portalContentReadTasksDto.getProcinsName())) {
                    throw new IncloudException("流程实例名称/标题不能为空。");
                }
//                if(StringUtils.isBlank(portalContentReadTasksDto.getCurrentNodeName())) {
//                    throw new IncloudException("当前节点名称不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentUnreadTasksDto.getBizKey())) {
//                    throw new IncloudException("业务单据号/流水号不能为空。");
//                }
                if(StringUtils.isBlank(portalContentReadTasksDto.getSysPcBizUrl())) {
                    throw new IncloudException("PC业务系统表单详情页面URL不能为空。");
                }

                if(StringUtils.isBlank(portalContentReadTasksDto.getSysAppBizUrl())) {
                    throw new IncloudException("APP业务系统表单详情页面URL不能为空。");
                }

                if(StringUtils.isBlank(portalContentReadTasksDto.getSysBizCode())) {
                    throw new IncloudException("业务系统CODE不能为空。");
                }
                if(StringUtils.isBlank(portalContentReadTasksDto.getSysBizId())) {
                    throw new IncloudException("业务系统ID不能为空。");
                }

//                if(null == portalContentUnreadTasksDto.getAssigneeId() || 0L == portalContentUnreadTasksDto.getAssigneeId()) {
//                    throw new IncloudException("任务办理人ID不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentUnreadTasksDto.getAssigneeName())) {
//                    throw new IncloudException("任务办理人名称不能为空。");
//                }
                if(StringUtils.isBlank(portalContentReadTasksDto.getSysBizClassify())) {
                    throw new IncloudException("数据的系统业务类型不能为空。");
                }
            }
        }
    }


    /**
    * 修改实体
    * @param portalContentReadTasksDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentReadTasksDto portalContentReadTasksDto) {
        portalContentReadTasksDto.setUpdateTime(LocalDateTime.now());
        PortalContentReadTasks portalContentReadTasks = dozerMapper.map(portalContentReadTasksDto,PortalContentReadTasks.class);
        boolean result = super.updateById(portalContentReadTasks);
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
        log.debug("推送集成待办【已阅】待办数据：=============================start。参数sysBizId：" + apiTaskDto.getSysBizCode() + ",sysBizCode:" + apiTaskDto.getSysBizCode());
        LambdaQueryWrapper<PortalContentReadTasks> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(PortalContentReadTasks::getSysBizId,apiTaskDto.getSysBizId());
        delWrapper.eq(PortalContentReadTasks::getSysBizCode,apiTaskDto.getSysBizCode());
        int line = portalContentReadTasksMapper.delete(delWrapper);
        if(line > 0) {
            log.debug("推送删除已阅数据：=============================end。返回：" + true);
            return true;
        } else {
            log.debug("推送删除已阅数据：=============================end。返回：" + false);
            return false;
        }
    }

    @Override
    public PortalContentReadTasks getBySysBizIdAndCode(ApiTaskDto apiTaskDto) {
        if(StringUtils.isBlank(apiTaskDto.getSysBizId())) {
            throw new IncloudException("sysBizId 不能为空！");
        }
        if(StringUtils.isBlank(apiTaskDto.getSysBizCode())) {
            throw new IncloudException("sysBizCode 不能为空！");
        }
        LambdaQueryWrapper<PortalContentReadTasks> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(PortalContentReadTasks::getSysBizId,apiTaskDto.getSysBizId());
        delWrapper.eq(PortalContentReadTasks::getSysBizCode,apiTaskDto.getSysBizCode());
        PortalContentReadTasks portalContentReadTasks = portalContentReadTasksMapper.selectOne(delWrapper);
        return portalContentReadTasks;
    }
}
