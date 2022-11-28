package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentLoginDto;
import com.netwisd.base.portal.vo.PortalContentLoginVo;
import com.netwisd.base.portal.service.PortalContentLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 登录页设置表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2021-12-27 16:36:19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentLogin" )
@Api(value = "portalContentLogin", tags = "登录页设置表Controller")
@Slf4j
public class PortalContentLoginController {

    private final  PortalContentLoginService portalContentLoginService;

    /**
     * 分页查询登录页设置表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentLoginDto 登录页设置表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentLoginDto portalContentLoginDto) {
        Page pageVo = portalContentLoginService.list(portalContentLoginDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询登录页设置表
     * @param portalContentLoginDto 登录页设置表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentLoginDto portalContentLoginDto) {
        Page pageVo = portalContentLoginService.lists(portalContentLoginDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询登录页设置表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentLoginVo> get(@PathVariable("id" ) Long id) {
        PortalContentLoginVo portalContentLoginVo = portalContentLoginService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentLoginVo);
    }

    /**
     * 新增登录页设置表
     * @param portalContentLoginDto 登录页设置表
     * @return Result
     */
    @ApiOperation(value = "新增登录页设置表", notes = "新增登录页设置表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentLoginDto portalContentLoginDto) {
        Boolean result = portalContentLoginService.save(portalContentLoginDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改登录页设置表
     * @param portalContentLoginDto 登录页设置表
     * @return Result
     */
    @ApiOperation(value = "修改登录页设置表", notes = "修改登录页设置表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentLoginDto portalContentLoginDto) {
        Boolean result = portalContentLoginService.update(portalContentLoginDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除登录页设置表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除登录页设置表", notes = "通过id删除登录页设置表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentLoginService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }
    /**
     * 设置启用
     * @param id
     * @return Result
     */
    @ApiOperation(value = "设置启用", notes = "设置启用")
    @GetMapping("/isEnable/{id}" )
    public Result<Boolean> isEnable(@PathVariable Long id) {
        Boolean result = portalContentLoginService.isEnable(id);
        log.debug("启用成功");
        return Result.success(result);
    }

    /**
     * 登录前调用
     * @param
     * @return Result
     */
    @ApiOperation(value = "登录前调用", notes = "登录前调用")
    @GetMapping("/getLog" )
    public Result<PortalContentLoginVo> isEnable() {
        PortalContentLoginVo getLog = portalContentLoginService.getLog();
        PortalContentLoginController.log.debug("加载资源成功");
        return Result.success(getLog);
    }
}
