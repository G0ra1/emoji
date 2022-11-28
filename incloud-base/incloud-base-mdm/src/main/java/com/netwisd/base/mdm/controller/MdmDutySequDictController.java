package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmDutySequDictDto;
import com.netwisd.base.mdm.vo.MdmDutySequDictVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.service.MdmDutySequDictService;
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
@RequestMapping("/mdmDutySequDict" )
@Api(value = "mdmDutySequDict", tags = "职务序列字典Controller")
@Slf4j
public class MdmDutySequDictController {

    private final  MdmDutySequDictService mdmDutySequDictService;

    /**
     * 分页查询职务序列
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmDutySequDictDto 职务序列
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmDutySequDictDto mdmDutySequDictDto) {
        Page pageVo = mdmDutySequDictService.list(mdmDutySequDictDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询职务序列
     * @param
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<List<MdmDutySequDictVo>> lists() {
        List<MdmDutySequDictVo>  mdmDutySequDictVos= mdmDutySequDictService.lists();
        log.debug("查询条数:"+mdmDutySequDictVos.size());
        return Result.success(mdmDutySequDictVos);
    }

    /**
     * 通过id查询职务序列
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmDutySequDictVo> get(@PathVariable("id" ) Long id) {
        MdmDutySequDictVo mdmDutySequDictVo = mdmDutySequDictService.get(id);
        log.debug("查询成功");
        return Result.success(mdmDutySequDictVo);
    }

    /**
     * 新增职务序列
     * @param mdmDutySequDictDto 职务序列
     * @return Result
     */
    @ApiOperation(value = "新增职务序列", notes = "新增职务序列")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MdmDutySequDictDto mdmDutySequDictDto) {
        Boolean result = mdmDutySequDictService.save(mdmDutySequDictDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改职务序列
     * @param mdmDutySequDictDto 职务序列
     * @return Result
     */
    @ApiOperation(value = "修改职务序列", notes = "修改职务序列")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmDutySequDictDto mdmDutySequDictDto) {
        Boolean result = mdmDutySequDictService.update(mdmDutySequDictDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除职务序列
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除职务序列", notes = "通过id删除职务序列")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmDutySequDictService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
