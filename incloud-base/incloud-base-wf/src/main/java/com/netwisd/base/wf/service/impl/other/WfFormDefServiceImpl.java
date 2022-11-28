package com.netwisd.base.wf.service.impl.other;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfFormFieldsDefDto;
import com.netwisd.base.wf.dto.WfVarDefDto;
import com.netwisd.base.wf.entity.WfExpreUserDef;
import com.netwisd.base.wf.entity.WfExpreUserParamDef;
import com.netwisd.base.wf.entity.WfFormDef;
import com.netwisd.base.wf.entity.WfFormFieldsDef;
import com.netwisd.base.wf.mapper.WfFormDefMapper;
import com.netwisd.base.wf.service.other.WfFormDefService;
import com.netwisd.base.wf.service.other.WfFormFieldsDefService;
import com.netwisd.base.wf.service.procdef.WfVarDefService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfFormDefDto;
import com.netwisd.base.wf.vo.WfFormDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 流程表单定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:31:54
 */
@Service
@Slf4j
public class WfFormDefServiceImpl extends BatchServiceImpl<WfFormDefMapper, WfFormDef> implements WfFormDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfFormDefMapper wfFormDefMapper;

    @Autowired
    private WfFormFieldsDefService wfFormFieldsDefService;

    @Autowired
    private WfVarDefService wfVarDefService;

    /**
    * 单表简单查询操作
    * @param wfFormDefDto
    * @return
    */
    @Override
    public Page list(WfFormDefDto wfFormDefDto) {
        WfFormDef wfFormDef = dozerMapper.map(wfFormDefDto,WfFormDef.class);
        LambdaQueryWrapper<WfFormDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfFormDef);
        Page<WfFormDef> page = wfFormDefMapper.selectPage(wfFormDefDto.getPage(),queryWrapper);
        Page<WfFormDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfFormDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfFormDefDto
    * @return
    */
    @Override
    public List<WfFormDefVo> lists(WfFormDefDto wfFormDefDto) {
        LambdaQueryWrapper<WfFormDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(wfFormDefDto.getCamundaProcdefId()),WfFormDef::getCamundaProcdefId,wfFormDefDto.getCamundaProcdefId());
        queryWrapper.eq(StringUtils.isNotBlank(wfFormDefDto.getCamundaNodeDefId()),WfFormDef::getCamundaNodeDefId,wfFormDefDto.getCamundaNodeDefId());
        List<WfFormDef> list = wfFormDefMapper.selectList(queryWrapper);
        List<WfFormDefVo> listVo = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list)) {
            listVo = DozerUtils.mapList(dozerMapper, list, WfFormDefVo.class);
        }
        return listVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfFormDefVo get(Long id) {
        WfFormDef wfFormDef = super.getById(id);
        WfFormDefVo wfFormDefVo = null;
        if(wfFormDef !=null){
            wfFormDefVo = dozerMapper.map(wfFormDef,WfFormDefVo.class);
        }
        log.debug("查询成功");
        return wfFormDefVo;
    }

    /**
    * 保存实体
    * @param wfFormDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfFormDefDto wfFormDefDto) {
        WfFormDef wfFormDef = dozerMapper.map(wfFormDefDto,WfFormDef.class);
        boolean result = super.save(wfFormDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfFormDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfFormDefDto wfFormDefDto) {
        WfFormDef wfFormDef = dozerMapper.map(wfFormDefDto,WfFormDef.class);
        boolean result = super.updateById(wfFormDef);
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
        log.debug("根据流程定义Key 删除所有的 表单 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfFormDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfFormDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfFormDefMapper.delete(delWrapper);
        log.debug("根据流程定义Key 删除所有的 表单 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 表单 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfFormDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfFormDef::getCamundaProcdefId,camundaDefId);
        int line = wfFormDefMapper.delete(delWrapper);
        log.debug("根据流程定义id 删除所有的 表单 信息(删除某个版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByNodeDefId(List<Serializable> delIds) {
        log.debug("根据子流程数据id 删除表单信息（子流程使用）. camundaNodeDefId：{}", delIds);
        if(CollectionUtil.isEmpty(delIds)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfFormDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfFormDef::getProcdefId,delIds);
        int line = wfFormDefMapper.deleteBatchIds(delIds);
        log.debug("根据子流程数据id 删除表单信息（子流程使用）. 影响行数：{}", line);
    }

    @Override
    @Transactional
    public void saveXml2WfFormDefDef(List<WfFormDefDto> wfFormDefDtoList) {
        List<WfFormDef> wfFormDefList = new ArrayList<>(); //表单信息
        List<WfFormFieldsDef> wfFormFieldsDefList = new ArrayList<>(); //表单字段
        if(CollectionUtil.isNotEmpty(wfFormDefDtoList)) {
            for (WfFormDefDto wfFormDefDto : wfFormDefDtoList) {
                WfFormDef wfFormDef = dozerMapper.map(wfFormDefDto, WfFormDef.class);
                wfFormDefList.add(wfFormDef);
                List<WfFormFieldsDefDto> wfFormFieldsDefDtoList = wfFormDefDto.getWfFormFieldsDefDtoList();
                if(CollectionUtil.isNotEmpty(wfFormFieldsDefDtoList)) {
                    for (WfFormFieldsDefDto wfFormFieldsDefDto : wfFormFieldsDefDtoList) {
                        WfFormFieldsDef wfFormFieldsDef = dozerMapper.map(wfFormFieldsDefDto, WfFormFieldsDef.class);
                        wfFormFieldsDefList.add(wfFormFieldsDef);
                    }
                }
                //处理表单映射变量
                List<WfVarDefDto> wfVarDefDtoList = wfFormDefDto.getWfVarDefDtoList();
                if(CollectionUtil.isNotEmpty(wfVarDefDtoList)) {
                    wfVarDefService.saveXml2WfVarsDef(wfVarDefDtoList);
                }
            }
        }
        if(CollectionUtil.isNotEmpty(wfFormDefList)) {
            boolean savEventParamDefBoo = this.saveBatch(wfFormDefList);
            log.debug("保存节点表单信息：{}", savEventParamDefBoo);
        }
        if(CollectionUtil.isNotEmpty(wfFormFieldsDefList)) {
            boolean saveExpreUserDefBoo = wfFormFieldsDefService.saveBatch(wfFormFieldsDefList);
            log.debug("保存节点表单字段信息：{}", saveExpreUserDefBoo);
        }
    }

    @Override
    @Transactional
    public void delFormByCmdNodeIdAndCmdProcdefId(String camundaNodeId, String camundaProcdefId) {
        if(ObjectUtil.isEmpty(camundaNodeId)) {
            throw new IncloudException("camunda节点定义id 不能为空！");
        }
        if(ObjectUtil.isEmpty(camundaProcdefId)) {
            throw new IncloudException("camunda流程定义id 不能为空！");
        }
        LambdaQueryWrapper<WfFormDef> delFormFieldsWrapper = new LambdaQueryWrapper<>();
        delFormFieldsWrapper.eq(WfFormDef::getCamundaNodeDefId,camundaNodeId);
        delFormFieldsWrapper.eq(WfFormDef::getCamundaProcdefId,camundaProcdefId);
        this.remove(delFormFieldsWrapper);
    }
}
