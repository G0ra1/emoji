package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentPicturesSonDto;
import com.netwisd.base.portal.vo.PortalContentPicturesSonVo;
import com.netwisd.base.portal.service.PortalContentPicturesSonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 图片轮播类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 08:55:53
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentPicturesSon" )
@Api(value = "portalContentPicturesSon", tags = "图片轮播类内容发布子表Controller")
@Slf4j
public class PortalContentPicturesSonController {

    private final  PortalContentPicturesSonService portalContentPicturesSonService;

    /**
     * 分页查询图片轮播类内容发布子表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentPicturesSonDto 图片轮播类内容发布子表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentPicturesSonDto portalContentPicturesSonDto) {
        Page pageVo = portalContentPicturesSonService.list(portalContentPicturesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询图片轮播类内容发布子表
     * @param portalContentPicturesSonDto 图片轮播类内容发布子表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentPicturesSonDto portalContentPicturesSonDto) {
        Page pageVo = portalContentPicturesSonService.lists(portalContentPicturesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询图片轮播类内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentPicturesSonVo> get(@PathVariable("id" ) Long id) {
        PortalContentPicturesSonVo portalContentPicturesSonVo = portalContentPicturesSonService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentPicturesSonVo);
    }

    /**
     * 新增图片轮播类内容发布子表
     * @param portalContentPicturesSonDto 图片轮播类内容发布子表
     * @return Result
     */
    @ApiOperation(value = "新增图片轮播类内容发布子表", notes = "新增图片轮播类内容发布子表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentPicturesSonDto portalContentPicturesSonDto) {
        Boolean result = portalContentPicturesSonService.save(portalContentPicturesSonDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改图片轮播类内容发布子表
     * @param portalContentPicturesSonDto 图片轮播类内容发布子表
     * @return Result
     */
    @ApiOperation(value = "修改图片轮播类内容发布子表", notes = "修改图片轮播类内容发布子表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentPicturesSonDto portalContentPicturesSonDto) {
        Boolean result = portalContentPicturesSonService.update(portalContentPicturesSonDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除图片轮播类内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除图片轮播类内容发布子表", notes = "通过id删除图片轮播类内容发布子表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentPicturesSonService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
