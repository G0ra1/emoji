package com.netwisd.base.wf.service.impl.procdef;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.constants.YesNo;
import com.netwisd.base.wf.dto.*;
import com.netwisd.base.wf.entity.*;
import com.netwisd.base.wf.mapper.WfSequDefMapper;
import com.netwisd.base.wf.service.procdef.*;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.db.constants.IdTypeEnum;
import com.netwisd.common.db.data.BatchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.vo.WfSequDefVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description 流程定义-序列流 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 16:21:33
 */
@Service
@Slf4j
public class WfSequDefServiceImpl extends BatchServiceImpl<WfSequDefMapper, WfSequDef> implements WfSequDefService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfSequDefMapper wfSequDefMapper;

    @Autowired
    private WfExpreSequDefService wfExpreSequDefService;

    @Autowired
    private WfExpreSequParamDefService wfExpreSequParamDefService;

    @Autowired
    private WfSequEventDefService wfSequEventDefService;

    @Autowired
    private WfSequEventParamDefService wfSequEventParamDefService;

    @Autowired
    private WfVarDefService wfVarDefService;

    /**
    * 单表简单查询操作
    * @param wfSequDefDto
    * @return
    */
    @Override
    public Page list(WfSequDefDto wfSequDefDto) {
        WfSequDef wfSequDef = dozerMapper.map(wfSequDefDto,WfSequDef.class);
        LambdaQueryWrapper<WfSequDef> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfSequDef);
        Page<WfSequDefVo> page = wfSequDefMapper.selectPage(wfSequDefDto.getPage(),queryWrapper);
        Page<WfSequDefVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfSequDefVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfSequDefDto
    * @return
    */
    @Override
    public Page lists(WfSequDefDto wfSequDefDto) {
        Page<WfSequDefVo> pageVo = wfSequDefMapper.getPageList(wfSequDefDto.getPage(),wfSequDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfSequDefVo get(Long id) {
        WfSequDef wfSequDef = super.getById(id);
        WfSequDefVo wfSequDefVo = null;
        if(wfSequDef !=null){
            wfSequDefVo = dozerMapper.map(wfSequDef,WfSequDefVo.class);
        }
        log.debug("查询成功");
        return wfSequDefVo;
    }

    /**
    * 保存实体
    * @param wfSequDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfSequDefDto wfSequDefDto) {
        WfSequDef wfSequDef = dozerMapper.map(wfSequDefDto,WfSequDef.class);
        boolean result = super.save(wfSequDef);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfSequDefDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfSequDefDto wfSequDefDto) {
        WfSequDef wfSequDef = dozerMapper.map(wfSequDefDto,WfSequDef.class);
        boolean result = super.updateById(wfSequDef);
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
    public void saveXml2WfSequDef(boolean isUpdate, List<WfSequDefDto> wfSequDefDtoList,String camundaSubProcessNodeDefId) {
        if(CollectionUtil.isNotEmpty(wfSequDefDtoList)) {
            log.debug("解析xml 序列流定 信息：{}", wfSequDefDtoList.toString());
            List<WfSequDef> wfSequDefList = new ArrayList<>(); //序列流
            List<WfExpreSequDef> wfExpreSequDefList = new ArrayList<>(); //表达式
            List<WfExpreSequParamDef> wfExpreSequParamDefList = new ArrayList<>(); //表达式参数
            List<WfVarDef> wfVarDefList = new ArrayList<>();//序列流变量
            List<WfSequEventDef> wfSequEventDefList = new ArrayList<>();
            List<WfSequEventParamDef> wfSequEventParamDefList = new ArrayList<>();
            for (WfSequDefDto wfSequDefDto : wfSequDefDtoList) {
                //序列流基础信息
                WfSequDef wfSequDef = dozerMapper.map(wfSequDefDto,WfSequDef.class);
                wfSequDefList.add(wfSequDef);
                //序列流表达式信息
                List<WfExpreSequDefDto> wfExpreSequDefDtoList = wfSequDefDto.getWfExpreSequDefDtoList();
                if(CollectionUtil.isNotEmpty(wfExpreSequDefDtoList)) {
                    log.debug("解析xml 序列流定-表达式 信息：{}", wfExpreSequDefDtoList.toString());
                    for (WfExpreSequDefDto wfExpreSequDefDto : wfExpreSequDefDtoList) {
                        WfExpreSequDef wfExpreSequDef = dozerMapper.map(wfExpreSequDefDto,WfExpreSequDef.class);
                        wfExpreSequDefList.add(wfExpreSequDef);
                        //表达式参数信息
                        List<WfExpreSequParamDefDto> wfExpreSequParamDefDtoList = wfExpreSequDefDto.getWfExpreSequParamDefDtoList();
                        if(CollectionUtil.isNotEmpty(wfExpreSequParamDefDtoList)) {
                            log.debug("解析xml 序列流定-表达式-参数 信息：{}", wfExpreSequParamDefDtoList.toString());
                            for (WfExpreSequParamDefDto wfExpreSequParamDefDto : wfExpreSequParamDefDtoList) {
                                WfExpreSequParamDef wfExpreSequParamDef = dozerMapper.map(wfExpreSequParamDefDto,WfExpreSequParamDef.class);
                                wfExpreSequParamDefList.add(wfExpreSequParamDef);
                            }
                        }
                    }
                }
                // 序列流变量信息
                List<WfVarDefDto> wfVarDefDtoList = wfSequDefDto.getWfVarDefDtoList();
                if(CollectionUtil.isNotEmpty(wfVarDefDtoList)) {
                    log.debug("解析xml 序列流定-变量 信息：{}", wfVarDefDtoList.toString());
                    for (WfVarDefDto wfVarDefDto : wfVarDefDtoList) {
                        WfVarDef wfVarDef = dozerMapper.map(wfVarDefDto,WfVarDef.class);
                        wfVarDefList.add(wfVarDef);
                    }
                }

                //序列流事件信息
                List<WfSequEventDefDto> wfSequEventDefDtoList = wfSequDefDto.getWfSequEventDefDtoList();
                if(CollectionUtil.isNotEmpty(wfSequEventDefDtoList)) {
                    log.debug("解析xml 序列流定-事件 信息：{}", wfSequEventDefDtoList.toString());
                    for (WfSequEventDefDto wfSequEventDefDto : wfSequEventDefDtoList) {
                        WfSequEventDef wfSequEventDef = dozerMapper.map(wfSequEventDefDto,WfSequEventDef.class);
                        wfSequEventDefList.add(wfSequEventDef);
                        List<WfSequEventParamDefDto> wfSequEventParamDefDtoList = wfSequEventDefDto.getWfSequEventParamDefDtoList();
                        for (WfSequEventParamDefDto wfSequEventParamDefDto : wfSequEventParamDefDtoList) {
                            WfSequEventParamDef wfSequEventParamDef = dozerMapper.map(wfSequEventParamDefDto,WfSequEventParamDef.class);
                            wfSequEventParamDefList.add(wfSequEventParamDef);
                        }
                    }

                }
            }
            if(CollectionUtil.isNotEmpty(wfSequDefList)) {
                if(isUpdate) {
                    LambdaQueryWrapper<WfSequDef> wfSequDefWrapper = new LambdaQueryWrapper<>();
                    wfSequDefWrapper.eq(WfSequDef::getCamundaProcdefId, wfSequDefList.get(0).getCamundaProcdefId());
                            //.eq(StringUtils.isNotBlank(camundaSubProcessNodeDefId),WfSequDef::getCamundaParentNodeDefId,camundaSubProcessNodeDefId);
                    if(StringUtils.isNotBlank(camundaSubProcessNodeDefId)) {
                        wfSequDefWrapper.eq(WfSequDef::getCamundaParentNodeDefId,camundaSubProcessNodeDefId);
                    } else {
                        wfSequDefWrapper.isNull(WfSequDef::getCamundaParentNodeDefId);
                    }
                    Map<String, List<Serializable>> resultMap = super.saveOrUpdateOrDeleteResultBatch(wfSequDefWrapper,wfSequDefList);
                    log.debug("修改 序列流定 基础信息resultMap：{}", resultMap.toString());
                    if(ObjectUtil.isNotEmpty(resultMap)) {
                        //如果是删除 序列  需要把序列流对应的 变量也删掉
                        List<Serializable> delIds = resultMap.get(IdTypeEnum.DEL.getName());
                        if(CollectionUtil.isNotEmpty(delIds)) {
                            LambdaQueryWrapper<WfVarDef> delWfVarDefWrapper = new LambdaQueryWrapper<>();
                            delWfVarDefWrapper.in(WfVarDef::getSequDefId,delIds);
                            wfVarDefService.remove(delWfVarDefWrapper);
                        }
                        //如果是修改 序列  需要把序列流对应的 变量也修改
                        List<Serializable> editIds = resultMap.get(IdTypeEnum.EDIT.getName());
                        if(CollectionUtil.isNotEmpty(editIds)) {
                            for (Serializable editId : editIds) {
                                //只过滤出当前修改的事件 对应的参数
                                List<WfVarDef> editWfVarDefList = wfVarDefList.stream().filter(d->(d.getSequDefId() == editId)).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(editWfVarDefList)) {
                                    LambdaQueryWrapper<WfVarDef> wfVarDefWrapper = new LambdaQueryWrapper<>();
                                    wfVarDefWrapper.eq(WfVarDef::getSequDefId, editId);
                                    boolean updateSequParamDefBoo = wfVarDefService.saveOrUpdateOrDeleteBatch(wfVarDefWrapper,editWfVarDefList);
                                    log.debug("修改 序列流定 基础信息 -流程变量 信息：{}", updateSequParamDefBoo);
                                }
                            }

                        }
                        //如果是增加序列流操作 把序列流的变量也保存了
                        List<Serializable> addIds = resultMap.get(IdTypeEnum.ADD.getName());
                        if(CollectionUtil.isNotEmpty(addIds)) {
                            for (Serializable addId : addIds) {
                                List<WfVarDef> addWfVarDefList = wfVarDefList.stream().filter(d->(d.getSequDefId() == addId)).collect(Collectors.toList());
                                boolean addSequEventParamDefBool = wfVarDefService.saveBatch(addWfVarDefList);
                                log.debug("修改 序列流定 基础信息 -添加流程变量 信息：{}", addSequEventParamDefBool);
                            }

                        }

                    }
                } else {
                    boolean saveSequDefBoo = super.saveBatch(wfSequDefList);//保存序列流信息
                    log.debug("保存 序列流定 基础信息：{}", saveSequDefBoo);
                    if(CollectionUtil.isNotEmpty(wfVarDefList)) {//保存变量信息
                        boolean addSequEventParamDefBool = wfVarDefService.saveBatch(wfVarDefList);
                        log.debug("保存 序列流定-添加流程变量 信息：{}", addSequEventParamDefBool);
                    }
                }
            }

            //事件处理
            if(CollectionUtil.isNotEmpty(wfSequEventDefList)) {
                if(isUpdate) {
                    LambdaQueryWrapper<WfSequEventDef> wfSequEventDefWrapper = new LambdaQueryWrapper<>();
                    wfSequEventDefWrapper.eq(WfSequEventDef::getCamundaProcdefId, wfSequEventDefList.get(0).getCamundaProcdefId());
                    Map<String, List<Serializable>> resultMap = wfSequEventDefService.saveOrUpdateOrDeleteResultBatch(wfSequEventDefWrapper,wfSequEventDefList);
                    log.debug("修改 序列流定 表达式信息resultMap：{}", resultMap.toString());
                    //编辑的时候 如果是删除的表达式 则需要把对应的参数也删掉
                    if(ObjectUtil.isNotEmpty(resultMap)) {
                        //如果是删除的事件 直接把事件参数也删除
                        List<Serializable> delIds = resultMap.get(IdTypeEnum.DEL.getName());
                        if(CollectionUtil.isNotEmpty(delIds)) {
                            LambdaQueryWrapper<WfSequEventParamDef> delSequEventParamWrapper = new LambdaQueryWrapper<>();
                            delSequEventParamWrapper.in(WfSequEventParamDef::getEventDefId,delIds);
                            wfSequEventParamDefService.remove(delSequEventParamWrapper);
                        }
                        //如果是修改的事件，根据事件id 操作
                        List<Serializable> editIds = resultMap.get(IdTypeEnum.EDIT.getName());
                        if(CollectionUtil.isNotEmpty(editIds)) {
                            for (Serializable editId : editIds) {
                                //只过滤出当前修改的事件 对应的参数
                                List<WfSequEventParamDef> editSequEventParamDefList = wfSequEventParamDefList.stream().filter(d->(d.getEventDefId() == editId)).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(editSequEventParamDefList)) {
                                    LambdaQueryWrapper<WfSequEventParamDef> wfSequEventParamDefWrapper = new LambdaQueryWrapper<>();
                                    wfSequEventParamDefWrapper.eq(WfSequEventParamDef::getEventDefId, editId);
                                    boolean updateSequParamDefBoo = wfSequEventParamDefService.saveOrUpdateOrDeleteBatch(wfSequEventParamDefWrapper,editSequEventParamDefList);
                                    log.debug("修改 序列流定 事件-参数 信息：{}", updateSequParamDefBoo);
                                }
                            }

                        }
                        //如果是增加的事件则直接增加
                        List<Serializable> addIds = resultMap.get(IdTypeEnum.ADD.getName());
                        if(CollectionUtil.isNotEmpty(addIds)) {
                            for (Serializable addId : addIds) {
                                List<WfSequEventParamDef> addSequEventParamDefList = wfSequEventParamDefList.stream().filter(d->(d.getEventDefId() == addId)).collect(Collectors.toList());
                                boolean addSequEventParamDefBool = wfSequEventParamDefService.saveBatch(addSequEventParamDefList);
                                log.debug("流程定义编辑：保存 序列流定 事件-参数信息：{}", addSequEventParamDefBool);
                            }

                        }

                    }
                    log.debug("保存 序列流定 事件信息 执行成功。");
                } else {
                    boolean saveSeqEventDefBoo = wfSequEventDefService.saveBatch(wfSequEventDefList);
                    if(CollectionUtil.isNotEmpty(wfSequEventParamDefList)) {
                        boolean saveSeqParamEventDefBoo = wfSequEventParamDefService.saveBatch(wfSequEventParamDefList);
                        log.debug("保存 序列流定 事件-参数 信息：{}", saveSeqParamEventDefBoo);
                    }
                    log.debug("保存 序列流定 事件信息：{}", saveSeqEventDefBoo);
                }
            }

            //表达式处理
            if(CollectionUtil.isNotEmpty(wfExpreSequDefList)) {
                if(isUpdate) {
                    LambdaQueryWrapper<WfExpreSequDef> wfExpreDefWrapper = new LambdaQueryWrapper<>();
                    wfExpreDefWrapper.eq(WfExpreSequDef::getCamundaProcdefId, wfExpreSequDefList.get(0).getCamundaProcdefId());
                    Map<String, List<Serializable>> resultMap = wfExpreSequDefService.saveOrUpdateOrDeleteResultBatch(wfExpreDefWrapper,wfExpreSequDefList);
                    log.debug("修改 序列流定 表达式信息resultMap：{}", resultMap.toString());
                    //编辑的时候 如果是删除的表达式 则需要把对应的参数也删掉
                    if(ObjectUtil.isNotEmpty(resultMap)) {
                        //如果是删除的表达式 直接把表达式参数也删除
                        List<Serializable> delIds = resultMap.get(IdTypeEnum.DEL.getName());
                        LambdaQueryWrapper<WfExpreSequParamDef> delExpreSequParamWrapper = new LambdaQueryWrapper<>();
                        delExpreSequParamWrapper.in(WfExpreSequParamDef::getExpreSequDefId,delIds);
                        wfExpreSequParamDefService.remove(delExpreSequParamWrapper);
                        //如果是修改的表达式，根据表达式id 操作
                        List<Serializable> editIds = resultMap.get(IdTypeEnum.EDIT.getName());
                        if(CollectionUtil.isNotEmpty(editIds)) {
                            for (Serializable editId : editIds) {
                                //只过滤出当前修改的事件 对应的参数
                                List<WfExpreSequParamDef> editExpreSequParamDefList = wfExpreSequParamDefList.stream().filter(d->(d.getExpreSequDefId() == editId)).collect(Collectors.toList());
                                if(CollectionUtil.isNotEmpty(editExpreSequParamDefList)) {
                                    LambdaQueryWrapper<WfExpreSequParamDef> wfExpreSequParamWrapper = new LambdaQueryWrapper<>();
                                    wfExpreSequParamWrapper.eq(WfExpreSequParamDef::getExpreSequDefId, editId);
                                    boolean updateSequParamDefBoo = wfExpreSequParamDefService.saveOrUpdateOrDeleteBatch(wfExpreSequParamWrapper,editExpreSequParamDefList);
                                    log.debug("修改 序列流定 事件-参数 信息：{}", updateSequParamDefBoo);
                                }
                            }

                        }
                        //如果是增加的事件则直接增加
                        List<Serializable> addIds = resultMap.get(IdTypeEnum.ADD.getName());
                        if(CollectionUtil.isNotEmpty(addIds)) {
                            for (Serializable addId : addIds) {
                                List<WfExpreSequParamDef> addExpreSequParamList = wfExpreSequParamDefList.stream().filter(d->(d.getExpreSequDefId() == addId)).collect(Collectors.toList());
                                boolean addSequEventParamDefBool = wfExpreSequParamDefService.saveBatch(addExpreSequParamList);
                                log.debug("流程定义编辑：保存 序列流定 事件-参数信息：{}", addSequEventParamDefBool);
                            }
                        }
                    }
                    log.debug("保存 序列流定 事件信息 执行成功。");
                } else {
                    boolean saveExpreSeqDefBoo = wfExpreSequDefService.saveBatch(wfExpreSequDefList);
                    log.debug("保存 序列流定 表达式信息：{}", saveExpreSeqDefBoo);
                    if(CollectionUtil.isNotEmpty(wfExpreSequParamDefList)) {
                        boolean saveExpreParamSeqDefBoo = wfExpreSequParamDefService.saveBatch(wfExpreSequParamDefList);
                        log.debug("保存 序列流定 表达式-参数 信息：{}", saveExpreParamSeqDefBoo);
                    }
                }
            }

        }
    }

    @Override
    public void deleteByCamundaDefKey(String camundaDefKey) {
        log.debug("根据流程定义Key 删除所有的 序列流 信息(删除大版本). 参数camundaDefKey：{}", camundaDefKey);
        if(StringUtils.isBlank(camundaDefKey)) {
            throw new IncloudException("camundaDefKey 不能为空！");
        }
        LambdaQueryWrapper<WfSequDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfSequDef::getCamundaProcdefKey,camundaDefKey);
        int line = wfSequDefMapper.delete(delWrapper);
        log.debug("根据流程定义Key 删除所有的 序列流 信息(删除大版本). 影响行数：{}", line);
    }

    @Override
    public void deleteByCamundaDefId(String camundaDefId) {
        log.debug("根据流程定义id 删除所有的 序列流 信息(删除某个版本). 参数camundaDefId：{}", camundaDefId);
        if(StringUtils.isBlank(camundaDefId)) {
            throw new IncloudException("camundaDefId 不能为空！");
        }
        LambdaQueryWrapper<WfSequDef> delWrapper = new LambdaQueryWrapper<>();
        delWrapper.eq(WfSequDef::getCamundaProcdefId,camundaDefId);
        int line = wfSequDefMapper.delete(delWrapper);
        log.debug("根据流程定义id 删除所有的 序列流 信息(删除某个版本). 影响行数：{}", line);
    }

    @Override
    public WfSequDef getSequByCmdProcIdAndCmdSeqId(String camundaProcdefId, String camundaSequId) {
        LambdaQueryWrapper<WfSequDef> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(WfSequDef::getCamundaProcdefId, camundaProcdefId);
        queryWrapper.eq(WfSequDef::getCamundaSequId,camundaSequId);
        return wfSequDefMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional
    public void saveSequDefInfo(List<WfSequDefDto> wfSequDefDtos, boolean isAdd) {
        if(CollectionUtil.isNotEmpty(wfSequDefDtos)) {
            if(isAdd) { //如果是新增
                this.newSaveTask(wfSequDefDtos);
            } else { //如果是编辑
                this.editSaveTask(wfSequDefDtos);
            }
        }
    }
    /**
     * 保存新建的序列流
     * @param wfSequDefDtos 序列流信息
     */
    public void newSaveTask(List<WfSequDefDto> wfSequDefDtos) {
        List<WfSequDefDto> wfSequDefDtoList = new ArrayList<>(); //序列流
        List<WfSequEventDefDto> wfSequEventDefDtoList = new ArrayList<>();//事件
        List<WfVarDefDto> wfVarDefDtoList = new ArrayList<>();//变量
        for (WfSequDefDto wfSequDefDto : wfSequDefDtos) {
            wfSequDefDtoList.add(wfSequDefDto);
            //事件
            List<WfSequEventDefDto> _wfSequEventDefDtoList = wfSequDefDto.getWfSequEventDefDtoList();
            if(CollectionUtil.isNotEmpty(_wfSequEventDefDtoList)) {
                wfSequEventDefDtoList.addAll(_wfSequEventDefDtoList);
            }
            //变量
            List<WfVarDefDto> _wfVarDefDtoList = wfSequDefDto.getWfVarDefDtoList();
            if(CollectionUtil.isNotEmpty(_wfVarDefDtoList)) {
                wfVarDefDtoList.addAll(_wfVarDefDtoList);
            }
        }
        //保存序列流
        if(CollectionUtil.isNotEmpty(wfSequDefDtoList)) {
            List<WfSequDef> wfSequDefs = DozerUtils.mapList(dozerMapper, wfSequDefDtoList, WfSequDef.class);
            this.saveBatch(wfSequDefs);
        }
        //保存事件
        wfSequEventDefService.saveXml2SequEventDef(wfSequEventDefDtoList);
        //保存变量
        if(CollectionUtil.isNotEmpty(wfVarDefDtoList)) {
            wfVarDefService.saveXml2WfVarsDef(wfVarDefDtoList);
        }
    }
    /**
     * 编辑序列信息
     * @param wfSequDefDtos 序列流信息
     */
    public void editSaveTask(List<WfSequDefDto> wfSequDefDtos) {
        for (WfSequDefDto wfSequDefDto : wfSequDefDtos) {
            if(wfSequDefDto.getIsChange() == YesNo.YES.code) {
                //修改序列流
                WfSequDef wfSequDef = getSequByCmdProcIdAndCmdSeqId(wfSequDefDto.getCamundaProcdefId(), wfSequDefDto.getCamundaSequId());
                wfSequDef.setSequName(wfSequDefDto.getSequName());
                wfSequDef.setExpression(wfSequDefDto.getExpression());
                wfSequDef.setExpressionName(wfSequDefDto.getExpressionName());
                this.updateById(wfSequDef);
                //删除事件以及参数
                wfSequEventDefService.delSeqEventByCmdNodeIdAndCmdProcdefId(wfSequDefDto.getCamundaSequId(),wfSequDefDto.getCamundaProcdefId());
                //变量
                wfVarDefService.delVarsByCmdSeqIdAndCmdProcdefId(wfSequDefDto.getCamundaSequId(),wfSequDefDto.getCamundaProcdefId());

                //重新保存事件
                List<WfSequEventDefDto> wfSequEventDefDtoList = wfSequDefDto.getWfSequEventDefDtoList();
                wfSequEventDefService.saveXml2SequEventDef(wfSequEventDefDtoList);
                //重新保存变量
                List<WfVarDefDto> wfVarDefDtoList = wfSequDefDto.getWfVarDefDtoList();
                wfVarDefService.saveXml2WfVarsDef(wfVarDefDtoList);
            }
        }
    }

}
