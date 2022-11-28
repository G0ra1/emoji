package com.netwisd.base.file.controller;

import com.netwisd.base.file.service.AliyunFileService;
import com.netwisd.base.file.vo.FileInfoVo;
import com.netwisd.base.file.vo.OssStsCredentialsVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@AllArgsConstructor
@RequestMapping("/aliyunFile")
@Api(value = "aliyunFile", tags = "阿里云文件存储Controller")
@Slf4j
public class AliyunFileController {

    @Resource
    private AliyunFileService aliyunFileService;

    /**
     * 文件上传
     *
     * @param file
     * @param fileSource
     * @param fileDuration 媒体文件时长
     * @return
     */
    @ApiOperation(value = "文件上传", notes = "文件上传")
    @PostMapping
    public Result<FileInfoVo> upload(@RequestParam("file") MultipartFile file, @RequestParam("fileSource") String fileSource, @RequestParam(value = "fileDuration", required = false) Long fileDuration) {
        return Result.success(aliyunFileService.uploadFile(file, fileSource, fileDuration));
    }

    @ApiOperation(value = "获取文件详情", notes = "获取文件详情")
    @GetMapping("/{id}")
    public Result<FileInfoVo> getById(@PathVariable("id") Long id) {
        log.debug("查询成功");
        return Result.success(aliyunFileService.detail(id));
    }

    @ApiOperation(value = "删除文件", notes = "删除文件")
    @DeleteMapping("/{id}")
    public Result<Boolean> removeById(@PathVariable Long id) {
        return Result.success(aliyunFileService.delete(id));
    }

    @ApiOperation(value = "获取STS授权凭证信息", notes = "获取STS授权凭证信息")
    @GetMapping("/stsCredentials")
    public Result<OssStsCredentialsVo> getStsCredentials() {
        log.debug("获取STS授权凭证信息");
        return Result.success(aliyunFileService.getOssStsAuthToken());
    }
}
