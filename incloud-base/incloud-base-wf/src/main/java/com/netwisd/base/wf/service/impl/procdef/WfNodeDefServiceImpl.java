package com.netwisd.base.wf.service.impl.procdef;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.constants.NodeTypeEnum;
import com.netwisd.base.wf.constants.YesNo;
import com.netwisd.base.wf.dto.*;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.mapper.WfNodeDefMapper;
import com.netwisd.base.wf.service.other.WfButtonDefService;
import com.netwisd.base.wf.service.other.WfFormDefService;
import com.netwisd.base.wf.service.other.WfFormFieldsDefService;
import com.netwisd.base.wf.service.procdef.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.constants.IdTypeEnum;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.vo.WfNodeDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 流程定义-节点定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 10:39:52
 */
@Service
@Slf4j
public class WfNodeDefServiceImpl extends BatchServiceImpl<WfNodeDefMapper, WfNodeDef> implements WfNodeDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfNodeDefMapper wfNodeDefMapper;

    @Autowired
    private WfEventDefService wfEventDefService;

    @Autowired
    private WfEventParamDefService wfEventParamDefService;

    @Autowired
    private WfExpreUserDefService wfExpreUserDefService;

    @Autowired
    private WfExpreUserParamDefService wfExpreUserParamDefService;

    @Autowired
    private WfFormFieldsDefService wfFormFieldsDefService;

    @Autowired
    private WfVarDefService wfVarDefService;

    @Autowired
    private WfButtonDefService wfButtonDefService;

    @Autowired
    private WfNodeDefService wfNodeDefService;

    @Autowired
    private WfSequDefService wfSequDefService;

    @Autowired
    private WfFormDefService wfFormDefService;

    @Autowired
    private WfProcDefRelService wfProcDefRelService;

    /**
    * 单表简单查询操作
    * @param wfNodeDefDto
    * @return
    */
    @Override
    public Page list(WfNodeDefDto wfNodeDefDto) {
        WfNodeDef wfNodeDef = dozerMapper.map(wfNodeDefDto,WfNodeDef.class);
        LambdaQueryWrapper<WfNodeDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfNodeDef);
        Page<WfNodeDef> page = wfNodeDefMapper.selectPage(wfNodeDefDto.getPage(),queryWrapper);
        Page<WfNodeDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfNodeDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfNodeDefDto
    * @return
    */
    @Override
    public Page lists(WfNodeDefDto wfNodeDefDto) {
        Page<WfNodeDefVo> pageVo = wfNodeDefMapper.getPageList(wfNodeDefDto.getPage(),wfNodeDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfNodeDefVo get(Long id) {
        WfNodeDef wfNodeDef = super.getById(id);
        WfNodeDefVo wfNodeDefVo = null;
        if(wfNodeDef !=null){
            wfNodeDefVo = dozerMapper.map(wfNodeDef,WfNodeDefVo.class);
        }
        log.debug("查询成功");
        return wfNodeDefVo;
    }

    /**
    * 保存实体
    * @param wfNodeDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfNodeDefDto wfNodeDefDto) {
        WfNodeDef wfNodeDef = dozerMapper.map(wfNodeDefDto,WfNodeDef.class);
        boolean result = super.save(wfNodeDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfNodeDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfNodeDefDto wfNodeDefDto) {
        WfNodeDef wfNodeDef = dozerMapper.map(wfNodeDefDto,WfNodeDef.class);
        boolean result = super.updateById(wfNodeDef);
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
    public WfNodeDefVo getNodeByProcDefIdAndNodeDefId(String camundaProcdefId, String camundaNodeDefId) {
        if(StringUtils.isBlank(camundaProcdefId)) {
            throw new IncloudException("camundaProcdefId 不能为空！");
        }
        if(StringUtils.isBlank(camundaNodeDefId)) {
            throw new IncloudException("camundaNodeDefId 不能为空！");
        }
        LambdaQueryWrapper<WfNodeDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotBlank(camundaProcdefId),WfNodeDef::getCamundaProcdefId,camundaProcdefId)
                .eq(StringUtils.isNotBlank(camundaNodeDefId),WfNodeDef::getCamundaNodeDefId,camundaNodeDefId);
        WfNodeDef wfNodeDef =  getOne(queryWrapper);
        WfNodeDefVo wfNodeDefVo = null;
        if(wfNodeDef !=null){
            wfNodeDefVo = dozerMapper.map(wfNodeDef,WfNodeDefVo.class);
        }
        return wfNodeDefVo;
    }

    @Override
    public WfNodeDefVo getEntity(String camundaProcDefId, String camundaNodeDefId) {
        LambdaQueryWrapper<WfNodeDef> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfNodeDef::getCamundaProcdefId,camundaProcDefId)
                .eq(WfNodeDef::getCamundaNodeDefId,camundaNodeDefId);
        WfNodeDef one = this.getOne(queryWrapper);
        WfNodeDefVo wfNodeDefVo = null;
        if(ObjectUtil.isNotEmpty(one)){
            wfNodeDefVo = dozerMapper.map(one, WfNodeDefVo.class);
            log.info("节点实例为：{}",JSONUtil.toJsonStr(wfNodeDefVo));
        }
        return wfNodeDefVo;
    }

    @Override
    public void saveXml2WfNodeDef(boolean isUpdate, List<WfNodeDefDto> wfNodeDefDtoList,Integer nodeType,String camundaSubProcessNodeDefId) {
        if(CollectionUtil.isNotEmpty(wfNodeDefDtoList)) {
            log.debug("解析xml 节点信息 信息：{}", wfNodeDefDtoList.toString());
            List<WfNodeDef> wfNodeDefList = new ArrayList<>();
            List<WfEventDef> gatWayEventDefDtoList = new ArrayList<>();
            List<WfEventParamDef> gatWayEventParamDefDtoList = new ArrayList<>();
            String camundaProcdefId = "";
            for (WfNodeDefDto wfNodeDefDto : wfNodeDefDtoList) {
                WfNodeDef WfNodeDef = dozerMapper.map(wfNodeDefDto,WfNodeDef.class);
                camundaProcdefId = WfNodeDef.getCamundaProcdefId();
                //node 信息
                wfNodeDefList.add(WfNodeDef);
                List<WfEventDefDto> eventDefDtoList = wfNodeDefDto.getWfEventDefDtoList();
                if(CollectionUtil.isNotEmpty(eventDefDtoList)) {
                    for (WfEventDefDto wfEventDefDto : eventDefDtoList) {
                        WfEventDef wfEventDef = dozerMapper.map(wfEventDefDto,WfEventDef.class);
                        List<WfEventParamDefDto> wfEventParamDefDtoList = wfEventDefDto.getWfEventParamDefDto();
                        if(CollectionUtil.isNotEmpty(wfEventParamDefDtoList)) {
                            for (WfEventParamDefDto wfEventParamDefDto : wfEventParamDefDtoList) {
                                WfEventParamDef wfEventParamDef = dozerMapper.map(wfEventParamDefDto,WfEventParamDef.class);
                                //事件参数信息
                                gatWayEventParamDefDtoList.add(wfEventParamDef);
                            }
                        }
                        //事件信息
                        gatWayEventDefDtoList.add(wfEventDef);
                    }
                }
            }
            if(isUpdate) {
                LambdaQueryWrapper<WfNodeDef> wfNodeDefWrapper = new LambdaQueryWrapper<>();
                wfNodeDefWrapper.eq(WfNodeDef::getCamundaProcdefId, camundaProcdefId)
                        .eq(WfNodeDef::getNodeType, nodeType);
                        //.eq(StringUtils.isNotBlank(camundaSubProcessNodeDefId),WfNodeDef::getCamundaParentNodeDefId,camundaSubProcessNodeDefId); //如果是子流程
                if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                    wfNodeDefWrapper.eq(WfNodeDef::getCamundaParentNodeDefId,camundaSubProcessNodeDefId);
                } else {
                    wfNodeDefWrapper.isNull(WfNodeDef::getCamundaParentNodeDefId);
                }
                Map<String, List<Serializable>> resultMap = this.saveOrUpdateOrDeleteResultBatch(wfNodeDefWrapper,wfNodeDefList);
                log.debug("修改 节点-事件 信息resultMap：{}", resultMap.toString());
                if(ObjectUtil.isNotEmpty(resultMap)){
                    //如果是删除的网关 直接把网关事件也删除
                    List<Serializable> delIds = resultMap.get(IdTypeEnum.DEL.getName());
                    if(CollectionUtil.isNotEmpty(delIds)) {
                        for (Serializable delId : delIds) {
                            wfEventDefService.delEventByNodeId((Long)delId,camundaProcdefId);
                        }
                    }

                    //如果是修改的网关，根据网关id 操作
                    List<Serializable> editIds = resultMap.get(IdTypeEnum.EDIT.getName());
                    if(CollectionUtil.isNotEmpty(editIds)) {
                        //先删除
                        for (Serializable editId : editIds) {
                            wfEventDefService.delEventByNodeId((Long)editId,camundaProcdefId);
                            //再保存 WfNodeDefDto 有且只有一个
                            List<WfNodeDefDto> editNodeDefDtoList = wfNodeDefDtoList.stream().filter(d->(d.getId() == editId)).collect(Collectors.toList());
                            if(CollectionUtil.isNotEmpty(editNodeDefDtoList)) {
                                wfEventDefService.saveXml2WfEventDef(editNodeDefDtoList.get(0).getWfEventDefDtoList());
                            }
                        }
                    }
                    //如果是增加的网关则直接增加
                    List<Serializable> addIds = resultMap.get(IdTypeEnum.ADD.getName());
                    if(CollectionUtil.isNotEmpty(addIds)) {
                        for (Serializable addId : addIds) {
                            List<WfNodeDefDto> editNodeDefDtoList = wfNodeDefDtoList.stream().filter(d->(d.getId() == addId)).collect(Collectors.toList());
                            if(CollectionUtil.isNotEmpty(editNodeDefDtoList)) {
                                wfEventDefService.saveXml2WfEventDef(editNodeDefDtoList.get(0).getWfEventDefDtoList());
                            }
                        }
                    }
                }
                log.debug("保存 节点 事件信息 执行成功。");
            } else {
                boolean saveNodeDefBoo = this.saveBatch(wfNodeDefList);
                log.debug("保存用户节点-网关信息：{}", saveNodeDefBoo);
                if(CollectionUtil.isNotEmpty(gatWayEventDefDtoList)) {
                    boolean saveButtonBoo = wfEventDefService.saveBatch(gatWayEventDefDtoList);
                    log.debug("保存 用户节点-事件 定义信息：{}", saveButtonBoo);
                }
                if(CollectionUtil.isNotEmpty(gatWayEventParamDefDtoList)) {
                    boolean saveButtonParamBoo = wfEventParamDefService.saveBatch(gatWayEventParamDefDtoList);
                    log.debug("保存 用户节点 事件定义-参数 信息信息：{}", saveButtonParamBoo);
                }

            }
        }
    }

    @Override
    public void saveXml2UserTaskDef(boolean isUpdate, List<WfNodeDefDto> userNodeDefDtoList,String camundaSubProcessNodeDefId) {
        if(CollectionUtil.isNotEmpty(userNodeDefDtoList)) {
            List<WfNodeDef> wfNodeDefList = new ArrayList<>(); //node 信息
            //List<WfNodeDetailDef> wfNodeDetailDefList = new ArrayList<>();//详细信息
            List<WfExpreUserDef> wfExpreUserDefList = new ArrayList<>(); //表达式信息
            List<WfExpreUserParamDef> wfExpreUserParamDefList = new ArrayList<>(); //表达式参数
            List<WfFormFieldsDef> wfFormFieldsDefList = new ArrayList<>(); //表单字段信息
            List<WfButtonDef> wfButtonDefList = new ArrayList<>(); //按钮信息
            List<WfVarDef> wfVarDefList = new ArrayList<>(); //变量信息
            List<WfEventDef> wfEventDefList = new ArrayList<>(); //事件
            List<WfEventParamDef> wfEventParamDefList = new ArrayList<>(); //事件参数
            String processDefinition = "";
            for (WfNodeDefDto wfNodeDefDto : userNodeDefDtoList) {
                // 节点
                WfNodeDef wfNodeDef = dozerMapper.map(wfNodeDefDto,WfNodeDef.class);
                wfNodeDefList.add(wfNodeDef);
                processDefinition = wfNodeDef.getCamundaProcdefId();
                //节点表达式
                List<WfExpreUserDefDto> wfExpreUserDefDtoList = wfNodeDefDto.getWfExpreUserDefDtoList();
                if(CollectionUtil.isNotEmpty(wfExpreUserDefDtoList)) {
                    log.debug("解析xml用户节点-表达式信息：{}", wfExpreUserDefDtoList.toString());
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
                //节点表单字段
//                List<WfFormFieldsDefDto> wfFormFieldsDefDtoList = wfNodeDefDto.getWfFormFieldsDefDtoList();
//                if(CollectionUtil.isNotEmpty(wfFormFieldsDefDtoList)) {
//                    log.debug("解析xml用户节点-表单字段信息：{}", wfFormFieldsDefDtoList.toString());
//                    for (WfFormFieldsDefDto wfFormVarDefDto : wfFormFieldsDefDtoList) {
//                        WfFormFieldsDef wfFormVarDef = dozerMapper.map(wfFormVarDefDto,WfFormFieldsDef.class);
//                        wfFormFieldsDefList.add(wfFormVarDef);
//                    }
//                }
                //节点按钮信息
                List<WfButtonDefDto> wfButtonDefDtoListDto = wfNodeDefDto.getWfButtonDefDtoListDto();
                if(CollectionUtil.isNotEmpty(wfButtonDefDtoListDto)) {
                    log.debug("解析xml用户节点-按钮信息：{}", wfButtonDefDtoListDto.toString());
                    for (WfButtonDefDto wfButtonDefDto : wfButtonDefDtoListDto) {
                        WfButtonDef wfButtonDef = dozerMapper.map(wfButtonDefDto,WfButtonDef.class);
                        wfButtonDefList.add(wfButtonDef);
                    }
                }
                //节点变量
                List<WfVarDefDto> wfVarDefDtoList = wfNodeDefDto.getWfVarDefDtoList();
                if(CollectionUtil.isNotEmpty(wfVarDefDtoList)) {
                    log.debug("解析xml用户节点-变量信息：{}", wfVarDefDtoList.toString());
                    for (WfVarDefDto wfVarDefDto : wfVarDefDtoList) {
                        WfVarDef wfVarDef = dozerMapper.map(wfVarDefDto,WfVarDef.class);
                        wfVarDefList.add(wfVarDef);
                    }
                }
                List<WfEventDefDto> wfEventDefDtoList = wfNodeDefDto.getWfEventDefDtoList();
                if(CollectionUtil.isNotEmpty(wfEventDefDtoList)) {
                    for (WfEventDefDto wfEventDefDto : wfEventDefDtoList) {
                        WfEventDef wfEventDef = dozerMapper.map(wfEventDefDto,WfEventDef.class);
                        wfEventDefList.add(wfEventDef);
                        List<WfEventParamDefDto> wfEventParamDefDto = wfEventDefDto.getWfEventParamDefDto();
                        if(CollectionUtil.isNotEmpty(wfEventParamDefDto)) {
                            for (WfEventParamDefDto eventParamDefDto : wfEventParamDefDto) {
                                WfEventParamDef wfEventParamDef = dozerMapper.map(eventParamDefDto,WfEventParamDef.class);
                                wfEventParamDefList.add(wfEventParamDef);
                            }
                        }
                    }
                }
            }

            if(CollectionUtil.isNotEmpty(wfNodeDefList)) {
                if(isUpdate) {
                    LambdaQueryWrapper<WfNodeDef> wfNodeDefWrapper = new LambdaQueryWrapper<>();
                    wfNodeDefWrapper.eq(WfNodeDef::getCamundaProcdefId, processDefinition)
                            //.eq(StringUtils.isNotBlank(camundaSubProcessNodeDefId),WfNodeDef::getCamundaParentNodeDefId,camundaSubProcessNodeDefId)
                            .in(WfNodeDef::getNodeType, NodeTypeEnum.USERTASK.code,NodeTypeEnum.MULTIINSTANCETASK.code);
                    if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                        wfNodeDefWrapper.eq(WfNodeDef::getCamundaParentNodeDefId,camundaSubProcessNodeDefId);
                    } else {
                        wfNodeDefWrapper.isNull(WfNodeDef::getCamundaParentNodeDefId);
                    }
                    Map<String, List<Serializable>> resultMap = super.saveOrUpdateOrDeleteResultBatch(wfNodeDefWrapper,wfNodeDefList);
                    log.debug("修改 UserTask 基础信息resultMap：{}", resultMap.toString());
                    if(ObjectUtil.isNotEmpty(resultMap)) {
                        //如果是删除 某个用户节点  需要删除 node详情、表达式、表达式参数、表单字段、按钮、变量、事件、事件参数 都删除
                        List<Serializable> delIds = resultMap.get(IdTypeEnum.DEL.getName());
                        if(CollectionUtil.isNotEmpty(delIds)) {
                            //删除详情 一次性删完
//                            LambdaQueryWrapper<WfNodeDetailDef> delNodeDetailDefWrapper = new LambdaQueryWrapper<>();
//                            delNodeDetailDefWrapper.in(WfNodeDetailDef::getNodeDefId,delIds);
//                            wfNodeDetailDefService.remove(delNodeDetailDefWrapper);
                            //删除表达式
                            LambdaQueryWrapper<WfExpreUserDef> delExpreUserDefWrapper = new LambdaQueryWrapper<>();
                            delExpreUserDefWrapper.in(WfExpreUserDef::getNodeDefId,delIds);
                            wfExpreUserDefService.remove(delExpreUserDefWrapper);
                            //删除表达式参数
                            LambdaQueryWrapper<WfExpreUserParamDef> delExpreUserParamDefWrapper = new LambdaQueryWrapper<>();
                            delExpreUserParamDefWrapper.in(WfExpreUserParamDef::getNodeDefId,delIds);
                            wfExpreUserParamDefService.remove(delExpreUserParamDefWrapper);
                            //删除表单字段
                            LambdaQueryWrapper<WfFormFieldsDef> delFormVarWrapper = new LambdaQueryWrapper<>();
                            delFormVarWrapper.in(WfFormFieldsDef::getNodeDefId,delIds);
                            wfFormFieldsDefService.remove(delFormVarWrapper);
                            //删除按钮
                            LambdaQueryWrapper<WfButtonDef> delButtonDefWrapper = new LambdaQueryWrapper<>();
                            delButtonDefWrapper.in(WfButtonDef::getNodeDefId,delIds);
                            wfButtonDefService.remove(delButtonDefWrapper);
                            //删除变量
                            LambdaQueryWrapper<WfVarDef> delVarDefWrapper = new LambdaQueryWrapper<>();
                            delVarDefWrapper.in(WfVarDef::getNodeDefId,delIds);
                            wfVarDefService.remove(delVarDefWrapper);
                            //删除事件
                            LambdaQueryWrapper<WfEventDef> delEventDefWrapper = new LambdaQueryWrapper<>();
                            delEventDefWrapper.in(WfEventDef::getNodeDefId,delIds);
                            wfEventDefService.remove(delEventDefWrapper);
                            //删除事件参数
                            LambdaQueryWrapper<WfEventParamDef> delEventParamDefWrapper = new LambdaQueryWrapper<>();
                            delEventParamDefWrapper.in(WfEventParamDef::getNodeDefId,delIds);
                            wfEventParamDefService.remove(delEventParamDefWrapper);
                        }
                        //如果是编辑 序列  需要把序列流对应的 变量也修改
                        List<Serializable> editIds = resultMap.get(IdTypeEnum.EDIT.getName());
                        if(CollectionUtil.isNotEmpty(editIds)) {
                            for (Serializable editId : editIds) {
                                //修改对应的详情
//                                List<WfNodeDetailDef> tempNodeDetailDefList = wfNodeDetailDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
//                                if(CollectionUtil.isNotEmpty(tempNodeDetailDefList)) {
//                                    LambdaQueryWrapper<WfNodeDetailDef> wfVarDefWrapper = new LambdaQueryWrapper<>();
//                                    wfVarDefWrapper.eq(WfNodeDetailDef::getNodeDefId, editId);
//                                    wfNodeDetailDefService.update(tempNodeDetailDefList.get(0),wfVarDefWrapper); //TODO 需测试
//                                }
                                //修改 表达式
                                List<WfExpreUserDef> tempWfExpreUserDefList = wfExpreUserDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempWfExpreUserDefList)) {
                                    LambdaQueryWrapper<WfExpreUserDef> wfExpreUserDefWrapper = new LambdaQueryWrapper<>();
                                    wfExpreUserDefWrapper.eq(WfExpreUserDef::getNodeDefId, editId);
                                    wfExpreUserDefService.saveOrUpdateOrDeleteBatch(wfExpreUserDefWrapper,tempWfExpreUserDefList);
                                }
                                //修改表达式参数
                                List<WfExpreUserParamDef> tempExpreUserParamDefList = wfExpreUserParamDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempExpreUserParamDefList)) {
                                    LambdaQueryWrapper<WfExpreUserParamDef> wfExpreUserParamDefWrapper = new LambdaQueryWrapper<>();
                                    wfExpreUserParamDefWrapper.eq(WfExpreUserParamDef::getNodeDefId, editId);
                                    wfExpreUserParamDefService.saveOrUpdateOrDeleteBatch(wfExpreUserParamDefWrapper,tempExpreUserParamDefList);
                                }
                                //修改表单字段
                                List<WfFormFieldsDef> tempFormVarDefList = wfFormFieldsDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempFormVarDefList)) {
                                    LambdaQueryWrapper<WfFormFieldsDef> wfFormVarDefWrapper = new LambdaQueryWrapper<>();
                                    wfFormVarDefWrapper.eq(WfFormFieldsDef::getNodeDefId, editId);
                                    wfFormFieldsDefService.saveOrUpdateOrDeleteBatch(wfFormVarDefWrapper,tempFormVarDefList);
                                }
                                //修改按钮
                                List<WfButtonDef> tempButtonDefList = wfButtonDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempButtonDefList)) {
                                    LambdaQueryWrapper<WfButtonDef> wfButtonDefWrapper = new LambdaQueryWrapper<>();
                                    wfButtonDefWrapper.eq(WfButtonDef::getNodeDefId, editId);
                                    wfButtonDefService.saveOrUpdateOrDeleteBatch(wfButtonDefWrapper,tempButtonDefList);
                                }
                                //修改变量
                                List<WfVarDef> tempVarDefList = wfVarDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempVarDefList)) {
                                    LambdaQueryWrapper<WfVarDef> wfVarDefWrapper = new LambdaQueryWrapper<>();
                                    wfVarDefWrapper.eq(WfVarDef::getNodeDefId, editId);
                                    wfVarDefService.saveOrUpdateOrDeleteBatch(wfVarDefWrapper,tempVarDefList);
                                }
                                //修改事件
                                List<WfEventDef> tempEventDefList = wfEventDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempEventDefList)) {
                                    LambdaQueryWrapper<WfEventDef> wfEventDefWrapper = new LambdaQueryWrapper<>();
                                    wfEventDefWrapper.eq(WfEventDef::getNodeDefId, editId);
                                    wfEventDefService.saveOrUpdateOrDeleteBatch(wfEventDefWrapper,tempEventDefList);
                                }
                                //修改事件参数
                                List<WfEventParamDef> tempEventParamDefList = wfEventParamDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempEventParamDefList)) {
                                    LambdaQueryWrapper<WfEventParamDef> wfEventParamDefWrapper = new LambdaQueryWrapper<>();
                                    wfEventParamDefWrapper.eq(WfEventParamDef::getNodeDefId, editId);
                                    wfEventParamDefService.saveOrUpdateOrDeleteBatch(wfEventParamDefWrapper,tempEventParamDefList);
                                }
                            }
                        }
                        //如果是增加的事件则直接增加
                        List<Serializable> addIds = resultMap.get(IdTypeEnum.ADD.getName());
                        if(CollectionUtil.isNotEmpty(addIds)) {
                            for (Serializable addId : addIds) {
                                //修改对应的详情
//                                List<WfNodeDetailDef> tempNodeDetailDefList = wfNodeDetailDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
//                                if(CollectionUtil.isNotEmpty(tempNodeDetailDefList)) {
//                                    wfNodeDetailDefService.saveBatch(tempNodeDetailDefList);
//                                }
                                //修改 表达式
                                List<WfExpreUserDef> tempWfExpreUserDefList = wfExpreUserDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempWfExpreUserDefList)) {
                                    wfExpreUserDefService.saveBatch(tempWfExpreUserDefList);
                                }
                                //修改表达式参数
                                List<WfExpreUserParamDef> tempExpreUserParamDefList = wfExpreUserParamDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempExpreUserParamDefList)) {
                                    wfExpreUserParamDefService.saveBatch(tempExpreUserParamDefList);
                                }
                                //修改表单字段
                                List<WfFormFieldsDef> tempFormVarDefList = wfFormFieldsDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempFormVarDefList)) {
                                    wfFormFieldsDefService.saveBatch(tempFormVarDefList);
                                }
                                //修改按钮
                                List<WfButtonDef> tempButtonDefList = wfButtonDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempButtonDefList)) {
                                    wfButtonDefService.saveBatch(tempButtonDefList);
                                }
                                //修改变量
                                List<WfVarDef> tempVarDefList = wfVarDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempVarDefList)) {
                                    wfVarDefService.saveBatch(tempVarDefList);
                                }
                                //修改事件
                                List<WfEventDef> tempEventDefList = wfEventDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempEventDefList)) {
                                    wfEventDefService.saveBatch(tempEventDefList);
                                }
                                //修改事件参数
                                List<WfEventParamDef> tempEventParamDefList = wfEventParamDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempEventParamDefList)) {
                                    wfEventParamDefService.saveBatch(tempEventParamDefList);
                                }
                            }
                        }
                    }
                } else {
                    boolean saveSequDefBoo = super.saveBatch(wfNodeDefList);
                    log.debug("保存 用户节点 基础信息：{}", saveSequDefBoo);

//                    if(CollectionUtil.isNotEmpty(wfNodeDetailDefList)) {
//                        wfNodeDetailDefService.saveBatch(wfNodeDetailDefList);
//                        log.debug("保存 用户节点 基础-详情 信息：{}", saveSequDefBoo);
//                    }
                    if(CollectionUtil.isNotEmpty(wfExpreUserDefList)) {
                        boolean saveExpreUserDefBoo = wfExpreUserDefService.saveBatch(wfExpreUserDefList);
                        log.debug("保存 用户节点-表达式信息 信息：{}", saveExpreUserDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfExpreUserParamDefList)) {
                        boolean saveExpreUserParamDefBoo = wfExpreUserParamDefService.saveBatch(wfExpreUserParamDefList);
                        log.debug("保存 用户节点-表达式-参数 信息：{}", saveExpreUserParamDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfFormFieldsDefList)) {
                        boolean savFormVarDefBoo = wfFormFieldsDefService.saveBatch(wfFormFieldsDefList);
                        log.debug("保存 用户节点-表单字段 信息：{}", savFormVarDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfButtonDefList)) {
                        boolean saveButtonDefBoo = wfButtonDefService.saveBatch(wfButtonDefList);
                        log.debug("保存 用户节点-按钮 信息：{}", saveButtonDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfVarDefList)) {
                        boolean savVarDefBoo = wfVarDefService.saveBatch(wfVarDefList);
                        log.debug("保存 用户节点-变量 信息：{}", savVarDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfEventDefList)) {
                        boolean savEventDefBoo = wfEventDefService.saveBatch(wfEventDefList);
                        log.debug("保存 用户节点-事件 信息：{}", savEventDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfEventParamDefList)) {
                        boolean savEventParamDefBoo = wfEventParamDefService.saveBatch(wfEventParamDefList);
                        log.debug("保存 用户节点-事件-参数 信息：{}", savEventParamDefBoo);
                    }
                }
            }
        }
    }

    @Override
    public void deleteByCamundaDefKey(String camundaDefKey) {
        log.debug("根据流程定义Key 删除所有的 节点定义 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfNodeDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfNodeDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfNodeDefMapper.delete(delWrapper);
        log.debug("根据流程定义Key 删除所有的 节点定义 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 节点定义 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfNodeDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfNodeDef::getCamundaProcdefId,camundaDefId);
        int line = wfNodeDefMapper.delete(delWrapper);
        log.debug("根据流程定义id 删除所有的 节点定义 信息(删除某个版本). 影响行数：{}", line);
    }

    @Override
    public void saveXml2SubProcessDef(boolean isUpdate, List<WfNodeDefDto> userNodeDefDtoList) {
        if(CollectionUtil.isNotEmpty(userNodeDefDtoList)) {
            List<WfNodeDef> wfNodeDefList = new ArrayList<>(); //用户节点信息
            List<WfFormDef> wfFormDefList = new ArrayList<>(); //表单信息
            //List<WfFormFieldsDef> wfFormVarDefList = new ArrayList<>(); //表单字段信息 子流程没有表单字段
            List<WfButtonDef> wfButtonDefList = new ArrayList<>(); //按钮信息
            List<WfEventDef> wfEventDefList = new ArrayList<>(); //事件
            List<WfEventParamDef> wfEventParamDefList = new ArrayList<>(); //事件参数
            List<WfExpreUserDef> wfExpreUserDefList = new ArrayList<>(); //表达式信息
            List<WfExpreUserParamDef> wfExpreUserParamDefList = new ArrayList<>(); //表达式参数
            List<WfVarDef> wfVarDefList = new ArrayList<>(); //变量信息
            String processDefinition = "";
            for (WfNodeDefDto wfNodeDefDto : userNodeDefDtoList) {
                //子流程 开始节点
                List<WfNodeDefDto> startNodeDefDtoList = wfNodeDefDto.getStartNodeDefDtoList();
                if(CollectionUtil.isNotEmpty(startNodeDefDtoList)) {
                    log.debug("saveXml2SubProcessDef 开始节点：{}", startNodeDefDtoList.toString());
                    this.saveXml2WfNodeDef(isUpdate,startNodeDefDtoList,NodeTypeEnum.STARTEVENT.code,wfNodeDefDto.getCamundaNodeDefId());
                }
                //子流程 结束节点
                List<WfNodeDefDto> endNodeDefDtoList = wfNodeDefDto.getEndNodeDefDtoList();
                if(CollectionUtil.isNotEmpty(endNodeDefDtoList)) {
                    log.debug("saveXml2SubProcessDef 结束节点：{}", endNodeDefDtoList.toString());
                    this.saveXml2WfNodeDef(isUpdate,endNodeDefDtoList,NodeTypeEnum.ENDEVENT.code,wfNodeDefDto.getCamundaNodeDefId());
                }
                //子流程 用户节点
                List<WfNodeDefDto> userTaskNodeDefDtoList = wfNodeDefDto.getUserTaskNodeDefDtoList();
                if(CollectionUtil.isNotEmpty(userTaskNodeDefDtoList)) {
                    log.debug("saveXml2SubProcessDef 用户节点：{}", userTaskNodeDefDtoList.toString());
                    this.saveXml2UserTaskDef(isUpdate,userTaskNodeDefDtoList,wfNodeDefDto.getCamundaNodeDefId());
                }
                //子流程 网关信息
                List<WfNodeDefDto> gatewayNodeDefDtoList = wfNodeDefDto.getGatewayNodeDefDtoList();
                if(CollectionUtil.isNotEmpty(gatewayNodeDefDtoList)) {
                    log.debug("saveXml2SubProcessDef 网关信息：{}", gatewayNodeDefDtoList.toString());
                    wfNodeDefService.saveXml2WfNodeDef(isUpdate,gatewayNodeDefDtoList,NodeTypeEnum.EXCLUSIVEGATEWAY.code,wfNodeDefDto.getCamundaNodeDefId());
                }
                //子流程 序列流信息
                List<WfSequDefDto> sequenceFlowDtoList = wfNodeDefDto.getSequenceFlowDtoList();
                if(CollectionUtil.isNotEmpty(sequenceFlowDtoList)) {
                    log.debug("saveXml2SubProcessDef 网关信息：{}", sequenceFlowDtoList.toString());
                    wfSequDefService.saveXml2WfSequDef(isUpdate,sequenceFlowDtoList,wfNodeDefDto.getCamundaNodeDefId());
                }
                //外嵌套 子流程信息
                List<WfNodeDefDto> callActivityNodeDefDtoList = wfNodeDefDto.getCallActivityNodeDefDtoList();
                saveXml2CallActivity(isUpdate, callActivityNodeDefDtoList,wfNodeDefDto.getCamundaNodeDefId());
                //子流程节点
                WfNodeDef wfNodeDef = dozerMapper.map(wfNodeDefDto,WfNodeDef.class);
                wfNodeDefList.add(wfNodeDef);
                processDefinition = wfNodeDef.getCamundaProcdefId();

                //子流程 表单信息
//                WfFormDefDto wfFormDefDto  = wfNodeDefDto.getWfFormDefDto();
//                if(null != wfFormDefDto) {
//                    log.debug("saveXml2SubProcessDef 子流程表单信息：{}", wfFormDefDto.toString());
//                    WfFormDef wfFormDef = dozerMapper.map(wfFormDefDto,WfFormDef.class);
//                    wfFormDefList.add(wfFormDef);
//                }

                //子流程 表单字段
//                List<WfFormFieldsDefDto> wfFormFieldsDefDtoList = wfNodeDefDto.getWfFormFieldsDefDtoList();
//                if(CollectionUtil.isNotEmpty(wfFormFieldsDefDtoList)) {
//                    for (WfFormFieldsDefDto wfFormVarDefDto : wfFormFieldsDefDtoList) {
//                        WfFormFieldsDef wfFormVarDef = dozerMapper.map(wfFormVarDefDto,WfFormFieldsDef.class);
//                        wfFormVarDefList.add(wfFormVarDef);
//                    }
//                }

                //子流程 事件 事件参数
                List<WfEventDefDto> wfEventDefDtoList = wfNodeDefDto.getWfEventDefDtoList();
                if(CollectionUtil.isNotEmpty(wfEventDefDtoList)) {
                    log.debug("saveXml2SubProcessDef 事件信息：{}", wfEventDefDtoList.toString());
                    for (WfEventDefDto wfEventDefDto : wfEventDefDtoList) {
                        WfEventDef wfEventDef = dozerMapper.map(wfEventDefDto,WfEventDef.class);
                        wfEventDefList.add(wfEventDef);
                        List<WfEventParamDefDto> wfEventParamDefDto = wfEventDefDto.getWfEventParamDefDto();
                        if(CollectionUtil.isNotEmpty(wfEventParamDefDto)) {
                            for (WfEventParamDefDto eventParamDefDto : wfEventParamDefDto) {
                                WfEventParamDef wfEventParamDef = dozerMapper.map(eventParamDefDto,WfEventParamDef.class);
                                wfEventParamDefList.add(wfEventParamDef);
                            }
                        }
                    }
                }

                //节点表达式
                List<WfExpreUserDefDto> wfExpreUserDefDtoList = wfNodeDefDto.getWfExpreUserDefDtoList();
                if(CollectionUtil.isNotEmpty(wfExpreUserDefDtoList)) {
                    log.debug("解析xml用户节点-表达式信息：{}", wfExpreUserDefDtoList.toString());
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

                //节点变量
                List<WfVarDefDto> wfVarDefDtoList = wfNodeDefDto.getWfVarDefDtoList();
                if(CollectionUtil.isNotEmpty(wfVarDefDtoList)) {
                    log.debug("解析xml用户节点-变量信息：{}", wfVarDefDtoList.toString());
                    for (WfVarDefDto wfVarDefDto : wfVarDefDtoList) {
                        WfVarDef wfVarDef = dozerMapper.map(wfVarDefDto,WfVarDef.class);
                        wfVarDefList.add(wfVarDef);
                    }
                }
            }
            if(CollectionUtil.isNotEmpty(wfNodeDefList)) {
                if(isUpdate) {
                    LambdaQueryWrapper<WfNodeDef> wfNodeDefWrapper = new LambdaQueryWrapper<>();
                    wfNodeDefWrapper.eq(WfNodeDef::getCamundaProcdefId, processDefinition)
                            .in(WfNodeDef::getNodeType, NodeTypeEnum.SUBPROCESS.code,NodeTypeEnum.MULTIINSTANCESUBPROCESS.code);//查询子流程信息 子流程多实例信息
                    Map<String, List<Serializable>> resultMap = super.saveOrUpdateOrDeleteResultBatch(wfNodeDefWrapper,wfNodeDefList);
                    log.debug("修改 UserTask 基础信息resultMap：{}", resultMap.toString());
                    if(ObjectUtil.isNotEmpty(resultMap)) {
                        //如果是删除
                        List<Serializable> delIds = resultMap.get(IdTypeEnum.DEL.getName());
                        if(CollectionUtil.isNotEmpty(delIds)) {
                            //删除表单信息
                            wfFormDefService.deleteByNodeDefId(delIds);
                            //删除表单字段
//                            LambdaQueryWrapper<WfFormFieldsDef> delFormVarWrapper = new LambdaQueryWrapper<>();
//                            delFormVarWrapper.in(WfFormFieldsDef::getNodeDefId,delIds);
//                            wfFormFieldsDefService.remove(delFormVarWrapper);
                            //删除按钮
                            LambdaQueryWrapper<WfButtonDef> delButtonDefWrapper = new LambdaQueryWrapper<>();
                            delButtonDefWrapper.in(WfButtonDef::getNodeDefId,delIds);
                            wfButtonDefService.remove(delButtonDefWrapper);
                            //删除事件
                            LambdaQueryWrapper<WfEventDef> delEventDefWrapper = new LambdaQueryWrapper<>();
                            delEventDefWrapper.in(WfEventDef::getNodeDefId,delIds);
                            wfEventDefService.remove(delEventDefWrapper);
                            //删除事件参数
                            LambdaQueryWrapper<WfEventParamDef> delEventParamDefWrapper = new LambdaQueryWrapper<>();
                            delEventParamDefWrapper.in(WfEventParamDef::getNodeDefId,delIds);
                            wfEventParamDefService.remove(delEventParamDefWrapper);
                            //删除表达式
                            LambdaQueryWrapper<WfExpreUserDef> delExpreUserDefWrapper = new LambdaQueryWrapper<>();
                            delExpreUserDefWrapper.in(WfExpreUserDef::getNodeDefId,delIds);
                            wfExpreUserDefService.remove(delExpreUserDefWrapper);
                            //删除表达式参数
                            LambdaQueryWrapper<WfExpreUserParamDef> delExpreUserParamDefWrapper = new LambdaQueryWrapper<>();
                            delExpreUserParamDefWrapper.in(WfExpreUserParamDef::getNodeDefId,delIds);
                            wfExpreUserParamDefService.remove(delExpreUserParamDefWrapper);
                            //删除变量
                            LambdaQueryWrapper<WfVarDef> delVarDefWrapper = new LambdaQueryWrapper<>();
                            delVarDefWrapper.in(WfVarDef::getNodeDefId,delIds);
                            wfVarDefService.remove(delVarDefWrapper);
                        }
                        //如果是编辑
                        List<Serializable> editIds = resultMap.get(IdTypeEnum.EDIT.getName());
                        if(CollectionUtil.isNotEmpty(editIds)) {
                            for (Serializable editId : editIds) {
                                //删除表单信息 重新保存

                                List<WfFormDef> tempWfFormDefList = wfFormDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempWfFormDefList)) {
                                    wfFormDefService.deleteByNodeDefId(Arrays.asList(editId));
                                    wfFormDefService.updateById(tempWfFormDefList.get(0));
                                }
                                //修改表单字段
//                                List<WfFormFieldsDef> tempFormVarDefList = wfFormVarDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
//                                if(CollectionUtil.isNotEmpty(tempFormVarDefList)) {
//                                    LambdaQueryWrapper<WfFormFieldsDef> wfFormVarDefWrapper = new LambdaQueryWrapper<>();
//                                    wfFormVarDefWrapper.eq(WfFormFieldsDef::getNodeDefId, editId);
//                                    wfFormFieldsDefService.saveOrUpdateOrDeleteBatch(wfFormVarDefWrapper,tempFormVarDefList);
//                                }
                                //修改按钮
                                List<WfButtonDef> tempButtonDefList = wfButtonDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempButtonDefList)) {
                                    LambdaQueryWrapper<WfButtonDef> wfButtonDefWrapper = new LambdaQueryWrapper<>();
                                    wfButtonDefWrapper.eq(WfButtonDef::getNodeDefId, editId);
                                    wfButtonDefService.saveOrUpdateOrDeleteBatch(wfButtonDefWrapper,tempButtonDefList);
                                }
                                //修改事件
                                List<WfEventDef> tempEventDefList = wfEventDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempEventDefList)) {
                                    LambdaQueryWrapper<WfEventDef> wfEventDefWrapper = new LambdaQueryWrapper<>();
                                    wfEventDefWrapper.eq(WfEventDef::getNodeDefId, editId);
                                    wfEventDefService.saveOrUpdateOrDeleteBatch(wfEventDefWrapper,tempEventDefList);
                                }
                                //修改事件参数
                                List<WfEventParamDef> tempEventParamDefList = wfEventParamDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempEventParamDefList)) {
                                    LambdaQueryWrapper<WfEventParamDef> wfEventParamDefWrapper = new LambdaQueryWrapper<>();
                                    wfEventParamDefWrapper.eq(WfEventParamDef::getNodeDefId, editId);
                                    wfEventParamDefService.saveOrUpdateOrDeleteBatch(wfEventParamDefWrapper,tempEventParamDefList);
                                }
                                //修改 表达式
                                List<WfExpreUserDef> tempWfExpreUserDefList = wfExpreUserDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempWfExpreUserDefList)) {
                                    LambdaQueryWrapper<WfExpreUserDef> wfExpreUserDefWrapper = new LambdaQueryWrapper<>();
                                    wfExpreUserDefWrapper.eq(WfExpreUserDef::getNodeDefId, editId);
                                    wfExpreUserDefService.saveOrUpdateOrDeleteBatch(wfExpreUserDefWrapper,tempWfExpreUserDefList);
                                }
                                //修改表达式参数
                                List<WfExpreUserParamDef> tempExpreUserParamDefList = wfExpreUserParamDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempExpreUserParamDefList)) {
                                    LambdaQueryWrapper<WfExpreUserParamDef> wfExpreUserParamDefWrapper = new LambdaQueryWrapper<>();
                                    wfExpreUserParamDefWrapper.eq(WfExpreUserParamDef::getNodeDefId, editId);
                                    wfExpreUserParamDefService.saveOrUpdateOrDeleteBatch(wfExpreUserParamDefWrapper,tempExpreUserParamDefList);
                                }
                                //修改变量
                                List<WfVarDef> tempVarDefList = wfVarDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempVarDefList)) {
                                    LambdaQueryWrapper<WfVarDef> wfVarDefWrapper = new LambdaQueryWrapper<>();
                                    wfVarDefWrapper.eq(WfVarDef::getNodeDefId, editId);
                                    wfVarDefService.saveOrUpdateOrDeleteBatch(wfVarDefWrapper,tempVarDefList);
                                }
                            }
                        }
                        //如果是增加
                        List<Serializable> addIds = resultMap.get(IdTypeEnum.ADD.getName());
                        if(CollectionUtil.isNotEmpty(addIds)) {
                            for (Serializable addId : addIds) {
                                //直接保存表单
                                List<WfFormDef> tempWfFormDefList = wfFormDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempWfFormDefList)) {
                                    wfFormDefService.saveBatch(tempWfFormDefList);
                                }
                                //修改表单字段
//                                List<WfFormFieldsDef> tempFormVarDefList = wfFormVarDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
//                                if(CollectionUtil.isNotEmpty(tempFormVarDefList)) {
//                                    wfFormFieldsDefService.saveBatch(tempFormVarDefList);
//                                }
                                //保存按钮
                                List<WfButtonDef> tempButtonDefList = wfButtonDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempButtonDefList)) {
                                    wfButtonDefService.saveBatch(tempButtonDefList);
                                }
                                //保存事件
                                List<WfEventDef> tempEventDefList = wfEventDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempEventDefList)) {
                                    wfEventDefService.saveBatch(tempEventDefList);
                                }
                                //保存事件参数
                                List<WfEventParamDef> tempEventParamDefList = wfEventParamDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempEventParamDefList)) {
                                    wfEventParamDefService.saveBatch(tempEventParamDefList);
                                }
                                //修改 表达式
                                List<WfExpreUserDef> tempWfExpreUserDefList = wfExpreUserDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempWfExpreUserDefList)) {
                                    wfExpreUserDefService.saveBatch(tempWfExpreUserDefList);
                                }
                                //修改表达式参数
                                List<WfExpreUserParamDef> tempExpreUserParamDefList = wfExpreUserParamDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempExpreUserParamDefList)) {
                                    wfExpreUserParamDefService.saveBatch(tempExpreUserParamDefList);
                                }
                                //修改变量
                                List<WfVarDef> tempVarDefList = wfVarDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempVarDefList)) {
                                    wfVarDefService.saveBatch(tempVarDefList);
                                }
                            }
                        }
                    }
                } else {
                    boolean saveSequDefBoo = super.saveBatch(wfNodeDefList);
                    log.debug("保存 子流程节点 基础信息：{}", saveSequDefBoo);

                    if(CollectionUtil.isNotEmpty(wfFormDefList)) {
                        boolean formDefBoo = wfFormDefService.saveBatch(wfFormDefList);
                        log.debug("保存 子流程节点  表单信息：{}", formDefBoo);
                    }
//                    if(CollectionUtil.isNotEmpty(wfFormVarDefList)) {
//                        boolean savFormVarDefBoo = wfFormFieldsDefService.saveBatch(wfFormVarDefList);
//                        log.debug("保存 子流程节点-表单字段 信息：{}", savFormVarDefBoo);
//                    }
                    if(CollectionUtil.isNotEmpty(wfButtonDefList)) {
                        boolean saveButtonDefBoo = wfButtonDefService.saveBatch(wfButtonDefList);
                        log.debug("保存 子流程节点-按钮 信息：{}", saveButtonDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfEventDefList)) {
                        boolean savEventDefBoo = wfEventDefService.saveBatch(wfEventDefList);
                        log.debug("保存 子流程节点-事件 信息：{}", savEventDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfEventParamDefList)) {
                        boolean savEventParamDefBoo = wfEventParamDefService.saveBatch(wfEventParamDefList);
                        log.debug("保存 子流程节点-事件-参数 信息：{}", savEventParamDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfExpreUserDefList)) {
                        boolean saveExpreUserDefBoo = wfExpreUserDefService.saveBatch(wfExpreUserDefList);
                        log.debug("保存 用户节点-表达式信息 信息：{}", saveExpreUserDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfExpreUserParamDefList)) {
                        boolean saveExpreUserParamDefBoo = wfExpreUserParamDefService.saveBatch(wfExpreUserParamDefList);
                        log.debug("保存 用户节点-表达式-参数 信息：{}", saveExpreUserParamDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfVarDefList)) {
                        boolean savVarDefBoo = wfVarDefService.saveBatch(wfVarDefList);
                        log.debug("保存 用户节点-变量 信息：{}", savVarDefBoo);
                    }
                }
            }
        }
    }

    @Override
    public void saveXml2CallActivity(boolean isUpdate, List<WfNodeDefDto> userNodeDefDtoList,String camundaSubProcessNodeDefId) {
        if(CollectionUtil.isNotEmpty(userNodeDefDtoList)) {
            List<WfNodeDef> wfNodeDefList = new ArrayList<>(); //用户节点信息
            List<WfEventDef> wfEventDefList = new ArrayList<>(); //事件
            List<WfEventParamDef> wfEventParamDefList = new ArrayList<>(); //事件参数
            List<WfProcDefRel> wfProcDefRelList = new ArrayList<>(); //与子流程的关系信息
            List<WfExpreUserDef> wfExpreUserDefList = new ArrayList<>(); //表达式信息
            List<WfExpreUserParamDef> wfExpreUserParamDefList = new ArrayList<>(); //表达式参数
            List<WfVarDef> wfVarDefList = new ArrayList<>(); //变量信息
            String processDefinition = "";
            for (WfNodeDefDto wfNodeDefDto : userNodeDefDtoList) {
                //子流程节点
                WfNodeDef wfNodeDef = dozerMapper.map(wfNodeDefDto,WfNodeDef.class);
                wfNodeDefList.add(wfNodeDef);
                processDefinition = wfNodeDef.getCamundaProcdefId();

                //每个子流程对应一条关系记录
                WfProcDefRelDto wfProcDefRelDto = wfNodeDefDto.getWfProcDefRel();
                WfProcDefRel wfProcDefRel = dozerMapper.map(wfProcDefRelDto,WfProcDefRel.class);
                wfProcDefRelList.add(wfProcDefRel);

                //子流程 事件 事件参数
                List<WfEventDefDto> wfEventDefDtoList = wfNodeDefDto.getWfEventDefDtoList();
                if(CollectionUtil.isNotEmpty(wfEventDefDtoList)) {
                    log.debug("saveXml2SubProcessDef 事件信息：{}", wfEventDefDtoList.toString());
                    for (WfEventDefDto wfEventDefDto : wfEventDefDtoList) {
                        WfEventDef wfEventDef = dozerMapper.map(wfEventDefDto,WfEventDef.class);
                        wfEventDefList.add(wfEventDef);
                        List<WfEventParamDefDto> wfEventParamDefDto = wfEventDefDto.getWfEventParamDefDto();
                        if(CollectionUtil.isNotEmpty(wfEventParamDefDto)) {
                            for (WfEventParamDefDto eventParamDefDto : wfEventParamDefDto) {
                                WfEventParamDef wfEventParamDef = dozerMapper.map(eventParamDefDto,WfEventParamDef.class);
                                wfEventParamDefList.add(wfEventParamDef);
                            }
                        }
                    }
                }

                //节点表达式
                List<WfExpreUserDefDto> wfExpreUserDefDtoList = wfNodeDefDto.getWfExpreUserDefDtoList();
                if(CollectionUtil.isNotEmpty(wfExpreUserDefDtoList)) {
                    log.debug("解析xml用户节点-表达式信息：{}", wfExpreUserDefDtoList.toString());
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

                //节点变量
                List<WfVarDefDto> wfVarDefDtoList = wfNodeDefDto.getWfVarDefDtoList();
                if(CollectionUtil.isNotEmpty(wfVarDefDtoList)) {
                    log.debug("解析xml用户节点-变量信息：{}", wfVarDefDtoList.toString());
                    for (WfVarDefDto wfVarDefDto : wfVarDefDtoList) {
                        WfVarDef wfVarDef = dozerMapper.map(wfVarDefDto,WfVarDef.class);
                        wfVarDefList.add(wfVarDef);
                    }
                }

            }
            if(CollectionUtil.isNotEmpty(wfNodeDefList)) {
                if(isUpdate) {
                    LambdaQueryWrapper<WfNodeDef> wfNodeDefWrapper = new LambdaQueryWrapper<>();
                    wfNodeDefWrapper.eq(WfNodeDef::getCamundaProcdefId, processDefinition)
                            .in(WfNodeDef::getNodeType, NodeTypeEnum.CALLACTIVITY.code,NodeTypeEnum.MULTIINSTANCECALLACTIVITYS.code);//查询子流程信息 子流程多实例信息
                    if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                        wfNodeDefWrapper.eq(WfNodeDef::getCamundaParentNodeDefId,camundaSubProcessNodeDefId);
                    } else {
                        wfNodeDefWrapper.isNull(WfNodeDef::getCamundaParentNodeDefId);
                    }
                    Map<String, List<Serializable>> resultMap = super.saveOrUpdateOrDeleteResultBatch(wfNodeDefWrapper,wfNodeDefList);
                    log.debug("修改 UserTask 基础信息resultMap：{}", resultMap.toString());
                    if(ObjectUtil.isNotEmpty(resultMap)) {
                        //如果是删除
                        List<Serializable> delIds = resultMap.get(IdTypeEnum.DEL.getName());
                        if(CollectionUtil.isNotEmpty(delIds)) {
                            //删除关系信息
                            wfProcDefRelService.deleteByNodeDefId(delIds);
                            //删除事件
                            LambdaQueryWrapper<WfEventDef> delEventDefWrapper = new LambdaQueryWrapper<>();
                            delEventDefWrapper.in(WfEventDef::getNodeDefId,delIds);
                            wfEventDefService.remove(delEventDefWrapper);
                            //删除事件参数
                            LambdaQueryWrapper<WfEventParamDef> delEventParamDefWrapper = new LambdaQueryWrapper<>();
                            delEventParamDefWrapper.in(WfEventParamDef::getNodeDefId,delIds);
                            wfEventParamDefService.remove(delEventParamDefWrapper);
                            //删除表达式
                            LambdaQueryWrapper<WfExpreUserDef> delExpreUserDefWrapper = new LambdaQueryWrapper<>();
                            delExpreUserDefWrapper.in(WfExpreUserDef::getNodeDefId,delIds);
                            wfExpreUserDefService.remove(delExpreUserDefWrapper);
                            //删除表达式参数
                            LambdaQueryWrapper<WfExpreUserParamDef> delExpreUserParamDefWrapper = new LambdaQueryWrapper<>();
                            delExpreUserParamDefWrapper.in(WfExpreUserParamDef::getNodeDefId,delIds);
                            wfExpreUserParamDefService.remove(delExpreUserParamDefWrapper);
                            //删除变量
                            LambdaQueryWrapper<WfVarDef> delVarDefWrapper = new LambdaQueryWrapper<>();
                            delVarDefWrapper.in(WfVarDef::getNodeDefId,delIds);
                            wfVarDefService.remove(delVarDefWrapper);

                        }
                        //如果是编辑
                        List<Serializable> editIds = resultMap.get(IdTypeEnum.EDIT.getName());
                        if(CollectionUtil.isNotEmpty(editIds)) {
                            for (Serializable editId : editIds) {
                                //修改 关系 信息
                                List<WfProcDefRel> tempWfProcDefRelDtoList = wfProcDefRelList.stream().filter(d->(d.getMainNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempWfProcDefRelDtoList)) {
                                    wfProcDefRelService.updateById(tempWfProcDefRelDtoList.get(0));
                                }

                                //修改事件
                                List<WfEventDef> tempEventDefList = wfEventDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempEventDefList)) {
                                    LambdaQueryWrapper<WfEventDef> wfEventDefWrapper = new LambdaQueryWrapper<>();
                                    wfEventDefWrapper.eq(WfEventDef::getNodeDefId, editId);
                                    wfEventDefService.saveOrUpdateOrDeleteBatch(wfEventDefWrapper,tempEventDefList);
                                }
                                //修改事件参数
                                List<WfEventParamDef> tempEventParamDefList = wfEventParamDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempEventParamDefList)) {
                                    LambdaQueryWrapper<WfEventParamDef> wfEventParamDefWrapper = new LambdaQueryWrapper<>();
                                    wfEventParamDefWrapper.eq(WfEventParamDef::getNodeDefId, editId);
                                    wfEventParamDefService.saveOrUpdateOrDeleteBatch(wfEventParamDefWrapper,tempEventParamDefList);
                                }

                                //修改 表达式
                                List<WfExpreUserDef> tempWfExpreUserDefList = wfExpreUserDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempWfExpreUserDefList)) {
                                    LambdaQueryWrapper<WfExpreUserDef> wfExpreUserDefWrapper = new LambdaQueryWrapper<>();
                                    wfExpreUserDefWrapper.eq(WfExpreUserDef::getNodeDefId, editId);
                                    wfExpreUserDefService.saveOrUpdateOrDeleteBatch(wfExpreUserDefWrapper,tempWfExpreUserDefList);
                                }
                                //修改表达式参数
                                List<WfExpreUserParamDef> tempExpreUserParamDefList = wfExpreUserParamDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempExpreUserParamDefList)) {
                                    LambdaQueryWrapper<WfExpreUserParamDef> wfExpreUserParamDefWrapper = new LambdaQueryWrapper<>();
                                    wfExpreUserParamDefWrapper.eq(WfExpreUserParamDef::getNodeDefId, editId);
                                    wfExpreUserParamDefService.saveOrUpdateOrDeleteBatch(wfExpreUserParamDefWrapper,tempExpreUserParamDefList);
                                }
                                //修改变量
                                List<WfVarDef> tempVarDefList = wfVarDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)editId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempVarDefList)) {
                                    LambdaQueryWrapper<WfVarDef> wfVarDefWrapper = new LambdaQueryWrapper<>();
                                    wfVarDefWrapper.eq(WfVarDef::getNodeDefId, editId);
                                    wfVarDefService.saveOrUpdateOrDeleteBatch(wfVarDefWrapper,tempVarDefList);
                                }
                            }
                        }
                        //如果是增加
                        List<Serializable> addIds = resultMap.get(IdTypeEnum.ADD.getName());
                        if(CollectionUtil.isNotEmpty(addIds)) {
                            for (Serializable addId : addIds) {
                                //保存关系
                                List<WfProcDefRel> tempWfProcDefRelDtoList = wfProcDefRelList.stream().filter(d->(d.getMainNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempWfProcDefRelDtoList)) {
                                    wfProcDefRelService.save(tempWfProcDefRelDtoList.get(0));
                                }
                                //保存事件
                                List<WfEventDef> tempEventDefList = wfEventDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempEventDefList)) {
                                    wfEventDefService.saveBatch(tempEventDefList);
                                }
                                //保存事件参数
                                List<WfEventParamDef> tempEventParamDefList = wfEventParamDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempEventParamDefList)) {
                                    wfEventParamDefService.saveBatch(tempEventParamDefList);
                                }
                                //保存 表达式
                                List<WfExpreUserDef> tempWfExpreUserDefList = wfExpreUserDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempWfExpreUserDefList)) {
                                    wfExpreUserDefService.saveBatch(tempWfExpreUserDefList);
                                }
                                //保存 表达式参数
                                List<WfExpreUserParamDef> tempExpreUserParamDefList = wfExpreUserParamDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempExpreUserParamDefList)) {
                                    wfExpreUserParamDefService.saveBatch(tempExpreUserParamDefList);
                                }
                                //保存 变量
                                List<WfVarDef> tempVarDefList = wfVarDefList.stream().filter(d->(d.getNodeDefId().longValue() == ((Long)addId).longValue())).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(tempVarDefList)) {
                                    wfVarDefService.saveBatch(tempVarDefList);
                                }

                            }
                        }
                    }
                } else {
                    boolean saveSequDefBoo = super.saveBatch(wfNodeDefList);
                    log.debug("保存 子流程节点 基础信息：{}", saveSequDefBoo);

                    if(CollectionUtil.isNotEmpty(wfProcDefRelList)) {
                        boolean savProcDefRelBoo = wfProcDefRelService.saveBatch(wfProcDefRelList);
                        log.debug("保存 子流程节点-callActivity 信息：{}", savProcDefRelBoo);
                    }

                    if(CollectionUtil.isNotEmpty(wfEventDefList)) {
                        boolean savEventDefBoo = wfEventDefService.saveBatch(wfEventDefList);
                        log.debug("保存 子流程节点-事件 信息：{}", savEventDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfEventParamDefList)) {
                        boolean savEventParamDefBoo = wfEventParamDefService.saveBatch(wfEventParamDefList);
                        log.debug("保存 子流程节点-事件-参数 信息：{}", savEventParamDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfExpreUserDefList)) {
                        boolean saveExpreUserDefBoo = wfExpreUserDefService.saveBatch(wfExpreUserDefList);
                        log.debug("保存 用户节点-表达式信息 信息：{}", saveExpreUserDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfExpreUserParamDefList)) {
                        boolean saveExpreUserParamDefBoo = wfExpreUserParamDefService.saveBatch(wfExpreUserParamDefList);
                        log.debug("保存 用户节点-表达式-参数 信息：{}", saveExpreUserParamDefBoo);
                    }
                    if(CollectionUtil.isNotEmpty(wfVarDefList)) {
                        boolean savVarDefBoo = wfVarDefService.saveBatch(wfVarDefList);
                        log.debug("保存 用户节点-变量 信息：{}", savVarDefBoo);
                    }
                }
            }
        }
    }

    @Override
    @Transactional
    public void saveNodeInfo(List<WfNodeDefDto> nodeDefDtos, boolean isAdd) {
        if(CollectionUtil.isNotEmpty(nodeDefDtos)) {
            if(isAdd) { //如果是新增
                this.newSaveTask(nodeDefDtos);
            } else { //如果是编辑
                this.editSaveTask(nodeDefDtos);
            }
        }
    }

    @Override
    public WfNodeDef geNodeByCmdProcIdAndCmdNodeId(String camundaProcdefId, String camundaNodeId) {
        LambdaQueryWrapper<WfNodeDef> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(WfNodeDef::getCamundaProcdefId, camundaProcdefId);
        queryWrapper.eq(WfNodeDef::getCamundaNodeDefId,camundaNodeId);
        return wfNodeDefMapper.selectOne(queryWrapper);
    }

    /**
     * 保存新建的节点信息
     * @param nodeDefDtos 节点信息
     */
    public void newSaveTask(List<WfNodeDefDto> nodeDefDtos) {
        List<WfNodeDefDto> wfNodeDefDtoList = new ArrayList<>(); //节点
        List<WfEventDefDto> wfEventDefDtoList = new ArrayList<>(); //事件
        List<WfExpreUserDefDto> wfExpreUserDefDtoList = new ArrayList<>(); //表达式
        List<WfVarDefDto> wfVarDefDtoList = new ArrayList<>(); //变量
        List<WfButtonDefDto> wfButtonDefDtoList = new ArrayList<>(); //按钮
        List<WfFormDefDto> wfFormDefDtoList = new ArrayList<>(); //表单
        List<WfProcDefRel> wfProcDefRelList = new ArrayList<>(); //与子流程的关系信息
        for (WfNodeDefDto wfNodeDefDto : nodeDefDtos) {
            wfNodeDefDtoList.add(wfNodeDefDto);
            //事件
            List<WfEventDefDto> _wfEventDefDtoList = wfNodeDefDto.getWfEventDefDtoList();
            if(CollectionUtil.isNotEmpty(_wfEventDefDtoList)) {
                wfEventDefDtoList.addAll(_wfEventDefDtoList);
            }
            //表达式
            List<WfExpreUserDefDto> _wfExpreUserDefDtoList = wfNodeDefDto.getWfExpreUserDefDtoList();
            if(CollectionUtil.isNotEmpty(_wfExpreUserDefDtoList)) {
                wfExpreUserDefDtoList.addAll(_wfExpreUserDefDtoList);
            }
            //变量
            List<WfVarDefDto> _wfVarDefDtoList = wfNodeDefDto.getWfVarDefDtoList();
            if(CollectionUtil.isNotEmpty(_wfVarDefDtoList)) {
                wfVarDefDtoList.addAll(_wfVarDefDtoList);
            }
            //按钮
            List<WfButtonDefDto> _wfButtonDefDtoList = wfNodeDefDto.getWfButtonDefDtoListDto();
            if(CollectionUtil.isNotEmpty(_wfButtonDefDtoList)) {
                wfButtonDefDtoList.addAll(_wfButtonDefDtoList);
            }
            //表单 - 字段
            List<WfFormDefDto> wfFormDefDtos = wfNodeDefDto.getWfFormDefDtos();
            if(CollectionUtil.isNotEmpty(wfFormDefDtos)) {
                wfFormDefDtoList.addAll(wfFormDefDtos);
            }
            //该节点如果是callActivity
            WfProcDefRelDto wfProcDefRelDto = wfNodeDefDto.getWfProcDefRel();
            if(null != wfProcDefRelDto) {
                WfProcDefRel wfProcDefRel = dozerMapper.map(wfProcDefRelDto,WfProcDefRel.class);
                wfProcDefRelList.add(wfProcDefRel);
            }
        }
        //保存节点
        if(CollectionUtil.isNotEmpty(wfNodeDefDtoList)) {
            List<WfNodeDef> wfNodeDefs = DozerUtils.mapList(dozerMapper, wfNodeDefDtoList, WfNodeDef.class);
            this.saveBatch(wfNodeDefs);
        }
        //保存事件处理
        if(CollectionUtil.isNotEmpty(wfEventDefDtoList)) {
            wfEventDefService.saveXml2WfEventDef(wfEventDefDtoList);
        }
        //保存表达式处理
        if(CollectionUtil.isNotEmpty(wfExpreUserDefDtoList)) {
            wfExpreUserDefService.saveXml2WfExpreDef(wfExpreUserDefDtoList);
        }
        //保存变量
        if(CollectionUtil.isNotEmpty(wfVarDefDtoList)) {
            wfVarDefService.saveXml2WfVarsDef(wfVarDefDtoList);
        }
        //保存按钮
        if(CollectionUtil.isNotEmpty(wfButtonDefDtoList)) {
            wfButtonDefService.saveXml2WfButtonsDef(wfButtonDefDtoList);
        }
        //保存 表单 - 字段
        if(CollectionUtil.isNotEmpty(wfFormDefDtoList)) {
            wfFormDefService.saveXml2WfFormDefDef(wfFormDefDtoList);
        }
        //保存 和子流程的关系信息
        if(CollectionUtil.isNotEmpty(wfProcDefRelList)) {
            wfProcDefRelService.saveBatch(wfProcDefRelList);
        }

    }

    /**
     * 编辑节点信息
     * @param nodeDefDtos
     */
    public void editSaveTask(List<WfNodeDefDto> nodeDefDtos) {
        for (WfNodeDefDto wfNodeDefDto : nodeDefDtos) {
            if(wfNodeDefDto.getIsChange() == YesNo.YES.code) {
                //修改节点信息
                WfNodeDef wfNodeDef = this.geNodeByCmdProcIdAndCmdNodeId(wfNodeDefDto.getCamundaProcdefId(),wfNodeDefDto.getCamundaNodeDefId());
                wfNodeDef.setNodeName(wfNodeDefDto.getNodeName());
                wfNodeDef.setDueDate(wfNodeDefDto.getDueDate());
                wfNodeDef.setPriority(wfNodeDefDto.getPriority());
                wfNodeDef.setSelectRule(wfNodeDefDto.getSelectRule());
                wfNodeDef.setBatchRule(wfNodeDefDto.getBatchRule());
                wfNodeDef.setCancelRule(wfNodeDefDto.getCancelRule());
                wfNodeDef.setReturnRule(wfNodeDefDto.getReturnRule());
                this.updateById(wfNodeDef);
                //修改和callActivity 关系
                WfProcDefRelDto wfProcDefRel = wfNodeDefDto.getWfProcDefRel();
                if(null != wfProcDefRel) {
                    WfProcDefRel _wfProcDefRel =  wfProcDefRelService.getCalActRelByCmdProcIdAndCmdNodeId(wfNodeDefDto.getCamundaProcdefId(),wfNodeDefDto.getCamundaNodeDefId());
                    if(null != _wfProcDefRel) {
                        wfProcDefRel.setMainNodeName(wfProcDefRel.getMainProcdefName());
                        wfProcDefRel.setChildProcdefName(_wfProcDefRel.getChildProcdefName());
                        wfProcDefRel.setChildProcdefVersion(_wfProcDefRel.getChildProcdefVersion());
                    }
                    wfProcDefRelService.updateById(_wfProcDefRel);
                }
                //删除事件及参数
                wfEventDefService.delEventByCmdNodeIdAndCmdProcdefId(wfNodeDefDto.getCamundaNodeDefId(),wfNodeDefDto.getCamundaProcdefId());
                //删除表达式及参数
                wfExpreUserDefService.delExpreByCmdNodeIdAndCmdProcdefId(wfNodeDefDto.getCamundaNodeDefId(),wfNodeDefDto.getCamundaProcdefId());
                //删除变量
                wfVarDefService.delVarsByCmdNodeIdAndCmdProcdefId(wfNodeDefDto.getCamundaNodeDefId(),wfNodeDefDto.getCamundaProcdefId());
                //删除表单
                wfFormDefService.delFormByCmdNodeIdAndCmdProcdefId(wfNodeDefDto.getCamundaNodeDefId(),wfNodeDefDto.getCamundaProcdefId());
                //删除表单字段
                wfFormFieldsDefService.delFieldsByCmdNodeIdAndCmdProcdefId(wfNodeDefDto.getCamundaNodeDefId(),wfNodeDefDto.getCamundaProcdefId());
                //删除按钮
                wfButtonDefService.delButtonsByCmdNodeIdAndCmdProcdefId(wfNodeDefDto.getCamundaNodeDefId(),wfNodeDefDto.getCamundaProcdefId());

                //重新添加事件处理
                List<WfEventDefDto> wfEventDefDtoList = wfNodeDefDto.getWfEventDefDtoList();
                wfEventDefService.saveXml2WfEventDef(wfEventDefDtoList);
                //重新添加表达式处理
                List<WfExpreUserDefDto> wfExpreUserDefDtoList = wfNodeDefDto.getWfExpreUserDefDtoList();
                wfExpreUserDefService.saveXml2WfExpreDef(wfExpreUserDefDtoList);
                //重新添加变量
                List<WfVarDefDto> wfVarDefDtoList = wfNodeDefDto.getWfVarDefDtoList();
                wfVarDefService.saveXml2WfVarsDef(wfVarDefDtoList);
                //重新添加按钮
                List<WfButtonDefDto> wfButtonDefDtoListDto = wfNodeDefDto.getWfButtonDefDtoListDto();
                wfButtonDefService.saveXml2WfButtonsDef(wfButtonDefDtoListDto);
                //修改 表单简单信息  以及表单字段
                List<WfFormDefDto> wfFormDefDtos = wfNodeDefDto.getWfFormDefDtos();
                wfFormDefService.saveXml2WfFormDefDef(wfFormDefDtos);
            }
        }
    }
}
