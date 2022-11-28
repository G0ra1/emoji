package com.netwisd.base.wf.event.delegate;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.netwisd.base.common.user.vo.expression.UserExpressionVO;
import com.netwisd.base.wf.constants.*;
import com.netwisd.base.wf.entity.WfExpreUserDef;
import com.netwisd.base.wf.entity.WfProcessLog;
import com.netwisd.base.wf.expression.WfConditionExpression;
import com.netwisd.base.wf.expression.WfUserExpression;
import com.netwisd.base.wf.service.procdef.WfExpreUserDefService;
import com.netwisd.base.wf.service.runtime.WfProcessLogService;
import com.netwisd.base.wf.starter.constants.SubmitTypeEnum;
import com.netwisd.base.wf.util.FlowUtils;
import com.netwisd.base.wf.vo.WfNextUserVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 外链式子流程的结束事件——用户处理子流程或多实例子流程结束后的下一步人员获取和assignee赋值；
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/27 1:13 下午
 */
@Slf4j
@Component
public class CallActivityEndDelegate implements ExecutionListener {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    WfConditionExpression wfConditionExpression;

    @Autowired
    WfUserExpression wfUserExpression;

    @Autowired
    WfExpreUserDefService wfExpreUserDefService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        /**
         * 特别要说明：
         * 当callActivity为单实例时跟多实例时，调用当事件的机制是完全不同的；
         * 为什么取execution.getProcessInstanceId()而不是直接用execution的id呢?
         * 原因是：如果对于多实例的情况来说：最后一个实例完成后才会调到这个事件————注意：不是每个实例走完都会调的啊。。
         * 那么当前的execution指的就是最后一个实例的exection了；如果这里拿这个exectionid获取到的variables，指的其实是这个执行流上的，或者说是分支上的局部变量；
         * 而不是当前这个流程实例的；只有操作当前实例的全局变量才会影响他后面节点的wfAssignee的值，才会让他正确赋值给下个节点；
         */
        String processInstanceId = execution.getProcessInstanceId();
        Map<String, Object> variables = runtimeService.getVariables(processInstanceId);
        List<WfNextUserVo> nextUser = getNextUser(execution,variables);
        /**
         * 说明下为什么只取第一个：
         * 集合返回多个的情况一般有三种情况：
         * 1.当前节点为被驳回节点，提交的时候，可以选择正常提交节点的用户；也可以选择被驳回节点的用户；故尔会有多个的情况
         * 2.当下一步流转的是一个网关，会有多个条件满足时，会有多个节点人员满足；也就是多条件手动选择（或对应老工作流的多路由）
         * 不过，这种情况目前还暂不支持，如果有多条件满足，会报错；
         * 3.并行网关，这种业务场景目前没有，而且真太复杂，目前工作流暂不支持；
         */
        if(ObjectUtil.isNotEmpty(nextUser) && nextUser.size()>1){
            throw new IncloudException("同时有多组条件表达式满足条件，请检查流程定义配置！");
        }
        if(ObjectUtil.isEmpty(nextUser)){
            throw new IncloudException("没有下一步节点配置！");
        }
        WfNextUserVo wfNextUserVo = nextUser.get(0);
        List<String> assigennList = new ArrayList();

        for (UserExpressionVO userExpressionVO: wfNextUserVo.getUserList()){
            assigennList.add(userExpressionVO.getUserName());
        }
        variables.put(InnerVariableEnum.ASSIGNEE.getName(),assigennList);
        runtimeService.setVariables(processInstanceId,variables);

        log.info("CallActivityEndDelegate的提交用户为：{}",assigennList);

