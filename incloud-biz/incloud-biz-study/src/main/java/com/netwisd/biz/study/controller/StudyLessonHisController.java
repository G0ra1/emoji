package com.netwisd.biz.study.controller;

import com.netwisd.biz.study.service.StudyLessonHisService;
import com.netwisd.biz.study.vo.StudyLessonHisVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 课程历史 功能描述...
 * @date 2022-05-11 18:50:02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyLessonHis")
@Api(value = "studyLessonHis", tags = "课程历史Controller")
@Slf4j
public class StudyLessonHisController {

    private final StudyLessonHisService studyLessonHisService;

    /**
     * 获取课程的历史记录
     *
     * @param lessonId
     * @return
     */
    @ApiOperation(value = "获取课程的历史记录", notes = "获取课程的历史记录")
    @GetMapping("/hisListFor/{lessonId}")
    public Result<List<StudyLessonHisVo>> hisListForLesson(@PathVariable("lessonId") Long lessonId) {
        return Result.success(studyLessonHisService.hisListForLesson(lessonId));
    }

    /**
     * 通过id查询课程历史详情
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询详情", notes = "通过id查询详情")
    @GetMapping("/{id}")
    public Result<StudyLessonHisVo> detail(@PathVariable("id") Long id) {
        return Result.success(studyLessonHisService.detail(id));
    }
}
