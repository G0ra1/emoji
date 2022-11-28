package com.netwisd.common.db.anntation;

import java.lang.annotation.*;

/**
 * @Description: 表注解
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/18 1:14 下午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Map {
    /**
     * 表名
     *
     * @return
     */
    String value() default "";
}
