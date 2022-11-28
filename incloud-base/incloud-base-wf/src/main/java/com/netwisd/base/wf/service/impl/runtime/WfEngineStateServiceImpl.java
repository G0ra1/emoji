package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.constants.EventBindTypeEnum;
import com.netwisd.base.wf.constants.EventTypeEnum;
import com.netwisd.base.wf.constants.WfProcessLogEnum;
import com.netwisd.base.wf.dto.WfProcessLogDto;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.entity.WfTodoTask;
import com.netwisd.base.wf.service.procdef.WfEventDefService;
import com.netwisd.base.wf.service.procdef.WfEventParamDefService;
import com.netwisd.base.wf.service.procdef.WfNodeDefService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.util.WfEventUtils;
import com.netwisd.base.wf.vo.WfEventParamRuntimeVo;
import com.netwisd.base.wf.vo.WfEventRuntimeVo;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;
import java.time.LocalDateTime;
import java.util.List;
/**
 * @author zouliming@netwisd.com
 * @description 流程状态操作相关的
 * @date 2021/12/6 14:47
 */

@Service
@Slf4j
public class WfEngineStateServiceImpl implements WfEngineStateService {

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    WfDoneTaskService wfDoneTaskService;

    @Autowired
    WfMyInDuplicateTaskService wfMyInDuplicateTaskService;

    @Autowired
    WfEventDefService wfEventDefService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    WfMyOutDuplicateTaskService wfMyOutDuplicateTaskService;

    @Autowired
    WfNodeDefService wfNodeDefService;

    @Autowired
    WfEventParamDefService wfEventParamDefService;

    @Autowired
    TaskService taskService;

    private final static String methodName = "notify";

    @Transactional
    @Override
    public Boolean suspendProcess(WfEngineDto.StateDto stateDto) {
        String camundaProcInsId = stateDto.getCamundaProcinsId();
        WfProcess wfProcess = wfProcessService.get(camundaProcInsId);
        if(ObjectUtil.isEmpty(wfProcess)){
            throw new IncloudException("通过camundaProcessInstanceId查找不到WfProcess");
        }
        if(wfProcess.getState().intValue() != WfProcessEnum.RUNNING.getType()){
            throw new IncloudException("流程不在运行中，不能挂起！");
        }
        //流程实例挂起更新
        wfProcessService.updateStateByProInsId(camundaProcInsId, WfProcessEnum.SUPENSION.getType());
        //待办任务挂起
        wfTodoTaskService.updateStateByProInsId(camundaProcInsId,WfProcessEnum.SUPENSION.getType());
        //已办任务挂起
        wfDoneTaskService.updateStateByProInsId(camundaProcInsId,WfProcessEnum.SUPENSION.getType());
        //传阅任务挂起
        wfMyInDuplicateTaskService.updateStateByProInsId(camundaProcInsId,WfProcessEnum.SUPENSION.getType());
        //流程日志
        WfProcessLogDto wfProcessLogDto = new WfProcessLogDto();
        wfProcessLogDto.setType(WfProcessLogEnum.SUSPEND.getType());
        wfProcessLogDto.setDecision(WfProcessLogEnum.SUSPEND.getType());
        wfProcessLogDto.setDescription(WfProcessLogEnum.SUSPEND.getName());
        setUserInfo(wfProcessLogDto,camundaProcInsId);
        WfTodoTask wfTodoTask = getTask(stateDto);
        setOtherInfo(wfProcessLogDto,wfTodoTask,stateDto);
        wfProcessLogService.save(wfProcessLogDto);
        //处理业务事件
        List<WfEventRuntimeVo> wfEventRuntimeVoList = wfEventDefService.getEventByConditions(stateDto.getCamundaProcdefId(),
                null, //流程定义的事件，节点传空
                EventTypeEnum.EXE_EVENT.code,
                EventBindTypeEnum.SUSPEND_PROCESS.code);
        if(ObjectUtil.isNotEmpty(wfEventRuntimeVoList)){
            for(WfEventRuntimeVo wfEventRuntimeVo : wfEventRuntimeVoList){
                //调用自定义返回事件
                eventListenerInvoke4Def(wfTodoTask,stateDto.getCamundaProcinsId(),stateDto.getCamundaProcdefId()
                        ,EventBindTypeEnum.SUSPEND_PROCESS.code,wfEventRuntimeVo);
            }
        }
        //最后台让他挂起
        runtimeService.suspendProcessInstanceById(camundaProcInsId);
        log.info("流程挂起成功");
        return true;
    }

