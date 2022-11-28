package com.netwisd.base.wf.service.procdef;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfExpreSequParamDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfExpreSequParamDefDto;
import com.netwisd.base.wf.vo.WfExpreSequParamDefVo;
import com.netwisd.common.db.data.BatchService;

/**
 * @Description 流程定义-序列流-表达式-参数 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 19:00:23
 */
public interface WfExpreSequParamDefService extends BatchService<WfExpreSequParamDef> {
    Page list(WfExpreSequParamDefDto wfExpreSequParamDefDto);
    Page lists(WfExpreSequParamDefDto wfExpreSequParamDefDto);
    WfExpreSequParamDefVo get(Long id);
    Boolean save(WfExpreSequParamDefDto wfExpreSequParamDefDto);
    Boolean update(WfExpreSequParamDefDto wfExpreSequParamDefDto);
    Boolean delete(Long id);

    /**
     * 根据流程定义Key 删除所有的 序列流-人员表达式-参数 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 序列流-人员表达式-参数 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);
}
