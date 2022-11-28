package com.netwisd.base.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.file.entity.FileInfo;
import com.netwisd.base.file.vo.FileInfoVo;
import com.netwisd.base.file.vo.OssStsCredentialsVo;
import org.springframework.web.multipart.MultipartFile;

public interface AliyunFileService extends IService<FileInfo> {
    /**
     * 上传文件
     *
     * @param file
     * @param fileSource
     * @param fileDuration 媒体文件时长
     * @return
     */
    FileInfoVo uploadFile(MultipartFile file, String fileSource, Long fileDuration);

    /**
     * 根据ID获取文件详情
     *
     * @param id
     * @return
     */
    FileInfoVo detail(Long id);

    /**
     * 根据ID删除文件
     *
     * @param id
     */
    Boolean delete(Long id);

    OssStsCredentialsVo getOssStsAuthToken();
}
