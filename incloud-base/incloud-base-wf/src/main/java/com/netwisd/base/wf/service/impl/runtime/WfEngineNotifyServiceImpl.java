package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.constants.WfProcessLogEnum;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.service.WfProcdefService;
import com.netwisd.base.wf.service.WfSendNotifyTaskService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.common.core.exception.CheckedException;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author XHL
 * @description
 * @date 2022/03/08 16:41
 */
@Service
@Slf4j
public class WfEngineNotifyServiceImpl implements WfEngineNotifyService {

    @Autowired
    @Qualifier("incloudUserCache")
    private IncloudCache<MdmUserVo> incloudCache;

    @Autowired
    Mapper dozerMapper;

    @Autowired
    WfTodoTaskService wfTodoTaskService;

    @Autowired
    WfMyOutDuplicateTaskService wfMyOutDuplicateTaskService;

    @Autowired
    WfReceiveNotifyTaskService wfReceiveNotifyTaskService;

    @Autowired
    WfProcdefService wfProcdefService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Autowired
    WfSendNotifyTaskService wfSendNotifyTaskService;

    @Autowired
    WfEngineSubmitService wfEngineSubmitService;

    @Override
    public void createNotify(WfEngineDto.HandleDto handleDto, Task task) {
        List<String> wfNotifyList = handleDto.getWfNotifyList();
        if(CollectionUtil.isNotEmpty(wfNotifyList)) {
            LoginAppUser loginAppUserInfo = AppUserUtil.getLoginAppUserAndCheck();
            List<Object> objList = new ArrayList<>();
            wfNotifyList.forEach(d->objList.add(d));
            List<MdmUserVo> mdmUserVos = incloudCache.gets(objList);
            if(CollectionUtil.isEmpty(mdmUserVos)) {
                throw new IncloudException("???????????????????????????");
            }
            Map<String, MdmUserVo> userMap = mdmUserVos.stream().collect(Collectors.toMap(MdmUserVo::getUserName, d -> d));
            String camundaTaskId = task.getId();
            // ?????????????????????????????????????????? ???????????????????????????
            WfTodoTask wfTodoTask = wfTodoTaskService.get(camundaTaskId);
            wfTodoTask.setState(WfProcessEnum.NOTIFY.getType());
            wfTodoTaskService.update(wfTodoTask);
            List<WfReceiveNotifyTask> list = new ArrayList<>();
            List<WfSendNotifyTask> _list = new ArrayList<>();
            if(ObjectUtil.isNotEmpty(wfTodoTask) && ObjectUtil.isNotEmpty(wfNotifyList)){
                //?????????????????? ???????????????
                StringBuilder userNames = new StringBuilder();
                StringBuilder AssigneeName = new StringBuilder();
                //???????????? ?????????????????????
                for(String userName : wfNotifyList){
                    userNames.append(userName);
                    userNames.append(",");
                    AssigneeName.append(userMap.get(userName).getUserNameCh());
                    AssigneeName.append(",");
                    WfReceiveNotifyTask wfReceiveNotifyTask = dozerMapper.map(wfTodoTask, WfReceiveNotifyTask.class);
                    wfReceiveNotifyTask.setId(IdGenerator.getIdGenerator());
                    wfReceiveNotifyTask.setCliamTime(null);
                    wfReceiveNotifyTask.setAssignee(userName); //???????????????
                    wfReceiveNotifyTask.setAssigneeName(userMap.get(userName).getUserNameCh());
                    wfReceiveNotifyTask.setOwnner(loginAppUserInfo.getUserName()); //??????????????????
                    wfReceiveNotifyTask.setOwnnerName(loginAppUserInfo.getUserNameCh());
                    wfReceiveNotifyTask.setCreateTime(LocalDateTime.now());
                    wfReceiveNotifyTask.setUpdateTime(LocalDateTime.now());
                    wfReceiveNotifyTask.setCamundaTaskId(task.getId());
                    wfReceiveNotifyTask.setCamundaNodeKey(task.getTaskDefinitionKey());
                    wfReceiveNotifyTask.setCamundaNodeName(task.getName());
                    wfReceiveNotifyTask.setCamundaNodeType(wfTodoTask.getNodeType());
                    wfReceiveNotifyTask.setNotifyOpinion(null);
                    wfReceiveNotifyTask.setIsCallActivity(wfTodoTask.getIsCallActivity());
                    wfReceiveNotifyTask.setIsClone(wfTodoTask.getIsClone());
                    wfReceiveNotifyTask.setCamundaParentProcinsId(wfTodoTask.getCamundaParentProcinsId());
                    wfReceiveNotifyTask.setParentProcinsId(wfTodoTask.getParentProcinsId());
                    wfReceiveNotifyTask.setCurrentActivityId(wfTodoTask.getNodeKey());
                    wfReceiveNotifyTask.setCurrentActivityName(wfTodoTask.getNodeName());
                    wfReceiveNotifyTask.setCurrentActivityAssignee(wfTodoTask.getAssignee());
                    wfReceiveNotifyTask.setCurrentActivityAssigneeName(wfTodoTask.getAssigneeName());
                    //?????????????????? ??????????????????
                    WfEngineDto.HandleDto _handleDto = dozerMapper.map(handleDto, WfEngineDto.HandleDto.class);
                    _handleDto.setWfNotifyList(null);
                    wfReceiveNotifyTask.setSubmitParam(JSONObject.toJSON(_handleDto).toString()); //??????????????????
                    list.add(wfReceiveNotifyTask);
                }
                //???????????? ?????????????????????
                WfSendNotifyTask wfSendNotifyTask = dozerMapper.map(wfTodoTask, WfSendNotifyTask.class);
                wfSendNotifyTask.setCliamTime(null);
                wfSendNotifyTask.setAssignee(userNames.deleteCharAt(userNames.length()-1).toString()); //???????????????
                wfSendNotifyTask.setAssigneeName(AssigneeName.deleteCharAt(AssigneeName.length()-1).toString());
                wfSendNotifyTask.setOwnner(loginAppUserInfo.getUserName()); //??????????????????
                wfSendNotifyTask.setOwnnerName(loginAppUserInfo.getUserNameCh());
                wfSendNotifyTask.setCreateTime(LocalDateTime.now());
                wfSendNotifyTask.setUpdateTime(LocalDateTime.now());
                wfSendNotifyTask.setCamundaTaskId(task.getId());
                wfSendNotifyTask.setCamundaNodeKey(task.getTaskDefinitionKey());
                wfSendNotifyTask.setCamundaNodeName(task.getName());
                wfSendNotifyTask.setCamundaNodeType(wfTodoTask.getNodeType());
                wfSendNotifyTask.setNotifyOpinion(null);
                wfSendNotifyTask.setIsCallActivity(wfTodoTask.getIsCallActivity());
                wfSendNotifyTask.setIsClone(wfTodoTask.getIsClone());
                wfSendNotifyTask.setCamundaParentProcinsId(wfTodoTask.getCamundaParentProcinsId());
                wfSendNotifyTask.setParentProcinsId(wfTodoTask.getParentProcinsId());
                wfSendNotifyTask.setCurrentActivityId(wfTodoTask.getNodeKey());
                wfSendNotifyTask.setCurrentActivityName(wfTodoTask.getNodeName());
                wfSendNotifyTask.setCurrentActivityAssignee(wfTodoTask.getAssignee());
                wfSendNotifyTask.setCurrentActivityAssigneeName(wfTodoTask.getAssigneeName());
                wfSendNotifyTask.setId(IdGenerator.getIdGenerator());
                _list.add(wfSendNotifyTask);
                if(CollectionUtil.isNotEmpty(list)) {
                    wfReceiveNotifyTaskService.saveBatch(list);
                }
                if(CollectionUtil.isNotEmpty(_list)) {
                    wfSendNotifyTaskService.saveBatch(_list);
                }
                log.info("???????????????????????????");
                duplicateProcessLog(list);
            }
        } else {
            throw new IncloudException("?????????????????????????????????");
        }
    }

