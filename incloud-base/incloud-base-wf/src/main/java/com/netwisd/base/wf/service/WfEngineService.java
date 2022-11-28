package com.netwisd.base.wf.service;

import com.netwisd.base.common.vo.wf.WfTaskVo;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.base.wf.vo.WfNextUserVo;
import com.netwisd.base.wf.vo.WfProcDefVo;
import com.netwisd.base.wf.vo.WfProcessLogVo;
import com.netwisd.base.wf.vo.WfVarDefVo;
import com.netwisd.common.core.util.Result;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/21 10:37 上午
 */
public interface WfEngineService {
    WfVo save(WfEngineDto.StartDto startDto);
    Map<String,List<WfNextUserVo>> handle(WfEngineDto.StartDto startDto);
    Boolean suspendProcess(WfEngineDto.StateDto stateDto);
    Boolean endProcess(WfEngineDto.StateDto stateDto);
    Boolean activateProcess(WfEngineDto.StateDto stateDto);
    Boolean deleteProcess(WfEngineDto.StateDto stateDto);
    Boolean deleteOnlyProcess(WfEngineDto.StateDto stateDto);
    List<WfNextUserVo> getNextUser(WfEngineDto wfEngineDto);
    Boolean submit(WfEngineDto.HandleDto handleDto);
    Boolean claim(String taskId);
    Boolean claimDuplicate(Long duplicateTaskId);
    Boolean insertNode(WfEngineDto.HandleDto handleDto);
    Boolean reject(WfEngineDto.HandleDto handleDto);
    Boolean revoke(String camundaTaskId);
    List<WfProcessLogVo> getList(String camundaProcinsId); //流程驳回时过滤的日志
    Result getTaskByProcInsId(String processInstanceId);
    List<WfProcessLogVo> getAllList(String camundaProcinsId, String camundaNodeDefId);//获取所有的流程日志
    List<WfProcessLogVo> getchildProcessLogActInsId(String camundaCurrentActInsId);
    Boolean replyDescription(Long id, String description);
    Result toSb(List<WfEngineDto.ToSbDto> toSbDtos);
    WfProcDefVo getFormInfo(String taskType,String id);//查询表单数据以及流程定义信息
    Boolean claimReceiveNotify(String notifyId); //收到的知会签收
    void handleNotifyOp(String id, String op); //处理收到的知会意见以及提交
    boolean handleUrge(String camundaProcInsId); //根据流程实例id催办
}
