package com.netwisd.common.core.function;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.SerializationUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;

import java.io.*;
import java.util.function.Function;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2021/12/16 2:00 下午
 */
@FunctionalInterface
public interface IncloudFunction<T,R> extends Function<T, R>, Serializable {

    /**
     * 通过反序列化转换 lambda 表达式，该方法只能序列化 lambda 表达式，不能序列化接口实现或者正常非 lambda 写法的对象
     *
     * @param lambda lambda对象
     * @return 返回解析后的 SerializedLambda
     */
    static SerializedLambda resolve(IncloudFunction<?, ?> lambda) {
        if (!lambda.getClass().isSynthetic()) {
            throw ExceptionUtils.mpe("该方法仅能传入 lambda 表达式产生的合成类");
        }
        try (ObjectInputStream objIn = new ObjectInputStream(new ByteArrayInputStream(SerializationUtils.serialize(lambda))) {
            @Override
            protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
                Class<?> clazz;
                try {
                    clazz = ClassUtils.toClassConfident(objectStreamClass.getName());
                } catch (Exception ex) {
                    clazz = super.resolveClass(objectStreamClass);
                }
                return clazz == java.lang.invoke.SerializedLambda.class ? SerializedLambda.class : clazz;
            }
        }) {
            return (SerializedLambda) objIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw ExceptionUtils.mpe("This is impossible to happen", e);
        }
    }
}
