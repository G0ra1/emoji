package com.netwisd.biz.study.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.study.dto.StudyUserApplyDto;
import com.netwisd.biz.study.vo.StudyUserApplyVo;
import com.netwisd.biz.study.service.StudyUserApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 在线学习人员申请表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-03-23 16:06:26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/studyUserApply" )
@Api(value = "studyUserApply", tags = "在线学习人员申请表Controller")
@Slf4j
public class StudyUserApplyController {

    private final  StudyUserApplyService studyUserApplyService;

    /**
     * 分页查询在线学习人员申请表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param studyUserApplyDto 在线学习人员申请表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody StudyUserApplyDto studyUserApplyDto) {
        Page pageVo = studyUserApplyService.list(studyUserApplyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 集合查询在线学习人员申请表
     * @param studyUserApplyDto 在线学习人员申请表
     * @return
     */
    @ApiOperation(value = "集合查询在线学习人员申请表", notes = "集合查询在线学习人员申请表")
    @PostMapping("/lists" )
    public Result<List<StudyUserApplyVo>> lists(@RequestBody StudyUserApplyDto studyUserApplyDto) {
        List<StudyUserApplyVo> studyUserApplyVos = studyUserApplyService.lists(studyUserApplyDto);
        return Result.success(studyUserApplyVos);
    }

    /**
     * 通过id查询在线学习人员申请表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<StudyUserApplyVo> get(@PathVariable("id" ) Long id) {
        StudyUserApplyVo studyUserApplyVo = studyUserApplyService.get(id);
        log.debug("查询成功");
        return Result.success(studyUserApplyVo);
    }

    /**
     * 新增在线学习人员申请表
     * @param studyUserApplyDto 在线学习人员申请表
     * @return Result
     */
    @ApiOperation(value = "新增在线学习人员申请表", notes = "新增在线学习人员申请表")
    @PostMapping
    public Result<Boolean> save(@RequestBody StudyUserApplyDto studyUserApplyDto) {
        Boolean result = studyUserApplyService.save(studyUserApplyDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改在线学习人员申请表
     * @param studyUserApplyDtos 在线学习人员申请表
     * @return Result
     */
    @ApiOperation(value = "修改在线学习人员申请表", notes = "修改在线学习人员申请表")
    @PutMapping
    public Result<Boolean> update(@RequestBody List<StudyUserApplyDto> studyUserApplyDtos) {
        Boolean result = studyUserApplyService.update(studyUserApplyDtos);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 批量通过id删除在线学习人员申请表
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "批量通过id删除在线学习人员申请表", notes = "批量通过id删除在线学习人员申请表")
    @DeleteMapping("/{ids}" )
    public Result<Boolean> delete(@PathVariable String ids) {
        Boolean result = studyUserApplyService.delete(ids);
        log.debug("删除成功");
        return Result.success(result);
    }

}
