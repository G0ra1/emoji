package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.vo.wf.WfMyInDuplicateTaskVo;
import com.netwisd.base.wf.constants.*;
import com.netwisd.base.wf.entity.WfDoneTask;
import com.netwisd.base.wf.entity.WfProcessLog;
import com.netwisd.base.wf.entity.WfTodoTask;
import com.netwisd.base.wf.service.procdef.WfEventDefService;
import com.netwisd.base.wf.service.procdef.WfEventParamDefService;
import com.netwisd.base.wf.service.procdef.WfNodeDefService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.util.WfEventUtils;
import com.netwisd.base.wf.vo.WfEventParamRuntimeVo;
import com.netwisd.base.wf.vo.WfEventRuntimeVo;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricTaskInstanceEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/13 10:15
 */
@Service
@Slf4j
public class WfEngineRevokeServiceImpl implements WfEngineRevokeService {

    private final static String methodName = "notify";

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    WfNodeDefService wfNodeDefService;

    @Autowired
    WfEngineCommonService wfEngineCommonService;

    @Autowired
    TaskService taskService;

    @Autowired
    WfEngineDelService wfEngineDelService;

    @Autowired
    HistoryService historyService;

    @Autowired
    WfDoneTaskService wfDoneTaskService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    WfEventDefService wfEventDefService;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    WfMyInDuplicateTaskService wfMyInDuplicateTaskService;

    @Autowired
    WfEventParamDefService wfEventParamDefService;

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> incloudCache;

    @Autowired
    Mapper dozerMapper;

    @Override
    public Boolean revoke(String camundaTaskId) {
        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery().taskId(camundaTaskId).singleResult();
        checkStaff(historicTaskInstance);

        String processInstanceId = historicTaskInstance.getProcessInstanceId();
        String processDefinitionId = historicTaskInstance.getProcessDefinitionId();
        String revokeTargetActivityId = historicTaskInstance.getTaskDefinitionKey();

        String userName = AppUserUtil.getLoginAppUserAndCheck().getUserName();
        Map<String, Object> taskVariable = new HashMap<>();
        taskVariable.put(InnerVariableEnum.ASSIGNEE.getName(), userName);
        WfDoneTask wfDoneTask = wfDoneTaskService.get(historicTaskInstance.getId());
        //处理过期时间
        wfEngineCommonService.dueDateHandle(processDefinitionId,taskVariable,revokeTargetActivityId);
        //根据当前（也就是已办的）任务ID，获取相应的流程日志
        WfProcessLog wfProcessLog = wfProcessLogService.get(historicTaskInstance.getId(),wfDoneTask.getDecision());
        //todo 暂时添加限制 驳回不让撤回
        if(WfProcessLogEnum.REJECT.getType() == wfProcessLog.getType()) {
            throw new IncloudException("驳回暂时不支持撤回！");
        }
        //获取当时任务下一步的目标节点
        String targetNodeId = wfProcessLog.getTargetNodeId();
        Integer targetNodeType = wfProcessLog.getTargetNodeType();
        if(targetNodeType.intValue() == NodeTypeEnum.CALLACTIVITY.code.intValue() || targetNodeType.intValue() == NodeTypeEnum.MULTIINSTANCECALLACTIVITYS.code.intValue()){
            throw new IncloudException("目标节点是一个子流程或外部调用流程，不能够撤回！");
        }
        //根据当前流程实例ID、任务节点ID以及当前活动任务标识，去获取相应的任务
        /**
         * 这个逻辑直白点说就是：我已经提交或驳回了；先在我的已办任务中找到任务，通过这个任务日志找到当时提交或驳回的目标节点KEY（比如叫他A）
         * 然后通过这个key找到当活动的任务实例；
         * ps:不加上taskDefinitionKey这个条件不对，因为可能是我提交A，A又往后提交了B；只找当前活动节点可能就找到B了；
         */
        //返回集合的原因是当前活动节点可能是个多实例
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId)
                .taskDefinitionKey(targetNodeId)
                .active().orderByTaskId().asc().list();
        if(list.isEmpty()){
            throw new IncloudException("目前节点或已签收处理过任务，不能撤回！");
        }
        //被撤回的目标节点
        WfNodeDefVo wfNodeDefVoTarget = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(wfDoneTask.getCamundaProcdefId(), targetNodeId);
        //撤回流程日志
        revokeProcessLog(wfDoneTask,wfNodeDefVoTarget);
        for (int i = 0; i < list.size(); i++) {
            Task task = list.get(i);
            log.info("目标撤回要取消的节点ID：{}",task.getTaskDefinitionKey());
            if(checkIsClaim(task,historicTaskInstance.getId())){
                throw new IncloudException("相关任务已被签收，不能被撤回！当前任务签收人：" + task.getAssignee());
            }
            //cancel会调用delete事件 所有有多个任务会触发多次事件 同时都删除WftodoTask
            if(i == list.size()-1) {
                runtimeService.createProcessInstanceModification(processInstanceId)
                        //cancel会调用delete事件，会让被撤回目标产生一条已办任务，不是我们想要的，后面要删掉他
                        .cancelAllForActivity(task.getTaskDefinitionKey())
                        .setAnnotation(userName+" 撤回")
                        .startBeforeActivity(revokeTargetActivityId)
                        .setVariables(taskVariable)
                        .execute();
            }
            //删除撤回目标所有待办
            wfEngineDelService.delWfTodoTaskByProInsAdnTaskId(processInstanceId,task.getId());
            //删除撤回目标所有待办对应的日志
            wfEngineDelService.delWfProcessLogByProInsAdnTaskId(processInstanceId,task.getId(),WfProcessLogEnum.NONE.getType());
        }

