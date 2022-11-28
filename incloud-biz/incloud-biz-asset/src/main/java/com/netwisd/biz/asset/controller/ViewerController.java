package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.ViewerDto;
import com.netwisd.biz.asset.vo.ViewerVo;
import com.netwisd.biz.asset.service.ViewerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 物资数据权限人员表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-08-01 10:14:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/viewer" )
@Api(value = "viewer", tags = "物资数据权限人员表Controller")
@Slf4j
public class ViewerController {

    private final  ViewerService viewerService;

    /**
     * 分页查询物资数据权限人员表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param viewerDto 物资数据权限人员表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody ViewerDto viewerDto) {
        Page pageVo = viewerService.list(viewerDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询物资数据权限人员表
     * @param viewerDto 物资数据权限人员表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody ViewerDto viewerDto) {
        Page pageVo = viewerService.lists(viewerDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询物资数据权限人员表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<ViewerVo> get(@PathVariable("id" ) Long id) {
        ViewerVo viewerVo = viewerService.get(id);
        log.debug("查询成功");
        return Result.success(viewerVo);
    }

    /**
     * 新增物资数据权限人员表
     * @param viewerDto 物资数据权限人员表
     * @return Result
     */
    @ApiOperation(value = "新增物资数据权限人员表", notes = "新增物资数据权限人员表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody ViewerDto viewerDto) {
        viewerService.save(viewerDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改物资数据权限人员表
     * @param viewerDto 物资数据权限人员表
     * @return Result
     */
    @ApiOperation(value = "修改物资数据权限人员表", notes = "修改物资数据权限人员表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody ViewerDto viewerDto) {
        viewerService.update(viewerDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除物资数据权限人员表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除物资数据权限人员表", notes = "通过id删除物资数据权限人员表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        viewerService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

}
