package com.netwisd.base.msg.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.msg.entity.Message;
import com.netwisd.base.common.msg.vo.MessageVo;

public interface MessageService extends IService<Message> {

    /**
     * 分页查询
     *
     * @param messageDto
     * @param isAll
     * @return
     */
    IPage queryPageList(MessageDto messageDto, boolean isAll);

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    MessageVo getMessage(String id);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    boolean delMessage(String ids);

    /**
     * 一键阅读
     *
     * @return
     */
    boolean readAll();

    /**
     * 更新阅读状态
     *
     * @param ids
     * @return
     */
    boolean editRead(String ids);

    /**
     * 获取未读数量
     *
     * @return
     */
    int getUnreadNumber();
}
