package com.netwisd.base.portal.fegin;

import com.netwisd.base.common.vo.wf.WfProcessLogVo;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/5/21 11:12 上午
 */
@FeignClient(value = "incloud-base-wf")
public interface WfClient {

    @ApiOperation(value = "判断是否是驳回的草稿状态", notes = "判断是否是驳回的草稿状态")
    @RequestMapping(value = "/wfprocesslog/isDraftByLog", method = RequestMethod.GET)
    @SneakyThrows
    Boolean isDraftByLog(@RequestParam("camundaProcinsId")String camundaProcinsId);

    @ApiOperation(value = "根据camundaProcinsId获取所有人员日志信息", notes = "根据camundaProcinsId获取所有人员日志信息")
    @RequestMapping(value = "/wfprocesslog/getList", method = RequestMethod.GET)
    @SneakyThrows
    List<WfProcessLogVo> getList(@RequestParam("camundaProcinsId")String camundaProcinsId);
}
