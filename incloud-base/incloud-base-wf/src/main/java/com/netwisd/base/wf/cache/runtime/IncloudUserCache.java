package com.netwisd.base.wf.cache.runtime;

import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.common.db.cache.IncloudAbstractCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("incloudUserCache")
public class IncloudUserCache<T> extends IncloudAbstractCache {

    private Map<String, MdmUserVo> map = new HashMap();

    @Override
    protected Map getSingleMap() {
        return map;
    }

}