    @Transactional
    @Override
    public Boolean endProcess(WfEngineDto.StateDto stateDto) {
        String camundaProcInsId = stateDto.getCamundaProcinsId();
        WfProcess wfProcess = wfProcessService.get(camundaProcInsId);
        if(ObjectUtil.isEmpty(wfProcess)){
            throw new IncloudException("通过camundaProcessInstanceId查找不到WfProcess");
        }
        if(wfProcess.getState().intValue() == WfProcessEnum.DONE.getType()){
            throw new IncloudException("流程已完成，不能终止！");
        }
        if(wfProcess.getState().intValue() == WfProcessEnum.TERMINATION.getType()){
            throw new IncloudException("流程已终止，不能再次终止！");
        }

        //流程实例挂起更新
        wfProcessService.updateStateByProInsId(camundaProcInsId,WfProcessEnum.TERMINATION.getType());
        //待办任务挂起
        wfTodoTaskService.updateStateByProInsId(camundaProcInsId,WfProcessEnum.TERMINATION.getType());
        //已办任务挂起
        wfDoneTaskService.updateStateByProInsId(camundaProcInsId,WfProcessEnum.TERMINATION.getType());
        //传阅任务挂起
        wfMyInDuplicateTaskService.updateStateByProInsId(camundaProcInsId,WfProcessEnum.TERMINATION.getType());
        //流程日志
        WfProcessLogDto wfProcessLogDto = new WfProcessLogDto();
        wfProcessLogDto.setType(WfProcessLogEnum.TERMINATION.getType());
        wfProcessLogDto.setDecision(WfProcessLogEnum.TERMINATION.getType());
        wfProcessLogDto.setDescription(WfProcessLogEnum.TERMINATION.getName());
        wfProcessLogDto.setEndTime(LocalDateTime.now());
        setUserInfo(wfProcessLogDto,camundaProcInsId);
        WfTodoTask wfTodoTask = getTask(stateDto);
        setOtherInfo(wfProcessLogDto,wfTodoTask,stateDto);
        wfProcessLogService.save(wfProcessLogDto);

        //处理业务事件
        List<WfEventRuntimeVo> wfEventRuntimeVoList = wfEventDefService.getEventByConditions(stateDto.getCamundaProcdefId(),
                null, //流程定义的事件，节点传空
                EventTypeEnum.EXE_EVENT.code,
                EventBindTypeEnum.END_PROCESS.code);
        if(ObjectUtil.isNotEmpty(wfEventRuntimeVoList)){
            for(WfEventRuntimeVo wfEventRuntimeVo : wfEventRuntimeVoList){
                //调用自定义返回事件
                eventListenerInvoke4Def(wfTodoTask,stateDto.getCamundaProcinsId(),stateDto.getCamundaProcdefId()
                        ,EventBindTypeEnum.END_PROCESS.code,wfEventRuntimeVo);
            }
        }
        //最后台让他挂起
        runtimeService.suspendProcessInstanceById(camundaProcInsId);
        log.info("流程终止成功");
        return true;
    }

