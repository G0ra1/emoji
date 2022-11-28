package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.base.portal.dto.PortalTemplateVersionDto;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalTemplateDto;
import com.netwisd.base.portal.vo.PortalTemplateVo;
import com.netwisd.base.portal.service.PortalTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;

import java.util.List;

/**
 * @Description 模板管理 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-11 00:09:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portaltemplate" )
@Api(value = "portaltemplate", tags = "模板管理Controller")
@Slf4j
public class PortalTemplateController {

    private final PortalTemplateService portalTemplateService;

    /**
     * 分页查询模板管理
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalTemplateDto 模板管理
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalTemplateDto portalTemplateDto) {
        Page pageVo = portalTemplateService.list(portalTemplateDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 不分页集合查询
     * @param portalTemplateDto 模板管理
     * @return
     */
    @ApiOperation(value = "不分页集合查询", notes = "不分页集合查询")
    @PostMapping("/lists" )
    public Result<List<PortalTemplateVo>> lists(@RequestBody PortalTemplateDto portalTemplateDto) {
        List<PortalTemplateVo> portalTemplateVos = portalTemplateService.lists(portalTemplateDto);
        log.debug("查询条数:"+portalTemplateVos.size());
        return Result.success(portalTemplateVos);
    }

    /**
     * 版本查看
     * @param portalTemplateDto
     * @return
     */
    @ApiOperation(value = "版本查看", notes = "版本查看")
    @PostMapping("/templateVersions" )
    public Result<List<PortalTemplateVo>> templateVersions(@RequestBody PortalTemplateDto portalTemplateDto) {
        List<PortalTemplateVo> portalTemplateVos = portalTemplateService.templateVersions(portalTemplateDto);
        log.debug("查询条数:"+portalTemplateVos.size());
        return Result.success(portalTemplateVos);
    }

    /**
     * 判断模板是否唯一
     * @param portalTemplateDto 模板管理
     * @return Result
     */
    @ApiOperation(value = "判断模板是否唯一", notes = "判断模板是否唯一")
    @PostMapping("/templateCodeIsOne")
    public Result<Boolean> templateCodeIsOne(@Validation @RequestBody PortalTemplateDto portalTemplateDto) {
        Boolean result = portalTemplateService.templateCodeIsOne(portalTemplateDto);
        log.debug("该模板code是唯一");
        return Result.success(result);
    }

    /**
     * 通过id查询模板管理
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalTemplateVo> get(@PathVariable("id" ) Long id) {
        PortalTemplateVo portalTemplateVo = portalTemplateService.get(id);
        log.debug("查询成功");
        return Result.success(portalTemplateVo);
    }

    /**
     * 新增模板管理
     * @param portalTemplateDto 模板管理
     * @return Result
     */
    @ApiOperation(value = "新增模板管理", notes = "新增模板管理")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalTemplateDto portalTemplateDto) {
        Boolean result = portalTemplateService.save(portalTemplateDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改模板管理
     * @param portalTemplateDto 模板管理
     * @return Result
     */
    @ApiOperation(value = "修改模板管理", notes = "修改模板管理")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalTemplateDto portalTemplateDto) {
        Boolean result = portalTemplateService.update(portalTemplateDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 修改启用版本
     * @param portalId
     * @param terminal
     * @param templateCode
     * @return
     */
    @ApiOperation(value = "修改启用版本", notes = "修改启用版本")
    @PutMapping("/updateStartEnable")
    public Result<Boolean> updateStartEnable(@Validation @RequestParam Long portalId,
                                         @Validation @RequestParam Integer terminal,
                                         @Validation @RequestParam String templateCode) {
        Boolean result = portalTemplateService.updateStartEnable(portalId, terminal, templateCode);
        log.debug("模板启用版本更新成功");
        return Result.success(result);
    }

    /**
     * 修改生效版本
     * @param versionDto
     * @return
     */
    @ApiOperation(value = "修改生效版本", notes = "修改生效版本")
    @PutMapping("/updateVersion")
    public Result<Boolean> updateVersion(@Validation @RequestBody PortalTemplateVersionDto versionDto) {
        Boolean result = portalTemplateService.updateVersion(
                versionDto.getPortalId(), versionDto.getTerminal(), versionDto.getTemplateCode(), versionDto.getTemplateId());
        log.debug("模板生效版本更新成功");
        return Result.success(result);
    }

    /**
     * 删除某个版本
     * @param ids ids
     * @return Result
     */
    @ApiOperation(value = "删除某个版本", notes = "删除某个版本")
    @DeleteMapping("/{ids}" )
    public Result<Boolean> deleteVersion(@PathVariable String ids) {
        Boolean result = portalTemplateService.deleteVersion(ids);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 删除一整个模板
     * @param portalTemplateDto
     * @return
     */
    @ApiOperation(value = "删除一整个模板", notes = "删除一整个模板")
    @DeleteMapping("/deleteTemplate" )
    public Result<Boolean> deleteTemplate(@Validation @RequestBody PortalTemplateDto portalTemplateDto) {
        Boolean result = portalTemplateService.deleteTemplate(portalTemplateDto);
        log.debug("删除成功");
        return Result.success(result);
    }

}
