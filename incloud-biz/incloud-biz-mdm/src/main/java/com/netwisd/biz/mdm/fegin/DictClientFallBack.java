package com.netwisd.biz.mdm.fegin;

import com.google.common.collect.Lists;
import com.netwisd.base.common.dict.dto.DictTreeDto;
import com.netwisd.base.common.dict.vo.DictItemVo;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DictClientFallBack implements FallbackFactory<DictClient> {

    @Override
    public DictClient create(Throwable throwable) {
        log.error("DictFeignClient异常:{}", throwable);
        return new DictClient() {
            @Override
            public List<DictTreeVo> list(String extIds) {
                return Lists.newArrayList();
            }

            @Override
            public String getRuleValue(String formName, String encondField, Map<String, Object> entityMap) {
                return null;
            }
        };
    }
}
