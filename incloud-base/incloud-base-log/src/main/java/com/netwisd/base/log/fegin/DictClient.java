package com.netwisd.base.log.fegin;

import com.netwisd.base.common.dict.vo.DictItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "incloud-base-dict", fallbackFactory = DictFeignClientFallbackFactory.class)
public interface DictClient {

    @GetMapping(path = "/api/item/list")
    List<DictItemVo> itemList(@RequestParam("dictCode") String dictCode);

}
