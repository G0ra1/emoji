package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.dto.MdmRolePostDto;
import com.netwisd.base.common.mdm.vo.MdmRolePostVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.service.MdmRolePostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 角色与岗位关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:19:56
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmRolePost" )
@Api(value = "mdmRolePost", tags = "角色与岗位关系Controller")
@Slf4j
public class MdmRolePostController {

    private final  MdmRolePostService mdmRolePostService;

    /**
     * 分页查询角色与岗位关系
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmRolePostDto 角色与岗位关系
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmRolePostDto mdmRolePostDto) {
        Page pageVo = mdmRolePostService.list(mdmRolePostDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询角色与岗位关系
     * @param mdmRolePostDto 角色与岗位关系
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MdmRolePostDto mdmRolePostDto) {
        Page pageVo = mdmRolePostService.lists(mdmRolePostDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询角色与岗位关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmRolePostVo> get(@PathVariable("id" ) Long id) {
        MdmRolePostVo mdmRolePostVo = mdmRolePostService.get(id);
        log.debug("查询成功");
        return Result.success(mdmRolePostVo);
    }

    /**
     * 新增角色与岗位关系
     * @param mdmRolePostDto 角色与岗位关系
     * @return Result
     */
    @ApiOperation(value = "新增角色与岗位关系", notes = "新增角色与岗位关系")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmRolePostDto mdmRolePostDto) {
        Boolean result = mdmRolePostService.save(mdmRolePostDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改角色与岗位关系
     * @param mdmRolePostDto 角色与岗位关系
     * @return Result
     */
    @ApiOperation(value = "修改角色与岗位关系", notes = "修改角色与岗位关系")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmRolePostDto mdmRolePostDto) {
        Boolean result = mdmRolePostService.update(mdmRolePostDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除角色与岗位关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除角色与岗位关系", notes = "通过id删除角色与岗位关系")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmRolePostService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通过id查询岗位与角色关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "根据岗位id查询岗位角色信息", notes = "根据岗位id查询岗位角色信息")
    @GetMapping("/user/getRolePostByPostId/{id}")
    public Result<List<MdmRolePostVo>> getRolePost(@PathVariable("id" ) Long id) {
        List<MdmRolePostVo> mdmRolePostVo = mdmRolePostService.getRolePostByPostId(id);
        log.debug("查询成功");
        return Result.success(mdmRolePostVo);
    }

}
