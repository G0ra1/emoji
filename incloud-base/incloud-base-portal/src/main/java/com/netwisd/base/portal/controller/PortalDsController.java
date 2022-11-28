package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalDsDto;
import com.netwisd.base.portal.vo.PortalDsVo;
import com.netwisd.base.portal.service.PortalDsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 数据源管理 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-10 19:25:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalDs" )
@Api(value = "portalds", tags = "数据源管理Controller")
@Slf4j
public class PortalDsController {

    private final  PortalDsService portalDsService;

    /**
     * 分页查询数据源管理
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalDsDto 数据源管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalDsDto portalDsDto) {
        Page pageVo = portalDsService.list(portalDsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询数据源管理
     * @param portalDsDto 数据源管理
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<List> lists(@RequestBody PortalDsDto portalDsDto) {
        List<PortalDsVo> listVo = portalDsService.lists(portalDsDto);
        return Result.success(listVo);
    }

    /**
     * 通过id查询数据源管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalDsVo> get(@PathVariable("id" ) Long id) {
        PortalDsVo portalDsVo = portalDsService.get(id);
        log.debug("查询成功");
        return Result.success(portalDsVo);
    }

    /**
     * 新增数据源管理
     * @param portalDsDto 数据源管理
     * @return Result
     */
    @ApiOperation(value = "新增数据源管理", notes = "新增数据源管理")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalDsDto portalDsDto) {
        Boolean result = portalDsService.save(portalDsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改数据源管理
     * @param portalDsDto 数据源管理
     * @return Result
     */
    @ApiOperation(value = "修改数据源管理", notes = "修改数据源管理")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalDsDto portalDsDto) {
        Boolean result = portalDsService.update(portalDsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除数据源管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除数据源管理", notes = "通过id删除数据源管理")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable String id) {
        Boolean result = portalDsService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
