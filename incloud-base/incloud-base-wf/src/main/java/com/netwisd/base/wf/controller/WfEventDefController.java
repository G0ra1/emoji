package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfEventDefDto;
import com.netwisd.base.wf.vo.WfEventDefVo;
import com.netwisd.base.wf.service.procdef.WfEventDefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 事件定义 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 16:45:46
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfeventdef" )
@Api(value = "wfeventdef", tags = "事件定义Controller")
@Slf4j
public class WfEventDefController {

    private final  WfEventDefService wfEventDefService;

    /**
     * 分页查询事件定义
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfEventDefDto 事件定义
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfEventDefDto wfEventDefDto) {
        Page pageVo = wfEventDefService.list(wfEventDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询事件定义
     * @param wfEventDefDto 事件定义
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfEventDefDto wfEventDefDto) {
        Page pageVo = wfEventDefService.lists(wfEventDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询事件定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfEventDefVo> get(@PathVariable("id" ) Long id) {
        WfEventDefVo wfEventDefVo = wfEventDefService.get(id);
        log.debug("查询成功");
        return Result.success(wfEventDefVo);
    }

    /**
     * 新增事件定义
     * @param wfEventDefDto 事件定义
     * @return Result
     */
    @ApiOperation(value = "新增事件定义", notes = "新增事件定义")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfEventDefDto wfEventDefDto) {
        Boolean result = wfEventDefService.save(wfEventDefDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改事件定义
     * @param wfEventDefDto 事件定义
     * @return Result
     */
    @ApiOperation(value = "修改事件定义", notes = "修改事件定义")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfEventDefDto wfEventDefDto) {
        Boolean result = wfEventDefService.update(wfEventDefDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除事件定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除事件定义", notes = "通过id删除事件定义")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfEventDefService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
