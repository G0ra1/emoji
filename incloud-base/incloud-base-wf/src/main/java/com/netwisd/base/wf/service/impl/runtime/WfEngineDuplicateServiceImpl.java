package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.user.vo.EmplVO;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.IdGenerator;
import com.netwisd.base.wf.constants.WfProcessLogEnum;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.service.WfProcdefService;
import com.netwisd.base.wf.service.runtime.*;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.common.core.exception.CheckedException;
import com.netwisd.common.db.cache.IncloudCache;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zouliming@netwisd.com
 * @description
 * @date 2021/12/9 16:41
 */
@Service
@Slf4j
public class WfEngineDuplicateServiceImpl implements WfEngineDuplicateService {

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
    WfMyInDuplicateTaskService wfMyInDuplicateTaskService;

    @Autowired
    WfProcdefService wfProcdefService;

    @Autowired
    WfProcessLogService wfProcessLogService;

    @Override
    public void createDuplicate(WfEngineDto.HandleDto handleDto, Task task) {
        List<String> wfDuplicateList = handleDto.getWfDuplicateList();
        List<Object> objList = new ArrayList<>();
        LoginAppUser loginAppUserInfo = AppUserUtil.getLoginAppUserAndCheck();
        if(CollectionUtil.isNotEmpty(wfDuplicateList)) {
            wfDuplicateList.forEach(d->objList.add(d));
            List<MdmUserVo> mdmUserVos = getMdmUserVos(objList);
            Map<String, MdmUserVo> userMap = mdmUserVos.stream().collect(Collectors.toMap(MdmUserVo::getUserName, d -> d));
            String camundaTaskId = task.getId();
            WfTodoTask wfTodoTask = wfTodoTaskService.get(camundaTaskId);
            List<WfMyInDuplicateTask> list = new ArrayList<>();
            if(ObjectUtil.isNotEmpty(wfTodoTask) && ObjectUtil.isNotEmpty(wfDuplicateList)){
                //发起的传阅
                WfMyOutDuplicateTask wfMyOutDuplicateTask = dozerMapper.map(wfTodoTask, WfMyOutDuplicateTask.class);
                wfMyOutDuplicateTask.setAssignee(loginAppUserInfo.getUserName());
                wfMyOutDuplicateTask.setAssigneeName(loginAppUserInfo.getUserNameCh());
                wfMyOutDuplicateTask.setId(IdGenerator.getIdGenerator());
                wfMyOutDuplicateTask.setCreateTime(LocalDateTime.now());
                wfMyOutDuplicateTask.setUpdateTime(LocalDateTime.now());
                wfMyOutDuplicateTask.setCamundaTaskId(task.getId());
                wfMyOutDuplicateTask.setCamundaNodeKey(task.getTaskDefinitionKey());
                wfMyOutDuplicateTask.setCamundaNodeName(task.getName());
                wfMyOutDuplicateTask.setCamundaNodeType(wfTodoTask.getNodeType());
                wfMyOutDuplicateTask.setIsCallActivity(wfTodoTask.getIsCallActivity());
                wfMyOutDuplicateTask.setIsClone(wfTodoTask.getIsClone());
                wfMyOutDuplicateTask.setCamundaParentProcinsId(wfTodoTask.getCamundaParentProcinsId());
                wfMyOutDuplicateTask.setParentProcinsId(wfTodoTask.getParentProcinsId());
                wfMyOutDuplicateTaskService.save(wfMyOutDuplicateTask);
                //被传阅人收到的传阅
                for(String userName : wfDuplicateList){
                    WfMyInDuplicateTask wfMyInDuplicateTask = dozerMapper.map(wfTodoTask, WfMyInDuplicateTask.class);
                    wfMyInDuplicateTask.setCliamTime(null);
                    wfMyInDuplicateTask.setAssignee(userName);
                    wfMyInDuplicateTask.setAssigneeName(userMap.get(userName).getUserNameCh());
                    wfMyInDuplicateTask.setOwnner(loginAppUserInfo.getUserName());
                    wfMyInDuplicateTask.setOwnnerName(loginAppUserInfo.getUserNameCh());
                    wfMyInDuplicateTask.setId(null);
                    wfMyInDuplicateTask.setCreateTime(LocalDateTime.now());
                    wfMyInDuplicateTask.setUpdateTime(LocalDateTime.now());
                    wfMyInDuplicateTask.setCamundaTaskId(task.getId());
                    wfMyInDuplicateTask.setCamundaNodeKey(task.getTaskDefinitionKey());
                    wfMyInDuplicateTask.setCamundaNodeName(task.getName());
                    wfMyInDuplicateTask.setCamundaNodeType(wfTodoTask.getNodeType());
                    wfMyInDuplicateTask.setOutDuplicateTaskId(wfMyOutDuplicateTask.getId());
                    wfMyInDuplicateTask.setDescription(null);//发起时 意见为null
                    wfMyInDuplicateTask.setIsCallActivity(wfTodoTask.getIsCallActivity());
                    wfMyInDuplicateTask.setIsClone(wfTodoTask.getIsClone());
                    wfMyInDuplicateTask.setCamundaParentProcinsId(wfTodoTask.getCamundaParentProcinsId());
                    wfMyInDuplicateTask.setParentProcinsId(wfTodoTask.getParentProcinsId());
                    list.add(wfMyInDuplicateTask);
                }
                wfMyInDuplicateTaskService.saveBatch(list);
                log.info("传阅任务保存成功！");
                duplicateProcessLog(list);
            }
        }
    }



    /**
     * 传阅日志
     * @param list
     */
    private void duplicateProcessLog(List<WfMyInDuplicateTask> list){
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
            /*wfProcessLog.setUpdateTime(LocalDateTime.now());*/
            wfProcessLog.setType(WfProcessLogEnum.DUPLICATE.getType());
            wfProcessLog.setDecision(WfProcessLogEnum.DUPLICATE.getType());
            //wfProcessLog.setFormKey(wfProcDef.getFormKey());
            /*wfProcessLog.setDescription(WfProcessLogEnum.DUPLICATE.getName());*/
            wfProcessLogList.add(wfProcessLog);
        });
        wfProcessLogService.saveBatch(wfProcessLogList);
        log.info("传阅wfProcessLog保存成功！");
    }

    /**
     * 通过分级获取用户信息
     * @param userName
     * @return
     */
    @SneakyThrows
    private MdmUserVo getMdmUserVo(String userName){
        MdmUserVo mdmUserVo = incloudCache.get(userName);
        if(mdmUserVo == null){
            throw new CheckedException("根据用户ID查找不到用户信息");
        }
        return mdmUserVo;
    }

    /**
     * 通过分级获取用户信息
     * @param wfDuplicateList
     * @return
     */
    @SneakyThrows
    private List<MdmUserVo> getMdmUserVos(List<Object> wfDuplicateList){
        List<MdmUserVo> users = incloudCache.gets(wfDuplicateList);
        if(users == null || users.isEmpty()){
            throw new CheckedException("根据用户ID查找不到用户信息");
        }
        return users;
    }
}
