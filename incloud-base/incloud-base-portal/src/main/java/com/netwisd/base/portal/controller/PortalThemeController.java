package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalThemeDto;
import com.netwisd.base.portal.vo.PortalThemeVo;
import com.netwisd.base.portal.service.PortalThemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description   主题管理 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-18 23:20:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalTheme" )
@Api(value = "portalTheme", tags = "  主题管理Controller")
@Slf4j
public class PortalThemeController {

    private final  PortalThemeService portalThemeService;

    /**
     * 分页查询  主题管理
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalThemeDto   主题管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalThemeDto portalThemeDto) {
        Page pageVo = portalThemeService.list(portalThemeDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询  主题管理
     * @param portalThemeDto   主题管理
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<List> lists(@RequestBody PortalThemeDto portalThemeDto) {
        List<PortalThemeVo> pageVo = portalThemeService.lists(portalThemeDto);
        log.debug("查询条数:"+pageVo.size());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询  主题管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalThemeVo> get(@PathVariable("id" ) Long id) {
        PortalThemeVo portalThemeVo = portalThemeService.get(id);
        log.debug("查询成功");
        return Result.success(portalThemeVo);
    }

    /**
     * 新增  主题管理
     * @param portalThemeDto   主题管理
     * @return Result
     */
    @ApiOperation(value = "新增  主题管理", notes = "新增  主题管理")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalThemeDto portalThemeDto) {
        Boolean result = portalThemeService.save(portalThemeDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改  主题管理
     * @param portalThemeDto   主题管理
     * @return Result
     */
    @ApiOperation(value = "修改  主题管理", notes = "修改  主题管理")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalThemeDto portalThemeDto) {
        Boolean result = portalThemeService.update(portalThemeDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除  主题管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除  主题管理", notes = "通过id删除  主题管理")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalThemeService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }
    /**
     * 切换主题
     *
     * @param id 主题切换
     * @return Result
     */
    @ApiOperation(value = "主题切换", notes = "主题切换")
    @GetMapping("switchTheme/{id}")

    public Result<Boolean> switchTheme(@PathVariable("id") Long id) {
        Boolean result = portalThemeService.switchTheme(id);
        log.debug("主题切换");
        return Result.success(result);
    }


}
