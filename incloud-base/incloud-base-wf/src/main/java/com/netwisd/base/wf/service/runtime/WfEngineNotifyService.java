package com.netwisd.base.wf.service.runtime;

import com.netwisd.base.wf.starter.dto.WfEngineDto;
import org.camunda.bpm.engine.task.Task;

/** 流程知会相关
 * @author XHL
 * @description
 * @date 2022/03/08 16:41
 */
public interface WfEngineNotifyService {
    /**
     * 流程提交 处理知会逻辑
     * @param handleDto
     * @param task
     */
    void createNotify(WfEngineDto.HandleDto handleDto, Task task);

    /**
     * 收到的知会台账 处理提交
     * @param id 知会id
     */
    void handleNotifyOp(String id, String op);

}
