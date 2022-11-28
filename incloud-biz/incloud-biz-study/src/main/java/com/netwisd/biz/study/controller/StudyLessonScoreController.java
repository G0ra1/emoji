package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyLessonScoreDto;
import com.netwisd.biz.study.vo.StudyLessonScoreVo;
import com.netwisd.biz.study.service.StudyLessonScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.math.BigDecimal;

/**
 * @Description 课程评分表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-06-22 17:27:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyLessonScore" )
@Api(value = "studyLessonScore", tags = "课程评分表Controller")
@Slf4j
public class StudyLessonScoreController {

    private final  StudyLessonScoreService studyLessonScoreService;

    /**
     * 新增课程评分表
     * @param studyLessonScoreDto 课程评分表
     * @return Result
     */
    @ApiOperation(value = "新增课程评分表", notes = "新增课程评分表")
    @PostMapping
    public Result<BigDecimal> save(@Validation @RequestBody StudyLessonScoreDto studyLessonScoreDto) {
        BigDecimal result = studyLessonScoreService.save(studyLessonScoreDto);
        log.debug("保存成功");
        return Result.success(result);
    }

}
