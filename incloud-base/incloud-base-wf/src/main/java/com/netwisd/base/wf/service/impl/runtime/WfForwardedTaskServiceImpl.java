package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.wf.dto.WfForwardedTaskDto;
import com.netwisd.base.wf.entity.WfForwardedTask;
import com.netwisd.base.wf.entity.WfMyInDuplicateTask;
import com.netwisd.base.wf.mapper.WfForwardedTaskMapper;
import com.netwisd.base.wf.service.runtime.WfForwardedTaskService;
import com.netwisd.common.core.exception.IncloudException;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.base.wf.vo.WfForwardedTaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 我发出的转办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2022-03-02 15:43:53
 */
@Service
@Slf4j
public class WfForwardedTaskServiceImpl extends ServiceImpl<WfForwardedTaskMapper, WfForwardedTask> implements WfForwardedTaskService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfForwardedTaskMapper wfForwardedTaskMapper;

    /**
    * 单表简单查询操作
    * @param wfForwardedTaskDto
    * @return
    */
    @Override
    public Page list(WfForwardedTaskDto wfForwardedTaskDto) {
        LambdaQueryWrapper<WfForwardedTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfForwardedTaskDto.getBizKey()), WfForwardedTask::getBizKey, wfForwardedTaskDto.getBizKey())
                .eq(WfForwardedTask::getOwnner,getUserID())
                .eq(StrUtil.isNotEmpty(wfForwardedTaskDto.getAssignee()), WfForwardedTask::getAssignee, wfForwardedTaskDto.getAssignee())
                .like(StrUtil.isNotEmpty(wfForwardedTaskDto.getAssigneeName()), WfForwardedTask::getAssigneeName, wfForwardedTaskDto.getAssigneeName())
                .like(StrUtil.isNotEmpty(wfForwardedTaskDto.getStarterName()), WfForwardedTask::getStarterName, wfForwardedTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfForwardedTaskDto.getProcdefTypeId()), WfForwardedTask::getProcdefTypeId, wfForwardedTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfForwardedTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfForwardedTaskDto.getApplyEndTime())
                        , WfForwardedTask::getApplyTime, wfForwardedTaskDto.getApplyStartTime(), wfForwardedTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfForwardedTaskDto.getStarterOrgId()), WfForwardedTask::getStarterOrgId, wfForwardedTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfForwardedTaskDto.getStarterDeptId()), WfForwardedTask::getStarterDeptId, wfForwardedTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfForwardedTaskDto.getCamundaProcdefKey()), WfForwardedTask::getCamundaProcdefKey, wfForwardedTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfForwardedTaskDto.getCamundaProcdefVersion()), WfForwardedTask::getCamundaProcdefVersion, wfForwardedTaskDto.getCamundaProcdefVersion());
        queryWrapper.orderByDesc(WfForwardedTask::getCreateTime);
        Page<WfForwardedTask> page = wfForwardedTaskMapper.selectPage(wfForwardedTaskDto.getPage(),queryWrapper);
        Page<WfForwardedTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfForwardedTaskVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    @Override
    public Page forwardedListForAd(WfForwardedTaskDto wfForwardedTaskDto) {
        LambdaQueryWrapper<WfForwardedTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfForwardedTaskDto.getBizKey()), WfForwardedTask::getBizKey, wfForwardedTaskDto.getBizKey())
                .eq(StrUtil.isNotEmpty(wfForwardedTaskDto.getOwnner()), WfForwardedTask::getOwnner, wfForwardedTaskDto.getOwnner())
                .like(StrUtil.isNotEmpty(wfForwardedTaskDto.getOwnnerName()), WfForwardedTask::getOwnnerName, wfForwardedTaskDto.getOwnnerName())
                .eq(StrUtil.isNotEmpty(wfForwardedTaskDto.getAssignee()), WfForwardedTask::getAssignee, wfForwardedTaskDto.getAssignee())
                .like(StrUtil.isNotEmpty(wfForwardedTaskDto.getAssigneeName()), WfForwardedTask::getAssigneeName, wfForwardedTaskDto.getAssigneeName())
                .like(StrUtil.isNotEmpty(wfForwardedTaskDto.getStarterName()), WfForwardedTask::getStarterName, wfForwardedTaskDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfForwardedTaskDto.getProcdefTypeId()), WfForwardedTask::getProcdefTypeId, wfForwardedTaskDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfForwardedTaskDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfForwardedTaskDto.getApplyEndTime())
                        , WfForwardedTask::getApplyTime, wfForwardedTaskDto.getApplyStartTime(), wfForwardedTaskDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfForwardedTaskDto.getStarterOrgId()), WfForwardedTask::getStarterOrgId, wfForwardedTaskDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfForwardedTaskDto.getStarterDeptId()), WfForwardedTask::getStarterDeptId, wfForwardedTaskDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfForwardedTaskDto.getCamundaProcdefKey()), WfForwardedTask::getCamundaProcdefKey, wfForwardedTaskDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfForwardedTaskDto.getCamundaProcdefVersion()), WfForwardedTask::getCamundaProcdefVersion, wfForwardedTaskDto.getCamundaProcdefVersion());
        queryWrapper.orderByDesc(WfForwardedTask::getCreateTime);
        Page<WfForwardedTask> page = wfForwardedTaskMapper.selectPage(wfForwardedTaskDto.getPage(),queryWrapper);
        Page<WfForwardedTaskVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfForwardedTaskVo.class);
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
            new IncloudException("用户未登录！");
        }
        String userId = loginAppUser.getUserName();
        return userId;
    }

    /**
    * 自定义查询操作
    * @param wfForwardedTaskDto
    * @return
    */
    @Override
    public Page lists(WfForwardedTaskDto wfForwardedTaskDto) {
        Page<WfForwardedTaskVo> pageVo = wfForwardedTaskMapper.getPageList(wfForwardedTaskDto.getPage(), wfForwardedTaskDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfForwardedTaskVo get(Long id) {
        WfForwardedTask wfForwardedTask = super.getById(id);
        WfForwardedTaskVo wfForwardedTaskVo = null;
        if(wfForwardedTask !=null){
            wfForwardedTaskVo = dozerMapper.map(wfForwardedTask, WfForwardedTaskVo.class);
        }
        log.debug("查询成功");
        return wfForwardedTaskVo;
    }

    /**
    * 保存实体
    * @param wfForwardedTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfForwardedTaskDto wfForwardedTaskDto) {
        WfForwardedTask wfForwardedTask = dozerMapper.map(wfForwardedTaskDto, WfForwardedTask.class);
        boolean result = super.save(wfForwardedTask);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfForwardedTaskDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfForwardedTaskDto wfForwardedTaskDto) {
        wfForwardedTaskDto.setUpdateTime(LocalDateTime.now());
        WfForwardedTask wfForwardedTask = dozerMapper.map(wfForwardedTaskDto, WfForwardedTask.class);
        boolean result = super.updateById(wfForwardedTask);
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
}
