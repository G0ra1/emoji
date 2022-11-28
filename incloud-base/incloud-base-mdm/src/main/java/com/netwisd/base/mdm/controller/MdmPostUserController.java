package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.dto.MdmPostUserDto;
import com.netwisd.base.common.mdm.vo.MdmPostUserVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.service.MdmPostUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 岗位与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 11:47:13
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmPostUser" )
@Api(value = "mdmPostUser", tags = "岗位与用户关系Controller")
@Slf4j
public class MdmPostUserController {

    private final  MdmPostUserService mdmPostUserService;

    /**
     * 分页查询岗位与用户关系
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmPostUserDto 岗位与用户关系
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmPostUserDto mdmPostUserDto) {
        Page pageVo = mdmPostUserService.list(mdmPostUserDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询岗位与用户关系
     * @param mdmPostUserDto 岗位与用户关系
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result lists(@RequestBody MdmPostUserDto mdmPostUserDto) {
        List<MdmPostUserVo> list = mdmPostUserService.lists(mdmPostUserDto);
        log.debug("查询条数:"+list.size());
        return Result.success(list);
    }

    /**
     * 通过id查询岗位与用户关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmPostUserVo> get(@PathVariable("id" ) Long id) {
        MdmPostUserVo mdmPostUserVo = mdmPostUserService.get(id);
        log.debug("查询成功");
        return Result.success(mdmPostUserVo);
    }

    /**
     * 新增岗位与用户关系
     * @param mdmPostUserDto 岗位与用户关系
     * @return Result
     */
    @ApiOperation(value = "新增岗位与用户关系", notes = "新增岗位与用户关系")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmPostUserDto mdmPostUserDto) {
        Boolean result = mdmPostUserService.save(mdmPostUserDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改岗位与用户关系
     * @param mdmPostUserDto 岗位与用户关系
     * @return Result
     */
    @ApiOperation(value = "修改岗位与用户关系", notes = "修改岗位与用户关系")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmPostUserDto mdmPostUserDto) {
        Boolean result = mdmPostUserService.update(mdmPostUserDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除岗位与用户关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除岗位与用户关系", notes = "通过id删除岗位与用户关系")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmPostUserService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通过id查询岗位与用户关系
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "根据岗位id查询岗位人员信息", notes = "根据岗位id查询岗位人员信息")
    @GetMapping("/user/getUserByPostId/{id}")
    public Result<List<MdmPostUserVo>> getUser(@PathVariable("id" ) Long id) {
        List<MdmPostUserVo> mdmPostUserVo = mdmPostUserService.getUserByPostId(id);
        log.debug("查询成功");
        return Result.success(mdmPostUserVo);
    }

    /**
     * 根据用户id 查询用户岗位信息
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "根据用户id 查询用户岗位信息", notes = "根据用户id 查询用户岗位信息")
    @GetMapping("/post/getPostByUserId/{id}")
    public Result getPostByUserId(@PathVariable("id" ) String id) {
        List<MdmPostUserVo> list = mdmPostUserService.getPostByUserId(id);
        log.debug("查询成功");
        return Result.success(list);
    }
}
