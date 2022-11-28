package com.netwisd.base.wf.service.runtime;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.entity.WfDelegationTask;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfDelegationTaskDto;
import com.netwisd.base.wf.vo.WfDelegationTaskVo;
/**
 * @Description 我委托的待办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2022-03-02 15:19:21
 */
public interface WfDelegationTaskService extends IService<WfDelegationTask> {
    Page list(WfDelegationTaskDto wfDelegationTaskDto);
    Page lists(WfDelegationTaskDto wfDelegationTaskDto);
    WfDelegationTaskVo get(Long id);
    Boolean save(WfDelegationTaskDto wfDelegationTaskDto);
    Boolean update(WfDelegationTaskDto wfDelegationTaskDto);
    Boolean delete(Long id);
}
