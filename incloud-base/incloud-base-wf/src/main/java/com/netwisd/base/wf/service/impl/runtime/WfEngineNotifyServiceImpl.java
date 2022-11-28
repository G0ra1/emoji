package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.constants.WfProcessLogEnum;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.service.WfProcdefService;
import com.netwisd.base.wf.service.WfSendNotifyTaskService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.common.core.exception.CheckedException;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author XHL
 * @description
 * @date 2022/03/08 16:41
 */
@Service
@Slf4j
public class WfEngineNotifyServiceImpl implements WfEngineNotifyService {

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> incloudCache;

    @Autowired
    Mapper dozerMapper;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    WfMyOutDuplicateTaskService wfMyOutDuplicateTaskService;

    @Autowired
    WfReceiveNotifyTaskService wfReceiveNotifyTaskService;

    @Autowired
    WfProcdefService wfProcdefService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    WfSendNotifyTaskService wfSendNotifyTaskService;

    @Autowired
    WfEngineSubmitService wfEngineSubmitService;

    @Override
    public void createNotify(WfEngineDto.HandleDto handleDto, Task task) {
        List<String> wfNotifyList = handleDto.getWfNotifyList();
        if(CollectionUtil.isNotEmpty(wfNotifyList)) {
            LoginAppUser loginAppUserInfo = AppUserUtil.getLoginAppUserAndCheck();
            List<Object> objList = new ArrayList<>();
            wfNotifyList.forEach(d->objList.add(d));
            List<MdmUserVo> mdmUserVos = incloudCache.gets(objList);
            if(CollectionUtil.isEmpty(mdmUserVos)) {
                throw new IncloudException("获取缓存数据失败！");
            }
            Map<String, MdmUserVo> userMap = mdmUserVos.stream().collect(Collectors.toMap(MdmUserVo::getUserName, d -> d));
            String camundaTaskId = task.getId();
            // 修改待办人任务状态为知会状态 切改状态不可以提交
            WfTodoTask wfTodoTask = wfTodoTaskService.get(camundaTaskId);
            wfTodoTask.setState(WfProcessEnum.NOTIFY.getType());
            wfTodoTaskService.update(wfTodoTask);
            List<WfReceiveNotifyTask> list = new ArrayList<>();
            List<WfSendNotifyTask> _list = new ArrayList<>();
            if(ObjectUtil.isNotEmpty(wfTodoTask) && ObjectUtil.isNotEmpty(wfNotifyList)){
                //我发出的待办 办理人拼接
                StringBuilder userNames = new StringBuilder();
                StringBuilder AssigneeName = new StringBuilder();
                //保存台账 进我收到的知会
                for(String userName : wfNotifyList){
                    userNames.append(userName);
                    userNames.append(",");
                    AssigneeName.append(userMap.get(userName).getUserNameCh());
                    AssigneeName.append(",");
                    WfReceiveNotifyTask wfReceiveNotifyTask = dozerMapper.map(wfTodoTask, WfReceiveNotifyTask.class);
                    wfReceiveNotifyTask.setId(IdGenerator.getIdGenerator());
                    wfReceiveNotifyTask.setCliamTime(null);
                    wfReceiveNotifyTask.setAssignee(userName); //知会办理人
                    wfReceiveNotifyTask.setAssigneeName(userMap.get(userName).getUserNameCh());
                    wfReceiveNotifyTask.setOwnner(loginAppUserInfo.getUserName()); //发起的知会人
                    wfReceiveNotifyTask.setOwnnerName(loginAppUserInfo.getUserNameCh());
                    wfReceiveNotifyTask.setCreateTime(LocalDateTime.now());
                    wfReceiveNotifyTask.setUpdateTime(LocalDateTime.now());
                    wfReceiveNotifyTask.setCamundaTaskId(task.getId());
                    wfReceiveNotifyTask.setCamundaNodeKey(task.getTaskDefinitionKey());
                    wfReceiveNotifyTask.setCamundaNodeName(task.getName());
                    wfReceiveNotifyTask.setCamundaNodeType(wfTodoTask.getNodeType());
                    wfReceiveNotifyTask.setNotifyOpinion(null);
                    wfReceiveNotifyTask.setIsCallActivity(wfTodoTask.getIsCallActivity());
                    wfReceiveNotifyTask.setIsClone(wfTodoTask.getIsClone());
                    wfReceiveNotifyTask.setCamundaParentProcinsId(wfTodoTask.getCamundaParentProcinsId());
                    wfReceiveNotifyTask.setParentProcinsId(wfTodoTask.getParentProcinsId());
                    wfReceiveNotifyTask.setCurrentActivityId(wfTodoTask.getNodeKey());
                    wfReceiveNotifyTask.setCurrentActivityName(wfTodoTask.getNodeName());
                    wfReceiveNotifyTask.setCurrentActivityAssignee(wfTodoTask.getAssignee());
                    wfReceiveNotifyTask.setCurrentActivityAssigneeName(wfTodoTask.getAssigneeName());
                    //处理新的参数 把知会人去掉
                    WfEngineDto.HandleDto _handleDto = dozerMapper.map(handleDto, WfEngineDto.HandleDto.class);
                    _handleDto.setWfNotifyList(null);
                    wfReceiveNotifyTask.setSubmitParam(JSONObject.toJSON(_handleDto).toString()); //设置提交参数
                    list.add(wfReceiveNotifyTask);
                }
                //保存调账 进我发起的知会
                WfSendNotifyTask wfSendNotifyTask = dozerMapper.map(wfTodoTask, WfSendNotifyTask.class);
                wfSendNotifyTask.setCliamTime(null);
                wfSendNotifyTask.setAssignee(userNames.deleteCharAt(userNames.length()-1).toString()); //知会办理人
                wfSendNotifyTask.setAssigneeName(AssigneeName.deleteCharAt(AssigneeName.length()-1).toString());
                wfSendNotifyTask.setOwnner(loginAppUserInfo.getUserName()); //发起的知会人
                wfSendNotifyTask.setOwnnerName(loginAppUserInfo.getUserNameCh());
                wfSendNotifyTask.setCreateTime(LocalDateTime.now());
                wfSendNotifyTask.setUpdateTime(LocalDateTime.now());
                wfSendNotifyTask.setCamundaTaskId(task.getId());
                wfSendNotifyTask.setCamundaNodeKey(task.getTaskDefinitionKey());
                wfSendNotifyTask.setCamundaNodeName(task.getName());
                wfSendNotifyTask.setCamundaNodeType(wfTodoTask.getNodeType());
                wfSendNotifyTask.setNotifyOpinion(null);
                wfSendNotifyTask.setIsCallActivity(wfTodoTask.getIsCallActivity());
                wfSendNotifyTask.setIsClone(wfTodoTask.getIsClone());
                wfSendNotifyTask.setCamundaParentProcinsId(wfTodoTask.getCamundaParentProcinsId());
                wfSendNotifyTask.setParentProcinsId(wfTodoTask.getParentProcinsId());
                wfSendNotifyTask.setCurrentActivityId(wfTodoTask.getNodeKey());
                wfSendNotifyTask.setCurrentActivityName(wfTodoTask.getNodeName());
                wfSendNotifyTask.setCurrentActivityAssignee(wfTodoTask.getAssignee());
                wfSendNotifyTask.setCurrentActivityAssigneeName(wfTodoTask.getAssigneeName());
                wfSendNotifyTask.setId(IdGenerator.getIdGenerator());
                _list.add(wfSendNotifyTask);
                if(CollectionUtil.isNotEmpty(list)) {
                    wfReceiveNotifyTaskService.saveBatch(list);
                }
                if(CollectionUtil.isNotEmpty(_list)) {
                    wfSendNotifyTaskService.saveBatch(_list);
                }
                log.info("知会任务保存成功！");
                duplicateProcessLog(list);
            }
        } else {
            throw new IncloudException("知会人员信息不能为空！");
        }
    }

