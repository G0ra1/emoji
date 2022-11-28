package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalPartDto;
import com.netwisd.base.portal.vo.PortalPartVo;
import com.netwisd.base.portal.service.PortalPartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 栏目管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-13 19:27:46
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalPart" )
@Api(value = "portalpart", tags = "栏目管理Controller")
@Slf4j
public class PortalPartController {

    private final  PortalPartService portalPartService;


    /**
     * 分页查询栏目管理
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalPartDto 栏目管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalPartDto portalPartDto) {
        Page pageVo = portalPartService.list(portalPartDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 不分页集合查询栏目管理
     * @param portalPartDto 栏目管理
     * @return
     */
    @ApiOperation(value = "不分页集合查询", notes = "不分页集合查询")
    @PostMapping("/lists" )
    public Result<List<PortalPartVo>> lists(@RequestBody PortalPartDto portalPartDto) {
        List<PortalPartVo> portalPartVos = portalPartService.lists(portalPartDto);
        log.debug("查询条数:"+portalPartVos.size());
        return Result.success(portalPartVos);
    }

    /**
     * 通过id查询栏目管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalPartVo> get(@PathVariable("id" ) Long id) {
        PortalPartVo portalPartVo = portalPartService.get(id);
        log.debug("查询成功");
        return Result.success(portalPartVo);
    }

    /**
     * 新增栏目管理
     * @param portalPartDto 栏目管理
     * @return Result
     */
    @ApiOperation(value = "新增栏目管理", notes = "新增栏目管理")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalPartDto portalPartDto) {
        Boolean result = portalPartService.save(portalPartDto);

        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改栏目管理
     * @param portalPartDto 栏目管理
     * @return Result
     */
    @ApiOperation(value = "修改栏目管理", notes = "修改栏目管理")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalPartDto portalPartDto) {
        Boolean result = portalPartService.update(portalPartDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除栏目管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除栏目管理", notes = "通过id删除栏目管理")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalPartService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }
    /**
     * 修改点击量
     *
     * @param id
     * @return Result
     */
    @ApiOperation(value = " 修改点击量", notes = " 修改点击量")
    @GetMapping("/upHits")
    public Result<Boolean> upHits(@RequestParam("id") Long id) {
        Boolean result = portalPartService.upHits(id);
        log.debug("点击量+1");
        return Result.success(result);
    }
}
