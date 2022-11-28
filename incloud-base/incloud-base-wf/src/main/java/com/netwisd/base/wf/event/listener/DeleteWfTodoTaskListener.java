package com.netwisd.base.wf.event.listener;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.wf.constants.NodeTypeEnum;
import com.netwisd.base.wf.service.procdef.WfNodeDefService;
import com.netwisd.base.wf.service.runtime.WfProcessLogService;
import com.netwisd.base.wf.service.runtime.WfTodoTaskService;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description: 删除wfTodoTask，正常情况下业务会删除
 * 此功能主要是针对一些特殊的情况做处理：比如会签有通过率时，通过后自动删除userTask，然后触发相应的事件删除todoTask
 * 注意：这个事件配置为delete事件，目前来讲适用于会签节点，有以下几种情况会触发
 * 1.当会签不通过时，会自动驳回到上个节点，这时会触发这个事件；
 * 2.当会签通过率不是100%，但是会签通过时，因为会触发camunda删除还未审批的节点（比如：通过率50%，有两个人，一个人通过了，那么另一个人没必要审批了，就删掉他的待办），这时会触发这个事件；
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/30 12:23 下午
 */
@Slf4j
@Component
public class DeleteWfTodoTaskListener implements TaskListener {

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    WfNodeDefService wfNodeDefService;

    @Autowired
    CurrentActSthListener currentActSthListener;

    /**
     * 给定要清除的变量的名称表达式
     * 比如：wf_decisionInstances，这个值我们要在过程变量中清掉
     * 也可以是多个，用,分隔
     */
    private Expression variableExpression;

    /**
     * 普通节点如果调用类似于taskService的cancel操作时，也会调用这个方法，不过在wfEngineService中已经处理了删除待办，这里可能会再调用删一次，当然可能就不会删，不会影响什么；
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        String taskId = delegateTask.getId();
        Boolean delete = wfTodoTaskService.delete(taskId);
        log.info("待办任务删除,ID为：{}，删除是否成功：{}",taskId,delete);
        if(delete){
            wfProcessLogService.delWfTodoTaskByProInsAndTaskId(delegateTask.getProcessInstanceId(),delegateTask.getId(),null);
            log.info("流程日志删除：{}",delete);
        }
        //这个东西是会签清除的事件用的，在上面说的两种情况下，如果进入此事件时，就清一下会签中的特殊变量，所以判断一下节点类型；
        String processDefinitionId = delegateTask.getProcessDefinitionId();
        String taskDefinitionKey = delegateTask.getTaskDefinitionKey();
        WfNodeDefVo wfNodeDefVo = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(processDefinitionId, taskDefinitionKey);
        if(ObjectUtil.isEmpty(wfNodeDefVo)){
            throw new IncloudException("DeleteWfTodoTaskListener 通过processDefinitionId && taskDefinitionKey 找不到WfNodeDefVo！！！");
        }
        Integer nodeType = wfNodeDefVo.getNodeType();
        if(nodeType.intValue() == NodeTypeEnum.MULTIINSTANCETASK.code.intValue()){
            clear(delegateTask);
            currentActSthListener.handCurrentActSth(delegateTask);
        }
    }

    private void clear(DelegateTask delegateTask){
        String expression = variableExpression.getValue(delegateTask).toString();
        if(StrUtil.isNotEmpty(expression)){
            List<String> split = StrUtil.split((CharSequence) expression, ",");
            for (String key:split){
                delegateTask.removeVariable(key);
                log.info("VariableClearListener 移除过程变量：{}",key);
            }
        }else {
            log.error("VariableClearListener 参数错误，请检查！");
        }
    }
}
