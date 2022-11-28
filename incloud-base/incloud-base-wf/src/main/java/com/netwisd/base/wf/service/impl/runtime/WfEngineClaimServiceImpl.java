package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.wf.constants.ClaimStatusEnum;
import com.netwisd.base.wf.constants.NodeTypeEnum;
import com.netwisd.base.wf.constants.WfProcessLogEnum;
import com.netwisd.base.wf.entity.WfDoneTask;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.entity.WfProcessLog;
import com.netwisd.base.wf.entity.WfTodoTask;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskAlreadyClaimedException;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zouliming@netwisd.com
 * @Description: 签收相关service
 * @date 2021/12/3 11:51
 */
@Service
@Slf4j
public class WfEngineClaimServiceImpl implements WfEngineClaimService {

    @Autowired
    WfTaskService wfTaskService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    WfDoneTaskService wfDoneTaskService;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    TaskService taskService;

    @Autowired
    WfEngineCheckService wfEngineCheckService;

    @Override
    public WfTodoTask claimProcess(String taskId) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Task task = wfTaskService.getAndCheck(taskId);
        wfEngineCheckService.checkProcessState(task.getProcessInstanceId());
        WfTodoTask wfTodoTask = updateWfTodo(loginAppUser, task);
        //签收时，更新流程日志
        WfProcessLog wfProcessLog = wfProcessLogService.get(taskId, WfProcessLogEnum.NONE.getType());
        updateClaimInfoInCurrentProcess(wfProcessLog,loginAppUser);
        //把流程实例中当前办理人也更新一下——可以做成异步；
        WfProcess wfProcess = updateAssigneeInCurrentProcess(wfProcessLog, loginAppUser, wfTodoTask);
        //把所有已办列表中的当前办理人也更新一下——可以做成异步；
        updateAssigneeInCurrentWfDoneTask(wfProcess);
        try {
            taskService.claim(taskId,loginAppUser.getUserName());
        }catch (TaskAlreadyClaimedException e){
            throw new IncloudException("此任务为公办任务，已经被其他人签收！");
        }
        return wfTodoTask;
    }

    WfTodoTask updateWfTodo(LoginAppUser loginAppUser,Task task){
        WfTodoTask wfTodoTask = wfTodoTaskService.getAndCheck(task.getId());
        //如果办理人已经有值，说明已经签收，返回不处理就行
        if(StrUtil.isNotEmpty(task.getAssignee())){
            return wfTodoTask;
        }
        wfTodoTask.setAssignee(loginAppUser.getUserName());
        wfTodoTask.setAssigneeName(loginAppUser.getUserNameCh());
        //把候选人也改成你自己，这样查询待办列表时，查的就是这个字段
        wfTodoTask.setCandidates(loginAppUser.getUserName());
        wfTodoTask.setCliamTime(LocalDateTime.now());
        wfTodoTaskService.update(wfTodoTask);
        return wfTodoTask;
    }

    void updateAssigneeInCurrentWfDoneTask(WfProcess wfProcess){
        List<WfDoneTask> lists = wfDoneTaskService.getList(wfProcess.getId());
        List<WfDoneTask> newWfDoneTaskList = new ArrayList();
        if(ObjectUtil.isNotEmpty(lists)){
            lists.forEach(wfDoneTask -> {
                wfDoneTask.setCurrentActivityAssignee(wfProcess.getCurrentActivityAssignee());
                wfDoneTask.setCurrentActivityAssigneeName(wfProcess.getCurrentActivityAssigneeName());
                newWfDoneTaskList.add(wfDoneTask);
            });
        }
        wfDoneTaskService.updateBatchById(newWfDoneTaskList);
    }

    //claim之后，camunda的task会把assginee赋值，并清除掉candidate中相应的其他人；
    WfProcess updateAssigneeInCurrentProcess(WfProcessLog wfProcessLog,LoginAppUser loginAppUser,WfTodoTask wfTodoTask){
        WfProcess wfProcess = wfProcessService.getById(wfTodoTask.getProcinsId());
        String currentActivityAssignee = wfProcess.getCurrentActivityAssignee();
        String currentActivityAssigneeName = wfProcess.getCurrentActivityAssigneeName();
        Integer nodeType = wfProcessLog.getNodeType();
        if(StrUtil.isEmpty(currentActivityAssignee) || StrUtil.isEmpty(currentActivityAssigneeName)
                //只要不是会签任务，谁签收，当前办理节点的办理人就是谁
                || nodeType.intValue() != NodeTypeEnum.MULTIINSTANCETASK.code.intValue()){
            wfProcess.setCurrentActivityAssignee(loginAppUser.getUserName());
            wfProcess.setCurrentActivityAssigneeName(loginAppUser.getUserNameCh());
        }else {
            List<String> strings = Arrays.asList(currentActivityAssignee.split(","));
            if(ObjectUtil.isNotEmpty(strings) && !strings.isEmpty()){
                if(!strings.contains(loginAppUser.getUserName())){
                    wfProcess.setCurrentActivityAssignee(currentActivityAssignee+","+loginAppUser.getUserName());
                    wfProcess.setCurrentActivityAssigneeName(currentActivityAssigneeName+","+loginAppUser.getUserNameCh());
                }
            }
        }
        wfProcessService.update(wfProcess);
        return wfProcess;
    }

    void updateClaimInfoInCurrentProcess(WfProcessLog wfProcessLog,LoginAppUser loginAppUser){
        if(ObjectUtil.isNotEmpty(wfProcessLog) ){
            //把流程日志的人员组织信息也更新一下
            setProcessUserAndOrgInfo(wfProcessLog,loginAppUser);
            wfProcessLog.setClaimStatus(ClaimStatusEnum.CLAIMED.getType());
            wfProcessLog.setClaimTime(LocalDateTime.now());
            wfProcessLogService.update(wfProcessLog);
            log.info("wfProcessLog签收人员更新成功！");
        }
    }

    void setProcessUserAndOrgInfo(WfProcessLog wfProcessLog,LoginAppUser loginAppUser){
        wfProcessLog.setUserName(loginAppUser.getUserName());
        wfProcessLog.setUserNameCh(loginAppUser.getUserNameCh());
        wfProcessLog.setDeptId(String.valueOf(loginAppUser.getParentDeptId()));
        wfProcessLog.setDeptName(loginAppUser.getParentDeptName());
        wfProcessLog.setOrgId(String.valueOf(loginAppUser.getParentOrgId()));
        wfProcessLog.setOrgName(loginAppUser.getParentOrgName());
    }
}
