package com.netwisd.base.wf.service.procdef;

import com.netwisd.base.wf.entity.WfEventDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfEventDefDto;
import com.netwisd.base.wf.vo.WfEventDefVo;
import com.netwisd.base.wf.vo.WfEventRuntimeVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @Description 事件定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 16:45:46
 */
public interface WfEventDefService extends BatchService<WfEventDef> {
    Page list(WfEventDefDto wfEventDefDto);
    Page lists(WfEventDefDto wfEventDefDto);
    WfEventDefVo get(Long id);
    Boolean save(WfEventDefDto wfEventDefDto);
    Boolean update(WfEventDefDto wfEventDefDto);
    Boolean delete(Long id);

    /**
     * 保存 解析xml对象
     * @param wfEventDefDtoList
     */
    public void saveXml2WfEventDef(List<WfEventDefDto> wfEventDefDtoList);

    /**
     * 根据camunda流程定义id 和camundaNodeId 事件分类eventType 绑定类型eventBindType 获取对应的事件
     * @param camundaProcdefId camundaProcdefId
     * @param camundaNodeDefId camundaNodeDefId
     * @param eventType eventType
     * @param eventBindType eventBindType
     */
    //WfEventRuntimeVo getEventByConditions(String camundaProcdefId, String camundaNodeDefId, Integer eventType, String eventBindType);

    /**
     * 根据节点id 删除 事件以及对应的参数
     * @param nodeDefId
     */
    public void delEventByNodeId(Long nodeDefId,String camundaProcdefId);

    /**
     *
     * @param camundaNodeDefId
     */
    //public void editXmlEventDefByNodeId(String camundaNodeDefId);
    List<WfEventRuntimeVo> getEventByConditions(String camundaProcdefId, String camundaNodeDefId, Integer eventType, String eventBindType);

    List<WfEventRuntimeVo> getMultiEventByConditions(String camundaProcdefId, String camundaNodeDefId, Integer eventType,String... eventBindType);
    /**
     * 根据流程定义Key 删除所有的 事件 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 事件 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);

    /**
     * 根据camumda节点id 以及camunda定义id 删除 事件以及对应的参数
     * @param camundaNodeId
     */
    public void delEventByCmdNodeIdAndCmdProcdefId(String camundaNodeId,String camundaProcdefId);
}
