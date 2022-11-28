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
public @interface Table {
    /**
     * 表名
     *
     * @return
     */
    public String value() default "";

    /**
     * 表前缀
     *
     * @return
     */
    public String prefix() default "";

    /**
     * 表注释
     *
     * @return
     */
    public String comment() default "";

    /**
     * 表的引擎
     *
     * @return
     */
    public String engine() default "InnoDB";

    /**
     * 表字符编码
     *
     * @return
     */
    public String charset() default "utf8";
}
