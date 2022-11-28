package com.netwisd.biz.mdm.service;

import com.netwisd.base.common.mdm.dto.MdmDutyDto;
import com.netwisd.base.common.mdm.dto.MdmOrgDto;
import com.netwisd.base.common.mdm.dto.MdmPostDto;
import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.biz.mdm.dto.*;
import com.netwisd.common.core.util.Result;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @Description 对外提供接口以及MQ等 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-10-09 11:00:47
 */
public interface MdmMqService {

    //send mq
    public Result sendRocketMq(String topic, String tag, String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;

    //同步物资到mq的全量数据
    public Result syncMqForItem(ItemDto itemDto);

    //同步物资分类到mq的全量数据
    public Result syncMqForItemClassify(ItemClassifyDto itemClassifyDto);

    //同步客户到mq的全量数据
    public Result syncMqForCustomer(CustomerDto customerDto);

    //同步销售合同到mq的全量数据
    public Result syncMqForSell(ContractSellDto sellDto) ;

    //同步项目到mq的全量数据
    public Result syncMqForProject(ProjectDto projectDto);

    //同步采购合同到mq的全量数据
    public Result syncMqForPurchase(ContractPurchaseDto purchaseDto);

    //同步供应商到mq的全量数据
    public Result syncMqForSupplier(SupplierDto supplierDto);

}
