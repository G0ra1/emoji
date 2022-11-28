package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyExamQuestionAnswerDto;
import com.netwisd.biz.study.vo.StudyExamQuestionAnswerVo;
import com.netwisd.biz.study.service.StudyExamQuestionAnswerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 问题答案表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2021-12-02 17:11:12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyExamQuestionAnswer" )
@Api(value = "studyExamQuestionAnswer", tags = "问题答案表Controller")
@Slf4j
public class StudyExamQuestionAnswerController {

    private final  StudyExamQuestionAnswerService studyExamQuestionAnswerService;

    /**
     * 分页查询问题答案表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyExamQuestionAnswerDto 问题答案表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyExamQuestionAnswerDto studyExamQuestionAnswerDto) {
        Page pageVo = studyExamQuestionAnswerService.list(studyExamQuestionAnswerDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询问题答案表
     * @param studyExamQuestionAnswerDto 问题答案表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody StudyExamQuestionAnswerDto studyExamQuestionAnswerDto) {
        Page pageVo = studyExamQuestionAnswerService.lists(studyExamQuestionAnswerDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询问题答案表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyExamQuestionAnswerVo> get(@PathVariable("id" ) Long id) {
        StudyExamQuestionAnswerVo studyExamQuestionAnswerVo = studyExamQuestionAnswerService.get(id);
        log.debug("查询成功");
        return Result.success(studyExamQuestionAnswerVo);
    }

    /**
     * 新增问题答案表
     * @param studyExamQuestionAnswerDto 问题答案表
     * @return Result
     */
    @ApiOperation(value = "新增问题答案表", notes = "新增问题答案表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyExamQuestionAnswerDto studyExamQuestionAnswerDto) {
        Boolean result = studyExamQuestionAnswerService.save(studyExamQuestionAnswerDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改问题答案表
     * @param studyExamQuestionAnswerDto 问题答案表
     * @return Result
     */
    @ApiOperation(value = "修改问题答案表", notes = "修改问题答案表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyExamQuestionAnswerDto studyExamQuestionAnswerDto) {
        Boolean result = studyExamQuestionAnswerService.update(studyExamQuestionAnswerDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除问题答案表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除问题答案表", notes = "通过id删除问题答案表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = studyExamQuestionAnswerService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
