package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.wf.constants.BooleanEnum;
import com.netwisd.base.wf.constants.EventTypeEnum;
import com.netwisd.base.wf.constants.FlowNodeTypeEnum;
import com.netwisd.base.wf.dto.WfEventDto;
import com.netwisd.base.wf.entity.WfEvent;
import com.netwisd.base.wf.entity.WfProcDef;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.mapper.WfEngineMapper;
import com.netwisd.base.wf.service.*;
import com.netwisd.base.wf.service.other.WfFormFieldsDefService;
import com.netwisd.base.wf.service.procdef.WfEventService;
import com.netwisd.base.wf.service.procdef.WfNodeDefService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.util.FlowUtils;
import com.netwisd.base.wf.util.XmlUtils;
import com.netwisd.base.wf.vo.WfFormFieldsDefVo;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import com.netwisd.base.wf.xml.*;
import com.netwisd.common.core.exception.IncloudException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author zouliming@netwisd.com
 * @description ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
 * @date 2021/12/9 10:53
 */
@Service
@Slf4j
public class WfEngineInsertNodeServiceImpl implements WfEngineInsertNodeService {

    @Autowired
    TaskService taskService;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    WfProcdefService wfProcdefService;

    @Autowired
    WfEngineMapper wfEngineMapper;

    @Autowired
    WfMyInDuplicateTaskService wfMyInDuplicateTaskService;

    @Autowired
    WfMyOutDuplicateTaskService wfMyOutDuplicateTaskService;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    WfDoneTaskService wfDoneTaskService;

    @Autowired
    WfEventService wfEventService;

    @Autowired
    WfFormFieldsDefService wfFormFieldsDefService;

    @Autowired
    WfNodeDefService wfNodeDefService;

