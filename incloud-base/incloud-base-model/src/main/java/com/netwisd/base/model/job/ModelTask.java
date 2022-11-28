package com.netwisd.base.model.job;

import com.netwisd.base.common.util.StringUtils;
import com.netwisd.base.model.mapper.ModelFormButtonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("task")
public class ModelTask {

    @Autowired
    private ModelFormButtonMapper modelFormButtonMapper;

    public void multipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        log.info(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void params(String params) throws InterruptedException {
        log.info("执行有参方法：" + params);
        Thread.sleep(5000L);
        if (params.equals("123")) {
            System.out.println(1 / 0);
            System.out.println(0 / 1);
        }
        log.info("执行完毕");
    }

    public void noParams() {
        log.info("执行无参方法");
    }
}
