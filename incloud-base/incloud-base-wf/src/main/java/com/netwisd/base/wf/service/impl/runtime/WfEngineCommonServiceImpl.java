package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.vo.EmplVO;
import com.netwisd.base.wf.constants.EventTypeEnum;
import com.netwisd.base.wf.constants.InnerVariableEnum;
import com.netwisd.base.wf.constants.WfProcessLogEnum;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.feign.UserClient;
import com.netwisd.base.wf.service.WfProcdefService;
import com.netwisd.base.wf.service.procdef.WfEventParamDefService;
import com.netwisd.base.wf.service.procdef.WfNodeDefService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.util.WfEventUtils;
import com.netwisd.base.wf.vo.WfEventParamRuntimeVo;
import com.netwisd.base.wf.vo.WfEventRuntimeVo;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Comment;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/9 16:52
 */
@Service
@Slf4j
public class WfEngineCommonServiceImpl implements WfEngineCommonService {

    private final static String methodName = "notify";

    private final static String DESCRIPTION = "wfLocalDescription";

    private final static String ISAGRREE = "wfLocalIsagree";

    @Autowired
    WfNodeDefService wfNodeDefService;

    @Autowired
    WfDoneTaskService wfDoneTaskService;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    WfProcdefService wfProcdefService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    TaskService taskService;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> incloudCache;

    @Autowired
    Mapper dozerMapper;

    @Autowired
    WfEventParamDefService wfEventParamDefService;

    /**
    * 过期日期处理
    * @param processDefinitionId
    * @param variables
    * @param camundaNodeDefId
    */
    @Override
    public void dueDateHandle(String processDefinitionId,Map<String, Object> variables,String camundaNodeDefId){
        WfNodeDefVo entity = wfNodeDefService.getEntity(processDefinitionId, camundaNodeDefId);
        if(ObjectUtil.isEmpty(entity)){
            throw new IncloudException("未查询到节点");
        }
        if(ObjectUtil.isNotEmpty(entity.getDueDate())){
            Double dueDate = entity.getDueDate();
            double target = dueDate.doubleValue() * 24 * 3600 * 1000 + System.currentTimeMillis();
            DateTime date = DateUtil.date((long) target);
            variables.put(InnerVariableEnum.DUEDATE.getName(),date);
        }
    }

    @Override
    public void wfDoneTaskHandle(Task task,String targetActivityId) {
        WfDoneTask wfDoneTask = wfDoneTaskService.get(task.getId());
        WfProcess wfProcess = wfProcessService.get(task.getProcessInstanceId());
        if(ObjectUtil.isEmpty(wfDoneTask)){
            if(ObjectUtil.isNotEmpty(wfProcess)){
                initWfDoneTask(task,wfProcess);
                //更新流程日志
                WfProcessLog wfProcessLog = wfProcessLogService.get(task.getId(), WfProcessLogEnum.NONE.getType());
                if(ObjectUtil.isNotEmpty(wfProcessLog)){
                    updateWfProcessLog(task,wfProcessLog,targetActivityId);
                }
            }
        }
    }

    /**
     * 通过活动ID获取当前的instanceID
     * @param activityInstance
     * @param activityId
     * @return
     */
    @Override
    public String getInstanceIdForActivity(ActivityInstance activityInstance, String activityId) {
        ActivityInstance instance = getChildInstanceForActivity(activityInstance, activityId);
        if (instance != null) {
            return instance.getId();
        }
        return null;
    }

