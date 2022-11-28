package com.netwisd.base.mdm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.base.common.mdm.dto.MdmDutyDto;
import com.netwisd.base.common.mdm.dto.MdmOrgDto;
import com.netwisd.base.common.mdm.dto.MdmPostDto;
import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.base.common.mdm.vo.MdmSysVo;
import com.netwisd.base.common.mdm.vo.MdmUserVo;
import com.netwisd.base.mdm.dto.MdmSysDto;
import com.netwisd.base.mdm.entity.MdmSys;
import com.netwisd.common.core.util.Result;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * @Description 对外提供接口以及MQ等 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-09 11:00:47
 */
public interface MdmMqService {

    //send mq
    public Result sendRocketMq(String topic, String tag, String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

    //同步组织到mq的全量数据
    public Result syncMqForOrgs(MdmOrgDto mdmOrgDto);

    //同步组织到mq的全量数据
    public Result syncMqForUsers(MdmUserDto mdmUserDto);

    //同步岗位到mq的全量数据
    public Result syncMqForPosts(MdmPostDto mdmPostDto);

    //同步职务到mq的全量数据
    public Result syncMqForDutys(MdmDutyDto mdmDutyDto);


    //缓存主数据人员数据
    //public Result sendAddRocketMqForCache(String tag, String msg);

}
