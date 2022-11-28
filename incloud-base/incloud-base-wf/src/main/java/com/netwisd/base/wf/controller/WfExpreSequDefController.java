package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfExpreSequDefDto;
import com.netwisd.base.wf.vo.WfExpreSequDefVo;
import com.netwisd.base.wf.service.procdef.WfExpreSequDefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 流程定义-序列流-表达式 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 18:59:28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfexpresequdef" )
@Api(value = "wfexpresequdef", tags = "流程定义-序列流-表达式Controller")
@Slf4j
public class WfExpreSequDefController {

    private final  WfExpreSequDefService wfExpreSequDefService;

    /**
     * 分页查询流程定义-序列流-表达式
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfExpreSequDefDto 流程定义-序列流-表达式
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfExpreSequDefDto wfExpreSequDefDto) {
        Page pageVo = wfExpreSequDefService.list(wfExpreSequDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询流程定义-序列流-表达式
     * @param wfExpreSequDefDto 流程定义-序列流-表达式
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfExpreSequDefDto wfExpreSequDefDto) {
        Page pageVo = wfExpreSequDefService.lists(wfExpreSequDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询流程定义-序列流-表达式
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfExpreSequDefVo> get(@PathVariable("id" ) Long id) {
        WfExpreSequDefVo wfExpreSequDefVo = wfExpreSequDefService.get(id);
        log.debug("查询成功");
        return Result.success(wfExpreSequDefVo);
    }

    /**
     * 新增流程定义-序列流-表达式
     * @param wfExpreSequDefDto 流程定义-序列流-表达式
     * @return Result
     */
    @ApiOperation(value = "新增流程定义-序列流-表达式", notes = "新增流程定义-序列流-表达式")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfExpreSequDefDto wfExpreSequDefDto) {
        Boolean result = wfExpreSequDefService.save(wfExpreSequDefDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改流程定义-序列流-表达式
     * @param wfExpreSequDefDto 流程定义-序列流-表达式
     * @return Result
     */
    @ApiOperation(value = "修改流程定义-序列流-表达式", notes = "修改流程定义-序列流-表达式")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfExpreSequDefDto wfExpreSequDefDto) {
        Boolean result = wfExpreSequDefService.update(wfExpreSequDefDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程定义-序列流-表达式
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程定义-序列流-表达式", notes = "通过id删除流程定义-序列流-表达式")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfExpreSequDefService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }
}
