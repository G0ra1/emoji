package com.netwisd.common.core.util;

import com.netwisd.common.core.exception.IncloudException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zouliming@netwisd.com
 * @description 类相关的处理方法
 * @date 2022/1/7 17:05
 */
public interface IncloudClassHandler<T>{
    Log logger= LogFactory.getLog(IncloudClassHandler.class);
    /**
     * 获取T真实的class类，用来在spring容器中查找；
     * @return
     */
    default Class<T> getTClass(){
        try {
            //获取当前实现类的接口
            Type[] genericInterfaces = getClass().getGenericInterfaces();
            /**
             * 如果取不到的话，取一下父类，然后再取接口
             * 这种情况主要是考虑到像file模块这种中间加了一层抽象类父类层的情况；
             * 目前只实现做一层中间父类，如果多层继承暂时不考虑
             */
            if(genericInterfaces.length == 0){
                Type type = getClass().getGenericSuperclass();
                if(type instanceof ParameterizedType){
                    //找到基接口中所有的泛型类型，然后再取第一个，即得到传入EService中的泛型类型
                    return getTClass(type);
                }else {
                    Class superClass = (Class)(getClass().getGenericSuperclass());
                    genericInterfaces = superClass.getGenericInterfaces();
                    return getTClass(genericInterfaces);
                }
            }/*else {
                return getTClass(genericInterfaces);
            }*/
            return null;
        }catch (Exception e){
            logger.error("当前实现类未正常实现！");
            throw new IncloudException("当前实现类未正常实现！");
        }
    }

    default Class<T> getTClass(Type[] genericInterfaces){
        for (Type t : genericInterfaces){
            if(t instanceof ParameterizedType){
                //找到基接口中所有的泛型类型，然后再取第一个，即得到传入EService中的泛型类型
                return getTClass(t);
            }
        }
        return null;
    }

    default Type getTType(){
        //获取当前实现类的接口
        Type[] genericInterfaces = getClass().getGenericInterfaces();
        for (Type t : genericInterfaces){
            if(t instanceof ParameterizedType){
                //找到基接口中所有的泛型类型，然后再取第一个，即得到传入EService中的泛型类型
                Type[] actualTypeArguments = ((ParameterizedType)t).getActualTypeArguments();
                return actualTypeArguments[0];
            }
        }
        return null;
    }

    default Class<T> getTClass(Type t){
        Type[] actualTypeArguments = ((ParameterizedType)t).getActualTypeArguments();
        Class<T> actualTypeArgument = (Class<T>) actualTypeArguments[0];
        return actualTypeArgument;
    }
}
