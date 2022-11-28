package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.dto.MdmPostTagDictDto;
import com.netwisd.base.mdm.vo.MdmPostTagDictVo;
import com.netwisd.base.mdm.service.MdmPostTagDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 岗位标识字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-29 21:44:20
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmPostTagDict" )
@Api(value = "mdmPostTagDict", tags = "岗位标识字典Controller")
@Slf4j
public class MdmPostTagDictController {

    private final  MdmPostTagDictService mdmPostTagDictService;

    /**
     * 分页查询岗位标识字典
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmPostTagDictDto 岗位标识字典
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmPostTagDictDto mdmPostTagDictDto) {
        Page pageVo = mdmPostTagDictService.list(mdmPostTagDictDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询岗位标识字典
     * @param
     * @return
     */
    @ApiOperation(value = "不分页集合查询", notes = "不分页集合查询")
    @PostMapping("/lists" )
    public Result<List<MdmPostTagDictVo>> lists() {
        List<MdmPostTagDictVo> mdmPostTagDictVos = mdmPostTagDictService.lists();
        log.debug("查询条数:"+mdmPostTagDictVos.size());
        return Result.success(mdmPostTagDictVos);
    }

    /**
     * 通过id查询岗位标识字典
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmPostTagDictVo> get(@PathVariable("id" ) Long id) {
        MdmPostTagDictVo mdmPostTagDictVo = mdmPostTagDictService.get(id);
        log.debug("查询成功");
        return Result.success(mdmPostTagDictVo);
    }

    /**
     * 新增岗位标识字典
     * @param mdmPostTagDictDto 岗位标识字典
     * @return Result
     */
    @ApiOperation(value = "新增岗位标识字典", notes = "新增岗位标识字典")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmPostTagDictDto mdmPostTagDictDto) {
        Boolean result = mdmPostTagDictService.save(mdmPostTagDictDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改岗位标识字典
     * @param mdmPostTagDictDto 岗位标识字典
     * @return Result
     */
    @ApiOperation(value = "修改岗位标识字典", notes = "修改岗位标识字典")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmPostTagDictDto mdmPostTagDictDto) {
        Boolean result = mdmPostTagDictService.update(mdmPostTagDictDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除岗位标识字典
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除岗位标识字典", notes = "通过id删除岗位标识字典")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmPostTagDictService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
