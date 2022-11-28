package com.netwisd.base.wf.service.procdef;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfProcdefType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfProcdefTypeDto;
import com.netwisd.base.wf.vo.WfProcdefTypeVo;

import java.util.List;

/**
 * @Description 流程分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-01 14:45:51
 */
public interface WfProcdefTypeService extends IService<WfProcdefType> {
    Page list(WfProcdefTypeDto wfProcdefTypeDto);
    List<WfProcdefTypeVo> lists(WfProcdefTypeDto wfProcdefTypeDto);
    WfProcdefTypeVo get(Long id);
    Boolean save(WfProcdefTypeDto wfProcdefTypeDto);
    Boolean update(WfProcdefTypeDto wfProcdefTypeDto);
    Boolean delete(String ids);
    List<WfProcdefTypeVo> selectBatchIds(List<Long> ids);
}
