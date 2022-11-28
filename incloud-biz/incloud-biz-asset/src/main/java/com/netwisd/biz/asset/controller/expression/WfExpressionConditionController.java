package com.netwisd.biz.asset.controller.expression;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.netwisd.base.wf.starter.event.ExecutionEntity;
import com.netwisd.base.wf.starter.expression.ExpressionEntity;
import com.netwisd.base.wf.starter.expression.HandleExpressionParam;
import com.netwisd.biz.asset.service.PurchaseStorageService;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PurchaseStorageService purchaseStorageService;

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

    /**
     * 购置入库草稿流程判断
     * @param expressionEntity
     * @return
     */
    public Boolean purStorageDraft(ExpressionEntity expressionEntity) {
        log.info("购置入库条件判断：{}", expressionEntity);
        Boolean result = purchaseStorageService.isEndByDraft(expressionEntity);
        log.info("购置入库条件判断结果：{}", result);
        return result;
    }
}