    @Transactional
    @Override
    public Boolean activateProcess(WfEngineDto.StateDto stateDto) {
        String camundaProcInsId = stateDto.getCamundaProcinsId();
        WfProcess wfProcess = wfProcessService.get(camundaProcInsId);
        if(ObjectUtil.isEmpty(wfProcess)){
            throw new IncloudException("通过camundaProcessInstanceId查找不到WfProcess");
        }
        if(wfProcess.getState().intValue() != WfProcessEnum.SUPENSION.getType()){
            throw new IncloudException("流程运行中、完成、终止等状态下不能激活！");
        }
        runtimeService.activateProcessInstanceById(camundaProcInsId);
        //流程实例激活更新
        wfProcessService.updateStateByProInsId(camundaProcInsId,WfProcessEnum.RUNNING.getType());
        //待办任务激活
        wfTodoTaskService.updateStateByProInsId(camundaProcInsId,WfProcessEnum.RUNNING.getType());
        //已办任务激活
        wfDoneTaskService.updateStateByProInsId(camundaProcInsId,WfProcessEnum.RUNNING.getType());
        //传阅任务激活
        wfMyInDuplicateTaskService.updateStateByProInsId(camundaProcInsId,WfProcessEnum.RUNNING.getType());
        //流程日志
        WfProcessLogDto wfProcessLogDto = new WfProcessLogDto();
        wfProcessLogDto.setType(WfProcessLogEnum.ACTIVATE.getType());
        wfProcessLogDto.setDecision(WfProcessLogEnum.ACTIVATE.getType());
        wfProcessLogDto.setDescription(WfProcessLogEnum.ACTIVATE.getName());
        setUserInfo(wfProcessLogDto,camundaProcInsId);
        WfTodoTask wfTodoTask = getTask(stateDto);
        setOtherInfo(wfProcessLogDto,wfTodoTask,stateDto);
        wfProcessLogService.save(wfProcessLogDto);
        //处理业务事件
        List<WfEventRuntimeVo> wfEventRuntimeVoList = wfEventDefService.getEventByConditions(stateDto.getCamundaProcdefId(),
                null, //流程定义的事件，节点传空
                EventTypeEnum.EXE_EVENT.code,
                EventBindTypeEnum.ACTIVATE_PROCESS.code);
        if(ObjectUtil.isNotEmpty(wfEventRuntimeVoList)){
            for(WfEventRuntimeVo wfEventRuntimeVo : wfEventRuntimeVoList){
                //调用自定义返回事件
                eventListenerInvoke4Def(wfTodoTask,stateDto.getCamundaProcinsId(),stateDto.getCamundaProcdefId()
                        ,EventBindTypeEnum.ACTIVATE_PROCESS.code,wfEventRuntimeVo);
            }
        }
        log.info("流程激活成功");
        return true;
    }

    @Transactional
    @Override
    public Boolean deleteProcess(WfEngineDto.StateDto stateDto) {
        String camundaProcInsId = stateDto.getCamundaProcinsId();
        WfTodoTask wfTodoTask = getTask(stateDto);
        //处理业务事件
        List<WfEventRuntimeVo> wfEventRuntimeVoList = wfEventDefService.getEventByConditions(stateDto.getCamundaProcdefId(),
                null, //流程定义的事件，节点传空
                EventTypeEnum.EXE_EVENT.code,
                EventBindTypeEnum.DELETE_PROCESS.code);
        if(ObjectUtil.isNotEmpty(wfEventRuntimeVoList)){
            for(WfEventRuntimeVo wfEventRuntimeVo : wfEventRuntimeVoList){
                //调用自定义返回事件
                eventListenerInvoke4Def(wfTodoTask,stateDto.getCamundaProcinsId(),stateDto.getCamundaProcdefId()
                        ,EventBindTypeEnum.DELETE_PROCESS.code,wfEventRuntimeVo);
            }
        }
        //删除多张表，包括：流程实例、待办任务、已办任务、传阅任务、流程日志；
        wfProcessService.delete(camundaProcInsId);
        wfTodoTaskService.deleteProcinsId(camundaProcInsId);
        wfDoneTaskService.deleteProcinsId(camundaProcInsId);
        wfMyInDuplicateTaskService.deleteProcinsId(camundaProcInsId);
        wfMyOutDuplicateTaskService.deleteProcinsId(camundaProcInsId);
        wfProcessLogService.delete(camundaProcInsId);
        runtimeService.deleteProcessInstance(camundaProcInsId,"流程实例删除！！！",true);
        log.info("流程删除成功");
        return true;
    }

    @Override
    public Boolean deleteOnlyProcess(WfEngineDto.StateDto stateDto) {
        String camundaProcInsId = stateDto.getCamundaProcinsId();
        //删除多张表，包括：流程实例、待办任务、已办任务、传阅任务、流程日志；
        wfProcessService.delete(camundaProcInsId);
        wfTodoTaskService.deleteProcinsId(camundaProcInsId);
        wfDoneTaskService.deleteProcinsId(camundaProcInsId);
        wfMyInDuplicateTaskService.deleteProcinsId(camundaProcInsId);
        wfMyOutDuplicateTaskService.deleteProcinsId(camundaProcInsId);
        wfProcessLogService.delete(camundaProcInsId);
        runtimeService.deleteProcessInstance(camundaProcInsId,"流程实例删除！！！",true);
        log.info("流程删除成功");
        return true;
    }

