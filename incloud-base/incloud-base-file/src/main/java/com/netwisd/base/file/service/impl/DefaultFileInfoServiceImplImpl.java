package com.netwisd.base.file.service.impl;

import com.netwisd.base.file.constants.FileStoreTypeEnum;
import com.netwisd.base.file.entity.FileDs;
import com.netwisd.base.file.entity.FileInfo;
import com.netwisd.base.file.vo.FileInfoVo;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * @author zouliming@netwisd.com
 * @description 默认实现，一般用于查询具体表时候
 * @date 2021/12/27 15:07
 */
public class DefaultFileInfoServiceImplImpl extends AbstractFileInfoServiceImpl {

    @Override
    public Collection<FileInfoVo> uploadBatch(Collection<MultipartFile> entityList) {
        return null;
    }

    @Override
    protected FileStoreTypeEnum fileStoreType() {
        return FileStoreTypeEnum.DEFAULT;
    }

    @Override
    protected void uploadFile(MultipartFile file, FileInfo fileInfo) {

    }

    @Override
    public Map<String, InputStream> getStream(FileInfo fileInfo) {
        return null;
    }

    @Override
    public InputStream getObjectsByList(Collection<FileInfo> fileInfos) {
        return null;
    }

    @Override
    protected boolean deleteFile(FileInfo fileInfo) {
        return false;
    }

    @Override
    public void connect(FileDs fileDs) {
    }
}
