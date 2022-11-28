package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.vo.WfFormFieldsDefVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfFormFieldsDefDto;
import com.netwisd.base.wf.service.other.WfFormFieldsDefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 流程表单字段定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:43:44
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfformvardef" )
@Api(value = "wfformvardef", tags = "流程表单字段定义Controller")
@Slf4j
public class WfFormFieldsDefController {

    private final WfFormFieldsDefService wfFormFieldsDefService;

    /**
     * 分页查询流程表单字段定义
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfFormFieldsDefDto 流程表单字段定义
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfFormFieldsDefDto wfFormFieldsDefDto) {
        Page pageVo = wfFormFieldsDefService.list(wfFormFieldsDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询流程表单字段定义
     * @param wfFormFieldsDefDto 流程表单字段定义
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfFormFieldsDefDto wfFormFieldsDefDto) {
        Page pageVo = wfFormFieldsDefService.lists(wfFormFieldsDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询流程表单字段定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfFormFieldsDefVo> get(@PathVariable("id" ) Long id) {
        WfFormFieldsDefVo wfFormFieldsDefVo = wfFormFieldsDefService.get(id);
        log.debug("查询成功");
        return Result.success(wfFormFieldsDefVo);
    }

    /**
     * 新增流程表单字段定义
     * @param wfFormFieldsDefDto 流程表单字段定义
     * @return Result
     */
    @ApiOperation(value = "新增流程表单字段定义", notes = "新增流程表单字段定义")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfFormFieldsDefDto wfFormFieldsDefDto) {
        Boolean result = wfFormFieldsDefService.save(wfFormFieldsDefDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改流程表单字段定义
     * @param wfFormFieldsDefDto 流程表单字段定义
     * @return Result
     */
    @ApiOperation(value = "修改流程表单字段定义", notes = "修改流程表单字段定义")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfFormFieldsDefDto wfFormFieldsDefDto) {
        Boolean result = wfFormFieldsDefService.update(wfFormFieldsDefDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程表单字段定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程表单字段定义", notes = "通过id删除流程表单字段定义")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfFormFieldsDefService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
