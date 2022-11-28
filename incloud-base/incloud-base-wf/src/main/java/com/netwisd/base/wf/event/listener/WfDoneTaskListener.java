package com.netwisd.base.wf.event.listener;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.wf.constants.WfProcessLogEnum;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.service.procdef.WfNodeDefService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.IdentityLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * @Description: 已办任务的事件------已弃用
 * @Author: zouliming@netwisd.com
 * @Date: 2020/8/13 11:14 上午
 * 已弃用
 */
@Slf4j
@Component
@Deprecated
public class WfDoneTaskListener implements TaskListener {

    @Autowired
    WfDoneTaskService wfDoneTaskService;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    WfNodeDefService wfNodeDefService;

    @Autowired
    TaskService taskService;

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> incloudCache;

    @Autowired
    Mapper dozerMapper;

    @Override
    @Deprecated
    public void notify(DelegateTask delegateTask) {
        logInfo(delegateTask);
        String description = delegateTask.getDescription();
        //意见不会空时，为空有可能是：被撤回的目标
        if(StrUtil.isNotEmpty(description)){
            WfDoneTask wfDoneTask = wfDoneTaskService.get(delegateTask.getId());
            WfProcess wfProcess = wfProcessService.get(delegateTask.getProcessInstanceId());
            if(ObjectUtil.isEmpty(wfDoneTask)){
                if(ObjectUtil.isNotEmpty(wfProcess)){
                    initWfDoneTask(delegateTask,wfProcess);
                    //更新流程日志
                    WfProcessLog wfProcessLog = wfProcessLogService.get(delegateTask.getId(),WfProcessLogEnum.NONE.getType());
                    if(ObjectUtil.isNotEmpty(wfProcessLog)){
                        updateWfProcessLog(delegateTask,wfProcessLog,wfProcess);
                    }
                }
            }
        }
    }

    /**
     * 创建用户任务时
     * @param delegateTask
     */
    @Deprecated
    private void initWfDoneTask(DelegateTask delegateTask,WfProcess wfProcess){
        WfTodoTask wfTodoTask = wfTodoTaskService.get(delegateTask.getId());
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(delegateTask.getProcessDefinitionId()).singleResult();
        WfDoneTask wfDoneTask = new WfDoneTask();
        dozerMapper.map(wfProcess,wfDoneTask);
        wfDoneTask.setId(null);
        wfDoneTask.setCamundaExectionId(delegateTask.getExecutionId());
        wfDoneTask.setCamundaProcdefId(delegateTask.getProcessDefinitionId());
        wfDoneTask.setCamundaProcdefKey(processDefinition.getKey());
        wfDoneTask.setCamundaProcdefVersion(processDefinition.getVersion());
        wfDoneTask.setCamundaProcinsId(delegateTask.getProcessInstanceId());
        wfDoneTask.setCamundaTaskId(delegateTask.getId());
        wfDoneTask.setCliamTime(ObjectUtil.isNotEmpty(wfTodoTask) ? wfTodoTask.getCliamTime():null);
        wfDoneTask.setProcinsId(wfProcess.getId());
        wfDoneTask.setCreateTime(LocalDateTime.now());
        wfDoneTask.setAssignee(delegateTask.getAssignee());
        if(StrUtil.isNotEmpty(delegateTask.getAssignee())){
            MdmUserVo mdmUserVo = incloudCache.get(delegateTask.getAssignee());
            wfDoneTask.setAssigneeName(mdmUserVo.getUserNameCh());
        }
        setCommonVar(delegateTask,wfDoneTask,wfProcess);
        wfDoneTaskService.save(wfDoneTask);
        log.info("事件保存已办任务成功！");
    }

