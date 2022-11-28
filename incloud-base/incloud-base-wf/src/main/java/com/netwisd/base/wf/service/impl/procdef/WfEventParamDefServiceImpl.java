package com.netwisd.base.wf.service.impl.procdef;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.entity.WfEventParamDef;
import com.netwisd.base.wf.mapper.WfEventParamDefMapper;
import com.netwisd.base.wf.service.procdef.WfEventParamDefService;
import com.netwisd.base.wf.vo.WfEventParamRuntimeVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfEventParamDefDto;
import com.netwisd.base.wf.vo.WfEventParamDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Description 事件定义参数维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-13 17:00:37
 */
@Service
@Slf4j
public class WfEventParamDefServiceImpl extends BatchServiceImpl<WfEventParamDefMapper, WfEventParamDef> implements WfEventParamDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfEventParamDefMapper wfEventParamDefMapper;

    /**
    * 单表简单查询操作
    * @param wfEventParamDefDto
    * @return
    */
    @Override
    public Page list(WfEventParamDefDto wfEventParamDefDto) {
        WfEventParamDef wfEventParamDef = dozerMapper.map(wfEventParamDefDto,WfEventParamDef.class);
        LambdaQueryWrapper<WfEventParamDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfEventParamDef);
        Page<WfEventParamDef> page = wfEventParamDefMapper.selectPage(wfEventParamDefDto.getPage(),queryWrapper);
        Page<WfEventParamDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfEventParamDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfEventParamDefDto
    * @return
    */
    @Override
    public Page lists(WfEventParamDefDto wfEventParamDefDto) {
        Page<WfEventParamDefVo> pageVo = wfEventParamDefMapper.getPageList(wfEventParamDefDto.getPage(),wfEventParamDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfEventParamDefVo get(Long id) {
        WfEventParamDef wfEventParamDef = super.getById(id);
        WfEventParamDefVo wfEventParamDefVo = null;
        if(wfEventParamDef !=null){
            wfEventParamDefVo = dozerMapper.map(wfEventParamDef,WfEventParamDefVo.class);
        }
        log.debug("查询成功");
        return wfEventParamDefVo;
    }

    /**
    * 保存实体
    * @param wfEventParamDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfEventParamDefDto wfEventParamDefDto) {
        WfEventParamDef wfEventParamDef = dozerMapper.map(wfEventParamDefDto,WfEventParamDef.class);
        boolean result = super.save(wfEventParamDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfEventParamDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfEventParamDefDto wfEventParamDefDto) {
        WfEventParamDef wfEventParamDef = dozerMapper.map(wfEventParamDefDto,WfEventParamDef.class);
        boolean result = super.updateById(wfEventParamDef);
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
    public List<WfEventParamRuntimeVo> getEventParamsByConditions(Long eventId,String camundaProcdefId, String camundaNodeDefId, Integer eventType, String eventBindType) {
        List<WfEventParamRuntimeVo> eventParamsByConditions = wfEventParamDefMapper.getEventParamsByConditions(eventId,camundaProcdefId, camundaNodeDefId, eventType, eventBindType);
        log.info("eventParamsByConditions:{}", ObjectUtil.isNotEmpty(eventParamsByConditions)? JSONUtil.toJsonStr(eventParamsByConditions):null);
        return eventParamsByConditions;
    }

    @Override
    public void deleteByCamundaDefKey(String camundaDefKey) {
        log.debug("根据流程定义Key 删除所有的 事件-参数 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfEventParamDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfEventParamDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfEventParamDefMapper.delete(delWrapper);
        log.debug("根据流程定义Key 删除所有的 事件-参数 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 事件-参数 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfEventParamDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfEventParamDef::getCamundaProcdefId,camundaDefId);
        int line = wfEventParamDefMapper.delete(delWrapper);
        log.debug("根据流程定义id 删除所有的 事件-参数 信息(删除某个版本). 影响行数：{}", line);
    }
}
