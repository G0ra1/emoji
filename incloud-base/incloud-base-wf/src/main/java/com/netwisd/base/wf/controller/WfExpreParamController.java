package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfExpreParamDto;
import com.netwisd.base.wf.vo.WfExpreParamVo;
import com.netwisd.base.wf.service.procdef.WfExpreParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 表达式参数维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:23:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfexpreparam" )
@Api(value = "wfexpreparam", tags = "表达式参数维护Controller")
@Slf4j
public class WfExpreParamController {

    private final  WfExpreParamService wfExpreParamService;

    /**
     * 分页查询表达式参数维护
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfExpreParamDto 表达式参数维护
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfExpreParamDto wfExpreParamDto) {
        Page pageVo = wfExpreParamService.list(wfExpreParamDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询表达式参数维护
     * @param wfExpreParamDto 表达式参数维护
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfExpreParamDto wfExpreParamDto) {
        Page pageVo = wfExpreParamService.lists(wfExpreParamDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询表达式参数维护
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfExpreParamVo> get(@PathVariable("id" ) Long id) {
        WfExpreParamVo wfExpreParamVo = wfExpreParamService.get(id);
        log.debug("查询成功");
        return Result.success(wfExpreParamVo);
    }

    /**
     * 新增表达式参数维护
     * @param wfExpreParamDto 表达式参数维护
     * @return Result
     */
    @ApiOperation(value = "新增表达式参数维护", notes = "新增表达式参数维护")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfExpreParamDto wfExpreParamDto) {
        Boolean result = wfExpreParamService.save(wfExpreParamDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改表达式参数维护
     * @param wfExpreParamDto 表达式参数维护
     * @return Result
     */
    @ApiOperation(value = "修改表达式参数维护", notes = "修改表达式参数维护")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfExpreParamDto wfExpreParamDto) {
        Boolean result = wfExpreParamService.update(wfExpreParamDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除表达式参数维护
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除表达式参数维护", notes = "通过id删除表达式参数维护")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfExpreParamService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
