package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.study.dto.StudyCouDto;
import com.netwisd.biz.study.service.StudyCouService;
import com.netwisd.biz.study.vo.StudyCouVo;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 云数网讯 lhy@netwisd.com
 * @Description 课件表 功能描述...
 * @date 2022-04-19 18:23:02
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyCou")
@Api(value = "studyCou", tags = "课件表Controller")
@Slf4j
public class StudyCouController {

    private final StudyCouService studyCouService;

    /**
     * 分页查询课件表
     * 没有使用参数注解，就是默认从form请求的方式
     *
     * @param studyCouDto 课件表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list")
    public Result<Page> list(@RequestBody StudyCouDto studyCouDto) {
        Page pageVo = studyCouService.pageList(studyCouDto);
        log.debug("查询条数:" + pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询课件表
     *
     * @param studyCouDto 课件表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists")
    public Result<List<StudyCouVo>> lists(@RequestBody StudyCouDto studyCouDto) {
        List<StudyCouVo> couVoList = studyCouService.lists(studyCouDto);
        log.debug("查询条数:" + couVoList.size());
        return Result.success(couVoList);
    }

    /**
     * 通过id查询课件表
     *
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}")
    public Result<StudyCouVo> get(@PathVariable("id") Long id) {
        StudyCouVo studyCouVo = studyCouService.get(id);
        log.debug("查询成功");
        return Result.success(studyCouVo);
    }

    /**
     * 新增课件表
     *
     * @param studyCouDto 课件表
     * @return Result
     */
    @ApiOperation(value = "新增课件表", notes = "新增课件表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyCouDto studyCouDto) {
        Boolean result = studyCouService.save(studyCouDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改课件表
     *
     * @param studyCouDto 课件表
     * @return Result
     */
    @ApiOperation(value = "修改课件表", notes = "修改课件表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyCouDto studyCouDto) {
        Boolean result = studyCouService.update(studyCouDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除课件表
     *
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "通过id删除课件表", notes = "通过id删除课件表")
    @DeleteMapping("/{ids}")
    public Result<Boolean> delete(@PathVariable String ids) {
        Boolean result = studyCouService.delete(ids);
        log.debug("删除成功");
        return Result.success(result);
    }

}
