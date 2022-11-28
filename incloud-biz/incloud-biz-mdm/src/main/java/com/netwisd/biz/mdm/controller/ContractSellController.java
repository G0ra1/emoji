package com.netwisd.biz.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.service.MdmMqService;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.mdm.dto.ContractSellDto;
import com.netwisd.biz.mdm.vo.ContractSellVo;
import com.netwisd.biz.mdm.service.ContractSellService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 销售合同 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-06 11:07:24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/contractSell" )
@Api(value = "contractSell", tags = "销售合同Controller")
@Slf4j
public class ContractSellController {

    private final  ContractSellService contractSellService;
    private final MdmMqService mdmMqService;

    /**
     * 分页查询销售合同
     * 没有使用参数注解，就是默认从form请求的方式
     * @param contractSellDto 销售合同
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody ContractSellDto contractSellDto) {
        Page pageVo = contractSellService.list(contractSellDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询销售合同
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<ContractSellVo> get(@PathVariable("id" ) Long id) {
        ContractSellVo contractSellVo = contractSellService.get(id);
        log.debug("查询成功");
        return Result.success(contractSellVo);
    }

    /**
     * 新增销售合同
     * @param contractSellDto 销售合同
     * @return Result
     */
    @ApiOperation(value = "新增销售合同", notes = "新增销售合同")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody ContractSellDto contractSellDto) {
        Boolean result = contractSellService.save(contractSellDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改销售合同
     * @param contractSellDto 销售合同
     * @return Result
     */
    @ApiOperation(value = "修改销售合同", notes = "修改销售合同")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody ContractSellDto contractSellDto) {
        Boolean result = contractSellService.update(contractSellDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除销售合同
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除销售合同", notes = "通过id删除销售合同")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = contractSellService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 同步数据
     * @param dtoList
     * @return
     */
    @ApiOperation(value = "同步数据", notes = "同步数据")
    @PostMapping("/saveList")
    public Result<Boolean> saveList(@Validation @RequestBody List<ContractSellDto> dtoList){
        Boolean result = contractSellService.saveList(dtoList);
        log.debug("同步成功");
        return Result.success(result);
    }

    /**
     * 全量数据推送第三方
     * @return
     */
    @ApiOperation(value = "全量数据推送mq", notes = "全量数据推送mq")
    @PostMapping("/outList")
    public Result<String> outList(ContractSellDto sellDto){
        mdmMqService.syncMqForSell(sellDto);
        log.debug("推送成功");
        return Result.success("推送成功");
    }

    /**
     * 删除数据
     * @param dtoList
     * @return
     */
    @ApiOperation(value = "删除数据", notes = "删除数据")
    @PostMapping("/delList")
    public Result<Boolean> delList(@Validation @RequestBody List<ContractSellDto> dtoList){
        Boolean result = contractSellService.delList(dtoList);
        log.debug("同步成功");
        return Result.success(result);
    }
}