    /**
     * 设置流程日志其他信息
     * @param wfProcessLogDto
     * @param wfTodoTask
     */
    private void setOtherInfo(WfProcessLogDto wfProcessLogDto,WfTodoTask wfTodoTask,WfEngineDto.StateDto stateDto){
        String camundaProcInsId  = stateDto.getCamundaProcinsId();
        if(ObjectUtil.isNotEmpty(wfTodoTask)){
            wfProcessLogDto.setCamundaTaskId(wfTodoTask.getCamundaTaskId());
            /*wfProcessLogDto.setFormKey(wfTodoTask.getFormKey());*/
            wfProcessLogDto.setNodeId(wfTodoTask.getNodeKey());
            wfProcessLogDto.setNodeName(wfTodoTask.getNodeName());
            wfProcessLogDto.setNodeType(wfTodoTask.getNodeType());

        }else{
            //获取当前的活动节点，因为上面已经处理了非done的情况，所以，只要流程不正常结束，就有活动节点；
            /*ActivityInstance activityInstance = runtimeService.getActivityInstance(camundaProcInsId);
            log.info("activityInstance:{}",activityInstance.getId());*/
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(camundaProcInsId).singleResult();
            if(ObjectUtil.isEmpty(processInstance)){
                throw new IncloudException("查找不到流程实例！");
            }
            //因为挂起了，获取不到相应活动节点信息，直接返回吧；
            if(processInstance.isSuspended()){
                return;
            }
            List<Execution> list = runtimeService.createExecutionQuery().processInstanceId(camundaProcInsId).active().list();
            if(ObjectUtil.isEmpty(list) || list.isEmpty()){
                throw new IncloudException("当前流程没有活动节点，请检查流程状态和数据！");
            }
            //只取第一个。。。这里注意，他的前提是：我们不考虑同时有多个节点并行（不是会签，会签是一个节点多实例），并行网关的情况就是并行多节点
            Execution execution = list.stream().findFirst().get();
            List<String> activeActivityIds = runtimeService.getActiveActivityIds(execution.getId());
            if(ObjectUtil.isNotEmpty(activeActivityIds) &&!activeActivityIds.isEmpty()){
                //因为没有并行网关，不会在同一时间会有多条Execution执行流存在，同样道理，因为没有并行网关，那么在同一个执行流上，也不会存在并行的多个节点同时存在；
                String camundaNodeDefId = activeActivityIds.stream().findFirst().get();
                log.info("actId为：{}",camundaNodeDefId);
                wfProcessLogDto.setNodeId(camundaNodeDefId);
                WfNodeDefVo wfNodeDefVo = wfNodeDefService.getEntity(stateDto.getCamundaProcdefId(), camundaNodeDefId);
                if(ObjectUtil.isEmpty(wfNodeDefVo)){
                    throw new IncloudException("根据camundaNodeDefId："+camundaNodeDefId+"找不到相应的对象，请检查流程定义配置!");
                }
                wfProcessLogDto.setNodeName(wfNodeDefVo.getNodeName());
                wfProcessLogDto.setNodeType(wfNodeDefVo.getNodeType());
            }
            wfProcessLogDto.setUpdateTime(LocalDateTime.now());
        }
    }

