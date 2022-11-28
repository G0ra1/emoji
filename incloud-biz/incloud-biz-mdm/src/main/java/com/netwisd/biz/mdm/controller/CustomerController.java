package com.netwisd.biz.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.entity.Customer;
import com.netwisd.biz.mdm.service.MdmMqService;
import com.netwisd.biz.mdm.vo.SupplierVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.mdm.dto.CustomerDto;
import com.netwisd.biz.mdm.vo.CustomerVo;
import com.netwisd.biz.mdm.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 客户 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 16:45:51
 */
@RestController
@AllArgsConstructor
@RequestMapping("/customer" )
@Api(value = "customer", tags = "客户Controller")
@Slf4j
public class CustomerController {

    private final  CustomerService customerService;
    private final MdmMqService mdmMqService;

    /**
     * 分页查询客户
     * 没有使用参数注解，就是默认从form请求的方式
     * @param customerDto 客户
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody CustomerDto customerDto) {
        Page pageVo = customerService.list(customerDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询客户
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<CustomerVo> get(@PathVariable("id" ) Long id) {
        CustomerVo customerVo = customerService.get(id);
        log.debug("查询成功");
        return Result.success(customerVo);
    }

    /**
     * 新增客户
     * @param customerDto 客户
     * @return Result
     */
    @ApiOperation(value = "新增客户", notes = "新增客户")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody CustomerDto customerDto) {
        Boolean result = customerService.save(customerDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改客户
     * @param customerDto 客户
     * @return Result
     */
    @ApiOperation(value = "修改客户", notes = "修改客户")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody CustomerDto customerDto) {
        Boolean result = customerService.update(customerDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除客户
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除客户", notes = "通过id删除客户")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = customerService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 同步客户数据
     * @param dtos 客户list
     * @return Result
     */
    @ApiOperation(value = "同步客户数据", notes = "同步客户数据")
    @PostMapping("/saveList")
    public Result<Boolean> saveList(@Validation @RequestBody List<CustomerDto> dtos){
        Boolean result=customerService.saveList(dtos);
        log.debug("同步成功");
        return Result.success(result);
    }


    /**
     * 删除客户数据
     * @param dtos 客户list
     * @return Result
     */
    @ApiOperation(value = "删除客户数据", notes = "删除客户数据")
    @PostMapping("/delList")
    public Result<Boolean> delList(@RequestBody List<CustomerDto> dtos){
        Boolean result=customerService.delList(dtos);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     *
     * @return
     */
    @ApiOperation(value = "全量数据推送mq", notes = "全量数据推送mq")
    @PostMapping("/outList")
    public  Result<String> outList(CustomerDto customerDto){
        mdmMqService.syncMqForCustomer(customerDto);
        log.debug("推送成功");
        return Result.success("全量数据推送成功");
    }


}
