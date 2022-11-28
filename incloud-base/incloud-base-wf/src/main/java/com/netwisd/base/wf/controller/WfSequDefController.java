package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfSequDefDto;
import com.netwisd.base.wf.vo.WfSequDefVo;
import com.netwisd.base.wf.service.procdef.WfSequDefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 流程定义-序列流 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 16:21:33
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfsequdef" )
@Api(value = "wfsequdef", tags = "流程定义-序列流Controller")
@Slf4j
public class WfSequDefController {

    private final  WfSequDefService wfSequDefService;

    /**
     * 分页查询流程定义-序列流
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfSequDefDto 流程定义-序列流
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfSequDefDto wfSequDefDto) {
        Page pageVo = wfSequDefService.list(wfSequDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询流程定义-序列流
     * @param wfSequDefDto 流程定义-序列流
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfSequDefDto wfSequDefDto) {
        Page pageVo = wfSequDefService.lists(wfSequDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询流程定义-序列流
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfSequDefVo> get(@PathVariable("id" ) Long id) {
        WfSequDefVo wfSequDefVo = wfSequDefService.get(id);
        log.debug("查询成功");
        return Result.success(wfSequDefVo);
    }

    /**
     * 新增流程定义-序列流
     * @param wfSequDefDto 流程定义-序列流
     * @return Result
     */
    @ApiOperation(value = "新增流程定义-序列流", notes = "新增流程定义-序列流")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfSequDefDto wfSequDefDto) {
        Boolean result = wfSequDefService.save(wfSequDefDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改流程定义-序列流
     * @param wfSequDefDto 流程定义-序列流
     * @return Result
     */
    @ApiOperation(value = "修改流程定义-序列流", notes = "修改流程定义-序列流")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfSequDefDto wfSequDefDto) {
        Boolean result = wfSequDefService.update(wfSequDefDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程定义-序列流
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程定义-序列流", notes = "通过id删除流程定义-序列流")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfSequDefService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
