package com.netwisd.base.wf.service.impl.procdef;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.wf.constants.NodeEventTypeEnum;
import com.netwisd.base.wf.dto.WfEventDto;
import com.netwisd.base.wf.dto.WfEventParamDto;
import com.netwisd.base.wf.entity.WfEvent;
import com.netwisd.base.wf.entity.WfEventParam;
import com.netwisd.base.wf.mapper.WfEventMapper;
import com.netwisd.base.wf.mapper.WfEventParamMapper;
import com.netwisd.base.wf.service.procdef.WfEventParamService;
import com.netwisd.base.wf.service.procdef.WfEventService;
import com.netwisd.base.wf.vo.WfEventParamVo;
import com.netwisd.base.wf.vo.WfEventVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 按钮维护 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-02 11:37:15
 */
@Service
@Slf4j
public class WfEventServiceImpl extends ServiceImpl<WfEventMapper, WfEvent> implements WfEventService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfEventMapper wfEventMapper;

    @Autowired
    private WfEventParamMapper wfEventParamMapper;

    @Autowired
    private WfEventParamService wfEventParamService;

    /**
    * 单表简单查询操作
    * @param wfEventDto
    * @return
    */
    @Override
    public Page list(WfEventDto wfEventDto) {
        log.debug("事件维护列表查询参数:"+wfEventDto.toString());
        LambdaQueryWrapper<WfEvent> queryWrapper = new LambdaQueryWrapper<>();
        List<String> nodeEventTypeStr = new ArrayList<>();
        if(StringUtils.isNotEmpty(wfEventDto.getNodeEventType())) {
            nodeEventTypeStr = Stream.of(wfEventDto.getNodeEventType().split(",")).collect(Collectors.toList());
        }
        queryWrapper.eq(ObjectUtil.isNotEmpty(wfEventDto.getEventType()), WfEvent::getEventType, wfEventDto.getEventType())//事件分类
                //.eq(ObjectUtil.isNotEmpty(wfEventDto.getListenerType()), WfEvent::getListenerType, wfEventDto.getListenerType())
                .eq(ObjectUtil.isNotEmpty(wfEventDto.getProcdefTypeId()), WfEvent::getProcdefTypeId, wfEventDto.getProcdefTypeId())//流程分类
                .in(CollectionUtil.isNotEmpty(nodeEventTypeStr),WfEvent::getNodeEventType, nodeEventTypeStr)//节点类型
                .eq(ObjectUtil.isNotEmpty(wfEventDto.getDefaultTrigVal()),WfEvent::getDefaultTrigVal,wfEventDto.getDefaultTrigVal())//默认周期
                .eq(ObjectUtil.isNotEmpty(wfEventDto.getSelectSign()),WfEvent::getSelectSign,wfEventDto.getSelectSign())//默认事件
                .eq(ObjectUtil.isNotEmpty(wfEventDto.getSelectMust()),WfEvent::getSelectMust,wfEventDto.getSelectMust())//强制事件
                .like(ObjectUtil.isNotEmpty(wfEventDto.getProcdefTypeName()), WfEvent::getProcdefTypeName, wfEventDto.getProcdefTypeName())
                .like(ObjectUtil.isNotEmpty(wfEventDto.getListenerName()), WfEvent::getListenerName, wfEventDto.getListenerName())//事件名称
                .like(ObjectUtil.isNotEmpty(wfEventDto.getListenerId()), WfEvent::getListenerId, wfEventDto.getListenerId());//事件id
        Page<WfEvent> page = wfEventMapper.selectPage(wfEventDto.getPage(),queryWrapper);
        List<WfEvent> wfEventList = page.getRecords();
        List<WfEventVo> wfEventVoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(wfEventList)) {
            for (WfEvent wfEvent : wfEventList) {
                WfEventVo wfEventVo = dozerMapper.map(wfEvent,WfEventVo.class);
                //查询出对应的子表数据
                LambdaQueryWrapper<WfEventParam> queryEventParamWrapper = new LambdaQueryWrapper<>();
                queryEventParamWrapper.eq(ObjectUtil.isNotEmpty(wfEventVo.getId()), WfEventParam::getEventId, wfEventVo.getId());
                List<WfEventParam> wfEventParamList = wfEventParamMapper.selectList(queryEventParamWrapper);
                List<WfEventParamVo> wfEventParamVoList = new ArrayList<>();
                if(CollectionUtil.isNotEmpty(wfEventParamList)) {
                    for (WfEventParam wfEventParam : wfEventParamList) {
                        WfEventParamVo wfEventParamVo = dozerMapper.map(wfEventParam,WfEventParamVo.class);
                        wfEventParamVoList.add(wfEventParamVo);
                    }
                }
                wfEventVo.setWfEventParamList(wfEventParamVoList);
                wfEventVoList.add(wfEventVo);
            }
        }
        Page<WfEventVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfEventVo.class);
        if(CollectionUtil.isNotEmpty(wfEventVoList)) {
            pageVo.setRecords(wfEventVoList);
        }
        log.debug("事件维护列表查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 部分也 带子表 自定义查询操作
    * @param wfEventDto
    * @return
    */
    @Override
    public List<WfEventVo> lists(WfEventDto wfEventDto) {
        log.debug("事件维护列表查询参数:"+wfEventDto.toString());
        LambdaQueryWrapper<WfEvent> queryWrapper = new LambdaQueryWrapper<>();
        List<String> nodeEventTypeStr = new ArrayList<>();
        if(StringUtils.isNotEmpty(wfEventDto.getNodeEventType())) {
            nodeEventTypeStr = Stream.of(wfEventDto.getNodeEventType().split(",")).collect(Collectors.toList());
        }
        queryWrapper.eq(ObjectUtil.isNotEmpty(wfEventDto.getEventType()), WfEvent::getEventType, wfEventDto.getEventType())//事件分类
                //.eq(ObjectUtil.isNotEmpty(wfEventDto.getListenerType()), WfEvent::getListenerType, wfEventDto.getListenerType())
                .eq(ObjectUtil.isNotEmpty(wfEventDto.getProcdefTypeId()), WfEvent::getProcdefTypeId, wfEventDto.getProcdefTypeId())//流程分类
                .in(CollectionUtil.isNotEmpty(nodeEventTypeStr),WfEvent::getNodeEventType, nodeEventTypeStr)//节点类型
                .eq(ObjectUtil.isNotEmpty(wfEventDto.getDefaultTrigVal()),WfEvent::getDefaultTrigVal,wfEventDto.getDefaultTrigVal())//默认周期
                .eq(ObjectUtil.isNotEmpty(wfEventDto.getSelectSign()),WfEvent::getSelectSign,wfEventDto.getSelectSign())//默认事件
                .like(ObjectUtil.isNotEmpty(wfEventDto.getProcdefTypeName()), WfEvent::getProcdefTypeName, wfEventDto.getProcdefTypeName())
                .like(ObjectUtil.isNotEmpty(wfEventDto.getListenerName()), WfEvent::getListenerName, wfEventDto.getListenerName())//事件名称
                .like(ObjectUtil.isNotEmpty(wfEventDto.getListenerId()), WfEvent::getListenerId, wfEventDto.getListenerId());//事件id
        List<WfEvent> wfEventList = wfEventMapper.selectList(queryWrapper);
        List<WfEventVo> wfEventVoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(wfEventList)) {
            for (WfEvent wfEvent : wfEventList) {
                WfEventVo wfEventVo = dozerMapper.map(wfEvent,WfEventVo.class);
                //查询出对应的子表数据
                LambdaQueryWrapper<WfEventParam> queryEventParamWrapper = new LambdaQueryWrapper<>();
                queryEventParamWrapper.eq(ObjectUtil.isNotEmpty(wfEventVo.getId()), WfEventParam::getEventId, wfEventVo.getId());
                List<WfEventParam> wfEventParamList = wfEventParamMapper.selectList(queryEventParamWrapper);
                List<WfEventParamVo> wfEventParamVoList = new ArrayList<>();
                if(CollectionUtil.isNotEmpty(wfEventParamList)) {
                    for (WfEventParam wfEventParam : wfEventParamList) {
                        WfEventParamVo wfEventParamVo = dozerMapper.map(wfEventParam,WfEventParamVo.class);
                        wfEventParamVoList.add(wfEventParamVo);
                    }
                }
                wfEventVo.setWfEventParamList(wfEventParamVoList);
                wfEventVoList.add(wfEventVo);
            }
        }
        log.debug("事件维护列表查询条数:"+wfEventVoList.size());
        return wfEventVoList;
    }

    /**
     * 给加签用的
     * @param wfEventDto
     * @return
     */
    @Override
    public List<WfEvent> getDefaultEventList(WfEventDto wfEventDto) {
        log.debug("事件维护列表查询参数:"+wfEventDto.toString());
        LambdaQueryWrapper<WfEvent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotEmpty(wfEventDto.getEventType()), WfEvent::getEventType, wfEventDto.getEventType())//事件分类
                //流程分类
                .and(i -> i.eq(ObjectUtil.isNotEmpty(wfEventDto.getProcdefTypeId()), WfEvent::getProcdefTypeId, wfEventDto.getProcdefTypeId()).or().eq(ObjectUtil.isNotEmpty(wfEventDto.getProcdefTypeId()), WfEvent::getProcdefTypeId, 0))
                //节点类型——匹配用户节点或单用户节点的事件
                .and(i -> i.eq(WfEvent::getNodeEventType, NodeEventTypeEnum.USERTASK.code).or().eq(WfEvent::getNodeEventType,NodeEventTypeEnum.USERTASK_SINGLE.code))
                .eq(ObjectUtil.isNotEmpty(wfEventDto.getSelectSign()),WfEvent::getSelectSign,wfEventDto.getSelectSign());//默认事件
        List<WfEvent> wfEventList = wfEventMapper.selectList(queryWrapper);
        log.debug("事件维护列表查询条数:"+wfEventList.size());
        return wfEventList;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfEventVo get(Long id) {
        WfEvent wfEvent = super.getById(id);
        WfEventVo wfEventVo = null;
        if(wfEvent !=null){
            wfEventVo = dozerMapper.map(wfEvent,WfEventVo.class);
            LambdaQueryWrapper<WfEventParam> queryEventParamWrapper = new LambdaQueryWrapper<>();
            queryEventParamWrapper.eq(ObjectUtil.isNotEmpty(wfEventVo.getId()), WfEventParam::getEventId, wfEventVo.getId());
            List<WfEventParam> wfEventParamList = wfEventParamMapper.selectList(queryEventParamWrapper);
            List<WfEventParamVo> wfEventParamVoList = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(wfEventParamList)) {
                for (WfEventParam wfEventParam : wfEventParamList) {
                    WfEventParamVo wfEventParamVo = dozerMapper.map(wfEventParam,WfEventParamVo.class);
                    wfEventParamVoList.add(wfEventParamVo);
                }
            }
            wfEventVo.setWfEventParamList(wfEventParamVoList);
        }
        log.debug("查询成功");
        return wfEventVo;
    }

    /**
    * 保存实体
    * @param wfEventDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfEventDto wfEventDto) {
        WfEvent wfEvent = dozerMapper.map(wfEventDto,WfEvent.class);
        //保存事件主表
        boolean result = super.save(wfEvent);
        //保存参数信息
        List<WfEventParamDto> wfEventParamDtoList = wfEventDto.getWfEventParamList();
        if(CollectionUtil.isNotEmpty(wfEventParamDtoList)) {
            for (WfEventParamDto wfEventParamDto : wfEventParamDtoList) {
                wfEventParamDto.setEventId(wfEvent.getId());
                WfEventParam wfEventParam = dozerMapper.map(wfEventParamDto, WfEventParam.class);
               wfEventParamMapper.insert(wfEventParam);
            }
        }

        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfEventDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfEventDto wfEventDto) {
        WfEvent wfEvent = dozerMapper.map(wfEventDto,WfEvent.class);
        boolean result = super.updateById(wfEvent);
        if(result){
            log.debug("修改成功");
        }
        //删除参数列表
        wfEventParamService.deleteByEventId(wfEvent.getId());
        //保存参数信息
        List<WfEventParamDto> wfEventParamDtoList = wfEventDto.getWfEventParamList();
        if(CollectionUtil.isNotEmpty(wfEventParamDtoList)) {
            for (WfEventParamDto wfEventParamDto : wfEventParamDtoList) {
                wfEventParamDto.setEventId(wfEvent.getId());
                WfEventParam wfEventParam = dozerMapper.map(wfEventParamDto, WfEventParam.class);
                wfEventParamMapper.insert(wfEventParam);
            }
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param ids
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(String ids) {
        if(StringUtils.isNotBlank(ids)) {
            List<String> streamStr = Stream.of(ids.split(",")).collect(Collectors.toList());
            boolean result = super.removeByIds(streamStr);
            if(result){
                log.debug("删除成功");
                streamStr.forEach(d->{
                    //删除参数列表
                    wfEventParamService.deleteByEventId(Long.valueOf(d));
                });
            }
        } else  {
            throw new IncloudException("ID不能为空！");
        }
        return true;
    }

    @Override
    public List<WfEventVo> selectBatchIds(List<Long> ids) {
        log.debug("按照多个按钮id查询 参数：" + ids.toString());
        List<WfEvent> wfEventList = wfEventMapper.selectBatchIds(ids);
        List<WfEventVo> wfEventVoList = new ArrayList<>();
        for (WfEvent wfEvent : wfEventList) {
            WfEventVo wfEventVo = dozerMapper.map(wfEvent, WfEventVo.class);
            //查询参数
            LambdaQueryWrapper<WfEventParam> queryEventParamWrapper = new LambdaQueryWrapper<>();
            queryEventParamWrapper.eq(ObjectUtil.isNotEmpty(wfEventVo.getId()), WfEventParam::getEventId, wfEventVo.getId());
            List<WfEventParam> wfEventParamList = wfEventParamMapper.selectList(queryEventParamWrapper);
            List<WfEventParamVo> wfEventParamVoList = new ArrayList<>();
            if(CollectionUtil.isNotEmpty(wfEventParamList)) {
                for (WfEventParam wfEventParam : wfEventParamList) {
                    WfEventParamVo wfEventParamVo = dozerMapper.map(wfEventParam,WfEventParamVo.class);
                    wfEventParamVoList.add(wfEventParamVo);
                }
                wfEventVo.setWfEventParamList(wfEventParamVoList);
            }
            wfEventVoList.add(wfEventVo);

        }
        log.debug("按照多个按钮id查询 结果：" + wfEventVoList.toString());
        return wfEventVoList;
    }
}
