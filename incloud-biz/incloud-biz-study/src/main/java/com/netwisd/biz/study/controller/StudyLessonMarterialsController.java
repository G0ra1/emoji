package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyLessonMarterialsDto;
import com.netwisd.biz.study.vo.StudyLessonMarterialsVo;
import com.netwisd.biz.study.service.StudyLessonMarterialsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 课程学习资料表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:21:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyLessonMarterials" )
@Api(value = "studyLessonMarterials", tags = "课程学习资料表Controller")
@Slf4j
public class StudyLessonMarterialsController {

    private final  StudyLessonMarterialsService studyLessonMarterialsService;

    /**
     * 分页查询课程学习资料表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyLessonMarterialsDto 课程学习资料表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyLessonMarterialsDto studyLessonMarterialsDto) {
        Page pageVo = studyLessonMarterialsService.list(studyLessonMarterialsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询课程学习资料表
     * @param studyLessonMarterialsDto 课程学习资料表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody StudyLessonMarterialsDto studyLessonMarterialsDto) {
        Page pageVo = studyLessonMarterialsService.lists(studyLessonMarterialsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询课程学习资料表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyLessonMarterialsVo> get(@PathVariable("id" ) Long id) {
        StudyLessonMarterialsVo studyLessonMarterialsVo = studyLessonMarterialsService.get(id);
        log.debug("查询成功");
        return Result.success(studyLessonMarterialsVo);
    }

    /**
     * 新增课程学习资料表
     * @param studyLessonMarterialsDto 课程学习资料表
     * @return Result
     */
    @ApiOperation(value = "新增课程学习资料表", notes = "新增课程学习资料表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyLessonMarterialsDto studyLessonMarterialsDto) {
        Boolean result = studyLessonMarterialsService.save(studyLessonMarterialsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改课程学习资料表
     * @param studyLessonMarterialsDto 课程学习资料表
     * @return Result
     */
    @ApiOperation(value = "修改课程学习资料表", notes = "修改课程学习资料表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyLessonMarterialsDto studyLessonMarterialsDto) {
        Boolean result = studyLessonMarterialsService.update(studyLessonMarterialsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除课程学习资料表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除课程学习资料表", notes = "通过id删除课程学习资料表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = studyLessonMarterialsService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
