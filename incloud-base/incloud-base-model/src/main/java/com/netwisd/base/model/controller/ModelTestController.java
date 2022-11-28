package com.netwisd.base.model.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.model.dto.ModelTestDto;
import com.netwisd.base.model.vo.ModelTestVo;
import com.netwisd.base.model.service.ModelTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 数据建模测试表 功能描述...
 * @author 云数网讯 sunzhenxi@netwisd.com
 * @date 2021-12-21 10:21:27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/modelTest" )
@Api(value = "modelTest", tags = "数据建模测试表Controller")
@Slf4j
public class ModelTestController {

    private final  ModelTestService modelTestService;

    /**
     * 分页查询数据建模测试表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param modelTestDto 数据建模测试表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody ModelTestDto modelTestDto) {
        Page pageVo = modelTestService.list(modelTestDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询数据建模测试表
     * @param modelTestDto 数据建模测试表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody ModelTestDto modelTestDto) {
        Page pageVo = modelTestService.lists(modelTestDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询数据建模测试表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<ModelTestVo> get(@PathVariable("id" ) Long id) {
        ModelTestVo modelTestVo = modelTestService.get(id);
        log.debug("查询成功");
        return Result.success(modelTestVo);
    }

    /**
     * 新增数据建模测试表
     * @param modelTestDto 数据建模测试表
     * @return Result
     */
    @ApiOperation(value = "新增数据建模测试表", notes = "新增数据建模测试表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody ModelTestDto modelTestDto) {
        Boolean result = modelTestService.save(modelTestDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改数据建模测试表
     * @param modelTestDto 数据建模测试表
     * @return Result
     */
    @ApiOperation(value = "修改数据建模测试表", notes = "修改数据建模测试表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody ModelTestDto modelTestDto) {
        Boolean result = modelTestService.update(modelTestDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除数据建模测试表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除数据建模测试表", notes = "通过id删除数据建模测试表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = modelTestService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
