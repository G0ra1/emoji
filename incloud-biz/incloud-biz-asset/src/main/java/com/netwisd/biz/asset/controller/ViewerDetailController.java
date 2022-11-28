package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.ViewerDetailDto;
import com.netwisd.biz.asset.vo.ViewerDetailVo;
import com.netwisd.biz.asset.service.ViewerDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 物资数据权限范围表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-08-01 10:14:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/viewerDetail" )
@Api(value = "viewerDetail", tags = "物资数据权限范围表Controller")
@Slf4j
public class ViewerDetailController {

    private final  ViewerDetailService viewerDetailService;

    /**
     * 分页查询物资数据权限范围表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param viewerDetailDto 物资数据权限范围表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody ViewerDetailDto viewerDetailDto) {
        Page pageVo = viewerDetailService.list(viewerDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询物资数据权限范围表
     * @param viewerDetailDto 物资数据权限范围表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody ViewerDetailDto viewerDetailDto) {
        Page pageVo = viewerDetailService.lists(viewerDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询物资数据权限范围表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<ViewerDetailVo> get(@PathVariable("id" ) Long id) {
        ViewerDetailVo viewerDetailVo = viewerDetailService.get(id);
        log.debug("查询成功");
        return Result.success(viewerDetailVo);
    }

    /**
     * 新增物资数据权限范围表
     * @param viewerDetailDto 物资数据权限范围表
     * @return Result
     */
    @ApiOperation(value = "新增物资数据权限范围表", notes = "新增物资数据权限范围表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody ViewerDetailDto viewerDetailDto) {
        viewerDetailService.save(viewerDetailDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改物资数据权限范围表
     * @param viewerDetailDto 物资数据权限范围表
     * @return Result
     */
    @ApiOperation(value = "修改物资数据权限范围表", notes = "修改物资数据权限范围表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody ViewerDetailDto viewerDetailDto) {
        viewerDetailService.update(viewerDetailDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除物资数据权限范围表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除物资数据权限范围表", notes = "通过id删除物资数据权限范围表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        viewerDetailService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

}
