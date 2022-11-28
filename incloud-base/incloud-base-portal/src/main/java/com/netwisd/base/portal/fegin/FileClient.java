package com.netwisd.base.portal.fegin;

import com.netwisd.base.portal.dto.FileInfoDto;
import com.netwisd.base.portal.vo.FileInfoVo;
import com.netwisd.common.core.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/21 11:12 上午
 */
@FeignClient(value = "incloud-base-file")
public interface FileClient {

    @ApiOperation(value = "添加文件", notes = "添加文件")
    @GetMapping(value = "/fileinfo")
    public FileInfoVo upload(@RequestParam("file") MultipartFile file, @RequestParam("fileSource") String fileSource);


    @ApiOperation(value = "添加文件", notes = "添加文件")
    @PostMapping(value = "/fileinfo/uploadByBase64")
    Result<FileInfoVo> uploadByBase64(@RequestBody FileInfoDto fileInfoDto);


}
