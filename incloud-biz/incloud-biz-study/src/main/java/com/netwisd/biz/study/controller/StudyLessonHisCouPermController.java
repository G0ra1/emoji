package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyLessonHisCouPermDto;
import com.netwisd.biz.study.vo.StudyLessonHisCouPermVo;
import com.netwisd.biz.study.service.StudyLessonHisCouPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 课程课件授权历史表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-23 21:53:32
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyLessonHisCouPerm" )
@Api(value = "studyLessonHisCouPerm", tags = "课程课件授权历史表Controller")
@Slf4j
public class StudyLessonHisCouPermController {

    private final  StudyLessonHisCouPermService studyLessonHisCouPermService;

    /**
     * 分页查询课程课件授权历史表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyLessonHisCouPermDto 课程课件授权历史表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyLessonHisCouPermDto studyLessonHisCouPermDto) {
        Page pageVo = studyLessonHisCouPermService.list(studyLessonHisCouPermDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询课程课件授权历史表
     * @param studyLessonHisCouPermDto 课程课件授权历史表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody StudyLessonHisCouPermDto studyLessonHisCouPermDto) {
        Page pageVo = studyLessonHisCouPermService.lists(studyLessonHisCouPermDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询课程课件授权历史表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyLessonHisCouPermVo> get(@PathVariable("id" ) Long id) {
        StudyLessonHisCouPermVo studyLessonHisCouPermVo = studyLessonHisCouPermService.get(id);
        log.debug("查询成功");
        return Result.success(studyLessonHisCouPermVo);
    }

    /**
     * 新增课程课件授权历史表
     * @param studyLessonHisCouPermDto 课程课件授权历史表
     * @return Result
     */
    @ApiOperation(value = "新增课程课件授权历史表", notes = "新增课程课件授权历史表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyLessonHisCouPermDto studyLessonHisCouPermDto) {
        Boolean result = studyLessonHisCouPermService.save(studyLessonHisCouPermDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改课程课件授权历史表
     * @param studyLessonHisCouPermDto 课程课件授权历史表
     * @return Result
     */
    @ApiOperation(value = "修改课程课件授权历史表", notes = "修改课程课件授权历史表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyLessonHisCouPermDto studyLessonHisCouPermDto) {
        Boolean result = studyLessonHisCouPermService.update(studyLessonHisCouPermDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除课程课件授权历史表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除课程课件授权历史表", notes = "通过id删除课程课件授权历史表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = studyLessonHisCouPermService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
