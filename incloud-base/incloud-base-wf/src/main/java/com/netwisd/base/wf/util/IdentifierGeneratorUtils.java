package com.netwisd.base.wf.util;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * @Description: Id自动生成器
 * @Author: zouliming@netwisd.com
 * @Date: 2020/8/6 2:15 下午
 */
public class IdentifierGeneratorUtils {
    /**
     * 主机和进程的机器码
     */
    private static IdentifierGenerator IDENTIFIER_GENERATOR = new DefaultIdentifierGenerator();

    public static Long next(){
        return IDENTIFIER_GENERATOR.nextId(IdentifierGeneratorUtils.class).longValue();
    }
}
