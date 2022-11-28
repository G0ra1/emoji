package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.dto.MdmDutyTypeDto;
import com.netwisd.base.mdm.vo.MdmDutyTypeVo;
import com.netwisd.base.mdm.service.MdmDutyTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 职务分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:00:30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmDutyType" )
@Api(value = "mdmDutyType", tags = "职务分类Controller")
@Slf4j
public class MdmDutyTypeController {

    private final  MdmDutyTypeService mdmDutyTypeService;

    /**
     * 分页查询职务分类
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmDutyTypeDto 职务分类
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmDutyTypeDto mdmDutyTypeDto) {
        Page pageVo = mdmDutyTypeService.list(mdmDutyTypeDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询职务分类
     * @param mdmDutyTypeDto 职务分类
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MdmDutyTypeDto mdmDutyTypeDto) {
        Page pageVo = mdmDutyTypeService.lists(mdmDutyTypeDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询职务分类
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmDutyTypeVo> get(@PathVariable("id" ) Long id) {
        MdmDutyTypeVo mdmDutyTypeVo = mdmDutyTypeService.get(id);
        log.debug("查询成功");
        return Result.success(mdmDutyTypeVo);
    }

    /**
     * 新增职务分类
     * @param mdmDutyTypeDto 职务分类
     * @return Result
     */
    @ApiOperation(value = "新增职务分类", notes = "新增职务分类")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmDutyTypeDto mdmDutyTypeDto) {
        Boolean result = mdmDutyTypeService.save(mdmDutyTypeDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改职务分类
     * @param mdmDutyTypeDto 职务分类
     * @return Result
     */
    @ApiOperation(value = "修改职务分类", notes = "修改职务分类")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmDutyTypeDto mdmDutyTypeDto) {
        Boolean result = mdmDutyTypeService.update(mdmDutyTypeDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除职务分类
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除职务分类", notes = "通过id删除职务分类")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable String id) {
        Boolean result = mdmDutyTypeService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
