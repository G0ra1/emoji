package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.dto.MdmOrgLevelDictDto;
import com.netwisd.base.common.mdm.vo.MdmOrgLevelDictVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.service.MdmOrgLevelDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 组织级别类型字典 功能描述...
 * @author 云数网讯 zouliming@netwisd.com@netwisd.com
 * @date 2021-08-26 09:56:26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmOrgLevelDict" )
@Api(value = "mdmOrgLevelDict", tags = "组织级别类型字典Controller")
@Slf4j
public class MdmOrgLevelDictController {

    private final  MdmOrgLevelDictService mdmOrgLevelDictService;

    /**
     * 分页查询组织级别类型字典
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmOrgLevelDictDto 组织级别类型字典
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmOrgLevelDictDto mdmOrgLevelDictDto) {
        Page pageVo = mdmOrgLevelDictService.list(mdmOrgLevelDictDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询组织级别类型字典
     * @param mdmOrgLevelDictDto 组织级别类型字典
     * @return
     */
    /*@ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MdmOrgLevelDictDto mdmOrgLevelDictDto) {
        Page pageVo = mdmOrgLevelDictService.lists(mdmOrgLevelDictDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }*/

    /**
     * 通过id查询组织级别类型字典
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmOrgLevelDictVo> get(@PathVariable("id" ) Long id) {
        MdmOrgLevelDictVo mdmOrgLevelDictVo = mdmOrgLevelDictService.get(id);
        log.debug("查询成功");
        return Result.success(mdmOrgLevelDictVo);
    }

    /**
     * 新增组织级别类型字典
     * @param mdmOrgLevelDictDto 组织级别类型字典
     * @return Result
     */
    @ApiOperation(value = "新增组织级别类型字典", notes = "新增组织级别类型字典")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmOrgLevelDictDto mdmOrgLevelDictDto) {
        Boolean result = mdmOrgLevelDictService.save(mdmOrgLevelDictDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改组织级别类型字典
     * @param mdmOrgLevelDictDto 组织级别类型字典
     * @return Result
     */
    @ApiOperation(value = "修改组织级别类型字典", notes = "修改组织级别类型字典")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmOrgLevelDictDto mdmOrgLevelDictDto) {
        Boolean result = mdmOrgLevelDictService.update(mdmOrgLevelDictDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除组织级别类型字典
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除组织级别类型字典", notes = "通过id删除组织级别类型字典")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmOrgLevelDictService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