    @Transactional
    @SneakyThrows
    @Override
    public Boolean insertNode(WfEngineDto.HandleDto handleDto) {
        log.info("-----------wfEngine/countersign------------");
        Task task = taskService.createTaskQuery().taskId(handleDto.getCamundaTaskId()).singleResult();
        if(ObjectUtil.isEmpty(task)){
            throw new IncloudException("??????????????????????????????");
        }
        wfProcessService.checkProcessState(task.getProcessInstanceId());
        if(StrUtil.isEmpty(task.getAssignee())){
            throw new IncloudException("????????????????????????");
        }

        WfProcess wfProcess = wfProcessService.get(task.getProcessInstanceId());
        Integer isClone = wfProcess.getIsClone();
        Long procdefId = wfProcess.getProcdefId();
        String camundaProcdefId = wfProcess.getCamundaProcdefId();

        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        String processDefinitionId = task.getProcessDefinitionId();
        //???????????????
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
        String deploymentId = processDefinition.getDeploymentId();

        //????????????????????????xml,????????????bpmn??????
        String xml = wfProcdefService.getXmlInfoByCamundaId(processDefinition.getId());
        Bpmn bpmn = XmlUtils.xmltoDoc(xml);

        if(BooleanUtil.toInt(false) == isClone.intValue()){
            bpmn = XmlUtils.clearXmlDocDbId(bpmn);
        }


        // todo ??????????????????????????? ???????????? ????????????????????????
        Bpmnprocess bpmnprocess = bpmn.getBpmndefinitions().getBpmnprocess();
        List<BpmnuserTask> bpmnuserTaskList = bpmnprocess.getBpmnuserTask();
        List<BpmnsequenceFlow> bpmnsequenceFlowList = bpmnprocess.getBpmnsequenceFlow();
        String targetActivityName = handleDto.getTargetActivityName();
        String targetActivityId = "temp_userTask_"+System.currentTimeMillis();
        if(StrUtil.isEmpty(targetActivityName)){
            targetActivityName = targetActivityId;
        }
        //??????????????????
        BpmnuserTask bpmnuserTask = new BpmnuserTask();
        bpmnuserTask.setId(targetActivityId);
        bpmnuserTask.setName(targetActivityName);
        StringBuffer sb = new StringBuffer();
        handleDto.getWfAssignee().forEach(s -> sb.append(s).append(","));
        bpmnuserTask.setCamundacandidateUsers(sb.substring(0,sb.length()-1));

        //?????????????????????
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(task.getProcessDefinitionId());
        ModelElementInstance instance = bpmnModelInstance.getModelElementById(task.getTaskDefinitionKey());
        FlowNode currentFlowNode = (FlowNode)instance;

        //??????????????????????????????????????????
        Collection<FlowNode> modelElementsByType = bpmnModelInstance.getModelElementsByType(FlowNode.class);
        FlowNode nextFlowNode = getNextFlowNode(handleDto);
        FlowNode targetFlowNode = modelElementsByType.stream().filter(flowNode -> flowNode.getId().equals(nextFlowNode.getId())).findFirst().get();

        //???????????????????????????
        initBpmnInfo(bpmnuserTask,bpmnsequenceFlowList,currentFlowNode, targetFlowNode, bpmn,procdefId,camundaProcdefId);
        bpmnuserTaskList.add(bpmnuserTask);

        //??????xml??????
        String xmlStr = XmlUtils.objectToXml(bpmn.getBpmndefinitions(), Bpmndefinitions.class);
        String bpmnXmlStr = XmlUtils.unCheckXml(xmlStr);

        log.info("-----??????????????????xml------\n {}",bpmnXmlStr);

        //???????????????????????????????????????????????????clone???????????????def????????????????????????????????????
        if(BooleanUtil.toInt(false) == isClone.intValue()){
            String deployId = wfProcdefService.saveOrUpdateBpmnModel(false, null, bpmnXmlStr, false, true);
            ProcessDefinition processDefinitionNew = repositoryService.createProcessDefinitionQuery().deploymentId(deployId).singleResult();
            processDefinitionId = processDefinitionNew.getId();

            /**
             * ??????camunda????????????ProcessDefinition????????????????????????????????????
             * act_hi_actinst
             * act_hi_detail
             * act_hi_identitylink
             * act_hi_procinst
             * act_hi_taskinst
             * act_hi_varinst
             * act_ru_execution
             * act_ru_identitylink
             * act_ru_task
             */
            wfEngineMapper.updateActInst(processDefinitionId,processInstance.getId());
            wfEngineMapper.updateDetail(processDefinitionId,processInstance.getId());
            wfEngineMapper.updateHiIdentitylink(processDefinitionId,processInstance.getId());
            wfEngineMapper.updateProcinst(processDefinitionId,processInstance.getId());
            wfEngineMapper.updateTaskinst(processDefinitionId,processInstance.getId());
            wfEngineMapper.updateVarinst(processDefinitionId,processInstance.getId());
            wfEngineMapper.updateTask(processDefinitionId,processInstance.getId());

            //act_ru_execution????????????????????????????????????????????????????????????
            List<Execution> executionList = runtimeService.createExecutionQuery().processInstanceId(task.getProcessInstanceId()).list();
            for(Execution execution : executionList){
                ExecutionEntity executionEntity = (ExecutionEntity)execution;
                executionEntity.setProcessDefinition((ProcessDefinitionImpl) processDefinitionNew);
            }
            wfEngineMapper.updateExecution(processDefinitionId,processInstance.getId());
            /**
             * ????????????wf???????????????ProcessDefinitionId,?????????????????????
             * incloud_base_wf_done_task
             * incloud_base_wf_my_in_duplicate_task
             * incloud_base_wf_my_out_duplicate_task
             * incloud_base_wf_process
             * incloud_base_wf_todo_task
             */
            WfProcDef wfProcDefNew = wfProcdefService.get(processDefinitionId);
            String camundaProcinsId = processInstance.getId();
            //???????????????????????????????????????WfProcDef
            if(ObjectUtil.isNotEmpty(wfProcDefNew)){
                wfProcDefNew.setIsClone(BooleanUtil.toInt(true));
                //????????????????????????copy??????????????????ID
                wfProcDefNew.setBeClonedFromCamundaProcdefId(processDefinition.getId());
                wfProcdefService.updateById(wfProcDefNew);
            }

            log.info("countersign?????????????????????ID??????{},???camunda????????????ID??????{}?????????camunda????????????version??????{}",camundaProcinsId,processDefinitionId,processDefinitionNew.getVersion());
            wfProcessService.updateDefId(processDefinitionNew,wfProcDefNew,camundaProcinsId);
            wfMyInDuplicateTaskService.updateDefId(processDefinitionNew,wfProcDefNew,camundaProcinsId);
            wfMyOutDuplicateTaskService.updateDefId(processDefinitionNew,wfProcDefNew,camundaProcinsId);
            wfTodoTaskService.updateDefId(processDefinitionNew,wfProcDefNew,camundaProcinsId);
            wfDoneTaskService.updateDefId(processDefinitionNew,wfProcDefNew,camundaProcinsId);

            WfProcess wfProcessNew = wfProcessService.get(camundaProcinsId);
            //??????isclone
            wfProcessNew.setIsClone(BooleanUtil.toInt(true));
            //?????????clone??????procdef???id???
            wfProcessNew.setBeClonedFromCamundaProcdefId(processDefinition.getId());
            wfProcessService.update(wfProcessNew);
        }else {
            //?????????????????????
            wfProcdefService.saveOrUpdateBpmnModel(true, deploymentId, bpmnXmlStr, false, true);
        }

        //??????submit
        handleDto.setTargetActivityId(targetActivityId);
        //submitDto.setCamundaProcdefId(processDefinitionId);

        return true;
    }

