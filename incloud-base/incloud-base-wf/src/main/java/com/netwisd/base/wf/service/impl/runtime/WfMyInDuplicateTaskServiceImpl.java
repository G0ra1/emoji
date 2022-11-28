package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.wf.dto.WfMyInDuplicateTaskDto;
import com.netwisd.base.wf.entity.WfMyInDuplicateTask;
import com.netwisd.base.wf.entity.WfMyOutDuplicateTask;
import com.netwisd.base.wf.entity.WfProcDef;
import com.netwisd.base.wf.mapper.WfMyInDuplicateTaskMapper;
import com.netwisd.base.wf.service.runtime.WfMyInDuplicateTaskService;
import com.netwisd.base.common.vo.wf.WfMyInDuplicateTaskVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 传阅任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-13 09:39:14
 */
@Service
@Slf4j
public class WfMyInDuplicateTaskServiceImpl extends ServiceImpl<WfMyInDuplicateTaskMapper, WfMyInDuplicateTask> implements WfMyInDuplicateTaskService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfMyInDuplicateTaskMapper wfMyInDuplicateTaskMapper;

    /**
    * 单表简单查询操作
    * @param wfMyInDuplicateTaskDto
    * @return
    */
    @Override
    public Page list(WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto) {
        log.debug("查询我收到的传阅参数:"+ wfMyInDuplicateTaskDto.toString());
        LambdaQueryWrapper<WfMyInDuplicateTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfMyInDuplicateTaskDto.getBizKey()), WfMyInDuplicateTask::getBizKey, wfMyInDuplicateTaskDto.getBizKey())
                .eq(WfMyInDuplicateTask::getAssignee,getUserID())
                .eq(StrUtil.isNotEmpty(wfMyInDuplicateTaskDto.getCurrentUserId()), WfMyInDuplicateTask::getAssignee, wfMyInDuplicateTaskDto.getCurrentUserId())
                .eq(StrUtil.isNotEmpty(wfMyInDuplicateTaskDto.getStarterName()), WfMyInDuplicateTask::getStarterName, wfMyInDuplicateTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfMyInDuplicateTaskDto.getProcdefTypeId()), WfMyInDuplicateTask::getProcdefTypeId, wfMyInDuplicateTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfMyInDuplicateTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfMyInDuplicateTaskDto.getApplyEndTime())
                        , WfMyInDuplicateTask::getApplyTime, wfMyInDuplicateTaskDto.getApplyStartTime(), wfMyInDuplicateTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfMyInDuplicateTaskDto.getStarterOrgId()), WfMyInDuplicateTask::getStarterOrgId, wfMyInDuplicateTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfMyInDuplicateTaskDto.getStarterDeptId()), WfMyInDuplicateTask::getStarterDeptId, wfMyInDuplicateTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfMyInDuplicateTaskDto.getCamundaProcdefKey()), WfMyInDuplicateTask::getCamundaProcdefKey, wfMyInDuplicateTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfMyInDuplicateTaskDto.getCamundaProcdefVersion()), WfMyInDuplicateTask::getCamundaProcdefVersion, wfMyInDuplicateTaskDto.getCamundaProcdefVersion());
        queryWrapper.orderByDesc(WfMyInDuplicateTask::getCreateTime);
        Page<WfMyInDuplicateTask> page = wfMyInDuplicateTaskMapper.selectPage(wfMyInDuplicateTaskDto.getPage(),queryWrapper);
        Page<WfMyInDuplicateTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfMyInDuplicateTaskVo.class);
        log.debug("查询我收到的传阅查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page inDuplicateListForAd(WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto) {
        log.debug("查询我收到的传阅参数:"+ wfMyInDuplicateTaskDto.toString());
        LambdaQueryWrapper<WfMyInDuplicateTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfMyInDuplicateTaskDto.getBizKey()), WfMyInDuplicateTask::getBizKey, wfMyInDuplicateTaskDto.getBizKey())
                .eq(StrUtil.isNotEmpty(wfMyInDuplicateTaskDto.getAssignee()), WfMyInDuplicateTask::getAssignee,wfMyInDuplicateTaskDto.getAssignee())
                .eq(StrUtil.isNotEmpty(wfMyInDuplicateTaskDto.getCurrentUserId()), WfMyInDuplicateTask::getAssignee, wfMyInDuplicateTaskDto.getCurrentUserId())
                .eq(StrUtil.isNotEmpty(wfMyInDuplicateTaskDto.getStarterName()), WfMyInDuplicateTask::getStarterName, wfMyInDuplicateTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfMyInDuplicateTaskDto.getProcdefTypeId()), WfMyInDuplicateTask::getProcdefTypeId, wfMyInDuplicateTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfMyInDuplicateTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfMyInDuplicateTaskDto.getApplyEndTime())
                        , WfMyInDuplicateTask::getApplyTime, wfMyInDuplicateTaskDto.getApplyStartTime(), wfMyInDuplicateTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfMyInDuplicateTaskDto.getStarterOrgId()), WfMyInDuplicateTask::getStarterOrgId, wfMyInDuplicateTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfMyInDuplicateTaskDto.getStarterDeptId()), WfMyInDuplicateTask::getStarterDeptId, wfMyInDuplicateTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfMyInDuplicateTaskDto.getCamundaProcdefKey()), WfMyInDuplicateTask::getCamundaProcdefKey, wfMyInDuplicateTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfMyInDuplicateTaskDto.getCamundaProcdefVersion()), WfMyInDuplicateTask::getCamundaProcdefVersion, wfMyInDuplicateTaskDto.getCamundaProcdefVersion());
        queryWrapper.orderByDesc(WfMyInDuplicateTask::getCreateTime);
        Page<WfMyInDuplicateTask> page = wfMyInDuplicateTaskMapper.selectPage(wfMyInDuplicateTaskDto.getPage(),queryWrapper);
        Page<WfMyInDuplicateTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfMyInDuplicateTaskVo.class);
        log.debug("查询我收到的传阅查询条数:"+pageVo.getTotal());
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
    * @param wfMyInDuplicateTaskDto
    * @return
    */
    @Override
    public Page lists(WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto) {
        Page<WfMyInDuplicateTaskVo> pageVo = wfMyInDuplicateTaskMapper.getPageList(wfMyInDuplicateTaskDto.getPage(), wfMyInDuplicateTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfMyInDuplicateTaskVo get(Long id) {
        WfMyInDuplicateTask wfMyInDuplicateTask = super.getById(id);
        WfMyInDuplicateTaskVo wfMyInDuplicateTaskVo = null;
        if(wfMyInDuplicateTask !=null){
            wfMyInDuplicateTaskVo = dozerMapper.map(wfMyInDuplicateTask, WfMyInDuplicateTaskVo.class);
        }
        log.debug("查询成功");
        return wfMyInDuplicateTaskVo;
    }

    /**
    * 保存实体
    * @param wfMyInDuplicateTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto) {
        WfMyInDuplicateTask wfMyInDuplicateTask = dozerMapper.map(wfMyInDuplicateTaskDto, WfMyInDuplicateTask.class);
        boolean result = super.save(wfMyInDuplicateTask);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfMyInDuplicateTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfMyInDuplicateTaskDto wfMyInDuplicateTaskDto) {
        WfMyInDuplicateTask wfMyInDuplicateTask = dozerMapper.map(wfMyInDuplicateTaskDto, WfMyInDuplicateTask.class);
        boolean result = super.updateById(wfMyInDuplicateTask);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean updateStateByProInsId(String processInstanceId,Integer state) {
        LambdaUpdateWrapper<WfMyInDuplicateTask> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(WfMyInDuplicateTask::getCamundaProcinsId,processInstanceId).
                     set(WfMyInDuplicateTask::getState,state);
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
    public Boolean claimDuplicate(Long duplicateTaskID, String userId) {
        LambdaUpdateWrapper<WfMyInDuplicateTask> wrapper = new LambdaUpdateWrapper();
        wrapper.set(WfMyInDuplicateTask::getCliamTime, LocalDateTime.now())
                .set(WfMyInDuplicateTask::getUpdateTime,LocalDateTime.now())
                .eq(WfMyInDuplicateTask::getId,duplicateTaskID);
        boolean update = this.update(wrapper);
        return update;
    }

    @Override
    @Transactional
    public Boolean deleteProcinsId(String processInstanceId) {
        LambdaQueryWrapper<WfMyInDuplicateTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfMyInDuplicateTask::getCamundaProcinsId,processInstanceId);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    @Transactional
    public Boolean delDuplicateByProInsAndTaskId(String processInstanceId, String camundaTaskId) {
        LambdaQueryWrapper<WfMyInDuplicateTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfMyInDuplicateTask::getCamundaProcinsId,processInstanceId)
                .eq(WfMyInDuplicateTask::getCamundaTaskId,camundaTaskId);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    public List getList(String camundaTaskId) {
        LambdaQueryWrapper<WfMyInDuplicateTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfMyInDuplicateTask::getCamundaTaskId, camundaTaskId);
        List<WfMyInDuplicateTask> list = this.list(queryWrapper);
        if(ObjectUtil.isEmpty(list) || list.size() == 0){
            return null;
        }
        List<WfMyInDuplicateTaskVo> listVo = DozerUtils.mapList(dozerMapper,list, WfMyInDuplicateTaskVo.class);
        return listVo;
    }

    @Override
    public Boolean replyDescription(Long id, String description) {
        if(ObjectUtil.isNull(id)) {
            throw new IncloudException("传阅的id不能为空！");
        }
        log.info("操作：replyDescription。被传阅人 设置已阅 并且添加意见:id:{},description:{}",id,description);
        LambdaUpdateWrapper<WfMyInDuplicateTask> wrapper = new LambdaUpdateWrapper();
        wrapper.set(WfMyInDuplicateTask::getDescription, description)
                .set(WfMyInDuplicateTask::getUpdateTime,LocalDateTime.now())
                .eq(WfMyInDuplicateTask::getId,id);
        boolean update = this.update(wrapper);
        return update;
    }

    @Override
    @Transactional
    public Boolean updateDefId(ProcessDefinition processDefinitionNew, WfProcDef wfProcDefNew, String processInstanceId) {
        LambdaUpdateWrapper<WfMyInDuplicateTask> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(WfMyInDuplicateTask::getCamundaProcinsId,processInstanceId)
                .set(WfMyInDuplicateTask::getCamundaProcdefId,processDefinitionNew.getId())
                .set(WfMyInDuplicateTask::getCamundaProcdefVersion,processDefinitionNew.getVersion())
                .set(WfMyInDuplicateTask::getProcdefId,wfProcDefNew.getId());
        boolean update = update(queryWrapper);
        return update;
    }
}
