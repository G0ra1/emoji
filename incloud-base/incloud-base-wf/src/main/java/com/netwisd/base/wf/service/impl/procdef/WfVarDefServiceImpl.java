package com.netwisd.base.wf.service.impl.procdef;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.constants.YesNo;
import com.netwisd.base.wf.entity.WfVarDef;
import com.netwisd.base.wf.mapper.WfVarDefMapper;
import com.netwisd.base.wf.service.procdef.WfVarDefService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfVarDefDto;
import com.netwisd.base.wf.vo.WfVarDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 流程定义-变量 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 12:56:28
 */
@Service
@Slf4j
public class WfVarDefServiceImpl extends BatchServiceImpl<WfVarDefMapper, WfVarDef> implements WfVarDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfVarDefMapper wfVarDefMapper;

    /**
    * 单表简单查询操作
    * @param wfVarDefDto
    * @return
    */
    @Override
    public Page list(WfVarDefDto wfVarDefDto) {
        WfVarDef wfVarDef = dozerMapper.map(wfVarDefDto,WfVarDef.class);
        LambdaQueryWrapper<WfVarDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfVarDef);
        Page<WfVarDef> page = wfVarDefMapper.selectPage(wfVarDefDto.getPage(),queryWrapper);
        Page<WfVarDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfVarDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfVarDefDto
    * @return
    */
    @Override
    public Page lists(WfVarDefDto wfVarDefDto) {
        Page<WfVarDefVo> pageVo = wfVarDefMapper.getPageList(wfVarDefDto.getPage(),wfVarDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfVarDefVo get(Long id) {
        WfVarDef wfVarDef = super.getById(id);
        WfVarDefVo wfVarDefVo = null;
        if(wfVarDef !=null){
            wfVarDefVo = dozerMapper.map(wfVarDef,WfVarDefVo.class);
        }
        log.debug("查询成功");
        return wfVarDefVo;
    }

    /**
    * 保存实体
    * @param wfVarDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfVarDefDto wfVarDefDto) {
        WfVarDef wfVarDef = dozerMapper.map(wfVarDefDto,WfVarDef.class);
        boolean result = super.save(wfVarDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfVarDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfVarDefDto wfVarDefDto) {
        WfVarDef wfVarDef = dozerMapper.map(wfVarDefDto,WfVarDef.class);
        boolean result = super.updateById(wfVarDef);
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
    public List<WfVarDefVo> getVarByProcDefIdAndNodeDefId(String camundaProcdefId, String camundaNodeDefId,Boolean isOrm) {
        log.debug("根据camunda流程定义和camunda节点定义获取对应的变量信息 参数{}，{}：", camundaProcdefId,camundaNodeDefId);
        if(StringUtils.isBlank(camundaProcdefId)) {
            throw  new IncloudException("流程定义id,不能为空！");
        }
        if(StringUtils.isBlank(camundaNodeDefId)) {
            throw  new IncloudException("节点定义id,不能为空！");
        }
        LambdaQueryWrapper<WfVarDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfVarDef::getCamundaProcdefId,camundaProcdefId)
        .eq(WfVarDef::getCamundaNodeDefId, camundaNodeDefId);
        if(ObjectUtil.isNotEmpty(isOrm)){
            if(isOrm){
                queryWrapper.eq(WfVarDef::getIsOrm, YesNo.YES.code);
            }else {
                queryWrapper.eq(WfVarDef::getIsOrm, YesNo.NO.code);
            }
        }
        List<WfVarDef> wfVarDefList = wfVarDefMapper.selectList(queryWrapper);
        List<WfVarDefVo> wfVarDefVoList = new ArrayList<>();
        log.debug("根据camunda流程定义和camunda节点定义获取对应的变量信息：" + wfVarDefVoList);
        if(CollectionUtil.isNotEmpty(wfVarDefList)) {
            for (WfVarDef wfVarDef : wfVarDefList) {
                WfVarDefVo wfVarDefVo = dozerMapper.map(wfVarDef,WfVarDefVo.class);
                wfVarDefVoList.add(wfVarDefVo);
            }
        }
        return wfVarDefVoList;
    }

    @Override
    public List<WfVarDefVo> getVarByProcDefIdAndSequDefId(String camundaProcdefId, String camundaSequDefId,Boolean isOrm) {
        log.debug("根据camunda流程定义和camunda序列流定义获取对应的变量信息 参数{}，{}：", camundaProcdefId,camundaSequDefId);
        if(StringUtils.isBlank(camundaProcdefId)) {
            throw  new IncloudException("流程定义id,不能为空！");
        }
        if(StringUtils.isBlank(camundaSequDefId)) {
            throw  new IncloudException("序列流定义id,不能为空！");
        }
        LambdaQueryWrapper<WfVarDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfVarDef::getCamundaProcdefId,camundaProcdefId)
                .eq(WfVarDef::getCamundaSequDefId, camundaSequDefId);
        if(ObjectUtil.isNotEmpty(isOrm)){
            if(isOrm){
                queryWrapper.eq(WfVarDef::getIsOrm, YesNo.YES.code);
            }else {
                queryWrapper.eq(WfVarDef::getIsOrm, YesNo.NO.code);
            }
        }
        List<WfVarDef> wfVarDefList = wfVarDefMapper.selectList(queryWrapper);
        List<WfVarDefVo> wfVarDefVoList = new ArrayList<>();
        log.debug("根据camunda流程定义和camunda序列流定义获取对应的变量信息：" + wfVarDefVoList);
        if(CollectionUtil.isNotEmpty(wfVarDefList)) {
            for (WfVarDef wfVarDef : wfVarDefList) {
                WfVarDefVo wfVarDefVo = dozerMapper.map(wfVarDef,WfVarDefVo.class);
                wfVarDefVoList.add(wfVarDefVo);
            }
        }
        return wfVarDefVoList;
    }

    @Override
    public void deleteByCamundaDefKey(String camundaDefKey) {
        log.debug("根据流程定义Key 删除所有的 变量 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfVarDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfVarDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfVarDefMapper.delete(delWrapper);
        log.debug("根据流程定义Key 删除所有的 变量 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 变量 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfVarDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfVarDef::getCamundaProcdefId,camundaDefId);
        int line = wfVarDefMapper.delete(delWrapper);
        log.debug("根据流程定义id 删除所有的 变量 信息(删除某个版本). 影响行数：{}", line);
    }

    @Override
    @Transactional
    public void delVarsByNodeId(Long nodeDefId) {
        LambdaQueryWrapper<WfVarDef> delVarDefWrapper = new LambdaQueryWrapper<>();
        delVarDefWrapper.eq(WfVarDef::getNodeDefId,nodeDefId);
        this.remove(delVarDefWrapper);
    }

    @Override
    @Transactional
    public void saveXml2WfVarsDef(List<WfVarDefDto> wfVarDefDtoList) {
        List<WfVarDef> wfVarDefList = new ArrayList<>(); //变量信息
        //节点变量
        if(CollectionUtil.isNotEmpty(wfVarDefDtoList)) {
            log.debug("saveNodeInfo-变量信息：{}", wfVarDefDtoList.toString());
            for (WfVarDefDto wfVarDefDto : wfVarDefDtoList) {
                WfVarDef wfVarDef = dozerMapper.map(wfVarDefDto,WfVarDef.class);
                wfVarDefList.add(wfVarDef);
            }
        }
        if(CollectionUtil.isNotEmpty(wfVarDefList)) {
            boolean savVarDefBoo = this.saveBatch(wfVarDefList);
            log.debug("保存 用户节点-变量 信息：{}", savVarDefBoo);
        }
    }

    @Override
    @Transactional
    public void delVarsByCmdNodeIdAndCmdProcdefId(String camundaNodeId, String camundaProcdefId) {
        if(ObjectUtil.isEmpty(camundaNodeId)) {
            throw new IncloudException("camunda节点定义id 不能为空！");
        }
        if(ObjectUtil.isEmpty(camundaProcdefId)) {
            throw new IncloudException("camunda流程定义id 不能为空！");
        }
        LambdaQueryWrapper<WfVarDef> delVarDefWrapper = new LambdaQueryWrapper<>();
        delVarDefWrapper.eq(WfVarDef::getCamundaNodeDefId,camundaNodeId);
        delVarDefWrapper.eq(WfVarDef::getCamundaProcdefId,camundaProcdefId);
        this.remove(delVarDefWrapper);
    }

    @Override
    @Transactional
    public void delVarsByCmdSeqIdAndCmdProcdefId(String camundaSeqId, String camundaProcdefId) {
        if(ObjectUtil.isEmpty(camundaSeqId)) {
            throw new IncloudException("camunda序列流定义id 不能为空！");
        }
        if(ObjectUtil.isEmpty(camundaProcdefId)) {
            throw new IncloudException("camunda流程定义id 不能为空！");
        }
        LambdaQueryWrapper<WfVarDef> delVarDefWrapper = new LambdaQueryWrapper<>();
        delVarDefWrapper.eq(WfVarDef::getCamundaSequDefId,camundaSeqId);
        delVarDefWrapper.eq(WfVarDef::getCamundaProcdefId,camundaProcdefId);
        this.remove(delVarDefWrapper);
    }
}
