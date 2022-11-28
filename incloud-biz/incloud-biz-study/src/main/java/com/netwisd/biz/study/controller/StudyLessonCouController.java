package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyLessonCouDto;
import com.netwisd.biz.study.vo.StudyLessonCouVo;
import com.netwisd.biz.study.service.StudyLessonCouService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 课程课件表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:20:24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyLessonCou" )
@Api(value = "studyLessonCou", tags = "课程课件表Controller")
@Slf4j
public class StudyLessonCouController {

    private final  StudyLessonCouService studyLessonCouService;

    /**
     * 分页查询课程课件表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyLessonCouDto 课程课件表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyLessonCouDto studyLessonCouDto) {
        Page pageVo = studyLessonCouService.list(studyLessonCouDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询课程课件表
     * @param studyLessonCouDto 课程课件表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody StudyLessonCouDto studyLessonCouDto) {
        Page pageVo = studyLessonCouService.lists(studyLessonCouDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询课程课件表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyLessonCouVo> get(@PathVariable("id" ) Long id) {
        StudyLessonCouVo studyLessonCouVo = studyLessonCouService.get(id);
        log.debug("查询成功");
        return Result.success(studyLessonCouVo);
    }

    /**
     * 新增课程课件表
     * @param studyLessonCouDto 课程课件表
     * @return Result
     */
    @ApiOperation(value = "新增课程课件表", notes = "新增课程课件表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyLessonCouDto studyLessonCouDto) {
        Boolean result = studyLessonCouService.save(studyLessonCouDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改课程课件表
     * @param studyLessonCouDto 课程课件表
     * @return Result
     */
    @ApiOperation(value = "修改课程课件表", notes = "修改课程课件表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyLessonCouDto studyLessonCouDto) {
        Boolean result = studyLessonCouService.update(studyLessonCouDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除课程课件表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除课程课件表", notes = "通过id删除课程课件表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = studyLessonCouService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
