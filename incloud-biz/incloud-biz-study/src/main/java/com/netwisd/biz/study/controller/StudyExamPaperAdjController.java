package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.vo.StudyExamPaperVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyExamPaperAdjDto;
import com.netwisd.biz.study.vo.StudyExamPaperAdjVo;
import com.netwisd.biz.study.service.StudyExamPaperAdjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 试卷调整申请 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 12:55:07
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyExamPaperAdj" )
@Api(value = "studyExamPaperAdj", tags = "试卷调整申请Controller")
@Slf4j
public class StudyExamPaperAdjController {

    private final  StudyExamPaperAdjService studyExamPaperAdjService;

    /**
     * 分页查询试卷调整申请
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyExamPaperAdjDto 试卷调整申请
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyExamPaperAdjDto studyExamPaperAdjDto) {
        Page pageVo = studyExamPaperAdjService.list(studyExamPaperAdjDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 查看调整详情
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "查看调整详情", notes = "查看调整详情")
    @GetMapping("/{id}" )
    public Result<StudyExamPaperAdjVo> get(@PathVariable("id" ) Long id) {
        StudyExamPaperAdjVo studyExamPaperAdjVo = studyExamPaperAdjService.get(id);
        log.debug("查询成功");
        return Result.success(studyExamPaperAdjVo);
    }

    /**
     * 查看试卷调整情况
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "查看试卷调整情况", notes = "查看试卷调整情况")
    @GetMapping("/adjListFor/{id}")
    public Result<List<StudyExamPaperAdjVo>> adjListForPaper( @PathVariable("id" ) Long id) {
        return Result.success(studyExamPaperAdjService.adjListForPaper(id));
    }

    /**
     * 流程新增或修改
     *
     * @param studyExamPaperAdjDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<StudyExamPaperAdjVo> procSaveOrUpdate(@Validation @RequestBody StudyExamPaperAdjDto studyExamPaperAdjDto) {
        StudyExamPaperAdjVo studyExamPaperAdjVo = studyExamPaperAdjService.procSaveOrUpdate(studyExamPaperAdjDto);
        log.debug("保存成功");
        return Result.success(studyExamPaperAdjVo);
    }

    /**
     * 流程查看
     *
     * @param procInstId procInstId
     * @return
     */
    @ApiOperation(value = "流程查看", notes = "流程查看")
    @GetMapping("/procDetail/{procInstId}")
    public Result<StudyExamPaperAdjVo> procDetail(@PathVariable String procInstId) {
        return Result.success(studyExamPaperAdjService.procDetail(procInstId));
    }

    /**
     * 调整试卷删除
     *
     * @param  paperAdjId
     * @return
     */
    @ApiOperation(value = "调整试卷删除", notes = "调整试卷删除")
    @DeleteMapping("/delete/{paperAdjId}")
    public Result<Boolean> delete(@PathVariable  Long paperAdjId) {
        return Result.success(studyExamPaperAdjService.delete(paperAdjId));
    }
}
