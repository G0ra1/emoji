package com.netwisd.biz.study.controller;

import com.netwisd.biz.study.dto.StudyLessonAdjDto;
import com.netwisd.biz.study.service.StudyLessonAdjService;
import com.netwisd.biz.study.vo.StudyLessonAdjVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程调整申请 功能描述...
 * @date 2022-05-12 09:17:24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyLessonAdj")
@Api(value = "studyLessonAdj", tags = "课程调整申请Controller")
@Slf4j
public class StudyLessonAdjController {

    private final StudyLessonAdjService studyLessonAdjService;

    /**
     * 获取课程的调整记录
     *
     * @param lessonId
     * @return
     */
    @ApiOperation(value = "获取课程的调整记录", notes = "获取课程的调整记录")
    @GetMapping("/adjListFor/{lessonId}")
    public Result<List<StudyLessonAdjVo>> adjListForLesson(@PathVariable("lessonId") Long lessonId) {
        return Result.success(studyLessonAdjService.adjListForLesson(lessonId));
    }

    /**
     * 通过id查询课程详情
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询详情", notes = "通过id查询详情")
    @GetMapping("/{id}")
    public Result<StudyLessonAdjVo> detail(@PathVariable("id") Long id) {
        return Result.success(studyLessonAdjService.detail(id));
    }

    /**
     * 流程新增或修改
     *
     * @param studyLessonAdjDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<StudyLessonAdjVo> procSaveOrUpdate(@RequestBody StudyLessonAdjDto studyLessonAdjDto) {
        return Result.success(studyLessonAdjService.procSaveOrUpdate(studyLessonAdjDto));
    }

    /**
     * 流程回显获取详情
     *
     * @param procInstId procInstId
     * @return
     */
    @ApiOperation(value = "流程回显获取详情", notes = "流程回显获取详情")
    @GetMapping("/procDetail/{procInstId}")
    public Result<StudyLessonAdjVo> procDetail(@PathVariable String procInstId) {
        return Result.success(studyLessonAdjService.procDetail(procInstId));
    }

    /**
     * 课程调整删除
     *
     * @param lessonAdjId
     * @return
     */
    @ApiOperation(value = "课程调整删除", notes = "课程调整删除")
    @DeleteMapping("/delete/{lessonAdjId}")
    public Result<Boolean> delete(@PathVariable Long lessonAdjId) {
        return Result.success(studyLessonAdjService.delete(lessonAdjId));
    }
}
