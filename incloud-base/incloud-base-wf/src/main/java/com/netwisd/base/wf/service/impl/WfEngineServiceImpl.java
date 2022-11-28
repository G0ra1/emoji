package com.netwisd.base.wf.service.impl;

import cn.hutool.core.util.*;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.vo.wf.WfDoneTaskVo;
import com.netwisd.base.common.vo.wf.WfTaskVo;
import com.netwisd.base.common.vo.wf.WfTodoTaskVo;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.config.License;
import com.netwisd.base.wf.constants.*;
import com.netwisd.base.wf.service.*;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.starter.dto.WfDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.base.wf.starter.vo.WfVo;
import com.netwisd.base.wf.vo.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * @Description: 流程运行实现
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/21 10:38 上午
 */
@Service
@Slf4j
public class WfEngineServiceImpl implements WfEngineService {
    @Autowired
    WfProcdefService wfProcdefService;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    WfMyInDuplicateTaskService wfMyInDuplicateTaskService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    WfDoneTaskService wfDoneTaskService;

    @Autowired
    WfEngineClaimService wfEngineClaimService;

    @Autowired
    WfEngineStateService wfEngineStateService;

    @Autowired
    WfEngineInsertNodeService wfEngineInsertNodeService;

    @Autowired
    WfEngineCommonService wfEngineCommonService;

    @Autowired
    WfEngineSubmitService wfEngineSubmitService;

    @Autowired
    WfEngineGetNextService wfEngineGetNextService;

    @Autowired
    WfEngineToSbService wfEngineToSbService;

    @Autowired
    WfEngineRejectService wfEngineRejectService;

    @Autowired
    WfEngineRevokeService wfEngineRevokeService;

    @Autowired
    WfEngineStartService wfEngineStartService;

    @Autowired
    WfEngineBizDataService wfEngineBizDataService;

    @Autowired
    WfGetDtoService wfGetDtoService;

    @Autowired
    WfBizFormHandleService wfBizFormHandleService;

    @Autowired
    WfReceiveNotifyTaskService wfReceiveNotifyTaskService;

    @Autowired
    WfEngineNotifyService wfEngineNotifyService;

    @Autowired
    WfEngineUrgeService wfEngineUrgeService;

    @Autowired
    Mapper dozerMapper;

    @EventListener({License.class})
    public void processLicenseEvent(License license){
    }

    @GlobalTransactional
    @Transactional
    public WfVo save(WfEngineDto.StartDto startDto){
        String camundaTaskId = startDto.getCamundaTaskId();
        List<WfEngineDto.BizData> bizDataList = startDto.getBizDataList();
        WfDto wfDto = null;
        if(StrUtil.isEmpty(camundaTaskId)){
            wfDto = wfEngineStartService.startProcess(startDto);
            wfDto.setAuditStatus(WfProcessEnum.DRAFT.getType());
        }else {
            wfDto = wfGetDtoService.returnData(camundaTaskId,null);
            WfTodoTaskVo wfTodoTaskVo = wfTodoTaskService.getFirstOne(wfDto.getCamundaProcinsId());
            if(YesNo.YES.code ==  wfTodoTaskVo.getIsDraft()) {
                wfDto.setAuditStatus(WfProcessEnum.DRAFT.getType());
            }
        }
        for (WfEngineDto.BizData bizData : bizDataList){
            if(StrUtil.isEmpty(camundaTaskId)){
                bizData.setParams(wfEngineBizDataService.invoke(bizData, wfDto, BizMethodTypeEnum.SAVE));
            }else {
                bizData.setParams(wfEngineBizDataService.invoke(bizData,wfDto,BizMethodTypeEnum.UPDATE));
            }
        }
        WfVo wfVo = dozerMapper.map(wfDto, WfVo.class);
        return wfVo;
    }

    public final static String forms = "forms";
    public final static String users = "users";
    public final static String camundaTaskIdFinal = "camundaTaskId";

    @GlobalTransactional
    @Transactional
    @Override
    public Map<String,List<WfNextUserVo>> handle(WfEngineDto.StartDto startDto) {
        Map result = new HashMap();
        String camundaTaskId = startDto.getCamundaTaskId();
        if(StrUtil.isEmpty(camundaTaskId)){
            WfVo wfVo = save(startDto);
            camundaTaskId = wfVo.getCamundaTaskId();
            result.put(forms,startDto.getBizDataList());//save 方法表单id已赋值
            result.put(camundaTaskIdFinal,camundaTaskId);
        }else {
            WfDto wfDto = wfGetDtoService.returnData(camundaTaskId,null);
            camundaTaskId = wfDto.getCamundaTaskId();
            result.put(camundaTaskIdFinal,camundaTaskId);
        }
        result.put(users,getNextUser(new WfEngineDto(camundaTaskId, startDto.getBizDataList())));
        return result;
    }

    /**
     * 流程挂起
     * @param stateDto
     * @return
     */
    @Override
    @Transactional
    public Boolean suspendProcess(WfEngineDto.StateDto stateDto) {
        return wfEngineStateService.suspendProcess(stateDto);
    }

    /**
     * 流程终止
     * @param stateDto
     * @return
     */
    @Override
    @Transactional
    public Boolean endProcess(WfEngineDto.StateDto stateDto) {
        return wfEngineStateService.endProcess(stateDto);
    }

    /**
     * 激活流程
     * @param stateDto
     * @return
     */
    @Override
    @Transactional
    public Boolean activateProcess(WfEngineDto.StateDto stateDto) {
        return wfEngineStateService.activateProcess(stateDto);
    }

