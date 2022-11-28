package com.netwisd.base.wf.service.procdef;

import com.netwisd.base.wf.entity.WfVarDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfVarDefDto;
import com.netwisd.base.wf.vo.WfVarDefVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @Description 流程定义-变量 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 12:56:28
 */
public interface WfVarDefService extends BatchService<WfVarDef> {
    Page list(WfVarDefDto wfVarDefDto);
    Page lists(WfVarDefDto wfVarDefDto);
    WfVarDefVo get(Long id);
    Boolean save(WfVarDefDto wfVarDefDto);
    Boolean update(WfVarDefDto wfVarDefDto);
    Boolean delete(Long id);
    /**
     * 根据camunda流程定义id 和camunda节点id 获取所有节点变量信息
     * @param camundaProcdefId camundaProcdefId
     * @param camundaNodeDefId nodeDefId
     * @param isOrm 如果是true 只查询映射变量 如果false 则查询全部
     */
    List<WfVarDefVo> getVarByProcDefIdAndNodeDefId(String camundaProcdefId, String camundaNodeDefId,Boolean isOrm);

    /**
     * 根据camunda流程定义id 和camunda序列流id 获取所有序列流变量信息
     * @param camundaProcdefId camundaProcdefId
     * @param camundaSequDefId nodeDefId
     * @param isOrm 如果是true 只查询映射变量 如果false 则查询全部
     */
    List<WfVarDefVo> getVarByProcDefIdAndSequDefId(String camundaProcdefId, String camundaSequDefId, Boolean isOrm);


    /**
     * 根据流程定义Key 删除所有的 变量 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 变量 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);

    /**
     * 根据节点id 删除 变量
     * @param nodeDefId
     */
    public void delVarsByNodeId(Long nodeDefId);

    /**
     * 保存 解析xml变量对象
     * @param wfVarDefDtoList
     */
    public void saveXml2WfVarsDef(List<WfVarDefDto> wfVarDefDtoList);

    /**
     * 根据camumda节点id 以及camunda定义id 删除 变量信息
     * @param camundaNodeId
     */
    public void delVarsByCmdNodeIdAndCmdProcdefId(String camundaNodeId,String camundaProcdefId);

    /**
     * 根据camumda序列流id 以及camunda定义id 删除 变量信息
     * @param camundaSeqId
     */
    public void delVarsByCmdSeqIdAndCmdProcdefId(String camundaSeqId,String camundaProcdefId);
}
