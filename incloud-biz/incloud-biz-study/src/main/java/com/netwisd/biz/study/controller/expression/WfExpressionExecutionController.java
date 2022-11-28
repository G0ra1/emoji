package com.netwisd.biz.study.controller.expression;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.netwisd.base.wf.starter.event.ExecutionEntity;
import com.netwisd.biz.study.entity.StudySpecialAdj;
import com.netwisd.biz.study.service.*;
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
    private StudyLessonService studyLessonService;

    @Autowired
    private StudyLessonAdjService studyLessonAdjService;

    @Autowired
    private StudySpecialService studySpecialService;

    @Autowired
    private StudySpecialAdjService specialAdjService;

    @Autowired
    private StudyExamPaperService studyExamPaperService;

    @Autowired
    private StudyExamPaperAdjService studyExamPaperAdjService;


    /**
     * 试卷新增审批完成
     *
     * @param executionEntity
     * @return
     */
    public Result procPaperAuditSuccess(ExecutionEntity executionEntity) {
        log.info("试卷新增流程审批完成:{}", executionEntity);
        return Result.success(studyExamPaperService.procAuditSuccess(executionEntity.getProcessInstanceId()));
    }

    /**
     * 试卷新增流程删除
     *
     * @param executionEntity
     * @return
     */
    public Result procPaperDelete(ExecutionEntity executionEntity) {
        log.info("试卷新增流程删除:{}", executionEntity);
        return Result.success(studyExamPaperService.procDelete(executionEntity.getProcessInstanceId()));
    }
    /**
     * 试卷调整流程审批完成
     *
     * @param executionEntity
     * @return
     */
    public Result procPaperAdjAuditSuccess(ExecutionEntity executionEntity) {
        log.info("试卷调整流程审批完成:{}", executionEntity);
        return Result.success(studyExamPaperAdjService.procAuditSuccess(executionEntity.getProcessInstanceId()));
    }

    /**
     * 试卷调整流程删除
     *
     * @param executionEntity
     * @return
     */
    public Result procPaperAdjDelete(ExecutionEntity executionEntity) {
        log.info("试卷调整流程删除:{}", executionEntity);
        return Result.success(studyExamPaperAdjService.procDelete(executionEntity.getProcessInstanceId()));
    }
    /**
     * 专题审批完成
     *
     * @param executionEntity
     * @return
     */
    public Result procSpecialAuditSuccess(ExecutionEntity executionEntity) {
        log.info("专题申请流程审批完成:{}", executionEntity);
        return Result.success(studySpecialService.procAuditSuccess(executionEntity.getProcessInstanceId()));
    }

    /**
     * 专题申请流程删除
     *
     * @param executionEntity
     * @return
     */
    public Result procSpecialDelete(ExecutionEntity executionEntity) {
        log.info("专题申请流程删除:{}", executionEntity);
        return Result.success(studySpecialService.procDelete(executionEntity.getProcessInstanceId()));
    }

    /**
     * 专题调整审批完成
     *
     * @param executionEntity
     * @return
     */
    public Result procSpecialAdjAuditSuccess(ExecutionEntity executionEntity) {
        log.info("专题申请流程审批完成:{}", executionEntity);
        return Result.success(specialAdjService.procAuditSuccess(executionEntity.getProcessInstanceId()));
    }

    /**
     * 专题调整申请流程删除
     *
     * @param executionEntity
     * @return
     */
    public Result procSpecialAdjDelete(ExecutionEntity executionEntity) {
        log.info("专题申请流程删除:{}", executionEntity);
        return Result.success(specialAdjService.procDelete(executionEntity.getProcessInstanceId()));
    }

    /**
     * 课程新增审批完成
     *
     * @param executionEntity
     * @return
     */
    public Result procLessonAuditSuccess(ExecutionEntity executionEntity) {
        log.info("课程新增流程审批完成:{}", executionEntity);
        return Result.success(studyLessonService.procAuditSuccess(executionEntity.getProcessInstanceId()));
    }

    /**
     * 课程新增流程删除
     *
     * @param executionEntity
     * @return
     */
    public Result procLessonDelete(ExecutionEntity executionEntity) {
        log.info("课程新增流程删除:{}", executionEntity);
        return Result.success(studyLessonService.procDelete(executionEntity.getProcessInstanceId()));
    }

    /**
     * 课程调整流程审批完成
     *
     * @param executionEntity
     * @return
     */
    public Result procLessonAdjAuditSuccess(ExecutionEntity executionEntity) {
        log.info("课程调整流程审批完成:{}", executionEntity);
        return Result.success(studyLessonAdjService.procAuditSuccess(executionEntity.getProcessInstanceId()));
    }

    /**
     * 课程调整流程删除
     *
     * @param executionEntity
     * @return
     */
    public Result procLessonAdjDelete(ExecutionEntity executionEntity) {
        log.info("课程调整流程删除:{}", executionEntity);
        return Result.success(studyLessonAdjService.procDelete(executionEntity.getProcessInstanceId()));
    }

    @ApiOperation(value = "执行表达式", notes = "执行表达式", hidden = true)
    @RequestMapping(value = "/expressionExecutionParser", method = RequestMethod.POST)
    public <T> Result<T> expressionExecutionParser(@RequestBody ExecutionEntity executionEntity) {
        log.info("UserExpressionController expressionEntity:{}", executionEntity);
        String methodName = executionEntity.getBizTag();
        int methodIndex = access.getIndex(methodName);
        return Result.success((T) access.invoke(this, methodIndex, executionEntity));
    }

    @ApiOperation(value = "获取表达式接口", notes = "获取表达式接口", hidden = true)
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
