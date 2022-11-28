package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.wf.dto.WfDelegationTaskDto;
import com.netwisd.base.wf.entity.WfDelegationTask;
import com.netwisd.base.wf.entity.WfMyInDuplicateTask;
import com.netwisd.base.wf.mapper.WfDelegationTaskMapper;
import com.netwisd.base.wf.service.runtime.WfDelegationTaskService;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.wf.vo.WfDelegationTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 我委托的待办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2022-03-02 15:19:21
 */
@Service
@Slf4j
public class WfDelegationTaskServiceImpl extends ServiceImpl<WfDelegationTaskMapper, WfDelegationTask> implements WfDelegationTaskService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfDelegationTaskMapper wfDelegationTaskMapper;

    /**
    * 单表简单查询操作
    * @param wfDelegationTaskDto
    * @return
    */
    @Override
    public Page list(WfDelegationTaskDto wfDelegationTaskDto) {
        LambdaQueryWrapper<WfDelegationTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfDelegationTaskDto.getBizKey()), WfDelegationTask::getBizKey, wfDelegationTaskDto.getBizKey())
                .eq(WfDelegationTask::getOwnner,getUserID())
                .eq(StrUtil.isNotEmpty(wfDelegationTaskDto.getAssignee()), WfDelegationTask::getAssignee, wfDelegationTaskDto.getAssignee())
                .like(StrUtil.isNotEmpty(wfDelegationTaskDto.getAssigneeName()), WfDelegationTask::getAssigneeName, wfDelegationTaskDto.getAssigneeName())
                .eq(StrUtil.isNotEmpty(wfDelegationTaskDto.getStarterName()), WfDelegationTask::getStarterName, wfDelegationTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfDelegationTaskDto.getProcdefTypeId()), WfDelegationTask::getProcdefTypeId, wfDelegationTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfDelegationTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfDelegationTaskDto.getApplyEndTime())
                        , WfDelegationTask::getApplyTime, wfDelegationTaskDto.getApplyStartTime(), wfDelegationTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfDelegationTaskDto.getStarterOrgId()), WfDelegationTask::getStarterOrgId, wfDelegationTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfDelegationTaskDto.getStarterDeptId()), WfDelegationTask::getStarterDeptId, wfDelegationTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfDelegationTaskDto.getCamundaProcdefKey()), WfDelegationTask::getCamundaProcdefKey, wfDelegationTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfDelegationTaskDto.getCamundaProcdefVersion()), WfDelegationTask::getCamundaProcdefVersion, wfDelegationTaskDto.getCamundaProcdefVersion());
        queryWrapper.orderByDesc(WfDelegationTask::getCreateTime);
        Page<WfDelegationTask> page = wfDelegationTaskMapper.selectPage(wfDelegationTaskDto.getPage(),queryWrapper);
        Page<WfDelegationTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfDelegationTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfDelegationTaskDto
    * @return
    */
    @Override
    public Page lists(WfDelegationTaskDto wfDelegationTaskDto) {
        Page<WfDelegationTaskVo> pageVo = wfDelegationTaskMapper.getPageList(wfDelegationTaskDto.getPage(), wfDelegationTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfDelegationTaskVo get(Long id) {
        WfDelegationTask wfDelegationTask = super.getById(id);
        WfDelegationTaskVo wfDelegationTaskVo = null;
        if(wfDelegationTask !=null){
            wfDelegationTaskVo = dozerMapper.map(wfDelegationTask, WfDelegationTaskVo.class);
        }
        log.debug("查询成功");
        return wfDelegationTaskVo;
    }

    /**
    * 保存实体
    * @param wfDelegationTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfDelegationTaskDto wfDelegationTaskDto) {
        WfDelegationTask wfDelegationTask = dozerMapper.map(wfDelegationTaskDto, WfDelegationTask.class);
        boolean result = super.save(wfDelegationTask);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfDelegationTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfDelegationTaskDto wfDelegationTaskDto) {
        wfDelegationTaskDto.setUpdateTime(LocalDateTime.now());
        WfDelegationTask wfDelegationTask = dozerMapper.map(wfDelegationTaskDto, WfDelegationTask.class);
        boolean result = super.updateById(wfDelegationTask);
        if(result){
            log.debug("修改成功");
        }
        return result;
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

    /**
     * 获取用户ID
     * @return
     */
    private String getUserID(){
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if(ObjectUtil.isEmpty(loginAppUser)){
            new IncloudException("用户未登录！");
        }
        String userId = loginAppUser.getUserName();
        return userId;
    }
}
