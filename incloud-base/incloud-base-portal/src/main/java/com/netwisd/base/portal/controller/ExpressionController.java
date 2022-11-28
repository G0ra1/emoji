package com.netwisd.base.portal.controller;

import com.netwisd.base.wf.starter.expression.ExpressionEntity;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description demo_app测试类 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-04-21 10:33:43
 */
@RestController
@AllArgsConstructor
@Slf4j
public class ExpressionController {

//    @Autowired
//    ExpenseMatterOutsourceingService expenseMatterOutsourceingService;
//
//    @Autowired
//    ExpenseMatterMeetingService expenseMatterMeetingService;

    @ApiOperation(value = "表达式解析", notes = "表达式解析")
    @RequestMapping(value = "/expressionParser", method = RequestMethod.POST)
    Result expressionParser(@RequestBody ExpressionEntity expressionEntity){
        if(expressionEntity.getBizTag().equals("TEST")) {
//            ExpenseMatterOutsourceing expenseMatterOutsourceing = expenseMatterOutsourceingService.queryByProcinsId(expressionEntity.getProcessInstanceId());
//            Map map = expressionEntity.getArgs();
//            String applyUserId = (String) map.get("userId");
//            if(expenseMatterOutsourceing.getApplyUserId().equals(applyUserId)) {
//                return Result.success(true);
//            } else {
//                return Result.success(false);
//            }
        }

//        if(expressionEntity.getBizTag().equals("T1")) {
//            ExpenseMatterMeeting expenseMatterMeeting = expenseMatterMeetingService.queryByProcinsId(expressionEntity.getProcessInstanceId());
//            return Result.success(expenseMatterMeeting.getSumBudgetAmount());
//        }
        return Result.success(false);
    }


}
