package com.netwisd.base.wf.feign;

import com.netwisd.base.wf.starter.event.ExecutionEntity;
import com.netwisd.base.wf.starter.event.TaskEntity;
import com.netwisd.base.wf.starter.expression.ExpressionEntity;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Description: 通用业务表达式
 * @Author: zouliming@netwisd.com
 * @Date: 2021/3/24 9:21 上午
 */
//其实写@FeignClient("incloud-base-wf")这个没什么意义，完全是为了注册到Spring容器中。后续使用需要用DynamicFeignClient来获取执行到对应的业务系统中；
//使用：
// @Autowired
// DynamicFeignClient<RemoteApi> client;
// client.GetFeignClient(RemoteApi.class, serviceId);
//SentinelFeign.java 54行从Spring上下文获取。
@FeignClient("incloud-base-wf")
public interface RemoteApi {
    //事件相关的通用解析 -----start-----
    @ApiOperation(value = "解析执行Task表达式", notes = "解析执行Task表达式")
    @RequestMapping(value = "/wfExpressionTask/expressionTaskParser", method = RequestMethod.POST)
    Result expressionTaskParser(@RequestBody TaskEntity taskEntity);

    @ApiOperation(value = "解析执行Execution表达式", notes = "解析执行Execution表达式")
    @RequestMapping(value = "/wfExpressionExecution/expressionExecutionParser", method = RequestMethod.POST)
    Result expressionExecutionParser(@RequestBody ExecutionEntity executionEntity);
    //事件相关的通用解析 -----end-----

    @ApiOperation(value = "表达式解析", notes = "表达式解析")
    @RequestMapping(value = "/wfExpressionCondition/expressionConditionParser", method = RequestMethod.POST)
    Result expressionConditionParser(@RequestBody ExpressionEntity expressionEntity);
    //表达式相关的通用解析 -----end-----

    //获取所有tag 相关 -----start-----
    @ApiOperation(value = "获取所有任务事件tag(方法名)", notes = "获取所有任务事件tag(方法名)")
    @RequestMapping(value = "/wfExpressionTask/getMethodsName", method = RequestMethod.POST)
    List<String> getTaskTagByServiceId();

    @ApiOperation(value = "获取所有执行事件tag(方法名)", notes = "获取所有执行事件tag(方法名)")
    @RequestMapping(value = "/wfExpressionExecution/getMethodsName", method = RequestMethod.POST)
    List<String> getExeTagByServiceId();

    @ApiOperation(value = "获取所有条件表达式tag(方法名)", notes = "获取所有条件表达式tag(方法名)")
    @RequestMapping(value = "/wfExpressionCondition/getMethodsName", method = RequestMethod.POST)
    List<String> getConditionTagByServiceId();
    //获取所有tag 相关 -----end-----

    //执行tag 相关 -----start-----
    @ApiOperation(value = "获取所有tag(方法名)", notes = "获取所有tag(方法名)")
    @RequestMapping(value = "/wfExpressionTask/expressionTaskParser", method = RequestMethod.POST)
    List<String> getMethodsNameFromTask();

    @ApiOperation(value = "获取所有tag(方法名)", notes = "获取所有tag(方法名)")
    @RequestMapping(value = "/wfExpressionExecution/expressionExecutionParser", method = RequestMethod.POST)
    List<String> getMethodsNameFromExecution();

    @ApiOperation(value = "获取所有tag(方法名)", notes = "获取所有tag(方法名)")
    @RequestMapping(value = "/wfExpressionCondition/expressionConditionParser", method = RequestMethod.POST)
    List<String> getMethodsNameFromCondition();
    //执行tag 相关 -----start-----

}
