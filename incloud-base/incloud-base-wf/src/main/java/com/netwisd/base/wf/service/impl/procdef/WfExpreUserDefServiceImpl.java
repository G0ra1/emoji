package com.netwisd.base.wf.service.impl.procdef;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfExpreUserParamDefDto;
import com.netwisd.base.wf.entity.WfExpreUserDef;
import com.netwisd.base.wf.entity.WfExpreUserParamDef;
import com.netwisd.base.wf.mapper.WfExpreUserDefMapper;
import com.netwisd.base.wf.service.procdef.WfExpreUserDefService;
import com.netwisd.base.wf.service.procdef.WfExpreUserParamDefService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfExpreUserDefDto;
import com.netwisd.base.wf.vo.WfExpreUserDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 人员表达式定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-14 11:48:32
 */
@Service
@Slf4j
public class WfExpreUserDefServiceImpl extends BatchServiceImpl<WfExpreUserDefMapper, WfExpreUserDef> implements WfExpreUserDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfExpreUserDefMapper wfExpreUserDefMapper;

    @Autowired
    private WfExpreUserParamDefService wfExpreUserParamDefService;

    /**
    * 单表简单查询操作
    * @param wfExpreUserDefDto
    * @return
    */
    @Override
    public Page list(WfExpreUserDefDto wfExpreUserDefDto) {
        WfExpreUserDef wfExpreUserDef = dozerMapper.map(wfExpreUserDefDto,WfExpreUserDef.class);
        LambdaQueryWrapper<WfExpreUserDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfExpreUserDef);
        Page<WfExpreUserDef> page = wfExpreUserDefMapper.selectPage(wfExpreUserDefDto.getPage(),queryWrapper);
        Page<WfExpreUserDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfExpreUserDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfExpreUserDefDto
    * @return
    */
    @Override
    public Page lists(WfExpreUserDefDto wfExpreUserDefDto) {
        Page<WfExpreUserDefVo> pageVo = wfExpreUserDefMapper.getPageList(wfExpreUserDefDto.getPage(),wfExpreUserDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfExpreUserDefVo get(Long id) {
        WfExpreUserDef wfExpreUserDef = super.getById(id);
        WfExpreUserDefVo wfExpreUserDefVo = null;
        if(wfExpreUserDef !=null){
            wfExpreUserDefVo = dozerMapper.map(wfExpreUserDef,WfExpreUserDefVo.class);
        }
        log.debug("查询成功");
        return wfExpreUserDefVo;
    }

    /**
    * 保存实体
    * @param wfExpreUserDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfExpreUserDefDto wfExpreUserDefDto) {
        WfExpreUserDef wfExpreUserDef = dozerMapper.map(wfExpreUserDefDto,WfExpreUserDef.class);
        boolean result = super.save(wfExpreUserDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfExpreUserDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfExpreUserDefDto wfExpreUserDefDto) {
        WfExpreUserDef wfExpreUserDef = dozerMapper.map(wfExpreUserDefDto,WfExpreUserDef.class);
        boolean result = super.updateById(wfExpreUserDef);
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
    public List<WfExpreUserDef> getExpreByProcDefIdAndNodeDefId(String camundaProcdefId, String camundaNodeDefId) {
        LambdaQueryWrapper<WfExpreUserDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfExpreUserDef::getCamundaProcdefId,camundaProcdefId)
                    .eq(WfExpreUserDef::getCamundaNodeDefId,camundaNodeDefId);
        List<WfExpreUserDef> list = this.list(queryWrapper);
        List<WfExpreUserDefVo> result = new ArrayList<>();
/*        if(ObjectUtil.isNotEmpty(list) && !list.isEmpty()){
            dozerMapper.map(list,result);
        }*/
        return list;
    }

    @Override
    public void deleteByCamundaDefKey(String camundaDefKey) {
        log.debug("根据流程定义Key 删除所有的 人员表达式 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfExpreUserDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfExpreUserDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfExpreUserDefMapper.delete(delWrapper);
        //删除对应的参数
        wfExpreUserParamDefService.deleteByCamundaDefKey(camundaDefKey);
        log.debug("根据流程定义Key 删除所有的 人员表达式 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 人员表达式 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfExpreUserDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfExpreUserDef::getCamundaProcdefId,camundaDefId);
        int line = wfExpreUserDefMapper.delete(delWrapper);
        wfExpreUserParamDefService.deleteByCamundaDefId(camundaDefId);
        log.debug("根据流程定义id 删除所有的 人员表达式 信息(删除某个版本). 影响行数：{}", line);
    }

    @Override
    @Transactional
    public void delExpreByNodeId(Long nodeDefId, String camundaProcdefId) {
        if(ObjectUtil.isEmpty(nodeDefId)) {
            throw new IncloudException("节点定义id 不能为空！");
        }
        //删除 表达式对应的参数
        LambdaQueryWrapper<WfExpreUserParamDef> delWfExpreUserParamDefWrapper = new LambdaQueryWrapper<>();
        delWfExpreUserParamDefWrapper.eq(WfExpreUserParamDef::getNodeDefId,nodeDefId);
        delWfExpreUserParamDefWrapper.eq(StringUtils.isNotBlank(camundaProcdefId),WfExpreUserParamDef::getCamundaProcdefId,camundaProcdefId);
        wfExpreUserParamDefService.remove(delWfExpreUserParamDefWrapper);
        //删除 表达式
        LambdaQueryWrapper<WfExpreUserDef> delWfExpreUserDefWrapper = new LambdaQueryWrapper<>();
        delWfExpreUserDefWrapper.eq(WfExpreUserDef::getNodeDefId,nodeDefId);
        delWfExpreUserDefWrapper.eq(StringUtils.isNotBlank(camundaProcdefId),WfExpreUserDef::getCamundaProcdefId,camundaProcdefId);
        this.remove(delWfExpreUserDefWrapper);
    }

    @Override
    @Transactional
    public void saveXml2WfExpreDef(List<WfExpreUserDefDto> wfExpreUserDefDtoList) {
        List<WfExpreUserDef> wfExpreUserDefList = new ArrayList<>(); //表达式信息
        List<WfExpreUserParamDef> wfExpreUserParamDefList = new ArrayList<>(); //表达式参数
        //节点表达式
        if(CollectionUtil.isNotEmpty(wfExpreUserDefDtoList)) {
            log.debug("saveNodeInfo-表达式信息：{}", wfExpreUserDefDtoList.toString());
            for (WfExpreUserDefDto wfExpreUserDefDto : wfExpreUserDefDtoList) {
                WfExpreUserDef wfExpreUserDef = dozerMapper.map(wfExpreUserDefDto, WfExpreUserDef.class);
                wfExpreUserDefList.add(wfExpreUserDef);
                List<WfExpreUserParamDefDto> wfExpreUserParamDefDtoList = wfExpreUserDefDto.getWfExpreUserParamDefDtoList();
                if (CollectionUtil.isNotEmpty(wfExpreUserParamDefDtoList)) {
                    for (WfExpreUserParamDefDto wfExpreUserParamDefDto : wfExpreUserParamDefDtoList) {
                        WfExpreUserParamDef wfExpreUserParamDef = dozerMapper.map(wfExpreUserParamDefDto, WfExpreUserParamDef.class);
                        wfExpreUserParamDefList.add(wfExpreUserParamDef);
                    }
                }
            }
        }
        if(CollectionUtil.isNotEmpty(wfExpreUserParamDefList)) {
            boolean savEventParamDefBoo = wfExpreUserParamDefService.saveBatch(wfExpreUserParamDefList);
            log.debug("保存 子流程节点-事件-参数 信息：{}", savEventParamDefBoo);
        }
        if(CollectionUtil.isNotEmpty(wfExpreUserDefList)) {
            boolean saveExpreUserDefBoo = this.saveBatch(wfExpreUserDefList);
            log.debug("保存 用户节点-表达式信息 信息：{}", saveExpreUserDefBoo);
        }
    }

    @Override
    @Transactional
    public void delExpreByCmdNodeIdAndCmdProcdefId(String camundaNodeId, String camundaProcdefId) {
        if(ObjectUtil.isEmpty(camundaNodeId)) {
            throw new IncloudException("camunda节点定义id 不能为空！");
        }
        if(ObjectUtil.isEmpty(camundaProcdefId)) {
            throw new IncloudException("camunda流程定义id 不能为空！");
        }
        //删除 表达式对应的参数
        LambdaQueryWrapper<WfExpreUserParamDef> delWfExpreUserParamDefWrapper = new LambdaQueryWrapper<>();
        delWfExpreUserParamDefWrapper.eq(WfExpreUserParamDef::getCamundaNodeDefId,camundaNodeId);
        delWfExpreUserParamDefWrapper.eq(WfExpreUserParamDef::getCamundaProcdefId,camundaProcdefId);
        wfExpreUserParamDefService.remove(delWfExpreUserParamDefWrapper);
        //删除 表达式
        LambdaQueryWrapper<WfExpreUserDef> delWfExpreUserDefWrapper = new LambdaQueryWrapper<>();
        delWfExpreUserDefWrapper.eq(WfExpreUserDef::getCamundaNodeDefId,camundaNodeId);
        delWfExpreUserDefWrapper.eq(WfExpreUserDef::getCamundaProcdefId,camundaProcdefId);
        this.remove(delWfExpreUserDefWrapper);
    }
}
