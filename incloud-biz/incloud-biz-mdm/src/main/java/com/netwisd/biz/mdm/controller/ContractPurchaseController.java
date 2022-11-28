package com.netwisd.biz.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractPurchaseThransmitDto;
import com.netwisd.biz.mdm.service.MdmMqService;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.mdm.dto.ContractPurchaseDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseVo;
import com.netwisd.biz.mdm.service.ContractPurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 采购合同 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:33:08
 */
@RestController
@AllArgsConstructor
@RequestMapping("/contractPurchase" )
@Api(value = "contractPurchase", tags = "采购合同Controller")
@Slf4j
public class ContractPurchaseController {

    private final  ContractPurchaseService contractPurchaseService;
    private final MdmMqService mdmMqService;

    /**
     * 分页查询采购合同
     * 没有使用参数注解，就是默认从form请求的方式
     * @param contractPurchaseDto 采购合同
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody ContractPurchaseDto contractPurchaseDto) {
        Page pageVo = contractPurchaseService.list(contractPurchaseDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }


    /**
     * 通过id查询采购合同
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<ContractPurchaseVo> get(@PathVariable("id" ) Long id) {
        ContractPurchaseVo contractPurchaseVo = contractPurchaseService.get(id);
        log.debug("查询成功");
        return Result.success(contractPurchaseVo);
    }

    /**
     * 新增采购合同
     * @param contractPurchaseDto 采购合同
     * @return Result
     */
    @ApiOperation(value = "新增采购合同", notes = "新增采购合同")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody ContractPurchaseDto contractPurchaseDto) {
        Boolean result = contractPurchaseService.save(contractPurchaseDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改采购合同
     * @param contractPurchaseDto 采购合同
     * @return Result
     */
    @ApiOperation(value = "修改采购合同", notes = "修改采购合同")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody ContractPurchaseDto contractPurchaseDto) {
        Boolean result = contractPurchaseService.update(contractPurchaseDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除采购合同
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除采购合同", notes = "通过id删除采购合同")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = contractPurchaseService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 数据同步
     * @param dtoList
     * @return
     */
    /*@ApiOperation(value = "数据同步", notes = "数据同步")
    @PostMapping("/saveList")
    public Result<Boolean> saveList(List<ContractPurchaseThransmitDto> dtoList) {
        Boolean result = contractPurchaseService.saveTransmitAll(dtoList);
        log.debug("同步成功");
        return Result.success(result);
    }
*/
    /**
     * 全量数据推送mq
     * @return
     */
    @ApiOperation(value = "全量数据推送mq", notes = "全量数据推送mq")
    @PostMapping("/outList")
    public Result<String> outList(ContractPurchaseDto contractPurchaseDto){
        mdmMqService.syncMqForPurchase(contractPurchaseDto);
        log.debug("推送成功");
        return Result.success("推送成功");
    }

    /**
     * 分页查询(不包含框架协议)
     * 没有使用参数注解，就是默认从form请求的方式
     * @param contractPurchaseDto 采购合同
     * @return
     */
    @ApiOperation(value = "分页查询(不包含框架协议)", notes = "分页查询(不包含框架协议)")
    @PostMapping("/getList" )
    public Result<Page> getList(@RequestBody ContractPurchaseDto contractPurchaseDto) {
        Page pageVo = contractPurchaseService.getList(contractPurchaseDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }
}
