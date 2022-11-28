package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.user.vo.EmplVO;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.entity.WfProcDef;
import com.netwisd.base.wf.entity.WfTodoTask;
import com.netwisd.base.wf.feign.UserClient;
import com.netwisd.base.wf.mapper.WfTodoTaskMapper;
import com.netwisd.base.wf.service.runtime.WfTodoTaskService;
import com.netwisd.base.wf.vo.WfProcDefVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.db.cache.IncloudCache;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfTodoTaskDto;
import com.netwisd.base.common.vo.wf.WfTodoTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 待办任务 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-06 16:38:05
 */
@Service
@Slf4j
public class WfTodoTaskServiceImpl extends ServiceImpl<WfTodoTaskMapper, WfTodoTask> implements WfTodoTaskService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfTodoTaskMapper wfTodoTaskMapper;

    @Autowired
    private UserClient userClient;

    @Autowired
    @Qualifier("incloudUserCache")
    IncloudCache<MdmUserVo> userIncloudCache;

    /**
    * 单表简单查询操作
    * @param wfTodoTaskDto
    * @return
    */
    @Override
    public Page list(WfTodoTaskDto wfTodoTaskDto) {
        LambdaQueryWrapper<WfTodoTask> queryWrapper = new LambdaQueryWrapper<>();
        StringBuffer sb = new StringBuffer();
        sb.append("FIND_IN_SET(\"").append(getUserID()).append("\",candidates)");
        queryWrapper
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getBizKey()), WfTodoTask::getBizKey,wfTodoTaskDto.getBizKey())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getCurrentUserId()), WfTodoTask::getCandidates,wfTodoTaskDto.getCurrentUserId())
                .apply(sb.toString())
                .like(WfTodoTask::getCandidates,getUserID())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getStarterName()),WfTodoTask::getStarterName,wfTodoTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getProcdefTypeId()),WfTodoTask::getProcdefTypeId,wfTodoTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfTodoTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfTodoTaskDto.getApplyEndTime())
                        ,WfTodoTask::getApplyTime,wfTodoTaskDto.getApplyStartTime(),wfTodoTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterOrgId()),WfTodoTask::getStarterOrgId,wfTodoTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterDeptId()),WfTodoTask::getStarterDeptId,wfTodoTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getStarterDeptName()),WfTodoTask::getStarterDeptName,wfTodoTaskDto.getStarterDeptName())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getCamundaProcdefKey()),WfTodoTask::getCamundaProcdefKey,wfTodoTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getCamundaProcdefVersion()),WfTodoTask::getCamundaProcdefVersion,wfTodoTaskDto.getCamundaProcdefVersion())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterId()), WfTodoTask::getStarterId,wfTodoTaskDto.getStarterId())
                .ne(WfTodoTask::getState, WfProcessEnum.NOTIFY.getType())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getState()), WfTodoTask::getState,wfTodoTaskDto.getState())
                .like(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterName()), WfTodoTask::getStarterName,wfTodoTaskDto.getStarterName())
                .like(ObjectUtil.isNotEmpty(wfTodoTaskDto.getReason()), WfTodoTask::getReason,wfTodoTaskDto.getReason())
                .ne(WfTodoTask::getIsDraft,1);
        queryWrapper.orderByDesc(WfTodoTask::getCreateTime);
        Page<WfTodoTask> page = wfTodoTaskMapper.selectPage(wfTodoTaskDto.getPage(),queryWrapper);
        Page<WfTodoTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfTodoTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
     * 获取用户ID
     * @return
     */
    private String getUserID(){
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(ObjectUtil.isEmpty(loginAppUser)){
            throw new IncloudException("用户未登录！");
        }
        String userId = loginAppUser.getUserName();
        return userId;
    }

    @Override
    public Page draftList(WfTodoTaskDto wfTodoTaskDto) {
        LambdaQueryWrapper<WfTodoTask> queryWrapper = new LambdaQueryWrapper<>();
        StringBuffer sb = new StringBuffer();
        sb.append("FIND_IN_SET(\"").append(getUserID()).append("\",candidates)");
        queryWrapper
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getBizKey()), WfTodoTask::getBizKey,wfTodoTaskDto.getBizKey())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getCurrentUserId()), WfTodoTask::getCandidates,wfTodoTaskDto.getCurrentUserId())
                .apply(sb.toString())
                .eq(StrUtil.isNotEmpty(wfTodoTaskDto.getStarterName()),WfTodoTask::getStarterName,wfTodoTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getProcdefTypeId()),WfTodoTask::getProcdefTypeId,wfTodoTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfTodoTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfTodoTaskDto.getApplyEndTime())
                        ,WfTodoTask::getApplyTime,wfTodoTaskDto.getApplyStartTime(),wfTodoTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterOrgId()),WfTodoTask::getStarterOrgId,wfTodoTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterDeptId()),WfTodoTask::getStarterDeptId,wfTodoTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getStarterDeptName()),WfTodoTask::getStarterDeptName,wfTodoTaskDto.getStarterDeptName())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getCamundaProcdefKey()),WfTodoTask::getCamundaProcdefKey,wfTodoTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getCamundaProcdefVersion()),WfTodoTask::getCamundaProcdefVersion,wfTodoTaskDto.getCamundaProcdefVersion())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterId()), WfTodoTask::getStarterId,wfTodoTaskDto.getStarterId())
                .like(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterName()), WfTodoTask::getStarterName,wfTodoTaskDto.getStarterName())
                .like(ObjectUtil.isNotEmpty(wfTodoTaskDto.getReason()), WfTodoTask::getReason,wfTodoTaskDto.getReason())
                .ne(WfTodoTask::getState, WfProcessEnum.NOTIFY.getType())
                .eq(WfTodoTask::getIsDraft,1);
        queryWrapper.orderByDesc(WfTodoTask::getCreateTime);
        Page<WfTodoTask> page = this.page(wfTodoTaskDto.getPage(),queryWrapper);
        Page<WfTodoTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfTodoTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfTodoTaskDto
    * @return
    */
    @Override
    public Page lists(WfTodoTaskDto wfTodoTaskDto) {
        Page<WfTodoTaskVo> pageVo = wfTodoTaskMapper.getPageList(wfTodoTaskDto.getPage(),wfTodoTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfTodoTaskVo get(Long id) {
        WfTodoTask wfTodoTask = super.getById(id);
        WfTodoTaskVo wfTodoTaskVo = null;
        if(wfTodoTask !=null){
            wfTodoTaskVo = dozerMapper.map(wfTodoTask,WfTodoTaskVo.class);
        }
        log.debug("查询成功");
        return wfTodoTaskVo;
    }

    /**
    * 保存实体
    * @param wfTodoTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfTodoTaskDto wfTodoTaskDto) {
        WfTodoTask wfTodoTask = dozerMapper.map(wfTodoTaskDto,WfTodoTask.class);
        boolean result = super.save(wfTodoTask);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfTodoTask
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfTodoTask wfTodoTask) {
        boolean result = super.updateById(wfTodoTask);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean updateStateByProInsId(String processInstanceId,Integer state) {
        LambdaUpdateWrapper<WfTodoTask> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.set(WfTodoTask::getState,state)
                    .eq(WfTodoTask::getCamundaProcinsId,processInstanceId);
        boolean update = update(queryWrapper);
        return update;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean delete(String camundaTaskId) {
        LambdaQueryWrapper<WfTodoTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfTodoTask::getCamundaTaskId,camundaTaskId);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    @Transactional
    public Boolean deleteProcinsId(String processInstanceId) {
        LambdaQueryWrapper<WfTodoTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfTodoTask::getCamundaProcinsId,processInstanceId);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    @Transactional
    public Boolean delWfTodoTaskByProInsAdnNodeKey(String procInstanceId, String nodeKey) {
        LambdaQueryWrapper<WfTodoTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfTodoTask::getCamundaProcinsId,procInstanceId)
                .eq(WfTodoTask::getNodeKey,nodeKey);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    @Transactional
    public Boolean delWfTodoTaskByProInsAdnTaskId(String procInstanceId, String taskId) {
        LambdaQueryWrapper<WfTodoTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfTodoTask::getCamundaProcinsId,procInstanceId)
                .eq(WfTodoTask::getCamundaTaskId,taskId);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    public WfTodoTask get(String camundaTaskId) {
        LambdaQueryWrapper<WfTodoTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfTodoTask::getCamundaTaskId,camundaTaskId);
        WfTodoTask one = this.getOne(queryWrapper);
        if(ObjectUtil.isEmpty(one)){
            log.info("通过camundaTaskId查找不到WfTodoTask对象！");
        }
        return one;
    }

    @Override
    public WfTodoTask getAndCheck(String camundaTaskId) {
        WfTodoTask wfTodoTask = get(camundaTaskId);
        if(ObjectUtil.isEmpty(wfTodoTask)){
            throw new IncloudException("通过taskId查找不到wfTodoTask对象");
        }
        return wfTodoTask;
    }

    @Override
    public WfTodoTaskVo getFirstOne(String camundaProcInsId) {
        LambdaQueryWrapper<WfTodoTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfTodoTask::getCamundaProcinsId,camundaProcInsId).
        like(WfTodoTask::getCandidates,getUserID()).
        orderBy(true,false,WfTodoTask::getCreateTime);
        List<WfTodoTask> list = this.list(queryWrapper);
        if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
            WfTodoTask wfTodoTask = list.stream().findFirst().get();
            WfTodoTaskVo vo = dozerMapper.map(wfTodoTask, WfTodoTaskVo.class);
            return vo;
        }
        log.info("通过camundaProcInsId查找不到WfTodoTask对象！");
        return null;
    }

    @Override
    public List<WfTodoTaskVo> getTodoList(String camundaProcInsId) {
        LambdaQueryWrapper<WfTodoTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfTodoTask::getCamundaProcinsId,camundaProcInsId).
                orderBy(true,false,WfTodoTask::getCreateTime);
        List<WfTodoTask> list = this.list(queryWrapper);
        if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
            WfTodoTask wfTodoTask = list.stream().findFirst().get();
            List<WfTodoTaskVo> listVo = DozerUtils.mapList(dozerMapper, list, WfTodoTaskVo.class);
            return listVo;
        }
        log.info("通过camundaProcInsId查找不到WfTodoTask对象！");
        return null;
    }

    @Override
    @Transactional
    public Boolean updateDefId(ProcessDefinition processDefinitionNew, WfProcDef wfProcDefNew, String processInstanceId) {
        LambdaUpdateWrapper<WfTodoTask> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(WfTodoTask::getCamundaProcinsId,processInstanceId)
                .set(WfTodoTask::getCamundaProcdefId,processDefinitionNew.getId())
                .set(WfTodoTask::getCamundaProcdefVersion,processDefinitionNew.getVersion())
                .set(WfTodoTask::getProcdefId,wfProcDefNew.getId());
        boolean update = update(queryWrapper);
        return update;
    }

    @Override
    public Result queryTodoTaskNum(String idCard) {
        if(StringUtils.isBlank(idCard)) {
            throw new IncloudException("身份证信息不能为空！");
        }
        log.info("查询当前登录人待办数量idCard{}！",idCard);
        //根据身份证号 查询用户信息
        EmplVO emplVO = userClient.findByIdentityNumber(idCard);
        if(null == emplVO) {
            throw new IncloudException("没有该用户信息！");
        }
        WfTodoTaskDto wfTodoTaskDto = new WfTodoTaskDto();
        wfTodoTaskDto.setCandidates(emplVO.getUserId());
        int num = wfTodoTaskMapper.queryTodoTaskNum(wfTodoTaskDto);
        log.info("查询当前登录人待办数量！{}",num);
        return Result.success(num);
    }

    @Override
    public Page draftListForAd(WfTodoTaskDto wfTodoTaskDto) {
        LambdaQueryWrapper<WfTodoTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getBizKey()), WfTodoTask::getBizKey,wfTodoTaskDto.getBizKey())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getCurrentUserId()), WfTodoTask::getCandidates,wfTodoTaskDto.getCurrentUserId())
                .eq(StrUtil.isNotEmpty(wfTodoTaskDto.getStarterName()),WfTodoTask::getStarterName,wfTodoTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getProcdefTypeId()),WfTodoTask::getProcdefTypeId,wfTodoTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfTodoTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfTodoTaskDto.getApplyEndTime())
                        ,WfTodoTask::getApplyTime,wfTodoTaskDto.getApplyStartTime(),wfTodoTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterOrgId()),WfTodoTask::getStarterOrgId,wfTodoTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterDeptId()),WfTodoTask::getStarterDeptId,wfTodoTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getStarterDeptName()),WfTodoTask::getStarterDeptName,wfTodoTaskDto.getStarterDeptName())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getCamundaProcdefKey()),WfTodoTask::getCamundaProcdefKey,wfTodoTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getCamundaProcdefVersion()),WfTodoTask::getCamundaProcdefVersion,wfTodoTaskDto.getCamundaProcdefVersion())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterId()), WfTodoTask::getStarterId,wfTodoTaskDto.getStarterId())
                .like(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterName()), WfTodoTask::getStarterName,wfTodoTaskDto.getStarterName())
                .like(ObjectUtil.isNotEmpty(wfTodoTaskDto.getReason()), WfTodoTask::getReason,wfTodoTaskDto.getReason())
                .eq(WfTodoTask::getIsDraft,1);
        queryWrapper.orderByDesc(WfTodoTask::getCreateTime);
        Page<WfTodoTask> page = this.page(wfTodoTaskDto.getPage(),queryWrapper);
        Page<WfTodoTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfTodoTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page todoListForAd(WfTodoTaskDto wfTodoTaskDto) {
        LambdaQueryWrapper<WfTodoTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getBizKey()), WfTodoTask::getBizKey,wfTodoTaskDto.getBizKey())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getCurrentUserId()), WfTodoTask::getCandidates,wfTodoTaskDto.getCurrentUserId())
                .like(WfTodoTask::getCandidates,getUserID())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getAssigneeName()),WfTodoTask::getAssigneeName,wfTodoTaskDto.getAssigneeName())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getProcdefTypeId()),WfTodoTask::getProcdefTypeId,wfTodoTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfTodoTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfTodoTaskDto.getApplyEndTime())
                        ,WfTodoTask::getApplyTime,wfTodoTaskDto.getApplyStartTime(),wfTodoTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterOrgId()),WfTodoTask::getStarterOrgId,wfTodoTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterDeptId()),WfTodoTask::getStarterDeptId,wfTodoTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getStarterDeptName()),WfTodoTask::getStarterDeptName,wfTodoTaskDto.getStarterDeptName())
                .like(StrUtil.isNotEmpty(wfTodoTaskDto.getCamundaProcdefKey()),WfTodoTask::getCamundaProcdefKey,wfTodoTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getCamundaProcdefVersion()),WfTodoTask::getCamundaProcdefVersion,wfTodoTaskDto.getCamundaProcdefVersion())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterId()), WfTodoTask::getStarterId,wfTodoTaskDto.getStarterId())
                .ne(WfTodoTask::getState, WfProcessEnum.NOTIFY.getType())
                .eq(ObjectUtil.isNotEmpty(wfTodoTaskDto.getState()), WfTodoTask::getState,wfTodoTaskDto.getState())
                .like(ObjectUtil.isNotEmpty(wfTodoTaskDto.getStarterName()), WfTodoTask::getStarterName,wfTodoTaskDto.getStarterName())
                .like(ObjectUtil.isNotEmpty(wfTodoTaskDto.getReason()), WfTodoTask::getReason,wfTodoTaskDto.getReason())
                .ne(WfTodoTask::getIsDraft,1);
        queryWrapper.orderByDesc(WfTodoTask::getCreateTime);
        Page<WfTodoTask> page = wfTodoTaskMapper.selectPage(wfTodoTaskDto.getPage(),queryWrapper);
        Page<WfTodoTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfTodoTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Boolean updateAssigneeByTodoId(String id, String assignees) {
        if(StringUtils.isBlank(id)) {
            throw new IncloudException("任务ID不能为空！");
        }
        if(StringUtils.isBlank(assignees)) {
            throw new IncloudException("修改的办理人不能为空！");
        }
        List<String> streamStr = Stream.of(assignees.split(",")).collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(streamStr)) {
            List<Object> objList = new ArrayList<>();
            streamStr.forEach(d->objList.add(Long.valueOf(d)));
            List<MdmUserVo> mdmUserVos = userIncloudCache.gets(objList);
            if(CollectionUtil.isEmpty(mdmUserVos)) {
                throw new IncloudException("获取缓存数据失败！");
            }
            List<String> userNames = mdmUserVos.stream().map(MdmUserVo::getUserName).collect(Collectors.toList());
            List<String> userNameChs = mdmUserVos.stream().map(MdmUserVo::getUserNameCh).collect(Collectors.toList());
            LambdaUpdateWrapper<WfTodoTask> queryWrapper = new LambdaUpdateWrapper<>();
            queryWrapper.set(WfTodoTask::getAssignee,String.join(",",userNames))
                    .set(WfTodoTask::getAssigneeName,String.join(",",userNameChs))
                    .eq(WfTodoTask::getId,id);
            boolean update = update(queryWrapper);
            return update;
        }else {
            throw new IncloudException("没有获取办理人信息！");
        }
    }

    @Override
    public Result queryTodoTaskNumByCmdProcDefId(String idCard, String camundaProcdefId) {
        if(StringUtils.isBlank(idCard)) {
            throw new IncloudException("身份证信息不能为空！");
        }
        if(StringUtils.isBlank(camundaProcdefId)) {
            throw new IncloudException("流程定义ID不能为空！");
        }
        log.info("根据身份证号 以及流程定义id查询 待办数据量:{},camundaProcdefId:{}.",idCard,camundaProcdefId);
        //根据身份证号 查询用户信息
        EmplVO emplVO = userClient.findByIdentityNumber(idCard);
        if(null == emplVO) {
            throw new IncloudException("没有该用户信息！");
        }
        WfTodoTaskDto wfTodoTaskDto = new WfTodoTaskDto();
        wfTodoTaskDto.setCandidates(emplVO.getUserId());
        wfTodoTaskDto.setCamundaProcdefId(camundaProcdefId);
        int num = wfTodoTaskMapper.queryTodoTaskNum(wfTodoTaskDto);
        log.info("查询当前登录人待办数量！{}",num);
        return Result.success(num);
    }

    @Override
    public Result queryTodokNumByCondition(String camundaProcdefKey) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        WfTodoTaskDto wfTodoTaskDto = new WfTodoTaskDto();
        wfTodoTaskDto.setCandidates(loginAppUser.getUsername());
        if(StringUtils.isNotBlank(camundaProcdefKey)) {
            wfTodoTaskDto.setCamundaProcdefKey(camundaProcdefKey);
        }
        int num = wfTodoTaskMapper.queryTodoTaskNum(wfTodoTaskDto);
        log.info("查询当前登录人待办数量！{}",num);
        return Result.success(num);
    }
}
