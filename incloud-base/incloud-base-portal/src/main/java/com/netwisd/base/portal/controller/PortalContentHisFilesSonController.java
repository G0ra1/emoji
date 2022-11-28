package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentHisFilesSonDto;
import com.netwisd.base.portal.vo.PortalContentHisFilesSonVo;
import com.netwisd.base.portal.service.PortalContentHisFilesSonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 历史 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:26:04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentHisFilesSon" )
@Api(value = "portalContentHisFilesSon", tags = "历史 文件下载类型内容发布子表Controller")
@Slf4j
public class PortalContentHisFilesSonController {

    private final  PortalContentHisFilesSonService portalContentHisFilesSonService;

    /**
     * 分页查询历史 文件下载类型内容发布子表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentHisFilesSonDto 历史 文件下载类型内容发布子表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentHisFilesSonDto portalContentHisFilesSonDto) {
        Page pageVo = portalContentHisFilesSonService.list(portalContentHisFilesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询历史 文件下载类型内容发布子表
     * @param portalContentHisFilesSonDto 历史 文件下载类型内容发布子表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentHisFilesSonDto portalContentHisFilesSonDto) {
        Page pageVo = portalContentHisFilesSonService.lists(portalContentHisFilesSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询历史 文件下载类型内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentHisFilesSonVo> get(@PathVariable("id" ) Long id) {
        PortalContentHisFilesSonVo portalContentHisFilesSonVo = portalContentHisFilesSonService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentHisFilesSonVo);
    }

    /**
     * 新增历史 文件下载类型内容发布子表
     * @param portalContentHisFilesSonDto 历史 文件下载类型内容发布子表
     * @return Result
     */
    @ApiOperation(value = "新增历史 文件下载类型内容发布子表", notes = "新增历史 文件下载类型内容发布子表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentHisFilesSonDto portalContentHisFilesSonDto) {
        Boolean result = portalContentHisFilesSonService.save(portalContentHisFilesSonDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改历史 文件下载类型内容发布子表
     * @param portalContentHisFilesSonDto 历史 文件下载类型内容发布子表
     * @return Result
     */
    @ApiOperation(value = "修改历史 文件下载类型内容发布子表", notes = "修改历史 文件下载类型内容发布子表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentHisFilesSonDto portalContentHisFilesSonDto) {
        Boolean result = portalContentHisFilesSonService.update(portalContentHisFilesSonDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除历史 文件下载类型内容发布子表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除历史 文件下载类型内容发布子表", notes = "通过id删除历史 文件下载类型内容发布子表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentHisFilesSonService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
