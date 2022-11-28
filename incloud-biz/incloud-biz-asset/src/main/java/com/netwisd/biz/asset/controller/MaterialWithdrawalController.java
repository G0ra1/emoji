package com.netwisd.biz.asset.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.service.AssetsDetailService;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.biz.asset.vo.MaterialAcceptResultVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.asset.dto.MaterialWithdrawalDto;
import com.netwisd.biz.asset.vo.MaterialWithdrawalVo;
import com.netwisd.biz.asset.service.MaterialWithdrawalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 资产退库 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-08-01 10:45:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/materialWithdrawal" )
@Api(value = "materialWithdrawal", tags = "资产退库Controller")
@Slf4j
public class MaterialWithdrawalController {

    private final  MaterialWithdrawalService materialWithdrawalService;
    /**
     * 分页查询资产退库
     * 没有使用参数注解，就是默认从form请求的方式
     * @param materialWithdrawalDto 资产退库
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MaterialWithdrawalDto materialWithdrawalDto) {
        Page pageVo = materialWithdrawalService.list(materialWithdrawalDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询资产退库
     * @param materialWithdrawalDto 资产退库
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody MaterialWithdrawalDto materialWithdrawalDto) {
        Page pageVo = materialWithdrawalService.lists(materialWithdrawalDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询资产退库
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MaterialWithdrawalVo> get(@PathVariable("id" ) Long id) {
        MaterialWithdrawalVo materialWithdrawalVo = materialWithdrawalService.get(id);
        log.debug("查询成功");
        return Result.success(materialWithdrawalVo);
    }

    /**
     * 新增资产退库
     * @param materialWithdrawalDto 资产退库
     * @return Result
     */
    @ApiOperation(value = "新增资产退库", notes = "新增资产退库")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody MaterialWithdrawalDto materialWithdrawalDto) {
        materialWithdrawalService.save(materialWithdrawalDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改资产退库
     * @param materialWithdrawalDto 资产退库
     * @return Result
     */
    @ApiOperation(value = "修改资产退库", notes = "修改资产退库")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MaterialWithdrawalDto materialWithdrawalDto) {
        materialWithdrawalService.update(materialWithdrawalDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除资产退库
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除资产退库", notes = "通过id删除资产退库")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        materialWithdrawalService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

    /**
     * 获取待退库物资资产明细
     * @param materialWithdrawalDto
     * @return Result
     */
    @ApiOperation(value = "获取待退库物资资产明细",notes = "获取待退库物资资产明细")
    @PostMapping("/getWithdrawalDetail")
    public Result getWithdrawalDetail(@RequestBody MaterialWithdrawalDto materialWithdrawalDto){
      Page<MaterialAcceptResultVo> pageVo =  materialWithdrawalService.getWithdrawalDetail(materialWithdrawalDto);
      log.debug("queryWrapper查询数据 ---->{}",pageVo.getRecords());
      return Result.success(pageVo);
    }
    /**
     * 流程中新增或修改
     * @param materialWithdrawalDto
     * @return Result
     */
    @ApiOperation(value = "流程中新增或修改",notes = "获取待退库物资资产明细")
    @PostMapping("/procSaveOrUpdate")
      public Result<MaterialWithdrawalVo> procSaveOrUpdate(@RequestBody MaterialWithdrawalDto materialWithdrawalDto){
            return Result.success(materialWithdrawalService.procSaveOrUpdate(materialWithdrawalDto));
        }
    /**
     * 通过流程实例Id查询
     * @param
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id查询", notes = "通过流程实例id查询")
    @GetMapping("/proc/{procInstId}")
    public Result<MaterialWithdrawalVo> getByProc(@PathVariable("procInstId") String procInstId){
      MaterialWithdrawalVo materialWithdrawalVo =  materialWithdrawalService.getByProc(procInstId);
      log.debug("通过流程id查询成功");
      return Result.success(materialWithdrawalVo);
    }

    /**
     * 通根据流程实例id删除业务数据
     * @param procInstId procInstId
     */
    @ApiOperation(value = "通过流程实例id删除", notes = "通过流程实例id删除")
    @DeleteMapping("/procDelete/{procInstId}" )
    public Result deleteByProc(@PathVariable("procInstId" ) String procInstId) {
        materialWithdrawalService.deleteByProc(procInstId);
        log.debug("删除成功");
        return Result.success();
    }
    /**
     * 退库流程结束
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "退库流程结束", notes = "退库流程结束")
    @PostMapping("purWithdrawalAuditSuccess/{procInstId}" )
    public Result purWithdrawalAsuditSuccess(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialWithdrawalService.procAuditSuccess(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 退库流程保存前
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "退库流程保存前", notes = "退库流程保存前")
    @PostMapping("/saveBefore/{procInstId}" )
    public Result saveBefore(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialWithdrawalService.procSaveBefore(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }

    /**
     * 退库流程保存后
     * @param procInstId procInstId
     * @return Result
     */
    @ApiOperation(value = "退库流程保存后", notes = "退库流程保存后")
    @PostMapping("/saveAfter/{procInstId}" )
    public Result saveAfter(@PathVariable("procInstId" ) String procInstId) {
        Boolean result = materialWithdrawalService.procSaveAfter(procInstId);
        log.debug("处理成功");
        return Result.success(result);
    }
}