        WfProcessLog wfProcessLog = wfProcessLogService.getByCurrentActInsId(execution.getActivityInstanceId(), WfProcessLogEnum.NONE.getType());
        if(ObjectUtil.isEmpty(wfProcessLog)){
            log.error("通过execution.getActivityInstanceId()没有查找到wfProcessLog！！！");
            throw new IncloudException("通过execution.getActivityInstanceId()没有查找到wfProcessLog！！！");
        }
        updateWfProcessLog(wfProcessLog,wfNextUserVo,assigennList);

    }

    public List<WfNextUserVo> getNextUser(DelegateExecution execution,Map<String, Object> variables) {
        String processId = execution.getProcessInstanceId();
        Map<String, Object> processVariables = new HashMap<>();
        log.info("原过程变量为：{}", processVariables);
        processVariables.putAll(variables);
        log.info("用户操作后过程变量为：{}", processVariables);
        //log.info("用户：{}，任务id：{} ,任务名称：{}，任务流程id:{}", userId, taskId, task.getName(), processId);
        List<UserExpressionVO> userList = new ArrayList();
        List<WfNextUserVo> wfNextUserVoList = new ArrayList();
        WfNextUserVo wfNextUserVo = new WfNextUserVo();
        wfNextUserVo.setUserList(userList);
        wfNextUserVoList.add(wfNextUserVo);
        String processDefinitionId = execution.getProcessDefinitionId();
        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance(processDefinitionId);

        //用于表达式计算用的两个内置表达式变量
        processVariables.put(InnerVariableEnum.CONDITIONEXPRESSION.getName(),wfConditionExpression);
        processVariables.put(InnerVariableEnum.USEREXPRESSION.getName(),wfUserExpression);

        List<FlowNode> nextFlowNode = FlowUtils.getNextUserTasks(bpmnModelInstance, execution.getCurrentActivityId(), processVariables, FlowNodeTypeEnum.USER_TASK.getType());

        //理论上如果是排它网关的话，会只有一个元素,当然如果同时两个条件都满足也是可以的，
        // 但由于camunda排他网关的特性，导致他只能默认选则第一个为true的序列流去执行。顾。我们限定只能出现一个满足条件的，否则报错；
        if(ObjectUtil.isNotEmpty(nextFlowNode) && nextFlowNode.size()>1){
            throw new IncloudException("同时有多组条件表达式满足条件，请检查流程定义配置！");
        }
        if(nextFlowNode.isEmpty()){
            return wfNextUserVoList;
        }
        FlowNode flowNode = nextFlowNode.get(0);
        if(flowNode instanceof EndEvent){
            EndEvent endEvent = (EndEvent) flowNode;
            wfNextUserVo.setNextCamundaNodeId(endEvent.getId());
            wfNextUserVo.setNextCamundaNodeName(endEvent.getName());
            wfNextUserVo.setNextcamundaNodeType(NodeTypeEnum.ENDEVENT.code);
            wfNextUserVo.setSubmitType(SubmitTypeEnum.SUBMIT.getType());
            return wfNextUserVoList;
        }else if(flowNode instanceof UserTask){
            log.info("UserTask");
            UserTask userTask = (UserTask)flowNode;
            //说明是多实例用户任务——即：会签节点
            if(ObjectUtil.isNotEmpty(userTask.getLoopCharacteristics())){
                wfNextUserVo.setNextcamundaNodeType(NodeTypeEnum.MULTIINSTANCETASK.code);
            }else {
                wfNextUserVo.setNextcamundaNodeType(NodeTypeEnum.USERTASK.code);
            }
        }else if(flowNode instanceof CallActivity){
            log.info("CallActivity");
            CallActivity callActivity = (CallActivity)flowNode;
            //说明是多实例callActivity
            if(ObjectUtil.isNotEmpty(callActivity.getLoopCharacteristics())){
                wfNextUserVo.setNextcamundaNodeType(NodeTypeEnum.MULTIINSTANCECALLACTIVITYS.code);
            }else {
                wfNextUserVo.setNextcamundaNodeType(NodeTypeEnum.CALLACTIVITY.code);
            }
        }else if(flowNode instanceof SubProcess){
            log.info("SubProcess");
            SubProcess subProcess = (SubProcess)flowNode;
            //说明是多实例callActivity
            if(ObjectUtil.isNotEmpty(subProcess.getLoopCharacteristics())){
                wfNextUserVo.setNextcamundaNodeType(NodeTypeEnum.MULTIINSTANCESUBPROCESS.code);
            }else {
                wfNextUserVo.setNextcamundaNodeType(NodeTypeEnum.SUBPROCESS.code);
            }
        }else {
            throw new IncloudException("获取下一步flowNode出错！");
        }

        //这儿注意，直接在candidateUser和assginee中同时放值，会有问题，实际情况下，我们还是自己单独存，存取的表达式可以是集合和更复杂的数据结构，不放在bnmn20.xml文件中
        String key = flowNode.getId();
        String name = flowNode.getName();
        //通过流程定义ID和节点定义ID获取表达式列表
        List<WfExpreUserDef> wfExpreUserList = wfExpreUserDefService.getExpreByProcDefIdAndNodeDefId(processDefinitionId, key);
        if(ObjectUtil.isNotEmpty(wfExpreUserList)){
            wfExpreUserList.forEach(wfExpreUserDefVo -> {
                String expression = wfExpreUserDefVo.getExpression();
                log.info("获取的表达式:{}",expression);
                String expressionResult = StrUtil.subBetween(expression, "${", "}");
                log.info("处理的表达式:{}",expressionResult);
                List<UserExpressionVO> result = FlowUtils.getResult(processVariables, expressionResult);
                if(ObjectUtil.isNotEmpty(result)){
                    log.info("获取到解析后的用户列表:{}", JSONUtil.toJsonStr(result));
                    userList.addAll(result);
                }
            });
        }
        wfNextUserVo.setNextCamundaNodeId(key);
        wfNextUserVo.setNextCamundaNodeName(name);
        wfNextUserVo.setSubmitType(SubmitTypeEnum.SUBMIT.getType());

        processVariables.remove(InnerVariableEnum.CONDITIONEXPRESSION.getName());
        processVariables.remove(InnerVariableEnum.USEREXPRESSION.getName());
        log.info("最终用户列表:{}", JSONUtil.toJsonStr(userList));

        return wfNextUserVoList;
    }

    private BpmnModelInstance getBpmnModelInstance(String processDefinitionId){
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);
        return bpmnModelInstance;
    }

    /**
     * 更新流程日志
     * @param wfProcessLog
     * @param wfNextUserVo
     * @param assigennList
     */
    private void updateWfProcessLog(WfProcessLog wfProcessLog, WfNextUserVo wfNextUserVo, List<String> assigennList){
        wfProcessLog.setUpdateTime(LocalDateTime.now());
        wfProcessLog.setEndTime(LocalDateTime.now());
        wfProcessLog.setTargetNodeId(wfNextUserVo.getNextCamundaNodeId());
        wfProcessLog.setTargetNodeName(wfNextUserVo.getNextCamundaNodeName());
        wfProcessLog.setTargetNodeType(wfNextUserVo.getNextcamundaNodeType());
        wfProcessLog.setType(wfProcessLog.getDecision());
        wfProcessLog.setDecision(WfProcessLogEnum.SUBMIT.getType());
        wfProcessLog.setDescription(WfProcessLogEnum.SUBMIT.getName());
        wfProcessLog.setIsAgree(BooleanEnum.TRUE.getValue());
        wfProcessLogService.update(wfProcessLog);
        log.info("wfProcessLog保存成功！");
    }
}
