package com.netwisd.biz.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.service.MdmMqService;
import com.netwisd.biz.mdm.vo.ItemClassifyAllVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.biz.mdm.dto.ItemClassifyDto;
import com.netwisd.biz.mdm.vo.ItemClassifyVo;
import com.netwisd.biz.mdm.service.ItemClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 物资分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:14:41
 */
@RestController
@AllArgsConstructor
@RequestMapping("/itemClassify" )
@Api(value = "itemClassify", tags = "物资分类Controller")
@Slf4j
public class ItemClassifyController {

    private final  ItemClassifyService itemClassifyService;

    private final  MdmMqService mdmMqService;
    /**
     * 分页查询物资分类
     * 没有使用参数注解，就是默认从form请求的方式
     * @param itemClassifyDto 物资分类
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<List<ItemClassifyAllVo>> list(@RequestBody ItemClassifyDto itemClassifyDto) {
        List<ItemClassifyAllVo> classifyVos = itemClassifyService.list(itemClassifyDto);
        log.debug("查询条数:"+classifyVos.size());
        return Result.success(classifyVos);
    }

    /**
     * 通过id查询物资分类
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<ItemClassifyVo> get(@PathVariable Long id) {
        ItemClassifyVo itemClassifyVo = itemClassifyService.get(id);
        log.debug("查询成功");
        return Result.success(itemClassifyVo);
    }

    /**
     * 新增物资分类
     * @param itemClassifyDto 物资分类
     * @return Result
     */
    @ApiOperation(value = "新增物资分类", notes = "新增物资分类")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody ItemClassifyDto itemClassifyDto) {
        Boolean result = itemClassifyService.save(itemClassifyDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改物资
     * @param itemClassifyDto 物资分类
     * @return Result
     */
    @ApiOperation(value = "修改物资分类", notes = "修改物资分类")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody ItemClassifyDto itemClassifyDto) {
        Boolean result = itemClassifyService.update(itemClassifyDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除物资
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除物资分类", notes = "通过id删除物资分类")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = itemClassifyService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     *全量数据推送mq
     * @return
     */
    @ApiOperation(value = "全量数据推送mq", notes = "全量数据推送mq")
    @PostMapping("/outList" )
    public Result<String> outList(ItemClassifyDto itemClassifyDto){
        mdmMqService.syncMqForItemClassify(itemClassifyDto);
        log.debug("全量数据推送成功");
        return Result.success("全量数据推送成功");
    }

    /**
     * 校验
     */
    @ApiOperation(value = "校验", notes = "校验")
    @PostMapping("/dealWithItemClassify" )
    public Result<String> dealWithItemClassify(){
        itemClassifyService.dealWithItemClassify();
        return Result.success("成功");
    }

}
