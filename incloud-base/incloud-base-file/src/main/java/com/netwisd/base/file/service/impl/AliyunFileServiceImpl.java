package com.netwisd.base.file.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dozermapper.core.Mapper;
import com.netwisd.base.common.user.LoginAppUser;
import com.netwisd.base.common.util.AppUserUtil;
import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.file.entity.FileInfo;
import com.netwisd.base.file.mapper.FileInfoMapper;
import com.netwisd.base.file.service.AliyunFileService;
import com.netwisd.base.file.service.RedisService;
import com.netwisd.base.file.util.FileUtil;
import com.netwisd.base.file.vo.FileInfoVo;
import com.netwisd.base.file.vo.OssStsCredentialsVo;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AliyunFileServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements AliyunFileService {

    //sts 用于获取oss临时访问认证
    @Value("${file.aliyun.stsEndpoint}")
    private String stsEndpoint;

    //sts 角色arn 用于获取oss临时访问认证
    @Value("${file.aliyun.stsRoleArn}")
    private String stsRoleArn;

    //用于oss文件上传
    @Value("${file.aliyun.ossEndpoint}")
    private String ossEndpoint;

    @Value("${file.aliyun.accessKeyId}")
    private String accessKeyId;

    @Value("${file.aliyun.accessKeySecret}")
    private String accessKeySecret;

    @Value("${file.aliyun.bucketName}")
    private String bucketName;

    @Value("${file.aliyun.domain:}")
    private String domain;

    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private RedisService redisService;

    //sts 凭证有效时间秒
    private static final Long OSS_STS_CREDENTIALS_EXPIRE_SECONDS = 3600L;

    private static final String OSS_STS_CREDENTIALS_REDIS_KEY = "oss:sts:credentials";

    @Override
    @Transactional
    public FileInfoVo uploadFile(MultipartFile file, String fileSource, Long fileDuration) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        //String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        //String accessKeyId = "LTAI5tC5tWSnsdm8nS9MQ7hZ";
        //String accessKeySecret = "BmoIWarCxadUXECFjz6Ml1bKysBiq3";
        // 填写Bucket名称
        //String bucketName = "netwisd";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build("https://" + ossEndpoint, accessKeyId, accessKeySecret);

        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        FileInfo fileInfo = null;
        try {
            fileInfo = FileUtil.getFileInfo(file);
            fileInfo.setFileSource(fileSource);
            fileInfo.setFileStoreType("ALIYUN");
            fileInfo.setFileBucketName(bucketName);
            //为了防止数据库报错
            fileInfo.setFilePoolName("");
            if (loginAppUser != null) {
                fileInfo.setCreateUserId(loginAppUser.getId());
                fileInfo.setCreateUserName(loginAppUser.getUsername());
            } else {
                fileInfo.setCreateUserId(-0l);
                fileInfo.setCreateUserName("admin");
            }
            String fileSizeView = AbstractFileInfoServiceImpl.readableFileSize(fileInfo.getFileSize());
            fileInfo.setFileSizeView(fileSizeView);

            //设置媒体文件时长毫秒
            if (file.getContentType().startsWith("audio/") || file.getContentType().startsWith("video/")) {
                log.info("当前媒体,时长:{}毫秒", fileDuration);
                fileInfo.setFileDuration(Optional.ofNullable(fileDuration).orElseGet(() -> 0L));
            }
            // 文件扩展名
            String fileSuffix = fileInfo.getFileName().substring(fileInfo.getFileName().lastIndexOf("."));

            // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
            String objectName = LocalDate.now().toString().replace("-", "/") + "/" + fileInfo.getFileMd5Code() + fileSuffix;

            //设置文件保存的路径
            fileInfo.setFilePath(objectName);
            //设置文件访问的url
            if (StringUtils.isNotBlank(domain)) {
                fileInfo.setFileUrl("https://" + domain + "/" + objectName);
            } else {//设置默认
                fileInfo.setFileUrl("https://" + bucketName + "." + ossEndpoint + "/" + objectName);
            }

            //创建PutObject请求。
            ossClient.putObject(bucketName, objectName, file.getInputStream());
            super.save(fileInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return Optional.of(fileInfo).map(x -> dozerMapper.map(x, FileInfoVo.class)).orElseGet(() -> new FileInfoVo());
    }

    @Override
    public FileInfoVo detail(Long id) {
        return Optional.of(super.getById(id)).map(x -> dozerMapper.map(x, FileInfoVo.class)).orElseGet(() -> new FileInfoVo());
    }

    @Override
    @Transactional
    public Boolean delete(Long id) {
        FileInfo fileInfo = super.getById(id);
        if (fileInfo != null && "ALIYUN".equals(fileInfo.getFileStoreType())) {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build("https://" + ossEndpoint, accessKeyId, accessKeySecret);
            try {
                // 删除文件或目录。如果要删除目录，目录必须为空。
                ossClient.deleteObject(fileInfo.getFileBucketName(), fileInfo.getFilePath());
                super.removeById(id);
            } catch (Exception e) {
                log.error("删除bucket:{},文件:{},发生错误:{}", fileInfo.getFileBucketName(), fileInfo.getFilePath(), e.getMessage());
                e.printStackTrace();
                throw new IncloudException("删除文件过程发生错误");
            } finally {
                if (ossClient != null) {
                    ossClient.shutdown();
                }
            }
        }
        return true;
    }

    @Override
    public OssStsCredentialsVo getOssStsAuthToken() {
        //从缓存中获取
        if (redisService.hasKey(OSS_STS_CREDENTIALS_REDIS_KEY)) {
            log.info("从缓存中获取凭证");
            return redisService.getCacheObject(OSS_STS_CREDENTIALS_REDIS_KEY);
        }
        //String roleArn = "acs:ram::1881812733047647:role/ramosstest";
        // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest，此处使用当前用户名。
        String roleSessionName = Optional.ofNullable(AppUserUtil.getLoginAppUser().getUserName()).orElseGet(() -> "public");
        try {
            // regionId表示RAM的地域ID。以华东1（杭州）地域为例，regionID填写为cn-hangzhou。也可以保留默认值，默认值为空字符串（""）。
            String regionId = "";
            // 添加endpoint。适用于Java SDK 3.12.0及以上版本。
            DefaultProfile.addEndpoint(regionId, "Sts", stsEndpoint);
            // 构造default profile。
            IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            // 构造client。
            DefaultAcsClient client = new DefaultAcsClient(profile);
            AssumeRoleRequest request = new AssumeRoleRequest();
            // 适用于Java SDK 3.12.0及以上版本。
            request.setSysMethod(MethodType.POST);
            request.setRoleArn(stsRoleArn);
            request.setRoleSessionName(roleSessionName);
            //request.setPolicy(policy); // 如果policy为空，则用户将获得该角色下所有权限。
            request.setDurationSeconds(OSS_STS_CREDENTIALS_EXPIRE_SECONDS); // 设置临时访问凭证的有效时间为3600秒。
            AssumeRoleResponse response = client.getAcsResponse(request);
            log.info("Expiration: {}", response.getCredentials().getExpiration());
            log.info("Access Key Id: " + response.getCredentials().getAccessKeyId());
            log.info("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            log.info("Security Token: " + response.getCredentials().getSecurityToken());
            log.info("RequestId: " + response.getRequestId());
            OssStsCredentialsVo result = OssStsCredentialsVo.buildVo(response.getCredentials(), bucketName);
            //将结果放入缓存
            redisService.setCacheObject(OSS_STS_CREDENTIALS_REDIS_KEY, result, request.getDurationSeconds(), TimeUnit.SECONDS);
            return result;
        } catch (ClientException e) {
            log.error("Failed：");
            log.error("Error code: " + e.getErrCode());
            log.error("Error message: " + e.getErrMsg());
            log.error("RequestId: " + e.getRequestId());
            throw new IncloudException(e.getMessage());
        }
    }
}
