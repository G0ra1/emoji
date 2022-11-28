package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.StrUtil;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.service.runtime.WfGetDtoService;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.base.wf.service.runtime.WfTaskService;
import com.netwisd.base.wf.starter.dto.WfDto;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2022/2/24 11:11
 */
@Service
@Slf4j
public class WfGetDtoServiceImpl implements WfGetDtoService {

    @Autowired
    WfTaskService wfTaskService;

    @Autowired
    WfProcessService wfProcessService;

    @Override
    public WfDto returnData(String camundaTaskId,String camundaProcinsId){
        if(StrUtil.isEmpty(camundaProcinsId)){
            Task task = wfTaskService.getAndCheck(camundaTaskId);
            camundaProcinsId = task.getProcessInstanceId();
        }
        WfProcess wfProcess = wfProcessService.getAndCheck(camundaProcinsId);
        WfDto wfDto = new WfDto();
        wfDto.setCamundaProcdefId(wfProcess.getCamundaProcdefId());
        wfDto.setCamundaProcdefKey(wfProcess.getCamundaProcdefKey());
        wfDto.setCamundaProcinsId(camundaProcinsId);
        wfDto.setCamundaTaskId(camundaTaskId);

        wfDto.setProcdefTypeId(wfProcess.getProcdefTypeId());
        wfDto.setProcdefTypeName(wfProcess.getProcdefTypeName());
        wfDto.setProcdefName(wfProcess.getProcdefName());
        wfDto.setProcdefVersion(wfProcess.getCamundaProcdefVersion());
        wfDto.setProcessName(wfProcess.getProcinsName());
        wfDto.setAuditStatus(wfProcess.getState());

        wfDto.setBizKey(wfProcess.getBizKey());
        /*wfDto.setApplyTime(wfProcess.getApplyTime());*/
        wfDto.setReason(wfProcess.getReason());
        //wfDto.setIsChange(isChange);
        return wfDto;
    }
}
