package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.user.vo.expression.UserExpressionVO;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.wf.constants.*;
import com.netwisd.base.wf.entity.WfExpreUserDef;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.expression.WfConditionExpression;
import com.netwisd.base.wf.expression.WfUserExpression;
import com.netwisd.base.wf.service.procdef.WfExpreUserDefService;
import com.netwisd.base.wf.service.procdef.WfVarDefService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.starter.constants.SubmitTypeEnum;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.util.FlowUtils;
import com.netwisd.base.wf.util.VariableUtils;
import com.netwisd.base.wf.vo.WfNextUserVo;
import com.netwisd.base.wf.vo.WfProcessLogVo;
import com.netwisd.base.wf.vo.WfVarDefVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author zouliming@netwisd.com
 * @description 获取下一步人等相关的操作
 * @date 2021/12/13 9:49
 */
@Service
@Slf4j
public class WfEngineGetNetxImplService implements WfEngineGetNextService {
    @Autowired
    WfTaskService wfTaskService;

    @Autowired
    WfRuntimeService wfRuntimeService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    WfVarDefService wfVarDefService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    WfExpreUserDefService wfExpreUserDefService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> incloudCache;

    @Autowired
    Mapper dozerMapper;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    WfConditionExpression wfConditionExpression;

    @Autowired
    WfUserExpression wfUserExpression;

    /**
     * 获取提交的变量
     * @param taskId
     * @return
     */
    public Set<WfVarDefVo> getSubmitVariables(String taskId) {
        Task task = wfTaskService.getAndCheck(taskId);
        String processId = task.getProcessInstanceId();
        ProcessInstance processInstance = wfRuntimeService.singleResultAndCheck(processId);
        String processDefinitionId = processInstance.getProcessDefinitionId();
        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance(task);
        Collection<BpmnModelElementInstance> results = FlowUtils.getOutgoings(bpmnModelInstance, task);
        //获取当前节点的所有映射变量；
        //先获取当前节点对应的有没有变量映射，如果有变量映射，那么需要先把当前变量映射的值（来自于当前表单）先从当前表单中取到；
        List<WfVarDefVo> varByProcDefIdAndDefId = wfVarDefService.getVarByProcDefIdAndNodeDefId(processDefinitionId, task.getTaskDefinitionKey(),true);
        Set<WfVarDefVo> resultSet = getNextSequenceAndTaskVar(processDefinitionId,results,varByProcDefIdAndDefId);
        return resultSet;
    }

    /**
     * 获取符合条件的所有出线和任务的变量
     * @param processDefinitionId
     * @param results
     * @param currentNodeVarMappings
     * @return
     */
    Set<WfVarDefVo> getNextSequenceAndTaskVar(String processDefinitionId,Collection<BpmnModelElementInstance> results,List<WfVarDefVo> currentNodeVarMappings){
        Set<WfVarDefVo> resultSet = new LinkedHashSet<>();
        //获取符合条件的所有出线和任务的变量
        for(BpmnModelElementInstance flowNode : results){
            if(flowNode instanceof UserTask){
                UserTask userTask = (UserTask)flowNode;
                setWfVarDefVo(userTask,processDefinitionId,resultSet,currentNodeVarMappings);
            }
            if(flowNode instanceof SequenceFlow){
                SequenceFlow sequenceFlow = (SequenceFlow)flowNode;
                setWfVarDefVo(sequenceFlow,processDefinitionId,resultSet,currentNodeVarMappings);
            }
            if(flowNode instanceof CallActivity){
                CallActivity callActivity = (CallActivity)flowNode;
                setWfVarDefVo(callActivity,processDefinitionId,resultSet,currentNodeVarMappings);
            }
            if(flowNode instanceof SubProcess){
                SubProcess subProcess = (SubProcess)flowNode;
                setWfVarDefVo(subProcess,processDefinitionId,resultSet,currentNodeVarMappings);
            }
        }
        log.info("getSubmitVariables 获取的变量为：{}",JSONUtil.toJsonStr(resultSet));
        return resultSet;
    }

