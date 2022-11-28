package com.netwisd.biz.study.controller;

import com.netwisd.biz.study.vo.StudySpecialLessonDetailVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyUserStudyRecordsDto;
import com.netwisd.biz.study.service.StudyUserStudyRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @Description 用户学习记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-20 11:02:11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyUserStudyRecords" )
@Api(value = "studyUserStudyRecords", tags = "用户学习记录表Controller")
@Slf4j
public class StudyUserStudyRecordsController {

    private final  StudyUserStudyRecordsService studyUserStudyRecordsService;

    /**
     * 学习课程记录
     *
     * @param userStudyRecordsDto
     * @return
     */
    @ApiOperation(value = "学习课程记录", notes = "学习课程记录")
    @PostMapping("/saveLessonRecord")
    public Result<Map<String,String>> saveLessonRecord(@RequestBody StudyUserStudyRecordsDto userStudyRecordsDto) {
        Map<String,String> aBoolean = studyUserStudyRecordsService.saveLessonRecord(userStudyRecordsDto);
        return Result.success(aBoolean);
    }

    /**
     * 学习专题记录
     *
     * @param userStudyRecordsDto
     * @return
     */
    @ApiOperation(value = "学习专题记录", notes = "学习专题记录")
    @PostMapping("/saveSpecialRecord")
    public Result<Map<String,String>> saveSpecialRecord(@RequestBody StudyUserStudyRecordsDto userStudyRecordsDto) {
        Map<String,String> specialRecord = studyUserStudyRecordsService.saveSpecialRecord(userStudyRecordsDto);
        return Result.success(specialRecord);
    }

    /**
     * 根绝type返回不同的个人学习信息
     *
     * @param userId
     * @param type
     * @return
     */
    @ApiOperation(value = "根绝type返回不同的个人学习信息", notes = "根绝type返回不同的个人学习信息")
    @GetMapping("/getUserStudyMsg")
    public Result<Long> getUserStudyMsg(@RequestParam(value = "userId") Long userId,
                                           @RequestParam(value = "type") Integer type) {
        long num = studyUserStudyRecordsService.getUserStudyMsg(userId,type);
        return Result.success(num);
    }

}
