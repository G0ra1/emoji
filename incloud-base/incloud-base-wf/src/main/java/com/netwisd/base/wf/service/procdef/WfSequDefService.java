package com.netwisd.base.wf.service.procdef;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.dto.WfEventDefDto;
import com.netwisd.base.wf.dto.WfNodeDefDto;
import com.netwisd.base.wf.entity.WfSequDef;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfSequDefDto;
import com.netwisd.base.wf.vo.WfExpreUserDefVo;
import com.netwisd.base.wf.vo.WfSequDefVo;
import com.netwisd.common.db.data.BatchService;

import java.util.List;

/**
 * @Description 流程定义-序列流 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 16:21:33
 */
public interface WfSequDefService extends BatchService<WfSequDef> {
    Page list(WfSequDefDto wfSequDefDto);
    Page lists(WfSequDefDto wfSequDefDto);
    WfSequDefVo get(Long id);
    Boolean save(WfSequDefDto wfSequDefDto);
    Boolean update(WfSequDefDto wfSequDefDto);
    Boolean delete(Long id);

    /**
     * 保存 解析xml对象
     * @param wfSequDefDtoList
     */
    public void saveXml2WfSequDef(boolean isUpdate,List<WfSequDefDto> wfSequDefDtoList,String camundaSubProcessNodeDefId);

    /**
     * 根据流程定义Key 删除所有的 序列流 信息(删除大版本)
     * @param camundaDefKey
     * @return
     */
    void deleteByCamundaDefKey(String camundaDefKey);

    /**
     * 根据流程定义id 删除所有的 序列流 信息(删除某个版本)
     * @param camundaDefId
     * @return
     */
    void deleteByCamundaDefId(String camundaDefId);

    //保存序列流信息
    void saveSequDefInfo(List<WfSequDefDto> wfSequDefDto, boolean isNew);

    //根据camunda流程定义id 以及序列流节点id修改序列信息
    WfSequDef getSequByCmdProcIdAndCmdSeqId(String camundaProcdefId,String camundaSequId);


}
