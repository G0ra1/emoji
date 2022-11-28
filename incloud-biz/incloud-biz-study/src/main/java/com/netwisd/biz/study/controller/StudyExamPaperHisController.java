package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyExamPaperHisDto;
import com.netwisd.biz.study.vo.StudyExamPaperHisVo;
import com.netwisd.biz.study.service.StudyExamPaperHisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

/**
 * @Description 试卷历史 功能描述...
 * @author 云数网讯 sun@netwisd.com
 * @date 2022-05-13 15:43:35
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyExamPaperHis" )
@Api(value = "studyExamPaperHis", tags = "试卷历史Controller")
@Slf4j
public class StudyExamPaperHisController {

    private final  StudyExamPaperHisService studyExamPaperHisService;

    /**
     * 分页查询试卷历史
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyExamPaperHisDto 试卷历史
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyExamPaperHisDto studyExamPaperHisDto) {
        Page pageVo = studyExamPaperHisService.list(studyExamPaperHisDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询试卷历史
     * @param studyExamPaperHisDto 试卷历史
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody StudyExamPaperHisDto studyExamPaperHisDto) {
        Page pageVo = studyExamPaperHisService.lists(studyExamPaperHisDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 查看历史详情
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "查看历史详情", notes = "查看历史详情")
    @GetMapping("/{id}" )
    public Result<StudyExamPaperHisVo> get(@PathVariable("id" ) Long id) {
        StudyExamPaperHisVo studyExamPaperHisVo = studyExamPaperHisService.get(id);
        log.debug("查询成功");
        return Result.success(studyExamPaperHisVo);
    }

    /**
     * 查看试卷多条记录
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "查看试卷多条记录", notes = "查看试卷多条记录")
    @GetMapping("/hisListFor/{id}" )
    public Result<List<StudyExamPaperHisVo>> hisListForPaper(@PathVariable("id" ) Long id) {
        return Result.success(studyExamPaperHisService.hisListForPaper(id));
    }
}