        //删除当前撤回节点所发出的传阅
        wfEngineDelService.delDuplicateByProInsAndTaskId(processInstanceId,historicTaskInstance.getId());
        //删除当前撤回节点所发出的传阅日志
        wfEngineDelService.delWfProcessLogByProInsAdnTaskId(processInstanceId,historicTaskInstance.getId(),WfProcessLogEnum.DUPLICATE.getType());
        //删除当前人已办 ———— 但已办日志保留，不删除，要留痕
        wfEngineDelService.delWfDoneTask(historicTaskInstance.getId());

        //根据是否回滚和提交前提交后，在handle方法中调用这个，如果需要回滚，则handle抛出异常
        //这里找的是当前撤回节点上定义的撤回事件
        List<WfEventRuntimeVo> wfEventRuntimeVoList = wfEventDefService.getEventByConditions(processDefinitionId,
                historicTaskInstance.getTaskDefinitionKey(), //撤回节点的key
                EventTypeEnum.TASK_EVENT.code,
                EventBindTypeEnum.REVOKE.code);
        if(ObjectUtil.isNotEmpty(wfEventRuntimeVoList)){
            for(WfEventRuntimeVo wfEventRuntimeVo : wfEventRuntimeVoList){
                //调用撤回自定义事件
                eventListenerInvoke4Revoke(historicTaskInstance,EventBindTypeEnum.REVOKE.code,wfEventRuntimeVo);
            }
        }
        log.info("---revoke---撤回成功");
        return true;
    }

    /**
     * 一些基本的检验
     * @param historicTaskInstance
     */
    void checkStaff(HistoricTaskInstance historicTaskInstance){
        if(ObjectUtil.isEmpty(historicTaskInstance)){
            throw new IncloudException("查找不到相应的任务！");
        }
        wfProcessService.checkProcessState(historicTaskInstance.getProcessInstanceId());
        /*WfNodeDetailDefVo wfNodeDetailDefVo = wfNodeDetailDefService.getByNodeDef(historicTaskInstance.getProcessDefinitionId(), historicTaskInstance.getTaskDefinitionKey());*/
        WfNodeDefVo wfNodeDefVo = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(historicTaskInstance.getProcessDefinitionId(), historicTaskInstance.getTaskDefinitionKey());
        if(ObjectUtil.isEmpty(wfNodeDefVo)){
            throw new IncloudException("查找不到当前节点定义");
        }
        Integer cancelRule = wfNodeDefVo.getCancelRule();
        Integer nodeType = wfNodeDefVo.getNodeType();
        if(cancelRule.intValue() == BooleanEnum.FALSE.getValue()){
            throw new IncloudException("要撤回的节点设置不允许撤回，请检查流程定义！");
        }
        if(nodeType.intValue() == NodeTypeEnum.MULTIINSTANCETASK.code.intValue()){
            throw new IncloudException("要撤回的节点为会签节点,无法撤回！");
        }
    }

    /**
     * 撤回驳回流程日志
     * @param wfDoneTask
     */
    private void revokeProcessLog(WfDoneTask wfDoneTask,WfNodeDefVo wfNodeDefVo){
        WfProcessLog wfProcessLog = new WfProcessLog();
        dozerMapper.map(wfDoneTask,wfProcessLog);
        wfProcessLog.setId(null);
        wfProcessLog.setNodeId(wfDoneTask.getNodeKey());
        wfProcessLog.setStartTime(LocalDateTime.now());
        wfProcessLog.setEndTime(LocalDateTime.now());
        wfProcessLog.setCreateTime(LocalDateTime.now());
        wfProcessLog.setUpdateTime(LocalDateTime.now());
        wfProcessLog.setType(WfProcessLogEnum.REVOKE.getType());
        wfProcessLog.setTargetNodeId(wfNodeDefVo.getCamundaNodeDefId());
        wfProcessLog.setTargetNodeName(wfNodeDefVo.getNodeName());
        wfProcessLog.setTargetNodeType(wfNodeDefVo.getNodeType());
        setUserInfo(wfDoneTask.getAssignee(),wfProcessLog);
        wfProcessLog.setDecision(WfProcessLogEnum.REVOKE.getType());
        /*wfProcessLog.setDescription(handleDto.getWfLocalDescription());
        wfProcessLog.setIsAgree(handleDto.getWfLocalIsAgree());*/
        wfProcessLogService.save(wfProcessLog);
        log.info("撤回wfProcessLog保存成功！");
    }

    private void setUserInfo(String userName,WfProcessLog wfProcessLog){
        MdmUserVo mdmUserVo = incloudCache.get(userName);
        if(ObjectUtil.isEmpty(mdmUserVo)){
            throw new IncloudException("查找不到用户！");
        }
        wfProcessLog.setUserName(userName);
        wfProcessLog.setDeptId(String.valueOf(mdmUserVo.getParentDeptId()));
        wfProcessLog.setDeptName(mdmUserVo.getParentDeptName());
        wfProcessLog.setOrgId(String.valueOf(mdmUserVo.getParentOrgId()));
        wfProcessLog.setOrgName(mdmUserVo.getParentOrgName());
        wfProcessLog.setUserNameCh(mdmUserVo.getUserNameCh());
    }

    private Boolean checkIsClaim(Task task,String camundaTaskId){
        WfTodoTask wfTodoTask = wfTodoTaskService.get(task.getId());
        if(ObjectUtil.isEmpty(wfTodoTask)){
            throw new IncloudException("根据taskId找不到WfTodoTask");
        }
        LocalDateTime cliamTime = wfTodoTask.getCliamTime();
        Boolean sign = false;
        //没被签收
        if(ObjectUtil.isEmpty(cliamTime)){
            //检查是否有传阅
            List<WfMyInDuplicateTaskVo> list = wfMyInDuplicateTaskService.getList(camundaTaskId);
            if(ObjectUtil.isNotEmpty(list)){
                for(WfMyInDuplicateTaskVo wfMyInDuplicateTaskVo :list){
                    LocalDateTime cliamTimeDup = wfMyInDuplicateTaskVo.getCliamTime();
                    if(ObjectUtil.isNotEmpty(cliamTimeDup)){
                        sign = true;
                        break;
                    }
                }
            }
        }else {
            sign = true;
        }
        return sign;
    }

    /**
     * eventListenerInvoke4Revoke
     * @param wfEventRuntimeVo
     * @return
     */
    @SneakyThrows
    public void eventListenerInvoke4Revoke(HistoricTaskInstance historicTaskInstance,String eventBindType,WfEventRuntimeVo wfEventRuntimeVo){
        String processDefinitionId = historicTaskInstance.getProcessDefinitionId();
        String taskDefinitionKey = historicTaskInstance.getTaskDefinitionKey();
        //获取targetBean
        Object targetBean = WfEventUtils.getTargetBean(wfEventRuntimeVo);
        Integer eventSubmitSign = wfEventRuntimeVo.getEventSubmitSign();
        Boolean submitSign = ObjectUtil.isEmpty(eventSubmitSign) || eventSubmitSign == 0 ? false : true;
        Integer eventType = wfEventRuntimeVo.getEventType();
        List<WfEventParamRuntimeVo> eventParamsByConditions = wfEventParamDefService.
                getEventParamsByConditions(wfEventRuntimeVo.getDefEvefId(),processDefinitionId, taskDefinitionKey,
                        EventTypeEnum.TASK_EVENT.code, eventBindType);
        WfEventUtils.handleWfEventParamRuntimeVo(eventParamsByConditions,targetBean);
        Class<?> clazz = null;
        Boolean error = false;
        if(EventTypeEnum.TASK_EVENT.code == eventType.intValue()){
            clazz = DelegateTask.class;
            try{
                if(ObjectUtil.isNotEmpty(historicTaskInstance)){
                    HistoricTaskInstanceEntity historicTaskInstanceEntity = (HistoricTaskInstanceEntity)historicTaskInstance;
                    TaskEntity taskEntity = new TaskEntity();
                    taskEntity.setProcessDefinitionId(historicTaskInstanceEntity.getProcessDefinitionId());
                    taskEntity.setProcessInstanceId(historicTaskInstanceEntity.getProcessInstanceId());
                    ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(targetBean.getClass(), methodName,clazz), targetBean, taskEntity);
                }
            }catch (Exception e){
                log.error("自定义事件执行失败");
                error = true;
            }
        }else if(EventTypeEnum.EXE_EVENT.code == eventType.intValue()){
            //todo 暂不实现
           /* clazz = DelegateExecution.class;
            try{
                ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(targetBean.getClass(), methodName,clazz), targetBean, execution);
            }catch (Exception e){
                log.error("自定义事件执行失败");
                error = true;
            }*/
        }
        if(submitSign && error){
            throw new IncloudException("事件异常，回滚！");
        }
    }
}