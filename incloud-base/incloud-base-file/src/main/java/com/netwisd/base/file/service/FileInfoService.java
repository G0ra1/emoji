package com.netwisd.base.file.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.file.dto.FileInfoDto;
import com.netwisd.base.file.entity.FileInfo;
import com.netwisd.base.file.vo.FileInfoVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author 云数网讯 zouliming@netwisd.com
 * @Description 文件存储 功能描述...
 * @date 2020-02-09 12:27:14
 */
public interface FileInfoService extends IService<FileInfo> {
    /**
     * 通过ID批量获取附件信息，用于展示附件及通过URL下载方式
     *
     * @param idList
     * @return
     */
    List<FileInfoVo> getFileInfoList(Collection<Long> idList);

    FileInfoVo getFileInfoById(Long id);

    /**
     * 台账
     *
     * @param fileInfoDto
     * @return
     */
    Page<FileInfoVo> list(FileInfoDto fileInfoDto);

    /**
     * 上传附件
     *
     * @param file
     * @param fileSource
     * @return
     * @see com.netwisd.base.common.constants.FileSource
     */
    FileInfoVo upload(MultipartFile file, String poolName, String fileSource);

    /**
     * 批量上传附件
     *
     * @param fileSource
     * @param file
     * @return
     * @see com.netwisd.base.common.constants.FileSource
     */
    List<FileInfoVo> uploads(String poolName, String fileSource, MultipartFile... file);

    /**
     * 批量上传，暂不实现
     *
     * @param entityList
     * @return
     */
    Collection<FileInfoVo> uploadBatch(Collection<MultipartFile> entityList);

    Boolean removeById(Long id);

    /**
     * 通过流的方式下载附件
     *
     * @param id
     * @return
     */
    Map<String, InputStream> getStream(Long id);

    /**
     * 批量打包下载流，暂不实现，需要时再说
     *
     * @param idList
     * @return
     */
    InputStream getObjectsByIds(Collection<Long> idList);

    /**
     * 保存fileInfo信息
     *
     * @param infoDto
     * @return
     */
    Boolean saveFileInfo(FileInfoDto infoDto);

    /**
     * 删除fileInfo信息
     *
     * @param id
     * @return
     */
    Boolean deleteFileInfo(Long id);

}
