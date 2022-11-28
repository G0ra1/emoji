package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyCouPermDto;
import com.netwisd.biz.study.vo.StudyCouPermVo;
import com.netwisd.biz.study.service.StudyCouPermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 课件授权表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-19 19:13:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyCouPerm" )
@Api(value = "studyCouPerm", tags = "课件授权表Controller")
@Slf4j
public class StudyCouPermController {

    private final  StudyCouPermService studyCouPermService;

    /**
     * 分页查询课件授权表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyCouPermDto 课件授权表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyCouPermDto studyCouPermDto) {
        Page pageVo = studyCouPermService.list(studyCouPermDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询课件授权表
     * @param studyCouPermDto 课件授权表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody StudyCouPermDto studyCouPermDto) {
        Page pageVo = studyCouPermService.lists(studyCouPermDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询课件授权表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyCouPermVo> get(@PathVariable("id" ) Long id) {
        StudyCouPermVo studyCouPermVo = studyCouPermService.get(id);
        log.debug("查询成功");
        return Result.success(studyCouPermVo);
    }

    /**
     * 新增课件授权表
     * @param studyCouPermDto 课件授权表
     * @return Result
     */
    @ApiOperation(value = "新增课件授权表", notes = "新增课件授权表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyCouPermDto studyCouPermDto) {
        Boolean result = studyCouPermService.save(studyCouPermDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改课件授权表
     * @param studyCouPermDto 课件授权表
     * @return Result
     */
    @ApiOperation(value = "修改课件授权表", notes = "修改课件授权表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyCouPermDto studyCouPermDto) {
        Boolean result = studyCouPermService.update(studyCouPermDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除课件授权表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除课件授权表", notes = "通过id删除课件授权表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = studyCouPermService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
