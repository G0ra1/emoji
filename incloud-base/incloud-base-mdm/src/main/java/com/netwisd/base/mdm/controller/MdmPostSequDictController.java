package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.dto.MdmPostSequDictDto;
import com.netwisd.base.mdm.vo.MdmPostSequDictVo;
import com.netwisd.base.mdm.service.MdmPostSequDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 岗位序列 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-27 11:01:12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmPostSequDict" )
@Api(value = "mdmPostSequDict", tags = "岗位序列字典Controller")
@Slf4j
public class MdmPostSequDictController {

    private final  MdmPostSequDictService mdmPostSequDictService;

    /**
     * 分页查询岗位序列
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmPostSequDictDto 岗位序列
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmPostSequDictDto mdmPostSequDictDto) {
        Page pageVo = mdmPostSequDictService.list(mdmPostSequDictDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询岗位序列
     * @param
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<List<MdmPostSequDictVo>> lists() {
        List<MdmPostSequDictVo>  mdmPostSequDictVos= mdmPostSequDictService.lists();
        log.debug("查询条数:"+mdmPostSequDictVos.size());
        return Result.success(mdmPostSequDictVos);
    }

    /**
     * 通过id查询岗位序列
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmPostSequDictVo> get(@PathVariable("id" ) Long id) {
        MdmPostSequDictVo mdmPostSequDictVo = mdmPostSequDictService.get(id);
        log.debug("查询成功");
        return Result.success(mdmPostSequDictVo);
    }

    /**
     * 新增岗位序列
     * @param mdmPostSequDictDto 岗位序列
     * @return Result
     */
    @ApiOperation(value = "新增岗位序列", notes = "新增岗位序列")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmPostSequDictDto mdmPostSequDictDto) {
        Boolean result = mdmPostSequDictService.save(mdmPostSequDictDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改岗位序列
     * @param mdmPostSequDictDto 岗位序列
     * @return Result
     */
    @ApiOperation(value = "修改岗位序列", notes = "修改岗位序列")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmPostSequDictDto mdmPostSequDictDto) {
        Boolean result = mdmPostSequDictService.update(mdmPostSequDictDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除岗位序列
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除岗位序列", notes = "通过id删除岗位序列")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmPostSequDictService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
