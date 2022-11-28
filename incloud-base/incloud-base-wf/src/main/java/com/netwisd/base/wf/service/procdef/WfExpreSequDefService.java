package com.netwisd.base.wf.service.procdef;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfExpreSequDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfExpreSequDefDto;
import com.netwisd.base.wf.vo.WfExpreSequDefVo;
import com.netwisd.base.wf.vo.WfExpreUserDefVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @Description 流程定义-序列流-表达式 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 18:59:28
 */
public interface WfExpreSequDefService extends BatchService<WfExpreSequDef> {
    Page list(WfExpreSequDefDto wfExpreSequDefDto);
    Page lists(WfExpreSequDefDto wfExpreSequDefDto);
    WfExpreSequDefVo get(Long id);
    Boolean save(WfExpreSequDefDto wfExpreSequDefDto);
    Boolean update(WfExpreSequDefDto wfExpreSequDefDto);
    Boolean delete(Long id);
    /**
     * 根据camunda流程定义id 和camunda 序列流id 获取所有的表达式信息
     * @param camundaProcdefId camundaProcdefId
     * @param camundaSequDefId camundaSequDefId
     */
    WfExpreSequDef getExpreByProcDefIdAndSequDefId(String camundaProcdefId, String camundaSequDefId);

    /**
     * 根据流程定义Key 删除所有的 序列流-人员表达式 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 序列流-人员表达式 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);
}
