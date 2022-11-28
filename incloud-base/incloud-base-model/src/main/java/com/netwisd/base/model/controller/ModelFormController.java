package com.netwisd.base.model.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.netwisd.base.model.dto.ModelFormDto;
import com.netwisd.base.model.service.ModelFormService;
import com.netwisd.base.common.model.vo.ModelFormVo;
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

@Api(value = "ModelForm", tags = "建模表单Controller")
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/form")
public class ModelFormController {

    private ModelFormService modelFormService;

    @SysLog(value = "分页查询")
    @PostMapping("/page")
    @ApiOperation(value = "分页查询")
    public Result<IPage> queryModelFormPage(@RequestBody ModelFormDto modelFormDto) {
        return Result.success(modelFormService.queryModelFormPage(modelFormDto));
    }

    @SysLog(value = "表单新增", operateType = CommonConstant.OPERATE_TYPE_ADD)
    @PostMapping()
    @ApiOperation(value = "表单新增")
    public Result saveModelForm(@Validation @RequestBody ModelFormDto modelFormDto) {
        return Result.success(modelFormService.saveModelForm(modelFormDto));
    }

    @SysLog(value = "表单编辑", operateType = CommonConstant.OPERATE_TYPE_EDIT)
    @PutMapping()
    @ApiOperation(value = "表单编辑")
    public Result upAndExecSql(@Validation @RequestBody ModelFormDto modelFormDto) {
        return Result.success(modelFormService.upModelForm(modelFormDto));
    }

    @SysLog(value = "表单删除", operateType = CommonConstant.OPERATE_TYPE_DEL)
    @DeleteMapping("/{id}")
    @ApiOperation(value = "表单删除")
    public Result upAndExecSql(@PathVariable("id") Long id) {
        return Result.success(modelFormService.delModelForm(id));
    }

    @SysLog(value = "绑定按钮")
    @PostMapping("/bind/button")
    @ApiOperation(value = "绑定按钮")
    public Result bindButton(@RequestBody ModelFormDto modelFormDto) {
        return Result.success(modelFormService.bindButton(modelFormDto));
    }

    @SysLog(value = "表单获取详情")
    @GetMapping("/detail/{id}")
    @ApiOperation(value = "表单获取详情")
    public Result<ModelFormVo> getFormDetail(@PathVariable(name = "id") Long id) {
        return Result.success(modelFormService.getModelFormDetail(id));
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable(name = "id") Long id, HttpServletResponse response) {
        try {
            byte[] outByte = modelFormService.download(id);
            response.reset();
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", "code"));
            //response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(outByte.length));
            response.setContentType("application/octet-stream; charset=UTF-8");
            OutputStream out = response.getOutputStream();
            out.write(outByte);
            out.flush();
        } catch (Exception e) {
            log.error("文件生成异常", e);
            throw new IncloudException("文件生成异常");
        }
    }


}