package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentLoginBackgroundSonDto;
import com.netwisd.base.portal.vo.PortalContentLoginBackgroundSonVo;
import com.netwisd.base.portal.service.PortalContentLoginBackgroundSonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 登录页-子表（背景轮播图） 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2021-12-27 17:03:27
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentLoginBackgroundSon" )
@Api(value = "portalContentLoginBackgroundSon", tags = "登录页-子表（背景轮播图）Controller")
@Slf4j
public class PortalContentLoginBackgroundSonController {

    private final  PortalContentLoginBackgroundSonService portalContentLoginBackgroundSonService;

    /**
     * 分页查询登录页-子表（背景轮播图）
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentLoginBackgroundSonDto 登录页-子表（背景轮播图）
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto) {
        Page pageVo = portalContentLoginBackgroundSonService.getList(portalContentLoginBackgroundSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询登录页-子表（背景轮播图）
     * @param portalContentLoginBackgroundSonDto 登录页-子表（背景轮播图）
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto) {
        Page pageVo = portalContentLoginBackgroundSonService.lists(portalContentLoginBackgroundSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询登录页-子表（背景轮播图）
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentLoginBackgroundSonVo> get(@PathVariable("id" ) Long id) {
        PortalContentLoginBackgroundSonVo portalContentLoginBackgroundSonVo = portalContentLoginBackgroundSonService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentLoginBackgroundSonVo);
    }

    /**
     * 新增登录页-子表（背景轮播图）
     * @param portalContentLoginBackgroundSonDto 登录页-子表（背景轮播图）
     * @return Result
     */
    @ApiOperation(value = "新增登录页-子表（背景轮播图）", notes = "新增登录页-子表（背景轮播图）")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto) {
        Boolean result = portalContentLoginBackgroundSonService.save(portalContentLoginBackgroundSonDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改登录页-子表（背景轮播图）
     * @param portalContentLoginBackgroundSonDto 登录页-子表（背景轮播图）
     * @return Result
     */
    @ApiOperation(value = "修改登录页-子表（背景轮播图）", notes = "修改登录页-子表（背景轮播图）")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentLoginBackgroundSonDto portalContentLoginBackgroundSonDto) {
        Boolean result = portalContentLoginBackgroundSonService.update(portalContentLoginBackgroundSonDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除登录页-子表（背景轮播图）
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除登录页-子表（背景轮播图）", notes = "通过id删除登录页-子表（背景轮播图）")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentLoginBackgroundSonService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
