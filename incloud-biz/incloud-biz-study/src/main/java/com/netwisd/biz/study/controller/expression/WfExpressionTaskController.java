package com.netwisd.biz.study.controller.expression;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.netwisd.base.wf.starter.event.TaskEntity;
import com.netwisd.biz.study.entity.StudySpecial;
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
 * @Description 业务任务事件总入口
 * @date 2022-02-24 10:33:43
 */
@Slf4j
@RestController
@RequestMapping("/wfExpressionTask")
public class WfExpressionTaskController {

    private static MethodAccess access = MethodAccess.get(WfExpressionTaskController.class);

    private static List<String> excludeParamterList = Arrays.asList("expressionTaskParser", "getMethodsName");

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
     * 课程新增流程提交
     *
     * @param taskEntity
     * @return
     */
    public Result lessonAfterSubmit(TaskEntity taskEntity) {
        log.info("课程新增流程提交:{}", taskEntity);
        return Result.success(studyLessonService.procAfterSubmit(taskEntity.getProcessInstanceId()));
    }
    /**
     * 专题新增流程提交
     *
     * @param taskEntity
     * @return
     */
    public Result specialAfterSubmit(TaskEntity taskEntity) {
        log.info("专题新增流程提交:{}", taskEntity);
        return Result.success(studySpecialService.procAfterSubmit(taskEntity.getProcessInstanceId()));
    }
    /**
     * 专题调整新增流程提交
     *
     * @param taskEntity
     * @return
     */
    public Result specialAdjAfterSubmit(TaskEntity taskEntity) {
        log.info("专题调整新增流程提交:{}", taskEntity);
        return Result.success(specialAdjService.procAfterSubmit(taskEntity.getProcessInstanceId()));
    }
    /**
     * 课程调整流程提交
     *
     * @param taskEntity
     * @return
     */
    public Result lessonAdjAfterSubmit(TaskEntity taskEntity) {
        log.info("课程调整流程提交:{}", taskEntity);
        return Result.success(studyLessonAdjService.procAfterSubmit(taskEntity.getProcessInstanceId()));
    }

    /**
     * 试卷新增流程提交
     *
     * @param taskEntity
     * @return
     */
    public Result paperAfterSubmit(TaskEntity taskEntity) {
        log.info("试卷新增流程提交:{}", taskEntity);
        return Result.success(studyExamPaperService.procAfterSubmit(taskEntity.getProcessInstanceId()));
    }

    /**
     * 试卷调整流程提交
     *
     * @param taskEntity
     * @return
     */
    public Result paperAdjAfterSubmit(TaskEntity taskEntity) {
        log.info("试卷调整流程提交:{}", taskEntity);
        return Result.success(studyExamPaperAdjService.procAfterSubmit(taskEntity.getProcessInstanceId()));
    }
    @ApiOperation(value = "执行表达式", notes = "执行表达式", hidden = true)
    @RequestMapping(value = "/expressionTaskParser", method = RequestMethod.POST)
    public <T> Result<T> expressionTaskParser(@RequestBody TaskEntity taskEntity) {
        log.info("UserExpressionController expressionEntity:{}", taskEntity);
        String methodName = taskEntity.getBizTag();
        int methodIndex = access.getIndex(methodName);
        return Result.success((T) access.invoke(this, methodIndex, taskEntity));
    }

    @ApiOperation(value = "获取表达式接口", notes = "获取表达式接口", hidden = true)
    @RequestMapping(value = "/getMethodsName", method = RequestMethod.POST)
    public List<String> getMethodsName() {
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
