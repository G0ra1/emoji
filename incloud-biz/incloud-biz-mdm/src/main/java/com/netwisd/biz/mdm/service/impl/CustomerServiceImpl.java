package com.netwisd.biz.mdm.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.base.common.dict.dto.DictTreeDto;
import com.netwisd.base.common.dict.vo.DictTreeVo;
import com.netwisd.base.common.mdm.dto.MdmUserDto;
import com.netwisd.biz.mdm.constants.MdmConstants;
import com.netwisd.biz.mdm.constants.YesNo;
import com.netwisd.biz.mdm.dto.*;
import com.netwisd.biz.mdm.entity.*;
import com.netwisd.biz.mdm.fegin.DictClient;
import com.netwisd.biz.mdm.mapper.CustomerMapper;
import com.netwisd.biz.mdm.service.*;
import com.netwisd.biz.mdm.vo.CustomerBankVo;
import com.netwisd.biz.mdm.vo.CustomerContactsVo;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.vo.CustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 客户 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 16:45:51
 */
@Service
@Slf4j
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Autowired
    private Mapper dozerMapper;

    @Value("${spring.rocketmq.customerTopics}")
    private String customerTopics;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerBankService customerBankService;

    @Autowired
    private CustomerContactsService customerContactsService;

    @Autowired
    private MdmMqService mdmMqService;

    @Autowired
    private DictClient dictClient;

    @Autowired
    private CustomerCategoryRelationService customerCategoryRelationService;

    /**
    * 单表简单查询操作
    * @param customerDto
    * @return
    */
    @Override
    public Page list(CustomerDto customerDto) {
        Customer customer = dozerMapper.map(customerDto,Customer.class);
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(customerDto.getCustomerCode()),Customer::getCustomerCode,customerDto.getCustomerCode())
                .like(ObjectUtils.isNotEmpty(customerDto.getCustomerName()),Customer::getCustomerName,customerDto.getCustomerName())
                .eq(Customer::getIsDel, YesNo.NO.getCode());

        Page<Customer> page = customerMapper.selectPage(customerDto.getPage(),queryWrapper);
        Page<CustomerVo> pageVo = DozerUtils.mapPage(dozerMapper, page, CustomerVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param customerDto
    * @return
    */
    @Override
    public List<CustomerVo> lists(CustomerDto customerDto) {
        List<CustomerVo> list=this.allList(customerDto);
        log.debug("查询条数:"+list.size());
        return list;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public CustomerVo get(Long id) {
        Customer customer = super.getById(id);
        CustomerVo customerVo = null;
        if(customer !=null){
            customerVo = dozerMapper.map(customer,CustomerVo.class);
        }
        getOther(customerVo);
        log.debug("查询成功");
        return customerVo;
    }

    /**
    * 保存实体
    * @param customerDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(CustomerDto customerDto) {
        Customer customer = dozerMapper.map(customerDto,Customer.class);
        LambdaQueryWrapper<Customer> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(customer.getCustomerCode()),Customer::getCustomerCode,customer.getCustomerCode());
        List<Customer> customers=super.list(queryWrapper);
        if (CollectionUtils.isEmpty(customers)){
            boolean result = super.save(customer);
            customerDto.setId(customer.getId());
            saveOther(customerDto);
            if(result){
                log.debug("保存成功");
            }
            return result;
        }
        log.error("此数据已存在");
        return null;
    }

    /**
    * 修改实体
    * @param customerDto
    * @return
    */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean update(CustomerDto customerDto) {
        Customer customer = dozerMapper.map(customerDto,Customer.class);
        boolean result = super.updateById(customer);

        deleteOther(customerDto);
        saveOther(customerDto);

        List<Customer> customerList=new ArrayList<>();
        customerList.add(super.getById(customer.getId()));

        String json = JSON.toJSONString(customerList);
        mdmMqService.sendRocketMq(customerTopics,MdmConstants.CUSTOMER,json);
        if(result){
            log.debug("修改成功");
        }
        return result;
    }

    /**
    * 通过ID删除
    * @param id
    * @return
    */
    @Transactional
    @Override
    public Boolean delete(Long id) {
        boolean result = super.removeById(id);
        Customer customer=super.getById(id);
        CustomerDto customerDto=dozerMapper.map(customer,CustomerDto.class);
        deleteOther(customerDto);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    @SneakyThrows
    public Boolean saveList(List<CustomerDto> dtos) {
        List<CustomerBank> banks=new ArrayList<>();
        List<CustomerContacts> contactsList=new ArrayList<>();
        List<String> dataSourceIdList=dtos.stream().map(CustomerDto::getDataSourceId).collect(Collectors.toList());
        //查询已有数据
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Customer::getDataSourceId,dataSourceIdList);
        List<Customer> customers=super.list(queryWrapper);
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(customers)){
            List<Long> idList=customers.stream().map(Customer::getId).collect(Collectors.toList());
            LambdaQueryWrapper<CustomerBank> delBankWrapper=new LambdaQueryWrapper<>();
            delBankWrapper.in(CustomerBank::getCustomerId,idList);
            customerBankService.remove(delBankWrapper);
            LambdaQueryWrapper<CustomerContacts> delContWrapper=new LambdaQueryWrapper<>();
            delContWrapper.in(CustomerContacts::getCustomerId,idList);
            customerContactsService.remove(delContWrapper);
            LambdaQueryWrapper<CustomerCategoryRelation> delCateWrapper=new LambdaQueryWrapper<>();
            delCateWrapper.in(CustomerCategoryRelation::getCustomerId,idList);
            customerCategoryRelationService.remove(delCateWrapper);
        }

        List<CustomerDto> customerDtos=new ArrayList<>();
        for (CustomerDto dto:dtos) {
            Customer customer = dozerMapper.map(dto,Customer.class);
            //查询客户
            LambdaQueryWrapper<Customer> customerQueryWrapper=new LambdaQueryWrapper<>();
            customerQueryWrapper.eq(Customer::getDataSourceId,dto.getDataSourceId());
            Customer oldCustomer=this.getOne(customerQueryWrapper);
            if(ObjectUtil.isNotEmpty(oldCustomer)){
                customer.setId(oldCustomer.getId());
                this.updateById(customer);
            }else {
                save(customer);
            }
            //saveOrUpdate(customer,Wrappers.<Customer>lambdaQuery().eq(Customer::getDataSourceId, dto.getDataSourceId()));
            if(StringUtils.isNotEmpty(dto.getCategoryId())){
                String cateIdString=dto.getCategoryId();
                List<DictTreeVo> dictTreeVos=dictClient.list(cateIdString);
                //修改关系id
                List<Long> cateIdList=dictTreeVos.stream().map(DictTreeVo::getId).collect(Collectors.toList());
                List<String> idsList=cateIdList.stream().map(x -> x + "").collect(Collectors.toList());
                dto.setCategoryId(String.join(",",idsList));
                List<CustomerCategoryRelation> relations=new ArrayList<>();
                for (DictTreeVo dictTreeVo:dictTreeVos) {
                    CustomerCategoryRelation relation=new CustomerCategoryRelation();
                    relation.setCustomerId(customer.getId());
                    relation.setCustomerTypeId(dictTreeVo.getId());
                    relation.setCustomerTypeName(dictTreeVo.getDictName());
                    relation.setCustomerTypeCode(dictTreeVo.getDictCode());
                    relations.add(relation);
                }
                customerCategoryRelationService.saveBatch(relations);
            }
            if(StringUtils.isNotEmpty(dto.getBankName())){
                CustomerBank customerBank = new CustomerBank();
                customerBank.setCustomerId(customer.getId());
                customerBank.setCustomerCode(customer.getCustomerCode());
                customerBank.setBankName(dto.getBankName());
                customerBank.setBankAccount(dto.getBankAccount());
                banks.add(customerBank);
            }
            if(StringUtils.isNotEmpty(dto.getContactsName())){
                CustomerContacts contacts=new CustomerContacts();
                contacts.setCustomerId(customer.getId());
                contacts.setCustomerCode(customer.getCustomerCode());
                contacts.setContactsId(dto.getContactsId());
                contacts.setContactsName(dto.getContactsName());
                contacts.setContactsPhone(dto.getContactsPhone());
                contactsList.add(contacts);
            }
            CustomerDto customerDto=dozerMapper.map(customer,CustomerDto.class);
            customerDto.setBankList(DozerUtils.mapList(dozerMapper,banks,CustomerBankDto.class));
            customerDto.setContactsList(DozerUtils.mapList(dozerMapper,contactsList,CustomerContactsDto.class));
            customerDtos.add(customerDto);
        }
        customerContactsService.saveBatch(contactsList);
        customerBankService.saveBatch(banks);
        String json = JSON.toJSONString(customerDtos);
        mdmMqService.sendRocketMq(customerTopics,MdmConstants.CUSTOMER,json);
        return true;
    }

    @Override
    public Boolean delList(List<CustomerDto> dtos) {
        List<String> dataSourceList=dtos.stream().map(CustomerDto::getDataSourceId).collect(Collectors.toList());
        Customer customer = new Customer();
        customer.setIsDel(String.valueOf(YesNo.YES.getCode()));
        return update(customer,Wrappers.<Customer>lambdaQuery().in(Customer::getDataSourceId, dataSourceList));
    }

    public void getOther(CustomerVo customerVo){
        LambdaQueryWrapper<CustomerBank> bankLambdaQueryWrapper=new LambdaQueryWrapper<>();
        bankLambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(customerVo.getCustomerCode()),CustomerBank::getCustomerCode,customerVo.getCustomerCode());
        List<CustomerBank> banks=customerBankService.list(bankLambdaQueryWrapper);
        List<CustomerBankVo> bankVoList=new ArrayList<>();
        for (CustomerBank bank:banks) {
            CustomerBankVo vo=dozerMapper.map(bank,CustomerBankVo.class);
            bankVoList.add(vo);
        }
        customerVo.setBankList(bankVoList);

        LambdaQueryWrapper<CustomerContacts> contactsLambdaQueryWrapper=new LambdaQueryWrapper<>();
        contactsLambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(customerVo.getCustomerCode()),CustomerContacts::getCustomerCode,customerVo.getCustomerCode());
        List<CustomerContacts> contactsList=customerContactsService.list(contactsLambdaQueryWrapper);
        List<CustomerContactsVo> contactsVos=new ArrayList<>();
        for (CustomerContacts contact:contactsList) {
            CustomerContactsVo vo=dozerMapper.map(contact,CustomerContactsVo.class);
            contactsVos.add(vo);
        }
        customerVo.setContactsList(contactsVos);
    }

    public void deleteOther(CustomerDto customerDto){
        LambdaQueryWrapper<CustomerBank> bankLambdaQueryWrapper=new LambdaQueryWrapper<>();
        bankLambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(customerDto.getCustomerCode()),CustomerBank::getCustomerCode,customerDto.getCustomerCode());
        customerBankService.remove(bankLambdaQueryWrapper);

        LambdaQueryWrapper<CustomerContacts> contactsLambdaQueryWrapper=new LambdaQueryWrapper<>();
        contactsLambdaQueryWrapper.eq(ObjectUtils.isNotEmpty(customerDto.getCustomerCode()),CustomerContacts::getCustomerCode,customerDto.getCustomerCode());
        customerContactsService.remove(contactsLambdaQueryWrapper);
    }

    public void saveOther(CustomerDto customerDto){
        List<CustomerBankDto> bankDtoList=customerDto.getBankList();
        List<CustomerBank> banks=new ArrayList<>();
        for (CustomerBankDto dto:bankDtoList) {
            CustomerBank bank=dozerMapper.map(dto,CustomerBank.class);
            bank.setCustomerId(customerDto.getId());
            bank.setCustomerCode(customerDto.getCustomerCode());
            banks.add(bank);
        }
        customerBankService.saveBatch(banks);

        List<CustomerContactsDto> contactsDtos=customerDto.getContactsList();
        List<CustomerContacts> contactsList=new ArrayList<>();
        for (CustomerContactsDto dto:contactsDtos
             ) {
            CustomerContacts contacts=dozerMapper.map(dto,CustomerContacts.class);
            contacts.setCustomerId(customerDto.getId());
            contacts.setCustomerCode(customerDto.getCustomerCode());
            contactsList.add(contacts);
        }
        customerContactsService.saveBatch(contactsList);

    }

    public List<CustomerVo> allList(CustomerDto customerDto){
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        if(customerDto!=null){
            queryWrapper.eq(StringUtils.isNotEmpty(customerDto.getCustomerCode()),Customer::getCustomerCode,customerDto.getCustomerCode())
                    .eq(StringUtils.isNotEmpty(customerDto.getCustomerName()),Customer::getCustomerName,customerDto.getCustomerName())
                    .eq(StringUtils.isNotEmpty(customerDto.getCredential()),Customer::getCredential,customerDto.getCredential())
                    .eq(Customer::getFlowState,YesNo.YES.getCode())
                    .between(ObjectUtil.isNotNull(customerDto.getSUpdateTime())&&ObjectUtil.isNotNull(customerDto.getEUpdateTime()), Customer::getUpdateTime, customerDto.getSUpdateTime(),customerDto.getEUpdateTime());
        }
        List<Customer> customers=super.list(queryWrapper);
        List<CustomerVo> voList=DozerUtils.mapList(dozerMapper,customers,CustomerVo.class);
        for (CustomerVo vo:voList) {
            getOther(vo);
        }
        return  voList;
    }

}
