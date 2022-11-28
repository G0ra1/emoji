package com.netwisd.base.mdm.feign;

import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.common.msg.vo.MessageVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("incloud-base-msg")
public interface MsgClient {

    //发送消息
    @PostMapping("/send")
    public List<MessageVo> sendTmpMsg(@RequestBody MessageDto messageDto);

    //修改已读
    @PutMapping("/upRead/{ids}")
    public boolean upRead(@PathVariable("ids") String ids);

    //删除消息
    @DeleteMapping("/del/{ids}")
    public boolean delMsg(@PathVariable("ids") String ids);

}
