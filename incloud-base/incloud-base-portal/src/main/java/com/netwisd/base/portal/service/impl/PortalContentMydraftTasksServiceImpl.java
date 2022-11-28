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
import com.netwisd.base.portal.dto.PortalContentDoneTasksDto;
import com.netwisd.base.portal.dto.PortalContentTodoTasksDto;
import com.netwisd.base.portal.entity.PortalContentDoneTasks;
import com.netwisd.base.portal.entity.PortalContentMydraftTasks;
import com.netwisd.base.portal.entity.PortalContentTodoTasks;
import com.netwisd.base.portal.mapper.PortalContentMydraftTasksMapper;
import com.netwisd.base.portal.service.PortalContentMydraftTasksService;
import com.netwisd.base.portal.vo.PortalContentDoneTasksVo;
import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.portal.dto.PortalContentMydraftTasksDto;
import com.netwisd.base.portal.vo.PortalContentMydraftTasksVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 任务集成类-我发起的任务 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-28 15:27:43
 */
@Service
@Slf4j
public class PortalContentMydraftTasksServiceImpl extends ServiceImpl<PortalContentMydraftTasksMapper, PortalContentMydraftTasks> implements PortalContentMydraftTasksService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private PortalContentMydraftTasksMapper portalContentMydraftTasksMapper;

    @Value("${portal.gepsTaskUrl}")
    private String gepsTaskUrl;

    @Value("${portal.oaTaskUrl}")
    private String oaTaskUrl;

    /**
    * 单表简单查询操作
    * @param portalContentMydraftTasksDto
    * @return
    */
    @Override
    public Page list(PortalContentMydraftTasksDto portalContentMydraftTasksDto) {
        //获取当前登录人
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String idCard = loginAppUser.getIdCard();
        LambdaQueryWrapper<PortalContentMydraftTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != portalContentMydraftTasksDto.getStarterIdCard(),PortalContentMydraftTasks::getStarterIdCard,portalContentMydraftTasksDto.getStarterIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentMydraftTasksDto.getStarterName()),PortalContentMydraftTasks::getStarterName,portalContentMydraftTasksDto.getStarterName());
        queryWrapper.eq(null != portalContentMydraftTasksDto.getStarterDeptId(),PortalContentMydraftTasks::getStarterDeptId,portalContentMydraftTasksDto.getStarterDeptId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentMydraftTasksDto.getStarterDeptName()),PortalContentMydraftTasks::getStarterDeptName,portalContentMydraftTasksDto.getStarterDeptName());
        queryWrapper.eq(null != portalContentMydraftTasksDto.getStarterOrgId(),PortalContentMydraftTasks::getStarterOrgId,portalContentMydraftTasksDto.getStarterOrgId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentMydraftTasksDto.getStarterOrgName()),PortalContentMydraftTasks::getStarterOrgName,portalContentMydraftTasksDto.getStarterOrgName());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentMydraftTasksDto.getSApplyTime())&&ObjectUtil.isNotNull(portalContentMydraftTasksDto.getEApplyTime()), PortalContentMydraftTasks::getApplyTime, portalContentMydraftTasksDto.getSApplyTime(),portalContentMydraftTasksDto.getEApplyTime());
        queryWrapper.like(StringUtils.isNotBlank(portalContentMydraftTasksDto.getProcinsName()),PortalContentMydraftTasks::getProcinsName,portalContentMydraftTasksDto.getProcinsName());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentMydraftTasksDto.getBizKey()),PortalContentMydraftTasks::getBizKey,portalContentMydraftTasksDto.getBizKey());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentMydraftTasksDto.getSysBizCode()),PortalContentMydraftTasks::getSysBizCode,portalContentMydraftTasksDto.getSysBizCode());
        queryWrapper.like(StringUtils.isNotBlank(portalContentMydraftTasksDto.getSysBizClassify()), PortalContentMydraftTasks::getSysBizClassify,portalContentMydraftTasksDto.getSysBizClassify());
        queryWrapper.eq(PortalContentMydraftTasks::getStarterIdCard,idCard);
        queryWrapper.orderByDesc(PortalContentMydraftTasks::getCreateTime);
        Page<PortalContentMydraftTasks> page = portalContentMydraftTasksMapper.selectPage(portalContentMydraftTasksDto.getPage(),queryWrapper);
        List<PortalContentMydraftTasks> list = page.getRecords();
        this.setUrlPrefix(list);
        Page<PortalContentMydraftTasksVo> pageVo = DozerUtils.mapPage(dozerMapper, page, PortalContentMydraftTasksVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 动态设置url 跳转前缀
     * @param list
     */
    public void setUrlPrefix(List<PortalContentMydraftTasks> list) {
        if(CollectionUtil.isNotEmpty(list)) {
            for (PortalContentMydraftTasks portalContentMydraftTasks : list) {
                if(OtherSysEnum.GEPS.code.equals(portalContentMydraftTasks.getSysBizCode())) {
                    portalContentMydraftTasks.setSysPcBizUrl(gepsTaskUrl + portalContentMydraftTasks.getSysPcBizUrl());
                    portalContentMydraftTasks.setSysAppBizUrl(gepsTaskUrl + portalContentMydraftTasks.getSysAppBizUrl());
                }
                if(OtherSysEnum.OA.code.equals(portalContentMydraftTasks.getSysBizCode())) {
                    portalContentMydraftTasks.setSysPcBizUrl(oaTaskUrl + portalContentMydraftTasks.getSysPcBizUrl());
                    portalContentMydraftTasks.setSysAppBizUrl(oaTaskUrl + portalContentMydraftTasks.getSysAppBizUrl());
                }
            }
        }
    }

    /**
    * 自定义查询操作
    * @param portalContentMydraftTasksDto
    * @return
    */
    @Override
    public List<PortalContentMydraftTasksVo> lists(PortalContentMydraftTasksDto portalContentMydraftTasksDto) {
        //获取当前登录人
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        String idCard = loginAppUser.getIdCard();
        LambdaQueryWrapper<PortalContentMydraftTasks> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != portalContentMydraftTasksDto.getStarterIdCard(),PortalContentMydraftTasks::getStarterIdCard,portalContentMydraftTasksDto.getStarterIdCard());
        queryWrapper.like(StringUtils.isNotBlank(portalContentMydraftTasksDto.getStarterName()),PortalContentMydraftTasks::getStarterName,portalContentMydraftTasksDto.getStarterName());
        queryWrapper.eq(null != portalContentMydraftTasksDto.getStarterDeptId(),PortalContentMydraftTasks::getStarterDeptId,portalContentMydraftTasksDto.getStarterDeptId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentMydraftTasksDto.getStarterDeptName()),PortalContentMydraftTasks::getStarterDeptName,portalContentMydraftTasksDto.getStarterDeptName());
        queryWrapper.eq(null != portalContentMydraftTasksDto.getStarterOrgId(),PortalContentMydraftTasks::getStarterOrgId,portalContentMydraftTasksDto.getStarterOrgId());
        queryWrapper.like(StringUtils.isNotBlank(portalContentMydraftTasksDto.getStarterOrgName()),PortalContentMydraftTasks::getStarterOrgName,portalContentMydraftTasksDto.getStarterOrgName());
        queryWrapper.between(ObjectUtil.isNotNull(portalContentMydraftTasksDto.getSApplyTime())&&ObjectUtil.isNotNull(portalContentMydraftTasksDto.getEApplyTime()), PortalContentMydraftTasks::getApplyTime, portalContentMydraftTasksDto.getSApplyTime(),portalContentMydraftTasksDto.getEApplyTime());
        queryWrapper.like(StringUtils.isNotBlank(portalContentMydraftTasksDto.getProcinsName()),PortalContentMydraftTasks::getProcinsName,portalContentMydraftTasksDto.getProcinsName());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentMydraftTasksDto.getBizKey()),PortalContentMydraftTasks::getBizKey,portalContentMydraftTasksDto.getBizKey());
        queryWrapper.eq(StringUtils.isNotBlank(portalContentMydraftTasksDto.getSysBizCode()),PortalContentMydraftTasks::getSysBizCode,portalContentMydraftTasksDto.getSysBizCode());
        queryWrapper.like(StringUtils.isNotBlank(portalContentMydraftTasksDto.getSysBizClassify()), PortalContentMydraftTasks::getSysBizClassify,portalContentMydraftTasksDto.getSysBizClassify());
        queryWrapper.eq(PortalContentMydraftTasks::getStarterIdCard,idCard);
        queryWrapper.orderByDesc(PortalContentMydraftTasks::getApplyTime);
        List<PortalContentMydraftTasks> list = portalContentMydraftTasksMapper.selectList(queryWrapper);
        this.setUrlPrefix(list);
        List<PortalContentMydraftTasksVo> voList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            voList = DozerUtils.mapList(dozerMapper, list, PortalContentMydraftTasksVo.class);
        }
        return voList;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public PortalContentMydraftTasksVo get(Long id) {
        PortalContentMydraftTasks portalContentMydraftTasks = super.getById(id);
        PortalContentMydraftTasksVo portalContentMydraftTasksVo = null;
        if(portalContentMydraftTasks !=null){
            portalContentMydraftTasksVo = dozerMapper.map(portalContentMydraftTasks,PortalContentMydraftTasksVo.class);
        }
        log.debug("查询成功");
        return portalContentMydraftTasksVo;
    }

    /**
    * 保存实体
    * @param portalContentMydraftTasksDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(PortalContentMydraftTasksDto portalContentMydraftTasksDto) {
        PortalContentMydraftTasks portalContentMydraftTasks = dozerMapper.map(portalContentMydraftTasksDto,PortalContentMydraftTasks.class);
        boolean result = super.save(portalContentMydraftTasks);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean saveList(List<PortalContentMydraftTasksDto> portalContentMydraftTasksDtos) {
        log.debug("推送我发起的任务数据：=============================start");
        checkPortalContentMydraftTasksDtos(portalContentMydraftTasksDtos);
        List<PortalContentMydraftTasks> addResultList = new ArrayList<>();
        List<PortalContentMydraftTasks> upResultList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(portalContentMydraftTasksDtos)) {
            for (PortalContentMydraftTasksDto portalContentMydraftTasksDto : portalContentMydraftTasksDtos) {
                ApiTaskDto apiTaskDto = new ApiTaskDto();
                apiTaskDto.setSysBizId(portalContentMydraftTasksDto.getSysBizId());
                apiTaskDto.setSysBizCode(portalContentMydraftTasksDto.getSysBizCode());
                PortalContentMydraftTasks portalContentMydraftTasks = this.getBySysBizIdAndCode(apiTaskDto);
                if(null != portalContentMydraftTasks) {
                    portalContentMydraftTasksDto.setId(portalContentMydraftTasks.getId());//把数据库中的id 赋值给dto
                    PortalContentMydraftTasks _portalContentMydraftTasks = dozerMapper.map(portalContentMydraftTasksDto,PortalContentMydraftTasks.class);
                    upResultList.add(_portalContentMydraftTasks);
                } else {
                    PortalContentMydraftTasks _portalContentMydraftTasks = dozerMapper.map(portalContentMydraftTasksDto,PortalContentMydraftTasks.class);
                    addResultList.add(_portalContentMydraftTasks);
                }
            }
        }
        if(CollectionUtil.isNotEmpty(addResultList)) {
            this.saveBatch(addResultList);
        }
        if(CollectionUtil.isNotEmpty(upResultList)) {
            this.updateBatchById(upResultList);
        }
        log.debug("推送我发起的任务数据：=============================end");
        return true;
    }

    public void checkPortalContentMydraftTasksDtos(List<PortalContentMydraftTasksDto> portalContentMydraftTasksDtos) {
        if(CollectionUtil.isNotEmpty(portalContentMydraftTasksDtos)) {
            for (PortalContentMydraftTasksDto portalContentMydraftTasksDto : portalContentMydraftTasksDtos) {
                if(StringUtils.isBlank(portalContentMydraftTasksDto.getStarterIdCard())) {
                    throw new IncloudException("起草人id不能为空。");
                }
                if(StringUtils.isBlank(portalContentMydraftTasksDto.getStarterName())) {
                    throw new IncloudException("起草人名称不能为空。");
                }
//                if(null == portalContentMydraftTasksDto.getStarterDeptId() || 0L == portalContentMydraftTasksDto.getStarterDeptId()) {
//                    throw new IncloudException("起草人部门id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentMydraftTasksDto.getStarterDeptName())) {
//                    throw new IncloudException("起草人部门名称不能为空。");
//                }
//                if(null == portalContentMydraftTasksDto.getStarterOrgId() || 0L == portalContentMydraftTasksDto.getStarterOrgId()) {
//                    throw new IncloudException("起草人机构id不能为空。");
//                }
//                if(StringUtils.isBlank(portalContentMydraftTasksDto.getStarterOrgName())) {
//                    throw new IncloudException("起草人机构名称不能为空。");
//                }
//                if(null == portalContentMydraftTasksDto.getApplyTime()) {
//                    throw new IncloudException("申请时间不能为空。");
//                }
                if(StringUtils.isBlank(portalContentMydraftTasksDto.getProcinsName())) {
                    throw new IncloudException("流程实例名称/标题不能为空。");
                }
//                if(StringUtils.isBlank(portalContentMydraftTasksDto.getBizKey())) {
//                    throw new IncloudException("业务单据号/流水号不能为空。");
//                }
                if(StringUtils.isBlank(portalContentMydraftTasksDto.getSysPcBizUrl())) {
                    throw new IncloudException("pc业务系统表单详情页面URL不能为空。");
                }
                if(StringUtils.isBlank(portalContentMydraftTasksDto.getSysAppBizUrl())) {
                    throw new IncloudException("APP业务系统表单详情页面URL不能为空。");
                }

                if(StringUtils.isBlank(portalContentMydraftTasksDto.getSysBizCode())) {
                    throw new IncloudException("业务系统CODE不能为空。");
                }
                if(StringUtils.isBlank(portalContentMydraftTasksDto.getSysBizId())) {
                    throw new IncloudException("业务系统ID不能为空。");
                }
                if(StringUtils.isBlank(portalContentMydraftTasksDto.getSysBizClassify())) {
                    throw new IncloudException("数据的系统业务类型不能为空。");
                }
            }
        }
    }

    /**
    * 修改实体
    * @param portalContentMydraftTasksDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(PortalContentMydraftTasksDto portalContentMydraftTasksDto) {
        portalContentMydraftTasksDto.setUpdateTime(LocalDateTime.now());
        PortalContentMydraftTasks portalContentMydraftTasks = dozerMapper.map(portalContentMydraftTasksDto,PortalContentMydraftTasks.class);
        boolean result = super.updateById(portalContentMydraftTasks);
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
        log.debug("推送集中待办【我发起的】数据：=============================start。参数sysBizId：" + apiTaskDto.getSysBizCode() + ",sysBizCode:" + apiTaskDto.getSysBizCode());
        LambdaQueryWrapper<PortalContentMydraftTasks> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(PortalContentMydraftTasks::getSysBizId,apiTaskDto.getSysBizId());
        delWrapper.eq(PortalContentMydraftTasks::getSysBizCode,apiTaskDto.getSysBizCode());
        int line = portalContentMydraftTasksMapper.delete(delWrapper);
        if(line > 0) {
            log.debug("推送删除我发起的任务数据：=============================end。返回：" + true);
            return true;
        } else {
            log.debug("推送删除我发起的任务数据：=============================end。返回：" + false);
            return false;
        }
    }

    @Override
    public PortalContentMydraftTasks getBySysBizIdAndCode(ApiTaskDto apiTaskDto) {
        if(StringUtils.isBlank(apiTaskDto.getSysBizId())) {
            throw new IncloudException("sysBizId 不能为空！");
        }

        if(StringUtils.isBlank(apiTaskDto.getSysBizCode())) {
            throw new IncloudException("sysBizCode 不能为空！");
        }
        LambdaQueryWrapper<PortalContentMydraftTasks> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(PortalContentMydraftTasks::getSysBizId,apiTaskDto.getSysBizId());
        delWrapper.eq(PortalContentMydraftTasks::getSysBizCode,apiTaskDto.getSysBizCode());
        PortalContentMydraftTasks portalContentMydraftTasks = portalContentMydraftTasksMapper.selectOne(delWrapper);
        return portalContentMydraftTasks;
    }
}
