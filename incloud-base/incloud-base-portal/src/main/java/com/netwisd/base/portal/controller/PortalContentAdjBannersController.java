package com.netwisd.base.portal.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.portal.dto.PortalContentAdjBannersDto;
import com.netwisd.base.portal.vo.PortalContentAdjBannersVo;
import com.netwisd.base.portal.service.PortalContentAdjBannersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.base.wf.starter.dto.ProcViewDto;
import com.netwisd.base.wf.starter.dto.WfEngineDto;
import com.netwisd.common.core.anntation.ExcludeAnntation;
import com.netwisd.common.core.util.JacksonUtil;

import java.util.List;

/**
 * @Description banner类内容-调整表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-30 00:02:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("/portalContentAdjBanners" )
@Api(value = "portalContentAdjBanners", tags = "banner类内容-调整表Controller")
@Slf4j
public class PortalContentAdjBannersController {

    private final  PortalContentAdjBannersService portalContentAdjBannersService;

    /**
     * 分页查询banner类内容-调整表
     * 没有使用参数注解，就是默认从form请求的方式
     * @param portalContentAdjBannersDto banner类内容-调整表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @PostMapping("/list" )
    public Result<Page> list(@RequestBody PortalContentAdjBannersDto portalContentAdjBannersDto) {
        Page pageVo = portalContentAdjBannersService.list(portalContentAdjBannersDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return Result.success(pageVo);
    }

    /**
     * 分页自定义查询banner类内容-调整表
     * @param portalContentAdjBannersDto banner类内容-调整表
     * @return
     */
    @ApiOperation(value = "分页自定义查询", notes = "分页自定义查询")
    @PostMapping("/lists" )
    public Result<List> lists(@RequestBody PortalContentAdjBannersDto portalContentAdjBannersDto) {
        List<PortalContentAdjBannersVo>  portalContentAdjBannersVos = portalContentAdjBannersService.lists(portalContentAdjBannersDto);
        log.debug("查询条数:"+portalContentAdjBannersVos.size());
        return Result.success(portalContentAdjBannersVos);
    }

    /**
     * 通过id查询banner类内容-调整表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public Result<PortalContentAdjBannersVo> get(@PathVariable("id" ) Long id) {
        PortalContentAdjBannersVo portalContentAdjBannersVo = portalContentAdjBannersService.get(id);
        log.debug("查询成功");
        return Result.success(portalContentAdjBannersVo);
    }

    /**
     * 新增banner类内容-调整表
     * @param portalContentAdjBannersDto banner类内容-调整表
     * @return Result
     */
    @ApiOperation(value = "新增banner类内容-调整表", notes = "新增banner类内容-调整表")
    @PostMapping
    public Result<Boolean> save(@Validation @RequestBody PortalContentAdjBannersDto portalContentAdjBannersDto) {
        Boolean result = portalContentAdjBannersService.save(portalContentAdjBannersDto);
        log.debug("保存成功");
        return Result.success(result);
    }

    /**
     * 修改banner类内容-调整表
     * @param portalContentAdjBannersDto banner类内容-调整表
     * @return Result
     */
    @ApiOperation(value = "修改banner类内容-调整表", notes = "修改banner类内容-调整表")
    @PutMapping
    public Result<Boolean> update(@Validation @RequestBody PortalContentAdjBannersDto portalContentAdjBannersDto) {
        Boolean result = portalContentAdjBannersService.update(portalContentAdjBannersDto);
        log.debug("更新成功");
        return Result.success(result);
    }

    /**
     * 通过id删除banner类内容-调整表
     * @param id id
     * @return Result
     */
    @ApiOperation(value = "通过id删除banner类内容-调整表", notes = "通过id删除banner类内容-调整表")
    @DeleteMapping("/{id}" )
    public Result<Boolean> delete(@PathVariable Long id) {
        Boolean result = portalContentAdjBannersService.delete(id);
        log.debug("删除成功");
        return Result.success(result);
    }

    /**
     * 通过流程实例id 查询banner类内容-调整表
     * @param procViewDto
     * @return Result
     */
    @ApiOperation(value = "通过流程实例id 查询banner类内容-调整表", notes = "通过流程实例id 查询banner类内容-调整表")
    @PostMapping("/procView" )
    public Result<String> procView(@RequestBody ProcViewDto procViewDto) {
            PortalContentAdjBannersVo portalContentAdjBannersVo = portalContentAdjBannersService.procView(procViewDto);
        log.debug("查询成功");
        return Result.success(JacksonUtil.toJSONString(portalContentAdjBannersVo));
    }

    /**
     * 新增banner类内容-调整表
     * @param portalContentAdjBannersDto 新增banner类内容-调整表
     * @return Result
     */
    @ApiOperation(value = "新增banner类内容-调整表", notes = "新增banner类内容-调整表")
    @PostMapping("/procSave")
    public Result<Boolean> procSave(@Validation @RequestBody PortalContentAdjBannersDto portalContentAdjBannersDto) {
        log.debug("新增banner类内容-调整表");
        return Result.success(portalContentAdjBannersService.procSave(portalContentAdjBannersDto));
    }

    /**
     * banner类内容-调整表提交
     * @param portalContentAdjBannersDto banner类内容-调整表提交
     * @return Result
     */
    @ApiOperation(value = "banner类内容-调整表提交", notes = "banner类内容-调整表提交")
    @PostMapping("/procSubmit")
    public Result<Boolean> procSubmit(@Validation(exclude = {
            @ExcludeAnntation(clazz = WfEngineDto.class, vars = {"taskId","innerVariable","bizInfoDto"})} ) @RequestBody PortalContentAdjBannersDto portalContentAdjBannersDto) {
        log.debug("banner类内容-调整表提交");
        return Result.success(portalContentAdjBannersService.procSubmit(portalContentAdjBannersDto));
    }
    /**
     * banner类内容-调整表流程删除
     *
     * @param camundaProcinsId 流程实例id
     * @return Result
     */
    @ApiOperation(value = " banner类内容-调整表流程删除", notes = " banner类内容-调整表流程删除")
    @GetMapping("/procDel")
    public Result procDel(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        log.debug(" banner类内容-调整表流程删除");
        return portalContentAdjBannersService.procDel(camundaProcinsId);
    }

    /**
     * banner类内容-调整表流程中止
     *
     * @param camundaProcinsId 流程实例id
     * @return Result
     */
    @ApiOperation(value = " banner类内容-调整表流程中止", notes = " banner类内容-调整表流程中止")
    @GetMapping("/procStop")
    public Result procStop(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        log.debug(" banner类内容-调整表流程中止");
        return portalContentAdjBannersService.procStop(camundaProcinsId);
    }

    /**
     * banner类内容-调整表审批完成
     *
     * @param camundaProcinsId 流程实例id
     * @return Result
     */
    @ApiOperation(value = " banner类内容-调整表审批完成", notes = " banner类内容-调整表审批完成")
    @GetMapping("/auditSucceed")
    public Result auditSucceed(@RequestParam("camundaProcinsId") String camundaProcinsId) {
        log.debug(" banner类内容-调整表审批完成");
        return portalContentAdjBannersService.auditSucceed(camundaProcinsId);
    }
}
