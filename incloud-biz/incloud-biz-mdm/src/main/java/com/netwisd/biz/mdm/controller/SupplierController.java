package com.netwisd.biz.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.entity.Supplier;
import com.netwisd.biz.mdm.service.MdmMqService;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.mdm.dto.SupplierDto;
import com.netwisd.biz.mdm.vo.SupplierVo;
import com.netwisd.biz.mdm.service.SupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 供应商 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 15:54:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/supplier" )
@Api(value = "supplier", tags = "供应商Controller")
@Slf4j
public class SupplierController {

    private final  SupplierService supplierService;
    private final MdmMqService mdmMqService;

    /**
     * 分页查询供应商
     * 没有使用参数注解，就是默认从form请求的方式
     * @param supplierDto 供应商
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody SupplierDto supplierDto) {
        Page pageVo = supplierService.list(supplierDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询供应商
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<SupplierVo> get(@PathVariable("id" ) Long id) {
        SupplierVo supplierVo = supplierService.get(id);
        log.debug("查询成功");
        return Result.success(supplierVo);
    }

    /**
     * 新增供应商
     * @param supplierDto 供应商
     * @return Result
     */
    @ApiOperation(value = "新增供应商", notes = "新增供应商")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody SupplierDto supplierDto) {
        Boolean result = supplierService.save(supplierDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改供应商
     * @param supplierDto 供应商
     * @return Result
     */
    @ApiOperation(value = "修改供应商", notes = "修改供应商")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody SupplierDto supplierDto) {
        Boolean result = supplierService.update(supplierDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除供应商
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除供应商", notes = "通过id删除供应商")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = supplierService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     *数据同步
     * @param dtoList
     * @return
     */
    @ApiOperation(value = "数据同步", notes = "数据同步")
    @PostMapping("/saveList")
    public Result<Boolean> saveList(@Validation @RequestBody List<SupplierDto> dtoList){
        Boolean result=supplierService.saveTransmitAll(dtoList);
        log.debug("同步成功");
        return Result.success(result);
    }

    /**
     *
     * @return
     */
    @ApiOperation(value = "全量数据推送mq", notes = "全量数据推送mq")
    @PostMapping("/outList")
    public  Result<String> outList(SupplierDto supplierDto){
        mdmMqService.syncMqForSupplier(supplierDto);
        log.debug("推送成功");
        return Result.success("");
    }

}
