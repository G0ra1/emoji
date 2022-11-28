package com.netwisd.base.msg.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.msg.dto.MessageDto;
import com.netwisd.base.msg.dto.MsgTemplateDto;
import com.netwisd.base.msg.entity.MsgTemplate;
import com.netwisd.base.msg.vo.MsgTemplateVo;

public interface MsgTemplateService extends IService<MsgTemplate> {

    /**
     * 分页查询
     *
     * @param msgTemplateDto
     * @return
     */
    IPage queryPageList(MsgTemplateDto msgTemplateDto);

    /**
     * 新增
     *
     * @param msgTemplateDto
     * @return
     */
    boolean addMsgTemplate(MsgTemplateDto msgTemplateDto);

    /**
     * 修改
     *
     * @param msgTemplateDto
     * @return
     */
    boolean editMsgTemplate(MsgTemplateDto msgTemplateDto);

    /**
     * 获取详情
     *
     * @param id
     * @return
     */
    MsgTemplateVo getMsgTemplate(String id);

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    boolean delMsgTemplate(String ids);

    /**
     * 消息模板发送
     *
     * @param messageDto
     */
    void sendTmpMsg(MessageDto messageDto);

    /**
     * 获取消息内容
     *
     * @param tmpContent
     * @param tmpParams
     * @return
     */
    String getMsgContent(String tmpContent, String tmpParams);
}