    private void initBpmnInfo(BpmnuserTask bpmnuserTask,List<BpmnsequenceFlow> bpmnsequenceFlowList
            ,FlowNode currentFlowNode,FlowNode targetFlowNode,Bpmn bpmn,Long procdefId,String camundaProcdefId){
        Bpmnprocess bpmnprocess = bpmn.getBpmndefinitions().getBpmnprocess();
        List<BpmndiBPMNEdge> bpmndiBPMNEdgeList = bpmn.getBpmndefinitions().getBpmndiBPMNDiagram().getBpmndiBPMNPlane().getBpmndiBPMNEdge();

        String idNameSequInCurrent = "temp_sequ_in_"+System.currentTimeMillis();
        String idNameSequOutCurrent = "temp_sequ_out_"+System.currentTimeMillis();

        BpmnFlowNode currentBpmnFlowNode = getBpmnFlowNode(bpmnprocess, currentFlowNode.getId());
        BpmnuserTask bpmnuserTaskCurrent = (BpmnuserTask)currentBpmnFlowNode;
        List<String> currentOutgoing = bpmnuserTaskCurrent.getBpmnoutgoing();

        //?????????????????????????????????????????????????????????????????????????????????????????????
        String currentSequenceFlowId = currentOutgoing.stream().findFirst().get();

        //?????????????????????????????????????????????
        currentOutgoing.remove(currentSequenceFlowId);
        //???????????????  -- ?????????????????????????????????????????????
        Optional<BpmnsequenceFlow> first1 = bpmnsequenceFlowList.stream().filter(bpmnsequenceFlow -> bpmnsequenceFlow.getId().equals(currentSequenceFlowId)).findFirst();
        if(first1 != null && !first1.isEmpty()){
            bpmnsequenceFlowList.remove(first1.get());
        }
        //????????????????????????  -- ?????????????????????????????????????????????
        Optional<BpmndiBPMNEdge> first2 = bpmndiBPMNEdgeList.stream().filter(bpmndiBPMNEdge -> bpmndiBPMNEdge.getBpmnElement().equals(currentSequenceFlowId)).findFirst();
        if(first2 != null && !first2.isEmpty()){
            bpmndiBPMNEdgeList.remove(first2.get());
        }
        //????????????????????????????????????????????????
        BpmnFlowNode bpmnFlowNode = getBpmnFlowNode(bpmnprocess, targetFlowNode.getId());
        List<String> bpmnincomingList = null;
        if(bpmnFlowNode instanceof BpmnuserTask){
            bpmnincomingList = ((BpmnuserTask)bpmnFlowNode).getBpmnincoming();
        }else if(bpmnFlowNode instanceof BpmnexclusiveGateway){
            bpmnincomingList = ((BpmnexclusiveGateway)bpmnFlowNode).getBpmnincoming();
        }else if(bpmnFlowNode instanceof BpmncallActivity){
            bpmnincomingList = ((BpmncallActivity)bpmnFlowNode).getBpmnincoming();
        }else if(bpmnFlowNode instanceof BpmnendEvent){
            bpmnincomingList = ((BpmnendEvent)bpmnFlowNode).getBpmnincoming();
        }
        if(ObjectUtil.isNotEmpty(bpmnincomingList)){
            Optional<String> first = bpmnincomingList.stream().filter(sequId -> sequId.equals(currentSequenceFlowId)).findFirst();
            if(ObjectUtil.isNotEmpty(first) && !first.isEmpty()){
                bpmnincomingList.remove(first.get());
            }
        }

        //????????????????????????????????????
        WfNodeDefVo wfNodeDefVo = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(camundaProcdefId, currentFlowNode.getId());

        //??????????????????
        bpmnuserTask.setNetwisdselectRule(String.valueOf(wfNodeDefVo.getSelectRule()));
        bpmnuserTask.setNetwisdbatchRule(String.valueOf(wfNodeDefVo.getBatchRule()));
        bpmnuserTask.setNetwisdcancelRule(String.valueOf(wfNodeDefVo.getCancelRule()));
        bpmnuserTask.setNetwisdreturnRule(String.valueOf(wfNodeDefVo.getReturnRule()));
        List<String> inList = new ArrayList<>();
        inList.add(idNameSequInCurrent);
        List<String> OutList = new ArrayList<>();
        OutList.add(idNameSequOutCurrent);
        bpmnuserTask.setBpmnincoming(inList);
        bpmnuserTask.setBpmnoutgoing(OutList);

        //??????????????????BpmnextensionElements???
        Optional<BpmnuserTask> first3 = bpmnprocess.getBpmnuserTask().stream()
                .filter(bpmnuserTaskInner -> bpmnuserTaskInner.getId().equals(currentFlowNode.getId())).findFirst();
        BpmnuserTask currentBpmnuserTask =  first3 != null && !first3.isEmpty() ? first3.get() : null;
        if(ObjectUtil.isEmpty(currentBpmnuserTask)){
            throw new IncloudException("????????????BpmnextensionElements???????????????");
        }
        BpmnextensionElements bpmnextensionElements = currentBpmnuserTask.getBpmnextensionElements();

        //???????????????????????????????????????????????????????????????????????????
        BpmnextensionElements bpmnextensionElementsed = new BpmnextensionElements();

        //???????????????????????????????????????UserTask???????????????????????????????????????????????????????????????
        List<WfEvent> userTaskDefaultExectionListenerList = getUserTaskDefaultListener(procdefId, EventTypeEnum.EXE_EVENT.code);
        List<WfEvent> userTaskDefaultTaskListenerList = getUserTaskDefaultListener(procdefId,EventTypeEnum.TASK_EVENT.code);
        //CamundaexecutionListener???
        List<CamundaexecutionListener> camundaexecutionListenerList = bpmnextensionElements.getCamundaexecutionListener();
        List<CamundaexecutionListener> executionCloneList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(camundaexecutionListenerList)){
            for(int i=0;i<camundaexecutionListenerList.size();i++){
                if(CollectionUtil.isNotEmpty(userTaskDefaultExectionListenerList)){
                    CamundaexecutionListener camundaexecutionListener = camundaexecutionListenerList.get(i);
                    Optional<WfEvent> first = userTaskDefaultExectionListenerList.stream().filter(wfEvent -> wfEvent.getListenerId().equals(camundaexecutionListener.getNetwisdlistenerId())).findFirst();
                    if(ObjectUtil.isNotEmpty(first) && first.isPresent()){
                        executionCloneList.add(camundaexecutionListener);
                    }
                }
            }
        }

