package com.netwisd.base.wf.service.procdef;

import com.netwisd.base.wf.entity.WfExpreUserDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfExpreUserDefDto;
import com.netwisd.base.wf.vo.WfExpreUserDefVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @Description 人员表达式定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-14 11:48:32
 */
public interface WfExpreUserDefService extends BatchService<WfExpreUserDef> {
    Page list(WfExpreUserDefDto wfExpreUserDefDto);
    Page lists(WfExpreUserDefDto wfExpreUserDefDto);
    WfExpreUserDefVo get(Long id);
    Boolean save(WfExpreUserDefDto wfExpreUserDefDto);
    Boolean update(WfExpreUserDefDto wfExpreUserDefDto);
    Boolean delete(Long id);
    /**
     * 根据camunda流程定义id 和camunda节点id 获取所有的表达式信息
     * @param camundaProcdefId camundaProcdefId
     * @param camundaNodeDefId nodeDefId
     */
    List<WfExpreUserDef> getExpreByProcDefIdAndNodeDefId(String camundaProcdefId, String camundaNodeDefId);

    /**
     * 根据流程定义Key 删除所有的 人员表达式 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 人员表达式 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);

    /**
     * 根据节点id 删除 表达式以及对应的参数
     * @param nodeDefId
     */
    public void delExpreByNodeId(Long nodeDefId,String camundaProcdefId);

    /**
     * 保存 解析xml用户表达式对象
     * @param wfExpreUserDefDtoList
     */
    public void saveXml2WfExpreDef(List<WfExpreUserDefDto> wfExpreUserDefDtoList);

    /**
     * 根据camumda节点id 以及camunda定义id 删除 表达式以及参数
     * @param camundaNodeId
     */
    public void delExpreByCmdNodeIdAndCmdProcdefId(String camundaNodeId,String camundaProcdefId);
}
