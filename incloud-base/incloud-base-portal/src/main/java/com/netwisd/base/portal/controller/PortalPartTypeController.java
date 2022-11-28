package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.entity.PortalPartType;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalPartTypeDto;
import com.netwisd.base.portal.vo.PortalPartTypeVo;
import com.netwisd.base.portal.service.PortalPartTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 栏目类型字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-12 17:20:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalPartType" )
@Api(value = "portalparttype", tags = "栏目类型字典Controller")
@Slf4j
public class PortalPartTypeController {

    private final  PortalPartTypeService portalPartTypeService;

    /**
     * 分页查询栏目类型字典
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalPartTypeDto 栏目类型字典
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalPartTypeDto portalPartTypeDto) {
        Page pageVo = portalPartTypeService.list(portalPartTypeDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 不分页集合查询
     * @param
     * @return
     */
    @ApiOperation(value = "不分页集合查询", notes = "不分页集合查询")
    @PostMapping("/lists" )
    public Result<List<PortalPartTypeVo>> lists() {
        List<PortalPartTypeVo> portalPartTypesVo= portalPartTypeService.lists();
        log.debug("查询条数:"+portalPartTypesVo.size());
        return Result.success(portalPartTypesVo);
    }

    /**
     * 通过id查询栏目类型字典
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalPartTypeVo> get(@PathVariable("id" ) Long id) {
        PortalPartTypeVo portalPartTypeVo = portalPartTypeService.get(id);
        log.debug("查询成功");
        return Result.success(portalPartTypeVo);
    }

    /**
     * 新增栏目类型字典
     * @param portalPartTypeDto 栏目类型字典
     * @return Result
     */
    @ApiOperation(value = "新增栏目类型字典", notes = "新增栏目类型字典")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalPartTypeDto portalPartTypeDto) {
        Boolean result = portalPartTypeService.save(portalPartTypeDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改栏目类型字典
     * @param portalPartTypeDto 栏目类型字典
     * @return Result
     */
    @ApiOperation(value = "修改栏目类型字典", notes = "修改栏目类型字典")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalPartTypeDto portalPartTypeDto) {
        Boolean result = portalPartTypeService.update(portalPartTypeDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除栏目类型字典
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除栏目类型字典", notes = "通过id删除栏目类型字典")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalPartTypeService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
