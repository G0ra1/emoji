package com.netwisd.base.wf.service.procdef;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfExpreUserParamDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfExpreUserParamDefDto;
import com.netwisd.base.wf.vo.WfExpreUserParamDefVo;
import com.netwisd.common.db.data.BatchService;

/**
 * @Description 人员表达式定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-14 11:49:34
 */
public interface WfExpreUserParamDefService extends BatchService<WfExpreUserParamDef> {
    Page list(WfExpreUserParamDefDto wfExpreUserParamDefDto);
    Page lists(WfExpreUserParamDefDto wfExpreUserParamDefDto);
    WfExpreUserParamDefVo get(Long id);
    Boolean save(WfExpreUserParamDefDto wfExpreUserParamDefDto);
    Boolean update(WfExpreUserParamDefDto wfExpreUserParamDefDto);
    Boolean delete(Long id);
    /**
     * 根据流程定义Key 删除所有的 人员表达式-参数 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 人员表达式-参数 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);
}
