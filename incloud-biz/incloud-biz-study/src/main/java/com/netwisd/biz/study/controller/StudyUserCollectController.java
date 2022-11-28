package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyUserCollectDto;
import com.netwisd.biz.study.vo.StudyUserCollectVo;
import com.netwisd.biz.study.service.StudyUserCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 人员收藏表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-04-27 11:16:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyUserCollect" )
@Api(value = "studyUserCollect", tags = "人员收藏表Controller")
@Slf4j
public class StudyUserCollectController {

    private final  StudyUserCollectService studyUserCollectService;

    /**
     * 分页查询人员收藏表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyUserCollectDto 人员收藏表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyUserCollectDto studyUserCollectDto) {
        Page pageVo = studyUserCollectService.list(studyUserCollectDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询人员收藏表
     * @param studyUserCollectDto 人员收藏表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody StudyUserCollectDto studyUserCollectDto) {
        Page pageVo = studyUserCollectService.lists(studyUserCollectDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询人员收藏表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyUserCollectVo> get(@PathVariable("id" ) Long id) {
        StudyUserCollectVo studyUserCollectVo = studyUserCollectService.get(id);
        log.debug("查询成功");
        return Result.success(studyUserCollectVo);
    }

    /**
     * 新增人员收藏表
     * @param studyUserCollectDto 人员收藏表
     * @return Result
     */
    @ApiOperation(value = "新增人员收藏表", notes = "新增人员收藏表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyUserCollectDto studyUserCollectDto) {
        Boolean result = studyUserCollectService.save(studyUserCollectDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改人员收藏表
     * @param studyUserCollectDto 人员收藏表
     * @return Result
     */
    @ApiOperation(value = "修改人员收藏表", notes = "修改人员收藏表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyUserCollectDto studyUserCollectDto) {
        Boolean result = studyUserCollectService.update(studyUserCollectDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除人员收藏表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除人员收藏表", notes = "通过id删除人员收藏表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = studyUserCollectService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
