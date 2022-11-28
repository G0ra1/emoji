package com.netwisd.common.msg.rocket.service;

import com.netwisd.common.core.type.IncloudTypeReference;
import com.netwisd.common.core.util.IncloudClassHandler;
import com.netwisd.common.core.util.JacksonUtil;

public interface BinLogDataService<T> extends IncloudClassHandler<T> {

    void handle(String binLogData);

    default T getBinLogData(String binlogData) {
        /**
         * 通过自定义IncloudTypeReference，实现使用自已获取的getTType覆盖父类_type的值，以达到动态取到真实泛型的目的；
         */
        T t = JacksonUtil.parseObject(binlogData, new IncloudTypeReference<>(getTType()));
        /**
         * 写个反例，是用原父类TypeReference，默认如果传T泛型，使用LinkedHashMap来实现装载；
         */
        //T t2 = JacksonUtil.parseObject(binlogData, new TypeReference<T>() {});
        return t;
    }
}
