package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.constants.*;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.expression.WfConditionExpression;
import com.netwisd.base.wf.service.WfProcdefService;
import com.netwisd.base.wf.service.other.WfDelegationService;
import com.netwisd.base.wf.service.procdef.WfEventDefService;
import com.netwisd.base.wf.service.procdef.WfEventParamDefService;
import com.netwisd.base.wf.service.procdef.WfNodeDefService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.starter.constants.SubmitTypeEnum;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.util.WfEventUtils;
import com.netwisd.base.wf.vo.*;
import com.netwisd.common.core.exception.CheckedException;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPut;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/9 15:53
 */
@Service
@Slf4j
public class WfEngineSubmitServiceImpl implements WfEngineSubmitService {

    private final static String DESCRIPTION = "wfLocalDescription";

    private final static String ISAGRREE = "wfLocalIsagree";
    //-------多实例相关-------
    //实例总数
    private static final String nrOfInstances = "nrOfInstances";
    //当前活动的，比如，还没完成的实例数量。 对于顺序执行的多实例，值一直为1。
    private static final String nrOfCompletedInstances = "nrOfCompletedInstances";
    //已经完成实例的数目。
    private static final String nrOfActiveInstances = "nrOfActiveInstances";
    //当前遍历的索引位置
    private static final String loopCounter = "loopCounter";

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> incloudCache;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    WfEventDefService wfEventDefService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    WfNodeDefService wfNodeDefService;

    @Autowired
    TaskService taskService;

    @Autowired
    WfTaskService wfTaskService;

    @Autowired
    WfEngineDuplicateService wfEngineDuplicateService;

    @Autowired
    WfConditionExpression wfConditionExpression;

    @Autowired
    WfProcdefService wfProcdefService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    WfDelegationService wfDelegationService;

    @Autowired
    Mapper dozerMapper;

    @Autowired
    WfEngineDelService wfEngineDelService;

    @Autowired
    WfEngineCommonService wfEngineCommonService;

    @Autowired
    WfEngineBizDataService wfEngineBizDataService;

    @Autowired
    WfGetDtoService wfGetDtoService;

    @Autowired
    WfEngineRejectService wfEngineRejectService;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    WfDelegationTaskService wfDelegationTaskService;

    @Autowired
    @Lazy
    WfEngineNotifyService wfEngineNotifyService;

    final static String endNode = "Event_";

    /**
     * 流程提交
     * @param handleDto
     * @return
     */
    @Override
    public Boolean submit(WfEngineDto.HandleDto handleDto) {
        //执行下业务处理
        invoke(handleDto);
        Task task = wfTaskService.getAndCheckClaim(handleDto.getCamundaTaskId());
        wfProcessService.checkProcessState(task.getProcessInstanceId());
        //如果提交存在知会人
        if(CollectionUtil.isNotEmpty(handleDto.getWfNotifyList())){
            submit2Notify(handleDto,task);
            return true;
        }
        //提交类型为原驳回人
        if(handleDto.getSubmitType().intValue() == SubmitTypeEnum.SUBMIT_TO_REJECT_NODE.getType().intValue()){
            submit2RejectNode(handleDto,task);
            return true;
        }
        //验证候选人
        checkWfAssignee(handleDto,task.getProcessDefinitionId());
        //目标提交节点
        String targetActivityId = handleDto.getTargetActivityId();
        //同意标识
        Integer wfLocalIsAgree = handleDto.getWfLocalIsAgree();
        //传阅 其实就是自定义创建一个Task,并关联上相应的流程实例信息
        if(CollectionUtil.isNotEmpty(handleDto.getWfDuplicateList())){
            wfEngineDuplicateService.createDuplicate(handleDto,task);
        }
        //获取variable变量
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        //处理提交变量
        submitVariablesHandle(task,processVariables,handleDto);
        WfProcessLogVo lastNodeInfo = null;
        //计算通过率问题——主要是涉及到多实例节点的时候；
        Boolean isComplete = calculatePassingRate(task, processVariables, wfLocalIsAgree, lastNodeInfo);
        //处理提交变量
        submitVariablesAfterHandle(task,processVariables,handleDto,targetActivityId,isComplete);
        //产生已办任务和日志
        wfEngineCommonService.wfDoneTaskHandle(task,targetActivityId);
        //删除掉当前人的待办
        wfEngineDelService.delWfTodoTask(task);
        if(isComplete){
            //处理下完成前事件
            handleSubmitEvent(task,EventBindTypeEnum.BEFORE_SUBMIT.code);
            //完成任务
            log.info("用户：{}，任务id：{} ,任务名称：{}，任务流程id:{}",task.getAssignee(),task.getId(),task.getName(),task.getProcessInstanceId());
            taskService.complete(task.getId());
            log.info("任务提交处理完成：id {} name {}",task.getId(),task.getName());
        }else {
            //由于驳回，所以清除掉没用的多实例变量
            clearMultiVariable(processVariables);
            //驳回到原会签提交前的节点；
            rejectLastNode(task,lastNodeInfo,processVariables);
        }
        return true;
    }


