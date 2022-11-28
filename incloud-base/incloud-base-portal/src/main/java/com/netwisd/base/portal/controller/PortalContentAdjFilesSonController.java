package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentAdjFilesSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjFilesSonVo;
import com.netwisd.base.portal.service.PortalContentAdjFilesSonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 调整 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:23:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentAdjFilesSon" )
@Api(value = "portalContentAdjFilesSon", tags = "调整 文件下载类型内容发布子表Controller")
@Slf4j
public class PortalContentAdjFilesSonController {

    private final  PortalContentAdjFilesSonService portalContentAdjFilesSonService;

    /**
     * 分页查询调整 文件下载类型内容发布子表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentAdjFilesSonDto 调整 文件下载类型内容发布子表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentAdjFilesSonDto portalContentAdjFilesSonDto) {
        Page pageVo = portalContentAdjFilesSonService.list(portalContentAdjFilesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询调整 文件下载类型内容发布子表
     * @param portalContentAdjFilesSonDto 调整 文件下载类型内容发布子表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentAdjFilesSonDto portalContentAdjFilesSonDto) {
        Page pageVo = portalContentAdjFilesSonService.lists(portalContentAdjFilesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询调整 文件下载类型内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentAdjFilesSonVo> get(@PathVariable("id" ) Long id) {
        PortalContentAdjFilesSonVo portalContentAdjFilesSonVo = portalContentAdjFilesSonService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentAdjFilesSonVo);
    }

    /**
     * 新增调整 文件下载类型内容发布子表
     * @param portalContentAdjFilesSonDto 调整 文件下载类型内容发布子表
     * @return Result
     */
    @ApiOperation(value = "新增调整 文件下载类型内容发布子表", notes = "新增调整 文件下载类型内容发布子表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentAdjFilesSonDto portalContentAdjFilesSonDto) {
        Boolean result = portalContentAdjFilesSonService.save(portalContentAdjFilesSonDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改调整 文件下载类型内容发布子表
     * @param portalContentAdjFilesSonDto 调整 文件下载类型内容发布子表
     * @return Result
     */
    @ApiOperation(value = "修改调整 文件下载类型内容发布子表", notes = "修改调整 文件下载类型内容发布子表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentAdjFilesSonDto portalContentAdjFilesSonDto) {
        Boolean result = portalContentAdjFilesSonService.update(portalContentAdjFilesSonDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除调整 文件下载类型内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除调整 文件下载类型内容发布子表", notes = "通过id删除调整 文件下载类型内容发布子表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentAdjFilesSonService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
