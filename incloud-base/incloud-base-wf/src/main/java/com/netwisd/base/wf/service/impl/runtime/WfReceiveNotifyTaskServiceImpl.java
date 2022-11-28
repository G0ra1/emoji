package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.vo.wf.WfReceiveNotifyTaskVo;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.dto.WfReceiveNotifyTaskDto;
import com.netwisd.base.wf.entity.WfReceiveNotifyTask;
import com.netwisd.base.wf.mapper.WfReceiveNotifyTaskMapper;
import com.netwisd.base.wf.service.runtime.WfReceiveNotifyTaskService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description 我收到的知会 功能描述...
 * @author 云数网讯 XHL
 * @date 2022-02-14 09:39:14
 */
@Service
@Slf4j
public class WfReceiveNotifyTaskServiceImpl extends ServiceImpl<WfReceiveNotifyTaskMapper, WfReceiveNotifyTask> implements WfReceiveNotifyTaskService {

    @Autowired
    private WfReceiveNotifyTaskMapper wfReceiveNotifyTaskMapper;

    @Autowired
    private Mapper dozerMapper;

    @Override
    public Page list(WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto) {
        log.debug("查询我收到的知会参数:"+ wfReceiveNotifyTaskDto.toString());
        LambdaQueryWrapper<WfReceiveNotifyTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfReceiveNotifyTaskDto.getBizKey()), WfReceiveNotifyTask::getBizKey, wfReceiveNotifyTaskDto.getBizKey())
                .eq(WfReceiveNotifyTask::getAssignee,getUserID())
                .eq(StrUtil.isNotEmpty(wfReceiveNotifyTaskDto.getOwnner()), WfReceiveNotifyTask::getOwnner, wfReceiveNotifyTaskDto.getOwnner())
                .like(StrUtil.isNotEmpty(wfReceiveNotifyTaskDto.getOwnnerName()), WfReceiveNotifyTask::getOwnnerName, wfReceiveNotifyTaskDto.getOwnnerName())
                .eq(StrUtil.isNotEmpty(wfReceiveNotifyTaskDto.getStarterName()), WfReceiveNotifyTask::getStarterName, wfReceiveNotifyTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfReceiveNotifyTaskDto.getProcdefTypeId()), WfReceiveNotifyTask::getProcdefTypeId, wfReceiveNotifyTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfReceiveNotifyTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfReceiveNotifyTaskDto.getApplyEndTime())
                        , WfReceiveNotifyTask::getApplyTime, wfReceiveNotifyTaskDto.getApplyStartTime(), wfReceiveNotifyTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfReceiveNotifyTaskDto.getStarterOrgId()), WfReceiveNotifyTask::getStarterOrgId, wfReceiveNotifyTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfReceiveNotifyTaskDto.getStarterDeptId()), WfReceiveNotifyTask::getStarterDeptId, wfReceiveNotifyTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfReceiveNotifyTaskDto.getCamundaProcdefKey()), WfReceiveNotifyTask::getCamundaProcdefKey, wfReceiveNotifyTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfReceiveNotifyTaskDto.getCamundaProcdefVersion()), WfReceiveNotifyTask::getCamundaProcdefVersion, wfReceiveNotifyTaskDto.getCamundaProcdefVersion());
        queryWrapper.orderByDesc(WfReceiveNotifyTask::getCreateTime);
        Page<WfReceiveNotifyTask> page = wfReceiveNotifyTaskMapper.selectPage(wfReceiveNotifyTaskDto.getPage(),queryWrapper);
        Page<WfReceiveNotifyTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfReceiveNotifyTaskVo.class);
        log.debug("查询我收到的知会查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page receiveNotifyListForAd(WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto) {
        log.debug("查询我收到的知会参数:"+ wfReceiveNotifyTaskDto.toString());
        LambdaQueryWrapper<WfReceiveNotifyTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfReceiveNotifyTaskDto.getBizKey()), WfReceiveNotifyTask::getBizKey, wfReceiveNotifyTaskDto.getBizKey())
                .eq(StrUtil.isNotEmpty(wfReceiveNotifyTaskDto.getAssignee()), WfReceiveNotifyTask::getAssignee, wfReceiveNotifyTaskDto.getAssignee())
                .like(StrUtil.isNotEmpty(wfReceiveNotifyTaskDto.getAssigneeName()), WfReceiveNotifyTask::getAssigneeName, wfReceiveNotifyTaskDto.getAssigneeName())
                .eq(StrUtil.isNotEmpty(wfReceiveNotifyTaskDto.getOwnner()), WfReceiveNotifyTask::getOwnner, wfReceiveNotifyTaskDto.getOwnner())
                .like(StrUtil.isNotEmpty(wfReceiveNotifyTaskDto.getOwnnerName()), WfReceiveNotifyTask::getOwnnerName, wfReceiveNotifyTaskDto.getOwnnerName())
                .eq(StrUtil.isNotEmpty(wfReceiveNotifyTaskDto.getStarterName()), WfReceiveNotifyTask::getStarterName, wfReceiveNotifyTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfReceiveNotifyTaskDto.getProcdefTypeId()), WfReceiveNotifyTask::getProcdefTypeId, wfReceiveNotifyTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfReceiveNotifyTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfReceiveNotifyTaskDto.getApplyEndTime())
                        , WfReceiveNotifyTask::getApplyTime, wfReceiveNotifyTaskDto.getApplyStartTime(), wfReceiveNotifyTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfReceiveNotifyTaskDto.getStarterOrgId()), WfReceiveNotifyTask::getStarterOrgId, wfReceiveNotifyTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfReceiveNotifyTaskDto.getStarterDeptId()), WfReceiveNotifyTask::getStarterDeptId, wfReceiveNotifyTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfReceiveNotifyTaskDto.getCamundaProcdefKey()), WfReceiveNotifyTask::getCamundaProcdefKey, wfReceiveNotifyTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfReceiveNotifyTaskDto.getCamundaProcdefVersion()), WfReceiveNotifyTask::getCamundaProcdefVersion, wfReceiveNotifyTaskDto.getCamundaProcdefVersion());
        queryWrapper.orderByDesc(WfReceiveNotifyTask::getCreateTime);
        Page<WfReceiveNotifyTask> page = wfReceiveNotifyTaskMapper.selectPage(wfReceiveNotifyTaskDto.getPage(),queryWrapper);
        Page<WfReceiveNotifyTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfReceiveNotifyTaskVo.class);
        log.debug("查询我收到的知会查询条数:"+pageVo.getTotal());
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

    @Override
    public Page lists(WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto) {
        return null;
    }

    @Override
    public WfReceiveNotifyTaskVo get(Long id) {
        WfReceiveNotifyTask WfReceiveNotifyTask = super.getById(id);
        WfReceiveNotifyTaskVo wfReceiveNotifyTaskVo = null;
        if(WfReceiveNotifyTask !=null){
            wfReceiveNotifyTaskVo = dozerMapper.map(WfReceiveNotifyTask, WfReceiveNotifyTaskVo.class);
        }
        log.debug("查询成功");
        return wfReceiveNotifyTaskVo;
    }

    @Override
    public Boolean save(WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto) {
        WfReceiveNotifyTask wfReceiveNotifyTask = dozerMapper.map(wfReceiveNotifyTaskDto, WfReceiveNotifyTask.class);
        boolean result = super.save(wfReceiveNotifyTask);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    @Override
    public Boolean update(WfReceiveNotifyTaskDto wfReceiveNotifyTaskDto) {
        WfReceiveNotifyTask wfReceiveNotifyTask = dozerMapper.map(wfReceiveNotifyTaskDto, WfReceiveNotifyTask.class);
        boolean result = super.updateById(wfReceiveNotifyTask);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    @Override
    public Boolean updateStateByProInsId(String processInstanceId, Integer state) {
        LambdaUpdateWrapper<WfReceiveNotifyTask> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(WfReceiveNotifyTask::getCamundaProcinsId,processInstanceId).
                set(WfReceiveNotifyTask::getState,state);
        boolean update = update(queryWrapper);
        return update;
    }

    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    public Boolean claimNotifyId(Long claimNotifyId, String userId) {
        LambdaUpdateWrapper<WfReceiveNotifyTask> wrapper = new LambdaUpdateWrapper();
        wrapper.set(WfReceiveNotifyTask::getCliamTime, LocalDateTime.now())
                .set(WfReceiveNotifyTask::getUpdateTime,LocalDateTime.now())
                .eq(WfReceiveNotifyTask::getId,claimNotifyId);
        boolean update = this.update(wrapper);
        if(update){
            log.debug("传阅：{},userName：{}.知会信息传阅成功！",claimNotifyId,userId);
        }
        return update;
    }

    @Override
    public Boolean deleteProcinsId(String processInstanceId) {
        LambdaQueryWrapper<WfReceiveNotifyTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfReceiveNotifyTask::getCamundaProcinsId,processInstanceId);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    public Boolean delNotifyByProInsAndTaskId(String processInstanceId, String camundaTaskId) {
        LambdaQueryWrapper<WfReceiveNotifyTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfReceiveNotifyTask::getCamundaProcinsId,processInstanceId)
                .eq(WfReceiveNotifyTask::getCamundaTaskId,camundaTaskId);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    public List getList(String camundaTaskId) {
        LambdaQueryWrapper<WfReceiveNotifyTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfReceiveNotifyTask::getCamundaTaskId, camundaTaskId);
        List<WfReceiveNotifyTask> list = this.list(queryWrapper);
        if(ObjectUtil.isEmpty(list) || list.size() == 0){
            return null;
        }
        List<WfReceiveNotifyTaskVo> listVo = DozerUtils.mapList(dozerMapper,list, WfReceiveNotifyTaskVo.class);
        return listVo;
    }

    @Override
    public List<WfReceiveNotifyTask> getNotifyTaskByProcinsIdAndNodeKey(String camundaProcinsId, String camundaNodeKey) {
        if(StringUtils.isBlank(camundaProcinsId)) {
            throw new IncloudException("流程实例ID不能为空！");
        }
        if(StringUtils.isBlank(camundaNodeKey)) {
            throw new IncloudException("流程定义Key不能为空！");
        }
        LambdaQueryWrapper<WfReceiveNotifyTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfReceiveNotifyTask::getCamundaProcinsId, camundaProcinsId);
        queryWrapper.eq(WfReceiveNotifyTask::getCamundaNodeKey, camundaNodeKey);
        queryWrapper.eq(WfReceiveNotifyTask::getState, WfProcessEnum.NOTIFY.getType()); //收到任务时状态就会标识是知会 完成代表意见已处理完毕
        List<WfReceiveNotifyTask> list = this.list(queryWrapper);
        return list;
    }
}