    void checkWfAssignee(WfEngineDto.HandleDto handleDto, String camundaProcdefId) {
        List<String> wfAssignees = handleDto.getWfAssignee();
        String targetActivityId = handleDto.getTargetActivityId();
        WfNodeDefVo wfNodeDefVo = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(camundaProcdefId,targetActivityId);
        if(wfNodeDefVo.getNodeType() != NodeTypeEnum.ENDEVENT.code) {
            if(CollectionUtil.isEmpty(wfAssignees)) {
                throw new IncloudException("候选人不能为空！");
            }
        }
    }

    void invoke(WfEngineDto.HandleDto handleDto){
        for (WfEngineDto.BizData bizData : handleDto.getBizDataList()){
            WfDto wfDto = wfGetDtoService.returnData(handleDto.getCamundaTaskId(),null);
            //判断是否是结束节点
            if(handleDto.getTargetActivityId().contains(endNode)) {
                wfDto.setAuditStatus(WfProcessEnum.DONE.getType());
            } else {
                wfDto.setAuditStatus(WfProcessEnum.RUNNING.getType());
            }
            wfEngineBizDataService.invoke(bizData,wfDto,BizMethodTypeEnum.UPDATE);
        }
    }

    /**
     * 自动驳回到前一个节点
     * @param task
     * @param lastNodeInfo
     * @param processVariables
     */
    void rejectLastNode(Task task,WfProcessLogVo lastNodeInfo,Map<String,Object> processVariables){
        String beforeActivity = lastNodeInfo.getNodeId();
        WfNodeDefVo wfNodeDefVoTarget = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(task.getProcessDefinitionId(), beforeActivity);
        if(ObjectUtil.isEmpty(wfNodeDefVoTarget)){
            throw new IncloudException("找不到要驳回的目标节点！");
        }
        //驳回到上一个usertask
        runtimeService.createProcessInstanceModification(task.getProcessInstanceId())
                .cancelAllForActivity(task.getTaskDefinitionKey())
                .setAnnotation("会签流程不通过")
                .startBeforeActivity(beforeActivity)
                .setVariables(processVariables)
                .execute();
        //2021.2.23增加会签不同意的驳回事件触发
        //根据是否回滚和提交前提交后，在handle方法中调用这个，如果需要回滚，则handle抛出异常
        List<WfEventRuntimeVo> wfEventRuntimeVoList = wfEventDefService.getEventByConditions(task.getProcessDefinitionId(),
                beforeActivity, //驳回的目标节点
                EventTypeEnum.TASK_EVENT.code,
                EventBindTypeEnum.REJECTED.code);
        if(ObjectUtil.isNotEmpty(wfEventRuntimeVoList)){
            for(WfEventRuntimeVo wfEventRuntimeVo : wfEventRuntimeVoList){
                //调用自定义返回事件
                /*eventListenerInvoke4Reject(task,beforeActivity,lastNodeInfo.getUserId(),EventBindTypeEnum.REJECTED.code,wfEventRuntimeVo);*/
                wfEngineCommonService.eventListenerInvoke(task,wfEventRuntimeVo,EventBindTypeEnum.REJECTED.code);
            }
        }
        log.info("---reject---驳回成功");
    }

    /**
     * 驳回的时候，清除掉variable中不必要的变量，因为后面节点可能还会有多实例节点，避免污染全局变量；
     * @param processVariables
     */
    void clearMultiVariable(Map<String, Object> processVariables){
        /**
         * 因为是要驳回了，那么在结束自己启动目标之前，把不相关的变量要清一下，防止污染目标节点数据，说明一下这里为什么不用事件清除；
         * 因为有个先后顺序的问题，cancel在前会先调用当肯节点对应的delete事件，虽然清掉了，但是调用目标之后时又传了一个processVariables，
         * 当然可以先start目标，再cancel掉当前，但不是正常的操作思维，不那样做；
         */
        processVariables.remove(nrOfInstances);
        processVariables.remove(nrOfCompletedInstances);
        processVariables.remove(nrOfActiveInstances);
        processVariables.remove(loopCounter);
        processVariables.remove(InnerVariableEnum.DECISION_INSTANCES.getName());
        processVariables.remove(InnerVariableEnum.UNDECISION_INSTANCES.getName());
    }

