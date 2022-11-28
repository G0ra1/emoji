package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.dto.MdmCommDictDto;
import com.netwisd.base.mdm.vo.MdmCommDictVo;
import com.netwisd.base.mdm.service.MdmCommDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description mdm通用字典  功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-16 15:58:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmCommDict" )
@Api(value = "mdmCommDict", tags = "mdm通用字典 Controller")
@Slf4j
public class MdmCommDictController {

    private final  MdmCommDictService mdmCommDictService;

    /**
     * 分页查询mdm通用字典 
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmCommDictDto mdm通用字典 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmCommDictDto mdmCommDictDto) {
        Page pageVo = mdmCommDictService.list(mdmCommDictDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询mdm通用字典 
     * @param mdmCommDictDto mdm通用字典 
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result lists(@RequestBody MdmCommDictDto mdmCommDictDto) {
        List<MdmCommDictVo> list = mdmCommDictService.lists(mdmCommDictDto);
        log.debug("查询条数:"+list.size());
        return Result.success(list);
    }

    /**
     * 通过id查询mdm通用字典 
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmCommDictVo> get(@PathVariable("id" ) Long id) {
        MdmCommDictVo mdmCommDictVo = mdmCommDictService.get(id);
        log.debug("查询成功");
        return Result.success(mdmCommDictVo);
    }

    /**
     * 新增mdm通用字典 
     * @param mdmCommDictDto mdm通用字典 
     * @return Result
     */
    @ApiOperation(value = "新增mdm通用字典 ", notes = "新增mdm通用字典 ")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmCommDictDto mdmCommDictDto) {
        Boolean result = mdmCommDictService.save(mdmCommDictDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改mdm通用字典 
     * @param mdmCommDictDto mdm通用字典 
     * @return Result
     */
    @ApiOperation(value = "修改mdm通用字典 ", notes = "修改mdm通用字典 ")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmCommDictDto mdmCommDictDto) {
        Boolean result = mdmCommDictService.update(mdmCommDictDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除mdm通用字典 
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除mdm通用字典 ", notes = "通过id删除mdm通用字典 ")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmCommDictService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
