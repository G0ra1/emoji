package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.vo.PortalEnableDataVo;
import com.netwisd.base.portal.vo.PortalTemplateVo;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalPortalDto;
import com.netwisd.base.portal.vo.PortalPortalVo;
import com.netwisd.base.portal.service.PortalPortalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 门户维护 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-11 09:50:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalPortal" )
@Api(value = "portalportal", tags = "门户维护Controller")
@Slf4j
public class PortalPortalController {

    private final  PortalPortalService portalPortalService;

    /**
     * 分页查询门户维护
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalPortalDto 门户维护
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalPortalDto portalPortalDto) {
        Page pageVo = portalPortalService.list(portalPortalDto);
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
    public Result<List<PortalPortalVo>> lists() {
        List<PortalPortalVo> lists = portalPortalService.lists();
        log.debug("查询条数:"+lists.size());
        return Result.success(lists);
    }

    /**
     * 通过id查询门户
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalPortalVo> get(@PathVariable("id" ) Long id) {
        PortalPortalVo portalPortalVo = portalPortalService.get(id);
        log.debug("查询成功");
        return Result.success(portalPortalVo);
    }

    /**
     * 新增门户
     * @param portalPortalDto 组织
     * @return Result
     */
    @ApiOperation(value = "新增门户", notes = "新增门户")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalPortalDto portalPortalDto) {
        Boolean result = portalPortalService.save(portalPortalDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改门户
     * @param portalPortalDto 组织
     * @return Result
     */
    @ApiOperation(value = "修改门户", notes = "修改门户")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalPortalDto portalPortalDto) {
        Boolean result = portalPortalService.update(portalPortalDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除门户
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除门户", notes = "通过id删除门户")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalPortalService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 设置首页
     * @param
     * @return Result
     */
    @ApiOperation(value = "设置首页", notes = "设置首页")
    @PutMapping("/{id}")
    public Result<Boolean> homePage(@PathVariable Long id) {
        Boolean result = portalPortalService.homePage(id);
        log.debug("更新成功");
        return Result.success(result);
    }
    /**
     * 修改点击量
     *
     * @param id
     * @return Result
     */
    @ApiOperation(value = " 修改点击量", notes = " 修改点击量")
    @GetMapping("/upHits")
    public Result<Boolean> upHits(@RequestParam("id") Long id) {
        Boolean result = portalPortalService.upHits(id);
        log.debug("点击量+1");
        return Result.success(result);
    }

    /**
     * 通过门户id和所属终端查询当前门户下启用的模板内容
     * @param portalId
     * @param terminal
     * @return
     */
    @ApiOperation(value = " 通过门户id和所属终端查询当前门户下启用的模板内容", notes = " 通过门户id和所属终端查询当前门户下启用的模板内容")
    @GetMapping("/findTemplateByPortalId")
    public Result<PortalTemplateVo> findTemplateByPortalId(@RequestParam("portalId") Long portalId,@RequestParam("terminal") Integer terminal) {
        PortalTemplateVo templateVo = portalPortalService.findTemplateByPortalId(portalId, terminal);
        log.debug("通过门户id和所属终端查询当前门户下启用的模板内容成功！");
        return Result.success(templateVo);
    }

    /**
     * 获取默认门户首页
     * @return Result
     */
    @ApiOperation(value = " 获取默认门户首页", notes = " 获取默认门户首页")
    @GetMapping("/getDefaultPortal")
    public PortalPortalVo getDefaultPortal() {
        PortalPortalVo portalPortalVo = portalPortalService.getDefaultPortal();
        return portalPortalVo;
    }

    /**
     * 通过生效门户 获取当前门户下模板；获取当前生效主题
     * @return
     */
    @ApiOperation(value = " 通过生效门户 获取当前门户下模板；获取当前生效主题", notes = " 通过默认门户 获取当前门户下模板；获取当前生效主题")
    @GetMapping("/getEnableData")
    public Result<PortalEnableDataVo> getEnableData() {
        PortalEnableDataVo portalEnableDataVo = portalPortalService.getEnableData();
        log.debug("通过生效门户 获取当前门户下模板；获取当前生效主题 成功！");
        return Result.success(portalEnableDataVo);
    }
}
