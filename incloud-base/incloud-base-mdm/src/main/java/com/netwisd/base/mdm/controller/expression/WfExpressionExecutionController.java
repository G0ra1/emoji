package com.netwisd.base.mdm.controller.expression;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.netwisd.base.wf.starter.event.ExecutionEntity;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 云数网讯 XHL
 * @Description 业务执行事件总入口
 * @date 2022-02-24 10:33:43
 */
@Slf4j
@RestController
@RequestMapping("/wfExpressionExecution")
public class WfExpressionExecutionController {

    private static MethodAccess access = MethodAccess.get(WfExpressionExecutionController.class);

    private static List<String> excludeParamterList = Arrays.asList("expressionExecutionParser", "getMethodsName");

    @ApiOperation(value = "测试流程删除会议", notes = "测试流程删除会议")
    @RequestMapping(value = "/delMeet", method = RequestMethod.POST)
    public Result delMeet(@RequestBody ExecutionEntity executionEntity) {
        System.out.println("this.is.delMeet---------测试流程删除会议---------------------------------instId:" + executionEntity.getProcessInstanceId());
        return Result.success();
    }

    @ApiOperation(value = "测试流程删除实例", notes = "测试流程删除实例")
    @RequestMapping(value = "/delIns", method = RequestMethod.POST)
    public Result delIns(@RequestBody ExecutionEntity executionEntity) {
        System.out.println("this.is.delIns---------测试流程删除实例---------------------------------instId:" + executionEntity.getProcessInstanceId());
        return Result.success();
    }

    @ApiOperation(value = "测试方法", notes = "测试方法")
    @RequestMapping(value = "/test4", method = RequestMethod.POST)
    public Result<Integer> test4(@RequestBody ExecutionEntity executionEntity) {
        System.out.println("this.is.test222222------------------------------------------");
        log.info("taskEntity:{}", executionEntity);
        return Result.success(123);
    }

    @ApiOperation(value = "执行表达式", notes = "执行表达式", hidden = true)
    @RequestMapping(value = "/expressionExecutionParser", method = RequestMethod.POST)
    public <T> Result<T> expressionExecutionParser(@RequestBody ExecutionEntity executionEntity) {
        log.info("UserExpressionController expressionEntity:{}", executionEntity);
        String methodName = executionEntity.getBizTag();
        int methodIndex = access.getIndex(methodName);
        return Result.success((T) access.invoke(this, methodIndex, executionEntity));
    }

    @ApiOperation(value = "执行表达式", notes = "执行表达式", hidden = true)
    @RequestMapping(value = "/getMethodsName", method = RequestMethod.POST)
    public List<String> getMethodsName() {
        List<String> paramterList = new ArrayList<>();
        Method[] methods = WfExpressionExecutionController.class.getDeclaredMethods();
        for (Method method : methods) {
            paramterList.add(method.getName());
        }
        //排出无用的方法
        paramterList.removeAll(excludeParamterList);
        return paramterList;
    }
}
