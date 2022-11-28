package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyExamDatabaseDto;
import com.netwisd.biz.study.vo.StudyExamDatabaseVo;
import com.netwisd.biz.study.service.StudyExamDatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 题库定义 功能描述...
 * @author 云数网讯 sundong@netwisd.com
 * @date 2022-04-29 14:53:35
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyExamDatabaseDef" )
@Api(value = "studyExamDatabase", tags = "题库定义Controller")
@Slf4j
public class StudyExamDatabaseController {

    private final  StudyExamDatabaseService studyExamDatabaseService;

    /**
     * 分页查询题库定义
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyExamDatabaseDto 题库定义
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyExamDatabaseDto studyExamDatabaseDto) {
        Page pageVo = studyExamDatabaseService.list(studyExamDatabaseDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询题库定义
     * @param studyExamDatabaseDto 题库定义
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<List<StudyExamDatabaseVo>> lists(@RequestBody StudyExamDatabaseDto studyExamDatabaseDto) {
        List<StudyExamDatabaseVo> studyExamDatabaseVoList = studyExamDatabaseService.lists(studyExamDatabaseDto);
        log.debug("查询条数:"+studyExamDatabaseVoList.size());
        return Result.success(studyExamDatabaseVoList);
    }

    /**
     * 通过id查询题库定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyExamDatabaseVo> get(@PathVariable("id" ) Long id) {
        StudyExamDatabaseVo studyExamDatabaseVo = studyExamDatabaseService.get(id);
        log.debug("查询成功");
        return Result.success(studyExamDatabaseVo);
    }

    /**
     * 新增题库定义
     * @param studyExamDatabaseDto 题库定义
     * @return Result
     */
    @ApiOperation(value = "新增题库定义", notes = "新增题库定义")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyExamDatabaseDto studyExamDatabaseDto) {
        Boolean result = studyExamDatabaseService.save(studyExamDatabaseDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改题库定义
     * @param studyExamDatabaseDto 题库定义
     * @return Result
     */
    @ApiOperation(value = "修改题库定义", notes = "修改题库定义")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyExamDatabaseDto studyExamDatabaseDto) {
        Boolean result = studyExamDatabaseService.update(studyExamDatabaseDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除题库定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除题库定义", notes = "通过id删除题库定义")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = studyExamDatabaseService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
