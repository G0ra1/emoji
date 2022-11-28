package com.netwisd.biz.mdm.fegin;

import com.google.common.collect.Lists;
import com.netwisd.common.core.util.Result;
import com.netwisd.base.common.mdm.vo.MdmOrgVo;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MdmClientFallBack implements FallbackFactory<MdmClient> {
    @Override
    public MdmClient create(Throwable throwable) {
        log.error("MdmFeignClient异常:{}", throwable);
        return new MdmClient() {
            @Override
            public Result<List<MdmOrgVo>> getOrgByJcOrgIds(String ids) {
                return new Result<List<MdmOrgVo>>();
            }@Override
            public Result<MdmOrgVo> get(Long ids) {
                return new Result<MdmOrgVo>();
            }
        };
    }

}
