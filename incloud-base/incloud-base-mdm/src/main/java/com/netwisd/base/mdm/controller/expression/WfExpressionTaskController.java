package com.netwisd.base.mdm.controller.expression;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.netwisd.base.wf.starter.event.TaskEntity;
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
 * @Description 业务任务事件总入口
 * @author 云数网讯 XHL
 * @date 2022-02-24 10:33:43
 */
@Slf4j
@RestController
@RequestMapping("/wfExpressionTask")
public class WfExpressionTaskController {

    private static MethodAccess access = MethodAccess.get(WfExpressionTaskController.class);

    private static List<String> excludeParamterList = Arrays.asList("expressionTaskParser","getMethodsName");

    @ApiOperation(value = "测试方法", notes = "测试方法")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Result<Boolean> test(@RequestBody TaskEntity taskEntity) {
        System.out.println("this.is.test------------------------------------------");
        log.info("taskEntity:{}",taskEntity);
        return Result.success(true);
    }

    @ApiOperation(value = "测试方法", notes = "测试方法")
    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    public Result<Integer> test2(@RequestBody TaskEntity taskEntity) {
        System.out.println("this.is.test222222------------------------------------------");
        log.info("taskEntity:{}",taskEntity);
        return Result.success(123);
    }

    @ApiOperation(value = "执行表达式", notes = "执行表达式", hidden = true)
    @RequestMapping(value = "/expressionTaskParser", method = RequestMethod.POST)
    public <T> Result<T> expressionTaskParser(@RequestBody TaskEntity taskEntity) {
        log.info("UserExpressionController expressionEntity:{}", taskEntity);
        String methodName = taskEntity.getBizTag();
        int methodIndex = access.getIndex(methodName);
        return Result.success( (T) access.invoke(this, methodIndex, taskEntity));
    }

    @ApiOperation(value = "执行表达式", notes = "执行表达式", hidden = true)
    @RequestMapping(value = "/getMethodsName", method = RequestMethod.POST)
    public List<String> getMethodsName(){
        List<String> paramterList = new ArrayList<>();
        Method[] methods = WfExpressionTaskController.class.getDeclaredMethods();
        for (Method method : methods) {
            paramterList.add(method.getName());
        }
        //排出无用的方法
        paramterList.removeAll(excludeParamterList);
        return paramterList;
    }
}
