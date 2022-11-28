package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyMarterialsDto;
import com.netwisd.biz.study.vo.StudyMarterialsVo;
import com.netwisd.biz.study.service.StudyMarterialsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 学习资料表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 20:04:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyMarterials" )
@Api(value = "studyMarterials", tags = "学习资料表Controller")
@Slf4j
public class StudyMarterialsController {

    private final  StudyMarterialsService studyMarterialsService;

    /**
     * 分页查询学习资料表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyMarterialsDto 学习资料表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyMarterialsDto studyMarterialsDto) {
        Page pageVo = studyMarterialsService.list(studyMarterialsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询学习资料表
     * @param studyMarterialsDto 学习资料表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<List<StudyMarterialsVo>> lists(@RequestBody StudyMarterialsDto studyMarterialsDto) {
        List<StudyMarterialsVo> marterialsVos = studyMarterialsService.lists(studyMarterialsDto);
        return Result.success(marterialsVos);
    }

    /**
     * 通过id查询学习资料表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyMarterialsVo> get(@PathVariable("id" ) Long id) {
        StudyMarterialsVo studyMarterialsVo = studyMarterialsService.get(id);
        log.debug("查询成功");
        return Result.success(studyMarterialsVo);
    }

    /**
     * 新增学习资料表
     * @param studyMarterialsDto 学习资料表
     * @return Result
     */
    @ApiOperation(value = "新增学习资料表", notes = "新增学习资料表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyMarterialsDto studyMarterialsDto) {
        Boolean result = studyMarterialsService.save(studyMarterialsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改学习资料表
     * @param studyMarterialsDto 学习资料表
     * @return Result
     */
    @ApiOperation(value = "修改学习资料表", notes = "修改学习资料表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyMarterialsDto studyMarterialsDto) {
        Boolean result = studyMarterialsService.update(studyMarterialsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除学习资料表
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "通过id删除学习资料表", notes = "通过id删除学习资料表")
    @DeleteMapping("/{ids}" )
    public Result<Boolean> delete(@PathVariable String ids) {
        return Result.success(studyMarterialsService.delete(ids));
    }

}
