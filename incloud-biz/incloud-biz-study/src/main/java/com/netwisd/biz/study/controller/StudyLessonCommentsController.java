package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyLessonCommentsDto;
import com.netwisd.biz.study.vo.StudyLessonCommentsVo;
import com.netwisd.biz.study.service.StudyLessonCommentsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 课程评论表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:18:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyLessonComments" )
@Api(value = "studyLessonComments", tags = "课程评论表Controller")
@Slf4j
public class StudyLessonCommentsController {

    private final  StudyLessonCommentsService studyLessonCommentsService;

    /**
     * 新增课程评论表
     * @param studyLessonCommentsDto 课程评论表
     * @return Result
     */
    @ApiOperation(value = "新增课程评论表", notes = "新增课程评论表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyLessonCommentsDto studyLessonCommentsDto) {
        Boolean result = studyLessonCommentsService.save(studyLessonCommentsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改课程评论表
     * @param studyLessonCommentsDto 课程评论表
     * @return Result
     */
    @ApiOperation(value = "修改课程评论表", notes = "修改课程评论表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyLessonCommentsDto studyLessonCommentsDto) {
        Boolean result = studyLessonCommentsService.update(studyLessonCommentsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除课程评论表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除课程评论表", notes = "通过id删除课程评论表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = studyLessonCommentsService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
