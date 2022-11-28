package com.netwisd.common.core.util;

import cn.hutool.core.util.ObjectUtil;
import com.netwisd.common.core.exception.IncloudException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Description: spring容器取bean的工具类；
 * @Author: zouliming@netwisd.com
 * @Date: 2020/3/27 11:26 上午
 */
@Component
@Slf4j
public class SpringContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextUtils.applicationContext == null) {
            SpringContextUtils.applicationContext = applicationContext;
        }
    }

    /**
     * @apiNote 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @apiNote 通过name获取 Bean.
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * @apiNote 通过class获取Bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * @apiNote 通过name, 以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 注册一个bean到spring容器中
     * @param beanName bean的name
     * @param clazz bean对应的class类，实际他会根据这个类来反射生成对象
     * @param args 如果class类对应的有 有参的构造器
     * @param <T> 返回这个bean的对象
     * @return
     */
    public static <T> T registerBean(String beanName, Class<T> clazz, Object... args) {
        if(applicationContext.containsBean(beanName)) {
            Object bean = applicationContext.getBean(beanName);
            Class<?> aClass = bean.getClass();
            /**
             * 解释可以参考jdk Class类的原文翻译——想了解的自己去翻译
             * 也就是判断当前的Class对象所表示的类，是不是参数中传递的Class对象所表示的类的父类，接口，
             * 或者是相同的类型。是则返回true，否则返回false。
             * 这里，bean.getClass()，只要在spring容器里的，都是基于spring给创建的代理类，也就是cglib；
             * 那么肯定符合子类这条，反过来就不行了，这个目前应用到了incloud-base-file里，
             * 后台会大量应用到动态注册的相关功能中。。。
             */
            if (clazz.isAssignableFrom(bean.getClass())) {
                return (T) bean;
            } else {
                throw new IncloudException("BeanName 重复：{} " , beanName);
            }
        }
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        if (ObjectUtil.isNotEmpty(args)) {
            for (Object arg : args) {
                beanDefinitionBuilder.addConstructorArgValue(arg);
            }
        }
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition)beanDefinitionBuilder.getRawBeanDefinition();
        beanDefinition.setBeanClass(clazz);
        beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_NAME);

        ConfigurableApplicationContext context = null;
        if (applicationContext instanceof ConfigurableApplicationContext){
            context = (ConfigurableApplicationContext)applicationContext;
        }

        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getBeanFactory();
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
        return applicationContext.getBean(beanName, clazz);
    }

    /**
     * 根据beanName删除掉spring容器中的对象
     * @param beanName
     */
    public static void removeBean(String beanName) {
        ConfigurableApplicationContext context = null;
        if (applicationContext instanceof ConfigurableApplicationContext){
            context = (ConfigurableApplicationContext)applicationContext;
        }
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getParentBeanFactory();
        //Object bean = applicationContext.getBean(beanName);
        try {
            beanFactory.removeBeanDefinition(beanName);
        }catch (Exception e){
            log.error("删除beanName:{}失败！",beanName);
        }
    }
}
