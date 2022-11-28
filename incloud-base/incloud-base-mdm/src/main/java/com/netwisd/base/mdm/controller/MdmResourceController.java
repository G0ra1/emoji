package com.netwisd.base.mdm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.dto.MdmResourceDto;
import com.netwisd.base.common.mdm.vo.MdmResourceVo;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.service.MdmResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 资源管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-09 10:39:04
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmResource" )
@Api(value = "mdmResource", tags = "资源管理Controller")
@Slf4j
public class MdmResourceController {

    private final  MdmResourceService mdmResourceService;

    /**
     * 分页查询资源管理
     * 没有使用参数注解，就是默认从form请求的方式
     * @param mdmResourceDto 资源管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<List> list(@RequestBody MdmResourceDto mdmResourceDto) {
        List pageVo = mdmResourceService.list(mdmResourceDto);
        log.debug("查询条数:");
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询资源管理
     * @param mdmResourceDto 资源管理
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result lists(@RequestBody MdmResourceDto mdmResourceDto) {
        List<MdmResourceVo> list = mdmResourceService.lists(mdmResourceDto);
        return Result.success(list);
    }

    /**
     * 通过id查询资源管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmResourceVo> get(@PathVariable("id" ) Long id) {
        MdmResourceVo mdmResourceVo = mdmResourceService.get(id);
        log.debug("查询成功");
        return Result.success(mdmResourceVo);
    }
    /**
     * 通过id查询资源管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询所对应的下一级子节点", notes = "通过id查询所对应的下一级子节点")
    @GetMapping("/kids/{id}" )
    public Result<List<MdmResourceVo>> getKids(@PathVariable("id" ) Long id) {
        List<MdmResourceVo> mdmResourceVos = mdmResourceService.getKids(id);
        log.debug("查询成功");
        return Result.success(mdmResourceVos);
    }
    /**
     * 新增资源管理
     * @param mdmResourceDto 资源管理
     * @return Result
     */
    @ApiOperation(value = "新增资源管理", notes = "新增资源管理")
    @PostMapping
    public Result<Boolean> save(@Validation (exclude =
                                    @ExcludeAnntation(vars = {"level","hasKids"}))
                                    @RequestBody MdmResourceDto mdmResourceDto) {
        Boolean result = mdmResourceService.save(mdmResourceDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改资源管理
     * @param mdmResourceDto 资源管理
     * @return Result
     */
    @ApiOperation(value = "修改资源管理", notes = "修改资源管理")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmResourceDto mdmResourceDto) {
        Boolean result = mdmResourceService.update(mdmResourceDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除资源管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除资源管理", notes = "通过id删除资源管理")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = mdmResourceService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 根据当前登录人获取对应的资源信息
     * @param pid 资源pid
     * @return Result
     */
    @ApiOperation(value = "根据当前登录人获取对应的资源信息 -1查所有", notes = "根据当前登录人获取对应的资源信息 -1查所有")
    @GetMapping("/getResByPid/{pid}")
    public Result getResByPid(@PathVariable("pid" ) Long pid) {
        return Result.success(mdmResourceService.getResByPid(pid));
    }

    /**
     * 效验当前按钮 是否存在该资源权限
     * @param resId 资源id
     * @return Result
     */
    @ApiOperation(value = "效验当前按钮 是否存在该资源权限", notes = "效验当前按钮 是否存在该资源权限")
    @GetMapping("/checkResByResId/{resId}")
    public Result checkResByResId(@PathVariable("resId" ) Long resId) {
        return Result.success(mdmResourceService.checkResByResId(resId));
    }

}
