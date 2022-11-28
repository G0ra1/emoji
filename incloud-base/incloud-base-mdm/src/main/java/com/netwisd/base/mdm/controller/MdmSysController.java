package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.vo.MdmSysVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.dto.MdmSysDto;
import com.netwisd.base.mdm.service.MdmSysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmSys" )
@Api(value = "mdmSys", tags = "子系统Controller")
@Slf4j
public class MdmSysController {

    private final  MdmSysService mdmSysService;

    /**
     * 分页查询子系统
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmSysDto 子系统
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmSysDto mdmSysDto) {
        Page pageVo = mdmSysService.list(mdmSysDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询子系统
     * @param mdmSysDto 子系统
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result lists(@RequestBody MdmSysDto mdmSysDto) {
        List<MdmSysVo> list = mdmSysService.lists(mdmSysDto);
        log.debug("查询条数:"+list.size());
        return Result.success(list);
    }

    /**
     * 通过id查询子系统
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmSysVo> get(@PathVariable("id" ) Long id) {
        MdmSysVo mdmSysVo = mdmSysService.get(id);
        log.debug("查询成功");
        return Result.success(mdmSysVo);
    }

    /**
     * 新增子系统
     * @param mdmSysDto 子系统
     * @return Result
     */
    @ApiOperation(value = "新增子系统", notes = "新增子系统")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmSysDto mdmSysDto) {
        Boolean result = mdmSysService.save(mdmSysDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改子系统
     * @param mdmSysDto 子系统
     * @return Result
     */
    @ApiOperation(value = "修改子系统", notes = "修改子系统")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmSysDto mdmSysDto) {
        Boolean result = mdmSysService.update(mdmSysDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除子系统
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除子系统", notes = "通过id删除子系统")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable String id) {
        Boolean result = mdmSysService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
