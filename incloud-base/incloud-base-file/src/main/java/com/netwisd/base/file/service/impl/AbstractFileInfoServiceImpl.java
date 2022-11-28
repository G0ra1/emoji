package com.netwisd.base.file.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.file.constants.FileStoreTypeEnum;
import com.netwisd.base.file.dto.FileInfoDto;
import com.netwisd.base.file.entity.FileDs;
import com.netwisd.base.file.entity.FileInfo;
import com.netwisd.base.file.mapper.FileInfoMapper;
import com.netwisd.base.file.service.FileInfoService;
import com.netwisd.base.file.util.FileUtil;
import com.netwisd.base.file.vo.FileInfoVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.core.util.SpringContextUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Description: 抽象文件存储实现方法，用于处理各种文件操作的公共逻辑，不能注解或注入bean，因为是不是具体实现bean
 * current package com.netwisd.base.file.service.impl
 * @Author: zouliming
 * @Date: 2020/2/9 12:47 下午
 */
@Slf4j
public abstract class AbstractFileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

    protected Mapper dozerMapper = SpringContextUtils.getBean(Mapper.class);

    protected abstract FileStoreTypeEnum fileStoreType();

    /**
     * 台账
     *
     * @return
     */
    public Page<FileInfoVo> list(FileInfoDto fileInfoDto) {
        LambdaQueryWrapper<FileInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(fileInfoDto.getFileIsImg()), FileInfo::getFileIsImg, fileInfoDto.getFileIsImg()).like(ObjectUtils.isNotEmpty(fileInfoDto.getFileName()), FileInfo::getFileName, fileInfoDto.getFileName());
        Page<FileInfo> result = page(fileInfoDto.getPage(), queryWrapper);
        Page<FileInfoVo> pageVo = DozerUtils.mapPage(dozerMapper, result, FileInfoVo.class);
        log.debug("查询条数:" + pageVo.getTotal());
        return pageVo;
    }

    /**
     * 上传文件抽象方法，由具体类实现
     *
     * @param file
     * @param fileInfo
     */
    protected abstract void uploadFile(MultipartFile file, FileInfo fileInfo);

    /**
     * 如果系统不能访问Minio所对应的端口的情况下，就不要调用此方法，直接通过ID下载流
     * @param fileInfoVo
     */
    //protected abstract void setUrl4FileInfoVo(FileInfoVo fileInfoVo);

    /**
     * 通过流的方式下载附件
     *
     * @param fileInfo
     * @return
     */
    public abstract Map<String, InputStream> getStream(FileInfo fileInfo);

    /**
     * 通过流的方式下载附件
     *
     * @param id
     * @return
     */
    public Map<String, InputStream> getStream(Long id) {
        FileInfo fileInfo = this.getById(id);
        if (fileInfo == null) {
            throw new IncloudException("查不到此附件！");
        }
        return getStream(fileInfo);
    }

    /**
     * 打包流下载，暂不实现
     *
     * @param fileInfos
     * @return
     */
    public abstract InputStream getObjectsByList(Collection<FileInfo> fileInfos);

    /**
     * 根据文件的字节大小计算显示大小
     *
     * @param size
     * @return
     */
    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + units[digitGroups];
    }

    @Override
    public InputStream getObjectsByIds(Collection<Long> idList) {
        List<FileInfo> list = this.listByIds(idList);
        if (list == null || list.size() == 0) {
            throw new IncloudException("查不到相应附件！");
        }
        return getObjectsByList(list);
    }

    /**
     * 批量上传附件
     *
     * @param fileSource
     * @param files
     * @return
     * @see com.netwisd.base.common.constants.FileSource
     */
    @Transactional
    public List<FileInfoVo> uploads(String poolName, String fileSource, MultipartFile... files) {
        if (files.length > 10) {
            throw new IncloudException("单次上传附件最多10个!");
        }
        List fileInfoList = new ArrayList();
        try {
            Arrays.asList(files).forEach(file -> {
                FileInfo fileInfo = null;
                try {
                    fileInfo = FileUtil.getFileInfo(file);
                } catch (Exception e) {
                    throw new IncloudException("文件获取失败");
                }
                if (!fileInfo.getFileName().contains(".")) {
                    throw new IncloudException("缺少后缀名");
                }
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                if (loginAppUser != null) {
//                    fileInfo.setCreateUserId(loginAppUser.getUserId());
                    fileInfo.setCreateUserName(loginAppUser.getUsername());
                } else {
                    fileInfo.setCreateUserId(0l);
                    fileInfo.setCreateUserName("admin");
                }
                FileInfoVo fileInfoVo = new FileInfoVo();
                fileInfo.setFileSource(fileSource);
                fileInfo.setFileStoreType(fileStoreType().name());
                /**
                 * todo 目前这种方式是有隐患的，首先minio并不支持批量操作；
                 * todo 另外，批量操作时，minio和数据库并不能做到事务统一，会出现异常后部分数据minio成功，数据库整体回滚的情况；
                 * todo 下一步改造是做minio异常处理中对部分成功数据做筛选，然后数据库做部分数据同步插入
                 */
                uploadFile(file, fileInfo);
                fileInfoList.add(fileInfo);
                //处理vo，也可以在子类中覆盖重写
                fileInfoVo = initFileInfoVo(fileInfoVo, fileInfo);
                fileInfoList.add(fileInfoVo);
            });
            //批量保存文件信息到数据库
            this.saveBatch(fileInfoList);
        } catch (Exception e) {
            throw new IncloudException("文件上传失败!");
        }


        return fileInfoList;
    }

    /**
     * 文件上传
     *
     * @param file
     * @param fileSource
     * @return
     * @see com.netwisd.base.common.constants.FileSource
     */
    @Override
    @SneakyThrows
    @Transactional
    public FileInfoVo upload(MultipartFile file, String poolName, String fileSource) {
        //String md5 = fileMd5(file.getInputStream());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        FileInfoVo fileInfoVo = new FileInfoVo();
        FileInfo fileInfo = FileUtil.getFileInfo(file);
        fileInfo.setFileSource(fileSource);
        // 设置文件存储方式
        fileInfo.setFileStoreType(fileStoreType().name());
        fileInfo.setFilePoolName(poolName);
        /*QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper -> wrapper.eq("file_name",fileInfo.getFileName()).eq("file_md5_code",fileInfo.getFileMd5Code()));
        //QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>().and(wrapper -> wrapper.eq("file_name",fileInfo.getFileName()).eq("file_md5_code",fileInfo.getFileMd5Code()));
        // 根据文件名称和md5值查询记录
        FileInfo oldFileInfo = this.getOne(queryWrapper);
        if (oldFileInfo != null) {// 如果已存在文件，避免重复上传同一个文件
            fileInfoVo = initFileInfoVo(fileInfoVo,oldFileInfo);
            return fileInfoVo;
        }*/
        if (!fileInfo.getFileName().contains(".")) {
            throw new IncloudException("缺少后缀名");
        }
        if (loginAppUser != null) {
//            fileInfo.setCreateUserId(loginAppUser.getUserId());
            fileInfo.setCreateUserName(loginAppUser.getUsername());
        } else {
            fileInfo.setCreateUserId(-0l);
            fileInfo.setCreateUserName("admin");
        }
        String fileSizeView = readableFileSize(fileInfo.getFileSize());
        fileInfo.setFileSizeView(fileSizeView);
        uploadFile(file, fileInfo);
        this.save(fileInfo);// 将文件信息保存到数据库
        log.info("上传文件：{}", fileInfo);
        fileInfoVo = initFileInfoVo(fileInfoVo, fileInfo);
        return fileInfoVo;
    }

    /**
     * 文件删除
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Boolean removeById(Long id) {
        FileInfo fileInfo = this.getById(id);
        if (ObjectUtil.isNotEmpty(fileInfo)) {
            Boolean result = super.removeById(id);
            checkRelationShip(fileInfo);
            log.info("删除文件成功：{}", id);
            return result;
        }
        throw new IncloudException("删除文件不存在");
        //return false;
    }

    @Async
    protected void checkRelationShip(FileInfo fileInfo) {
        try {
            String fileMd5Code = fileInfo.getFileMd5Code();
            QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.and(wrapper -> wrapper.eq("file_md5_code", fileInfo.getFileMd5Code())).eq("create_user_id", fileInfo.getCreateUserId()).eq("create_user_name", fileInfo.getCreateUserName());
            int count = this.count(queryWrapper);
            //说明除了当前被删除的记录外，没有别的文件在引用这个文档，代表可以删了；
            if (count == 0) {
                deleteFile(fileInfo);
                log.info("真实文件删除成功！");
            }
        } catch (Exception e) {
            log.error("真实文件删除失败！");
        }
    }

    /**
     * 删除文件资源
     *
     * @param fileInfo
     * @return
     */
    protected abstract boolean deleteFile(FileInfo fileInfo);

    /**
     * 文件copy，并且动态设置文件URL
     *
     * @param fileInfoVo
     * @param fileInfo
     */
    public FileInfoVo initFileInfoVo(FileInfoVo fileInfoVo, FileInfo fileInfo) {
        if (fileInfo != null) fileInfoVo = dozerMapper.map(fileInfo, FileInfoVo.class);
        //不再使用动态url，使用流方式下载
        //setUrl4FileInfoVo(fileInfoVo);
        return fileInfoVo;
    }

    /**
     * 通过ID获取到文件
     *
     * @param id
     * @return
     */
    @Override
    public FileInfoVo getFileInfoById(Long id) {
        FileInfo fileInfo = this.getById(id);
        FileInfoVo fileInfoVo = null;
        if (fileInfo != null) {
            fileInfoVo = initFileInfoVo(fileInfoVo, fileInfo);
            //不再使用动态url，使用流方式下载
            //setUrl4FileInfoVo(fileInfoVo);
        }
        return fileInfoVo;
    }

    /**
     * 批量获取文件通过ID
     *
     * @param idList
     * @return
     */
    @Override
    public List<FileInfoVo> getFileInfoList(Collection<Long> idList) {
        List<FileInfo> list = this.listByIds(idList);
        List<FileInfoVo> listVo = new ArrayList<FileInfoVo>();
        list.forEach(fileInfo -> {
            FileInfoVo fileInfoVo = dozerMapper.map(fileInfo, FileInfoVo.class);
            if (fileInfo != null) {
                fileInfoVo = initFileInfoVo(fileInfoVo, fileInfo);
                //不再使用动态url，使用流方式下载
                //setUrl4FileInfoVo(fileInfoVo);
            }
            listVo.add(fileInfoVo);
        });
        return listVo;
    }

    public abstract void connect(FileDs fileDs);

    /**
     * 保存fileInfo信息
     *
     * @param infoDto
     * @return
     */
    @Transactional
    public Boolean saveFileInfo(FileInfoDto infoDto) {
        Optional.ofNullable(infoDto.getFileName()).orElseThrow(() -> new IncloudException("请输入文件名称"));
        Optional.ofNullable(infoDto.getFileSource()).orElseThrow(() -> new IncloudException("请输入文件业务来源"));
        Optional.ofNullable(infoDto.getFileStoreType()).orElseThrow(() -> new IncloudException("请输入文件存储方式"));
        Optional.ofNullable(infoDto.getFileMd5Code()).orElseThrow(() -> new IncloudException("请输入文件MD5值"));
        Optional.ofNullable(infoDto.getFileSize()).orElseThrow(() -> new IncloudException("请输入文件大小"));
        Optional.ofNullable(infoDto.getFileSize()).orElseThrow(() -> new IncloudException("请输入文件大小"));

        if (StringUtils.isNotBlank(infoDto.getFileContentType())) {
            infoDto.setFileIsImg(infoDto.getFileContentType().startsWith("image/") ? 0 : 1);
        }
        infoDto.setFileSizeView(readableFileSize(infoDto.getFileSize()));

        FileInfo info = dozerMapper.map(infoDto, FileInfo.class);
        info.setCreateTime(LocalDateTime.now());
        info.setUpdateTime(LocalDateTime.now());

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

        if (loginAppUser != null) {
            info.setCreateUserId(loginAppUser.getId());
            info.setCreateUserName(loginAppUser.getUsername());
        } else {
            info.setCreateUserId(-0l);
            info.setCreateUserName("admin");
        }
        return super.save(info);
    }

    /**
     * 删除fileInfo信息
     *
     * @param id
     * @return
     */
    @Transactional
    public Boolean deleteFileInfo(Long id) {
        Optional.ofNullable(id).orElseThrow(() -> new IncloudException("请输入ID"));
        return super.removeById(id);
    }
}
