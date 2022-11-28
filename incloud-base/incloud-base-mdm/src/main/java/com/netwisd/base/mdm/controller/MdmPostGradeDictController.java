package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.dto.MdmPostGradeDictDto;
import com.netwisd.base.mdm.vo.MdmPostGradeDictVo;
import com.netwisd.base.mdm.service.MdmPostGradeDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 岗位职等 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 10:54:55
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmPostGradeDict" )
@Api(value = "mdmPostGradeDict", tags = "岗位职等字典Controller")
@Slf4j
public class MdmPostGradeDictController {

    private final  MdmPostGradeDictService mdmPostGradeDictService;

    /**
     * 分页查询岗位职等
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmPostGradeDictDto 岗位职等
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmPostGradeDictDto mdmPostGradeDictDto) {
        Page pageVo = mdmPostGradeDictService.list(mdmPostGradeDictDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 不分页自定义查询岗位职等
     * @param mdmPostGradeDictDto 岗位职等
     * @return
     */
    @ApiOperation(value = "根据岗位序列id查询职等集合", notes = "根据岗位序列id查询职等集合")
    @PostMapping("/lists" )
    public Result<List<MdmPostGradeDictVo>> lists(@RequestBody MdmPostGradeDictDto mdmPostGradeDictDto) {
        List<MdmPostGradeDictVo> mdmPostGradeDictVos = mdmPostGradeDictService.lists(mdmPostGradeDictDto);
        log.debug("查询条数:"+mdmPostGradeDictVos.size());
        return Result.success(mdmPostGradeDictVos);
    }

    /**
     * 通过id查询岗位职等
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmPostGradeDictVo> get(@PathVariable("id" ) Long id) {
        MdmPostGradeDictVo mdmPostGradeDictVo = mdmPostGradeDictService.get(id);
        log.debug("查询成功");
        return Result.success(mdmPostGradeDictVo);
    }

    /**
     * 新增岗位职等
     * @param mdmPostGradeDictDto 岗位职等
     * @return Result
     */
    @ApiOperation(value = "新增岗位职等", notes = "新增岗位职等")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmPostGradeDictDto mdmPostGradeDictDto) {
        Boolean result = mdmPostGradeDictService.save(mdmPostGradeDictDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改岗位职等
     * @param mdmPostGradeDictDto 岗位职等
     * @return Result
     */
    @ApiOperation(value = "修改岗位职等", notes = "修改岗位职等")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmPostGradeDictDto mdmPostGradeDictDto) {
        Boolean result = mdmPostGradeDictService.update(mdmPostGradeDictDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除岗位职等
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除岗位职等", notes = "通过id删除岗位职等")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmPostGradeDictService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
