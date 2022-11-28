package com.netwisd.base.wf.service.impl.procdef;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfSequEventParamDefDto;
import com.netwisd.base.wf.entity.WfEventDef;
import com.netwisd.base.wf.entity.WfEventParamDef;
import com.netwisd.base.wf.entity.WfSequEventDef;
import com.netwisd.base.wf.entity.WfSequEventParamDef;
import com.netwisd.base.wf.mapper.WfSequEventDefMapper;
import com.netwisd.base.wf.service.procdef.WfSequEventDefService;
import com.netwisd.base.wf.service.procdef.WfSequEventParamDefService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfSequEventDefDto;
import com.netwisd.base.wf.vo.WfSequEventDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 流程定义-序列流-事件 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-22 16:55:36
 */
@Service
@Slf4j
public class WfSequEventDefServiceImpl extends BatchServiceImpl<WfSequEventDefMapper, WfSequEventDef> implements WfSequEventDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfSequEventDefMapper wfSequEventDefMapper;

    @Autowired
    private WfSequEventParamDefService wfSequEventParamDefService;

    /**
    * 单表简单查询操作
    * @param wfSequEventDefDto
    * @return
    */
    @Override
    public Page list(WfSequEventDefDto wfSequEventDefDto) {
        WfSequEventDef wfSequEventDef = dozerMapper.map(wfSequEventDefDto,WfSequEventDef.class);
        LambdaQueryWrapper<WfSequEventDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfSequEventDef);
        Page<WfSequEventDef> page = wfSequEventDefMapper.selectPage(wfSequEventDefDto.getPage(),queryWrapper);
        Page<WfSequEventDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfSequEventDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfSequEventDefDto
    * @return
    */
    @Override
    public Page lists(WfSequEventDefDto wfSequEventDefDto) {
        Page<WfSequEventDefVo> pageVo = wfSequEventDefMapper.getPageList(wfSequEventDefDto.getPage(),wfSequEventDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfSequEventDefVo get(Long id) {
        WfSequEventDef wfSequEventDef = super.getById(id);
        WfSequEventDefVo wfSequEventDefVo = null;
        if(wfSequEventDef !=null){
            wfSequEventDefVo = dozerMapper.map(wfSequEventDef,WfSequEventDefVo.class);
        }
        log.debug("查询成功");
        return wfSequEventDefVo;
    }

    /**
    * 保存实体
    * @param wfSequEventDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfSequEventDefDto wfSequEventDefDto) {
        WfSequEventDef wfSequEventDef = dozerMapper.map(wfSequEventDefDto,WfSequEventDef.class);
        boolean result = super.save(wfSequEventDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfSequEventDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfSequEventDefDto wfSequEventDefDto) {
        WfSequEventDef wfSequEventDef = dozerMapper.map(wfSequEventDefDto,WfSequEventDef.class);
        boolean result = super.updateById(wfSequEventDef);
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
    public void deleteByCamundaDefKey(String camundaDefKey) {
        log.debug("根据流程定义Key 删除所有的 序列流-事件 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfSequEventDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfSequEventDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfSequEventDefMapper.delete(delWrapper);
        //删除事件参数
        wfSequEventParamDefService.deleteByCamundaDefKey(camundaDefKey);
        log.debug("根据流程定义Key 删除所有的 序列流-事件 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 序列流-事件 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfSequEventDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfSequEventDef::getCamundaProcdefId,camundaDefId);
        int line = wfSequEventDefMapper.delete(delWrapper);
        //删除事件参数
        wfSequEventParamDefService.deleteByCamundaDefKey(camundaDefId);
        log.debug("根据流程定义id 删除所有的 序列流-事件 信息(删除某个版本). 影响行数：{}", line);
    }

    @Override
    @Transactional
    public void delSeqEventByCmdNodeIdAndCmdProcdefId(String camundaSequId, String camundaProcdefId) {
        if(ObjectUtil.isEmpty(camundaSequId)) {
            throw new IncloudException("camunda序列流定义id 不能为空！");
        }
        if(ObjectUtil.isEmpty(camundaProcdefId)) {
            throw new IncloudException("camunda序列流定义id 不能为空！");
        }
        // 删除 事件对应的参数
        LambdaQueryWrapper<WfSequEventParamDef> delEventParamDefWrapper = new LambdaQueryWrapper<>();
        delEventParamDefWrapper.eq(WfSequEventParamDef::getCamundaSequDefId,camundaSequId);
        delEventParamDefWrapper.eq(WfSequEventParamDef::getCamundaProcdefId,camundaProcdefId);
        wfSequEventParamDefService.remove(delEventParamDefWrapper);
        //删除 事件
        LambdaQueryWrapper<WfSequEventDef> delEventDefWrapper = new LambdaQueryWrapper<>();
        delEventDefWrapper.eq(WfSequEventDef::getCamundaSequDefId,camundaSequId);
        delEventDefWrapper.eq(WfSequEventDef::getCamundaProcdefId,camundaProcdefId);
        this.remove(delEventDefWrapper);
    }

    @Override
    @Transactional
    public void saveXml2SequEventDef(List<WfSequEventDefDto> wfSequEventDefDtoList) {
        List<WfSequEventDef> wfSequEventDefList = new ArrayList<>();
        List<WfSequEventParamDef> wfSequEventParamDefList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(wfSequEventDefDtoList)) {
            for (WfSequEventDefDto wfSequEventDefDto : wfSequEventDefDtoList) {
                WfSequEventDef wfSequEventDef = dozerMapper.map(wfSequEventDefDto,WfSequEventDef.class);
                wfSequEventDefList.add(wfSequEventDef);
                List<WfSequEventParamDefDto> wfSequEventParamDefDtoList = wfSequEventDefDto.getWfSequEventParamDefDtoList();
                if(CollectionUtil.isNotEmpty(wfSequEventParamDefDtoList)) {
                    for (WfSequEventParamDefDto wfSequEventParamDefDto : wfSequEventParamDefDtoList) {
                        WfSequEventParamDef wfSequEventParamDef = dozerMapper.map(wfSequEventParamDefDto,WfSequEventParamDef.class);
                        wfSequEventParamDefList.add(wfSequEventParamDef);
                    }
                }
            }
            if(CollectionUtil.isNotEmpty(wfSequEventDefList)) {
                if(CollectionUtil.isNotEmpty(wfSequEventDefList)) {
                    boolean saveButtonBoo = super.saveBatch(wfSequEventDefList);
                    log.debug("保存序列流事件定义信息：{}", saveButtonBoo);
                }
                if(CollectionUtil.isNotEmpty(wfSequEventParamDefList)) {
                    boolean saveButtonParamBoo = wfSequEventParamDefService.saveBatch(wfSequEventParamDefList);
                    log.debug("保存序列流事件参数定义-参数信息信息：{}", saveButtonParamBoo);
                }
            }
        }
    }
}
