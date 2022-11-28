package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentLoginCorporateSonDto;
import com.netwisd.base.portal.vo.PortalContentLoginCorporateSonVo;
import com.netwisd.base.portal.service.PortalContentLoginCorporateSonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 登录页-子表（企业文化轮播图） 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2021-12-27 17:22:50
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentLoginCorporateSon" )
@Api(value = "portalContentLoginCorporateSon", tags = "登录页-子表（企业文化轮播图）Controller")
@Slf4j
public class PortalContentLoginCorporateSonController {

    private final  PortalContentLoginCorporateSonService portalContentLoginCorporateSonService;

    /**
     * 分页查询登录页-子表（企业文化轮播图）
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentLoginCorporateSonDto 登录页-子表（企业文化轮播图）
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto) {
        Page pageVo = portalContentLoginCorporateSonService.list(portalContentLoginCorporateSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询登录页-子表（企业文化轮播图）
     * @param portalContentLoginCorporateSonDto 登录页-子表（企业文化轮播图）
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto) {
        Page pageVo = portalContentLoginCorporateSonService.lists(portalContentLoginCorporateSonDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询登录页-子表（企业文化轮播图）
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentLoginCorporateSonVo> get(@PathVariable("id" ) Long id) {
        PortalContentLoginCorporateSonVo portalContentLoginCorporateSonVo = portalContentLoginCorporateSonService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentLoginCorporateSonVo);
    }

    /**
     * 新增登录页-子表（企业文化轮播图）
     * @param portalContentLoginCorporateSonDto 登录页-子表（企业文化轮播图）
     * @return Result
     */
    @ApiOperation(value = "新增登录页-子表（企业文化轮播图）", notes = "新增登录页-子表（企业文化轮播图）")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto) {
        Boolean result = portalContentLoginCorporateSonService.save(portalContentLoginCorporateSonDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改登录页-子表（企业文化轮播图）
     * @param portalContentLoginCorporateSonDto 登录页-子表（企业文化轮播图）
     * @return Result
     */
    @ApiOperation(value = "修改登录页-子表（企业文化轮播图）", notes = "修改登录页-子表（企业文化轮播图）")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto) {
        Boolean result = portalContentLoginCorporateSonService.update(portalContentLoginCorporateSonDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除登录页-子表（企业文化轮播图）
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除登录页-子表（企业文化轮播图）", notes = "通过id删除登录页-子表（企业文化轮播图）")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentLoginCorporateSonService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