    @Override
    public void eventListenerInvoke(Task task,WfEventRuntimeVo wfEventRuntimeVo,String eventBindType){
        //获取targetBean
        Object targetBean = WfEventUtils.getTargetBean(wfEventRuntimeVo);
        Integer eventSubmitSign = wfEventRuntimeVo.getEventSubmitSign();
        Boolean submitSign = ObjectUtil.isEmpty(eventSubmitSign) || eventSubmitSign == 0 ? false : true;
        Integer eventType = wfEventRuntimeVo.getEventType();
        Boolean error = false;
        Class clazz = DelegateTask.class;
        try{
            List<WfEventParamRuntimeVo> eventParamsByConditions = wfEventParamDefService.
                    getEventParamsByConditions(wfEventRuntimeVo.getId(),task.getProcessDefinitionId(), task.getTaskDefinitionKey(),
                            EventTypeEnum.TASK_EVENT.code, eventBindType);
            WfEventUtils.handleWfEventParamRuntimeVo(eventParamsByConditions,targetBean);
            if(ObjectUtil.isNotEmpty(task)){
                if(EventTypeEnum.TASK_EVENT.code == eventType.intValue()){
                    TaskEntity taskEntity = (TaskEntity)task;
                    ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(targetBean.getClass(), methodName,clazz), targetBean, taskEntity);
                }else if(EventTypeEnum.EXE_EVENT.code == eventType.intValue()){
                    //todo 需要把task转换成DelegateExecution，这个后面实际用到比较多的时候，再实现
                    /*TaskEntity taskEntity = (TaskEntity)task;
                    ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(targetBean.getClass(), methodName,clazz), targetBean, taskEntity);*/
                }
            }
        }catch (Exception e){
            log.error("自定义事件执行失败");
            error = true;
        }
        if(submitSign && error){
            throw new IncloudException("事件异常，并且需要回滚！！！");
        }
    }

    /**
     * 递归获取其最终子实例ID
     * @param activityInstance
     * @param activityId
     * @return
     */
    ActivityInstance getChildInstanceForActivity(ActivityInstance activityInstance, String activityId) {
        if (activityId.equals(activityInstance.getActivityId())) {
            return activityInstance;
        }
        for (ActivityInstance childInstance : activityInstance.getChildActivityInstances()) {
            ActivityInstance instance = getChildInstanceForActivity(childInstance, activityId);
            if (instance != null) {
                return instance;
            }
        }
        return null;
    }

    /**
     * 创建用户任务时
     * @param task
     */
    private void initWfDoneTask(Task task,WfProcess wfProcess){
        WfTodoTask wfTodoTask = wfTodoTaskService.get(task.getId());
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
        WfDoneTask wfDoneTask = new WfDoneTask();
        dozerMapper.map(wfProcess,wfDoneTask);
        wfDoneTask.setId(null);
        wfDoneTask.setCamundaExectionId(task.getExecutionId());
        wfDoneTask.setCamundaProcdefId(task.getProcessDefinitionId());
        wfDoneTask.setCamundaProcdefKey(processDefinition.getKey());
        wfDoneTask.setCamundaProcdefVersion(processDefinition.getVersion());
        wfDoneTask.setCamundaProcinsId(task.getProcessInstanceId());
        wfDoneTask.setCamundaTaskId(task.getId());
        Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        log.info("initWfDoneTask中 execution值为{}",execution);
        if(execution instanceof ExecutionEntity){
            ExecutionEntity executionEntity = (ExecutionEntity) execution;
            String activityInstanceId = executionEntity.getActivityInstanceId();
            String currentActivityId = executionEntity.getCurrentActivityId();
            log.info("activityInstanceId：{},currentActivityId：{}",activityInstanceId,currentActivityId);
            wfDoneTask.setCamundaActInsId(activityInstanceId);
        }
        if(execution instanceof ProcessInstance){
            ProcessInstance processInstance = (ProcessInstance) execution;
            String processInstanceId = processInstance.getProcessInstanceId();
            wfDoneTask.setCamundaActInsId(processInstanceId);
        }
        wfDoneTask.setCliamTime(ObjectUtil.isNotEmpty(wfTodoTask) ? wfTodoTask.getCliamTime():null);
        wfDoneTask.setStartTime(ObjectUtil.isNotEmpty(wfTodoTask) ? wfTodoTask.getCreateTime():null);
        wfDoneTask.setEndTime(LocalDateTime.now());
        wfDoneTask.setProcinsId(wfProcess.getId());
        wfDoneTask.setCreateTime(LocalDateTime.now());
        wfDoneTask.setAssignee(task.getAssignee());
        if(StrUtil.isNotEmpty(task.getAssignee())){
            MdmUserVo mdmUserVo = incloudCache.get(task.getAssignee());
            wfDoneTask.setAssigneeName(mdmUserVo.getUserNameCh());
        }
        setCommonVar(task,wfDoneTask,wfProcess);
        wfDoneTaskService.save(wfDoneTask);
        log.info("事件保存已办任务成功！");
    }

    /**
     * 公共字段处理
     * @param task
     * @param wfDoneTask
     * @param wfProcess
     */
    private void setCommonVar(Task task,WfDoneTask wfDoneTask,WfProcess wfProcess){
        if(ObjectUtil.isNotEmpty(task.getDueDate())){
            wfDoneTask.setDueDate(LocalDateTime.ofInstant(task.getDueDate().toInstant(), ZoneId.systemDefault()));
        }
        wfDoneTask.setNodeKey(task.getTaskDefinitionKey());
        wfDoneTask.setNodeName(task.getName());
        wfDoneTask.setOwnner(task.getOwner());
        wfDoneTask.setPriority(task.getPriority());
        wfDoneTask.setState(wfProcess.getState());
        wfDoneTask.setTaskName(task.getName());
        setDescriptions(task,wfDoneTask);
        WfNodeDefVo wfNodeDefVo = wfNodeDefService.getNodeByProcDefIdAndNodeDefId(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
        if(ObjectUtil.isNotEmpty(wfNodeDefVo)){
            wfDoneTask.setNodeType(wfNodeDefVo.getNodeType());
        }
    }



    /**
     * 处理意见相关
     * @param task
     * @param wfDoneTask
     */
    private void setDescriptions(Task task,WfDoneTask wfDoneTask){
        if(ObjectUtil.isNotEmpty(task.getDescription())){
            wfDoneTask.setDecision(Integer.valueOf(task.getDescription()));
        }
        List<Comment> taskComments = taskService.getTaskComments(task.getId());
        if(CollectionUtil.isNotEmpty(taskComments)) {
            String message = taskComments.get(0).getFullMessage();
            Map map = JSONUtil.toBean(message, Map.class);
            wfDoneTask.setDescription(map.get(DESCRIPTION).toString());
            wfDoneTask.setIsAgree((Integer)map.get(ISAGRREE));
        }
    }

    /**
     * 更新流程日志
     * @param task
     * @param wfProcessLog
     */
    private void updateWfProcessLog(Task task, WfProcessLog wfProcessLog, String targetActivityId){
        WfNodeDefVo entity = wfNodeDefService.getEntity(task.getProcessDefinitionId(), targetActivityId);
        if(ObjectUtil.isEmpty(entity)){
            throw new IncloudException("未查询到节点");
        }
        wfProcessLog.setUpdateTime(LocalDateTime.now());
        wfProcessLog.setEndTime(LocalDateTime.now());
        wfProcessLog.setTargetNodeId(targetActivityId);
        wfProcessLog.setTargetNodeName(entity.getNodeName());
        wfProcessLog.setTargetNodeType(entity.getNodeType());
        setDescriptions(task,wfProcessLog);
        wfProcessLog.setType(wfProcessLog.getDecision());
        /*if(ObjectUtil.isNotEmpty(wfProcessLog.getDecision()) && wfProcessLog.getDecision() == 0){
            wfProcessLog.setType(WfProcessLogEnum.REJECT.getType());
        }*/
        wfProcessLogService.update(wfProcessLog);
        log.info("wfProcessLog保存成功！");
    }

    /**
     * 处理意见相关
     * @param task
     * @param wfProcessLog
     */
    private void setDescriptions(Task task,WfProcessLog wfProcessLog){
        if(ObjectUtil.isNotEmpty(task.getDescription())){
            wfProcessLog.setDecision(Integer.valueOf(task.getDescription()));
        }
        List<Comment> taskComments = taskService.getTaskComments(task.getId());
        if(CollectionUtil.isNotEmpty(taskComments)) {
            String message = taskComments.get(0).getFullMessage();
            Map map = JSONUtil.toBean(message, Map.class);
            wfProcessLog.setDescription(map.get(DESCRIPTION).toString());
            wfProcessLog.setIsAgree((Integer)map.get(ISAGRREE));
        }
    }
}
