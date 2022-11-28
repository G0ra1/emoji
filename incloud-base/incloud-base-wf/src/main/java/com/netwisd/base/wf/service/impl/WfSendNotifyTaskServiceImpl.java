package com.netwisd.base.wf.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.wf.entity.WfReceiveNotifyTask;
import com.netwisd.base.wf.entity.WfSendNotifyTask;
import com.netwisd.base.wf.mapper.WfSendNotifyTaskMapper;
import com.netwisd.base.wf.service.WfSendNotifyTaskService;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.wf.dto.WfSendNotifyTaskDto;
import com.netwisd.base.wf.vo.WfSendNotifyTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 我发出的知会 功能描述...
 * @author 云数网讯 XHK@netwisd.com
 * @date 2022-03-09 10:06:02
 */
@Service
@Slf4j
public class WfSendNotifyTaskServiceImpl extends ServiceImpl<WfSendNotifyTaskMapper, WfSendNotifyTask> implements WfSendNotifyTaskService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfSendNotifyTaskMapper wfSendNotifyTaskMapper;

    /**
    * 单表简单查询操作
    * @param wfSendNotifyTaskDto
    * @return
    */
    @Override
    public Page list(WfSendNotifyTaskDto wfSendNotifyTaskDto) {
        LambdaQueryWrapper<WfSendNotifyTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfSendNotifyTaskDto.getBizKey()), WfSendNotifyTask::getBizKey, wfSendNotifyTaskDto.getBizKey())
                .eq(WfSendNotifyTask::getOwnner,getUserID())
                .eq(StrUtil.isNotEmpty(wfSendNotifyTaskDto.getAssignee()), WfSendNotifyTask::getAssignee, wfSendNotifyTaskDto.getAssignee())
                .like(StrUtil.isNotEmpty(wfSendNotifyTaskDto.getAssigneeName()), WfSendNotifyTask::getAssigneeName, wfSendNotifyTaskDto.getAssigneeName())
                .eq(StrUtil.isNotEmpty(wfSendNotifyTaskDto.getStarterName()), WfSendNotifyTask::getStarterName, wfSendNotifyTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfSendNotifyTaskDto.getProcdefTypeId()), WfSendNotifyTask::getProcdefTypeId, wfSendNotifyTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfSendNotifyTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfSendNotifyTaskDto.getApplyEndTime())
                        , WfSendNotifyTask::getApplyTime, wfSendNotifyTaskDto.getApplyStartTime(), wfSendNotifyTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfSendNotifyTaskDto.getStarterOrgId()), WfSendNotifyTask::getStarterOrgId, wfSendNotifyTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfSendNotifyTaskDto.getStarterDeptId()), WfSendNotifyTask::getStarterDeptId, wfSendNotifyTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfSendNotifyTaskDto.getCamundaProcdefKey()), WfSendNotifyTask::getCamundaProcdefKey, wfSendNotifyTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfSendNotifyTaskDto.getCamundaProcdefVersion()), WfSendNotifyTask::getCamundaProcdefVersion, wfSendNotifyTaskDto.getCamundaProcdefVersion());
        queryWrapper.orderByDesc(WfSendNotifyTask::getCreateTime);
        Page<WfSendNotifyTask> page = wfSendNotifyTaskMapper.selectPage(wfSendNotifyTaskDto.getPage(),queryWrapper);
        Page<WfSendNotifyTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfSendNotifyTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page sendNotifyListForAd(WfSendNotifyTaskDto wfSendNotifyTaskDto) {
        LambdaQueryWrapper<WfSendNotifyTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfSendNotifyTaskDto.getBizKey()), WfSendNotifyTask::getBizKey, wfSendNotifyTaskDto.getBizKey())
                .eq(StrUtil.isNotEmpty(wfSendNotifyTaskDto.getOwnner()), WfSendNotifyTask::getOwnner, wfSendNotifyTaskDto.getOwnner())
                .like(StrUtil.isNotEmpty(wfSendNotifyTaskDto.getOwnnerName()), WfSendNotifyTask::getOwnnerName, wfSendNotifyTaskDto.getOwnnerName())
                .eq(StrUtil.isNotEmpty(wfSendNotifyTaskDto.getAssignee()), WfSendNotifyTask::getAssignee, wfSendNotifyTaskDto.getAssignee())
                .like(StrUtil.isNotEmpty(wfSendNotifyTaskDto.getAssigneeName()), WfSendNotifyTask::getAssigneeName, wfSendNotifyTaskDto.getAssigneeName())
                .eq(StrUtil.isNotEmpty(wfSendNotifyTaskDto.getStarterName()), WfSendNotifyTask::getStarterName, wfSendNotifyTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfSendNotifyTaskDto.getProcdefTypeId()), WfSendNotifyTask::getProcdefTypeId, wfSendNotifyTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfSendNotifyTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfSendNotifyTaskDto.getApplyEndTime())
                        , WfSendNotifyTask::getApplyTime, wfSendNotifyTaskDto.getApplyStartTime(), wfSendNotifyTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfSendNotifyTaskDto.getStarterOrgId()), WfSendNotifyTask::getStarterOrgId, wfSendNotifyTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfSendNotifyTaskDto.getStarterDeptId()), WfSendNotifyTask::getStarterDeptId, wfSendNotifyTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfSendNotifyTaskDto.getCamundaProcdefKey()), WfSendNotifyTask::getCamundaProcdefKey, wfSendNotifyTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfSendNotifyTaskDto.getCamundaProcdefVersion()), WfSendNotifyTask::getCamundaProcdefVersion, wfSendNotifyTaskDto.getCamundaProcdefVersion());
        queryWrapper.orderByDesc(WfSendNotifyTask::getCreateTime);
        Page<WfSendNotifyTask> page = wfSendNotifyTaskMapper.selectPage(wfSendNotifyTaskDto.getPage(),queryWrapper);
        Page<WfSendNotifyTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfSendNotifyTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 获取用户ID
     * @return
     */
    private String getUserID(){
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(ObjectUtil.isEmpty(loginAppUser)){
            new IncloudException("用户未登录！");
        }
        String userId = loginAppUser.getUserName();
        return userId;
    }

    /**
    * 自定义查询操作
    * @param wfSendNotifyTaskDto
    * @return
    */
    @Override
    public Page lists(WfSendNotifyTaskDto wfSendNotifyTaskDto) {
        Page<WfSendNotifyTaskVo> pageVo = wfSendNotifyTaskMapper.getPageList(wfSendNotifyTaskDto.getPage(),wfSendNotifyTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfSendNotifyTaskVo get(Long id) {
        WfSendNotifyTask wfSendNotifyTask = super.getById(id);
        WfSendNotifyTaskVo wfSendNotifyTaskVo = null;
        if(wfSendNotifyTask !=null){
            wfSendNotifyTaskVo = dozerMapper.map(wfSendNotifyTask,WfSendNotifyTaskVo.class);
        }
        log.debug("查询成功");
        return wfSendNotifyTaskVo;
    }

    /**
    * 保存实体
    * @param wfSendNotifyTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfSendNotifyTaskDto wfSendNotifyTaskDto) {
        WfSendNotifyTask wfSendNotifyTask = dozerMapper.map(wfSendNotifyTaskDto,WfSendNotifyTask.class);
        boolean result = super.save(wfSendNotifyTask);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfSendNotifyTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfSendNotifyTaskDto wfSendNotifyTaskDto) {
        wfSendNotifyTaskDto.setUpdateTime(LocalDateTime.now());
        WfSendNotifyTask wfSendNotifyTask = dozerMapper.map(wfSendNotifyTaskDto,WfSendNotifyTask.class);
        boolean result = super.updateById(wfSendNotifyTask);
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
}
