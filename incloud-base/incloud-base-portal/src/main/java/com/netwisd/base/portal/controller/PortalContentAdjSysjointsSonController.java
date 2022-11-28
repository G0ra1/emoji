package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentAdjSysjointsSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjSysjointsSonVo;
import com.netwisd.base.portal.service.PortalContentAdjSysjointsSonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 调整 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 14:39:52
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentAdjSysjointsSon" )
@Api(value = "portalContentAdjSysjointsSon", tags = "调整 系统集成类内容发布子表Controller")
@Slf4j
public class PortalContentAdjSysjointsSonController {

    private final  PortalContentAdjSysjointsSonService portalContentAdjSysjointsSonService;

    /**
     * 分页查询调整 系统集成类内容发布子表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentAdjSysjointsSonDto 调整 系统集成类内容发布子表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto) {
        Page pageVo = portalContentAdjSysjointsSonService.list(portalContentAdjSysjointsSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询调整 系统集成类内容发布子表
     * @param portalContentAdjSysjointsSonDto 调整 系统集成类内容发布子表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto) {
        Page pageVo = portalContentAdjSysjointsSonService.lists(portalContentAdjSysjointsSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询调整 系统集成类内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentAdjSysjointsSonVo> get(@PathVariable("id" ) Long id) {
        PortalContentAdjSysjointsSonVo portalContentAdjSysjointsSonVo = portalContentAdjSysjointsSonService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentAdjSysjointsSonVo);
    }

    /**
     * 新增调整 系统集成类内容发布子表
     * @param portalContentAdjSysjointsSonDto 调整 系统集成类内容发布子表
     * @return Result
     */
    @ApiOperation(value = "新增调整 系统集成类内容发布子表", notes = "新增调整 系统集成类内容发布子表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto) {
        Boolean result = portalContentAdjSysjointsSonService.save(portalContentAdjSysjointsSonDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改调整 系统集成类内容发布子表
     * @param portalContentAdjSysjointsSonDto 调整 系统集成类内容发布子表
     * @return Result
     */
    @ApiOperation(value = "修改调整 系统集成类内容发布子表", notes = "修改调整 系统集成类内容发布子表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentAdjSysjointsSonDto portalContentAdjSysjointsSonDto) {
        Boolean result = portalContentAdjSysjointsSonService.update(portalContentAdjSysjointsSonDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除调整 系统集成类内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除调整 系统集成类内容发布子表", notes = "通过id删除调整 系统集成类内容发布子表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentAdjSysjointsSonService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