    @Override
    @Transactional
    public void handleNotifyOp(String id, String op) {
        if(StringUtils.isBlank(id)) {
            throw new IncloudException("知会任务id不能为空！");
        }
        if(StringUtils.isBlank(op)) {
            throw new IncloudException("知会任务意见不能为空！");
        }
        //查询出知会具体信息
        WfReceiveNotifyTask wfReceiveNotifyTask = wfReceiveNotifyTaskService.getById(id);
        if(null == wfReceiveNotifyTask) {
            throw new IncloudException("不存在该知会任务。");
        }
        //设置意见
        wfReceiveNotifyTask.setNotifyOpinion(op);
        wfReceiveNotifyTask.setState(WfProcessEnum.DONE.getType());
        wfReceiveNotifyTaskService.updateById(wfReceiveNotifyTask);
        //判断是否所有得知会信息都处理了 如果是最后一条则模拟提交
        List<WfReceiveNotifyTask> wfReceiveNotifyTaskList = wfReceiveNotifyTaskService.getNotifyTaskByProcinsIdAndNodeKey(wfReceiveNotifyTask.getCamundaProcinsId(),wfReceiveNotifyTask.getCamundaNodeKey());
        //如果是最后一条 则后台调用提交
        if(CollectionUtil.isEmpty(wfReceiveNotifyTaskList)) {
           String submitParam = wfReceiveNotifyTask.getSubmitParam();
           if(StringUtils.isBlank(submitParam)) {
               throw new IncloudException("提交参数不能为空！");
           }
           //做流程提交
           WfEngineDto.HandleDto handleDto = JSON.parseObject(submitParam, WfEngineDto.HandleDto.class);
           wfEngineSubmitService.submit(handleDto);
        }
    }

