package com.netwisd.base.wf.service.procdef;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfProcDefRel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfProcDefRelDto;
import com.netwisd.base.wf.vo.WfProcDefRelVo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 流程定义和子流程定义关系表 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-10-21 11:22:02
 */
public interface WfProcDefRelService extends IService<WfProcDefRel> {
    Page list(WfProcDefRelDto wfProcDefRelDto);
    Page lists(WfProcDefRelDto wfProcDefRelDto);
    WfProcDefRelVo get(Long id);
    Boolean save(WfProcDefRelDto wfProcDefRelDto);
    Boolean update(WfProcDefRelDto wfProcDefRelDto);
    Boolean delete(Long id);

    /**
     * 根据 子流程数据id 删除表单信息（子流程使用）
     * @param delIds
     * @return
     */
    void deleteByNodeDefId(List<Serializable> delIds);

    /**
     * 根据camunda流程定义id以及camundanodeId查询主流程和子流程的关系
     * @param camundaProcdefId
     * @param camundaNodeId
     * @return
     */
    WfProcDefRel getCalActRelByCmdProcIdAndCmdNodeId(String camundaProcdefId, String camundaNodeId);
}
