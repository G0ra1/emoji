package com.netwisd.base.msg.mq;

import com.netwisd.base.msg.mq.rocket.producer.RocketProducer;
import com.netwisd.common.core.exception.IncloudException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public enum ConsumerPool {

    INSTANCE;

    //先使用map、如果后期多实例部署需改成redis等中间件。
    private final Map<String, Object> producerPool = new ConcurrentHashMap<>();

    private static final Object lock = new Object();

    public Object get(String key) {
        return producerPool.get(key);
    }

    public void put(String key, Object val) {
        synchronized (lock) {
            producerPool.put(key, val);
        }
    }

    public void remove(String key) {
        producerPool.remove(key);
    }

    /*private <T> T create(String mq, Function<Producer, T> function) {
        Producer producer = new RocketProducer();
        try {
            return function.apply(producer);
        } catch (IncloudException e) {
            throw e;
        } catch (Exception e) {
            throw new IncloudException("执行失败");
        } finally {
            producerPool.put("", producer);
        }
    }*/
}
