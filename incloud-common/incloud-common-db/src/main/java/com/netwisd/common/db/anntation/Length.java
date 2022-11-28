package com.netwisd.common.db.anntation;

import java.lang.annotation.*;

/**
 * @Description: 长度注解
 * @Author: zouliming@netwisd.com
 * @Date: 2020/4/18 1:09 下午
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Length {
    /**
     * 默认是1，0表示不需要设置，1表示需要设置一个，2表示需要设置两个
     *
     * @return 默认是1，0表示不需要设置，1表示需要设置一个，2表示需要设置两个
     */
    public int length() default 1;
}
