package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.netwisd.base.wf.constants.*;
import com.netwisd.base.wf.service.procdef.WfEventDefService;
import com.netwisd.base.wf.service.procdef.WfNodeDefService;
import com.netwisd.base.wf.service.procdef.WfVarDefService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.vo.WfEventRuntimeVo;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import com.netwisd.base.wf.vo.WfVarDefVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPut;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/13 10:14
 */
@Service
@Slf4j
public class WfEngineRejectServiceImpl implements WfEngineRejectService {

    private final static String DESCRIPTION = "wfLocalDescription";

    private final static String ISAGRREE = "wfLocalIsagree";

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    WfNodeDefService wfNodeDefService;

    @Autowired
    WfEngineCommonService wfEngineCommonService;

    @Autowired
    WfEngineDuplicateService wfEngineDuplicateService;

    @Autowired
    WfEngineDelService wfEngineDelService;

    @Autowired
    WfEventDefService wfEventDefService;

    @Autowired
    WfVarDefService wfVarDefService;

    @Autowired
    WfTaskService wfTaskService;

    @Autowired
    WfGetDtoService wfGetDtoService;

    @Autowired
    WfEngineBizDataService wfEngineBizDataService;

    /**
     * 目前驳回不支持驳回到会签节点
     * @param handleDto
     * @return
     */
    @Override
    public Boolean reject(WfEngineDto.HandleDto handleDto) {
        //执行下业务处理
        invoke(handleDto);
        Task task = wfTaskService.getAndCheckClaim(handleDto.getCamundaTaskId());
        wfProcessService.checkProcessState(task.getProcessInstanceId());
        WfNodeDefVo wfNodeDefVo = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
        if(ObjectUtil.isEmpty(wfNodeDefVo)){
            throw new IncloudException("查找不到当前节点定义");
        }
        Integer returnRule = wfNodeDefVo.getReturnRule();
        if(returnRule == 0){
            throw new IncloudException("节点设置不允许驳回，请检查流程定义！");
        }
        //因为暂时不支持驳回到会签节点，这里只会取驳回到目标的原审批人，只有一个人；
        //String targetAssignee = handleDto.getWfAssignee().get(0);
        //驳回的目标节点
        String targetActivityId = handleDto.getTargetActivityId();

        rejectTargetActivity(handleDto,task,WfProcessLogEnum.REJECT);
        //根据是否回滚和提交前提交后，在handle方法中调用这个，如果需要回滚，则handle抛出异常
        List<WfEventRuntimeVo> wfEventRuntimeVoList = wfEventDefService.getEventByConditions(task.getProcessDefinitionId(),
                targetActivityId, //驳回的目标节点
                EventTypeEnum.TASK_EVENT.code,
                EventBindTypeEnum.REJECTED.code);
        if(ObjectUtil.isNotEmpty(wfEventRuntimeVoList)){
            for(WfEventRuntimeVo wfEventRuntimeVo : wfEventRuntimeVoList){
                //调用自定义返回事件
                /*eventListenerInvoke4Reject(task,targetActivityId,targetAssignee,EventBindTypeEnum.REJECTED.code,wfEventRuntimeVo);*/
                wfEngineCommonService.eventListenerInvoke(task,wfEventRuntimeVo,EventBindTypeEnum.REJECTED.code);
            }
        }
        log.info("---reject---驳回成功");
        return true;
    }

    void invoke(WfEngineDto.HandleDto handleDto){
        for (WfEngineDto.BizData bizData : handleDto.getBizDataList()){
            WfDto wfDto = wfGetDtoService.returnData(handleDto.getCamundaTaskId(),null);
            wfEngineBizDataService.invoke(bizData,wfDto, BizMethodTypeEnum.UPDATE);
        }
    }

    @Override
    public void rejectTargetActivity(WfEngineDto.HandleDto handleDto,Task task,WfProcessLogEnum wfProcessLogEnum){
        //因为暂时不支持驳回到会签节点，这里只会取驳回到目标的原审批人，只有一个人；
        String targetAssignee = handleDto.getWfAssignee().get(0);
        //驳回的目标节点
        String targetActivityId = handleDto.getTargetActivityId();
        String processInstanceId = task.getProcessInstanceId();
        log.info("---reject---当前任务的流程实例ID",processInstanceId);
        log.info("---reject---驳回的目标活动ID：{}，目标受理人：{}",targetActivityId,targetAssignee);
        ActivityInstance activityInstance = runtimeService.getActivityInstance(task.getProcessInstanceId());
        log.info("---reject---当前任务所对应的流程定义ID:{},executionID:{}",activityInstance.getActivityId(),activityInstance.getId());
        String currentActivityId = wfEngineCommonService.getInstanceIdForActivity(activityInstance,task.getTaskDefinitionKey());
        log.info("---reject---获取当前activity实例ID：{}",currentActivityId);

        //传阅 其实就是自定义创建一个Task,并关联上相应的流程实例信息
        List<String> wfDuplicateList = handleDto.getWfDuplicateList();
        if(ObjectUtil.isNotEmpty(wfDuplicateList) && wfDuplicateList.size() > 0){
            wfEngineDuplicateService.createDuplicate(handleDto,task);
        }

        //设置目标节点对应的过程变量
        Map<String, Object> taskVariable = runtimeService.getVariables(task.getExecutionId());
        taskVariable.put(InnerVariableEnum.ASSIGNEE.getName(), targetAssignee);
        task.setDescription(String.valueOf(wfProcessLogEnum.getType()));
        //驳回原因
        String json = JSONUtil.createObj().set(DESCRIPTION,handleDto.getWfLocalDescription()).set(ISAGRREE,handleDto.getWfLocalIsAgree()).toStringPretty();
        taskService.createComment(task.getId(),processInstanceId,json);
        taskService.saveTask(task);

        //处理过期时间
        wfEngineCommonService.dueDateHandle(task.getProcessDefinitionId(),taskVariable,targetActivityId);
        //删除待办
        wfEngineDelService.delWfTodoTask(task);
        //产生已办和日志
        wfEngineCommonService.wfDoneTaskHandle(task,targetActivityId);
        //驳回
        runtimeService.createProcessInstanceModification(processInstanceId)
                //只需要cancel掉当前活动的实例，之前的不用，因为之前的已经完成——，这个前提是没有并行execution，我们暂不考虑并行网关这种；
                .cancelActivityInstance(currentActivityId)//关闭相关任务
                .setAnnotation(handleDto.getWfLocalDescription())
                .startBeforeActivity(targetActivityId)//启动目标活动节点
                .setVariables(taskVariable)//流程的可变参数赋值
                .execute();
        WfNodeDefVo wfNodeDefVoTarget = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(task.getProcessDefinitionId(), targetActivityId);
        if(ObjectUtil.isEmpty(wfNodeDefVoTarget)){
            throw new IncloudException("找不到要驳回的目标节点！");
        }
    }

    /**
     * 获取驳回需要的变量
     * @param taskId
     * @return
     */
    @Override
    public Set<String> getRejectVariables(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        List<WfVarDefVo> varByProcDefIdAndNodeDefId = wfVarDefService.getVarByProcDefIdAndNodeDefId(task.getProcessDefinitionId(), task.getTaskDefinitionKey(),null);
        Set<String> resultSet = new LinkedHashSet<>();
        if(ObjectUtil.isNotEmpty(varByProcDefIdAndNodeDefId) && !varByProcDefIdAndNodeDefId.isEmpty()){
            varByProcDefIdAndNodeDefId.forEach(wfVarDefVo -> {
                resultSet.add(wfVarDefVo.getJavaName());
            });
        }
        return resultSet;
    }
}