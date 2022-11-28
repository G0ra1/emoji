package com.netwisd.common.core.anntation;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.netwisd.common.core.function.IncloudFunction;

import java.lang.annotation.*;

/**
 * @Description: 参数校验注解
 * @see com.netwisd.common.core.anntation.Valid
 * 支持Java基本类型
 * 支持自定义包装类（实现IValidation接口，配合@Valid注解使用）；并且，可以多层嵌套；
 * 支持Java内置Map，Collection数据结构；并且，支持自身多层嵌套和自定义（实现IValidation接口）包装类多层嵌套
 * 其他Java内置对象，需要使用时自己扩展
 * 扩展AutoValidateParam;
 * PS,由于JDK8对反射的目标类的编译文件做了优化操作，默认编译后的classs不显示参数名，会以arg0...类似出现；
 * 使用-parameters参数打开
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/23 3:18 下午
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validation {
    //非空做为必校验项，不提供设置了；
    /*boolean checkEmpty() default true;*/

    //暂不实现--需要时再说
    boolean checkLength() default false;

    ExcludeAnntation[] exclude() default {};

    //暂不实现--正常使用Valid,需要排除加上ExcludeAnntation，实现IncludeAnntation会影响一部分性能
    IncludeAnntation[] include() default {};
}
