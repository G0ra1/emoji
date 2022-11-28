package com.netwisd.base.wf.event.listener;

import cn.hutool.core.util.ObjectUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.wf.constants.*;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.feign.UserClient;
import com.netwisd.base.wf.service.*;
import com.netwisd.base.wf.service.procdef.WfNodeDefService;
import com.netwisd.base.wf.service.runtime.WfDoneTaskService;
import com.netwisd.base.wf.service.runtime.WfProcessLogService;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.base.wf.service.runtime.WfTodoTaskService;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.task.IdentityLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @Description: 待办任务监听事件处理
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/3 10:37 上午
 */
@Slf4j
@Component
public class WfTodoTaskListener implements TaskListener {

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    WfDoneTaskService wfDoneTaskService;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    WfNodeDefService wfNodeDefService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    Mapper dozerMapper;

    @Autowired
    WfProcdefService wfProcdefService;

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> incloudCache;

    @Override
    public void notify(DelegateTask delegateTask) {
        logInfo(delegateTask);
        WfTodoTask wfTodoTask = wfTodoTaskService.get(delegateTask.getId());
        WfProcess wfProcess = wfProcessService.get(delegateTask.getProcessInstanceId());
        if(ObjectUtil.isEmpty(wfTodoTask)){
            if(ObjectUtil.isNotEmpty(wfProcess)){
                wfTodoTask = initWfTodoTask(delegateTask,wfProcess);
                updateProcess(delegateTask,wfProcess);
                initProcessLog(delegateTask,wfTodoTask,wfProcess);
            }
        }else {
            //理论上不会存在这种情况
            if(ObjectUtil.isNotEmpty(wfProcess)){
                updateWfTodoTask(wfTodoTask,delegateTask,wfProcess);
            }
        }
    }

    private void updateProcess(DelegateTask delegateTask,WfProcess wfProcess){
        if(ObjectUtil.isNotEmpty(wfProcess)){
            //当前办理人现在不设置，放到签收时，因为涉及到公办、会签等 特殊操作
            wfProcess.setCurrentActivityId(delegateTask.getTaskDefinitionKey());
            wfProcess.setCurrentActivityName(delegateTask.getName());
            //把所有已办里里的当前当前节点也更新一下；
            List<WfDoneTask> list = wfDoneTaskService.getList(wfProcess.getId());
            List<WfDoneTask> newWfDoneTaskList = new ArrayList();
            if(ObjectUtil.isNotEmpty(list)){
                list.forEach(wfDoneTask -> {
                    wfDoneTask.setCurrentActivityId(delegateTask.getTaskDefinitionKey());
                    wfDoneTask.setCurrentActivityName(delegateTask.getName());
                    newWfDoneTaskList.add(wfDoneTask);
                });
            }
            wfDoneTaskService.updateBatchById(newWfDoneTaskList);
        }
        wfProcessService.saveOrUpdate(wfProcess);
    }

    /**
     * 创建用户任务时
     * @param delegateTask
     */
    private WfTodoTask initWfTodoTask(DelegateTask delegateTask,WfProcess wfProcess){
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(delegateTask.getProcessDefinitionId()).singleResult();
        String activityInstanceId = delegateTask.getExecution().getActivityInstanceId();
        String currentActivityId = delegateTask.getExecution().getCurrentActivityId();
        log.info("activityInstanceId：{},currentActivityId：{}",activityInstanceId,currentActivityId);
        StringBuffer sb = new StringBuffer();
        WfTodoTask wfTodoTask = new WfTodoTask();
        dozerMapper.map(wfProcess,wfTodoTask);
        wfTodoTask.setId(null);
        wfTodoTask.setCamundaExectionId(delegateTask.getExecutionId());
        wfTodoTask.setCamundaProcdefId(delegateTask.getProcessDefinitionId());
        wfTodoTask.setCamundaProcdefKey(processDefinition.getKey());
        wfTodoTask.setCamundaProcdefVersion(processDefinition.getVersion());
        wfTodoTask.setCamundaProcinsId(delegateTask.getProcessInstanceId());
        wfTodoTask.setCamundaTaskId(delegateTask.getId());
        wfTodoTask.setCamundaActInsId(activityInstanceId);
        wfTodoTask.setCliamTime(null);
        wfTodoTask.setProcinsId(wfProcess.getId());
        wfTodoTask.setAssignee(delegateTask.getAssignee());
        Object draftObj = runtimeService.getVariable(delegateTask.getExecutionId(), InnerVariableEnum.DRAFT.getName());
        wfTodoTask.setIsDraft(ObjectUtil.isNotEmpty(draftObj) ? (Integer) draftObj : BooleanEnum.FALSE.getValue());
        //WfProcDef wfProcDef = wfProcdefService.getProcDefByCamundaProcdefId(wfProcess.getCamundaProcdefId());
        //wfTodoTask.setFormKey(wfProcDef.getFormKey());
        setCommonVar(delegateTask,wfTodoTask,wfProcess);
        WfNodeDefVo wfNodeDefVo = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(delegateTask.getProcessDefinitionId(), delegateTask.getTaskDefinitionKey());
        if(ObjectUtil.isEmpty(wfNodeDefVo)){
            throw new IncloudException("找不到流程定义中的节点信息");
        }
        wfTodoTask.setNodeType(wfNodeDefVo.getNodeType());
        Set<IdentityLink> candidates = delegateTask.getCandidates();
        if(ObjectUtil.isNotEmpty(candidates) && candidates.size() > 0){
            for(IdentityLink identityLink :candidates){
                sb.append(identityLink.getUserId()).append(",");
            }
        }
        wfTodoTask.setCandidates(sb.substring(0,sb.length()-1));
        wfTodoTaskService.save(wfTodoTask);
        log.info("事件保存待办任务成功！");
        return wfTodoTask;
    }

