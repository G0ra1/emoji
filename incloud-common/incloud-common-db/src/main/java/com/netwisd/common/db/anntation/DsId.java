package com.netwisd.common.db.anntation;

import java.lang.annotation.*;

/**
 * @Description: 自定义数据源参数注解
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/22 4:34 下午
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DsId {
    String value() default "incloud3";
}
