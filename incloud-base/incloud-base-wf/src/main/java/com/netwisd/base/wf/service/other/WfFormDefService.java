package com.netwisd.base.wf.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.dto.WfFormFieldsDefDto;
import com.netwisd.base.wf.entity.WfFormDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfFormDefDto;
import com.netwisd.base.wf.vo.WfFormDefVo;
import com.netwisd.common.db.data.BatchService;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 流程表单定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:31:54
 */
public interface WfFormDefService extends BatchService<WfFormDef> {
    Page list(WfFormDefDto wfFormDefDto);
    List<WfFormDefVo> lists(WfFormDefDto wfFormDefDto);
    WfFormDefVo get(Long id);
    Boolean save(WfFormDefDto wfFormDefDto);
    Boolean update(WfFormDefDto wfFormDefDto);
    Boolean delete(Long id);

    /**
     * 根据流程定义Key 删除所有的 表单 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 表单 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);

    /**
     * 根据 子流程数据id 删除表单信息（子流程使用）
     * @param delIds
     * @return
     */
    void deleteByNodeDefId(List<Serializable> delIds);

    /**
     * 保存表单信息 以及表单字段信息
     * @param wfFormDefDtoList
     */
    public void saveXml2WfFormDefDef(List<WfFormDefDto> wfFormDefDtoList);

    /**
     * 根据camumda节点id 以及camunda定义id 删除 表单信息
     * @param camundaNodeId
     */
    public void delFormByCmdNodeIdAndCmdProcdefId(String camundaNodeId,String camundaProcdefId);
}
