package com.netwisd.base.mdm.controller.expression;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.netwisd.base.wf.starter.expression.ExpressionEntity;
import com.netwisd.base.wf.starter.expression.HandleExpressionParam;
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
 * @Description 工作流条件表达式总入口
 * @author 云数网讯 XHL
 * @date 2022-02-24 10:33:43
 */
@Slf4j
@RestController
@RequestMapping("/wfExpressionCondition")
public class WfExpressionConditionController {

    private static MethodAccess access = MethodAccess.get(WfExpressionConditionController.class);

    private static List<String> excludeParamterList = Arrays.asList("expressionConditionParser","getMethodsName");

    @ApiOperation(value = "测试方法", notes = "测试方法")
    @RequestMapping(value = "/test5", method = RequestMethod.POST)
    public Boolean test5(@RequestBody ExpressionEntity expressionEntity) {
        System.out.println("this.is.test.");
        return true;
    }

    @ApiOperation(value = "测试方法", notes = "测试方法")
    @RequestMapping(value = "/test6", method = RequestMethod.POST)
    public Integer test6(@RequestBody ExpressionEntity expressionEntity) {
        log.info("expressionEntity:{}",expressionEntity);
        return 123;
    }

    @ApiOperation(value = "执行表达式", notes = "执行表达式", hidden = true)
    @RequestMapping(value = "/expressionConditionParser", method = RequestMethod.POST)
    public <T> Result<T> expressionConditionParser(@RequestBody ExpressionEntity expressionEntity) {
        log.info("WfExpressionConditionController expressionConditionParser:{}", expressionEntity);
        String methodName = expressionEntity.getBizTag();
        List<HandleExpressionParam> argList = expressionEntity.getArgList();
//        List<Object> list = new ArrayList<>();
//        for (HandleExpressionParam handleExpressionParam : argList){
//            list.add(handleExpressionParam.getParamValue());
//        }
        int methodIndex = access.getIndex(methodName);
        return Result.success( (T) access.invoke(this, methodIndex, expressionEntity));
    }

    @ApiOperation(value = "执行表达式", notes = "执行表达式", hidden = true)
    @RequestMapping(value = "/getMethodsName", method = RequestMethod.POST)
    public List<String> getMethodsName(){
        List<String> paramterList = new ArrayList<>();
        Method[] methods = WfExpressionConditionController.class.getDeclaredMethods();
        for (Method method : methods) {
            paramterList.add(method.getName());
        }
        //排出无用的方法
        paramterList.removeAll(excludeParamterList);
        return paramterList;
    }
}
