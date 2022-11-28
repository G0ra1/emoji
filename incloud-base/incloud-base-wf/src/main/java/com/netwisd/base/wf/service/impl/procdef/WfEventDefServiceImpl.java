package com.netwisd.base.wf.service.impl.procdef;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfEventParamDefDto;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.mapper.WfEventDefMapper;
import com.netwisd.base.wf.service.procdef.WfEventDefService;
import com.netwisd.base.wf.service.procdef.WfEventParamDefService;
import com.netwisd.base.wf.vo.WfEventRuntimeVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfEventDefDto;
import com.netwisd.base.wf.vo.WfEventDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 事件定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 16:45:46
 */
@Service
@Slf4j
public class WfEventDefServiceImpl extends BatchServiceImpl<WfEventDefMapper, WfEventDef> implements WfEventDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfEventDefMapper wfEventDefMapper;

    @Autowired
    private WfEventParamDefService wfEventParamDefService;

    /**
    * 单表简单查询操作
    * @param wfEventDefDto
    * @return
    */
    @Override
    public Page list(WfEventDefDto wfEventDefDto) {
        WfEventDef wfEventDef = dozerMapper.map(wfEventDefDto,WfEventDef.class);
        LambdaQueryWrapper<WfEventDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfEventDef);
        Page<WfEventDef> page = wfEventDefMapper.selectPage(wfEventDefDto.getPage(),queryWrapper);
        Page<WfEventDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfEventDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfEventDefDto
    * @return
    */
    @Override
    public Page lists(WfEventDefDto wfEventDefDto) {
        Page<WfEventDefVo> pageVo = wfEventDefMapper.getPageList(wfEventDefDto.getPage(),wfEventDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfEventDefVo get(Long id) {
        WfEventDef wfEventDef = super.getById(id);
        WfEventDefVo wfEventDefVo = null;
        if(wfEventDef !=null){
            wfEventDefVo = dozerMapper.map(wfEventDef,WfEventDefVo.class);
        }
        log.debug("查询成功");
        return wfEventDefVo;
    }

    /**
    * 保存实体
    * @param wfEventDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfEventDefDto wfEventDefDto) {
        WfEventDef wfEventDef = dozerMapper.map(wfEventDefDto,WfEventDef.class);
        boolean result = super.save(wfEventDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfEventDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfEventDefDto wfEventDefDto) {
        WfEventDef wfEventDef = dozerMapper.map(wfEventDefDto,WfEventDef.class);
        boolean result = super.updateById(wfEventDef);
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

    @Override
    @Transactional
    public void saveXml2WfEventDef(List<WfEventDefDto> wfEventDefDtoList) {
        if(CollectionUtil.isNotEmpty(wfEventDefDtoList)) {
            log.info("操作：{}，根据条件查询详情记录，参数：[wfEventDefDtoList={}]", "saveXml2WfEventDef", wfEventDefDtoList.toString());
            List<WfEventDef> wfEventDefList = new ArrayList<>();
            List<WfEventParamDef> wfEventParamDefList = new ArrayList<>();
            for (WfEventDefDto wfEventDefDto : wfEventDefDtoList) {
                WfEventDef wfEventDef = dozerMapper.map(wfEventDefDto,WfEventDef.class);
                wfEventDefList.add(wfEventDef);
                List<WfEventParamDefDto> wfEventParamDefDto = wfEventDefDto.getWfEventParamDefDto();
                if(CollectionUtil.isNotEmpty(wfEventParamDefDto)) {
                    for (WfEventParamDefDto eventParamDefDto : wfEventParamDefDto) {
                        WfEventParamDef wfEventParamDef = dozerMapper.map(eventParamDefDto,WfEventParamDef.class);
                        wfEventParamDef.setEventDefId(wfEventDefDto.getId());
                        wfEventParamDefList.add(wfEventParamDef);
                    }
                }
            }
            if(CollectionUtil.isNotEmpty(wfEventDefList)) {
                if(CollectionUtil.isNotEmpty(wfEventDefList)) {
                    boolean saveButtonBoo = super.saveBatch(wfEventDefList);
                    log.debug("保存事件定义信息：{}", saveButtonBoo);
                }
                if(CollectionUtil.isNotEmpty(wfEventParamDefList)) {
                    boolean saveButtonParamBoo = wfEventParamDefService.saveBatch(wfEventParamDefList);
                    log.debug("保存事件定义-参数信息信息：{}", saveButtonParamBoo);
                }
            }
        }
    }

    @Override
    public List<WfEventRuntimeVo> getEventByConditions(String camundaProcdefId, String camundaNodeDefId, Integer eventType, String eventBindType) {
        List<WfEventRuntimeVo> eventByConditions = wfEventDefMapper.getEventByConditions(camundaProcdefId,camundaNodeDefId,eventType,eventBindType);
        return eventByConditions;
    }

    @Override
    public List<WfEventRuntimeVo> getMultiEventByConditions(String camundaProcdefId, String camundaNodeDefId, Integer eventType,String... eventBindType){
        return wfEventDefMapper.getMultiEventByConditions(camundaProcdefId,camundaNodeDefId,eventType,CollectionUtil.toList(eventBindType));
    }

    @Override
    @Transactional
    public void delEventByNodeId(Long nodeDefId, String camundaProcdefId) {
        if(ObjectUtil.isEmpty(nodeDefId)) {
            throw new IncloudException("节点定义id 不能为空！");
        }
        // 删除 事件对应的参数
        LambdaQueryWrapper<WfEventParamDef> delEventParamDefWrapper = new LambdaQueryWrapper<>();
        delEventParamDefWrapper.eq(WfEventParamDef::getNodeDefId,nodeDefId);
        delEventParamDefWrapper.eq(StringUtils.isNotBlank(camundaProcdefId),WfEventParamDef::getCamundaProcdefId,camundaProcdefId);
        wfEventParamDefService.remove(delEventParamDefWrapper);
        //删除 事件
        LambdaQueryWrapper<WfEventDef> delEventDefWrapper = new LambdaQueryWrapper<>();
        delEventDefWrapper.eq(WfEventDef::getNodeDefId,nodeDefId);
        delEventDefWrapper.eq(StringUtils.isNotBlank(camundaProcdefId),WfEventDef::getCamundaProcdefId,camundaProcdefId);
        this.remove(delEventDefWrapper);
    }

    @Override
    public void deleteByCamundaDefKey(String camundaDefKey) {
        log.debug("根据流程定义Key 删除所有的 事件 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfEventDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfEventDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfEventDefMapper.delete(delWrapper);
        //删除事件对应的参数信息
        wfEventParamDefService.deleteByCamundaDefKey(camundaDefKey);
        log.debug("根据流程定义Key 删除所有的 事件 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 事件 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfEventDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfEventDef::getCamundaProcdefId,camundaDefId);
        int line = wfEventDefMapper.delete(delWrapper);
        //删除事件对应的参数信息
        wfEventParamDefService.deleteByCamundaDefId(camundaDefId);
        log.debug("根据流程定义id 删除所有的 事件 信息(删除某个版本). 影响行数：{}", line);
    }

    @Override
    @Transactional
    public void delEventByCmdNodeIdAndCmdProcdefId(String camundaNodeId, String camundaProcdefId) {
        if(ObjectUtil.isEmpty(camundaNodeId)) {
            throw new IncloudException("camunda节点定义id 不能为空！");
        }
        if(ObjectUtil.isEmpty(camundaProcdefId)) {
            throw new IncloudException("camunda流程定义id 不能为空！");
        }
        // 删除 事件对应的参数
        LambdaQueryWrapper<WfEventParamDef> delEventParamDefWrapper = new LambdaQueryWrapper<>();
        delEventParamDefWrapper.eq(WfEventParamDef::getCamundaNodeDefId,camundaNodeId);
        delEventParamDefWrapper.eq(WfEventParamDef::getCamundaProcdefId,camundaProcdefId);
        wfEventParamDefService.remove(delEventParamDefWrapper);
        //删除 事件
        LambdaQueryWrapper<WfEventDef> delEventDefWrapper = new LambdaQueryWrapper<>();
        delEventDefWrapper.eq(WfEventDef::getCamundaNodeDefId,camundaNodeId);
        delEventDefWrapper.eq(WfEventDef::getCamundaProcdefId,camundaProcdefId);
        this.remove(delEventDefWrapper);
    }
}