    /**
     * 公共字段处理
     * @param delegateTask
     * @param wfTodoTask
     * @param wfProcess
     */
    private void setCommonVar(DelegateTask delegateTask,WfTodoTask wfTodoTask,WfProcess wfProcess){
        if(ObjectUtil.isNotEmpty(delegateTask.getDueDate())){
            wfTodoTask.setDueDate(LocalDateTime.ofInstant(delegateTask.getDueDate().toInstant(), ZoneId.systemDefault()));
        }
        wfTodoTask.setNodeKey(delegateTask.getTaskDefinitionKey());
        wfTodoTask.setNodeName(delegateTask.getName());
        wfTodoTask.setOwnner(delegateTask.getOwner());
        wfTodoTask.setPriority(delegateTask.getPriority());
        wfTodoTask.setState(wfProcess.getState());
        wfTodoTask.setTaskName(delegateTask.getName());
    }


    /**
     * 更新用户任务时
     * @param wfTodoTask
     * @param delegateTask
     */
    private void updateWfTodoTask(WfTodoTask wfTodoTask,DelegateTask delegateTask,WfProcess wfProcess){
        setCommonVar(delegateTask,wfTodoTask,wfProcess);
        wfTodoTaskService.update(wfTodoTask);
        log.info("事件修改待办任务成功！");
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

    private void initProcessLog(DelegateTask delegateTask, WfTodoTask wfTodoTask, WfProcess wfProcess){
        WfProcessLog wfProcessLog = new WfProcessLog();
        dozerMapper.map(wfTodoTask,wfProcessLog);
        String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
        WfNodeDefVo entity = wfNodeDefService.getEntity(delegateTask.getProcessDefinitionId(), taskDefinitionKey);
        if(ObjectUtil.isEmpty(entity)){
            throw new IncloudException("通过流程定义id和节点key获取不到相应的节点定义信息");
        }
        List<Object> userNameList = new ArrayList<>();
        Set<IdentityLink> set = delegateTask.getCandidates();
        for (IdentityLink identityLink : set){
            String userId = identityLink.getUserId();
            userNameList.add(userId);
        }
        List<MdmUserVo> users = incloudCache.gets(userNameList);

        if(ObjectUtil.isNotEmpty(users) && !users.isEmpty()){
            StringBuffer userNames = new StringBuffer();
            StringBuffer userNameChs = new StringBuffer();
            StringBuffer deptIds = new StringBuffer();
            StringBuffer deptNames = new StringBuffer();
            StringBuffer orgIds = new StringBuffer();
            StringBuffer orgNames = new StringBuffer();
            for (MdmUserVo mdmUserVo: users){
                userNames.append(mdmUserVo.getUserName()).append(",");
                userNameChs.append(mdmUserVo.getUserNameCh()).append(",");
                deptIds.append(mdmUserVo.getParentDeptId()).append(",");
                deptNames.append(mdmUserVo.getParentDeptName()).append(",");
                orgIds.append(mdmUserVo.getParentOrgId()).append(",");
                orgNames.append(mdmUserVo.getParentOrgName()).append(",");
            }
            wfProcessLog.setUserName(userNames.substring(0,userNames.length()-1));
            wfProcessLog.setUserName(userNames.substring(0,userNames.length()-1));
            wfProcessLog.setDeptId(deptIds.substring(0,deptIds.length()-1));
            wfProcessLog.setDeptName(deptNames.substring(0,deptNames.length()-1));
            wfProcessLog.setOrgId(orgIds.substring(0,orgIds.length()-1));
            wfProcessLog.setOrgName(orgNames.substring(0,orgNames.length()-1));
        }

        wfProcessLog.setId(null);
        wfProcessLog.setNodeId(wfTodoTask.getNodeKey());
        wfProcessLog.setStartTime(LocalDateTime.now());
        wfProcessLog.setCreateTime(LocalDateTime.now());
        wfProcessLog.setType(WfProcessLogEnum.NONE.getType());
        wfProcessLog.setCamundaCallActivityDefId(wfProcess.getCamundaCallActivityDefId());
        wfProcessLog.setCamundaParentActInsId(wfProcess.getCamundaParentActInsId());
        wfProcessLog.setClaimStatus(ClaimStatusEnum.UNCLAIMED.getType());
        wfProcessLog.setCamundaProcinsId(wfProcess.getCamundaProcinsId());
        wfProcessLog.setProcinsId(wfProcess.getId());
        wfProcessLog.setCamundaTaskId(delegateTask.getId());
        wfProcessLogService.save(wfProcessLog);
        log.info("wfProcessLog保存成功！");
    }
}