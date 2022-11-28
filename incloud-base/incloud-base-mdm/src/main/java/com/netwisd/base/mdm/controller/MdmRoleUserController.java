package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.dto.MdmRoleUserDto;
import com.netwisd.base.common.mdm.vo.MdmRoleUserVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.service.MdmRoleUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 角色与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:44:05
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmRoleUser" )
@Api(value = "mdmRoleUser", tags = "角色与用户关系Controller")
@Slf4j
public class MdmRoleUserController {

    private final  MdmRoleUserService mdmRoleUserService;

    /**
     * 分页查询角色与用户关系
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmRoleUserDto 角色与用户关系
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmRoleUserDto mdmRoleUserDto) {
        Page pageVo = mdmRoleUserService.list(mdmRoleUserDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询角色与用户关系
     * @param mdmRoleUserDto 角色与用户关系
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MdmRoleUserDto mdmRoleUserDto) {
        Page pageVo = mdmRoleUserService.lists(mdmRoleUserDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询角色与用户关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmRoleUserVo> get(@PathVariable("id" ) Long id) {
        MdmRoleUserVo mdmRoleUserVo = mdmRoleUserService.get(id);
        log.debug("查询成功");
        return Result.success(mdmRoleUserVo);
    }

    /**
     * 新增角色与用户关系
     * @param mdmRoleUserDto 角色与用户关系
     * @return Result
     */
    @ApiOperation(value = "新增角色与用户关系", notes = "新增角色与用户关系")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmRoleUserDto mdmRoleUserDto) {
        Boolean result = mdmRoleUserService.save(mdmRoleUserDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改角色与用户关系
     * @param mdmRoleUserDto 角色与用户关系
     * @return Result
     */
    @ApiOperation(value = "修改角色与用户关系", notes = "修改角色与用户关系")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmRoleUserDto mdmRoleUserDto) {
        Boolean result = mdmRoleUserService.update(mdmRoleUserDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除角色与用户关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除角色与用户关系", notes = "通过id删除角色与用户关系")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmRoleUserService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }
    /**
     * 根据用户id获取所有角色信息
     * @param userId 用户ID
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/getRoleUserByUserId/{userId}" )
    public Result<List<MdmRoleUserVo>> getRoleUserByUserId(@PathVariable("userId" ) Long userId) {
        List<MdmRoleUserVo> list = mdmRoleUserService.getRoleUserByUserId(userId);
        log.debug("查询成功");
        return Result.success(list);
    }
}
