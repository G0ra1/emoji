package com.netwisd.common.core.anntation;

import java.lang.annotation.*;

/**
 * @Description: 校验注解
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/23 7:28 下午
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Valid {
    /**
     * 是否不为空，可自定义提示不为空的内容
     * @return
     */
    String nullMsg() default "";

    /**
     * 长度限定
     * @return
     */
    int length() default 0;

    /**
     * 唯一限制，这个是service层使用的，需要查数据库
     * @return
     */
    boolean unique() default false;
}
