package com.netwisd.base.dict.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.dict.dto.EncondRuleDetailDto;
import com.netwisd.base.common.dict.vo.EncondRuleDetailVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.dict.service.EncondRuleDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

/**
 * @Description 编码规则详情 功能描述...
 * @author 云数网讯 zhanghongbin@netwisd.com
 * @date 2022-03-07 17:41:47
 */
@RestController
@AllArgsConstructor
@RequestMapping("/encondRuleDetail" )
@Api(value = "encondRuleDetail", tags = "编码规则详情Controller")
@Slf4j
public class EncondRuleDetailController {

    private final  EncondRuleDetailService encondRuleDetailService;

    /**
     * 分页查询编码规则详情
     * 没有使用参数注解，就是默认从form请求的方式
     * @param encondRuleDetailDto 编码规则详情
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody EncondRuleDetailDto encondRuleDetailDto) {
        Page pageVo = encondRuleDetailService.list(encondRuleDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询编码规则详情
     * @param encondRuleDetailDto 编码规则详情
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<Page> lists(@RequestBody EncondRuleDetailDto encondRuleDetailDto) {
        Page pageVo = encondRuleDetailService.lists(encondRuleDetailDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 通过id查询编码规则详情
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<EncondRuleDetailVo> get(@PathVariable("id" ) Long id) {
        EncondRuleDetailVo encondRuleDetailVo = encondRuleDetailService.get(id);
        log.debug("查询成功");
        return Result.success(encondRuleDetailVo);
    }

    /**
     * 新增编码规则详情
     * @param encondRuleDetailDto 编码规则详情
     * @return Result
     */
    @ApiOperation(value = "新增编码规则详情", notes = "新增编码规则详情")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody EncondRuleDetailDto encondRuleDetailDto) {
        encondRuleDetailService.save(encondRuleDetailDto);
        log.debug("保存成功");
        return Result.success();
    }

    /**
     * 修改编码规则详情
     * @param encondRuleDetailDto 编码规则详情
     * @return Result
     */
    @ApiOperation(value = "修改编码规则详情", notes = "修改编码规则详情")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody EncondRuleDetailDto encondRuleDetailDto) {
        encondRuleDetailService.update(encondRuleDetailDto);
        log.debug("更新成功");
        return Result.success();
    }

    /**
     * 通过id删除编码规则详情
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除编码规则详情", notes = "通过id删除编码规则详情")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        encondRuleDetailService.delete(id);
        log.debug("删除成功");
        return Result.success();
    }

}
