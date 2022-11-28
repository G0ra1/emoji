package com.netwisd.base.wf.event.delegate;

import cn.hutool.core.util.ObjectUtil;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.entity.WfDoneTask;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.service.runtime.WfDoneTaskService;
import com.netwisd.base.wf.service.runtime.WfMyInDuplicateTaskService;
import com.netwisd.base.wf.service.runtime.WfMyOutDuplicateTaskService;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 流程实例更新 exection listener的 end事件中触发
 * @Author: zouliming@netwisd.com
 * @Date: 2020/8/7 9:15 上午
 */
@Slf4j
@Component
public class ProcessStateDelegate implements ExecutionListener {

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    WfMyInDuplicateTaskService wfMyInDuplicateTaskService;

    @Autowired
    WfMyOutDuplicateTaskService wfMyOutDuplicateTaskService;

    @Autowired
    WfDoneTaskService wfDoneTaskService;

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {
        String processInstanceId = delegateExecution.getProcessInstanceId();
        WfProcess wfProcess = wfProcessService.get(processInstanceId);
        Execution execution = runtimeService.createExecutionQuery().executionId(delegateExecution.getId()).singleResult();
        boolean ended = execution.isEnded();
        if(ObjectUtil.isNotEmpty(wfProcess) && ended){
            log.info("流程:{} 完成...",wfProcess.getCamundaProcinsId());
            wfProcess.setState(WfProcessEnum.DONE.getType());
            wfProcess.setCurrentActivityAssignee(null);
            wfProcess.setCurrentActivityAssigneeName(null);
            wfProcess.setCurrentActivityId(null);
            wfProcess.setEndTime(LocalDateTime.now());
            wfProcess.setCurrentActivityName("结束");
            //修改流程实例状态
            wfProcessService.update(wfProcess);

            //把所有已办列表中的当前办理人也更新一下——可以做成异步；
            List<WfDoneTask> newWfDoneTaskList = new ArrayList();
            List<WfDoneTask> list = wfDoneTaskService.getList(wfProcess.getId());
            if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
                list.forEach(wfDoneTask -> {
                    wfDoneTask.setCurrentActivityAssignee(null);
                    wfDoneTask.setCurrentActivityAssigneeName(null);
                    wfDoneTask.setCurrentActivityId(null);
                    wfDoneTask.setCurrentActivityName(null);
                    newWfDoneTaskList.add(wfDoneTask);
                });
            }
            wfDoneTaskService.updateBatchById(newWfDoneTaskList);

            //修改当前流程实例所有已办中的流程状态
            wfDoneTaskService.updateStateByProInsId(processInstanceId,WfProcessEnum.DONE.getType());
            //修改我收到所有传阅中的流程状态
            wfMyInDuplicateTaskService.updateStateByProInsId(processInstanceId, WfProcessEnum.DONE.getType());
            //修改我发起的所有传阅中的流程状态
            wfMyOutDuplicateTaskService.updateStateByProInsId(processInstanceId,WfProcessEnum.DONE.getType());
        }
    }
}
