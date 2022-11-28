package com.netwisd.base.wf.cache.runtime;

import com.netwisd.common.db.cache.IncloudAbstractCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 流程缓存（可以覆盖父类，自己处理特殊的缓存逻辑实现）
 * @Author: zouliming@netwisd.com
 * @Date: 2021/12/15 5:21 下午
 */
@Service
@Slf4j
public class IncloudProcessCache<T> extends IncloudAbstractCache {

    private Map<String,Map<Object,T>> map = new HashMap();

    @Override
    protected Map getSingleMap() {
        return map;
    }

}
