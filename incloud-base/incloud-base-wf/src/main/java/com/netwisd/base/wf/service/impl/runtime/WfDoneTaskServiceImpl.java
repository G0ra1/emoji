package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.wf.entity.WfDoneTask;
import com.netwisd.base.wf.entity.WfProcDef;
import com.netwisd.base.wf.mapper.WfDoneTaskMapper;
import com.netwisd.base.wf.service.runtime.WfDoneTaskService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfDoneTaskDto;
import com.netwisd.base.common.vo.wf.WfDoneTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description 已办任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-13 11:20:23
 */
@Service
@Slf4j
public class WfDoneTaskServiceImpl extends ServiceImpl<WfDoneTaskMapper, WfDoneTask> implements WfDoneTaskService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfDoneTaskMapper wfDoneTaskMapper;

    /**
    * 单表简单查询操作
    * @param wfDoneTaskDto
    * @return
    */
    @Override
    public Page list(WfDoneTaskDto wfDoneTaskDto) {
        LambdaQueryWrapper<WfDoneTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfDoneTaskDto.getBizKey()), WfDoneTask::getBizKey,wfDoneTaskDto.getBizKey())
                .eq(WfDoneTask::getAssignee,getUserID())
                .eq(StrUtil.isNotEmpty(wfDoneTaskDto.getCurrentUserId()),WfDoneTask::getAssignee,wfDoneTaskDto.getCurrentUserId())
                .eq(StrUtil.isNotEmpty(wfDoneTaskDto.getStarterName()),WfDoneTask::getStarterName,wfDoneTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getProcdefTypeId()),WfDoneTask::getProcdefTypeId,wfDoneTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfDoneTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfDoneTaskDto.getApplyEndTime())
                        ,WfDoneTask::getApplyTime,wfDoneTaskDto.getApplyStartTime(),wfDoneTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterOrgId()),WfDoneTask::getStarterOrgId,wfDoneTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterDeptId()),WfDoneTask::getStarterDeptId,wfDoneTaskDto.getStarterDeptId())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterId()), WfDoneTask::getStarterId,wfDoneTaskDto.getStarterId())
                .like(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterName()), WfDoneTask::getStarterName,wfDoneTaskDto.getStarterName())
                .like(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterDeptName()), WfDoneTask::getStarterDeptName,wfDoneTaskDto.getStarterDeptName())
                .like(StrUtil.isNotEmpty(wfDoneTaskDto.getCamundaProcdefKey()),WfDoneTask::getCamundaProcdefKey,wfDoneTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getCamundaProcdefVersion()),WfDoneTask::getCamundaProcdefVersion,wfDoneTaskDto.getCamundaProcdefVersion())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getState()), WfDoneTask::getState,wfDoneTaskDto.getState())
                /*.eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getFormKey()),WfDoneTask::getFormKey,wfDoneTaskDto.getFormKey())*/
                .like(ObjectUtil.isNotEmpty(wfDoneTaskDto.getReason()), WfDoneTask::getReason,wfDoneTaskDto.getReason());
        queryWrapper.orderByDesc(WfDoneTask::getCreateTime);
        Page<WfDoneTask> page = wfDoneTaskMapper.selectPage(wfDoneTaskDto.getPage(),queryWrapper);
        Page<WfDoneTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfDoneTaskVo.class);
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
            throw new IncloudException("用户未登录！");
        }
        String userId = loginAppUser.getUserName();
        return userId;
    }

    /**
    * 自定义查询操作
    * @param wfDoneTaskDto
    * @return
    */
    @Override
    public Page lists(WfDoneTaskDto wfDoneTaskDto) {
        Page<WfDoneTaskVo> pageVo = wfDoneTaskMapper.getPageList(wfDoneTaskDto.getPage(),wfDoneTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfDoneTaskVo get(Long id) {
        WfDoneTask wfDoneTask = super.getById(id);
        WfDoneTaskVo wfDoneTaskVo = null;
        if(wfDoneTask !=null){
            wfDoneTaskVo = dozerMapper.map(wfDoneTask,WfDoneTaskVo.class);
        }
        log.debug("查询成功");
        return wfDoneTaskVo;
    }

    /**
    * 保存实体
    * @param wfDoneTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfDoneTaskDto wfDoneTaskDto) {
        WfDoneTask wfDoneTask = dozerMapper.map(wfDoneTaskDto,WfDoneTask.class);
        boolean result = super.save(wfDoneTask);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfDoneTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfDoneTaskDto wfDoneTaskDto) {
        WfDoneTask wfDoneTask = dozerMapper.map(wfDoneTaskDto,WfDoneTask.class);
        return update(wfDoneTask);
    }

    @Override
    @Transactional
    public Boolean update(WfDoneTask wfDoneTask) {
        boolean result = super.updateById(wfDoneTask);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean updateStateByProInsId(String processInstanceId,Integer state) {
        LambdaUpdateWrapper<WfDoneTask> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(WfDoneTask::getCamundaProcinsId,processInstanceId)
                    .set(WfDoneTask::getState,state);;
        boolean update = update(queryWrapper);
        return update;
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
    public Boolean delete(String camundaTaskId) {
        LambdaQueryWrapper<WfDoneTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfDoneTask::getCamundaTaskId,camundaTaskId);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    @Transactional
    public Boolean deleteProcinsId(String processInstanceId) {
        LambdaQueryWrapper<WfDoneTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfDoneTask::getCamundaProcinsId,processInstanceId);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    public WfDoneTask get(String camundaTaskId) {
        LambdaQueryWrapper<WfDoneTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfDoneTask::getCamundaTaskId,camundaTaskId);
        WfDoneTask one = this.getOne(queryWrapper);
        if(ObjectUtil.isEmpty(one)){
            log.info("通过camundaTaskId查找不到WfDoneTask对象！");
        }
        return one;
    }

    @Override
    public List<WfDoneTask> getList(Long procinsId) {
        LambdaQueryWrapper<WfDoneTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfDoneTask::getProcinsId,procinsId);
        List<WfDoneTask> list = this.list(queryWrapper);
        return list;
    }

    @Override
    @Transactional
    public Boolean delWfTodoTaskByProInsAdnNodeKey(String procInstanceId, String nodeKey) {
        LambdaQueryWrapper<WfDoneTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfDoneTask::getCamundaProcinsId,procInstanceId)
                .eq(WfDoneTask::getNodeKey,nodeKey);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    public WfDoneTaskVo getFirstOne(String camundaProcInsId) {
        LambdaQueryWrapper<WfDoneTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfDoneTask::getCamundaProcinsId,camundaProcInsId).
                like(WfDoneTask::getAssignee,getUserID()).
                orderBy(true,false,WfDoneTask::getCreateTime);
        List<WfDoneTask> list = this.list(queryWrapper);
        if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
            WfDoneTask wfDoneTask = list.stream().findFirst().get();
            WfDoneTaskVo vo = dozerMapper.map(wfDoneTask, WfDoneTaskVo.class);
            return vo;
        }
        log.info("通过camundaProcInsId查找不到WfDoneTask对象！");
        return null;
    }

    @Override
    @Transactional
    public Boolean updateDefId(ProcessDefinition processDefinitionNew, WfProcDef wfProcDefNew, String processInstanceId) {
        LambdaUpdateWrapper<WfDoneTask> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(WfDoneTask::getCamundaProcinsId,processInstanceId)
                .set(WfDoneTask::getCamundaProcdefId,processDefinitionNew.getId())
                .set(WfDoneTask::getCamundaProcdefVersion,processDefinitionNew.getVersion())
                .set(WfDoneTask::getProcdefId,wfProcDefNew.getId());
        boolean update = update(queryWrapper);
        return update;
    }

    /**
     * 我发起的流程
     * @param wfDoneTaskDto
     * @return
     */
    @Override
    public Page myDraftList(WfDoneTaskDto wfDoneTaskDto) {
        LambdaQueryWrapper<WfDoneTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfDoneTaskDto.getBizKey()), WfDoneTask::getBizKey,wfDoneTaskDto.getBizKey())
                .eq(WfDoneTask::getStarterId,getUserID())
                .eq(StrUtil.isNotEmpty(wfDoneTaskDto.getAssignee()),WfDoneTask::getAssignee,wfDoneTaskDto.getAssignee())
                .eq(StrUtil.isNotEmpty(wfDoneTaskDto.getStarterName()),WfDoneTask::getStarterName,wfDoneTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getProcdefTypeId()),WfDoneTask::getProcdefTypeId,wfDoneTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfDoneTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfDoneTaskDto.getApplyEndTime())
                        ,WfDoneTask::getApplyTime,wfDoneTaskDto.getApplyStartTime(),wfDoneTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterOrgId()),WfDoneTask::getStarterOrgId,wfDoneTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterDeptId()),WfDoneTask::getStarterDeptId,wfDoneTaskDto.getStarterDeptId())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterId()), WfDoneTask::getStarterId,wfDoneTaskDto.getStarterId())
                .like(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterName()), WfDoneTask::getStarterName,wfDoneTaskDto.getStarterName())
                .like(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterDeptName()), WfDoneTask::getStarterDeptName,wfDoneTaskDto.getStarterDeptName())
                .like(StrUtil.isNotEmpty(wfDoneTaskDto.getCamundaProcdefKey()),WfDoneTask::getCamundaProcdefKey,wfDoneTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getCamundaProcdefVersion()),WfDoneTask::getCamundaProcdefVersion,wfDoneTaskDto.getCamundaProcdefVersion())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getState()), WfDoneTask::getState,wfDoneTaskDto.getState())
                .like(ObjectUtil.isNotEmpty(wfDoneTaskDto.getReason()), WfDoneTask::getReason,wfDoneTaskDto.getReason());
        queryWrapper.orderByDesc(WfDoneTask::getCreateTime);
        Page<WfDoneTask> page = wfDoneTaskMapper.selectPage(wfDoneTaskDto.getPage(),queryWrapper);
        Page<WfDoneTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfDoneTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page myDraftListForAd(WfDoneTaskDto wfDoneTaskDto) {
        LambdaQueryWrapper<WfDoneTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(StrUtil.isNotEmpty(wfDoneTaskDto.getBizKey()), WfDoneTask::getBizKey,wfDoneTaskDto.getBizKey())
                .eq(StrUtil.isNotEmpty(wfDoneTaskDto.getAssignee()),WfDoneTask::getAssignee,wfDoneTaskDto.getAssignee())
                .like(StrUtil.isNotEmpty(wfDoneTaskDto.getCurrentActivityAssigneeName()), WfDoneTask::getCurrentActivityAssigneeName,wfDoneTaskDto.getCurrentActivityAssigneeName())
                .eq(StrUtil.isNotEmpty(wfDoneTaskDto.getStarterName()),WfDoneTask::getStarterName,wfDoneTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getProcdefTypeId()),WfDoneTask::getProcdefTypeId,wfDoneTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfDoneTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfDoneTaskDto.getApplyEndTime())
                        ,WfDoneTask::getApplyTime,wfDoneTaskDto.getApplyStartTime(),wfDoneTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterOrgId()),WfDoneTask::getStarterOrgId,wfDoneTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterDeptId()),WfDoneTask::getStarterDeptId,wfDoneTaskDto.getStarterDeptId())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterId()), WfDoneTask::getStarterId,wfDoneTaskDto.getStarterId())
                .like(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterName()), WfDoneTask::getStarterName,wfDoneTaskDto.getStarterName())
                .like(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterDeptName()), WfDoneTask::getStarterDeptName,wfDoneTaskDto.getStarterDeptName())
                .like(StrUtil.isNotEmpty(wfDoneTaskDto.getCamundaProcdefKey()),WfDoneTask::getCamundaProcdefKey,wfDoneTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getCamundaProcdefVersion()),WfDoneTask::getCamundaProcdefVersion,wfDoneTaskDto.getCamundaProcdefVersion())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getState()), WfDoneTask::getState,wfDoneTaskDto.getState())
                .like(ObjectUtil.isNotEmpty(wfDoneTaskDto.getReason()), WfDoneTask::getReason,wfDoneTaskDto.getReason());
        queryWrapper.orderByDesc(WfDoneTask::getCreateTime);
        Page<WfDoneTask> page = wfDoneTaskMapper.selectPage(wfDoneTaskDto.getPage(),queryWrapper);
        Page<WfDoneTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfDoneTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page doneListForAd(WfDoneTaskDto wfDoneTaskDto) {
        LambdaQueryWrapper<WfDoneTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfDoneTaskDto.getBizKey()), WfDoneTask::getBizKey,wfDoneTaskDto.getBizKey())
                .eq(StrUtil.isNotEmpty(wfDoneTaskDto.getAssignee()),WfDoneTask::getAssignee,wfDoneTaskDto.getAssignee())
                .eq(StrUtil.isNotEmpty(wfDoneTaskDto.getCurrentActivityAssigneeName()),WfDoneTask::getCurrentActivityAssigneeName,wfDoneTaskDto.getCurrentActivityAssigneeName())
                .eq(StrUtil.isNotEmpty(wfDoneTaskDto.getStarterName()),WfDoneTask::getStarterName,wfDoneTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getProcdefTypeId()),WfDoneTask::getProcdefTypeId,wfDoneTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfDoneTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfDoneTaskDto.getApplyEndTime())
                        ,WfDoneTask::getApplyTime,wfDoneTaskDto.getApplyStartTime(),wfDoneTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterOrgId()),WfDoneTask::getStarterOrgId,wfDoneTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterDeptId()),WfDoneTask::getStarterDeptId,wfDoneTaskDto.getStarterDeptId())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterId()), WfDoneTask::getStarterId,wfDoneTaskDto.getStarterId())
                .like(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterName()), WfDoneTask::getStarterName,wfDoneTaskDto.getStarterName())
                .like(ObjectUtil.isNotEmpty(wfDoneTaskDto.getStarterDeptName()), WfDoneTask::getStarterDeptName,wfDoneTaskDto.getStarterDeptName())
                .like(StrUtil.isNotEmpty(wfDoneTaskDto.getCamundaProcdefKey()),WfDoneTask::getCamundaProcdefKey,wfDoneTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getCamundaProcdefVersion()),WfDoneTask::getCamundaProcdefVersion,wfDoneTaskDto.getCamundaProcdefVersion())
                .eq(ObjectUtil.isNotEmpty(wfDoneTaskDto.getState()), WfDoneTask::getState,wfDoneTaskDto.getState())
                .like(ObjectUtil.isNotEmpty(wfDoneTaskDto.getReason()), WfDoneTask::getReason,wfDoneTaskDto.getReason());
        queryWrapper.orderByDesc(WfDoneTask::getCreateTime);
        Page<WfDoneTask> page = wfDoneTaskMapper.selectPage(wfDoneTaskDto.getPage(),queryWrapper);
        Page<WfDoneTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfDoneTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }
}
