package com.netwisd.common.es.config;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.netwisd.common.core.util.IncloudClassHandler;
import com.netwisd.common.core.util.SpringContextUtils;
import com.netwisd.common.es.index.Index2Table;
import com.netwisd.common.es.jobs.UpdateIndexScheduling;
import com.netwisd.common.es.service.ElasticSearchService;
import com.netwisd.common.es.util.IndexUtil;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

/**
 * @Description: 索引创始化
 * @Author: zouliming@netwisd.com
 * @Date: 2020/9/15 2:22 下午
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "incloud.index")
@Data
@EnableScheduling
public class IndexInitConfig<T extends Index2Table> implements IncloudClassHandler<T>, ApplicationRunner {
    private String update;

    @Autowired
    ElasticSearchService elasticSearchService;

    private static String prefix = Index2Table.database_prefix;

    //@Bean
    public UpdateIndexScheduling updateIndexScheduling() {
        if (StrUtil.isNotEmpty(update) && BooleanUtil.toBoolean(update)) {
            return new UpdateIndexScheduling();
        }
        return null;
    }

    @SneakyThrows
    @Override
    public void run(ApplicationArguments args) {
        T t = null;
        Class<T> clazz = getTClass();
        if (clazz == null) {
            return;
        }
        try {
            t = SpringContextUtils.getBean(clazz);
        } catch (Exception e) {
            logger.warn("通过bean类型：" + clazz + " 找不到相应的bean,将通过反射方法来创建对象！");
            t = clazz.getDeclaredConstructor().newInstance();
        }
        prefix += t.getModulePrefix();
        List<String> indexList = IndexUtil.getIndex(null, t.getMap());
        indexList.forEach(index -> {
            String jsonFile = StrUtil.subAfter(index, prefix, true);
            String idxEQL = ResourceUtil.readUtf8Str("index/" + jsonFile + ".json");
            try {
                if (!elasticSearchService.isExistsIndex(index)) {
                    log.info("初始化index的idxEQL为：{}", idxEQL);
                    elasticSearchService.createIndex(index, idxEQL);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
