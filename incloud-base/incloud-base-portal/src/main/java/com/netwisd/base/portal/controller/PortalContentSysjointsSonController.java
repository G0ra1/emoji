package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentSysjointsSonDto;
import com.netwisd.base.portal.vo.PortalContentSysjointsSonVo;
import com.netwisd.base.portal.service.PortalContentSysjointsSonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 系统集成类内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-07 10:19:41
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentSysjointsSon" )
@Api(value = "portalContentSysjointsSon", tags = "系统集成类内容发布子表Controller")
@Slf4j
public class PortalContentSysjointsSonController {

    private final  PortalContentSysjointsSonService portalContentSysjointsSonService;

    /**
     * 分页查询系统集成类内容发布子表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentSysjointsSonDto 系统集成类内容发布子表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentSysjointsSonDto portalContentSysjointsSonDto) {
        Page pageVo = portalContentSysjointsSonService.list(portalContentSysjointsSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询系统集成类内容发布子表
     * @param portalContentSysjointsSonDto 系统集成类内容发布子表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentSysjointsSonDto portalContentSysjointsSonDto) {
        Page pageVo = portalContentSysjointsSonService.lists(portalContentSysjointsSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询系统集成类内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentSysjointsSonVo> get(@PathVariable("id" ) Long id) {
        PortalContentSysjointsSonVo portalContentSysjointsSonVo = portalContentSysjointsSonService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentSysjointsSonVo);
    }

    /**
     * 新增系统集成类内容发布子表
     * @param portalContentSysjointsSonDto 系统集成类内容发布子表
     * @return Result
     */
    @ApiOperation(value = "新增系统集成类内容发布子表", notes = "新增系统集成类内容发布子表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentSysjointsSonDto portalContentSysjointsSonDto) {
        Boolean result = portalContentSysjointsSonService.save(portalContentSysjointsSonDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改系统集成类内容发布子表
     * @param portalContentSysjointsSonDto 系统集成类内容发布子表
     * @return Result
     */
    @ApiOperation(value = "修改系统集成类内容发布子表", notes = "修改系统集成类内容发布子表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentSysjointsSonDto portalContentSysjointsSonDto) {
        Boolean result = portalContentSysjointsSonService.update(portalContentSysjointsSonDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除系统集成类内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除系统集成类内容发布子表", notes = "通过id删除系统集成类内容发布子表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentSysjointsSonService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
