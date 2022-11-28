package com.netwisd.base.file.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.base.file.constants.FileStoreTypeEnum;
import com.netwisd.base.file.entity.FileDs;
import com.netwisd.base.file.service.impl.LocalFileServiceImplImpl;
import com.netwisd.base.file.service.impl.MinioFileServiceImplImpl;
import com.netwisd.base.file.vo.FileResultVo;
import com.netwisd.common.core.exception.IncloudException;
import com.netwisd.common.core.util.SpringContextUtils;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description: current project incloud
 * current package com.netwisd.base.file.service
 * @Author: zouliming
 * @Date: 2020/2/9 12:37 下午
 */
@Configuration
public class FileInfoServiceFactory {
    private final static String defaultStoreType = "default";
    private final static String minioPoolNamePrefix = "minio";
    private final static String localPoolNamePrefix = "local";

    private Map<String, FileInfoService> map = new HashMap<>();

    @Autowired
    FileDsService fileDsService;

    /**
     * 这几个是默认的实现Bean，其他的由数据添加的数据源，会通过通过创建bean的形式加载到当前map中
     */
    @PostConstruct
    public void init() {

    }

    /**
     * 动态测试连接
     * @param fileDs
     */
    public void dynamicConnect(FileDs fileDs){
        dynamicHandler(fileDs,true);
    }

    /**
     * 动态添加数据源
     * @param fileDs
     */
    @SneakyThrows
    public void dynamicAddDs(FileDs fileDs){
        dynamicHandler(fileDs,false);
    }

    /**
     * 动态处理的实现
     * @param fileDs
     * @param isConnect
     */
    @SneakyThrows
    public void dynamicHandler(FileDs fileDs,Boolean isConnect){
        FileStoreTypeEnum fileStoreTypeEnum = FileStoreTypeEnum.valueOf(fileDs.getType());
        String poolName = fileDs.getPoolName();
        switch (fileStoreTypeEnum){
            case MINIO:
                if (isConnect) {
                    dynamicConnectMinio(fileDs);
                } else {
                    dynamicRegisterMinioBean(fileDs);
                }
                break;
            case LOCAL:
                if (isConnect) {
                    dynamicConnectLocal(fileDs);
                } else {
                    map.put(poolName,SpringContextUtils.registerBean(poolName,
                            LocalFileServiceImplImpl.class,fileDs.getLocalFilePrefix(),fileDs.getLocalFilePath()));
                }
                break;
            default:
                throw new IncloudException("数据存储类型有误，请检查配置！");
        }
    }

    /**
     * Minio的连接测试实现
     * @param fileDs
     */
    void dynamicConnectMinio(FileDs fileDs){
        new MinioFileServiceImplImpl().connect(fileDs);
    }

    /**
     * 本地的连接测试实现
     * @param fileDs
     */
    void dynamicConnectLocal(FileDs fileDs){
        new LocalFileServiceImplImpl().connect(fileDs);
    }

    /**
     * 动态创建MinioClient并注入到spring容器中
     * 动态创建MinioFileServiceImplImpl并注入到spring容器中
     * 把MinioFileServiceImplImpl对应的beanName放入到map对象中
     * @param fileDs
     */
    void dynamicRegisterMinioBean(FileDs fileDs){
        String poolName = fileDs.getPoolName();
        SpringContextUtils.registerBean(minioPoolNamePrefix+StrUtil.upperFirst(poolName),MinioClient.class,fileDs.getMinioUrl(),fileDs.getMinioAccessKey(),fileDs.getMinioSecretKey());
        map.put(poolName,SpringContextUtils.registerBean(poolName, MinioFileServiceImplImpl.class,poolName,fileDs.getMinioBucketName()));
    }

    /**
     * 删除spring容器中对应的bean，并删除map中的bean
     * @param poolName
     */
    public void removeDs(String poolName){
        SpringContextUtils.removeBean(poolName);
        map.remove(poolName);
    }

    /**
     * 根据文件源获取具体的实现类
     * @param poolName
     * @return
     */
    public FileResultVo getFileInfoService(String poolName) {
        if (StrUtil.isEmpty(poolName) || defaultStoreType.equalsIgnoreCase(poolName)) {
            //使用默认存储
            FileDs defaultDs = fileDsService.getDefaultDs();
            if(ObjectUtil.isEmpty(defaultDs)){
                throw new IncloudException("无默认文件数据源，请先设置默认数据源");
            }
            poolName = defaultDs.getPoolName();
        }
        FileInfoService fileInfoService = map.get(poolName);
        if (fileInfoService == null) {
            throw new IncloudException("请检查数据源配置，看是否有" + poolName + "对应的实现类");
        }
        return new FileResultVo(poolName,fileInfoService);
    }
}
