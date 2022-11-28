package com.netwisd.base.wf.service.other;

import com.netwisd.base.wf.entity.WfButtonDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfButtonDefDto;
import com.netwisd.base.wf.vo.WfButtonDefVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @Description 流程定义-按钮 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 22:26:53
 */
public interface WfButtonDefService extends BatchService<WfButtonDef> {
    Page list(WfButtonDefDto wfButtonDefDto);
    Page lists(WfButtonDefDto wfButtonDefDto);
    WfButtonDefVo get(Long id);
    Boolean save(WfButtonDefDto wfButtonDefDto);
    Boolean update(WfButtonDefDto wfButtonDefDto);
    Boolean delete(Long id);

    /**
     * 根据流程定义Key 删除所有的按钮信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的按钮信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);

    /**
     * 根据表单id、流程定义id 和节点id 获取 所有的按钮信息
     * @param camundaDefId
     * @param camundaNodeDefId
     * @return
     */
    List<WfButtonDefVo> queryDefButtons(String camundaDefId, String camundaNodeDefId);

    /**
     * 根据节点id 删除 按钮
     * @param nodeDefId
     */
    public void delButtonsByNodeId(Long nodeDefId);

    /**
     * 保存 解析xml按钮对象
     * @param wfButtonDefDtoList
     */
    public void saveXml2WfButtonsDef(List<WfButtonDefDto> wfButtonDefDtoList);

    /**
     * 根据camumda节点id 以及camunda定义id 删除 按钮
     * @param camundaNodeId
     */
    public void delButtonsByCmdNodeIdAndCmdProcdefId(String camundaNodeId,String camundaProcdefId);
}