    @Override
    @Transactional
    public void handleNotifyOp(String id, String op) {
        if(StringUtils.isBlank(id)) {
            throw new IncloudException("????????????id???????????????");
        }
        if(StringUtils.isBlank(op)) {
            throw new IncloudException("?????????????????????????????????");
        }
        //???????????????????????????
        WfReceiveNotifyTask wfReceiveNotifyTask = wfReceiveNotifyTaskService.getById(id);
        if(null == wfReceiveNotifyTask) {
            throw new IncloudException("???????????????????????????");
        }
        //????????????
        wfReceiveNotifyTask.setNotifyOpinion(op);
        wfReceiveNotifyTask.setState(WfProcessEnum.DONE.getType());
        wfReceiveNotifyTaskService.updateById(wfReceiveNotifyTask);
        //????????????????????????????????????????????? ????????????????????????????????????
        List<WfReceiveNotifyTask> wfReceiveNotifyTaskList = wfReceiveNotifyTaskService.getNotifyTaskByProcinsIdAndNodeKey(wfReceiveNotifyTask.getCamundaProcinsId(),wfReceiveNotifyTask.getCamundaNodeKey());
        //????????????????????? ?????????????????????
        if(CollectionUtil.isEmpty(wfReceiveNotifyTaskList)) {
           String submitParam = wfReceiveNotifyTask.getSubmitParam();
           if(StringUtils.isBlank(submitParam)) {
               throw new IncloudException("???????????????????????????");
           }
           //???????????????
           WfEngineDto.HandleDto handleDto = JSON.parseObject(submitParam, WfEngineDto.HandleDto.class);
           wfEngineSubmitService.submit(handleDto);
        }
    }

