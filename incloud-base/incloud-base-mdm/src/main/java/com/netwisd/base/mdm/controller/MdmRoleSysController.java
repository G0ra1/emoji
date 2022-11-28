package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.dto.MdmRoleSysDto;
import com.netwisd.base.mdm.vo.MdmRoleSysVo;
import com.netwisd.base.mdm.service.MdmRoleSysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 角色对应的功能权限子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-23 19:12:14
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmRoleSys" )
@Api(value = "mdmRoleSys", tags = "角色对应的功能权限子系统Controller")
@Slf4j
public class MdmRoleSysController {

    private final  MdmRoleSysService mdmRoleSysService;

    /**
     * 分页查询角色对应的功能权限子系统
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmRoleSysDto 角色对应的功能权限子系统
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmRoleSysDto mdmRoleSysDto) {
        Page pageVo = mdmRoleSysService.list(mdmRoleSysDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询角色对应的功能权限子系统
     * @param mdmRoleSysDto 角色对应的功能权限子系统
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MdmRoleSysDto mdmRoleSysDto) {
        Page pageVo = mdmRoleSysService.lists(mdmRoleSysDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询角色对应的功能权限子系统
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmRoleSysVo> get(@PathVariable("id" ) Long id) {
        MdmRoleSysVo mdmRoleSysVo = mdmRoleSysService.get(id);
        log.debug("查询成功");
        return Result.success(mdmRoleSysVo);
    }

    /**
     * 新增角色对应的功能权限子系统
     * @param mdmRoleSysDto 角色对应的功能权限子系统
     * @return Result
     */
    @ApiOperation(value = "新增角色对应的功能权限子系统", notes = "新增角色对应的功能权限子系统")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmRoleSysDto mdmRoleSysDto) {
        Boolean result = mdmRoleSysService.save(mdmRoleSysDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改角色对应的功能权限子系统
     * @param mdmRoleSysDto 角色对应的功能权限子系统
     * @return Result
     */
    @ApiOperation(value = "修改角色对应的功能权限子系统", notes = "修改角色对应的功能权限子系统")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmRoleSysDto mdmRoleSysDto) {
        Boolean result = mdmRoleSysService.update(mdmRoleSysDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除角色对应的功能权限子系统
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除角色对应的功能权限子系统", notes = "通过id删除角色对应的功能权限子系统")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmRoleSysService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
