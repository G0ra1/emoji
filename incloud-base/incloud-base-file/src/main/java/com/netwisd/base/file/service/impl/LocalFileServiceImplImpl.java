package com.netwisd.base.file.service.impl;

import com.netwisd.base.file.constants.FileStoreTypeEnum;
import com.netwisd.base.file.entity.FileDs;
import com.netwisd.base.file.entity.FileInfo;
import com.netwisd.base.file.util.FileUtil;
import com.netwisd.base.file.vo.FileInfoVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.MultimediaObject;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 本地存储文件<br>
 * 该实现文件服务只能部署一台<br>
 * 如多台机器间能共享到一个目录，即可部署多台
 *
 * @author 云数网讯 zouliming@netwisd.com
 */
@NoArgsConstructor
@Slf4j
public class LocalFileServiceImplImpl extends AbstractFileInfoServiceImpl {

    @Override
    protected FileStoreTypeEnum fileStoreType() {
        return FileStoreTypeEnum.LOCAL;
    }

    private String urlPrefix;
    /**
     * 上传文件存储在本地的根路径
     */
    private String localFilePath;

    public LocalFileServiceImplImpl(String urlPrefix, String localFilePath) {
        this.urlPrefix = urlPrefix;
        this.localFilePath = localFilePath;
    }

    @Override
    @SneakyThrows
    protected void uploadFile(MultipartFile file, FileInfo fileInfo) {
        int index = fileInfo.getFileName().lastIndexOf(".");
        // 文件扩展名
        String fileSuffix = fileInfo.getFileName().substring(index);
        String suffix = "/" + LocalDate.now().toString().replace("-", "/") + "/" + fileInfo.getFileMd5Code() + fileSuffix;
        String path = localFilePath + suffix;
        String url = urlPrefix + suffix;
        fileInfo.setFilePath(path);
        fileInfo.setFileUrl(url);
        FileUtil.saveFile(file, path);
        //设置媒体文件时长毫秒
        try {
            if (file.getContentType().startsWith("audio/") || file.getContentType().startsWith("video/")) {
                MultimediaObject multimediaObject = new MultimediaObject(new File(path));
                fileInfo.setFileDuration(multimediaObject.getInfo().getDuration());
            }
        } catch (Exception e) {
            //获取时长过程发生错误
            log.error("获取媒体文件时长出现问题");
        }
    }

    @Override
    protected boolean deleteFile(FileInfo fileInfo) {
        return FileUtil.deleteFile(fileInfo.getFilePath());
    }

    @Override
    public FileInfoVo getFileInfoById(Long id) {
        return super.getFileInfoById(id);
    }

    @Override
    public Collection<FileInfoVo> uploadBatch(Collection<MultipartFile> entityList) {
        return null;
    }

    @Override
    public List<FileInfoVo> getFileInfoList(Collection<Long> idList) {
        return super.getFileInfoList(idList);
    }

    @Override
    public void connect(FileDs fileDs) {
        try {
            boolean directory = cn.hutool.core.io.FileUtil.isDirectory(fileDs.getLocalFilePath());
            if (!directory) {
                throw new IncloudException("本地存储目录{}连接测试有误，请检查配置！", fileDs.getLocalFilePath());
            }
        } catch (Exception e) {
            log.error("本地存储目录连接测试有误，请检查配置！");
            throw new IncloudException("本地存储目录{}连接测试有误，请检查配置！", fileDs.getLocalFilePath());
        }
    }

    @Override
    public Map<String, InputStream> getStream(FileInfo fileInfo) {
        Map<String, InputStream> map = new HashMap<>();
        map.put(fileInfo.getFileName(), cn.hutool.core.io.FileUtil.getInputStream(fileInfo.getFilePath()));
        return map;
    }

    @Override
    public InputStream getObjectsByList(Collection<FileInfo> fileInfos) {
        return null;
    }

}
