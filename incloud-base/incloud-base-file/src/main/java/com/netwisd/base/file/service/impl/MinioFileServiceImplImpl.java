package com.netwisd.base.file.service.impl;

import cn.hutool.core.util.StrUtil;
import com.netwisd.base.file.constants.FileStoreTypeEnum;
import com.netwisd.base.file.entity.FileDs;
import com.netwisd.base.file.entity.FileInfo;
import com.netwisd.base.file.vo.FileInfoVo;
import com.netwisd.base.file.vo.MinioItemVo;
import com.netwisd.base.file.vo.MinioObjectVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.SpringContextUtils;
import io.minio.MinioClient;
import io.minio.messages.Item;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.*;

/**
 * @Description: minio实现类
 * current package com.netwisd.base.file.service.impl
 * @Author: zouliming
 * @Date: 2020/2/9 2:45 下午
 */
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class MinioFileServiceImplImpl extends AbstractFileInfoServiceImpl {

    private final static String minioPoolNamePrefix = "minio";

    private MinioClient minioClient;

    private String bucketName;

    /**
     * 动态创建bean的时候，用来使用此构造方法实现动态传值给其他依赖的bean
     * @param poolName
     * @param bucketName
     */
    public MinioFileServiceImplImpl(String poolName, String bucketName){
        minioClient = (MinioClient) SpringContextUtils.getBean(minioPoolNamePrefix+StrUtil.upperFirst(poolName));
        this.bucketName = bucketName;
        log.info("minioClient 初始化完成");
    }

    @Override
    protected FileStoreTypeEnum fileStoreType() {
        return FileStoreTypeEnum.MINIO;
    }

    @Override
    @SneakyThrows
    protected void uploadFile(MultipartFile file, FileInfo fileInfo) {
        fileInfo.setFileBucketName(bucketName);
        createBucket(fileInfo.getFileBucketName());
        //minioClient.putObject(fileInfo.getFileBucketName(), fileInfo.getFileName(), file.getInputStream(), fileInfo.getFileSize(), null, null, fileInfo.getFileContentType());
        /**
         * 为了使实际文档不重复存储，浪费存储空间，同一个文件（MD5相同）只在minio上存在一份；
         * 名称不同，或同一个文件多个个上传等信息是多份的，存储在file表中，相当于引用是多份，对象只有一份；
         * folder形式的存储，这绝对是Minio官方隐藏的彩蛋，因为我找了官方sdk文档都没找到这个用法，至少当前这个版本没有；
         * 只保证同一个用户下md5唯一
         */
        String objectName = fileInfo.getCreateUserName() + "/" + fileInfo.getFileMd5Code();
        minioClient.putObject(fileInfo.getFileBucketName(), objectName, file.getInputStream(), fileInfo.getFileSize(), null, null, fileInfo.getFileContentType());
        //String url = getObjectURL(fileInfo.getBucketName(), fileInfo.getName(),  3600*24*7);
        //fileInfo.setUrl(url);
    }

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public void createBucket(String bucketName) {
        if (!minioClient.bucketExists(bucketName)) {
            minioClient.makeBucket(bucketName);
        }
    }

    @Override
    @SneakyThrows
    protected boolean deleteFile(FileInfo fileInfo) {
        // folder形式的存储,只保证同一个用户下md5唯一
        String objectName = fileInfo.getCreateUserName() + "/" + fileInfo.getFileMd5Code();
        minioClient.removeObject(fileInfo.getFileBucketName(), objectName);
        return true;
    }

    @Override
    public FileInfoVo getFileInfoById(Long id) {
        return super.getFileInfoById(id);
    }

    @Override
    /**
     * 暂不实现
     */
    public Collection<FileInfoVo> uploadBatch(Collection<MultipartFile> entityList) {
        return null;
    }


    @Override
    public Map<String,InputStream> getStream(FileInfo fileInfo) {
        String bucketName = fileInfo.getFileBucketName();
        String fileName = fileInfo.getFileName();
        String fileMd5Code = fileInfo.getFileMd5Code();
        String objectName = fileInfo.getCreateUserName() + "/" + fileMd5Code;
        InputStream inputStream = null;
        Map<String,InputStream> map = new HashMap<String,InputStream>();
        if(StrUtil.isNotEmpty(bucketName) && StrUtil.isNotEmpty(fileMd5Code)){
            try {
                inputStream = minioClient.getObject(bucketName,objectName);
                map.put(fileName,inputStream);
                return map;
            }catch (Exception e){
                throw new IncloudException("查询失败！");
            }finally {
                /*try {
                    if (inputStream != null){
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        }
        new IncloudException("参数为空！");
        return null;
    }

    @Override
    public InputStream getObjectsByList(Collection<FileInfo> fileInfos) {
        //暂不实现
        return null;
    }

    public List<FileInfoVo> getFileInfoList(Collection<Long> idList){
        return super.getFileInfoList(idList);
    }

    @Override
    public void connect(FileDs fileDs) {
        try {
            MinioClient minioClient = new MinioClient(fileDs.getMinioUrl(),fileDs.getMinioAccessKey(),fileDs.getMinioSecretKey());
            minioClient.listBuckets();
        }catch (Exception e){
            log.error("minio连接错误，请检查配置"+e);
            throw new IncloudException("minio连接错误，请检查配置");
        }
    }

    /*@Override
    protected void setUrl4FileInfoVo(FileInfoVo fileInfoVo) {
        String url = getObjectURL(fileInfoVo.getFileBucketName(), fileInfoVo.getFileMd5Code(),  3600*24*1);
        fileInfoVo.setFileUrl(url);
    }*/

    /**
     * 根据文件前置查询文件
     *
     * @param bucketName bucket名称
     * @param prefix     前缀
     * @param recursive  是否递归查询
     * @return MinioItem 列表
     */
    @SneakyThrows
    public List<MinioItemVo> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
        List<MinioItemVo> objectList = new ArrayList<>();
        Iterable<io.minio.Result<Item>> objectsIterator = minioClient
                .listObjects(bucketName, prefix, recursive);

        while (objectsIterator.iterator().hasNext()) {
            objectList.add(new MinioItemVo(objectsIterator.iterator().next().get()));
        }
        return objectList;
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        return minioClient.presignedGetObject(bucketName, objectName, expires);
    }

    public MinioObjectVo getObjectInfo(String bucketName, String objectName) throws Exception {
        return new MinioObjectVo(minioClient.statObject(bucketName, objectName));
    }
}
