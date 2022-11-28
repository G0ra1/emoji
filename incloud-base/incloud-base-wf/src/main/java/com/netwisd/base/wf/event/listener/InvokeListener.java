package com.netwisd.base.wf.event.listener;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.event.WfDelegateTaskDto;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.common.core.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.el.FixedValue;
import org.camunda.bpm.engine.task.IdentityLink;

import java.util.Set;

/**
 * @Description: 动态的任务监听
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/5 1:01 下午
 */
@Deprecated
@Slf4j
public class InvokeListener implements TaskListener{

    private FixedValue fixedValue;

    /*private String type;*/

    HttpClient httpClient = SpringContextUtils.getBean(HttpClient.class);

    Mapper dozerMapper = SpringContextUtils.getBean(Mapper.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        /*type = type.toUpperCase();
        Map<String, Object> type = EnumUtil.getNameFieldMap(MethodTypeEnum.class, "type");*/
        String url = fixedValue.getExpressionText();
        log.info("url:{}",url);
        HttpResponse execute = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            String accessToken = AppUserUtil.getAccessToken();
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            httpPost.setHeader("Authorization",accessToken);

            WfDelegateTaskDto wfDelegateTask = dozerMapper.map(delegateTask, WfDelegateTaskDto.class);

            getAssigee(delegateTask,wfDelegateTask);
            String jsonString = JSONUtil.toJsonStr(wfDelegateTask);
            StringEntity entity = new StringEntity(jsonString, "UTF-8");
            httpPost.setEntity(entity);

            execute = httpClient.execute(httpPost);

            // 判断返回状态是否为200
            if (execute.getStatusLine().getStatusCode() == 200) {
                log.info("自定义任务执行完成！");
            }else {
                log.info("自定义任务执行失败，错误代码：{}",execute.getStatusLine().getStatusCode());
                throw new BpmnError("errorooooooooooooo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }

    }

    private void getAssigee(DelegateTask delegateTask, WfDelegateTaskDto wfDelegateTask){
        String assignee = delegateTask.getAssignee();
        String userId = null;
        if(StrUtil.isEmpty(assignee)){
            Set<IdentityLink> candidates = delegateTask.getCandidates();
            for(IdentityLink identityLink : candidates){
                //获取到当前人员的候选人
                userId = identityLink.getUserId();
                log.info("用户ID为：{}",userId);
                break;
            }
        }
        wfDelegateTask.setAssignee(userId);
    }
}
