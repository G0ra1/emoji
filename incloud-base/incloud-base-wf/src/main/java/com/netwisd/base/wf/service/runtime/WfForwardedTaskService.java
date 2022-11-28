package com.netwisd.base.wf.service.runtime;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.wf.dto.WfForwardedTaskDto;
import com.netwisd.base.wf.entity.WfForwardedTask;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.vo.WfForwardedTaskVo;
/**
 * @Description 我发出的转办 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2022-03-02 15:43:53
 */
public interface WfForwardedTaskService extends IService<WfForwardedTask> {
    Page list(WfForwardedTaskDto wfForwardedTaskDto);
    Page lists(WfForwardedTaskDto wfForwardedTaskDto);
    WfForwardedTaskVo get(Long id);
    Boolean save(WfForwardedTaskDto wfForwardedTaskDto);
    Boolean update(WfForwardedTaskDto wfForwardedTaskDto);
    Boolean delete(Long id);

    //管理员查询
    Page forwardedListForAd(WfForwardedTaskDto wfForwardedTaskDto);
}
