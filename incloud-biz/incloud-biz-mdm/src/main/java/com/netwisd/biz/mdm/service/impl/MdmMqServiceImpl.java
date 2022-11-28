package com.netwisd.biz.mdm.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.dozermapper.core.Mapper;
import com.netwisd.biz.mdm.constants.ContractType;
import com.netwisd.biz.mdm.constants.MdmConstants;
import com.netwisd.biz.mdm.dto.*;
import com.netwisd.biz.mdm.entity.*;
import com.netwisd.biz.mdm.service.*;
import com.netwisd.biz.mdm.vo.ItemClassifyAllVo;
import com.netwisd.biz.mdm.vo.ItemClassifyVo;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.common.core.util.Result;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class MdmMqServiceImpl implements MdmMqService {

    @Value("${spring.rocketmq.disable}")
    private boolean disable;

    @Value("${spring.rocketmq.itemTopics}")
    private String itemTopics;

    @Value("${spring.rocketmq.itemClassifyTopics}")
    private String itemClassifyTopics;

    @Value("${spring.rocketmq.customerTopics}")
    private String customerTopics;

    @Value("${spring.rocketmq.sellTopics}")
    private String sellTopics;

    @Value("${spring.rocketmq.projectTopics}")
    private String projectTopics;

    @Value("${spring.rocketmq.supplierTopics}")
    private String supplierTopics;

    @Value("${spring.rocketmq.purchaseTopics}")
    private String purchaseTopics;

    @Autowired
    @Lazy
    private CustomerService customerService;

    @Autowired
    @Lazy
    private CustomerBankService customerBankService;
    @Autowired
    @Lazy
    private CustomerContactsService customerContactsService;

    @Autowired
    @Lazy
    private ItemService itemService;

    @Autowired
    @Lazy
    private ItemClassifyService itemClassifyService;

    @Autowired
    @Lazy
    private ContractSellService contractSellService;

    @Autowired
    @Lazy
    private ContractSellDetailsService contractSellDetailsService;
    @Autowired
    @Lazy
    private ContractPartyaService partyaService;
    @Autowired
    @Lazy
    private ContractPartybService partybService;
    @Autowired
    @Lazy
    private ContractPartycService partycService;
    @Autowired
    @Lazy
    private ProjectService projectService;
    @Autowired
    @Lazy
    private SupplierService supplierService;
    @Autowired
    @Lazy
    private SupplierContactsService supplierContactsService;
    @Autowired
    @Lazy
    private SupplierBankService supplierBankService;
    @Autowired
    @Lazy
    private ContractPurchaseService contractPurchaseService;
    @Autowired
    @Lazy
    private ContractPurchaseDetailsService contractPurchaseDetailsService;


    @Autowired
    private DefaultMQProducer defaultMQProducer;

    @Autowired
    private Mapper dozerMapper;
    @Override
    @SneakyThrows
    public Result sendRocketMq(String topic, String tag, String msg){
        if(disable) {//判断如果禁用则不发送消息
            log.error("mq消息已经禁用！");
            return  Result.success();
        }
        if (StringUtils.isEmpty(msg)) {
            log.error("发送rocketMq msg为空。");
            return  Result.success();
        }
        if(StringUtils.isEmpty(topic)) {
            log.error("发送rocketMq topic为空。");
            return  Result.success();
        }
        if(StringUtils.isEmpty(tag)) {
            log.error("发送rocketMq tag。");
            return  Result.success();
        }
        log.info("发送rocketMq消息内容：" + msg);
        List<String> streamStr = Stream.of(topic.split(",")).collect(Collectors.toList());
        if(CollectionUtil.isNotEmpty(streamStr)) {
            for (String tp : streamStr) {
                Message sendMsg = new Message(tp, tag, msg.getBytes());
                // 默认3秒超时
                SendResult sendResult = defaultMQProducer.send(sendMsg,new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        return mqs.get(0); //数据量不大 直接指定一个队列
                    }
                }, msg);
                log.info("消息发送响应：" + sendResult.toString());
            }
        } else {
            log.error("发送rocketMq topic为空。");
            return  Result.success();
        }
        return  Result.success();
    }

    @Override
    @SneakyThrows
    @Async
    public Result syncMqForItem(ItemDto itemDto){
        int size=5;
        for (int i =1;i<3;i++) {
            Page<Item> page = new Page();
            page.setCurrent(i);
            page.setSize(size);
            Page pageList = itemService.page(page, new LambdaQueryWrapper());
            List<Item> list=pageList.getRecords();
            if(CollectionUtils.isNotEmpty(list)){
                String json = JSON.toJSONString(list);
                this.sendRocketMq( itemTopics, MdmConstants.ITEM_CODE,json);
            }
            if (list.size()<size) {return Result.success();}
        }
        return Result.success();
    }

    @Override
    public Result syncMqForItemClassify(ItemClassifyDto itemClassifyDto) {
        List<ItemClassifyAllVo> list=itemClassifyService.list(itemClassifyDto);
        String json = JSON.toJSONString(list);
        this.sendRocketMq( itemClassifyTopics, MdmConstants.ITEM_TYPE,json);
        return Result.success();
    }

    @Override
    public Result syncMqForProject(ProjectDto projectDto) {
        int size=5000;
        for (int i =1;i<3;i++) {
            Page<Project> page = new Page();
            page.setCurrent(i);
            page.setSize(size);
            Page pageList = projectService.page(page, new LambdaQueryWrapper());
            List<Project> list=pageList.getRecords();
            if(CollectionUtils.isNotEmpty(list)){
                String json = JSON.toJSONString(list);
                this.sendRocketMq( projectTopics, MdmConstants.PROJECT,json);
            }
            if (list.size()<size)
                return Result.success();
        }
        return Result.success();
    }

    @Override
    public Result syncMqForCustomer(CustomerDto customerDto) {
        //查询相关数据
        List<CustomerBank> customerBanks=customerBankService.list();
        List<CustomerContacts> customerContacts=customerContactsService.list();
        int size=5000;
        for (int i =1;i<3;i++) {
            Page<Customer> page = new Page();
            page.setCurrent(i);
            page.setSize(size);
            Page pageList = customerService.page(page, new LambdaQueryWrapper());
            List<Customer> list=pageList.getRecords();
            List<CustomerDto> dtoList=DozerUtils.mapList(dozerMapper,list,CustomerDto.class);
            dtoList.forEach(customer -> {
                List<CustomerBank> banks=customerBanks.stream().filter(customerBank -> customerBank.getCustomerCode().equals(customer.getCustomerCode())).collect(Collectors.toList());
                customer.setBankList(DozerUtils.mapList(dozerMapper,banks, CustomerBankDto.class));
                List<CustomerContacts> contacts=customerContacts.stream().filter(contancts -> contancts.getCustomerCode().equals(customer.getCustomerCode())).collect(Collectors.toList());
                customer.setContactsList(DozerUtils.mapList(dozerMapper,contacts, CustomerContactsDto.class));
            });
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(dtoList)){
                String json = JSON.toJSONString(dtoList);
                this.sendRocketMq(customerTopics, MdmConstants.CUSTOMER, json);
            }
            if (list.size()<size)
                return  Result.success();
        }
        return Result.success();
    }

    @Override
    public Result syncMqForSell(ContractSellDto sellDto) {
        int size=5000;
        for (int i =1;i<3;i++) {
            Page<ContractSell> page = new Page();
            page.setCurrent(i);
            page.setSize(size);
            Page pageList = contractSellService.page(page, new LambdaQueryWrapper());
            List<ContractSell> list=pageList.getRecords();
            List<ContractSellDto> dtoList=DozerUtils.mapList(dozerMapper,list,ContractSellDto.class);

            List<String> contractSellCodeList=dtoList.stream().map(ContractSellDto::getContractCode).collect(Collectors.toList());
            List<ContractSellDetails> sellDetails=contractSellDetailsService.list(Wrappers.<ContractSellDetails>lambdaQuery().in(ContractSellDetails::getContractCode,contractSellCodeList));
            List<ContractPartya> partyaList=partyaService.list(Wrappers.<ContractPartya>lambdaQuery().in(ContractPartya::getContractCode,contractSellCodeList).eq(ContractPartya::getContractType, ContractType.SALE.getCode()));
            List<ContractPartyb> partybList=partybService.list(Wrappers.<ContractPartyb>lambdaQuery().in(ContractPartyb::getContractCode,contractSellCodeList).eq(ContractPartyb::getContractType,ContractType.SALE.getCode()));
            List<ContractPartyc> partycList=partycService.list(Wrappers.<ContractPartyc>lambdaQuery().in(ContractPartyc::getContractCode,contractSellCodeList).eq(ContractPartyc::getContractType,ContractType.SALE.getCode()));

            dtoList.forEach(sell -> {
                List<ContractSellDetails> details=sellDetails.stream().filter(sellDetails1 -> sellDetails1.getContractCode().equals(sell.getContractCode())).collect(Collectors.toList());
                List<ContractPartya> partyaListStr=partyaList.stream().filter(partya ->partya.getContractCode().equals(sell.getContractCode())).collect(Collectors.toList());
                List<ContractPartyb> partybListStr=partybList.stream().filter(partyb ->partyb.getContractCode().equals(sell.getContractCode())).collect(Collectors.toList());
                List<ContractPartyc> partycListStr=partycList.stream().filter(partyc ->partyc.getContractCode().equals(sell.getContractCode())).collect(Collectors.toList());
                sell.setDetailsList(DozerUtils.mapList(dozerMapper,details,ContractSellDetailsDto.class));
                sell.setPartyaList(DozerUtils.mapList(dozerMapper,partyaListStr,ContractPartyaDto.class));
                sell.setPartybList(DozerUtils.mapList(dozerMapper,partybListStr,ContractPartybDto.class));
                sell.setPartycList(DozerUtils.mapList(dozerMapper,partycListStr,ContractPartycDto.class));
            });
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(dtoList)){
                String json = JSON.toJSONString(dtoList);
                this.sendRocketMq(sellTopics, MdmConstants.SELL, json);
            }
            if (list.size()<size)
                return Result.success();
        }

        return Result.success();
    }

    @Override
    public Result syncMqForPurchase(ContractPurchaseDto purchaseDto) {
        int size=5000;
        for (int i =1;i<3;i++) {
            Page<ContractPurchase> page = new Page();
            page.setCurrent(i);
            page.setSize(size);
            Page pageList = contractPurchaseService.page(page, new LambdaQueryWrapper());
            List<ContractPurchase> list=pageList.getRecords();
            List<ContractPurchaseDto> dtoList=DozerUtils.mapList(dozerMapper,list,ContractPurchaseDto.class);

            List<String> contractPurchaseCodeList=dtoList.stream().map(ContractPurchaseDto::getContractCode).collect(Collectors.toList());
            List<ContractPurchaseDetails> purchaseDetails=contractPurchaseDetailsService.list(Wrappers.<ContractPurchaseDetails>lambdaQuery().in(ContractPurchaseDetails::getContractCode,contractPurchaseCodeList));
            List<ContractPartya> partyaList=partyaService.list(Wrappers.<ContractPartya>lambdaQuery().in(ContractPartya::getContractCode,contractPurchaseCodeList).eq(ContractPartya::getContractType,ContractType.PURCHASE.getCode()));
            List<ContractPartyb> partybList=partybService.list(Wrappers.<ContractPartyb>lambdaQuery().in(ContractPartyb::getContractCode,contractPurchaseCodeList).eq(ContractPartyb::getContractType,ContractType.PURCHASE.getCode()));
            List<ContractPartyc> partycList=partycService.list(Wrappers.<ContractPartyc>lambdaQuery().in(ContractPartyc::getContractCode,contractPurchaseCodeList).eq(ContractPartyc::getContractType,ContractType.PURCHASE.getCode()));

            dtoList.forEach(purchase -> {
                List<ContractPurchaseDetails> details=purchaseDetails.stream().filter(purchaseDetails1 -> purchaseDetails1.getContractCode().equals(purchase.getContractCode())).collect(Collectors.toList());
                List<ContractPartya> partyaListStr=partyaList.stream().filter(partya ->partya.getContractCode().equals(purchase.getContractCode())).collect(Collectors.toList());
                List<ContractPartyb> partybListStr=partybList.stream().filter(partyb ->partyb.getContractCode().equals(purchase.getContractCode())).collect(Collectors.toList());
                List<ContractPartyc> partycListStr=partycList.stream().filter(partyc ->partyc.getContractCode().equals(purchase.getContractCode())).collect(Collectors.toList());
                purchase.setDetailsList(details);
                purchase.setPartyaList(DozerUtils.mapList(dozerMapper,partyaListStr,ContractPartyaDto.class));
                purchase.setPartybList(DozerUtils.mapList(dozerMapper,partybListStr,ContractPartybDto.class));
                purchase.setPartycList(DozerUtils.mapList(dozerMapper,partycListStr,ContractPartycDto.class));
            });
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(dtoList)){
                String json = JSON.toJSONString(dtoList);
                this.sendRocketMq(purchaseTopics, MdmConstants.PURCHASE, json);
            }
            if (list.size()<size)
                return Result.success();
        }

        return Result.success();
    }

    @Override
    public Result syncMqForSupplier(SupplierDto supplierDto) {
        int size=5000;
        for (int i =1;i<3;i++) {
            Page<Supplier> page = new Page();
            page.setCurrent(i);
            page.setSize(size);
            Page pageList = supplierService.page(page, new LambdaQueryWrapper());
            List<Supplier> list=pageList.getRecords();
            List<SupplierDto> dtoList=DozerUtils.mapList(dozerMapper,list,SupplierDto.class);
            List<String> supplierCodeList=dtoList.stream().map(SupplierDto::getSupplierCode).collect(Collectors.toList());
            List<SupplierBank> supplierBanks=supplierBankService.list(Wrappers.<SupplierBank>lambdaQuery().in(SupplierBank::getSupplierCode,supplierCodeList));
            List<SupplierContacts> supplierContacts=supplierContactsService.list(Wrappers.<SupplierContacts>lambdaQuery().in(SupplierContacts::getSupplierCode,supplierCodeList));

            dtoList.forEach(supplier -> {
                List<SupplierBank> banks=supplierBanks.stream().filter(supplierBank -> supplierBank.getSupplierCode().equals(supplier.getSupplierCode())).collect(Collectors.toList());
                supplier.setBankList(DozerUtils.mapList(dozerMapper,banks,SupplierBankDto.class));
                List<SupplierContacts> contacts=supplierContacts.stream().filter(contancts -> contancts.getSupplierCode().equals(supplier.getSupplierCode())).collect(Collectors.toList());
                supplier.setContactsList(DozerUtils.mapList(dozerMapper,contacts,SupplierContactsDto.class));
            });
            if(org.apache.commons.collections.CollectionUtils.isNotEmpty(dtoList)){
                String json = JSON.toJSONString(dtoList);
                this.sendRocketMq(supplierTopics, MdmConstants.SUPPLIER, json);
            }
            if (list.size()<size)
                return Result.success();
        }

        return Result.success();
    }
}
