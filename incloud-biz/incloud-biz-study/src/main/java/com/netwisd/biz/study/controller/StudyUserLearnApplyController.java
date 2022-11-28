package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyUserLearnApplyDto;
import com.netwisd.biz.study.service.StudyUserLearnApplyService;
import com.netwisd.biz.study.vo.StudyUserLearnApplyVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author 云数网讯 zhaixiaoliang@netwisd.com
 * @Description 人员学习申请
 * @date 2022-04-25 09:39:13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyUserLearnApply")
@Api(value = "studyUserLearnApply", tags = "人员学习申请Controller")
@Slf4j
public class StudyUserLearnApplyController {

    private final StudyUserLearnApplyService studyUserLearnApplyService;

    /**
     * 分页查询人员学习申请
     *
     * @param infoDto
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/pageList")
    public Result<Page> pageList(@RequestBody StudyUserLearnApplyDto infoDto) {
        Page pageVo = studyUserLearnApplyService.pageList(infoDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 获取详情
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "获取详情", notes = "获取详情")
    @GetMapping("/{id}")
    public Result<StudyUserLearnApplyVo> detail(@PathVariable("id") Long id) {
        return Result.success(studyUserLearnApplyService.detail(id));
    }

    @ApiOperation(value = "处理学习申请", notes = "处理学习申请")
    @PutMapping("/dealApply")
    public Result<Boolean> dealApply(@RequestBody StudyUserLearnApplyDto infoDto) {
        studyUserLearnApplyService.dealApply(infoDto);
        return Result.success(true);
    }
}