    /**
     * 计算通过率
     * @param task
     * @param processVariables
     * @param wfLocalIsAgree
     * @param lastNodeInfo
     * @return
     */
    Boolean calculatePassingRate(Task task,Map<String,Object> processVariables,Integer wfLocalIsAgree,WfProcessLogVo lastNodeInfo){
        //获取当前节点定义
        WfNodeDefVo wfNodeDefVo = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
        Integer nodeType = wfNodeDefVo.getNodeType();
        if(NodeTypeEnum.MULTIINSTANCETASK.code == nodeType.intValue()){
            Object decisionInstanceValue = processVariables.get(InnerVariableEnum.DECISION_INSTANCES.getName());
            Integer decisionInstances =  ObjectUtil.isEmpty(decisionInstanceValue) ? 0 : (Integer)decisionInstanceValue;
            Object unDecisionInstanceValue = processVariables.get(InnerVariableEnum.UNDECISION_INSTANCES.getName());
            Integer unDecisionInstances =  ObjectUtil.isEmpty(unDecisionInstanceValue) ? 0 : (Integer)unDecisionInstanceValue;
            //如果同意------新加字段，相当于原来的wfLocalDecision；因为decision赋予了其他标识；
            if(wfLocalIsAgree.intValue() == YesNo.YES.code.intValue()){
                processVariables.put(InnerVariableEnum.DECISION_INSTANCES.getName(),++decisionInstances);
                processVariables.put(InnerVariableEnum.UNDECISION_INSTANCES.getName(),unDecisionInstances);
            } else {
                processVariables.put(InnerVariableEnum.DECISION_INSTANCES.getName(),decisionInstances);
                processVariables.put(InnerVariableEnum.UNDECISION_INSTANCES.getName(),++unDecisionInstances);
            }
            //Object instObj = processVariables.get(InnerVariableEnum.DECISION_INSTANCES.getName());
            Object instUnObj = processVariables.get(InnerVariableEnum.UNDECISION_INSTANCES.getName());
            //实例总数
            Object nrOfInstancesObj = processVariables.get(nrOfInstances);
            //当前活动的，比如，还没完成的实例数量。 对于顺序执行的多实例，值一直为1。
            // nrOfActiveInstancesObj = processVariables.get(nrOfActiveInstances);
            //已经完成实例的数目。
            //Object nrOfCompletedInstancesObj = processVariables.get(nrOfCompletedInstances);
            //Integer wfLocalDecisionInstances  = (Integer) instObj;
            Integer wfLocalUnDecisionInstances  = (Integer) instUnObj;
            if(wfLocalIsAgree.intValue() != YesNo.YES.code.intValue()){
                /**
                 * 计算不通过率 -- 解释下为什么计算不通过率
                 * 计算不通过率为true时，代表当前会签已经整体不通过了，后面还有人再审批已经没有意义，所以直接进行自动驳回上个节点的操作；
                 * 然后要删除掉还没审批的相应人员的待办，我们我自己的todoTask 则不需要考虑，只删camunda的userTask即可，todoTask通过事件来删；
                 */
                if(multiUnPassingRate(nrOfInstancesObj,wfNodeDefVo,wfLocalUnDecisionInstances)){
                    log.info("整体会签没通过~~~");
                    //原路返回给发起会签的人
                    lastNodeInfo = wfProcessLogService.getLastNodeInfo(task.getProcessInstanceId(), task.getTaskDefinitionKey());
                    if(ObjectUtil.isEmpty(lastNodeInfo)){
                        throw new IncloudException("找不到上一个提交过来的节点！");
                    }
                    processVariables.put(InnerVariableEnum.ASSIGNEE.getName(),lastNodeInfo.getUserName());
                    return false;
                }
            }
        }else {
            //普通节点的一些处理；
            WfProcess wfProcess = wfProcessService.get(task.getProcessInstanceId());
            wfProcess.setCurrentActivityAssignee(null);
            wfProcess.setCurrentActivityAssigneeName(null);
            wfProcessService.update(wfProcess);
        }
        return true;
    }

