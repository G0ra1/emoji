package com.netwisd.biz.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ItemClassifyDto;
import com.netwisd.biz.mdm.dto.ItemDto;
import com.netwisd.biz.mdm.entity.ItemClassify;
import com.netwisd.biz.mdm.service.ItemClassifyService;
import com.netwisd.biz.mdm.service.ItemService;
import com.netwisd.biz.mdm.vo.ItemClassifyAllVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: baiyulan@netwisd.com
 * @Date: 2022/5/18 11:11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Api(value = "api", tags = "对外提供的restFull接口")
@Slf4j
public class ApiController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ItemClassifyService itemClassifyService;

    /**
     * 查询（isDefault==1，常规查询，其他为递归查询）
     * 没有使用参数注解，就是默认从form请求的方式
     * @param itemClassifyDto 物资分类
     * @return
     */
    @ApiOperation(value = "查询（isDefault==1，常规查询，其他为递归查询）", notes = "查询（isDefault==1，常规查询，其他为递归查询）")
    @PostMapping("/queryItemClassify" )
    public Result<List<ItemClassifyAllVo>> queryItemClassify(@RequestBody ItemClassifyDto itemClassifyDto) {
        List<ItemClassifyAllVo> classifyVos = itemClassifyService.list(itemClassifyDto);
        log.debug("查询条数:"+classifyVos.size());
        return Result.success(classifyVos);
    }
    /**
     * 分页查询物资
     * 没有使用参数注解，就是默认从form请求的方式
     * @param itemDto 物资
     * @return
     */
    @ApiOperation(value = "分页查询物资", notes = "分页查询物资")
    @PostMapping("/queryItemPage" )
    public Result<Page> queryItemPage(@RequestBody ItemDto itemDto) {
        Page pageVo = itemService.list(itemDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }
}
