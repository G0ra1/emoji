package com.netwisd.base.wf.event.delegate;

import cn.hutool.core.util.ObjectUtil;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.wf.constants.*;
import com.netwisd.base.wf.dto.WfProcessLogDto;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.service.procdef.WfNodeDefService;
import com.netwisd.base.wf.service.runtime.WfProcessLogService;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: 外链式子流程的结束事件——用户处理子流程或多实例子流程结束后的下一步人员获取和assignee赋值；
 * @Author: zouliming@netwisd.com
 * @Date: 2020/10/27 1:13 下午
 */
@Slf4j
@Component
public class CallActivityStartDelegate implements ExecutionListener {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    WfProcessService wfProcessService;

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> incloudCache;

    @Autowired
    WfNodeDefService wfNodeDefService;

    @Override
    public void notify(DelegateExecution execution) throws Exception {
        String processInstanceId = execution.getProcessInstanceId();
        String exectionId = execution.getId();
        Object variable = runtimeService.getVariable(processInstanceId, InnerVariableEnum.ASSIGNEE.getName());
        String userId = null;
        if(ObjectUtil.isNotEmpty(variable)){
            if(variable instanceof String){
                userId =  variable.toString();
            }else {
                List<String> wfAssigneeList = (List)variable;
                StringBuffer sb = new StringBuffer();
                wfAssigneeList.forEach(wfAssignee -> sb.append(wfAssignee).append(","));
                userId = sb.substring(0,sb.length()-1);
            }
        }
        WfProcess wfProcess = wfProcessService.get(processInstanceId);
        String currentActivityId = execution.getCurrentActivityId();
        /**
         * 这里设置一下是为了覆盖当前流程实例变量当中CALLACTIVITY_DEF_ID的值，这时的值实际是当前流程实例processInstanceId的父流程实例当中传过来的值
         * 或者说是同级上一个子流程实例的值（一个流程定义中有前后两个子流程定义）；当然如果当前流程实例processInstanceId是最顶级父，这个值还不存在；
         * 目的是为了让后面具体的某一个实例去取到这个值，为什么不去设置exectionId呢，解释一下。因为设置了变量传递；
         * 因为这个值是节点定义ID，不管多少实例，节点定义ID是相同的，通过设置变量传递，给每个实例都传这个节点定义ID；
         */
        runtimeService.setVariable(processInstanceId,InnerVariableEnum.CALLACTIVITY_DEF_ID.getName(),currentActivityId);
        //这个是设置当前exection中（也就是当前如果是多实例子流程的话，具体的某一个流程实例的exection中设置变量），记得要通过变量映射进行传递
        runtimeService.setVariable(exectionId,InnerVariableEnum.ACT_INS_ID.getName(),execution.getActivityInstanceId());
        log.info("当前子流程节点为：{}",currentActivityId);
        WfNodeDefVo wfNodeDefVo = wfNodeDefService.getEntity(wfProcess.getCamundaProcdefId(), currentActivityId);
        initProcessLog(userId,wfProcess,wfNodeDefVo,execution);
        log.info("子流程流程日志创建成功！");
    }

    /**
     * 这里的日志，指的不是子流程里面的日志，而是主流程中显示的这条子流程的总日志引用相当于是，它展开后会是具体子流程日志
     * @param userName
     * @param wfProcess
     * @param wfNodeDefVo
     * @param execution
     */
    private void initProcessLog(String userName, WfProcess wfProcess,WfNodeDefVo wfNodeDefVo,DelegateExecution execution){
        WfProcessLogDto wfProcessLog = new WfProcessLogDto();
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

        wfProcessLog.setNodeId(wfNodeDefVo.getCamundaNodeDefId());
        wfProcessLog.setNodeName(wfNodeDefVo.getNodeName());
        wfProcessLog.setNodeType(wfNodeDefVo.getNodeType());
        wfProcessLog.setStartTime(LocalDateTime.now());
        wfProcessLog.setCreateTime(LocalDateTime.now());
        wfProcessLog.setCamundaProcinsId(wfProcess.getCamundaProcinsId());
        wfProcessLog.setProcinsId(wfProcess.getId());
        wfProcessLog.setParentProcinsId(wfProcess.getParentProcinsId());
        wfProcessLog.setCamundaParentProcinsId(wfProcess.getCamundaParentProcinsId());
        wfProcessLog.setCamundaProcdefId(wfProcess.getCamundaProcdefId());
        wfProcessLog.setCamundaProcdefKey(wfProcess.getCamundaProcdefKey());
        wfProcessLog.setCamundaProcdefVersion(wfProcess.getCamundaProcdefVersion());
        wfProcessLog.setProcdefId(wfProcess.getProcdefId());
        wfProcessLog.setProcdefName(wfProcess.getProcdefName());
        wfProcessLog.setProcinsName(wfProcess.getProcinsName());
        wfProcessLog.setProcdefTypeId(wfProcess.getProcdefTypeId());
        wfProcessLog.setProcdefTypeName(wfProcess.getProcdefTypeName());
        //这里特别标注一下：存的活动实例ID，用于区分一个节点的不同多实例
        wfProcessLog.setCamundaCurrentActInsId(execution.getActivityInstanceId());
        wfProcessLog.setCamundaParentActInsId(wfProcess.getCamundaParentActInsId());

        wfProcessLog.setType(WfProcessLogEnum.NONE.getType());
        wfProcessLog.setDecision(WfProcessLogEnum.NONE.getType());
        wfProcessLog.setDescription(WfProcessLogEnum.NONE.getName());
        /*wfProcessLog.setFormKey(wfProcess.getFormKey());*/
        wfProcessLog.setCamundaCallActivityDefId(wfProcess.getCamundaCallActivityDefId());
        wfProcessLogService.save(wfProcessLog);
        log.info("wfProcessLog保存成功！");
    }
}