    /**
     * 流程驳回
     * @param handleDto
     * @return
     */
    @Transactional
    public void submit2RejectNode(WfEngineDto.HandleDto handleDto,Task task) {
        wfEngineRejectService.rejectTargetActivity(handleDto,task,WfProcessLogEnum.SUBMIT);
        //最后，搞一下正常提交要走的事件，因为是非正常提交，所以手动触发事件
        handleSubmitEvent(task,EventBindTypeEnum.BEFORE_SUBMIT.code,EventBindTypeEnum.COMPLETE.code);

        //最后，搞一下正常提交要走的事件，因为是非正常提交，所以手动触发事件——2021.3.24 zouliming修改
        //先怼提交前（业务提交前）的；
        /*List<WfEventRuntimeVo> wfEventRuntimeVoBeforeList = wfEventDefService.getEventByConditions(task.getProcessDefinitionId(),
                task.getTaskDefinitionKey(),
                EventTypeEnum.TASK_EVENT.code,
                EventBindTypeEnum.BEFORE_SUBMIT.code);
        if(ObjectUtil.isNotEmpty(wfEventRuntimeVoBeforeList)){
            for(WfEventRuntimeVo wfEventRuntimeVo : wfEventRuntimeVoBeforeList){
                //调用自定义返回事件
                eventListenerInvoke(task,wfEventRuntimeVo,EventBindTypeEnum.BEFORE_SUBMIT);
            }
        }
        //再怼提交后（工作流提交后）的；
        List<WfEventRuntimeVo> wfEventRuntimeVoAfterList = wfEventDefService.getEventByConditions(task.getProcessDefinitionId(),
                task.getTaskDefinitionKey(),
                EventTypeEnum.TASK_EVENT.code,
                EventBindTypeEnum.COMPLETE.code);
        if(ObjectUtil.isNotEmpty(wfEventRuntimeVoAfterList)){
            for(WfEventRuntimeVo wfEventRuntimeVo : wfEventRuntimeVoAfterList){
                //调用自定义返回事件
                eventListenerInvoke(task,wfEventRuntimeVo,EventBindTypeEnum.COMPLETE);
            }
        }*/

        log.info("---submit2RejectNode---提交原驳回节点成功");
    }

    /**
     * 流程知会
     * @param handleDto
     * @return
     */
    @Transactional
    public void submit2Notify(WfEngineDto.HandleDto handleDto,Task task) {
        wfEngineNotifyService.createNotify(handleDto,task);
    }

    /**
     * 自定义执行提交事件
     * @param task
     * @param eventBindTypes
     */
    void handleSubmitEvent(Task task,String... eventBindTypes){
        List<WfEventRuntimeVo> wfEventRuntimeVoBeforeList = wfEventDefService.getMultiEventByConditions(task.getProcessDefinitionId(),
                task.getTaskDefinitionKey(),
                EventTypeEnum.TASK_EVENT.code,
                eventBindTypes);
        if(ObjectUtil.isNotEmpty(wfEventRuntimeVoBeforeList)){
            for(WfEventRuntimeVo wfEventRuntimeVo : wfEventRuntimeVoBeforeList){
                //调用自定义返回事件
                wfEngineCommonService.eventListenerInvoke(task,wfEventRuntimeVo,wfEventRuntimeVo.getEventBindType());
            }
        }
    }

    /**
     * 提前前的一些变量处理
     * @param task
     * @param processVariables
     * @param handleDto
     */
    private void submitVariablesHandle(Task task,Map<String, Object> processVariables,WfEngineDto.HandleDto handleDto){
        //同意标识
        Integer wfLocalIsAgree = handleDto.getWfLocalIsAgree();
        //意见
        String wfLocalDescription = handleDto.getWfLocalDescription();
        task.setDescription(String.valueOf(WfProcessLogEnum.SUBMIT.getType()));
        //处理意见和同意标识，放入camunda的task中
        String json = JSONUtil.createObj().set(DESCRIPTION,wfLocalDescription).set(ISAGRREE,wfLocalIsAgree).toStringPretty();
        //存入到camunda的task的备注中
        taskService.createComment(task.getId(),task.getProcessInstanceId(),json);
        processVariables.put("taskId",task.getId());
        log.info("原过程变量为：{}",processVariables);
        /*BpmnModelInstance bpmnModelInstance = getBpmnModelInstance(task);*/
        //处理表达式变量
        //用于表达式计算用的两个内置表达式变量
        /*processVariables.put(InnerVariableEnum.CONDITIONEXPRESSION.getName(),wfConditionExpression);

        FlowUtils.getNextUserTasks(bpmnModelInstance, task.getTaskDefinitionKey(), processVariables,FlowNodeTypeEnum.USER_TASK.getType());
        processVariables.remove(InnerVariableEnum.CONDITIONEXPRESSION.getName());*/
    }

