package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.entity.WfTodoTask;

/**
 * @author zouliming@netwisd.com
 * @Description:
 * @date 2021/12/3 11:50
 */
public interface WfEngineClaimService {
    WfTodoTask claimProcess(String taskId);
}
