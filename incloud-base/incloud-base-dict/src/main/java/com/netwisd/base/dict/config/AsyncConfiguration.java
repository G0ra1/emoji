package com.netwisd.base.dict.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/12/18 4:20 下午
 */
@EnableAsync
@Configuration
@Slf4j
public class AsyncConfiguration {

    @Bean("incloudExecutor") // bean的名称，，不写的里面值，则默认为首字母小写的方法名
    public TaskExecutor taskExecutor() {
        log.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int core = Runtime.getRuntime().availableProcessors();
        //配置核心线程数
        executor.setCorePoolSize(core * 2);
        //配置最大线程数
        executor.setMaxPoolSize(core * 10 + 1);
        // 允许线程空闲时间（单位：默认为秒）
        executor.setKeepAliveSeconds(10);
        //配置队列大小
        executor.setQueueCapacity(9999);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-incloud-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }
}