    /**
     * 设置用户信息并保存
     * @param wfProcessLogDto
     * @param processInstanceId
     */
    void setUserInfo(WfProcessLogDto wfProcessLogDto,String processInstanceId){
        WfProcess wfProcess = wfProcessService.get(processInstanceId);
        if(ObjectUtil.isEmpty(wfProcess)){
            throw new IncloudException("通过camundaProcessInstanceId查找不到WfProcess");
        }
        wfProcessLogDto.setStartTime(LocalDateTime.now());
        wfProcessLogDto.setCreateTime(LocalDateTime.now());
        wfProcessLogDto.setCamundaProcinsId(processInstanceId);
        wfProcessLogDto.setProcinsId(wfProcess.getId());
        wfProcessLogDto.setParentProcinsId(wfProcess.getParentProcinsId());
        wfProcessLogDto.setCamundaParentProcinsId(wfProcess.getCamundaParentProcinsId());
        wfProcessLogDto.setCamundaProcdefId(wfProcess.getCamundaProcdefId());
        wfProcessLogDto.setCamundaProcdefKey(wfProcess.getCamundaProcdefKey());
        wfProcessLogDto.setCamundaProcdefVersion(wfProcess.getCamundaProcdefVersion());
        wfProcessLogDto.setProcdefId(wfProcess.getProcdefId());
        wfProcessLogDto.setProcdefName(wfProcess.getProcdefName());
        wfProcessLogDto.setProcinsName(wfProcess.getProcinsName());
        wfProcessLogDto.setProcdefTypeId(wfProcess.getProcdefTypeId());
        wfProcessLogDto.setProcdefTypeName(wfProcess.getProcdefTypeName());

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(ObjectUtil.isEmpty(loginAppUser)){
            throw new IncloudException("请先登录再操作！");
        }
        wfProcessLogDto.setUserName(loginAppUser.getUserName());
        wfProcessLogDto.setUserNameCh(loginAppUser.getUserNameCh());
        wfProcessLogDto.setDeptId(String.valueOf(loginAppUser.getParentDeptId()));
        wfProcessLogDto.setDeptName(loginAppUser.getParentDeptName());
        wfProcessLogDto.setOrgId(loginAppUser.getOrgFullId());
        wfProcessLogDto.setOrgName(loginAppUser.getOrgFullName());
    }

    /**
     * 获取任务信息
     * @param stateDto
     * @return
     */
    WfTodoTask getTask(WfEngineDto.StateDto stateDto){
        String camundaTaskId = stateDto.getCamundaTaskId();
        WfTodoTask wfTodoTask = null;
        if(StrUtil.isNotEmpty(camundaTaskId)){
            wfTodoTask = wfTodoTaskService.get(camundaTaskId);
            if(ObjectUtil.isEmpty(wfTodoTask)){
                throw new IncloudException("根据camundaTaskId查找不到相应任务");
            }
        }
        return wfTodoTask;
    }

    /**
     * 流程挂起、激活、终止、删除的自定义事件
     * @param wfTodoTask
     * @param processInstanceId
     * @param processDefinitionId
     * @param eventBindType
     * @param wfEventRuntimeVo
     */
    @SneakyThrows
    public void eventListenerInvoke4Def(WfTodoTask wfTodoTask,String processInstanceId,String processDefinitionId,
                                        String eventBindType,WfEventRuntimeVo wfEventRuntimeVo){
        List<WfEventParamRuntimeVo> eventParamsByConditions = wfEventParamDefService.
                getEventParamsByConditions(wfEventRuntimeVo.getDefEvefId(),processDefinitionId, null,
                        EventTypeEnum.EXE_EVENT.code, eventBindType);
        //获取targetBean
        Object targetBean = WfEventUtils.getTargetBean(wfEventRuntimeVo);
        //回滚标识
        Integer eventSubmitSign = wfEventRuntimeVo.getEventSubmitSign();
        Boolean rollBackSign = ObjectUtil.isEmpty(eventSubmitSign) || eventSubmitSign == 0 ? false : true;

        WfEventUtils.handleWfEventParamRuntimeVo(eventParamsByConditions,targetBean);

        Execution execution = null;
        //说明是从任务那儿发起的操作
        if(ObjectUtil.isNotEmpty(wfTodoTask)){
            String executionId = taskService.createTaskQuery().taskId(wfTodoTask.getCamundaTaskId()).singleResult().getExecutionId();
            execution = runtimeService.createExecutionQuery().executionId(executionId).singleResult();
        }else {
            //找到当前实例中的活动实例列表
            List<Execution> list = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
            if(ObjectUtil.isEmpty(list) || list.isEmpty()){
                throw new IncloudException("找不到流程实例信息");
            }
            //在目前不考虑并行网关的情况下，当前活动节点只有一个；
            execution = list.stream().findFirst().get();
        }

        Boolean error = false;

        Class<?> clazz = DelegateExecution.class;
        try{
            ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(targetBean.getClass(), methodName,clazz), targetBean, execution);
        }catch (Exception e){
            log.error("自定义事件执行失败");
            error = true;
        }
        if(rollBackSign && error){
            throw new IncloudException("事件异常，并且需要回滚！！！");
        }
    }
}