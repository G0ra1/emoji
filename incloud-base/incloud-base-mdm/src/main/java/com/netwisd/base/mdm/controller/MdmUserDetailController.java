package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.dto.MdmUserDetailDto;
import com.netwisd.base.common.mdm.vo.MdmUserDetailVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.service.MdmUserDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 用户详情 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 09:09:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmUserDetail" )
@Api(value = "mdmUserDetail", tags = "用户详情Controller")
@Slf4j
public class MdmUserDetailController {

    private final  MdmUserDetailService mdmUserDetailService;

    /**
     * 分页查询用户详情
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmUserDetailDto 用户详情
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmUserDetailDto mdmUserDetailDto) {
        Page pageVo = mdmUserDetailService.list(mdmUserDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询用户详情
     * @param mdmUserDetailDto 用户详情
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MdmUserDetailDto mdmUserDetailDto) {
        Page pageVo = mdmUserDetailService.lists(mdmUserDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询用户详情
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmUserDetailVo> get(@PathVariable("id" ) Long id) {
        MdmUserDetailVo mdmUserDetailVo = mdmUserDetailService.get(id);
        log.debug("查询成功");
        return Result.success(mdmUserDetailVo);
    }

    /**
     * 新增用户详情
     * @param mdmUserDetailDto 用户详情
     * @return Result
     */
    @ApiOperation(value = "新增用户详情", notes = "新增用户详情")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmUserDetailDto mdmUserDetailDto) {
        Boolean result = mdmUserDetailService.save(mdmUserDetailDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改用户详情
     * @param mdmUserDetailDto 用户详情
     * @return Result
     */
    @ApiOperation(value = "修改用户详情", notes = "修改用户详情")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmUserDetailDto mdmUserDetailDto) {
        Boolean result = mdmUserDetailService.update(mdmUserDetailDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除用户详情
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除用户详情", notes = "通过id删除用户详情")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmUserDetailService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
