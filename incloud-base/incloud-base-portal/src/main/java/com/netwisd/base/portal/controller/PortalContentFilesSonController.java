package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentFilesSonDto;
import com.netwisd.base.portal.vo.PortalContentFilesSonVo;
import com.netwisd.base.portal.service.PortalContentFilesSonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 10:20:31
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentFilesSon" )
@Api(value = "portalContentFilesSon", tags = "文件下载类型内容发布子表Controller")
@Slf4j
public class PortalContentFilesSonController {

    private final  PortalContentFilesSonService portalContentFilesSonService;

    /**
     * 分页查询文件下载类型内容发布子表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentFilesSonDto 文件下载类型内容发布子表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentFilesSonDto portalContentFilesSonDto) {
        Page pageVo = portalContentFilesSonService.list(portalContentFilesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询文件下载类型内容发布子表
     * @param portalContentFilesSonDto 文件下载类型内容发布子表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentFilesSonDto portalContentFilesSonDto) {
        Page pageVo = portalContentFilesSonService.lists(portalContentFilesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询文件下载类型内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentFilesSonVo> get(@PathVariable("id" ) Long id) {
        PortalContentFilesSonVo portalContentFilesSonVo = portalContentFilesSonService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentFilesSonVo);
    }

    /**
     * 新增文件下载类型内容发布子表
     * @param portalContentFilesSonDto 文件下载类型内容发布子表
     * @return Result
     */
    @ApiOperation(value = "新增文件下载类型内容发布子表", notes = "新增文件下载类型内容发布子表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentFilesSonDto portalContentFilesSonDto) {
        Boolean result = portalContentFilesSonService.save(portalContentFilesSonDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改文件下载类型内容发布子表
     * @param portalContentFilesSonDto 文件下载类型内容发布子表
     * @return Result
     */
    @ApiOperation(value = "修改文件下载类型内容发布子表", notes = "修改文件下载类型内容发布子表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentFilesSonDto portalContentFilesSonDto) {
        Boolean result = portalContentFilesSonService.update(portalContentFilesSonDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除文件下载类型内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除文件下载类型内容发布子表", notes = "通过id删除文件下载类型内容发布子表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentFilesSonService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
