package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.vo.wf.WfProcessVo;
import com.netwisd.base.common.wf.eunm.WfProcessEnum;
import com.netwisd.base.wf.entity.WfProcDef;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.mapper.WfProcessMapper;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfProcessDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description 流程实例 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-06 13:49:51
 */
@Service
@Slf4j
public class WfProcessServiceImpl extends ServiceImpl<WfProcessMapper, WfProcess> implements WfProcessService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfProcessMapper wfProcessMapper;

    /**
    * 单表简单查询操作
    * @param wfProcessDto
    * @return
    */
    @Override
    public Page list(WfProcessDto wfProcessDto) {
        LambdaQueryWrapper<WfProcess> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StrUtil.isNotEmpty(wfProcessDto.getBizKey()),WfProcess::getBizKey,wfProcessDto.getBizKey())
                .eq(StrUtil.isNotEmpty(wfProcessDto.getStarterName()),WfProcess::getStarterName,wfProcessDto.getStarterName())
                .eq(ObjectUtil.isNotEmpty(wfProcessDto.getProcdefTypeId()),WfProcess::getProcdefTypeId,wfProcessDto.getProcdefTypeId())
                .between(ObjectUtil.isNotEmpty(wfProcessDto.getApplyStartTime()) && ObjectUtil.isNotEmpty(wfProcessDto.getApplyEndTime())
                        ,WfProcess::getApplyTime,wfProcessDto.getApplyStartTime(),wfProcessDto.getApplyEndTime())
                .eq(ObjectUtil.isNotEmpty(wfProcessDto.getStarterOrgId()),WfProcess::getStarterOrgId,wfProcessDto.getStarterOrgId())
                .eq(ObjectUtil.isNotEmpty(wfProcessDto.getStarterDeptId()),WfProcess::getStarterDeptId,wfProcessDto.getStarterDeptId())
                .like(StrUtil.isNotEmpty(wfProcessDto.getCamundaProcdefKey()),WfProcess::getCamundaProcdefKey,wfProcessDto.getCamundaProcdefKey())
                .eq(ObjectUtil.isNotEmpty(wfProcessDto.getCamundaProcdefVersion()),WfProcess::getCamundaProcdefVersion,wfProcessDto.getCamundaProcdefVersion());
        queryWrapper.orderByDesc(WfProcess::getCreateTime);
        Page<WfProcess> page = wfProcessMapper.selectPage(wfProcessDto.getPage(),queryWrapper);
        Page<WfProcessVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfProcessVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfProcessDto
    * @return
    */
    @Override
    public Page lists(WfProcessDto wfProcessDto) {
        Page<WfProcessVo> pageVo = wfProcessMapper.getPageList(wfProcessDto.getPage(),wfProcessDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfProcessVo get(Long id) {
        WfProcess wfProcess = super.getById(id);
        WfProcessVo wfProcessVo = null;
        if(wfProcess !=null){
            wfProcessVo = dozerMapper.map(wfProcess,WfProcessVo.class);
        }
        log.debug("查询成功");
        return wfProcessVo;
    }

    /**
    * 保存实体
    * @param wfProcessDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfProcessDto wfProcessDto) {
        WfProcess wfProcess = dozerMapper.map(wfProcessDto,WfProcess.class);
        boolean result = super.save(wfProcess);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfProcessDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfProcessDto wfProcessDto) {
        WfProcess wfProcess = dozerMapper.map(wfProcessDto,WfProcess.class);
        boolean result = update(wfProcess);
        return result;
    }

    /**
     * 修改实体
     * @param wfProcess
     * @return
     */
    @Transactional
    @Override
    public Boolean update(WfProcess wfProcess) {
        boolean result = super.updateById(wfProcess);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    @Override
    @Transactional
    public Boolean updateStateByProInsId(String processInstanceId,Integer state) {
        LambdaUpdateWrapper<WfProcess> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(WfProcess::getCamundaProcinsId,processInstanceId)
                    .set(WfProcess::getState,state);
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
    public Boolean delete(String processInstanceId) {
        LambdaQueryWrapper<WfProcess> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcess::getCamundaProcinsId,processInstanceId);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    public WfProcess get(String camundaProcessId) {
        LambdaQueryWrapper<WfProcess> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcess::getCamundaProcinsId,camundaProcessId);
        try {
            WfProcess wfProcess = this.getOne(queryWrapper);
            return wfProcess;
        }catch (Exception e){
            throw new IncloudException("根据camundaProcessId：{}获取流程实例失败",camundaProcessId);
        }
    }

    @Override
    public WfProcess getAndCheck(String camundaProcessId) {
        WfProcess wfProcess = get(camundaProcessId);
        if(ObjectUtil.isEmpty(wfProcess)){
            throw new IncloudException("流程实例：{}不存在!",camundaProcessId);
        }
        return wfProcess;
    }

    @Override
    @Transactional
    public Boolean updateDefId(ProcessDefinition processDefinitionNew, WfProcDef wfProcDefNew, String processInstanceId) {
        LambdaUpdateWrapper<WfProcess> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(WfProcess::getCamundaProcinsId,processInstanceId)
                .set(WfProcess::getCamundaProcdefId,processDefinitionNew.getId())
                .set(WfProcess::getCamundaProcdefVersion,processDefinitionNew.getVersion())
                .set(WfProcess::getProcdefId,wfProcDefNew.getId());
        boolean update = update(queryWrapper);
        return update;
    }

    @Override
    public void checkProcessState(String processId) {
        if(StrUtil.isEmpty(processId)){
            throw new IncloudException("流程实例ID为空！");
        }
        WfProcess wfProcess = get(processId);

        if(ObjectUtil.isEmpty(wfProcess)){
            throw new IncloudException("通过流程实例ID查找不到流程实例！");
        }
        if(wfProcess.getState().intValue() == WfProcessEnum.SUPENSION.getType().intValue()){
            throw new IncloudException("流程已挂起，请重新激活后继续！");
        }
        if(wfProcess.getState().intValue() == WfProcessEnum.TERMINATION.getType().intValue()){
            throw new IncloudException("流程已终止！");
        }
        if(wfProcess.getState().intValue() == WfProcessEnum.DONE.getType().intValue()){
            throw new IncloudException("流程已结束！");
        }
        /*ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();
        if(ObjectUtil.isEmpty(processInstance)){
            throw new IncloudException("通过流程实例ID查找不到流程实例！");
        }
        if(processInstance.isSuspended()){
            throw new IncloudException("流程已挂起，请重新激活后继续！");
        }
        if(processInstance.isEnded()){
            throw new IncloudException("流程已结束！");
        }*/
    }

    @Override
    public Boolean getIsExistInstProcByCamundaProcdefId(String camundaProcdefId) {
        log.debug("根据流程定义id 查询是否存在流程实例（正在运行）参数：{}", camundaProcdefId);
        if(StringUtils.isNotBlank(camundaProcdefId)) {
            LambdaUpdateWrapper<WfProcess> queryWrapper = new LambdaUpdateWrapper<>();
            queryWrapper.eq(WfProcess::getCamundaProcdefId,camundaProcdefId)
                    .in(WfProcess::getState,1,3);
            List<WfProcess> list = wfProcessMapper.selectList(queryWrapper);
            if(CollectionUtil.isNotEmpty(list)) {
                log.debug("根据流程定义id 查询是否存在流程实例（正在运行）返回：{}", true);
                return true;
            } else {
                log.debug("根据流程定义id 查询是否存在流程实例（正在运行）返回：{}", false);
                return false;
            }
        } else {
            throw new IncloudException("流程定义id不能为空！");
        }
    }
}
