package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfExpreUserParamDefDto;
import com.netwisd.base.wf.vo.WfExpreUserParamDefVo;
import com.netwisd.base.wf.service.procdef.WfExpreUserParamDefService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 人员表达式定义 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-07-14 11:49:34
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfexpreuserparamdef" )
@Api(value = "wfexpreuserparamdef", tags = "人员表达式定义Controller")
@Slf4j
public class WfExpreUserParamDefController {

    private final  WfExpreUserParamDefService wfExpreUserParamDefService;

    /**
     * 分页查询人员表达式定义
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfExpreUserParamDefDto 人员表达式定义
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfExpreUserParamDefDto wfExpreUserParamDefDto) {
        Page pageVo = wfExpreUserParamDefService.list(wfExpreUserParamDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询人员表达式定义
     * @param wfExpreUserParamDefDto 人员表达式定义
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfExpreUserParamDefDto wfExpreUserParamDefDto) {
        Page pageVo = wfExpreUserParamDefService.lists(wfExpreUserParamDefDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询人员表达式定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfExpreUserParamDefVo> get(@PathVariable("id" ) Long id) {
        WfExpreUserParamDefVo wfExpreUserParamDefVo = wfExpreUserParamDefService.get(id);
        log.debug("查询成功");
        return Result.success(wfExpreUserParamDefVo);
    }

    /**
     * 新增人员表达式定义
     * @param wfExpreUserParamDefDto 人员表达式定义
     * @return Result
     */
    @ApiOperation(value = "新增人员表达式定义", notes = "新增人员表达式定义")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody WfExpreUserParamDefDto wfExpreUserParamDefDto) {
        Boolean result = wfExpreUserParamDefService.save(wfExpreUserParamDefDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改人员表达式定义
     * @param wfExpreUserParamDefDto 人员表达式定义
     * @return Result
     */
    @ApiOperation(value = "修改人员表达式定义", notes = "修改人员表达式定义")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfExpreUserParamDefDto wfExpreUserParamDefDto) {
        Boolean result = wfExpreUserParamDefService.update(wfExpreUserParamDefDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除人员表达式定义
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除人员表达式定义", notes = "通过id删除人员表达式定义")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = wfExpreUserParamDefService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
