package com.netwisd.base.wf.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.wf.dto.WfProcdefTypeDto;
import com.netwisd.base.wf.service.procdef.WfProcdefTypeService;
import com.netwisd.base.wf.vo.WfProcdefTypeVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 流程分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2020-07-01 14:45:51
 */
@RestController
@AllArgsConstructor
@RequestMapping("/wfprocdeftype" )
@Api(value = "wfprocdeftype", tags = "流程分类Controller")
@Slf4j
public class WfProcdefTypeController {

    private final WfProcdefTypeService wfProcdefTypeService;

    /**
     * 分页查询流程分类
     * 没有使用参数注解，就是默认从form请求的方式
     * @param wfProcdefTypeDto 流程分类
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody WfProcdefTypeDto wfProcdefTypeDto) {
        Page pageVo = wfProcdefTypeService.list(wfProcdefTypeDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询流程分类
     * @param wfProcdefTypeDto 流程分类
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result lists(@RequestBody WfProcdefTypeDto wfProcdefTypeDto) {
        List<WfProcdefTypeVo> list = wfProcdefTypeService.lists(wfProcdefTypeDto);
        return Result.success(list);
    }

    /**
     * 通过id查询流程分类
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<WfProcdefTypeVo> get(@PathVariable("id" ) Long id) {
        WfProcdefTypeVo wfProcdefTypeVo = wfProcdefTypeService.get(id);
        log.debug("查询成功");
        return Result.success(wfProcdefTypeVo);
    }

    /**
     * 新增按钮维护
     * @param wfProcdefTypeDto 流程分类
     * @return Result
     */
    @ApiOperation(value = "新增流程分类", notes = "新增流程分类")
    @PostMapping("/save")
    public Result<Boolean> save(@Validation @RequestBody WfProcdefTypeDto wfProcdefTypeDto) {
        Boolean result = wfProcdefTypeService.save(wfProcdefTypeDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改流程分类
     * @param wfProcdefTypeDto 流程分类
     * @return Result
     */
    @ApiOperation(value = "修改流程分类", notes = "修改流程分类")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody WfProcdefTypeDto wfProcdefTypeDto) {
        Boolean result = wfProcdefTypeService.update(wfProcdefTypeDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除流程分类
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "通过id删除流程分类", notes = "通过id删除流程分类")
    @DeleteMapping("/{ids}" )
    public Result<Boolean> delete(@PathVariable String ids) {
        Boolean result = wfProcdefTypeService.delete(ids);
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
    public Result<List<WfProcdefTypeVo>> get(@PathVariable("id" ) Long ... id) {
        List<Long> ids = new ArrayList<>();
        for (Long aLong : id) {
            ids.add(aLong);
        }
        List<WfProcdefTypeVo> wfProcdefTypeVoList = wfProcdefTypeService.selectBatchIds(ids);
        log.debug("查询成功");
        return Result.success(wfProcdefTypeVoList);
    }

}
