package com.netwisd.base.common.event;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/5 1:40 下午
 */
public interface WfTaskHandler {

    String EVENTNAME_CREATE = "create";
    String EVENTNAME_ASSIGNMENT = "assignment";
    String EVENTNAME_COMPLETE = "complete";
    String EVENTNAME_UPDATE = "update";
    String EVENTNAME_DELETE = "delete";
    String EVENTNAME_TIMEOUT = "timeout";

    void handle(WfDelegateTaskDto delegateTask);

}
