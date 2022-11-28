package com.netwisd.biz.asset.controller.expression;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.netwisd.base.wf.starter.event.ExecutionEntity;
import com.netwisd.base.wf.starter.event.TaskEntity;
import com.netwisd.biz.asset.service.*;
import com.netwisd.common.core.constants.ResultConstants;
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


    /**
     * 购置采购登记保存前
     * @param taskEntity
     * @return
     */
    public Result purRegSaveBefore(TaskEntity taskEntity) {
        log.info("购置采购登记流程保存前:{}", taskEntity);
        String procInstId = taskEntity.getProcessInstanceId();
//        Result result = purchaseRegisterService.verifyRegisterNumberAndAmount(procInstId);
//        if(result.getCode() == ResultConstants.FAIL.code){
//            return result;
//        }
        return Result.success(purchaseRegisterService.purRegSaveBefore(procInstId));
    }

    /**
     * 购置采购登记保存前
     * @param taskEntity
     * @return
     */
    public Result purRegSaveAfter(TaskEntity taskEntity) {
        log.info("购置采购登记流程保存后:{}", taskEntity);
        Boolean result = purchaseRegisterService.purRegSaveAfter(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 购置采购登记流程结束
     * @param taskEntity
     * @return
     */
    public Result purRegAuditSuccess(TaskEntity taskEntity) {
        log.info("购置采购登记流程结束:{}", taskEntity);
        Boolean result = purchaseRegisterService.purRegAuditSuccess(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }


    /**
     * 购置验收流程保存前
     * @param taskEntity
     * @return
     */
    public Result purAcceptSaveBefore(TaskEntity taskEntity) {
        log.info("购置验收流程保存前:{}", taskEntity);
        Boolean result = purchaseAcceptService.purAcceptSaveBefore(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 购置验收流程保存后
     * @param taskEntity
     * @return
     */
    public Result purAcceptSaveAfter(TaskEntity taskEntity) {
        log.info("购置验收流程保存后:{}", taskEntity);
        Boolean result = purchaseAcceptService.purAcceptSaveAfter(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 购置验收流程结束
     * @param taskEntity
     * @return
     */
    public Result purAcceptAuditSuccess(TaskEntity taskEntity) {
        log.info("购置验收流程结束:{}", taskEntity);
        Boolean result = purchaseAcceptService.purAcceptAuditSuccess(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 流程购置入库保存前，修改申请数据
     * @param taskEntity
     * @return Result
     */
    public Result purStorageSaveBefore(TaskEntity taskEntity) {
        log.info("流程购置入库保存前:{}", taskEntity);
        Boolean result = purchaseStorageService.purStorageSaveBefore(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 流程购置入库保存后，修改申请数据
     * @param taskEntity
     * @return Result
     */
     public Result purStorageSaveAfter(TaskEntity taskEntity) {
         log.info("流程购置入库保存后:{}", taskEntity);
         Boolean result = purchaseStorageService.purStorageSaveAfter(taskEntity.getProcessInstanceId());
         return Result.success(result);
    }
    /**
     * 流程资产领用保存前，修改可用数量
     * @param taskEntity
     * @return Result
     */
    public Result materialAcceptSaveBefore(TaskEntity taskEntity) {
        log.info("流程资产领用保存前:{}", taskEntity);
        Boolean result = materialAcceptService.acceptSaveBefore(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 资产领用流程保存后
     * @param taskEntity
     * @return
     */
    public Result materialAcceptSaveAfter(TaskEntity taskEntity) {
        log.info("资产领用流程流程保存后:{}", taskEntity);
        Result result = materialAcceptService.acceptSaveAfter(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 流程资产派发保存前
     * @param taskEntity
     * @return Result
     */
    public Result materialDistributeSaveBefore(TaskEntity taskEntity) {
        log.info("流程资产派发保存前:{}", taskEntity);
        Boolean result = materialDistributeService.procSaveBefore(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 资产派发流程保存后
     * @param taskEntity
     * @return
     */
    public Result materialDistributeSaveAfter(TaskEntity taskEntity) {
        log.info("资产派发流程流程保存后:{}", taskEntity);
        Boolean result = materialDistributeService.procSaveAfter(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 资产签收流程保存前
     * @param taskEntity
     * @return Result
     */
    public Result materialSignSaveBefore(TaskEntity taskEntity) {
        log.info("资产签收流程保存前:{}", taskEntity);
        Boolean result = materialSignService.procSaveBefore(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 资产签收流程保存后
     * @param taskEntity
     * @return
     */
    public Result materialSignSaveAfter(TaskEntity taskEntity) {
        log.info("资产签收流程流程保存后:{}", taskEntity);
        Boolean result = materialSignService.procSaveAfter(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 流程资产变更保存前
     * @param taskEntity
     * @return Result
     */
    public Result materialChangeSaveBefore(TaskEntity taskEntity) {
        log.info("流程资产派发保存前:{}", taskEntity);
        Boolean result = materialChangeService.procSaveBefore(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 资产变更流程保存后
     * @param taskEntity
     * @return
     */
    public Result materialChangeSaveAfter(TaskEntity taskEntity) {
        log.info("资产派发流程流程保存后:{}", taskEntity);
        Boolean result = materialChangeService.procSaveAfter(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }


    /**
     * 购置申请发起任务
     * @param taskEntity
     * @return
     */
    public Result materialSendTask(TaskEntity taskEntity) {
        log.info("购置申请发送任务:{}", taskEntity);
        return materialAcceptService.sendTask(taskEntity.getProcessInstanceId());
    }

    /**
     * 购置申请发送派发任务
     * @param taskEntity
     * @return
     */
    public Result materialProcessTask(TaskEntity taskEntity) {
        log.info("购置申请发送派发任务:{}", taskEntity);
        Boolean result = materialAcceptService.processTask(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 流程资产退库保存前
     * @param taskEntity
     * @return
     */
    public Result materialWithdrawalSaveBefore(TaskEntity taskEntity) {
        log.info("流程资产退库保存前:{}", taskEntity);
        Boolean result = materialWithdrawalService.procSaveBefore(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 流程资产退库保存后
     * @param taskEntity
     * @return
     */
    public Result materialWithdrawalSaveAfter(TaskEntity taskEntity) {
        log.info("流程资产退库保存后:{}", taskEntity);
        Boolean result = materialWithdrawalService.procSaveAfter(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 流程报废申请保存前
     * @param taskEntity
     * @return
     */
    public Result scrapApplySaveBefore(TaskEntity taskEntity) {
        log.info("流程报废申请保存前:{}", taskEntity);
        Boolean result = scrapApplyService.procSaveBefore(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 流程报废申请保存后
     * @param taskEntity
     * @return
     */
    public Result scrapApplySaveAfter(TaskEntity taskEntity) {
        log.info("流程报废申请保存后:{}", taskEntity);
        Boolean result = scrapApplyService.procSaveAfter(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }
    /**
     * 流程报废登记保存前
     * @param taskEntity
     * @return
     */
    public Result scrapRegisterSaveBefore(TaskEntity taskEntity) {
        log.info("流程报废申请保存前:{}", taskEntity);
        Boolean result = scrapRegisterService.procSaveBefore(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }

    /**
     * 流程报废登记保存后
     * @param taskEntity
     * @return
     */
    public Result scrapRegisterSaveAfter(TaskEntity taskEntity) {
        log.info("流程报废申请保存后:{}", taskEntity);
        Boolean result = scrapRegisterService.procSaveAfter(taskEntity.getProcessInstanceId());
        return Result.success(result);
    }
}
