package com.netwisd.biz.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.mdm.dto.ContractPurchaseDetailsDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseDetailsVo;
import com.netwisd.biz.mdm.service.ContractPurchaseDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 采购合同清单 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:49:25
 */
@RestController
@AllArgsConstructor
@RequestMapping("/contractPurchaseDetails" )
@Api(value = "contractPurchaseDetails", tags = "采购合同清单Controller")
@Slf4j
public class ContractPurchaseDetailsController {

    private final  ContractPurchaseDetailsService contractPurchaseDetailsService;

    /**
     * 分页查询采购合同清单
     * 没有使用参数注解，就是默认从form请求的方式
     * @param contractPurchaseDetailsDto 采购合同清单
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody ContractPurchaseDetailsDto contractPurchaseDetailsDto) {
        Page pageVo = contractPurchaseDetailsService.list(contractPurchaseDetailsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询采购合同清单
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<ContractPurchaseDetailsVo> get(@PathVariable("id" ) Long id) {
        ContractPurchaseDetailsVo contractPurchaseDetailsVo = contractPurchaseDetailsService.get(id);
        log.debug("查询成功");
        return Result.success(contractPurchaseDetailsVo);
    }

    /**
     * 新增采购合同清单
     * @param contractPurchaseDetailsDto 采购合同清单
     * @return Result
     */
    @ApiOperation(value = "新增采购合同清单", notes = "新增采购合同清单")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody ContractPurchaseDetailsDto contractPurchaseDetailsDto) {
        Boolean result = contractPurchaseDetailsService.save(contractPurchaseDetailsDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改采购合同清单
     * @param contractPurchaseDetailsDto 采购合同清单
     * @return Result
     */
    @ApiOperation(value = "修改采购合同清单", notes = "修改采购合同清单")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody ContractPurchaseDetailsDto contractPurchaseDetailsDto) {
        Boolean result = contractPurchaseDetailsService.update(contractPurchaseDetailsDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除采购合同清单
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除采购合同清单", notes = "通过id删除采购合同清单")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = contractPurchaseDetailsService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

}
