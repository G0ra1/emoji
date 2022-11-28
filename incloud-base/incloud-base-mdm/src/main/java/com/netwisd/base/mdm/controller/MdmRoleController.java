package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.dto.MdmRoleDto;
import com.netwisd.base.common.mdm.vo.MdmRoleVo;
import com.netwisd.base.mdm.dto.MdmRolePostUserDto;
import com.netwisd.base.mdm.service.MdmResourceService;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.service.MdmRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 角色 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 18:05:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmRole" )
@Api(value = "mdmRole", tags = "角色Controller")
@Slf4j
public class MdmRoleController {

    private final  MdmRoleService mdmRoleService;

    private final MdmResourceService mdmResourceService;

    /**
     * 分页查询角色
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmRoleDto 角色
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmRoleDto mdmRoleDto) {
        Page pageVo = mdmRoleService.list(mdmRoleDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询角色
     * @param mdmRoleDto 角色
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MdmRoleDto mdmRoleDto) {
        Page pageVo = mdmRoleService.lists(mdmRoleDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询角色
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmRoleVo> get(@PathVariable("id" ) Long id) {
        MdmRoleVo mdmRoleVo = mdmRoleService.get(id);
        log.debug("查询成功");
        return Result.success(mdmRoleVo);
    }

    /**
     * 新增角色
     * @param mdmRoleDto 角色
     * @return Result
     */
    @ApiOperation(value = "新增角色", notes = "新增角色")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmRoleDto mdmRoleDto) {
        Boolean result = mdmRoleService.save(mdmRoleDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改角色
     * @param mdmRoleDto 角色
     * @return Result
     */
    @ApiOperation(value = "修改角色", notes = "修改角色")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmRoleDto mdmRoleDto) {
        Boolean result = mdmRoleService.update(mdmRoleDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除角色
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除角色", notes = "通过id删除角色")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable String id) {
        Boolean result = mdmRoleService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }


    /**
     * 设置角色和岗位角色和人员的关系
     * @param mdmRolePostUserDto 色和岗位角色和人员的关系
     * @return Result
     */
    @ApiOperation(value = "设置角色和岗位角色和人员的关系", notes = "设置角色和岗位角色和人员的关系")
    @PostMapping("/setRolePostUserRel")
    public Result<Boolean> setRolePostUserRel(@Validation @RequestBody MdmRolePostUserDto mdmRolePostUserDto) {
        Boolean result = mdmRoleService.setRolePostUserRel(mdmRolePostUserDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 获取角色岗位角色和人员的关系
     * @param roleId 角色id
     * @return Result
     */
    @ApiOperation(value = "获取角色岗位角色和人员的关系", notes = "获取角色岗位角色和人员的关系")
    @GetMapping("/getRolePostUserRel")
    public Result getRolePostUserRel(@RequestParam Long roleId) {
        MdmRoleVo mdmRoleVo = mdmRoleService.getRolePostUserRel(roleId);
        log.debug("保存成功");
        return Result.success(mdmRoleVo);
    }
}
