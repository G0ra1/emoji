package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyUserExamDto;
import com.netwisd.biz.study.entity.StudyExamQuestion;
import com.netwisd.biz.study.vo.StudyExamPaperAdjVo;
import com.netwisd.biz.study.vo.StudyExamQuestionVo;
import com.netwisd.biz.study.vo.StudyUserExamVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyExamPaperDto;
import com.netwisd.biz.study.vo.StudyExamPaperVo;
import com.netwisd.biz.study.service.StudyExamPaperService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;
import java.util.List;

/**
 * @Description 试卷结果表 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 17:32:00
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyExamPaperDef" )
@Api(value = "studyExamPaper", tags = "试卷结果表Controller")
@Slf4j
public class StudyExamPaperController {

    private final  StudyExamPaperService studyExamPaperService;

    /**
     * 分页查询试卷结果表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyExamPaperDto 试卷结果表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyExamPaperDto studyExamPaperDto) {
        Page pageVo = studyExamPaperService.list(studyExamPaperDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 列表查询试卷结果表
     * @param studyExamPaperDto 试卷结果表
     * @return
     */
    @ApiOperation(value = "列表查询", notes = "列表查询")
    @PostMapping("/lists" )
    public Result<List<StudyExamPaperVo>> lists(@RequestBody StudyExamPaperDto studyExamPaperDto) {
        List<StudyExamPaperVo> list = studyExamPaperService.lists(studyExamPaperDto);
        log.debug("查询条数:"+list.size());
        return Result.success(list);
    }

    /**
     * 通过结果id查出结果转成调整
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过结果id查出结果转成调整", notes = "通过结果id查出结果转成调整")
    @GetMapping("/getPaper/{id}" )
    public Result<StudyExamPaperAdjVo> getPaper(@PathVariable("id" ) Long id) {
        StudyExamPaperAdjVo studyExamPaperAdjVo = studyExamPaperService.getPaper(id);
        log.debug("查询成功");
        return Result.success(studyExamPaperAdjVo);
    }
    /**
     * 查看试卷详情
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "查看试卷详情", notes = "查看试卷详情")
    @GetMapping("/{id}" )
    public Result<StudyExamPaperVo> get(@PathVariable("id" ) Long id) {
        StudyExamPaperVo studyExamPaperVo = studyExamPaperService.get(id);
        log.debug("查询成功");
        return Result.success(studyExamPaperVo);
    }

    /**
     * 分页获取待阅试卷列表
     * @param studyUserExamDto
     * @return
     */
    @ApiOperation(value = "分页获取待阅试卷列表", notes = "分页获取待阅试卷列表")
    @PostMapping("/getPaperList")
    public Result<Page<StudyUserExamVo>> getPaperList(@RequestBody StudyUserExamDto studyUserExamDto) {
        Page<StudyUserExamVo> paperList = studyExamPaperService.getPaperList(studyUserExamDto);
        log.debug("查询条数:"+paperList.getTotal());
        return Result.success(paperList);
    }

    /**
     * 获取待阅试卷详情
     * @param id
     * @return
     */
    @ApiOperation(value = "获取待阅试卷详情", notes = "获取待阅试卷详情")
    @GetMapping("/markPaperDetail/{id}")
    public Result<StudyUserExamVo> markPaperDetail(@PathVariable Long id) {
        return Result.success(studyExamPaperService.markPaperDetail(id));
    }

    /**
     * 阅卷保存
     * @param studyUserExamDto
     * @return
     */
    @ApiOperation(value = "阅卷保存", notes = "阅卷保存")
    @PutMapping("/teacherMarking")
    public Result<Boolean> teacherMarking(@RequestBody StudyUserExamDto studyUserExamDto) {
        return Result.success(studyExamPaperService.teacherMarking(studyUserExamDto)) ;
    }

    /**
     * 获取已阅试卷详情
     * @param id
     * @return
     */
    @ApiOperation(value = "获取已阅试卷详情", notes = "获取已阅试卷详情")
    @GetMapping("/markedPaperDetail/{id}")
    public Result<StudyUserExamVo> markedPaperDetail(@PathVariable Long id) {
        return Result.success(studyExamPaperService.markedPaperDetail(id)) ;
    }

    /**
     *修改试卷状态(启用或者禁用)
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "修改试卷状态(启用或者禁用)", notes = "修改试卷状态(启用或者禁用)")
    @PutMapping("/isEnable/{id}")
    public Result<Boolean> isEnable(@PathVariable("id" ) Long id) {
        Boolean result =  studyExamPaperService.isEnable(id);
        log.debug("更新成功");
        return Result.success(result);
    }
    /**
     * 流程新增或修改
     *
     * @param studyExamPaperDto
     * @return
     */
    @ApiOperation(value = "流程新增或修改", notes = "流程新增或修改")
    @PostMapping("/procSaveOrUpdate")
    public Result<StudyExamPaperVo> procSaveOrUpdate(@Validation @RequestBody StudyExamPaperDto studyExamPaperDto) {
        StudyExamPaperVo studyExamPaperVo = studyExamPaperService.procSaveOrUpdate(studyExamPaperDto);
        log.debug("保存成功");
        return Result.success(studyExamPaperVo);
    }

    /**
     * 流程查看
     *
     * @param procInstId procInstId
     * @return
     */
    @ApiOperation(value = "流程查看", notes = "流程查看")
    @GetMapping("/procDetail/{procInstId}")
    public Result<StudyExamPaperVo> procDetail(@PathVariable String procInstId) {
        return Result.success(studyExamPaperService.procDetail(procInstId));
    }

    /**
     * 试卷删除
     *
     * @param  paperId
     * @return
     */
    @ApiOperation(value = "试卷删除", notes = "试卷删除")
    @DeleteMapping("/delete/{paperId}")
    public Result<Boolean> delete(@PathVariable  Long paperId) {
        return Result.success(studyExamPaperService.delete(paperId));
    }
}
