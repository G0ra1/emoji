package com.netwisd.base.wf.service.other;

import com.netwisd.base.wf.dto.WfFormFieldsDefDto;
import com.netwisd.base.wf.entity.WfFormFieldsDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.vo.WfFormFieldsDefVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @Description 流程表单字段定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:43:44
 */
public interface WfFormFieldsDefService extends BatchService<WfFormFieldsDef> {
    Page list(WfFormFieldsDefDto wfFormFieldsDefDto);
    Page lists(WfFormFieldsDefDto wfFormFieldsDefDto);
    WfFormFieldsDefVo get(Long id);
    Boolean save(WfFormFieldsDefDto wfFormFieldsDefDto);
    Boolean update(WfFormFieldsDefDto wfFormFieldsDefDto);
    Boolean delete(Long id);

    /**
     * 根据流程定义Key 删除所有的 表单字段 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 表单字段 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);

    /**
     * 根据表单id、流程定义id 和节点定义id 查询出所有的表单字段信息
     * @param camundaDefId
     * @param camundaNodeDefId
     * @return
     */
    List<WfFormFieldsDefVo> getFormFields(String camundaDefId, String camundaNodeDefId);

    /**
     * 根据节点id 和表单id 删除表单字段信息
     * @param nodeDefId
     */
    public void delFormFieldsByNodeIdAndFormId(Long nodeDefId, List formIdList);

    /**
     * 根据camumda节点id 以及camunda定义id 删除 表单字段信息
     * @param camundaNodeId
     */
    public void delFieldsByCmdNodeIdAndCmdProcdefId(String camundaNodeId,String camundaProcdefId);

}
