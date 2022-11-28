package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.dto.PushMsgDto;
import com.netwisd.biz.mdm.entity.PushMsg;
import com.netwisd.biz.mdm.vo.PushMsgVo;
import com.netwisd.common.core.util.Result;
import javax.jms.Destination;

/**
 * @Description 推送消息 功能描述...
 * @author 云数网讯 zouliming@netwisd.com
 * @date 2020-10-16 11:54:52
 */
public interface PushMsgService extends IService<PushMsg> {
    Page list(PushMsgDto pushMsgDto);
    Page lists(PushMsgDto pushMsgDto);
    PushMsgVo get(Long id);
    Boolean save(PushMsgDto pushMsgDto);
    Result update(PushMsgDto pushMsgDto);
    Boolean delete(Long id);
    PushMsgDto initPushMsg(PushMsgDto pushMsgDto);

    Result queryNoneNum(String accessToken);

    public void sendMessage(Destination destination, final String message);
}