    /**
     * 公共字段处理
     * @param delegateTask
     * @param wfDoneTask
     * @param wfProcess
     */
    private void setCommonVar(DelegateTask delegateTask,WfDoneTask wfDoneTask,WfProcess wfProcess){
        if(ObjectUtil.isNotEmpty(delegateTask.getDueDate())){
            wfDoneTask.setDueDate(LocalDateTime.ofInstant(delegateTask.getDueDate().toInstant(), ZoneId.systemDefault()));
        }
        wfDoneTask.setNodeKey(delegateTask.getTaskDefinitionKey());
        wfDoneTask.setNodeName(delegateTask.getName());
        wfDoneTask.setOwnner(delegateTask.getOwner());
        wfDoneTask.setPriority(delegateTask.getPriority());
        wfDoneTask.setState(wfProcess.getState());
        wfDoneTask.setTaskName(delegateTask.getName());
        setDescriptions(delegateTask,wfDoneTask);
        WfNodeDefVo wfNodeDefVo = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(delegateTask.getProcessDefinitionId(), delegateTask.getTaskDefinitionKey());
        if(ObjectUtil.isNotEmpty(wfNodeDefVo)){
            wfDoneTask.setNodeType(wfNodeDefVo.getNodeType());
        }
    }

    /**
     * 处理意见相关
     * @param delegateTask
     * @param wfDoneTask
     */
    private void setDescriptions(DelegateTask delegateTask,WfDoneTask wfDoneTask){
        if(ObjectUtil.isNotEmpty(delegateTask.getDescription())){
            wfDoneTask.setDecision(Integer.valueOf(delegateTask.getDescription()));
        }
        List<Comment> taskComments = taskService.getTaskComments(delegateTask.getId());
        if(ObjectUtil.isNotEmpty(taskComments)){
            taskComments.forEach(comment -> {
                wfDoneTask.setDescription(comment.getFullMessage());
            });
        }
    }

    /**
     * 处理意见相关
     * @param delegateTask
     * @param wfProcessLog
     */
    private void setDescriptions(DelegateTask delegateTask,WfProcessLog wfProcessLog){
        if(ObjectUtil.isNotEmpty(delegateTask.getDescription())){
            wfProcessLog.setDecision(Integer.valueOf(delegateTask.getDescription()));
        }
        List<Comment> taskComments = taskService.getTaskComments(delegateTask.getId());
        if(ObjectUtil.isNotEmpty(taskComments)){
            taskComments.forEach(comment -> {
                wfProcessLog.setDescription(comment.getFullMessage());
            });
        }
    }

    /**
     * 相关log
     * @param delegateTask
     */
    private void logInfo(DelegateTask delegateTask){
        log.info("taskId:{}",delegateTask.getId());
        if(ObjectUtil.isNotEmpty(delegateTask.getCandidates()) && delegateTask.getCandidates().size() > 0){
            log.info("candidateUser:{}",((IdentityLink)delegateTask.getCandidates().toArray()[0]).getUserId());
        }
        log.info("taskdefId:{}",delegateTask.getTaskDefinitionKey());
        log.info("createTime:{}",delegateTask.getCreateTime());
        log.info("ownner:{}",delegateTask.getOwner());
        log.info("assignee:{}",delegateTask.getAssignee());
        log.info("taskName:{}",delegateTask.getName());
        log.info("processInstanceId:{}",delegateTask.getProcessInstanceId());
    }

    /**
     * 公共字段处理
     * @param delegateTask
     * @param wfProcessLog
     * @param wfProcess
     */
    private void updateWfProcessLog(DelegateTask delegateTask, WfProcessLog wfProcessLog, WfProcess wfProcess){
        wfProcessLog.setUpdateTime(LocalDateTime.now());
        wfProcessLog.setEndTime(LocalDateTime.now());
        setDescriptions(delegateTask,wfProcessLog);
        if(ObjectUtil.isNotEmpty(wfProcessLog.getDecision()) && wfProcessLog.getDecision() == 0){
            wfProcessLog.setType(WfProcessLogEnum.REJECT.getType());
        }
        wfProcessLogService.update(wfProcessLog);
        log.info("wfProcessLog保存成功！");
    }
}