    /**
     * 根据任务获取一个bpmn实例
     * @param task
     * @return
     */
    /*private BpmnModelInstance getBpmnModelInstance(Task task){
        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance(task.getProcessDefinitionId());
        return bpmnModelInstance;
    }

    private BpmnModelInstance getBpmnModelInstance(String processDefinitionId){
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);
        return bpmnModelInstance;
    }*/

    private void submitVariablesAfterHandle(Task task,Map<String, Object> processVariables,WfEngineDto.HandleDto handleDto,String targetActivityId,Boolean isComplete){
        List<String> wfAssigneeList = null;
        if(isComplete){
            wfAssigneeList = handleDto.getWfAssignee();
        }else {
            wfAssigneeList = new ArrayList<>();
            String ass = (String)processVariables.get(InnerVariableEnum.ASSIGNEE.getName());
            wfAssigneeList.add(ass);
        }
        //处理委托待办
        delegationHandle(wfAssigneeList,task);
        processVariables.put(InnerVariableEnum.ASSIGNEE.getName(),wfAssigneeList);
        //清除本地变量
        clearWfLocalVariable(processVariables);
        //处理过期时间
        wfEngineCommonService.dueDateHandle(task.getProcessDefinitionId(),processVariables,targetActivityId);
        taskService.setVariables(task.getId(),processVariables);
        log.info("提交合并后操作后过程变量为：{}",processVariables);
        taskService.saveTask(task);
    }

    /**
     * 清除掉本地变量
     * @param variables
     */
    public void clearWfLocalVariable(Map<String, Object> variables){
        List<Object> fieldValues = EnumUtil.getFieldValues(InnerVariableEnum.class,"name");
        for (Object name:fieldValues){
            String filedValue = String.valueOf(name);
            if(ObjectUtil.isNotEmpty(variables.get(filedValue)) && filedValue.startsWith(InnerVariableEnum.PREFIX_LOCAL.getName())){
                variables.remove(filedValue);
            }
        }
    }

    /**
     * 委拖待办
     * @param wfAssignee
     */
    private void delegationHandle(List<String> wfAssignee,Task task){
        List<String> list = new ArrayList<>();
        List<Object> objList = new ArrayList<>();
        dozerMapper.map(wfAssignee,list);
        List<WfDelegationVo> entityList = wfDelegationService.getEntityList(list);
        if(CollectionUtil.isNotEmpty(entityList)) { //当前办理人以及委托人 都加入缓存在查询条件
            list.forEach(d->objList.add(d));
            entityList.forEach(d->objList.add(d.getDelegationUserName()));
            List<MdmUserVo> mdmUserVos = incloudCache.gets(objList);
            if(CollectionUtil.isEmpty(mdmUserVos)) {
                throw new IncloudException("获取缓存数据失败！");
            }
            Map<String, MdmUserVo> userMap = userMap = mdmUserVos.stream().collect(Collectors.toMap(MdmUserVo::getUserName, d -> d));
            WfTodoTask wfTodoTask = wfTodoTaskService.get(task.getId());
            for(String assignee:list){
                for(WfDelegationVo entity:entityList){
                    //有委办人
                    if(ObjectUtil.isNotEmpty(entity) && assignee.equals(entity.getDesignateUserName())){
                        //获取到被委托人
                        String delegationPersonId = entity.getDelegationUserName();
                        wfAssignee.remove(assignee);
                        wfAssignee.add(String.valueOf(delegationPersonId));
                        //保存到我委托的待办台账
                        if(ObjectUtil.isNotEmpty(wfTodoTask)){
                            WfDelegationTask wfDelegationTask = dozerMapper.map(wfTodoTask, WfDelegationTask.class);
                            wfDelegationTask.setCliamTime(null);
                            wfDelegationTask.setAssignee(delegationPersonId); //委托办理人
                            wfDelegationTask.setAssigneeName(userMap.get(delegationPersonId).getUserNameCh());
                            wfDelegationTask.setOwnner(assignee); //被委托人
                            wfDelegationTask.setOwnnerName(userMap.get(assignee).getUserNameCh());
                            wfDelegationTask.setCreateTime(LocalDateTime.now());
                            wfDelegationTask.setUpdateTime(LocalDateTime.now());
                            wfDelegationTask.setCamundaTaskId(task.getId());
                            wfDelegationTask.setCamundaNodeKey(task.getTaskDefinitionKey());
                            wfDelegationTask.setCamundaNodeName(task.getName());
                            wfDelegationTask.setCamundaNodeType(wfTodoTask.getNodeType());
                            wfDelegationTask.setIsCallActivity(wfTodoTask.getIsCallActivity());
                            wfDelegationTask.setIsClone(wfTodoTask.getIsClone());
                            wfDelegationTask.setCamundaParentProcinsId(wfTodoTask.getCamundaParentProcinsId());
                            wfDelegationTask.setParentProcinsId(wfTodoTask.getParentProcinsId());
                            wfDelegationTask.setCurrentActivityId(wfTodoTask.getNodeKey());
                            wfDelegationTask.setCurrentActivityName(wfTodoTask.getNodeName());
                            //wfDelegationTask.setCurrentActivityAssignee(wfTodoTask.getAssignee());
                            //wfDelegationTask.setCurrentActivityAssigneeName(wfTodoTask.getAssigneeName());
                            wfDelegationTaskService.save(wfDelegationTask);
                        }
                    }
                }
            }
        }
    }

