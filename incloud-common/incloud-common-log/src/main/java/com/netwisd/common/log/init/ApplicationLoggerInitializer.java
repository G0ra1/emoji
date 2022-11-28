package com.netwisd.common.log.init;

import cn.hutool.core.util.StrUtil;
import com.netwisd.common.log.constant.ApplicationProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

public class ApplicationLoggerInitializer implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        //是因为会加载多次，第一次加载bootstrap.yml,对于的发送MQ配置文件,必须配置在bootstrap.yml文件中;
        ApplicationProperty instance = ApplicationProperty.getInstance();
        if (StrUtil.isEmpty(instance.getAppName())) {
            instance.setAppName(environment.getProperty("spring.application.name", StrUtil.EMPTY));
        }
        if (StrUtil.isEmpty(instance.getRocketMqUrl())) {
            //从配置文件读取rocketMqUrl
            instance.setRocketMqUrl(environment.getProperty("spring.rocketmq.namesrvAddr", StrUtil.EMPTY));
        }
        if (StrUtil.isEmpty(instance.getApplicationLogIsSendRocket())) {
            //从配置文件读取是否发送到MQ
            instance.setApplicationLogIsSendRocket(environment.getProperty("spring.rocketmq.isSend", StrUtil.EMPTY));
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
