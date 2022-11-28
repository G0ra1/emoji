package com.netwisd.base.mdm.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.mdm.dto.MdmSortDto;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.common.mdm.dto.MdmDutyDto;
import com.netwisd.base.common.mdm.vo.MdmDutyVo;
import com.netwisd.base.mdm.service.MdmDutyService;
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
@RequestMapping("/mdmDuty" )
@Api(value = "mdmDuty", tags = "职务Controller")
@Slf4j
public class MdmDutyController {

    private final  MdmDutyService mdmDutyService;

    /**
     * 分页查询职务
     * 没有使用参数注解，就是默认从form请求的方式
     * @param
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmDutyDto mdmDutyDto) {
        Page pageVo = mdmDutyService.list(mdmDutyDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 不分页集合查询
     * @param
     * @return
     */
    @ApiOperation(value = "不分页集合查询", notes = "不分页集合查询")
    @PostMapping("/lists" )
    public Result<List<MdmDutyVo>> lists(@RequestBody MdmDutyDto mdmDutyDto) {
        List<MdmDutyVo> mdmDutyVos = mdmDutyService.lists(mdmDutyDto);
        log.debug("查询条数:"+mdmDutyVos.size());
        return Result.success(mdmDutyVos);
    }

    /**
     * 通过id查询职务
     * @param
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmDutyVo> get(@PathVariable("id" ) Long id) {
        MdmDutyVo mdmDutyVo = mdmDutyService.get(id);
        log.debug("查询成功");
        return Result.success(mdmDutyVo);
    }

    /**
     * 查询同级所有职务
     * @param
     * @return Result
     */
    @ApiOperation(value = "查询同级所有职务", notes = "查询同级所有职务")
    @PostMapping("/sameLevel")
    public Result<List<MdmDutyVo>> getSameLevel(@RequestBody MdmDutyDto mdmDutyDto) {
        List<MdmDutyVo> mdmDutyVos = mdmDutyService.getSameLevel(mdmDutyDto);
        log.debug("查询成功");
        return Result.success(mdmDutyVos);
    }

    /**
     * 通过部门id查询职务
     * @param
     * @return Result
     */
    @ApiOperation(value = "通过部门id查询已启用的职务", notes = "通过部门id查询已启用的职务")
    @GetMapping("/org/{id}" )
    public Result<List<MdmDutyVo>> getDuty(@PathVariable("id" ) Long id) {
        List<MdmDutyVo> mdmDutyVos = mdmDutyService.getDuty(id);
        log.debug("查询成功");
        return Result.success(mdmDutyVos);
    }

    /**
     * 通过部门id查询职务
     * @param
     * @return Result
     */
    @ApiOperation(value = "查询所有启用职务", notes = "查询所有启用职务")
    @PostMapping("/org/all")
    public Result<List<MdmDutyVo>> getAllDuty(@RequestBody MdmDutyDto mdmDutyDto) {
        List<MdmDutyVo> mdmDutyVos = mdmDutyService.getAllDuty(mdmDutyDto);
        log.debug("查询成功");
        return Result.success(mdmDutyVos);
    }

    /**
     * 新增职务
     * @param
     * @return Result
     */
    @ApiOperation(value = "新增职务", notes = "新增职务")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody List<MdmDutyDto> mdmDutyDtos) {
        Boolean result = mdmDutyService.save(mdmDutyDtos);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 新增职务
     * @param
     * @return Result
     */
    @ApiOperation(value = "单个新增职务", notes = "单个新增职务")
    @PostMapping("/save")
    public Result<Boolean> saveOne(@Validation @RequestBody MdmDutyDto mdmDutyDtos) {
        Boolean result = mdmDutyService.saveOne(mdmDutyDtos);
        log.debug("保存成功");
        return Result.success(result);
    }


    /**
     * 修改职务
     * @param
     * @return Result
     */
    @ApiOperation(value = "修改职务", notes = "修改职务")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmDutyDto mdmDutyDto) {
        Boolean result = mdmDutyService.update(mdmDutyDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除职务
     * @param
     * @return Result
     */
    @ApiOperation(value = "通过id批量删除职务", notes = "通过id批量删除职务")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable String id) {
        Boolean result = mdmDutyService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 复制职务到另一个组织
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "复制职务", notes = "复制职务")
    @PostMapping("/copy")
    public Result<Boolean> copyToOrg(@Validation @RequestBody MdmDutyDto mdmDutyDto) {
        Boolean result = mdmDutyService.copyToOrg(mdmDutyDto);
        log.debug("复制成功");
        return Result.success(result);
    }

    /**
     * 部门内职务排序
     * @param sortDto 排序
     * @return
     */
    @ApiOperation(value = "部门内职务排序", notes = "部门内职务排序")
    @PostMapping("/sort" )
    public Result<Boolean> sortForDept(@Validation @RequestBody MdmSortDto sortDto) {
        Long sourceId = sortDto.getSourceId();
        Long targetId = sortDto.getTargetId();

        String index = sortDto.getIndex();
        if(ObjectUtil.isEmpty(targetId)){
            if(StrUtil.isEmpty(index)){
                throw new IncloudException("置顶、置底、要排序的目标组织、三者至少有一个不能为空！");
            }
        }else {
            if(StrUtil.isNotEmpty(index)){
                log.error("前端传值有误，如果source、target都有值的情况下，Index不能有值，后台强制index置为null！");
                index = null;
            }
        }
        return Result.success(mdmDutyService.sortForDept(sourceId,targetId,index));
    }


}
