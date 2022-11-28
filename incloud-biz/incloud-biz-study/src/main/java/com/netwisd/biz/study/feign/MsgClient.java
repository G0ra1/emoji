package com.netwisd.biz.study.feign;

import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.common.msg.dto.MessageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Description:
 * @Author: limengzheng@netwisd.com
 * @Date: 2022/2/14 10:48
 */
@FeignClient(value = "incloud-base-msg")
public interface MsgClient {

    @PostMapping(path = "/api/send")
    public void sendTmpMsg(MessageDto messageDto);

}
