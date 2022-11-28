package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalPartDsDto;
import com.netwisd.base.portal.vo.PortalPartDsVo;
import com.netwisd.base.portal.service.PortalPartDsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 栏目管理数据源 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-13 20:07:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalPartDs" )
@Api(value = "portalpartds", tags = "栏目管理数据源Controller")
@Slf4j
public class PortalPartDsController {

    private final  PortalPartDsService portalPartDsService;

    /**
     * 分页查询栏目管理数据源
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalPartDsDto 栏目管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalPartDsDto portalPartDsDto) {
        Page pageVo = portalPartDsService.list(portalPartDsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询栏目管理数据源
     * @param portalPartDsDto 栏目管理
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalPartDsDto portalPartDsDto) {
        Page pageVo = portalPartDsService.lists(portalPartDsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询栏目管理数据源
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalPartDsVo> get(@PathVariable("id" ) Long id) {
        PortalPartDsVo portalPartDsVo = portalPartDsService.get(id);
        log.debug("查询成功");
        return Result.success(portalPartDsVo);
    }

    /**
     * 新增栏目管理数据源
     * @param portalPartDsDto 栏目管理数据源
     * @return Result
     */
    @ApiOperation(value = "新增栏目管理数据源", notes = "新增栏目管理数据源")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalPartDsDto portalPartDsDto) {
        Boolean result = portalPartDsService.save(portalPartDsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改栏目管理数据源
     * @param portalPartDsDto 栏目管理数据源
     * @return Result
     */
    @ApiOperation(value = "修改栏目管理数据源", notes = "修改栏目管理数据源")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalPartDsDto portalPartDsDto) {
        Boolean result = portalPartDsService.update(portalPartDsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除栏目管理数据源
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除栏目管理数据源", notes = "通过id删除栏目管理数据源")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalPartDsService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