    /**
     * 根据流程定义ID和节点定义ID，获取到所有的变量，并放到set中；
     * @param flowNode
     * @param processDefinitionId
     * @param resultSet
     * @param currentNodeVarMappings
     */
    void setWfVarDefVo(BaseElement flowNode,String processDefinitionId,Set<WfVarDefVo> resultSet,List<WfVarDefVo> currentNodeVarMappings){
        String id = flowNode.getId();
        List<WfVarDefVo> varByProcDefIdAndDefId;
        if(flowNode instanceof SequenceFlow){
            varByProcDefIdAndDefId = wfVarDefService.getVarByProcDefIdAndSequDefId(processDefinitionId, id,null);
        }else {
            varByProcDefIdAndDefId = wfVarDefService.getVarByProcDefIdAndNodeDefId(processDefinitionId, id,null);
        }
        if(ObjectUtil.isNotEmpty(varByProcDefIdAndDefId) && !varByProcDefIdAndDefId.isEmpty()){
            varByProcDefIdAndDefId.forEach(wfVarDefVo -> {
                //如果包括，意味着，当前节点的映射变量，在下个节点或序列中也需要；说明需要从当前提交的表单中取最新的变量映射值；
                //如果不在的放，就不加上了，说明之前已经在全局variable存在过
                if(wfVarDefVo.getIsOrm().intValue() == YesNo.YES.getCode().intValue()){
                    if(currentNodeVarMappings.contains(wfVarDefVo.getExpreJavaName())){
                        resultSet.add(wfVarDefVo);
                    }
                }else {
                    resultSet.add(wfVarDefVo);
                }
            });
            log.info("getSubmitVariables ：{}",JSONUtil.toJsonStr(resultSet));
        }
    }

    /**
     * 根据业务表单数据，和变量key获取到相应的变量map结构
     * @param wfEngineDto
     */
    Map<String, Object> getVariableByBizData(WfEngineDto wfEngineDto){
        String executionId = wfTaskService.getAndCheck(wfEngineDto.getCamundaTaskId()).getProcessInstanceId();
        //获取所有提交前所需要所有变量——包括：条件表达式、人员表达式中的表单、变量映射所需要的变量；
        Set<WfVarDefVo> nextSequAndTaskVars = getSubmitVariables(wfEngineDto.getCamundaTaskId());
        //从当前提交表单中，获取到下个节点中需要的表单变量、和部分映射变量（当前节点映射了的，没有映射的已经在之前节点放入全局variable中了）
        Map<String,Object> variable = VariableUtils.getVariableByKeyAndEntityJson(nextSequAndTaskVars, wfEngineDto.getBizDataList());
        //获取下全局variable，并put进去刚加入的新变量；
        Map<String, Object> variables = runtimeService.getVariables(executionId);
        log.info("variables前:{}",variables);
        variables.putAll(variable);
        //返回的一个类型，并不是他的成员变量，所以需要重新set一次；
        // 这个会把包括人员表达式、条件表达式中所需要的所有变量都会放放variable中，这样的话；在submit中，就不再需要提交这些变量值了；
        runtimeService.setVariables(executionId,variables);
        log.info("variables后:{}",runtimeService.getVariables(executionId));
        return variable;
    }

    /**
     * 获取下一步人员列表
     * @param wfEngineDto
     * @return
     */
    @Override
    public List<WfNextUserVo> getNextUser(WfEngineDto wfEngineDto) {
        //处理下variable变量
        Map<String, Object> variables = getVariableByBizData(wfEngineDto);
        String taskId = wfEngineDto.getCamundaTaskId();
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUserAndCheck();
        variables.put(InnerVariableEnum.TASK_ID.getName(), taskId);
        Task task = wfTaskService.getAndCheckClaim(taskId);
        String processId = task.getProcessInstanceId();
        Map<String, Object> processVariables = wfRuntimeService.getVariables(task.getProcessInstanceId());
        log.info("原过程变量为：{}", processVariables);
        processVariables.putAll(variables);
        //用于表达式计算用的两个内置表达式变量
        processVariables.put(InnerVariableEnum.CONDITIONEXPRESSION.getName(),wfConditionExpression);
        processVariables.put(InnerVariableEnum.USEREXPRESSION.getName(),wfUserExpression);
        log.info("用户操作后过程变量为：{}", processVariables);
        log.info("用户：{}，任务id：{} ,任务名称：{}，任务流程id:{}", loginAppUser.getUserName(), taskId, task.getName(), processId);
        List<WfNextUserVo> wfNextUserVoList = new ArrayList();
        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance(task);
        /**
         * 这里注释说胆一下，processVariables里除了会放人员表达式相关的变量，同时也会放条件表达式的变量以及相应的value,value是
         */
        List<FlowNode> nextFlowNodes = FlowUtils.getNextUserTasks(bpmnModelInstance, task.getTaskDefinitionKey(), processVariables, FlowNodeTypeEnum.USER_TASK.getType());
        /*//理论上如果是排它网关的话，会只有一个元素,当然如果同时两个条件都满足也是可以的，
        // 但由于camunda排他网关的特性，导致他只能默认选则第一个为true的序列流去执行。顾。我们限定只能出现一个满足条件的，否则报错；
        if(ObjectUtil.isNotEmpty(nextFlowNode) && nextFlowNode.size()>1){
            throw new IncloudException("同时有多组条件表达式满足条件，请检查流程定义配置！");
        }*/
        if(nextFlowNodes.isEmpty()){
            return wfNextUserVoList;
        }
        getWfNextUserVoList(nextFlowNodes,wfNextUserVoList,processVariables,task);
        processVariables.remove(InnerVariableEnum.CONDITIONEXPRESSION.getName());
        processVariables.remove(InnerVariableEnum.USEREXPRESSION.getName());
        runtimeService.setVariables(task.getProcessInstanceId(),processVariables);
        return wfNextUserVoList;
    }

