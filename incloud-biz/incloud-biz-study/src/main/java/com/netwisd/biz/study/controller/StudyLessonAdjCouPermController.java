package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyLessonAdjCouPermDto;
import com.netwisd.biz.study.vo.StudyLessonAdjCouPermVo;
import com.netwisd.biz.study.service.StudyLessonAdjCouPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 课程课件授权调整表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-23 21:54:14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyLessonAdjCouPerm" )
@Api(value = "studyLessonAdjCouPerm", tags = "课程课件授权调整表Controller")
@Slf4j
public class StudyLessonAdjCouPermController {

    private final  StudyLessonAdjCouPermService studyLessonAdjCouPermService;

    /**
     * 分页查询课程课件授权调整表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyLessonAdjCouPermDto 课程课件授权调整表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyLessonAdjCouPermDto studyLessonAdjCouPermDto) {
        Page pageVo = studyLessonAdjCouPermService.list(studyLessonAdjCouPermDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询课程课件授权调整表
     * @param studyLessonAdjCouPermDto 课程课件授权调整表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody StudyLessonAdjCouPermDto studyLessonAdjCouPermDto) {
        Page pageVo = studyLessonAdjCouPermService.lists(studyLessonAdjCouPermDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询课程课件授权调整表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyLessonAdjCouPermVo> get(@PathVariable("id" ) Long id) {
        StudyLessonAdjCouPermVo studyLessonAdjCouPermVo = studyLessonAdjCouPermService.get(id);
        log.debug("查询成功");
        return Result.success(studyLessonAdjCouPermVo);
    }

    /**
     * 新增课程课件授权调整表
     * @param studyLessonAdjCouPermDto 课程课件授权调整表
     * @return Result
     */
    @ApiOperation(value = "新增课程课件授权调整表", notes = "新增课程课件授权调整表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyLessonAdjCouPermDto studyLessonAdjCouPermDto) {
        Boolean result = studyLessonAdjCouPermService.save(studyLessonAdjCouPermDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改课程课件授权调整表
     * @param studyLessonAdjCouPermDto 课程课件授权调整表
     * @return Result
     */
    @ApiOperation(value = "修改课程课件授权调整表", notes = "修改课程课件授权调整表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyLessonAdjCouPermDto studyLessonAdjCouPermDto) {
        Boolean result = studyLessonAdjCouPermService.update(studyLessonAdjCouPermDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除课程课件授权调整表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除课程课件授权调整表", notes = "通过id删除课程课件授权调整表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = studyLessonAdjCouPermService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
