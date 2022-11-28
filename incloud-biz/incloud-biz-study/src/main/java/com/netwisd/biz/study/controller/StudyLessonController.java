package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyLessonDto;
import com.netwisd.biz.study.service.StudyLessonService;
import com.netwisd.biz.study.vo.StudyLessonAdjVo;
import com.netwisd.biz.study.vo.StudyLessonForSpecialVo;
import com.netwisd.biz.study.vo.StudyLessonVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 课程表 功能描述...
 * @date 2022-04-19 19:15:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyLesson")
@Api(value = "studyLesson", tags = "课程表Controller")
@Slf4j
public class StudyLessonController {

    private final StudyLessonService studyLessonService;

    /**
     * 分页查询课程表
     *
     * @param studyLessonDto 课程表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list")
    public Result<Page> list(@RequestBody StudyLessonDto studyLessonDto) {
        Page pageVo = studyLessonService.list(studyLessonDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 不分页查询课程信息
     *
     * @param studyLessonDto 课程表
     * @return
     */
    @ApiOperation(value = "不分页查询课程信息", notes = "不分页查询课程信息")
    @PostMapping("/lists")
    public Result<List<StudyLessonForSpecialVo>> lists(@RequestBody StudyLessonDto studyLessonDto) {
        List<StudyLessonForSpecialVo> lessonVoList = studyLessonService.lists(studyLessonDto);
        log.debug("查询条数:" + lessonVoList.size());
        return Result.success(lessonVoList);
    }

    /**
     * 通过id查询课程详情
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询课程详情", notes = "通过id查询课程详情")
    @GetMapping("/{id}")
    public Result<StudyLessonVo> get(@PathVariable("id") Long id) {
        StudyLessonVo studyLessonVo = studyLessonService.get(id);
        log.debug("课程详情查询成功");
        return Result.success(studyLessonVo);
    }

    /**
     * 根据课程id修改是否首页展示
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "根据课程id修改是否首页展示", notes = "根据课程id修改是否首页展示")
    @PutMapping("/updateIsIndex/{id}")
    public Result<Boolean> updateIsIndex(@PathVariable("id") Long id) {
        Boolean aBoolean = studyLessonService.updateIsIndex(id);
        log.debug("课程修改是否首页展示成功");
        return Result.success(aBoolean);
    }

    /**
     * 根据课程id修改是否启用
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "根据课程id修改是否启用", notes = "根据课程id修改是否启用")
    @PutMapping("/updateIsEnable/{id}")
    public Result<Boolean> updateIsEnable(@PathVariable("id") Long id) {
        Boolean aBoolean = studyLessonService.updateIsEnable(id);
        log.debug("课程修改是否启用成功");
        return Result.success(aBoolean);
    }

    /**
     * 流程新增或修改
     *
     * @param studyLessonDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<StudyLessonVo> procSaveOrUpdate(@RequestBody StudyLessonDto studyLessonDto) {
        return Result.success(studyLessonService.procSaveOrUpdate(studyLessonDto));
    }

    /**
     * 流程查看
     *
     * @param procInstId procInstId
     * @return
     */
    @ApiOperation(value = "流程查看", notes = "流程查看")
    @GetMapping("/procDetail/{procInstId}")
    public Result<StudyLessonVo> procDetail(@PathVariable String procInstId) {
        return Result.success(studyLessonService.procDetail(procInstId));
    }

    /**
     * 通过id组装调整信息
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id组装调整信息", notes = "通过id组装调整信息")
    @GetMapping("/detailForAdj/{id}")
    public Result<StudyLessonAdjVo> detailForAdj(@PathVariable("id") Long id) {
        return Result.success(studyLessonService.detailForAdj(id));
    }

    /**
     * 课程调整删除
     *
     * @param lessonId
     * @return
     */
    @ApiOperation(value = "课程调整删除", notes = "课程调整删除")
    @DeleteMapping("/delete/{lessonId}")
    public Result<Boolean> delete(@PathVariable Long lessonId) {
        return Result.success(studyLessonService.delete(lessonId));
    }

}
