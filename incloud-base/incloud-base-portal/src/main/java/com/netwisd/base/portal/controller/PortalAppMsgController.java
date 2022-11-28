package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalAppMsgDto;
import com.netwisd.base.portal.vo.PortalAppMsgVo;
import com.netwisd.base.portal.service.PortalAppMsgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 应用市场app信息表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-12-23 15:07:50
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalAppMsg" )
@Api(value = "portalAppMsg", tags = "应用市场app信息表Controller")
@Slf4j
public class PortalAppMsgController {

    private final  PortalAppMsgService portalAppMsgService;

    /**
     * 分页查询应用市场app信息表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalAppMsgDto 应用市场app信息表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalAppMsgDto portalAppMsgDto) {
        Page pageVo = portalAppMsgService.list(portalAppMsgDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询应用市场app信息表
     * @param portalAppMsgDto 应用市场app信息表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalAppMsgDto portalAppMsgDto) {
        Page pageVo = portalAppMsgService.lists(portalAppMsgDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询应用市场app信息表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalAppMsgVo> get(@PathVariable("id" ) Long id) {
        PortalAppMsgVo portalAppMsgVo = portalAppMsgService.get(id);
        log.debug("查询成功");
        return Result.success(portalAppMsgVo);
    }

    /**
     * 新增应用市场app信息表
     * @param portalAppMsgDto 应用市场app信息表
     * @return Result
     */
    @ApiOperation(value = "新增应用市场app信息表", notes = "新增应用市场app信息表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalAppMsgDto portalAppMsgDto) {
        Boolean result = portalAppMsgService.save(portalAppMsgDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改应用市场app信息表
     * @param portalAppMsgDto 应用市场app信息表
     * @return Result
     */
    @ApiOperation(value = "修改应用市场app信息表", notes = "修改应用市场app信息表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalAppMsgDto portalAppMsgDto) {
        Boolean result = portalAppMsgService.update(portalAppMsgDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除应用市场app信息表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除应用市场app信息表", notes = "通过id删除应用市场app信息表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalAppMsgService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 查询状态
     * @param portalAppMsgDto
     * @return Result
     */
    @ApiOperation(value = "查询状态", notes = "查询状态")
    @PostMapping("/getState" )
    public Result<String> getState(@RequestBody PortalAppMsgDto portalAppMsgDto) {
        String state = portalAppMsgService.getState(portalAppMsgDto);
        log.debug("查询成功");
        return Result.success(state);
    }
}
