package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.netwisd.biz.study.dto.StudySpecialDto;
import com.netwisd.biz.study.dto.StudyUserLearnApplyDto;
import com.netwisd.biz.study.service.StudyLessonService;
import com.netwisd.biz.study.service.StudySpecialService;
import com.netwisd.biz.study.service.StudyUserLearnApplyService;
import com.netwisd.biz.study.vo.StudySpecialLessonDetailVo;
import com.netwisd.biz.study.vo.StudySpecialVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 学员端专题Controller
 * @date 2022-03-15 16:37:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/xueyuan/special")
@Api(value = "/xueyuan/special", tags = "学员端专题Controller")
@Slf4j
public class StudyXySpecialController {

    private final StudySpecialService studySpecialService;

    private final StudyUserLearnApplyService studyUserLearnApplyService;

    private final StudyLessonService studyLessonService;

    @PostMapping("/pageList")
    @ApiOperation(value = "学员端分页查询专题")
    public Result<IPage> pageList(@RequestBody StudySpecialDto specialDto) {
        return Result.success(studySpecialService.xueyuanPageList(specialDto));
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "学员端获取专题详情")
    public Result<StudySpecialVo> xueyuanDetail(@PathVariable("id") Long id) {
        return Result.success(studySpecialService.xueyuanDetail(id));
    }

    @GetMapping("/detail/getLessonMsg")
    @ApiOperation(value = "学员端获取专题课程详情")
    public Result<StudySpecialLessonDetailVo> xueyuanLessonDetail(@RequestParam("specialId") Long specialId,
                                                                  @RequestParam("lessonId") Long lessonId) {
        return Result.success(studyLessonService.getLessonDetailForSpecial(specialId,lessonId));
    }

    @PostMapping("/learnApply")
    @ApiOperation(value = "专题学习申请")
    public Result<Boolean> learnApply(@RequestBody StudyUserLearnApplyDto infoDto) {
        return Result.success(studyUserLearnApplyService.specialLearnApply(infoDto));
    }

    @GetMapping("/isCanExam/{id}")
    @ApiOperation(value = "判断学员是否可以开始考试")
    public Result<Boolean> isCanExam(@PathVariable("id") Long id) {
        return Result.success(studySpecialService.isCanExam(id));
    }

}
