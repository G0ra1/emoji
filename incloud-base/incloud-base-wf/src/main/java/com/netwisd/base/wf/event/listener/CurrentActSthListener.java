package com.netwisd.base.wf.event.listener;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.wf.constants.InnerVariableEnum;
import com.netwisd.base.wf.entity.WfDoneTask;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.service.runtime.WfDoneTaskService;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 处理会签的当前办理人清除
 * 当前事件使用complete事件来做，并做了限定，只处理会签通过率为100%，并且全部通过的情况；
 * 其他情况由DeleteWfTodoTaskListener事件来处理
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/1 3:51 下午
 */
@Slf4j
@Component
public class CurrentActSthListener implements TaskListener {

    //实例总数
    private static final String nrOfInstances = "nrOfInstances";
    //已经完成实例的数目。
    private static final String nrOfCompletedInstances = "nrOfCompletedInstances";
    //当前活动的，比如，还没完成的实例数量。 对于顺序执行的多实例，值一直为1。
    private static final String nrOfActiveInstances = "nrOfActiveInstances";

    @Autowired
    WfDoneTaskService wfDoneTaskService;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    RuntimeService runtimeService;

    @Override
    public void notify(DelegateTask delegateTask) {
        Object nrOfInstancesObj = runtimeService.getVariable(delegateTask.getExecutionId(), nrOfInstances);
        Object wfDecisionInstancesObj = runtimeService.getVariable(delegateTask.getExecutionId(), InnerVariableEnum.DECISION_INSTANCES.getName());
        if(ObjectUtil.isNotEmpty(nrOfInstancesObj) && ObjectUtil.isNotEmpty(wfDecisionInstancesObj)){
            int nrOfInstancesValue = (int)nrOfInstancesObj;
            int wfDecisionInstancesValue = (int)wfDecisionInstancesObj;
            log.info("nrOfInstancesValue:{},wfDecisionInstancesValue:{}",nrOfInstancesValue,wfDecisionInstancesValue);
            //上面注解说了，只处理会签通过率为100%，并且全部通过的情况；
            if(nrOfInstancesValue == wfDecisionInstancesValue){
                log.info("-------------VariableClearListener 会签100%通过率全通过 清除变量事件！----------------------");
                handCurrentActSth(delegateTask);
            }
        }
    }

    /**
     * 11.9新加逻辑，利用这个事件，处理下当前办理人的功能
     * @param delegateTask
     */
    public void handCurrentActSth(DelegateTask delegateTask){
        String camundaProcinsId = delegateTask.getProcessInstanceId();
        WfProcess wfProcess = wfProcessService.get(camundaProcinsId);
        if(ObjectUtil.isEmpty(wfProcess)){
            throw new IncloudException("VariableClearListener  根据camundaProcinsId找不到WfProcess！！！");
        }
        //不为空的时候处理，为空的时候跳过不处理
        if(StrUtil.isNotEmpty(wfProcess.getCurrentActivityAssignee())){
            wfProcess.setCurrentActivityAssignee(null);
            wfProcess.setCurrentActivityAssigneeName(null);
            wfProcessService.update(wfProcess);
            //把所有已办列表中的当前办理人也更新一下——可以做成异步；
            List<WfDoneTask> newWfDoneTaskList = new ArrayList();
            List<WfDoneTask> list = wfDoneTaskService.getList(wfProcess.getId());
            if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
                list.forEach(wfDoneTask -> {
                    wfDoneTask.setCurrentActivityAssignee(null);
                    wfDoneTask.setCurrentActivityAssigneeName(null);
                    newWfDoneTaskList.add(wfDoneTask);
                });
            }
            wfDoneTaskService.updateBatchById(newWfDoneTaskList);
        }
    }
}