    /**
     * 计算流程多实例的通过率是否通过
     * @param nrOfInstancesObj
     * @param wfNodeDefVo
     * @param decisionInstances
     * @return
     */
    private boolean multiPassingRate(Object nrOfInstancesObj,WfNodeDefVo wfNodeDefVo,Integer decisionInstances){
        log.info("---计算通过率 --> 当前通过数：{}",decisionInstances.intValue());
        BigDecimal passingRate = wfNodeDefVo.getPassingRate();
        Integer nrOfInstances = null;
        if(ObjectUtil.isNotEmpty(nrOfInstancesObj)){
            nrOfInstances = (Integer) nrOfInstancesObj;
        }
        log.info("---计算通过率 --> 实例总数：{}",nrOfInstances.intValue());
        boolean result = false;
        if(ObjectUtil.isNotEmpty(nrOfInstances)){
            BigDecimal div = NumberUtil.div(decisionInstances, nrOfInstances);
            log.info("---计算通过率 --> 当前通过率：{}",div.doubleValue());
            log.info("---计算通过率 --> 节点维护的会签通过率：{}",passingRate);
            //当前通过率和维护的通过率相比较
            int compareResult = div.compareTo(passingRate);
            if(compareResult >= 0){
                log.info("--------会签通过！！！");
                result = true;
            }
        }
        return result;
    }

    /**
     * 计算流程多实例的不通过率是否不通过
     * @param nrOfInstancesObj
     * @param wfNodeDefVo
     * @param unDecisionInstances
     * @return
     */
    private boolean multiUnPassingRate(Object nrOfInstancesObj,WfNodeDefVo wfNodeDefVo,Integer unDecisionInstances){
        log.info("---计算不通过率 --> 当前不通过数：{}",unDecisionInstances.intValue());
        BigDecimal passingRate = wfNodeDefVo.getPassingRate();
        BigDecimal unPassingRate = BigDecimal.ONE.subtract(passingRate);

        Integer nrOfInstances = null;
        if(ObjectUtil.isNotEmpty(nrOfInstancesObj)){
            nrOfInstances = (Integer) nrOfInstancesObj;
        }
        log.info("---计算不通过率 --> 实例总数：{}",nrOfInstances.intValue());
        boolean result = false;
        if(ObjectUtil.isNotEmpty(nrOfInstances)){
            BigDecimal div = NumberUtil.div(unDecisionInstances, nrOfInstances);
            log.info("---计算不通过率 --> 当前不通过率：{}",div.doubleValue());
            log.info("---计算不通过率 --> 节点维护的不通过率（通过计算得出）：{}",unPassingRate);
            //当前不通过率和维护的不通过率相比较
            int compareResult = div.compareTo(unPassingRate);
            //只有不通过率大于设置的不通过率时（不能等于，我们设定通过率时，是包含等于的，所以不通过率不包含等于）
            if(compareResult > 0){
                log.info("--------会签不通过！！！");
                result = true;
            }
        }
        return result;
    }
}
