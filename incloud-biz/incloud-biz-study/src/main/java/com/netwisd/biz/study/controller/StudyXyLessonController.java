package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyLessonDto;
import com.netwisd.biz.study.dto.StudyUserLearnApplyDto;
import com.netwisd.biz.study.service.StudyLessonCommentsService;
import com.netwisd.biz.study.service.StudyUserLearnApplyService;
import com.netwisd.biz.study.service.StudyXyLessonService;
import com.netwisd.biz.study.vo.StudyLessonForShowVo;
import com.netwisd.biz.study.vo.StudyLessonVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 学员端课程Controller
 * @date 2022-03-15 16:37:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/xueyuan/lesson")
@Api(value = "/xueyuan/lesson", tags = "学员端课程Controller")
@Slf4j
public class StudyXyLessonController {

    @Autowired
    private StudyXyLessonService studyXyLessonService;

    @Autowired
    private StudyUserLearnApplyService studyUserLearnApplyService;

    @Autowired
    private StudyLessonCommentsService studyLessonCommentsService;

    /**
     * 课程模块-列表展示
     * @param studyLessonDto 在线学习通知公告表
     * @return
     */
    @ApiOperation(value = "课程模块-列表展示", notes = "课程模块-列表展示")
    @PostMapping("/pageForIndex" )
    public Result<Page<StudyLessonVo>> pageForIndex(@RequestBody StudyLessonDto studyLessonDto) {
        Page<StudyLessonVo> pageVo = studyXyLessonService.pageForIndex(studyLessonDto);
        return Result.success(pageVo);
    }

    /**
     * 课程模块-查询课程详情
     * @param id id
     * @return
     */
    @ApiOperation(value = "课程模块-查询课程详情", notes = "课程模块-查询课程详情")
    @GetMapping("/getLessonDetail/{id}" )
    public Result<StudyLessonForShowVo> getLessonDetail(@PathVariable Long id) {
        StudyLessonForShowVo lessonVo = studyXyLessonService.getLessonDetail(id);
        return Result.success(lessonVo);
    }

    /**
     * 课程模块-获取课程评论
     * @param id id
     * @return
     */
    @ApiOperation(value = "课程模块-获取课程评论", notes = "课程模块-获取课程评论")
    @GetMapping("/getCommentsByLessonId/{id}" )
    public Result<Map<String,Object>> getCommentsByLessonId(@PathVariable Long id) {
        Map<String,Object> lessonCommentsVos = studyLessonCommentsService.getByLessonId(id);
        return Result.success(lessonCommentsVos);
    }

    /**
     * 课程中的课件学习申请
     * @param infoDto
     * @return
     */
    @PostMapping("/learnApply")
    @ApiOperation(value = "课程中的课件学习申请")
    public Result<Boolean> learnApply(@RequestBody StudyUserLearnApplyDto infoDto) {
        return Result.success(studyUserLearnApplyService.couLearnApply(infoDto));
    }


}