    /**
     * ????????????
     * @param list
     */
    private void duplicateProcessLog(List<WfReceiveNotifyTask> list){
        List<WfProcessLog> wfProcessLogList = new ArrayList<>();
        list.forEach(wfDuplicateTask -> {
            WfProcessLog wfProcessLog = new WfProcessLog();
            dozerMapper.map(wfDuplicateTask,wfProcessLog);
            MdmUserVo currentUser = getMdmUserVo(wfDuplicateTask.getAssignee());
            wfProcessLog.setUserName(currentUser.getUserName());
            wfProcessLog.setUserNameCh(currentUser.getUserNameCh());
            wfProcessLog.setDeptId(String.valueOf(currentUser.getParentDeptId()));
            wfProcessLog.setDeptName(currentUser.getParentDeptName());
            wfProcessLog.setOrgId(String.valueOf(currentUser.getParentOrgId()));
            wfProcessLog.setOrgName(currentUser.getParentOrgName());
            wfProcessLog.setId(null);
            wfProcessLog.setNodeId(wfDuplicateTask.getCamundaNodeKey());
            wfProcessLog.setNodeName(wfDuplicateTask.getCamundaNodeName());
            wfProcessLog.setNodeType(wfDuplicateTask.getCamundaNodeType());
            wfProcessLog.setStartTime(LocalDateTime.now());
            wfProcessLog.setEndTime(LocalDateTime.now());
            wfProcessLog.setCreateTime(LocalDateTime.now());
            wfProcessLog.setType(WfProcessLogEnum.NOTIFY.getType());
            wfProcessLog.setDecision(WfProcessLogEnum.NOTIFY.getType());
            wfProcessLogList.add(wfProcessLog);
        });
        wfProcessLogService.saveBatch(wfProcessLogList);
        log.info("??????wfProcessLog???????????????");
    }

    /**
     * ??????????????????????????????
     * @param userName
     * @return
     */
    @SneakyThrows
    private MdmUserVo getMdmUserVo(String userName){
        MdmUserVo mdmUserVo = incloudCache.get(userName);
        if(mdmUserVo == null){
            throw new CheckedException("????????????ID????????????????????????");
        }
        return mdmUserVo;
    }
}
