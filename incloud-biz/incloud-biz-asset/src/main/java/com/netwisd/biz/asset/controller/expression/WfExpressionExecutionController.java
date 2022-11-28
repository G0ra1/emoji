package com.netwisd.biz.asset.controller.expression;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.netwisd.base.wf.starter.event.ExecutionEntity;
import com.netwisd.biz.asset.service.*;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private PurchaseApplyService purchaseApplyService;

    @Autowired
    private PurchaseRegisterService purchaseRegisterService;

    @Autowired
    private PurchaseAcceptService purchaseAcceptService;

    @Autowired
    private PurchaseStorageService purchaseStorageService;

    @Autowired
    private MaterialAcceptService materialAcceptService;

    @Autowired
    private MaterialStorageService materialStorageService;

    @Autowired
    private MaterialDeliveryService materialDeliveryService;

    @Autowired
    private MaterialDistributeService materialDistributeService;

    @Autowired
    private MaterialSignService materialSignService;

    @Autowired
    private MaterialChangeService materialChangeService;

    @Autowired
    private MaterialWithdrawalService materialWithdrawalService;

    @Autowired
    private ScrapApplyService scrapApplyService;

    @Autowired
    private ScrapRegisterService scrapRegisterService;

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


    /**
     * 购置申请流程结束
     * @param executionEntity
     * @return
     */
    public Result purApplyAuditSuccess(ExecutionEntity executionEntity) {
        log.info("购置申请流程结束:{}", executionEntity);
        Boolean result = purchaseApplyService.purApplyAuditSuccess(executionEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 购置采购登记流程结束
     * @param executionEntity
     * @return
     */
    public Result purRegAuditSuccess(ExecutionEntity executionEntity) {
        log.info("购置采购登记流程结束:{}", executionEntity);
        Boolean result = purchaseRegisterService.purRegAuditSuccess(executionEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 购置验收流程结束
     * @param executionEntity
     * @return
     */
    public Result purAcceptAuditSuccess(ExecutionEntity executionEntity) {
        log.info("购置验收流程结束:{}", executionEntity);
        Boolean result = purchaseAcceptService.purAcceptAuditSuccess(executionEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 购置入库流程结束
     * @param executionEntity
     * @return
     */
    public Result purStorageAuditSuccess(ExecutionEntity executionEntity) {
        log.info("购置入库流程结束:{}", executionEntity);
        Boolean result = purchaseStorageService.purStorageAuditSuccess(executionEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 物资领用流程结束
     * @param executionEntity
     * @return
     */
    public Result acceptAuditSuccess(ExecutionEntity executionEntity) {
        log.info("资产领用流程结束:{}", executionEntity);
        Boolean result = materialAcceptService.acceptAuditSuccess(executionEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 物资领用流程结束--发起派发
     * @param executionEntity
     * @return
     */
    public Result materialSendTask(ExecutionEntity executionEntity) {
        log.info("资产领用流程结束--发起派发任务:{}", executionEntity);
        Boolean result = materialAcceptService.sendDistriButeTask(executionEntity.getProcessInstanceId());
        return Result.success(result);
    }


    /**
     * 资产派发流程结束
     * @param executionEntity
     * @return
     */
    public Result materialDistributeAuditSuccess(ExecutionEntity executionEntity) {
        log.info("资产派发流程结束:{}", executionEntity);
        Boolean result = materialDistributeService.procAuditSuccess(executionEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 签收流程结束
     * @param executionEntity
     * @return Result
     */
    public Result materialSignAuditSuccess(ExecutionEntity executionEntity) {
        log.info("签收流程结束:{}", executionEntity);
        Boolean result = materialSignService.procAuditSuccess(executionEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 使用人变更流程结束
     * @param executionEntity
     * @return Result
     */
    public Result materialChangeAuditSuccess(ExecutionEntity executionEntity) {
        log.info("使用人变更流程结束:{}", executionEntity);
        Boolean result = materialChangeService.procAuditSuccess(executionEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 退库流程结束
     * @param executionEntity
     * @return Result
     */
    public Result materialWithdrawalAuditSuccess(ExecutionEntity executionEntity) {
        log.info("退库流程结束:{}", executionEntity);
        Boolean result = materialWithdrawalService.procAuditSuccess(executionEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 报废申请流程结束
     * @param executionEntity
     * @return Result
     */
    public Result scrapApplyAuditSuccess(ExecutionEntity executionEntity) {
        log.info("报废申请流程结束:{}", executionEntity);
        Boolean result = scrapApplyService.procAuditSuccess(executionEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 报废登记流程结束
     * @param executionEntity
     * @return Result
     */
    public Result scrapRegisterAuditSuccess(ExecutionEntity executionEntity) {
        log.info("报废申请流程结束:{}", executionEntity);
        Boolean result = scrapRegisterService.procAuditSuccess(executionEntity.getProcessInstanceId());
        return Result.success(result);
    }
}
