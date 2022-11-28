package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.wf.constants.NodeTypeEnum;
import com.netwisd.base.wf.constants.WfProcessLogEnum;
import com.netwisd.base.wf.constants.YesNo;
import com.netwisd.base.wf.entity.WfProcess;
import com.netwisd.base.wf.entity.WfProcessLog;
import com.netwisd.base.wf.mapper.WfProcessLogMapper;
import com.netwisd.base.wf.service.runtime.WfProcessLogService;
import com.netwisd.base.wf.service.runtime.WfProcessService;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.wf.dto.WfProcessLogDto;
import com.netwisd.base.wf.vo.WfProcessLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 流程日志 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-08-14 17:42:43
 */
@Service
@Slf4j
public class WfProcessLogServiceImpl extends ServiceImpl<WfProcessLogMapper, WfProcessLog> implements WfProcessLogService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private WfProcessLogMapper wfProcessLogMapper;

    @Autowired
    private WfProcessService wfProcessService;

    /**
    * 单表简单查询操作
    * @param wfProcessLogDto
    * @return
    */
    @Override
    public Page list(WfProcessLogDto wfProcessLogDto) {
        WfProcessLog wfProcessLog = dozerMapper.map(wfProcessLogDto,WfProcessLog.class);
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        //实际开发中根据业务需求做查询，不用这种默认设置实体方式
        queryWrapper.setEntity(wfProcessLog);
        Page<WfProcessLog> page = wfProcessLogMapper.selectPage(wfProcessLogDto.getPage(),queryWrapper);
        Page<WfProcessLogVo> pageVo = DozerUtils.mapPage(dozerMapper, page, WfProcessLogVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param wfProcessLogDto
    * @return
    */
    @Override
    public Page lists(WfProcessLogDto wfProcessLogDto) {
        Page<WfProcessLogVo> pageVo = wfProcessLogMapper.getPageList(wfProcessLogDto.getPage(),wfProcessLogDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public WfProcessLogVo get(Long id) {
        WfProcessLog wfProcessLog = super.getById(id);
        WfProcessLogVo wfProcessLogVo = null;
        if(wfProcessLog !=null){
            wfProcessLogVo = dozerMapper.map(wfProcessLog,WfProcessLogVo.class);
        }
        log.debug("查询成功");
        return wfProcessLogVo;
    }

    /**
    * 保存实体
    * @param wfProcessLogDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(WfProcessLogDto wfProcessLogDto) {
        WfProcessLog wfProcessLog = dozerMapper.map(wfProcessLogDto,WfProcessLog.class);
        boolean result = super.save(wfProcessLog);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param wfProcessLogDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(WfProcessLogDto wfProcessLogDto) {
        WfProcessLog wfProcessLog = dozerMapper.map(wfProcessLogDto,WfProcessLog.class);
        Boolean result = update(wfProcessLog);
        return result;
    }

    @Override
    public Boolean update(WfProcessLog wfProcessLog) {
        boolean result = super.updateById(wfProcessLog);
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
    public Boolean delete(String processInstanceId) {
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcessLog::getCamundaProcinsId,processInstanceId);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    @Transactional
    public Boolean delWfTodoTaskByProInsAndTaskId(String procInstanceId, String camundaTaskId,Integer logType) {
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcessLog::getCamundaProcinsId,procInstanceId)
                    .eq(WfProcessLog::getCamundaTaskId,camundaTaskId)
                    .eq(ObjectUtil.isNotEmpty(logType),WfProcessLog::getType,logType);
        boolean remove = this.remove(queryWrapper);
        return remove;
    }

    @Override
    public WfProcessLog get(String camundaTaskId,Integer logType) {
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcessLog::getCamundaTaskId,camundaTaskId)
                    .eq(ObjectUtil.isNotEmpty(logType),WfProcessLog::getType,logType);
        WfProcessLog one = this.getOne(queryWrapper);
        return one;
    }

    @Override
    public WfProcessLog getByCurrentActInsId(String currentActInsId,Integer logType) {
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcessLog::getCamundaCurrentActInsId,currentActInsId)
                .eq(ObjectUtil.isNotEmpty(logType),WfProcessLog::getType,logType);
        WfProcessLog one = this.getOne(queryWrapper);
        return one;
    }

    public List<WfProcessLogVo> forQueryChildLog(WfProcess wfProcess,List<WfProcessLogVo> mainLoglistVo) {
        /*String camundaChildLogProcinsId = wfProcess.getCamundaChildLogProcinsId();
        if(StringUtils.isNotBlank(camundaChildLogProcinsId)) {
            String[] camundaChildLogProcinsIdArr = camundaChildLogProcinsId.split(",");
            if(camundaChildLogProcinsIdArr.length > 0) {
                //迭代出子是否还存在子流程
                List<WfProcessLogVo> childLoglist = new ArrayList<>();
                for (String childProcess : camundaChildLogProcinsIdArr) {
                    LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(WfProcessLog::getCamundaProcinsId,childProcess);
                    queryWrapper.orderByDesc(WfProcessLog::getCreateTime);
                    WfProcess _wfProcess = wfProcessService.get(childProcess);
                    List<WfProcessLog> mainLoglist = this.list(queryWrapper);
                    List<WfProcessLogVo> _mainLoglistVo = DozerUtils.mapList(dozerMapper, mainLoglist, WfProcessLogVo.class);
                    log.info("主流程日志条数：{}",mainLoglist.size());
                    childLoglist.addAll(this.forQueryChildLog(_wfProcess,_mainLoglistVo));
                }

                Map<String, List<WfProcessLogVo>> childLogMap = childLoglist.stream().collect(Collectors.groupingBy(WfProcessLogVo::getCamundaCallActivityDefId));
                mainLoglistVo.forEach(d-> {
                    List<WfProcessLogVo> _childLogList = childLogMap.get(d.getNodeId());
                    if(CollectionUtils.isNotEmpty(_childLogList)) {
                        if(d.getNodeType() == NodeTypeEnum.CALLACTIVITY.code) {
                            Map map = new HashMap();
                            map.put(_childLogList.get(0).getCamundaProcinsId(),_childLogList);
                            d.setChildWfProcessLog(map);
                        }
                        if(d.getNodeType() == NodeTypeEnum.MULTIINSTANCECALLACTIVITYS.code) {
                            Map<String, List<WfProcessLogVo>> multiChildLogMap = _childLogList.stream().collect(Collectors.groupingBy(WfProcessLogVo::getCamundaProcinsId));
                            d.setChildWfProcessLog(multiChildLogMap);
                        }
                    }
                });
                return mainLoglistVo;
            }
        }*/
        return  mainLoglistVo;
    }

    @Override
    public List<WfProcessLogVo> getList(String camundaProcinsId,boolean isAll,boolean isSingleUserTask) {
        if(StringUtils.isBlank(camundaProcinsId)) {
            throw new IncloudException("camundaProcinsId,不能为空！");
        }
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcessLog::getCamundaProcinsId,camundaProcinsId);
        if(!isAll){
            queryWrapper.in(WfProcessLog::getType, WfProcessLogEnum.NONE.getType(),WfProcessLogEnum.SUBMIT.getType());
        }
        if(isSingleUserTask){
            queryWrapper.eq(WfProcessLog::getNodeType, NodeTypeEnum.USERTASK.getCode());
        }
        queryWrapper.orderByDesc(WfProcessLog::getCreateTime);
        //查询 当前流程是否是主流程
        WfProcess wfProcess = wfProcessService.get(camundaProcinsId);
        if(ObjectUtil.isNotNull(wfProcess)) {
            /*if(wfProcess.getIsCallActivity() == YesNo.NO.code) {  //主流程
                //所有的主流程日志
                List<WfProcessLog> mainLoglist = this.list(queryWrapper);
                List<WfProcessLogVo> mainLoglistVo = DozerUtils.mapList(dozerMapper, mainLoglist, WfProcessLogVo.class);
                log.info("主流程日志条数：{}",mainLoglist.size());
                return  forQueryChildLog(wfProcess,mainLoglistVo);
            } else {  //子流程
                //查询所有本流程的日志
                List<WfProcessLog> childLoglist = this.list(queryWrapper);
                List<WfProcessLogVo> _childLoglistVo = DozerUtils.mapList(dozerMapper, childLoglist, WfProcessLogVo.class);
                List<WfProcessLogVo> loglistVo =forQueryParentLog(wfProcess,_childLoglistVo);
                return loglistVo;
            }*/
            return DozerUtils.mapList(dozerMapper, list(queryWrapper), WfProcessLogVo.class);
        } else {
            throw new IncloudException("没有对应的流程实例信息！");
        }
    }

    public List<WfProcessLogVo> forQueryParentLog(WfProcess wfProcess,List<WfProcessLogVo> childLoglistVo) {
        //判断本流程是否存在父级流程日志
        /*String camundaParentProcinsId = wfProcess.getCamundaParentProcinsId();
        if(StringUtils.isNotBlank(camundaParentProcinsId)) {
            LambdaQueryWrapper<WfProcessLog> queryMainWrapper = new LambdaQueryWrapper<>();
            queryMainWrapper.eq(WfProcessLog::getCamundaProcinsId,wfProcess.getCamundaParentProcinsId());
            queryMainWrapper.orderByDesc(WfProcessLog::getCreateTime);
            List<WfProcessLog> mainLoglist = this.list(queryMainWrapper);
            List<WfProcessLogVo> _mainLoglistVo = DozerUtils.mapList(dozerMapper, mainLoglist, WfProcessLogVo.class);
            //查询所有本流程的日志
            _mainLoglistVo.forEach(d -> {
                if(d.getNodeId().equals(wfProcess.getCamundaCallActivityDefId())) {
                    Map map = new HashMap();
                    map.put(wfProcess.getCamundaProcinsId(),childLoglistVo);
                    d.setChildWfProcessLog(map);
                }
            });
            WfProcess wfParentProcess = wfProcessService.get(wfProcess.getCamundaParentProcinsId());
            if(ObjectUtil.isNotNull(wfParentProcess)) {
               Long  parentLogProcinsId = wfParentProcess.getParentLogProcinsId();
               if(ObjectUtil.isNotNull(parentLogProcinsId)) {
                   forQueryParentLog(wfParentProcess,_mainLoglistVo);
               }
            } else {
                throw new IncloudException("没有查询到父级实例信息！");
            }
            return _mainLoglistVo;
        }*/
        return childLoglistVo;
    }


    /**
     * 通过当前节点获取他上一个节点
     * @param camundaProcinsId
     * @param camundaDefKey
     * @return
     */
    @Override
    public WfProcessLogVo getLastNodeInfo(String camundaProcinsId, String camundaDefKey) {
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcessLog::getCamundaProcinsId,camundaProcinsId)
                //目标节点是当前节点的
                .eq(WfProcessLog::getTargetNodeId,camundaDefKey)
                //查找的当前的节点类型是普通用户任务的
                .eq(WfProcessLog::getNodeType,NodeTypeEnum.USERTASK.getCode())
                .orderByDesc(WfProcessLog::getEndTime);
        List<WfProcessLog> list = this.list(queryWrapper);
        if(ObjectUtil.isEmpty(list) || list.isEmpty()){
            throw new IncloudException("查询上一个节点流程日志失败！");
        }
        WfProcessLog wfProcessLog = list.get(0);
        WfProcessLogVo wfProcessLogVo = dozerMapper.map(wfProcessLog, WfProcessLogVo.class);
        return wfProcessLogVo;
    }

    /**
     * 通过当前节点获取他上一个驳回节点
     * @param camundaProcinsId
     * @param camundaDefKey
     * @return
     */
    @Override
    public WfProcessLogVo getLastRejectNodeInfo(String camundaProcinsId, String camundaDefKey) {
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcessLog::getCamundaProcinsId,camundaProcinsId)
                //目标节点是当前节点的
                .eq(WfProcessLog::getTargetNodeId,camundaDefKey)
                //查找的当前的节点类型是普通用户任务的
                .eq(WfProcessLog::getNodeType,NodeTypeEnum.USERTASK.getCode())
                //获取的是驳回类型的
                .eq(WfProcessLog::getType,WfProcessLogEnum.REJECT.getType())
                .orderByDesc(WfProcessLog::getEndTime);
        List<WfProcessLog> list = this.list(queryWrapper);
        if(ObjectUtil.isEmpty(list) || list.isEmpty()){
            return null;
        }
        WfProcessLog wfProcessLog = list.get(0);
        WfProcessLogVo wfProcessLogVo = dozerMapper.map(wfProcessLog, WfProcessLogVo.class);
        return wfProcessLogVo;
    }

    @Override
    public List<WfProcessLogVo> getRejectAllList(String camundaProcinsId) {
        if(StringUtils.isBlank(camundaProcinsId)) {
            throw new IncloudException("camundaProcinsId,不能为空！");
        }
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcessLog::getCamundaProcinsId,camundaProcinsId);
        queryWrapper.in(WfProcessLog::getType, WfProcessLogEnum.NONE.getType(),WfProcessLogEnum.SUBMIT.getType());
        queryWrapper.eq(WfProcessLog::getNodeType, NodeTypeEnum.USERTASK.getCode());
        List<WfProcessLog> loglist = this.list(queryWrapper);
        List<WfProcessLogVo> loglistVo = DozerUtils.mapList(dozerMapper, loglist, WfProcessLogVo.class);
        return loglistVo;
    }

    @Override
    public List<WfProcessLogVo> getRejectAllToList(String camundaProcinsId) {
        if(StringUtils.isBlank(camundaProcinsId)) {
            throw new IncloudException("camundaProcinsId,不能为空！");
        }
        //查询出所有的可以驳回的节点
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcessLog::getCamundaProcinsId,camundaProcinsId);
        queryWrapper.in(WfProcessLog::getType, WfProcessLogEnum.NONE.getType(),WfProcessLogEnum.SUBMIT.getType());
        queryWrapper.eq(WfProcessLog::getNodeType, NodeTypeEnum.USERTASK.getCode());
        queryWrapper.isNotNull(WfProcessLog::getTargetNodeId);
        List<WfProcessLog> loglist = this.list(queryWrapper);
        List<WfProcessLogVo> _loglist = new ArrayList<>();
        //按照节点id 分组 并且取创建时间最新的一条
        if(CollectionUtils.isNotEmpty(loglist)) {
            Map<String, List<WfProcessLog>> sortedDatas = loglist.stream().sorted(Comparator.comparing(WfProcessLog::getCreateTime, Comparator.nullsLast(Comparator.reverseOrder())))
                    .collect(Collectors.groupingBy(WfProcessLog::getNodeId, LinkedHashMap::new, Collectors.toList()));
            for (Map.Entry<String, List<WfProcessLog>> elem : sortedDatas.entrySet()) {
                System.out.println("key:" + elem.getKey());
                List<WfProcessLog> wfProcessLogList = elem.getValue();
                if(CollectionUtils.isNotEmpty(wfProcessLogList)) {
                    for (int i = 0; i < wfProcessLogList.size(); i++) {
                        if(i == 0) {
                            WfProcessLog wfProcessLog = wfProcessLogList.get(0);
                            WfProcessLogVo wfProcessLogVo = dozerMapper.map(wfProcessLog,WfProcessLogVo.class);
                            _loglist.add(wfProcessLogVo);
                        }else {
                            continue;
                        }
                    }
                }
            }
        }
        return _loglist;
    }

    @Override
    public List<WfProcessLogVo> getLogList(String camundaProcinsId, String callActivityId) {
        if(StringUtils.isBlank(camundaProcinsId)) {
            throw new IncloudException("流程实例id不能为空！");
        }
        //如果不是查子 则只查自己
        if(StringUtils.isBlank(callActivityId)) {
            LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WfProcessLog::getCamundaProcinsId,camundaProcinsId);
            List<WfProcessLog> loglist = this.list(queryWrapper);
            List<WfProcessLogVo> loglistVo = DozerUtils.mapList(dozerMapper, loglist, WfProcessLogVo.class);
            return loglistVo;
        }
        //如果查子需要两个参数都传
        if(StringUtils.isNotBlank(callActivityId)) {
            LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WfProcessLog::getCamundaCallActivityDefId,callActivityId);
            queryWrapper.eq(WfProcessLog::getCamundaParentProcinsId,camundaProcinsId);
            List<WfProcessLog> loglist = this.list(queryWrapper);
            List<WfProcessLogVo> loglistVo = DozerUtils.mapList(dozerMapper, loglist, WfProcessLogVo.class);
            return loglistVo;
        }
        return null;
    }

    @Override
    public List<WfProcessLogVo> getchildProcessLogActInsId(String camundaCurrentActInsId) {
        if(StringUtils.isBlank(camundaCurrentActInsId)) {
            throw new IncloudException("camundaCurrentActInsId不能为空！");
        }
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcessLog::getCamundaParentActInsId,camundaCurrentActInsId);
        List<WfProcessLog> loglist = this.list(queryWrapper);
        List<WfProcessLogVo> loglistVo = DozerUtils.mapList(dozerMapper, loglist, WfProcessLogVo.class);
        return loglistVo;
    }

    @Override
    public boolean isDraftByLog(String camundaProcinsId) {
        if(StringUtils.isBlank(camundaProcinsId)) {
            throw new IncloudException("camundaProcinsId不能为空！");
        }
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcessLog::getCamundaProcinsId,camundaProcinsId);
        queryWrapper.orderByDesc(WfProcessLog::getCreateTime);
        List<WfProcessLog> wfProcessLogList = wfProcessLogMapper.selectList(queryWrapper);
        if(CollectionUtils.isNotEmpty(wfProcessLogList)) {
            WfProcessLog wfProcessLog = wfProcessLogList.get(0);
            //根据当前人的流程日志 查询targetNode
            LambdaQueryWrapper<WfProcessLog> _queryWrapper = new LambdaQueryWrapper<>();
            _queryWrapper.eq(WfProcessLog::getCamundaProcinsId,camundaProcinsId);
            _queryWrapper.eq(WfProcessLog::getTargetNodeId,wfProcessLog.getNodeId());
            _queryWrapper.orderByDesc(WfProcessLog::getCreateTime);
            List<WfProcessLog> _wfProcessLogList = wfProcessLogMapper.selectList(_queryWrapper);
            if(CollectionUtils.isNotEmpty(_wfProcessLogList)) {
                WfProcessLog _wfProcessLog = _wfProcessLogList.get(0);
                if(_wfProcessLog.getType() == WfProcessLogEnum.REJECT.getType()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<WfProcessLogVo> getLogsByInsIdAndNodeId(String camundaProcinsId, String nodeId) {
        if(StringUtils.isBlank(camundaProcinsId)) {
            throw new IncloudException("流程实例id不能为空！");
        }
        if(StringUtils.isBlank(nodeId)) {
            throw new IncloudException("节点id不能为空！");
        }
        LambdaQueryWrapper<WfProcessLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WfProcessLog::getNodeId,nodeId);
        queryWrapper.eq(WfProcessLog::getCamundaProcinsId,camundaProcinsId);
        List<WfProcessLog> loglist = this.list(queryWrapper);
        List<WfProcessLogVo> loglistVo = DozerUtils.mapList(dozerMapper, loglist, WfProcessLogVo.class);
        return loglistVo;
    }
}
