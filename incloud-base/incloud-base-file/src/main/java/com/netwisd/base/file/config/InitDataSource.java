package com.netwisd.base.file.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.netwisd.base.file.constants.FileStoreTypeEnum;
import com.netwisd.base.file.entity.FileDs;
import com.netwisd.base.file.service.FileDsService;
import com.netwisd.base.file.service.FileInfoServiceFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import java.util.List;

/**
 * @Description: 流程启动时加载缓存
 * @Author: zouliming@netwisd.com
 * @Date: 2021/12/15 5:21 下午
 */

@Slf4j
@Configuration
@Data
public class InitDataSource implements ApplicationRunner {
    private static final String pri = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALrLWoXPU/Ex+gogTHQaWXDCPOzIBQUEUZMowguJW4deLiRfS69x31VxRSr8kxSD/cL8ifhlaQhuo3gS8XfltGTf6/6B2XXjKmrYLTaZaD86CGeG0bFSyDytZlWs0H0aNoK0pdDjrb4f8dO1wMpM6wt7qODvpygjZvBsflAW1fYnAgMBAAECgYAQtID6+Ii0SFjpMOxFcdnP2L8kGdtBEJrPA1UfPQB/ga+0twUIwrFLbd7WslHhAtDd8EHSghc7ltFtupv3sgZI26BXLe1EwiPr/vC9ZAi6IKQDDU2AEdYRzIDigfl8XJCGk3woRXNzqQcJafZw36XpT5k0pAMImYG/oCNTF2KrmQJBAP6otIqOP4DnjeJ6Pojswm3GCReyHUMFO8rS2SqZoGEEbKjl6CZJnogpNl+D85//WT7D1YzutZ8U1i8zTBn3DD0CQQC7xym4M9CB8S2j22af6ueIPuNVjBpyHqgxTHbshzdPf5wB7wFZEtUv+RQZT/oYSr4YnnMRqtYq8G505ePEAv4zAkBgER6CgmT8aN3CiSEcIEy8go+di8i0Jr5GpkHcazXwQ24GTSzFfNI8RWfIoot+WSK+pbvivY5wY7jk93IG/YZ1AkBGzW7epLLe/BhQa17Dt6f7iHLhg8VI+GREymchAk8Jq70gQYVJl79Iqms4rB5J4IzS7ZPHupmscSHE9BWwh8xfAkBEqfjRdOb8fryMD17xFQOj4OC3XxhutM7moBGKEUvmhYEmcK2A0sI/HZq6ZZd+hNNaXmmt7quSDhVvgZZ3jKXw";
    private static final String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6y1qFz1PxMfoKIEx0GllwwjzsyAUFBFGTKMILiVuHXi4kX0uvcd9VcUUq/JMUg/3C/In4ZWkIbqN4EvF35bRk3+v+gdl14ypq2C02mWg/OghnhtGxUsg8rWZVrNB9GjaCtKXQ462+H/HTtcDKTOsLe6jg76coI2bwbH5QFtX2JwIDAQAB";
    RSA rsa = new RSA(pri, null);
    @Autowired
    FileDsService fileDsService;

    @Autowired
    FileInfoServiceFactory fileInfoServiceFactory;

    /**
     * 系统启动时，初始化一下所有文件数据源，到spring容器和工厂map中
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) {
        List<FileDs> list = fileDsService.list();
        if(CollectionUtil.isNotEmpty(list)){
            handleSecret(list);
        }
    }

    /**
     * 处理下密码
     * @param list
     */
    void handleSecret(List<FileDs> list){
        list.forEach(fileDs -> {
            if(fileDs.getType().equals(FileStoreTypeEnum.MINIO.name())){
                byte[] decrypt = rsa.decrypt(fileDs.getMinioSecretKey(), KeyType.PrivateKey);
                fileDs.setMinioSecretKey(StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
            }
            fileInfoServiceFactory.dynamicAddDs(fileDs);
        });
    }
}
