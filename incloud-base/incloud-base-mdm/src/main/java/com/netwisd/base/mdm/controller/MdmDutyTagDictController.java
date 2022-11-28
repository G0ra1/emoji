package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmDutyTagDictDto;
import com.netwisd.base.mdm.vo.MdmDutyTagDictVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.dto.MdmDutyTagDictDto;
import com.netwisd.base.mdm.vo.MdmDutyTagDictVo;
import com.netwisd.base.mdm.service.MdmDutyTagDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmDutyTagDict" )
@Api(value = "mdmDutyTagDict", tags = "职务标识字典Controller")
@Slf4j
public class MdmDutyTagDictController {

    private final  MdmDutyTagDictService mdmDutyTagDictService;

    /**
     * 分页查询职务标识字典
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmDutyTagDictDto 职务标识字典
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmDutyTagDictDto mdmDutyTagDictDto) {
        Page pageVo = mdmDutyTagDictService.list(mdmDutyTagDictDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询职务标识字典
     * @param
     * @return
     */
    @ApiOperation(value = "不分页集合查询", notes = "不分页集合查询")
    @PostMapping("/lists" )
    public Result<List<MdmDutyTagDictVo>> lists() {
        List<MdmDutyTagDictVo> mdmDutyTagDictVos = mdmDutyTagDictService.lists();
        log.debug("查询条数:"+mdmDutyTagDictVos.size());
        return Result.success(mdmDutyTagDictVos);
    }

    /**
     * 通过id查询职务标识字典
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmDutyTagDictVo> get(@PathVariable("id" ) Long id) {
        MdmDutyTagDictVo mdmDutyTagDictVo = mdmDutyTagDictService.get(id);
        log.debug("查询成功");
        return Result.success(mdmDutyTagDictVo);
    }

    /**
     * 新增职务标识字典
     * @param mdmDutyTagDictDto 职务标识字典
     * @return Result
     */
    @ApiOperation(value = "新增职务标识字典", notes = "新增职务标识字典")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmDutyTagDictDto mdmDutyTagDictDto) {
        Boolean result = mdmDutyTagDictService.save(mdmDutyTagDictDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改职务标识字典
     * @param mdmDutyTagDictDto 职务标识字典
     * @return Result
     */
    @ApiOperation(value = "修改职务标识字典", notes = "修改职务标识字典")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmDutyTagDictDto mdmDutyTagDictDto) {
        Boolean result = mdmDutyTagDictService.update(mdmDutyTagDictDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除职务标识字典
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除职务标识字典", notes = "通过id删除职务标识字典")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmDutyTagDictService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
