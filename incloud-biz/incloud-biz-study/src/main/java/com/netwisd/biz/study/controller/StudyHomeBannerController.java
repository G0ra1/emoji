package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyHomeBannerDto;
import com.netwisd.biz.study.vo.StudyHomeBannerVo;
import com.netwisd.biz.study.service.StudyHomeBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 在线学习轮播图表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-03-17 14:17:50
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyHomeBanner" )
@Api(value = "studyHomeBanner", tags = "在线学习轮播图表Controller")
@Slf4j
public class StudyHomeBannerController {

    private final  StudyHomeBannerService studyHomeBannerService;

    /**
     * 分页查询在线学习轮播图表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyHomeBannerDto 在线学习轮播图表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyHomeBannerDto studyHomeBannerDto) {
        Page pageVo = studyHomeBannerService.list(studyHomeBannerDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询在线学习轮播图表
     * @param studyHomeBannerDto 在线学习轮播图表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<List<StudyHomeBannerVo>> lists(@RequestBody StudyHomeBannerDto studyHomeBannerDto) {
        List<StudyHomeBannerVo> pageVo = studyHomeBannerService.lists(studyHomeBannerDto);
        log.debug("查询条数:"+pageVo.size());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询在线学习轮播图表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyHomeBannerVo> get(@PathVariable("id" ) Long id) {
        StudyHomeBannerVo studyHomeBannerVo = studyHomeBannerService.get(id);
        log.debug("查询成功");
        return Result.success(studyHomeBannerVo);
    }

    /**
     * 新增在线学习轮播图表
     * @param studyHomeBannerDto 在线学习轮播图表
     * @return Result
     */
    @ApiOperation(value = "新增在线学习轮播图表", notes = "新增在线学习轮播图表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyHomeBannerDto studyHomeBannerDto) {
        Boolean result = studyHomeBannerService.save(studyHomeBannerDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改在线学习轮播图表
     * @param studyHomeBannerDto 在线学习轮播图表
     * @return Result
     */
    @ApiOperation(value = "修改在线学习轮播图表", notes = "修改在线学习轮播图表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyHomeBannerDto studyHomeBannerDto) {
        Boolean result = studyHomeBannerService.update(studyHomeBannerDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除在线学习轮播图表
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "通过id删除在线学习轮播图表", notes = "通过id删除在线学习轮播图表")
    @DeleteMapping("/{ids}" )
    public Result<Boolean> delete(@PathVariable String ids) {
        Boolean result = studyHomeBannerService.delete(ids);
        log.debug("删除成功"+ids);
        return Result.success(result);
    }

}
