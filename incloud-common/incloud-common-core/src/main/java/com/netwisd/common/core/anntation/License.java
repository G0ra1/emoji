package com.netwisd.common.core.anntation;

import java.lang.annotation.*;

/**
 * @Description: 这个注解使用在controller层，用来aop切入他来做license认证
 * 为什么不放在service层，或者其他层，主要原因是为因为service层有很多业务逻辑在里面，包括一些事务、代理等，怕影响到数据层面的东西；
 * 这个日后有时间再研究
 * 为了安全起见，这个注解的处理放到了ValidationHandler中
 * @Author: zouliming@netwisd.com
 * @Date: 2021/4/27 14:10
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface License {
    String incloud() default "Netwisd*8";
}
