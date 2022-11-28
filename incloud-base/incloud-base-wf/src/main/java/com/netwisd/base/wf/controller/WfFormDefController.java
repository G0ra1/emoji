package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfFormDefDto;
import com.netwisd.base.wf.vo.WfFormDefVo;
import com.netwisd.base.wf.service.other.WfFormDefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 流程表单定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-09 17:31:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfformdef" )
@Api(value = "wfformdef", tags = "流程表单定义Controller")
@Slf4j
public class WfFormDefController {

    private final  WfFormDefService wfFormDefService;

    /**
     * 分页查询流程表单定义
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfFormDefDto 流程表单定义
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfFormDefDto wfFormDefDto) {
        Page pageVo = wfFormDefService.list(wfFormDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询流程表单定义
     * @param wfFormDefDto 流程表单定义
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result lists(@RequestBody WfFormDefDto wfFormDefDto) {
        return Result.success(wfFormDefService.lists(wfFormDefDto));
    }

    /**
     * 通过id查询流程表单定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfFormDefVo> get(@PathVariable("id" ) Long id) {
        WfFormDefVo wfFormDefVo = wfFormDefService.get(id);
        log.debug("查询成功");
        return Result.success(wfFormDefVo);
    }

    /**
     * 新增流程表单定义
     * @param wfFormDefDto 流程表单定义
     * @return Result
     */
    @ApiOperation(value = "新增流程表单定义", notes = "新增流程表单定义")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfFormDefDto wfFormDefDto) {
        Boolean result = wfFormDefService.save(wfFormDefDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改流程表单定义
     * @param wfFormDefDto 流程表单定义
     * @return Result
     */
    @ApiOperation(value = "修改流程表单定义", notes = "修改流程表单定义")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfFormDefDto wfFormDefDto) {
        Boolean result = wfFormDefService.update(wfFormDefDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程表单定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程表单定义", notes = "通过id删除流程表单定义")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfFormDefService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