    /**
     * 获取所有下一步选人的人员集合，包括不同节点的
     * @param nextFlowNodes
     * @param wfNextUserVoList
     * @param processVariables
     * @param task
     */
    void getWfNextUserVoList(List<FlowNode> nextFlowNodes,List<WfNextUserVo> wfNextUserVoList,Map<String, Object> processVariables,Task task){
        //4.0版本已经支持并行网关，所以可能会出现多个用户节点选人的情况；
        for (FlowNode flowNode : nextFlowNodes){
            WfNextUserVo wfNextUserVo = new WfNextUserVo();
            wfNextUserVo.setCamundaTaskId(task.getId());
            List<UserExpressionVO> userList = new ArrayList();
            wfNextUserVo.setUserList(userList);
            wfNextUserVoList.add(wfNextUserVo);
            if(flowNode instanceof EndEvent){
                EndEvent endEvent = (EndEvent) flowNode;
                wfNextUserVo.setNextCamundaNodeId(endEvent.getId());
                wfNextUserVo.setNextCamundaNodeName(endEvent.getName());
                wfNextUserVo.setNextcamundaNodeType(NodeTypeEnum.ENDEVENT.code);
                wfNextUserVo.setSubmitType(SubmitTypeEnum.SUBMIT.getType());
                return;
            }else if(flowNode instanceof UserTask){
                log.info("UserTask");
                UserTask userTask = (UserTask)flowNode;
                String name = userTask.getName();
                String id = userTask.getId();
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
            //获取人员表达式，并执行表达式，获取人员列表
            setUserList(task,processVariables,userList,flowNode.getId());
            wfNextUserVo.setNextCamundaNodeId(flowNode.getId());
            wfNextUserVo.setNextCamundaNodeName(flowNode.getName());
            wfNextUserVo.setSubmitType(SubmitTypeEnum.SUBMIT.getType());
            /*processVariables.remove(InnerVariableEnum.CONDITIONEXPRESSION.getName());
            processVariables.remove(InnerVariableEnum.USEREXPRESSION.getName());*/
            log.info("最终用户列表:{}", JSONUtil.toJsonStr(userList));
            //获取到原驳回人的人员列表
            getRejectSourceUserList(task,wfNextUserVoList);
        }
    }

    /**
     * 设置栽一个节点的所有人员，先执行人员表达式，根据执行结果设置人到集合中；
     * @param task
     * @param processVariables
     * @param userList
     * @param key
     */
    void setUserList(Task task,Map<String, Object> processVariables,List<UserExpressionVO> userList,String key){
        //通过流程定义ID和节点定义ID获取表达式列表
        List<WfExpreUserDef> wfExpreUserList = wfExpreUserDefService.getExpreByProcDefIdAndNodeDefId(task.getProcessDefinitionId(), key);
        if(ObjectUtil.isNotEmpty(wfExpreUserList)){
            wfExpreUserList.forEach(wfExpreUserDefVo -> {
                String bizType = wfExpreUserDefVo.getBizType();
                String expression = wfExpreUserDefVo.getExpression();
                log.info("获取的表达式:{}",expression);
                String exp = StrUtil.subAfter(expression, "${", false);
                String expressionResult = StrUtil.subBefore(exp, "}", true);
                log.info("处理的表达式:{}",expressionResult);
                if(bizType.equals(ExpressionBizTypeEnum.MDM_EXPRESSION.getType())){
                    List<UserExpressionVO> result = FlowUtils.getResult(processVariables, expressionResult);
                    if(ObjectUtil.isNotEmpty(result)){
                        log.info("获取到解析后的用户列表:{}", JSONUtil.toJsonStr(result));
                        userList.addAll(result);
                    }
                }else if(bizType.equals(ExpressionBizTypeEnum.INNER_EXPRESSION.getType())){//内部表达式
                    handleInnerExpression(wfExpreUserDefVo,task,userList);
                }
            });
        }
    }

    /**
     * 处理内部表达式
     * @param wfExpreUserDef
     * @param task
     * @param userList
     */
    void handleInnerExpression(WfExpreUserDef wfExpreUserDef,Task task,List<UserExpressionVO> userList){
        String expression = wfExpreUserDef.getExpression();
        if(expression.contains(InnerUserExpressionEnum.START_USER.name().toLowerCase())){ //前面已经验证是否是内置表达式
            WfProcess wfProcess = wfProcessService.getAndCheck(task.getProcessInstanceId());
            MdmUserVo mdmUserVo = incloudCache.get(wfProcess.getStarterId());
            if(ObjectUtil.isNotEmpty(mdmUserVo)){
                UserExpressionVO userExpressionVO = dozerMapper.map(mdmUserVo, UserExpressionVO.class);
                userList.add(userExpressionVO);
            }
        }else if(expression.contains(InnerUserExpressionEnum.LAST_USER.name().toLowerCase())){
            //todo 回头再实现
        }
    }

    /**
     * 获取原驳回人列表，相当于快速提交到原驳回人，这个是根据日志来查的；
     * @param task
     * @param wfNextUserVoList
     */
    void getRejectSourceUserList(Task task,List<WfNextUserVo> wfNextUserVoList){
        //获取原驳回节点信息，如果有的话，说明当前节点是被驳回过的，可以正向提交，也可以提交到原驳回人；
        WfProcessLogVo lastRejectNodeInfo = wfProcessLogService.getLastRejectNodeInfo(task.getProcessInstanceId(), task.getTaskDefinitionKey());
        if(ObjectUtil.isNotEmpty(lastRejectNodeInfo)){
            WfNextUserVo wfNextUserVoLast = new WfNextUserVo();
            wfNextUserVoLast.setNextCamundaNodeId(lastRejectNodeInfo.getNodeId());
            wfNextUserVoLast.setNextCamundaNodeName(lastRejectNodeInfo.getNodeName());
            wfNextUserVoLast.setNextcamundaNodeType(lastRejectNodeInfo.getNodeType());
            wfNextUserVoLast.setCamundaTaskId(task.getId());
            wfNextUserVoLast.setSubmitType(SubmitTypeEnum.SUBMIT_TO_REJECT_NODE.getType());
            List<UserExpressionVO> userListLast = new ArrayList();
            MdmUserVo mdmUserVo = incloudCache.get(lastRejectNodeInfo.getUserName());
            if(ObjectUtil.isEmpty(mdmUserVo)){
                throw new IncloudException("不存在用户："+lastRejectNodeInfo.getUserName());
            }
            UserExpressionVO userExpressionVO = dozerMapper.map(mdmUserVo, UserExpressionVO.class);
            userListLast.add(userExpressionVO);
            wfNextUserVoLast.setUserList(userListLast);
            wfNextUserVoList.add(wfNextUserVoLast);
        }
    }

    /**
     * 根据任务获取一个bpmn实例
     * @param task
     * @return
     */
    private BpmnModelInstance getBpmnModelInstance(Task task){
        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance(task.getProcessDefinitionId());
        return bpmnModelInstance;
    }

    /**
     * 根据流程定义ID获取一个BpmnModelInstance实例
     * @param processDefinitionId
     * @return
     */
    private BpmnModelInstance getBpmnModelInstance(String processDefinitionId){
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);
        return bpmnModelInstance;
    }
}