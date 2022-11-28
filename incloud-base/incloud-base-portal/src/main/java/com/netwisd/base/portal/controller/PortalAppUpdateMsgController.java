package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalAppUpdateMsgDto;
import com.netwisd.base.portal.vo.PortalAppUpdateMsgVo;
import com.netwisd.base.portal.service.PortalAppUpdateMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description app安装包更新记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-01-06 10:16:32
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalAppUpdateMsg" )
@Api(value = "portalAppUpdateMsg", tags = "app安装包更新记录表Controller")
@Slf4j
public class PortalAppUpdateMsgController {

    private final  PortalAppUpdateMsgService portalAppUpdateMsgService;

    /**
     * 分页查询app安装包更新记录表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalAppUpdateMsgDto app安装包更新记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalAppUpdateMsgDto portalAppUpdateMsgDto) {
        Page pageVo = portalAppUpdateMsgService.list(portalAppUpdateMsgDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询app安装包更新记录表
     * @param portalAppUpdateMsgDto app安装包更新记录表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalAppUpdateMsgDto portalAppUpdateMsgDto) {
        Page pageVo = portalAppUpdateMsgService.lists(portalAppUpdateMsgDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询app安装包更新记录表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalAppUpdateMsgVo> get(@PathVariable("id" ) Long id) {
        PortalAppUpdateMsgVo portalAppUpdateMsgVo = portalAppUpdateMsgService.get(id);
        log.debug("查询成功");
        return Result.success(portalAppUpdateMsgVo);
    }

    /**
     * 新增app安装包更新记录表
     * @param portalAppUpdateMsgDto app安装包更新记录表
     * @return Result
     */
    @ApiOperation(value = "新增app安装包更新记录表", notes = "新增app安装包更新记录表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalAppUpdateMsgDto portalAppUpdateMsgDto) {
        Boolean result = portalAppUpdateMsgService.save(portalAppUpdateMsgDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改app安装包更新记录表
     * @param portalAppUpdateMsgDto app安装包更新记录表
     * @return Result
     */
    @ApiOperation(value = "修改app安装包更新记录表", notes = "修改app安装包更新记录表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalAppUpdateMsgDto portalAppUpdateMsgDto) {
        Boolean result = portalAppUpdateMsgService.update(portalAppUpdateMsgDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除app安装包更新记录表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除app安装包更新记录表", notes = "通过id删除app安装包更新记录表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalAppUpdateMsgService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 获取最新app安装包信息
     * @return
     */
    @ApiOperation(value = "获取最新app安装包信息", notes = "获取最新app安装包信息")
    @GetMapping("/getNewVersion" )
    public Result<PortalAppUpdateMsgVo> getNewVersion() {
        PortalAppUpdateMsgVo portalAppUpdateMsgVo = portalAppUpdateMsgService.getNewVersion();
        log.debug("最新app安装包信息查询成功");
        return Result.success(portalAppUpdateMsgVo);
    }

}
