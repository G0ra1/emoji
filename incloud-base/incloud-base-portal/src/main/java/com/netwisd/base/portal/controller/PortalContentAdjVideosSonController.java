package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentAdjVideosSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjVideosSonVo;
import com.netwisd.base.portal.service.PortalContentAdjVideosSonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description  视频类型内容发布-调整表子表   功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-06 16:15:52
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentAdjVideosSon" )
@Api(value = "portalContentAdjVideosSon", tags = " 视频类型内容发布-调整表子表  Controller")
@Slf4j
public class PortalContentAdjVideosSonController {

    private final  PortalContentAdjVideosSonService portalContentAdjVideosSonService;

    /**
     * 分页查询 视频类型内容发布-调整表子表  
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentAdjVideosSonDto  视频类型内容发布-调整表子表  
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentAdjVideosSonDto portalContentAdjVideosSonDto) {
        Page pageVo = portalContentAdjVideosSonService.list(portalContentAdjVideosSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询 视频类型内容发布-调整表子表  
     * @param portalContentAdjVideosSonDto  视频类型内容发布-调整表子表  
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentAdjVideosSonDto portalContentAdjVideosSonDto) {
        Page pageVo = portalContentAdjVideosSonService.lists(portalContentAdjVideosSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询 视频类型内容发布-调整表子表  
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentAdjVideosSonVo> get(@PathVariable("id" ) Long id) {
        PortalContentAdjVideosSonVo portalContentAdjVideosSonVo = portalContentAdjVideosSonService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentAdjVideosSonVo);
    }

    /**
     * 新增 视频类型内容发布-调整表子表  
     * @param portalContentAdjVideosSonDto  视频类型内容发布-调整表子表  
     * @return Result
     */
    @ApiOperation(value = "新增 视频类型内容发布-调整表子表  ", notes = "新增 视频类型内容发布-调整表子表  ")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentAdjVideosSonDto portalContentAdjVideosSonDto) {
        Boolean result = portalContentAdjVideosSonService.save(portalContentAdjVideosSonDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改 视频类型内容发布-调整表子表  
     * @param portalContentAdjVideosSonDto  视频类型内容发布-调整表子表  
     * @return Result
     */
    @ApiOperation(value = "修改 视频类型内容发布-调整表子表  ", notes = "修改 视频类型内容发布-调整表子表  ")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentAdjVideosSonDto portalContentAdjVideosSonDto) {
        Boolean result = portalContentAdjVideosSonService.update(portalContentAdjVideosSonDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除 视频类型内容发布-调整表子表  
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除 视频类型内容发布-调整表子表  ", notes = "通过id删除 视频类型内容发布-调整表子表  ")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentAdjVideosSonService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
