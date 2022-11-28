package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyLessonCouWatchApplyDto;
import com.netwisd.biz.study.vo.StudyLessonCouWatchApplyVo;
import com.netwisd.biz.study.service.StudyLessonCouWatchApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 课程课件观看申请表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-29 14:22:04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyLessonCouWatchApply" )
@Api(value = "studyLessonCouWatchApply", tags = "课程课件观看申请表Controller")
@Slf4j
public class StudyLessonCouWatchApplyController {

    private final  StudyLessonCouWatchApplyService studyLessonCouWatchApplyService;

    /**
     * 分页查询课程课件观看申请表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyLessonCouWatchApplyDto 课程课件观看申请表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyLessonCouWatchApplyDto studyLessonCouWatchApplyDto) {
        Page pageVo = studyLessonCouWatchApplyService.list(studyLessonCouWatchApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询课程课件观看申请表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyLessonCouWatchApplyVo> get(@PathVariable("id" ) Long id) {
        StudyLessonCouWatchApplyVo studyLessonCouWatchApplyVo = studyLessonCouWatchApplyService.get(id);
        log.debug("查询成功");
        return Result.success(studyLessonCouWatchApplyVo);
    }

    /**
     * 新增课程课件观看申请表
     * @param studyLessonCouWatchApplyDto 课程课件观看申请表
     * @return Result
     */
    @ApiOperation(value = "新增课程课件观看申请表", notes = "新增课程课件观看申请表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyLessonCouWatchApplyDto studyLessonCouWatchApplyDto) {
        Boolean result = studyLessonCouWatchApplyService.save(studyLessonCouWatchApplyDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改课程课件观看申请表
     * @param studyLessonCouWatchApplyDto 课程课件观看申请表
     * @return Result
     */
    @ApiOperation(value = "修改课程课件观看申请表", notes = "修改课程课件观看申请表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyLessonCouWatchApplyDto studyLessonCouWatchApplyDto) {
        Boolean result = studyLessonCouWatchApplyService.update(studyLessonCouWatchApplyDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 修改课程课件观看申请表
     * @param studyLessonCouWatchApplyDtos 课程课件观看申请表
     * @return Result
     */
    @ApiOperation(value = "修改课程课件观看申请表", notes = "修改课程课件观看申请表")
    @PutMapping("/updateStatusBatch")
    public Result<Boolean> updateStatusBatch(@RequestBody List<StudyLessonCouWatchApplyDto> studyLessonCouWatchApplyDtos) {
        Boolean result = studyLessonCouWatchApplyService.updateStatusBatch(studyLessonCouWatchApplyDtos);
        log.debug("更新成功");
        return Result.success(result);
    }

}
