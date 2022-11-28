package com.netwisd.biz.study.controller;

import com.netwisd.biz.study.dto.StudyUserExamDto;
import com.netwisd.biz.study.service.StudyUserExamService;
import com.netwisd.biz.study.service.StudyXyExamService;
import com.netwisd.biz.study.vo.StudyExamPaperVo;
import com.netwisd.biz.study.vo.StudyUserExamVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 学员端-考试相关全部接口controller
 * @date 2022-03-15 16:37:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/xueyuan/exam")
@Api(value = "/xueyuan/exam", tags = "学员端-考试相关全部接口controller")
@Slf4j
public class StudyXyExamController {
    @Autowired
    private StudyXyExamService studyXyExamService;
    @Autowired
    private StudyUserExamService studyUserExamService;
    /**
     * 生成试卷
     * @param paperId 试卷id
     * @return
     */
    @ApiOperation(value = "生成试卷", notes = "生成试卷")
    @GetMapping("/generatePaper/{paperId}" )
    public Result<StudyExamPaperVo> generatePaper(@PathVariable Long paperId) {
        StudyExamPaperVo studyExamPaperVo = studyXyExamService.generatePaper(paperId);
        return Result.success(studyExamPaperVo);
    }
    /**
     * 学员答题保存
     * @param studyUserExamDto
     * @return
     */
    @ApiOperation(value = "学员答题保存", notes = "学员答题保存")
    @PostMapping("/studentAnswer")
    public Result<Boolean> studentAnswer (@RequestBody StudyUserExamDto studyUserExamDto) {
        return Result.success(studyUserExamService.save(studyUserExamDto));
    }
    /**
     * 提交试卷
     * @param id
     * @return
     */
    @ApiOperation(value = "提交试卷", notes = "提交试卷")
    @PutMapping("/submittedPapers/{id}")
    public Result<Boolean> submittedPapers(@PathVariable("id") Long id) {
        return Result.success(studyXyExamService.submittedPapers(id));
    }

}
