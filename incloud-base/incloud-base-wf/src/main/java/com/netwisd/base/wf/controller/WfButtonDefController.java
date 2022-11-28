package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfButtonDefDto;
import com.netwisd.base.wf.vo.WfButtonDefVo;
import com.netwisd.base.wf.service.other.WfButtonDefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 流程定义-按钮 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-15 22:26:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfbuttondef" )
@Api(value = "wfbuttondef", tags = "流程定义-按钮Controller")
@Slf4j
public class WfButtonDefController {

    private final  WfButtonDefService wfButtonDefService;

    /**
     * 分页查询流程定义-按钮
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfButtonDefDto 流程定义-按钮
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfButtonDefDto wfButtonDefDto) {
        Page pageVo = wfButtonDefService.list(wfButtonDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询流程定义-按钮
     * @param wfButtonDefDto 流程定义-按钮
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfButtonDefDto wfButtonDefDto) {
        Page pageVo = wfButtonDefService.lists(wfButtonDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询流程定义-按钮
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfButtonDefVo> get(@PathVariable("id" ) Long id) {
        WfButtonDefVo wfButtonDefVo = wfButtonDefService.get(id);
        log.debug("查询成功");
        return Result.success(wfButtonDefVo);
    }

    /**
     * 新增流程定义-按钮
     * @param wfButtonDefDto 流程定义-按钮
     * @return Result
     */
    @ApiOperation(value = "新增流程定义-按钮", notes = "新增流程定义-按钮")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfButtonDefDto wfButtonDefDto) {
        Boolean result = wfButtonDefService.save(wfButtonDefDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改流程定义-按钮
     * @param wfButtonDefDto 流程定义-按钮
     * @return Result
     */
    @ApiOperation(value = "修改流程定义-按钮", notes = "修改流程定义-按钮")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfButtonDefDto wfButtonDefDto) {
        Boolean result = wfButtonDefService.update(wfButtonDefDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程定义-按钮
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程定义-按钮", notes = "通过id删除流程定义-按钮")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfButtonDefService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
