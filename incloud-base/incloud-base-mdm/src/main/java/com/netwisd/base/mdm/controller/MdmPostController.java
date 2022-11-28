package com.netwisd.base.mdm.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.common.mdm.dto.MdmPostDto;
import com.netwisd.base.common.mdm.vo.MdmPostVo;
import com.netwisd.base.mdm.dto.MdmSortDto;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.mdm.service.MdmPostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 岗位 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 *
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mdmPost" )
@Api(value = "mdmPost", tags = "岗位Controller")
@Slf4j
public class MdmPostController {

    private final  MdmPostService mdmPostService;

    /**
     * 分页查询岗位
     * 没有使用参数注解，就是默认从form请求的方式
     * @param
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody MdmPostDto mdmPostDto) {
        Page pageVo = mdmPostService.list(mdmPostDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 不分页集合查询
     * @param
     * @return
     */
    @ApiOperation(value = "不分页集合查询", notes = "不分页集合查询")
    @PostMapping("/lists" )
    public Result<List<MdmPostVo>> lists(@RequestBody MdmPostDto mdmPostDto) {
        List<MdmPostVo> mdmPostVos = mdmPostService.lists(mdmPostDto);
        log.debug("查询条数:"+mdmPostVos.size());
        return Result.success(mdmPostVos);
    }

    /**
     * 通过id查询岗位
     * @param
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<MdmPostVo> get(@PathVariable("id" ) Long id) {
        MdmPostVo mdmPostVo = mdmPostService.get(id);
        log.debug("查询成功");
        return Result.success(mdmPostVo);
    }

    /**
     * 查询同级所有岗位
     * @param
     * @return Result
     */
    @ApiOperation(value = "查询同级所有岗位", notes = "查询同级所有岗位")
    @PostMapping("/sameLevel")
    public Result<List<MdmPostVo>> getSameLevel(@RequestBody MdmPostDto mdmPostDto) {
        List<MdmPostVo> mdmPostVos = mdmPostService.getSameLevel(mdmPostDto);
        log.debug("查询成功");
        return Result.success(mdmPostVos);
    }

    /**
     * 通过部门id查询岗位
     * @param
     * @return Result
     */
    @ApiOperation(value = "通过部门id查询已启用的岗位", notes = "通过部门id查询已启用的岗位")
    @GetMapping("/org/{id}" )
    public Result<List<MdmPostVo>> getPost(@PathVariable("id" ) Long id) {
        List<MdmPostVo> mdmPostVos = mdmPostService.getPost(id);
        log.debug("查询成功");
        return Result.success(mdmPostVos);
    }

    /**
     * 通过部门id查询岗位
     * @param
     * @return Result
     */
    @ApiOperation(value = "查询所有启用岗位", notes = "查询所有启用岗位")
    @PostMapping("/org/all")
    public Result<List<MdmPostVo>> getAllPost(@RequestBody MdmPostDto mdmPostDto) {
        List<MdmPostVo> mdmPostVos = mdmPostService.getAllPost(mdmPostDto);
        log.debug("查询成功");
        return Result.success(mdmPostVos);
    }

    /**
     * 新增岗位
     * @param
     * @return Result
     */
    @ApiOperation(value = "新增岗位", notes = "新增岗位")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody List<MdmPostDto> mdmPostDtos) {
        Boolean result = mdmPostService.save(mdmPostDtos);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 新增岗位
     * @param
     * @return Result
     */
    @ApiOperation(value = "单个新增岗位", notes = "单个新增岗位")
    @PostMapping("/save")
    public Result<Boolean> saveOne(@Validation @RequestBody MdmPostDto mdmPostDtos) {
        Boolean result = mdmPostService.saveOne(mdmPostDtos);
        log.debug("保存成功");
        return Result.success(result);
    }


    /**
     * 修改岗位
     * @param
     * @return Result
     */
    @ApiOperation(value = "修改岗位", notes = "修改岗位")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody MdmPostDto mdmPostDto) {
        Boolean result = mdmPostService.update(mdmPostDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除岗位
     * @param
     * @return Result
     */
    @ApiOperation(value = "通过id批量删除岗位", notes = "通过id批量删除岗位")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable String id) {
        Boolean result = mdmPostService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 复制岗位到另一个组织
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "复制岗位", notes = "复制岗位")
    @PostMapping("/copy")
    public Result<Boolean> copyToOrg(@Validation @RequestBody MdmPostDto mdmPostDto) {
        Boolean result = mdmPostService.copyToOrg(mdmPostDto);
        log.debug("复制成功");
        return Result.success(result);
    }

    /**
     * 部门内岗位排序
     * @param sortDto 排序
     * @return
     */
    @ApiOperation(value = "部门内岗位排序", notes = "部门内岗位排序")
    @PostMapping("/sort" )
    public Result<Boolean> sortForDept(@Validation @RequestBody MdmSortDto sortDto) {
        Long sourceId = sortDto.getSourceId();
        Long targetId = sortDto.getTargetId();

        String index = sortDto.getIndex();
        if(ObjectUtil.isEmpty(targetId)){
            if(StrUtil.isEmpty(index)){
                throw new IncloudException("置顶、置底、要排序的目标组织、三者至少有一个不能为空！");
            }
        }else {
            if(StrUtil.isNotEmpty(index)){
                log.error("前端传值有误，如果source、target都有值的情况下，Index不能有值，后台强制index置为null！");
                index = null;
            }
        }
        return Result.success(mdmPostService.sortForDept(sourceId,targetId,index));
    }
}
