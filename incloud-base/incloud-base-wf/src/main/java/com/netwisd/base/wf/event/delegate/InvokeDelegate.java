package com.netwisd.base.wf.event.delegate;

import cn.hutool.json.JSONUtil;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.event.WfDelegateTaskDto;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.el.FixedValue;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/7/3 10:19 上午
 */
@Slf4j
@Deprecated
public class InvokeDelegate implements ExecutionListener {

    private FixedValue fixedValue;

    HttpClient httpClient = SpringContextUtils.getBean(HttpClient.class);

    Mapper dozerMapper = SpringContextUtils.getBean(Mapper.class);

    @Override
    public void notify(DelegateExecution execution){
        String url = fixedValue.getExpressionText();
        log.info("url:{}",url);
        HttpResponse execute = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            String accessToken = AppUserUtil.getAccessToken();
            httpPost.setHeader("Content-Type", "application/json;charset=utf8");
            httpPost.setHeader("Authorization",accessToken);

            //todo 临时的，这个dto是任务的，不是给execution用的；
            WfDelegateTaskDto wfDelegateTask = dozerMapper.map(execution, WfDelegateTaskDto.class);

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
            throw new IncloudException("");
        }finally {

        }
    }
}
