package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyNewsDto;
import com.netwisd.biz.study.vo.StudyNewsVo;
import com.netwisd.biz.study.service.StudyNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 在线学习通知公告表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-03-15 16:37:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyNews" )
@Api(value = "studyNews", tags = "在线学习通知公告表Controller")
@Slf4j
public class StudyNewsController {

    private final  StudyNewsService studyNewsService;

    /**
     * 分页查询在线学习通知公告表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyNewsDto 在线学习通知公告表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyNewsDto studyNewsDto) {
        Page pageVo = studyNewsService.list(studyNewsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询在线学习通知公告表
     * @param studyNewsDto 在线学习通知公告表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<List<StudyNewsVo>> lists(@RequestBody StudyNewsDto studyNewsDto) {
        List<StudyNewsVo> pageVo = studyNewsService.lists(studyNewsDto);
        log.debug("查询条数:"+pageVo.size());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询在线学习通知公告表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyNewsVo> get(@PathVariable("id" ) Long id) {
        StudyNewsVo studyNewsVo = studyNewsService.get(id);
        log.debug("查询成功");
        return Result.success(studyNewsVo);
    }

    /**
     * 新增在线学习通知公告表
     * @param studyNewsDto 在线学习通知公告表
     * @return Result
     */
    @ApiOperation(value = "新增在线学习通知公告表", notes = "新增在线学习通知公告表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody StudyNewsDto studyNewsDto) {
        Boolean result = studyNewsService.save(studyNewsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改在线学习通知公告表
     * @param studyNewsDto 在线学习通知公告表
     * @return Result
     */
    @ApiOperation(value = "修改在线学习通知公告表", notes = "修改在线学习通知公告表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody StudyNewsDto studyNewsDto) {
        Boolean result = studyNewsService.update(studyNewsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除在线学习通知公告表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除在线学习通知公告表", notes = "通过id删除在线学习通知公告表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = studyNewsService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }
    /**
     * 修改浏览量
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "修改浏览量",notes = "修改浏览量")
    @GetMapping("/upHits")
    public Result<Boolean> upHits(@RequestParam("id") Long id){
        Boolean result = studyNewsService.upHits(id);
        log.debug("浏览量+1");
        return  Result.success(result);
    }
}