    /**
     * 删除流程
     * @param stateDto
     * @return
     */
    @Override
    @Transactional
    public Boolean deleteProcess(WfEngineDto.StateDto stateDto) {
        return wfEngineStateService.deleteProcess(stateDto);
    }

    @Override
    public Boolean deleteOnlyProcess(WfEngineDto.StateDto stateDto) {
        return wfEngineStateService.deleteOnlyProcess(stateDto);
    }

    /**
     * 提交获取下一步选人
     * @param wfEngineDto
     * @return
     */
    @Override
    public List<WfNextUserVo> getNextUser(WfEngineDto wfEngineDto) {
        return wfEngineGetNextService.getNextUser(wfEngineDto);
    }


    /**
     * 流程提交
     * @param handleDto
     * @return
     */
    @GlobalTransactional
    @Transactional
    @Override
    public Boolean submit(WfEngineDto.HandleDto handleDto) {
        return wfEngineSubmitService.submit(handleDto);
    }

    /**
     * 流程签收
     * @param taskId
     * @return
     */
    @Transactional
    @Override
    public Boolean claim(String taskId) {
        return wfEngineClaimService.claimProcess(taskId) != null ? true : false;
    }

    /**
     * 传阅签收
     * @param duplicateTaskID
     * @return
     */
    @Override
    public Boolean claimDuplicate(Long duplicateTaskID) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Boolean aBoolean = wfMyInDuplicateTaskService.claimDuplicate(duplicateTaskID,loginAppUser.getUserName());
        log.info("传阅签收成功！");
        return aBoolean;
    }

    /**
     * 加签 目前支持当前人只能加一个签，被加签的人可以继续无限加签...
     * @param handleDto
     * @return
     */
    @SneakyThrows
    @Override
    @Transactional
    public Boolean insertNode(WfEngineDto.HandleDto handleDto){
        return wfEngineInsertNodeService.insertNode(handleDto) ? submit(handleDto) : false;
    }

    /**
     * 流程驳回
     * @param handleDto
     * @return
     */
    @Override
    @Transactional
    public Boolean reject(WfEngineDto.HandleDto handleDto) {
        return wfEngineRejectService.reject(handleDto);
    }

    /**
     * 流程撤回
     * @param camundaTaskId
     * @return
     */
    @Override
    @Transactional
    public Boolean revoke(String camundaTaskId) {
        return wfEngineRevokeService.revoke(camundaTaskId);
    }

    @Override
    public List<WfProcessLogVo> getList(String camundaProcinsId) {
        List<WfProcessLogVo> list = wfProcessLogService.getRejectAllList(camundaProcinsId);
        return list;
    }

    @Override
    public List<WfProcessLogVo> getAllList(String camundaProcinsId, String camundaNodeDefId) {
        List<WfProcessLogVo> list = wfProcessLogService.getLogList(camundaProcinsId,camundaNodeDefId);
        return list;
    }

    @Override
    public List<WfProcessLogVo> getchildProcessLogActInsId(String camundaCurrentActInsId) {
        return wfProcessLogService.getchildProcessLogActInsId(camundaCurrentActInsId);
    }

    @Override
    public Boolean replyDescription(Long duplicateTaskID, String description) {
        return wfMyInDuplicateTaskService.replyDescription(duplicateTaskID,description);
    }

    /**
     * 转办
     * @param toSbDtos
     * @return
     */
    @Override
    @Transactional
    public Result toSb(List<WfEngineDto.ToSbDto> toSbDtos) {
        return wfEngineToSbService.toSb(toSbDtos);
    }

    @Override
    public Result getTaskByProcInsId(String camundaProcInsId) {
        WfTodoTaskVo firstOne = wfTodoTaskService.getFirstOne(camundaProcInsId);
        Map map = new HashMap();
        if(ObjectUtil.isNotEmpty(firstOne)){
            Integer isDraft = firstOne.getIsDraft();
            if(isDraft ==1){
                map.put("action",MyTaskTypeEnum.DRAFT.code);
                map.put("id",String.valueOf(firstOne.getId()));
                return Result.success(map);
            }else {
                map.put("action",MyTaskTypeEnum.TODO.code);
                map.put("id",String.valueOf(firstOne.getId()));
                return Result.success(map);
            }
        }
        WfDoneTaskVo firstOneDone = wfDoneTaskService.getFirstOne(camundaProcInsId);
        if(ObjectUtil.isNotEmpty(firstOneDone)){
            map.put("action",MyTaskTypeEnum.DONE.code);
            map.put("id",String.valueOf(firstOneDone.getId()));
            return Result.success(map);
        } else {
            return Result.failed("获取流程信息异常！");
        }
    }

    @Override
    public WfProcDefVo getFormInfo(String taskType,String id) {
        return wfBizFormHandleService.getFormInfo(taskType,id);
    }

    @Override
    public Boolean claimReceiveNotify(String notifyId) {
        if(StringUtils.isBlank(notifyId)) {
            throw new IncloudException("收到的传阅id不能为空！");
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        Boolean aBoolean = wfReceiveNotifyTaskService.claimNotifyId(Long.valueOf(notifyId),loginAppUser.getUserName());
        log.info("收到的知会签收成功！");
        return aBoolean;
    }

    @Override
    public void handleNotifyOp(String id, String op) {
        wfEngineNotifyService.handleNotifyOp(id,op);
    }

    @Override
    public boolean handleUrge(String camundaProcInsId) {
        return wfEngineUrgeService.handleUrge(camundaProcInsId);
    }
}