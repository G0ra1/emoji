package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.vo.wf.WfMyInDuplicateTaskVo;
import com.netwisd.base.common.vo.wf.WfMyOutDuplicateTaskVo;
import com.netwisd.base.wf.entity.WfMyInDuplicateTask;
import com.netwisd.base.wf.entity.WfMyOutDuplicateTask;
import com.netwisd.base.wf.entity.WfProcDef;
import com.netwisd.base.wf.mapper.WfMyOutDuplicateTaskMapper;
import com.netwisd.base.wf.service.runtime.WfMyOutDuplicateTaskService;
import com.netwisd.common.core.exception.IncloudException;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.wf.dto.WfMyOutDuplicateTaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
/**
 * @Description 我发起的传阅 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-09-18 16:16:11
 */
@Service
@Slf4j
public class WfMyOutDuplicateTaskServiceImpl extends ServiceImpl<WfMyOutDuplicateTaskMapper, WfMyOutDuplicateTask> implements WfMyOutDuplicateTaskService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfMyOutDuplicateTaskMapper wfMyOutDuplicateTaskMapper;

    /**
    * 单表简单查询操作
    * @param wfMyOutDuplicateTaskDto
    * @return
    */
    @Override
    public Page list(WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto) {
        log.debug("查询我发起的传阅。参数:"+wfMyOutDuplicateTaskDto.toString());
        LambdaQueryWrapper<WfMyOutDuplicateTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getBizKey()), WfMyOutDuplicateTask::getBizKey,wfMyOutDuplicateTaskDto.getBizKey())
                .eq(WfMyOutDuplicateTask::getAssignee,getUserID())
                .eq(StrUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getCurrentUserId()), WfMyOutDuplicateTask::getAssignee,wfMyOutDuplicateTaskDto.getCurrentUserId())
                .eq(StrUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getStarterName()),WfMyOutDuplicateTask::getStarterName,wfMyOutDuplicateTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getProcdefTypeId()),WfMyOutDuplicateTask::getProcdefTypeId,wfMyOutDuplicateTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getApplyEndTime())
                        ,WfMyOutDuplicateTask::getApplyTime,wfMyOutDuplicateTaskDto.getApplyStartTime(),wfMyOutDuplicateTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getStarterOrgId()),WfMyOutDuplicateTask::getStarterOrgId,wfMyOutDuplicateTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getStarterDeptId()),WfMyOutDuplicateTask::getStarterDeptId,wfMyOutDuplicateTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getCamundaProcdefKey()),WfMyOutDuplicateTask::getCamundaProcdefKey,wfMyOutDuplicateTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getCamundaProcdefVersion()),WfMyOutDuplicateTask::getCamundaProcdefVersion,wfMyOutDuplicateTaskDto.getCamundaProcdefVersion());
        queryWrapper.orderByDesc(WfMyOutDuplicateTask::getCreateTime);
        Page<WfMyInDuplicateTask> page = wfMyOutDuplicateTaskMapper.selectPage(wfMyOutDuplicateTaskDto.getPage(),queryWrapper);
        Page<WfMyInDuplicateTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfMyInDuplicateTaskVo.class);
        log.debug("查询我发起的传阅。查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page outDuplicateListForAd(WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto) {
        log.debug("查询我发起的传阅。参数:"+wfMyOutDuplicateTaskDto.toString());
        LambdaQueryWrapper<WfMyOutDuplicateTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getBizKey()), WfMyOutDuplicateTask::getBizKey,wfMyOutDuplicateTaskDto.getBizKey())
                .eq(StrUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getAssignee()),WfMyOutDuplicateTask::getAssignee,wfMyOutDuplicateTaskDto.getAssignee())
                .like(StrUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getAssigneeName()), WfMyOutDuplicateTask::getAssigneeName,wfMyOutDuplicateTaskDto.getAssigneeName())
                .eq(StrUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getCurrentUserId()), WfMyOutDuplicateTask::getAssignee,wfMyOutDuplicateTaskDto.getCurrentUserId())
                .eq(StrUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getStarterName()),WfMyOutDuplicateTask::getStarterName,wfMyOutDuplicateTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getProcdefTypeId()),WfMyOutDuplicateTask::getProcdefTypeId,wfMyOutDuplicateTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getApplyEndTime())
                        ,WfMyOutDuplicateTask::getApplyTime,wfMyOutDuplicateTaskDto.getApplyStartTime(),wfMyOutDuplicateTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getStarterOrgId()),WfMyOutDuplicateTask::getStarterOrgId,wfMyOutDuplicateTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getStarterDeptId()),WfMyOutDuplicateTask::getStarterDeptId,wfMyOutDuplicateTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getCamundaProcdefKey()),WfMyOutDuplicateTask::getCamundaProcdefKey,wfMyOutDuplicateTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfMyOutDuplicateTaskDto.getCamundaProcdefVersion()),WfMyOutDuplicateTask::getCamundaProcdefVersion,wfMyOutDuplicateTaskDto.getCamundaProcdefVersion());
        queryWrapper.orderByDesc(WfMyOutDuplicateTask::getCreateTime);
        Page<WfMyInDuplicateTask> page = wfMyOutDuplicateTaskMapper.selectPage(wfMyOutDuplicateTaskDto.getPage(),queryWrapper);
        Page<WfMyInDuplicateTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfMyInDuplicateTaskVo.class);
        log.debug("查询我发起的传阅。查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfMyOutDuplicateTaskDto
    * @return
    */
    @Override
    public Page lists(WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto) {
        Page<WfMyOutDuplicateTaskVo> page = wfMyOutDuplicateTaskMapper.getPageList(wfMyOutDuplicateTaskDto.getPage(),wfMyOutDuplicateTaskDto);
        Page<WfMyOutDuplicateTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfMyOutDuplicateTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfMyOutDuplicateTaskVo get(Long id) {
        WfMyOutDuplicateTask wfMyOutDuplicateTask = super.getById(id);
        WfMyOutDuplicateTaskVo wfMyOutDuplicateTaskVo = null;
        if(wfMyOutDuplicateTask !=null){
            wfMyOutDuplicateTaskVo = dozerMapper.map(wfMyOutDuplicateTask,WfMyOutDuplicateTaskVo.class);
        }
        log.debug("查询成功");
        return wfMyOutDuplicateTaskVo;
    }

    /**
    * 保存实体
    * @param wfMyOutDuplicateTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto) {
        WfMyOutDuplicateTask wfMyOutDuplicateTask = dozerMapper.map(wfMyOutDuplicateTaskDto,WfMyOutDuplicateTask.class);
        boolean result = super.save(wfMyOutDuplicateTask);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfMyOutDuplicateTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfMyOutDuplicateTaskDto wfMyOutDuplicateTaskDto) {
        WfMyOutDuplicateTask wfMyOutDuplicateTask = dozerMapper.map(wfMyOutDuplicateTaskDto,WfMyOutDuplicateTask.class);
        boolean result = super.updateById(wfMyOutDuplicateTask);
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

    @Override
    public Boolean updateStateByProInsId(String processInstanceId, Integer state) {
        LambdaUpdateWrapper<WfMyOutDuplicateTask> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(WfMyOutDuplicateTask::getCamundaProcinsId,processInstanceId).
                set(WfMyOutDuplicateTask::getState,state);
        boolean update = update(queryWrapper);
        return update;
    }
    @Override
    @Transactional
    public Boolean deleteProcinsId(String processInstanceId) {
        LambdaQueryWrapper<WfMyOutDuplicateTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfMyOutDuplicateTask::getCamundaProcinsId,processInstanceId);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    @Transactional
    public Boolean updateDefId(ProcessDefinition processDefinitionNew, WfProcDef wfProcDefNew, String processInstanceId) {
        LambdaUpdateWrapper<WfMyOutDuplicateTask> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(WfMyOutDuplicateTask::getCamundaProcinsId,processInstanceId)
                .set(WfMyOutDuplicateTask::getCamundaProcdefId,processDefinitionNew.getId())
                .set(WfMyOutDuplicateTask::getCamundaProcdefVersion,processDefinitionNew.getVersion())
                .set(WfMyOutDuplicateTask::getProcdefId,wfProcDefNew.getId());
        boolean update = update(queryWrapper);
        return update;
    }
}