    /**
     * 传阅日志
     * @param list
     */
    private void duplicateProcessLog(List<WfReceiveNotifyTask> list){
        List<WfProcessLog> wfProcessLogList = new ArrayList<>();
        list.forEach(wfDuplicateTask -> {
            WfProcessLog wfProcessLog = new WfProcessLog();
            dozerMapper.map(wfDuplicateTask,wfProcessLog);
            MdmUserVo currentUser = getMdmUserVo(wfDuplicateTask.getAssignee());
            wfProcessLog.setUserName(currentUser.getUserName());
            wfProcessLog.setUserNameCh(currentUser.getUserNameCh());
            wfProcessLog.setDeptId(String.valueOf(currentUser.getParentDeptId()));
            wfProcessLog.setDeptName(currentUser.getParentDeptName());
            wfProcessLog.setOrgId(String.valueOf(currentUser.getParentOrgId()));
            wfProcessLog.setOrgName(currentUser.getParentOrgName());
            wfProcessLog.setId(null);
            wfProcessLog.setNodeId(wfDuplicateTask.getCamundaNodeKey());
            wfProcessLog.setNodeName(wfDuplicateTask.getCamundaNodeName());
            wfProcessLog.setNodeType(wfDuplicateTask.getCamundaNodeType());
            wfProcessLog.setStartTime(LocalDateTime.now());
            wfProcessLog.setEndTime(LocalDateTime.now());
            wfProcessLog.setCreateTime(LocalDateTime.now());
            wfProcessLog.setType(WfProcessLogEnum.NOTIFY.getType());
            wfProcessLog.setDecision(WfProcessLogEnum.NOTIFY.getType());
            wfProcessLogList.add(wfProcessLog);
        });
        wfProcessLogService.saveBatch(wfProcessLogList);
        log.info("知会wfProcessLog保存成功！");
    }

    /**
     * 通过分级获取用户信息
     * @param userName
     * @return
     */
    @SneakyThrows
    private MdmUserVo getMdmUserVo(String userName){
        MdmUserVo mdmUserVo = incloudCache.get(userName);
        if(mdmUserVo == null){
            throw new CheckedException("根据用户ID查找不到用户信息");
        }
        return mdmUserVo;
    }
}
