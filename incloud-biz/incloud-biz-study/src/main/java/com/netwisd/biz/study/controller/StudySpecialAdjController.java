package com.netwisd.biz.study.controller;


import com.netwisd.biz.study.dto.StudySpecialAdjDto;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.vo.StudySpecialAdjVo;
import com.netwisd.biz.study.service.StudySpecialAdjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题调整申请表 功能描述...
 * @date 2022-05-13 11:27:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studySpecialAdj")
@Api(value = "studySpecialAdj", tags = "专题调整申请表Controller")
@Slf4j
public class StudySpecialAdjController {

    private final StudySpecialAdjService specialAdjService;

    /**
     * 获取专题的调整记录
     *
     * @param specialId
     * @return
     */
    @ApiOperation(value = "获取专题的调整记录", notes = "获取专题的调整记录")
    @GetMapping("/adjListFor/{specialId}")
    public Result<List<StudySpecialAdjVo>> adjListForLesson(@PathVariable("specialId") Long specialId) {
        return Result.success(specialAdjService.adjListForSpecial(specialId));
    }

    /**
     * 通过id查询专题详情
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询详情", notes = "通过id查询详情")
    @GetMapping("/{id}")
    public Result<StudySpecialAdjVo> detail(@PathVariable("id") Long id) {
        return Result.success(specialAdjService.detail(id));
    }


    /**
     * 流程新增
     *
     * @param studySpecialAdjDto
     * @return
     */
    @ApiOperation(value = "流程新增", notes = "流程新增")
    @PostMapping("/procSave")
    public Result<StudySpecialAdjVo> procSave(@RequestBody @Validation StudySpecialAdjDto studySpecialAdjDto) {
        return Result.success(specialAdjService.procSave(studySpecialAdjDto));
    }

    /**
     * 流程修改
     *
     * @param studySpecialAdjDto
     * @return
     */
    @ApiOperation(value = "流程修改", notes = "流程修改")
    @PutMapping("/procUpdate")
    public Result<StudySpecialAdjVo> procUpdate(@RequestBody @Validation StudySpecialAdjDto studySpecialAdjDto) {
        return Result.success(specialAdjService.procUpdate(studySpecialAdjDto));
    }

    /**
     * 流程回显获取详情
     *
     * @param procInstId procInstId
     * @return
     */
    @ApiOperation(value = "流程回显获取详情", notes = "流程回显获取详情")
    @GetMapping("/procDetail/{procInstId}")
    public Result<StudySpecialAdjVo> procDetail(@PathVariable String procInstId) {
        return Result.success(specialAdjService.procDetail(procInstId));
    }

    /**
     * 专题调整删除
     *
     * @param specialAdjId
     * @return
     */
    @ApiOperation(value = "专题调整删除", notes = "专题调整删除")
    @DeleteMapping("/delete/{specialAdjId}")
    public Result<Boolean> delete(@PathVariable Long specialAdjId) {
        return Result.success(specialAdjService.delete(specialAdjId));
    }
}
