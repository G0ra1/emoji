package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.wf.dto.WfExpreDto;
import com.netwisd.base.wf.vo.WfExpreVo;
import com.netwisd.base.wf.service.procdef.WfExpreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 表达式维护 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-06-30 11:22:57
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfexpre" )
@Api(value = "wfexpre", tags = "表达式维护Controller")
@Slf4j
public class WfExpreController {

    private final  WfExpreService wfExpreService;



    /**
     * 分页查询表达式维护
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfExpreDto 表达式维护
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfExpreDto wfExpreDto) {
        Page pageVo = wfExpreService.list(wfExpreDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询表达式维护
     * @param wfExpreDto 表达式维护
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody WfExpreDto wfExpreDto) {
        Page pageVo = wfExpreService.lists(wfExpreDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询表达式维护
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfExpreVo> get(@PathVariable("id" ) Long id) {
        WfExpreVo wfExpreVo = wfExpreService.get(id);
        log.debug("查询成功");
        return Result.success(wfExpreVo);
    }

    /**
     * 新增表达式维护
     * @param wfExpreDto 表达式维护
     * @return Result
     */
    @ApiOperation(value = "新增表达式维护", notes = "新增表达式维护")
    @PostMapping("/save")
    public Result<Boolean> save(@Validation @RequestBody WfExpreDto wfExpreDto) {
        Boolean result = wfExpreService.save(wfExpreDto);
        if(!result) {
            return Result.failed("系统异常");
        }
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改表达式维护
     * @param wfExpreDto 表达式维护
     * @return Result
     */
    @ApiOperation(value = "修改表达式维护", notes = "修改表达式维护")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfExpreDto wfExpreDto) {
        Boolean result = wfExpreService.update(wfExpreDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除表达式维护
     * @param ids id
     * @return Result
     */
    @ApiOperation(value = "通过id删除表达式维护", notes = "通过id删除表达式维护")
    @DeleteMapping("/{ids}" )
    public Result<Boolean> delete(@PathVariable String ids) {
        Boolean result = wfExpreService.delete(ids);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通过多个id查询按钮维护
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过多个id查询按钮维护", notes = "通过多个id查询按钮维护")
    @GetMapping("/multipId/{id}" )
    public Result<List<WfExpreVo>> get(@PathVariable("id" ) Long ... id) {
        List<Long> ids = new ArrayList<>();
        for (Long aLong : id) {
            ids.add(aLong);
        }
        List<WfExpreVo> wfExpreVoList = wfExpreService.selectBatchIds(ids);
        log.debug("查询成功");
        return Result.success(wfExpreVoList);
    }

//    /**
//     * 获取分级中定义表达式信息
//     * @return Result
//     */
//    @ApiOperation(value = "获取分级中定义表达式信息", notes = "获取分级中定义表达式信息")
//    @GetMapping("/getExpressionList" )
//    public Result getExpressionList() {
//
//        return wfExpreService.getExpressionList();
//    }

}