        List<CamundataskListener> camundataskListenerList = bpmnextensionElements.getCamundataskListener();
        List<CamundataskListener> taskCloneList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(camundataskListenerList)){
            for (int i=0;i<camundataskListenerList.size();i++){
                if(CollectionUtil.isNotEmpty(userTaskDefaultTaskListenerList)){
                    CamundataskListener camundataskListener = camundataskListenerList.get(i);
                    Optional<WfEvent> first = userTaskDefaultTaskListenerList.stream().filter(wfEvent -> wfEvent.getListenerId().equals(camundataskListener.getNetwisdlistenerId())).findFirst();
                    if(ObjectUtil.isNotEmpty(first) && first.isPresent()){
                        taskCloneList.add(camundataskListener);
                    }
                }
            }
        }

        //???????????????
        bpmnextensionElementsed.setCamundaexecutionListener(executionCloneList);
        bpmnextensionElementsed.setCamundataskListener(taskCloneList);
        bpmnextensionElementsed.setNetwisdexecutionListener(bpmnextensionElements.getNetwisdexecutionListener());
        bpmnextensionElementsed.setNetwisdtaskListener(null);

        bpmnextensionElementsed.setCamundaproperties(bpmnextensionElements.getCamundaproperties());
        //?????????????????????????????????
        List<WfFormFieldsDefVo> formVarDefVoList = wfFormFieldsDefService.getFormFields(camundaProcdefId, currentFlowNode.getId());
        NetwisdNodeForm netwisdNodeForm = new NetwisdNodeForm();
        netwisdNodeForm.setMustFlag("default");
        List<NetwisdField> fields = new ArrayList<>();

