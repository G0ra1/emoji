package com.netwisd.base.wf.controller;

import cn.hutool.core.io.resource.ResourceUtil;
import com.netwisd.base.common.event.WfDelegateTaskDto;
import com.netwisd.base.common.event.WfTaskHandler;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.wf.dto.BizTestDto;
import com.netwisd.common.msg.rocket.data.BinLogData;
import com.netwisd.common.msg.rocket.service.BinLogDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: zouliming@netwisd.com
 * @Date: 2020/6/5 3:28 下午
 */
@Slf4j
@RestController
@RequestMapping("/test")
@RefreshScope
@Deprecated
public class TestController implements WfTaskHandler {

    @Autowired
    private BinLogDataService<BinLogData<MdmUserVo>> binLogDataService;

    @GetMapping("/test1")
    public void test1() {
        String json = ResourceUtil.readUtf8Str("test.json");
        BinLogData<MdmUserVo> mdmUserVoBinLogData = binLogDataService.getBinLogData(json);
        System.out.println(mdmUserVoBinLogData);
    }

    @Override
    @PostMapping("handle")
    public void handle(@RequestBody WfDelegateTaskDto delegateTask) {
        log.info("----test----assignee {}", delegateTask.getAssignee());
        //throw new IncloudException("test exception");
    }

    @PostMapping("/save")
    public void save(@RequestBody BizTestDto bizTestDto) {
        log.info("bizTestDto:{}", bizTestDto);
    }

    @PutMapping("/update")
    public void update(@RequestBody BizTestDto bizTestDto) {
        log.info("bizTestDto:{}", bizTestDto);
    }
}
