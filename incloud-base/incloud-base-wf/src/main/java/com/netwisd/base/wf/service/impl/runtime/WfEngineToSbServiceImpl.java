package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.constants.ClaimStatusEnum;
import com.netwisd.base.wf.constants.WfProcessLogEnum;
import com.netwisd.base.wf.entity.WfProcessLog;
import com.netwisd.base.wf.entity.WfTodoTask;
import com.netwisd.base.wf.service.runtime.WfEngineClaimService;
import com.netwisd.base.wf.service.runtime.WfEngineToSbService;
import com.netwisd.base.wf.service.runtime.WfProcessLogService;
import com.netwisd.base.wf.service.runtime.WfTodoTaskService;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.common.core.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/13 10:09
 */
@Service
@Slf4j
public class WfEngineToSbServiceImpl implements WfEngineToSbService {
    @Autowired
    TaskService taskService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    Mapper dozerMapper;

    @Autowired
    WfEngineClaimService wfEngineClaimService;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Override
    public Result toSb(List<WfEngineDto.ToSbDto> toSbDtos) {
        List<WfTodoTask> wfTodoTasks = new ArrayList<>();
        //List<Task> tasks = new ArrayList<>();
        List<WfProcessLog> wfProcessLogs = new ArrayList<>();
        for (WfEngineDto.ToSbDto toSbDto : toSbDtos){
            String taskId = toSbDto.getTaskId();
            String targetUserId = toSbDto.getTargetUserId();
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            WfTodoTask wfTodoTask = wfEngineClaimService.claimProcess(taskId);
            if(ObjectUtil.isNotEmpty(task)){
                task.setAssignee(null);
                task.setOwner(null);
                //todo ???????????????????????????camunda???taskService????????????????????????????????????????????????
                taskService.saveTask(task);
                if(ObjectUtil.isNotEmpty(wfTodoTask)){
                    wfTodoTask.setAssignee(null);
                    wfTodoTask.setOwnner(null);
                    wfTodoTask.setAssigneeName(null);
                    wfTodoTask.setCandidates(targetUserId);
                    LocalDateTime now = LocalDateTime.now();
                    WfProcessLog wfProcessLog = wfProcessLogService.get(taskId, WfProcessLogEnum.NONE.getType());
                    //copy????????????????????????
                    WfProcessLog newWfProcessLog = new WfProcessLog();
                    dozerMapper.map(wfProcessLog,newWfProcessLog);

                    //?????????????????????????????????????????????
                    wfProcessLog.setType(WfProcessLogEnum.TOSB.getType());
                    wfProcessLog.setDecision(WfProcessLogEnum.TOSB.getType());
                    wfProcessLog.setUpdateTime(now);
                    wfProcessLog.setEndTime(now);

                    //todo ???????????????????????????????????????
                    newWfProcessLog.setId(null);
                    newWfProcessLog.setClaimStatus(ClaimStatusEnum.UNCLAIMED.getType());
                    newWfProcessLog.setClaimTime(null);
                    newWfProcessLog.setStartTime(now);
                    newWfProcessLog.setCreateTime(now);
                    wfProcessLogs.add(newWfProcessLog);
                    wfTodoTasks.add(wfTodoTask);
                }
            }
        }
        wfTodoTaskService.saveOrUpdateBatch(wfTodoTasks);
        //todo ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????ID???????????????????????????
        wfProcessLogService.saveBatch(wfProcessLogs);
        return Result.success();
    }
}
