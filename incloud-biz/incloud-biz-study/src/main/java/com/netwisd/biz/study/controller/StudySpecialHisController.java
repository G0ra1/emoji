package com.netwisd.biz.study.controller;


import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.vo.StudySpecialHisVo;
import com.netwisd.biz.study.service.StudySpecialHisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;


import java.util.List;

/**
 * @author 云数网讯 cr@netwisd.com
 * @Description 专题历史表 功能描述...
 * @date 2022-05-13 14:23:33
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studySpecialHis")
@Api(value = "studySpecialHis", tags = "专题历史表Controller")
@Slf4j
public class StudySpecialHisController {

    private final StudySpecialHisService studySpecialHisService;


    /**
     * 获取专题的历史记录
     *
     * @param specialId
     * @return
     */
    @ApiOperation(value = "获取专题的历史记录", notes = "获取专题的历史记录")
    @GetMapping("/hisListFor/{specialId}")
    public Result<List<StudySpecialHisVo>> hisListForSprcial(@PathVariable("specialId") Long specialId) {
        return Result.success(studySpecialHisService.hisListForSpecial(specialId));
    }

    /**
     * 通过id查询专题历史详情
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询详情", notes = "通过id查询详情")
    @GetMapping("/{id}")
    public Result<StudySpecialHisVo> detail(@PathVariable("id") Long id) {
        return Result.success(studySpecialHisService.detail(id));
    }

}
