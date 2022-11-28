package com.netwisd.base.wf.service.impl.runtime;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.wf.service.runtime.WfTaskService;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zouliming@netwisd.com
 * @Description:
 * @date 2021/12/3 11:22
 */
@Service
@Slf4j
public class WfTaskServiceImpl implements WfTaskService {

    @Autowired
    private TaskService taskService;

    @Override
    public Task getAndCheck(String taskId) {
        if(StringUtils.isBlank(taskId)) {
            throw new IncloudException("taskId不能为空！");
        }
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if(ObjectUtil.isEmpty(task)){
            throw new IncloudException("通过taskId查找不到task对象");
        }
        return task;
    }

    @Override
    public Task getAndCheckClaim(String taskId) {
        Task task = getAndCheck(taskId);
        if(StrUtil.isEmpty(task.getAssignee())){
            throw new IncloudException("请先签收才能提交");
        }
        return task;
    }
}
