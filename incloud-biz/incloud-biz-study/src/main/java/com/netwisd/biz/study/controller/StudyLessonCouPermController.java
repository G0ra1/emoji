package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyLessonCouPermDto;
import com.netwisd.biz.study.vo.StudyLessonCouPermVo;
import com.netwisd.biz.study.service.StudyLessonCouPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 课程课件授权表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-22 11:13:20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyLessonCouPerm" )
@Api(value = "studyLessonCouPerm", tags = "课程课件授权表Controller")
@Slf4j
public class StudyLessonCouPermController {

    private final  StudyLessonCouPermService studyLessonCouPermService;

    /**
     * 分页查询课程课件授权表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyLessonCouPermDto 课程课件授权表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyLessonCouPermDto studyLessonCouPermDto) {
        Page pageVo = studyLessonCouPermService.list(studyLessonCouPermDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询课程课件授权表
     * @param studyLessonCouPermDto 课程课件授权表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody StudyLessonCouPermDto studyLessonCouPermDto) {
        Page pageVo = studyLessonCouPermService.lists(studyLessonCouPermDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询课程课件授权表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyLessonCouPermVo> get(@PathVariable("id" ) Long id) {
        StudyLessonCouPermVo studyLessonCouPermVo = studyLessonCouPermService.get(id);
        log.debug("查询成功");
        return Result.success(studyLessonCouPermVo);
    }

    /**
     * 新增课程课件授权表
     * @param studyLessonCouPermDto 课程课件授权表
     * @return Result
     */
    @ApiOperation(value = "新增课程课件授权表", notes = "新增课程课件授权表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyLessonCouPermDto studyLessonCouPermDto) {
        Boolean result = studyLessonCouPermService.save(studyLessonCouPermDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改课程课件授权表
     * @param studyLessonCouPermDto 课程课件授权表
     * @return Result
     */
    @ApiOperation(value = "修改课程课件授权表", notes = "修改课程课件授权表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyLessonCouPermDto studyLessonCouPermDto) {
        Boolean result = studyLessonCouPermService.update(studyLessonCouPermDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除课程课件授权表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除课程课件授权表", notes = "通过id删除课程课件授权表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = studyLessonCouPermService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
