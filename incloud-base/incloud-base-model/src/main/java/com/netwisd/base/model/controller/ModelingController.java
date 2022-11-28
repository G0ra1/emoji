package com.netwisd.base.model.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.netwisd.base.model.dto.ModelingDto;
import com.netwisd.base.model.service.ModelingService;
import com.netwisd.base.model.vo.ModelingVo;
import com.netwisd.common.code.service.GeneratorService;
import com.netwisd.common.core.anntation.Validation;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.Result;
import com.netwisd.common.log.annotation.SysLog;
import com.netwisd.common.log.constant.CommonConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Api(value = "Modeling", tags = "建模Controller")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/modeling")
public class ModelingController {

    private final ModelingService modelingService;

    private final GeneratorService generatorService;

    @SysLog(value = "实体树形")
    @GetMapping("/entityTree/{entityId}")
    @ApiOperation(value = "实体树形")
    public Result getEntityTree(@PathVariable(value = "entityId") Long entityId,
                                @RequestParam(name = "modelingId", required = false) Long modelingId,
                                @RequestParam(name = "modelProperty", required = false, defaultValue = "0") Integer modelProperty) {
        return Result.success(modelingService.getEntityTree(entityId, modelingId, modelProperty));
    }

    @SysLog(value = "建模分页查询")
    @PostMapping("/page")
    @ApiOperation(value = "分页查询")
    public Result<IPage> queryModelingPage(@RequestBody ModelingDto modelingDto) {
        return Result.success(modelingService.queryModelingPage(modelingDto));
    }

    @SysLog(value = "建模列表查询")
    @GetMapping("/list")
    @ApiOperation(value = "建模列表查询")
    public Result queryModelingList(@RequestParam(value = "modelTypeId", required = false) Long modelTypeId,
                                    @RequestParam(value = "modelName", required = false) String modelName,
                                    @RequestParam(value = "modelNameCh", required = false) String modelNameCh,
                                    @RequestParam(value = "entityNameCh", required = false) String entityNameCh,
                                    @RequestParam(value = "modelProperty", required = false) Integer modelProperty) {
        ModelingDto modelingDto = new ModelingDto();
        modelingDto.setModelTypeId(modelTypeId);
        modelingDto.setModelName(modelName);
        modelingDto.setModelNameCh(modelNameCh);
        modelingDto.setEntityName(entityNameCh);
        modelingDto.setModelProperty(modelProperty);
        return Result.success(modelingService.queryModelingList(modelingDto));
    }

    @SysLog(value = "建模新增", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @PostMapping()
    @ApiOperation(value = "建模新增")
    public Result saveModeling(@Validation @RequestBody ModelingDto modelingDto) {
        return Result.success(modelingService.saveModeling(modelingDto));
    }

    @SysLog(value = "建模编辑", operateType = CommonConstant.OPERATE_TYPE_EDIT)
    @PutMapping()
    @ApiOperation(value = "建模编辑")
    public Result upModeling(@Validation @RequestBody ModelingDto modelingDto) {
        return Result.success(modelingService.upModeling(modelingDto));
    }

    @SysLog(value = "建模删除", operateType = CommonConstant.OPERATE_TYPE_DEL)
    @DeleteMapping("/{id}")
    @ApiOperation(value = "建模删除")
    public Result delModeling(@PathVariable("id") Long id) {
        return Result.success(modelingService.delModeling(id));
    }

    @SysLog(value = "表单建模详情")
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "表单获取详情")
    public Result<ModelingVo> getModelingDetail(@PathVariable(name = "id") Long id) {
        return Result.success(modelingService.getModelingDetail(id));
    }

    @SysLog(value = "建模字段列表")
    @GetMapping("/filds")
    @ApiOperation(value = "建模字段列表")
    public Result fieldLists(@RequestParam(name = "modelingId") Long modelingId) {
        return Result.success(modelingService.fieldLists(modelingId));
    }

    @SysLog(value = "代码下载")
    @RequestMapping(value = "/downloadCode", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation(value = "代码下载")
    public void downloadCode(HttpServletResponse response, @RequestParam(name = "modelingId") Long modelingId,
                             @RequestParam(name = "codeType", defaultValue = "df") String codeType) {
        try {
            byte[] data = generatorService.generatorCode(modelingService.getModelConfig(modelingId, codeType));
            response.reset();
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", "generator"));
            //response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
            response.setContentType("application/octet-stream; charset=UTF-8");
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
        } catch (Exception e) {
            log.error("文件生成异常", e);
            throw new IncloudException("文件生成异常");
        }
    }
}