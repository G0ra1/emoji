package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.entity.MdmRoleResource;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.dto.MdmRoleResourceDto;
import com.netwisd.base.mdm.vo.MdmRoleResourceVo;
import com.netwisd.base.mdm.service.MdmRoleResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 角色与资源关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-17 17:27:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmRoleResource" )
@Api(value = "mdmRoleResource", tags = "角色与资源关系Controller")
@Slf4j
public class MdmRoleResourceController {

    private final  MdmRoleResourceService mdmRoleResourceService;

    /**
     * 分页查询角色与资源关系
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmRoleResourceDto 角色与资源关系
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmRoleResourceDto mdmRoleResourceDto) {
        Page pageVo = mdmRoleResourceService.list(mdmRoleResourceDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询角色与资源关系
     * @param mdmRoleResourceDto 角色与资源关系
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MdmRoleResourceDto mdmRoleResourceDto) {
        Page pageVo = mdmRoleResourceService.lists(mdmRoleResourceDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询角色与资源关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmRoleResourceVo> get(@PathVariable("id" ) Long id) {
        MdmRoleResourceVo mdmRoleResourceVo = mdmRoleResourceService.get(id);
        log.debug("查询成功");
        return Result.success(mdmRoleResourceVo);
    }

    /**
     * 新增角色与资源关系
     * @param mdmRoleResourceDto 角色与资源关系
     * @return Result
     */
    @ApiOperation(value = "新增角色与资源关系", notes = "新增角色与资源关系")
    @PostMapping
    public Result<Boolean> save(@Validation(exclude =@ExcludeAnntation
            (vars = {"resourceId"})) @RequestBody MdmRoleResourceDto mdmRoleResourceDto) {
        Boolean result = mdmRoleResourceService.save(mdmRoleResourceDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改角色与资源关系
     * @param mdmRoleResourceDto 角色与资源关系
     * @return Result
     */
    @ApiOperation(value = "修改角色与资源关系", notes = "修改角色与资源关系")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmRoleResourceDto mdmRoleResourceDto) {
        Boolean result = mdmRoleResourceService.update(mdmRoleResourceDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除角色与资源关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除角色与资源关系", notes = "通过id删除角色与资源关系")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmRoleResourceService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 根据角色id 查询关联的资源信息
     * @param roleId 角色与资源关系
     * @return
     */
    @ApiOperation(value = "根据角色id 查询关联的资源信息", notes = "根据角色id 查询关联的资源信息")
    @GetMapping("/getByRoleId" )
    public Result getByRoleId(@RequestParam Long roleId) {
        if(null == roleId || 0L ==roleId) {
            throw new IncloudException("角色id不能为空！");
        }
        List<MdmRoleResourceVo> list = mdmRoleResourceService.getByRoleId(roleId);
        return Result.success(list);
    }

    /**
     * 根据角色id 查询关联的资源信息
     * @param
     * @return
     */
    @ApiOperation(value = "根据资源id 查询关联的资源信息", notes = "根据资源id 查询关联的资源信息")
    @GetMapping("/getByResourceId" )
    public Result getByResourceId(@RequestParam Long resourceId) {
        if(null == resourceId || 0L ==resourceId) {
            throw new IncloudException("资源id不能为空！");
        }
        List<MdmRoleResourceVo> list = mdmRoleResourceService.getByResourceId(resourceId);
        return Result.success(list);
    }

}
