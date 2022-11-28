package com.netwisd.common.core.type;


import java.lang.reflect.Type;

/**
 * @author ：zouliming@netwisd.com
 * @date ：Created in 2022/2/15 10:56 PM
 * @description：自定义类型引用，主要用于处理自定义泛型获取的type类型
 */
public class IncloudTypeReference<T,E extends Type> extends com.fasterxml.jackson.core.type.TypeReference<T> {
    //代表传入的泛型
    protected final Type type;

    public IncloudTypeReference(E e){
        type = e;
    }

    @Override
    public Type getType() {
        return type;
    }
}