//        for (WfFormFieldsDefVo wfFormVarDefVo : formVarDefVoList){
//            NetwisdField netwisdField = new NetwisdField();
//            netwisdField.setVarId(wfFormVarDefVo.getVarId());
//            netwisdField.setVarName(wfFormVarDefVo.getVarName());
//            netwisdField.setId(String.valueOf(wfFormVarDefVo.getFormVarId()));
//            netwisdField.setJavaType(wfFormVarDefVo.getJavaType());
//            netwisdField.setIsMoreRow(String.valueOf(wfFormVarDefVo.getIsMoreRow()));
//            netwisdField.setPowerCode(String.valueOf(wfFormVarDefVo.getPowerCode()));
//            netwisdField.setVarType(wfFormVarDefVo.getVarType());
//            netwisdField.setDbId(IdentifierGeneratorUtils.next().toString());
//            fields.add(netwisdField);
//        }
        netwisdNodeForm.setNetwisdfield(fields);
        bpmnextensionElementsed.setNetwisdnodeForm(netwisdNodeForm);
        bpmnextensionElementsed.setNetwisdnodeButtons(bpmnextensionElements.getNetwisdnodeButtons());

        bpmnuserTask.setBpmnextensionElements(bpmnextensionElementsed);

        //???????????????????????????
        BpmnsequenceFlow incoming = new BpmnsequenceFlow();
        incoming.setId(idNameSequInCurrent);
        incoming.setName(idNameSequInCurrent);
        //????????????????????????????????????????????????????????????
        incoming.setSourceRef(currentFlowNode.getId());
        //?????????????????????????????????
        incoming.setTargetRef(bpmnuserTask.getId());
        bpmnsequenceFlowList.add(incoming);

        //???????????????????????????
        BpmnsequenceFlow outcoming = new BpmnsequenceFlow();
        outcoming.setId(idNameSequOutCurrent);
        outcoming.setName(idNameSequOutCurrent);
        //????????????????????????
        outcoming.setSourceRef(bpmnuserTask.getId());
        //????????????????????????????????????????????????
        outcoming.setTargetRef(targetFlowNode.getId());
        bpmnsequenceFlowList.add(outcoming);

    }

    /**
     * ?????????????????????????????????????????????????????????????????????
     * @param handleDto
     * @return
     */
    private FlowNode getNextFlowNode(WfEngineDto.HandleDto handleDto){
        String taskId = handleDto.getCamundaTaskId();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(ObjectUtil.isEmpty(task)){
            throw new IncloudException("????????????ID??????????????????????????????????????????ID");
        }
        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance(task);
        List<FlowNode> nextFlowNode = FlowUtils.getNextUserTasks(bpmnModelInstance, task.getTaskDefinitionKey(), null, FlowNodeTypeEnum.ONLY_NEXT_NODE.getType());

        //????????????????????????????????????????????????????????????,?????????????????????????????????????????????????????????
        // ?????????camunda???????????????????????????????????????????????????????????????true?????????????????????????????????????????????????????????????????????????????????????????????
        if(ObjectUtil.isNotEmpty(nextFlowNode) && nextFlowNode.size()>1){
            throw new IncloudException("???????????????????????????????????????????????????????????????");
        }
        if(nextFlowNode.isEmpty()){
            throw new IncloudException("?????????????????????????????????????????????");
        }
        FlowNode flowNode = nextFlowNode.get(0);
        return flowNode;
    }

    private List<WfEvent> getUserTaskDefaultListener(Long procdefTypeId,Integer eventType){
        WfEventDto wfEventDto = new WfEventDto();
        wfEventDto.setProcdefTypeId(procdefTypeId);
        wfEventDto.setSelectSign(BooleanEnum.TRUE.getValue());
        wfEventDto.setEventType(eventType);
        List<WfEvent> lists = wfEventService.getDefaultEventList(wfEventDto);
        return lists;
    }

    private BpmnFlowNode getBpmnFlowNode(Bpmnprocess bpmnprocess, String flowNodeId){
        List<BpmnuserTask> bpmnuserTaskList = bpmnprocess.getBpmnuserTask();
        if(CollectionUtil.isNotEmpty(bpmnuserTaskList)){
            Optional<BpmnuserTask> bpmnuserTaskResult = bpmnuserTaskList.stream().filter(bpmnuserTaskInner -> bpmnuserTaskInner.getId().equals(flowNodeId)).findFirst();
            if(bpmnuserTaskResult.isPresent()){
                return bpmnuserTaskResult.get();
            }
        }

        List<BpmnexclusiveGateway> bpmnexclusiveGatewayList = bpmnprocess.getBpmnexclusiveGateway();
        if(CollectionUtil.isNotEmpty(bpmnexclusiveGatewayList)){
            Optional<BpmnexclusiveGateway> bpmnexclusiveGatewayResult = bpmnexclusiveGatewayList.stream().filter(bpmnexclusiveGateway -> bpmnexclusiveGateway.getId().equals(flowNodeId)).findFirst();
            if(bpmnexclusiveGatewayResult.isPresent()){
                return bpmnexclusiveGatewayResult.get();
            }
        }

        List<BpmnendEvent> bpmnendEventList = bpmnprocess.getBpmnendEvent();
        if(CollectionUtil.isNotEmpty(bpmnendEventList)){
            Optional<BpmnendEvent> bpmnendEventResult = bpmnendEventList.stream().filter(bpmnendEvent -> bpmnendEvent.getId().equals(flowNodeId)).findFirst();
            if(bpmnendEventResult.isPresent()){
                return bpmnendEventResult.get();
            }
        }

        List<BpmncallActivity> bpmncallActivityList = bpmnprocess.getBpmncallActivity();
        if(CollectionUtil.isNotEmpty(bpmncallActivityList)){
            Optional<BpmncallActivity> bpmncallActivityResult = bpmncallActivityList.stream().filter(bpmncallActivity -> bpmncallActivity.getId().equals(flowNodeId)).findFirst();
            if(bpmncallActivityResult.isPresent()){
                return bpmncallActivityResult.get();
            }
        }

        return null;
    }

    /**
     * ????????????????????????bpmn??????
     * @param task
     * @return
     */
    private BpmnModelInstance getBpmnModelInstance(Task task){
        BpmnModelInstance bpmnModelInstance = getBpmnModelInstance(task.getProcessDefinitionId());
        return bpmnModelInstance;
    }

    private BpmnModelInstance getBpmnModelInstance(String processDefinitionId){
        BpmnModelInstance bpmnModelInstance = repositoryService.getBpmnModelInstance(processDefinitionId);
        return bpmnModelInstance;
    }
}
