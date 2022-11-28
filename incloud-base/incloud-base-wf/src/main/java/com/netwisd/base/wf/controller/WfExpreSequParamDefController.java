package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfExpreSequParamDefDto;
import com.netwisd.base.wf.vo.WfExpreSequParamDefVo;
import com.netwisd.base.wf.service.procdef.WfExpreSequParamDefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 流程定义-序列流-表达式-参数 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-21 19:00:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfexpresequparamdef" )
@Api(value = "wfexpresequparamdef", tags = "流程定义-序列流-表达式-参数Controller")
@Slf4j
public class WfExpreSequParamDefController {

    private final  WfExpreSequParamDefService wfExpreSequParamDefService;

    /**
     * 分页查询流程定义-序列流-表达式-参数
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfExpreSequParamDefDto 流程定义-序列流-表达式-参数
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfExpreSequParamDefDto wfExpreSequParamDefDto) {
        Page pageVo = wfExpreSequParamDefService.list(wfExpreSequParamDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询流程定义-序列流-表达式-参数
     * @param wfExpreSequParamDefDto 流程定义-序列流-表达式-参数
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfExpreSequParamDefDto wfExpreSequParamDefDto) {
        Page pageVo = wfExpreSequParamDefService.lists(wfExpreSequParamDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询流程定义-序列流-表达式-参数
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfExpreSequParamDefVo> get(@PathVariable("id" ) Long id) {
        WfExpreSequParamDefVo wfExpreSequParamDefVo = wfExpreSequParamDefService.get(id);
        log.debug("查询成功");
        return Result.success(wfExpreSequParamDefVo);
    }

    /**
     * 新增流程定义-序列流-表达式-参数
     * @param wfExpreSequParamDefDto 流程定义-序列流-表达式-参数
     * @return Result
     */
    @ApiOperation(value = "新增流程定义-序列流-表达式-参数", notes = "新增流程定义-序列流-表达式-参数")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfExpreSequParamDefDto wfExpreSequParamDefDto) {
        Boolean result = wfExpreSequParamDefService.save(wfExpreSequParamDefDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改流程定义-序列流-表达式-参数
     * @param wfExpreSequParamDefDto 流程定义-序列流-表达式-参数
     * @return Result
     */
    @ApiOperation(value = "修改流程定义-序列流-表达式-参数", notes = "修改流程定义-序列流-表达式-参数")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfExpreSequParamDefDto wfExpreSequParamDefDto) {
        Boolean result = wfExpreSequParamDefService.update(wfExpreSequParamDefDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程定义-序列流-表达式-参数
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程定义-序列流-表达式-参数", notes = "通过id删除流程定义-序列流-表达式-参数")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfExpreSequParamDefService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
