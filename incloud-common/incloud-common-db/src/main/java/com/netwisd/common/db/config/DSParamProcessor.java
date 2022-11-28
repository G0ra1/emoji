package com.netwisd.common.db.config;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.netwisd.common.db.anntation.DsId;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Parameter;

/**
 * @Description: 自定义ds处理器，用于处理DsId注解动态获取数据源的逻辑
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/22 4:24 下午
 */
@Slf4j
public class DSParamProcessor extends DsProcessor {
    private static final String PARAM_PREFIX = "#param";

    @Override
    public boolean matches(String key) {
        if (key.equalsIgnoreCase(PARAM_PREFIX)) {
            //DynamicDataSourceContextHolder.clear();
            return true;
        }
        return false;
    }

    @Override
    public String doDetermineDatasource(MethodInvocation methodInvocation, String key) {
        Parameter[] parameters = methodInvocation.getMethod().getParameters();
        Object[] arguments = methodInvocation.getArguments();
        String dsId = "";
        for (Parameter parameter : parameters) {
            DsId annotation = parameter.getAnnotation(DsId.class);
            if (ObjectUtil.isNotEmpty(annotation)) {
                int modifiers = parameter.getModifiers();
                dsId = String.valueOf(arguments[modifiers]);
                log.info("DsId is :{}", dsId);
                if (StrUtil.isEmpty(dsId)) {
                    //如果没传，使用注解自带的incloud3数据源
                    dsId = annotation.value();
                }
                break;
            }
        }
        return dsId;
    }
}
