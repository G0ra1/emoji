package com.netwisd.base.wf.event.listener;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.task.IdentityLink;

import java.util.Set;

/**
 * @Description: 内置监听：发送给相应人员消息
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/2 9:57 上午
 */
@Slf4j
@Deprecated
public class MessageListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        //获取到当前人员的受理人
        String assignee = delegateTask.getAssignee();
        if(StrUtil.isNotEmpty(assignee)){
            //sendmsg 模拟
            sendMsg(assignee);
        }else {
            Set<IdentityLink> candidates = delegateTask.getCandidates();
            for(IdentityLink identityLink : candidates){
                //获取到当前人员的候选人
                String userId = identityLink.getUserId();
                //sendmsg 模拟
                sendMsg(userId);
            }
        }
    }

    private void sendMsg(String userId){
        //sendmsg 模拟
        log.info("发送一个消息到指定人：{}",userId);
    }
}
