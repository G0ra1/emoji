package com.netwisd.base.log.fegin;

import com.google.common.collect.Lists;
import com.netwisd.base.common.dict.vo.DictItemVo;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DictFeignClientFallbackFactory implements FallbackFactory<DictClient> {

    @Override
    public DictClient create(Throwable throwable) {
        log.error("DictFeignClient异常:{}", throwable);
        return new DictClient() {
            @Override
            public List<DictItemVo> itemList(String dictCode) {
                return Lists.newArrayList();
            }
        };
    }

}
