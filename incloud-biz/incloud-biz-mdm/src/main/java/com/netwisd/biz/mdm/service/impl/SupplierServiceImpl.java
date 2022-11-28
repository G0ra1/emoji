package com.netwisd.biz.mdm.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.netwisd.biz.mdm.constants.MdmConstants;
import com.netwisd.biz.mdm.dto.SupplierBankDto;
import com.netwisd.biz.mdm.dto.SupplierContactsDto;
import com.netwisd.biz.mdm.entity.*;
import com.netwisd.biz.mdm.mapper.SupplierMapper;
import com.netwisd.biz.mdm.service.MdmMqService;
import com.netwisd.biz.mdm.service.SupplierBankService;
import com.netwisd.biz.mdm.service.SupplierContactsService;
import com.netwisd.biz.mdm.service.SupplierService;
import com.netwisd.biz.mdm.vo.SupplierBankVo;
import com.netwisd.biz.mdm.vo.SupplierContactsVo;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.SupplierDto;
import com.netwisd.biz.mdm.vo.SupplierVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description 供应商 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 15:54:49
 */
@Service
@Slf4j
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private SupplierBankService bankService;
    @Autowired
    private SupplierContactsService supplierContactsService;

    @Value("${spring.rocketmq.supplierTopics}")
    private String supplierTopics;

    @Autowired
    MdmMqService mdmMqService;

    /**
    * 单表简单查询操作
    * @param supplierDto
    * @return
    */
    @Override
    public Page list(SupplierDto supplierDto) {
        Supplier supplier = dozerMapper.map(supplierDto,Supplier.class);
        LambdaQueryWrapper<Supplier> queryWrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotEmpty(supplierDto.getSearchCondition())){
            queryWrapper.like(Supplier::getSupplierCode,supplierDto.getSearchCondition()).or()
                    .like(Supplier::getSupplierName,supplierDto.getSearchCondition()).or()
                    .like(Supplier::getRegNumber,supplierDto.getSearchCondition()).or()
                    .like(Supplier::getAddress,supplierDto.getSearchCondition()).or()
                    .like(Supplier::getPrimaryBusiness,supplierDto.getSearchCondition()).or()
                    .like(Supplier::getTaxpayerCode,supplierDto.getSearchCondition());
        }else{
            //根据实际业务构建具体的参数做查询
            queryWrapper.eq(ObjectUtils.isNotEmpty(supplierDto.getSupplierCode()),Supplier::getSupplierCode,supplierDto.getSupplierCode())
                    .like(ObjectUtils.isNotEmpty(supplierDto.getSupplierName()),Supplier::getSupplierName,supplierDto.getSupplierName());
        }

        Page<Supplier> page = supplierMapper.selectPage(supplierDto.getPage(),queryWrapper);
        Page<SupplierVo> pageVo = DozerUtils.mapPage(dozerMapper, page, SupplierVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param dto
    * @return
    */
    @Override
    public List<SupplierVo> lists(SupplierDto dto) {
        LambdaQueryWrapper<Supplier> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtils.isNotEmpty(dto.getSupplierCode()),Supplier::getSupplierCode,dto.getSupplierCode())
                .like(ObjectUtils.isNotEmpty(dto.getSupplierName()),Supplier::getSupplierName,dto.getSupplierName());
        List<Supplier> list=super.list(queryWrapper);
        List<SupplierVo> voList=DozerUtils.mapList(dozerMapper,list,SupplierVo.class);
        for (SupplierVo vo:voList) {
            getOther(vo);
        }
        log.debug("查询条数:"+voList.size());
        return voList;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public SupplierVo get(Long id) {
        Supplier supplier = super.getById(id);
        SupplierVo supplierVo = null;
        if(supplier !=null){
            supplierVo = dozerMapper.map(supplier,SupplierVo.class);
        }
        //获取相关数据
        getOther(supplierVo);

        log.debug("查询成功");
        return supplierVo;
    }

    /**
    * 保存实体
    * @param supplierDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(SupplierDto supplierDto) {
        Supplier supplier = dozerMapper.map(supplierDto,Supplier.class);
        boolean result = super.save(supplier);
        supplierDto.setId(supplier.getId());

        //保存相关数据
        saveOther(supplierDto);

        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param supplierDto
    * @return
    */
    @Transactional
    @Override
    @SneakyThrows
    public Boolean update(SupplierDto supplierDto) {
        Supplier supplier = dozerMapper.map(supplierDto,Supplier.class);

        //删除相关数据
        deleteOther(supplierDto);

        boolean result = super.updateById(supplier);
        supplierDto.setId(supplier.getId());

        //新增相关数据
        saveOther(supplierDto);
        List<SupplierDto> list=new ArrayList<>();
        list.add(supplierDto);
        mdmMqService.sendRocketMq(supplierTopics, MdmConstants.SUPPLIER, JSON.toJSONString(list));

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
        Supplier supplier=super.getById(id);
        SupplierDto supplierDto=dozerMapper.map(supplier,SupplierDto.class);

        deleteOther(supplierDto);

        boolean result = super.removeById(id);
        if(result){
            log.debug("删除成功");
        }
        return result;
    }

    @Override
    @SneakyThrows
    public Boolean saveTransmitAll(List<SupplierDto> dtoList) {
        //删除已有数据
        List<String> supolierCodeList=dtoList.stream().map(SupplierDto::getSupplierCode).collect(Collectors.toList());
        LambdaQueryWrapper<Supplier> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Supplier::getSupplierCode,supolierCodeList);
        List<Supplier> suppliers=super.list(queryWrapper);
        //super.remove(queryWrapper);

        LambdaQueryWrapper<SupplierBank> queryWrapperBank=new LambdaQueryWrapper<>();
        queryWrapperBank.in(SupplierBank::getSupplierCode,supolierCodeList);
        bankService.remove(queryWrapperBank);

        List<SupplierDto> list=new ArrayList<>();
        for (SupplierDto dto:dtoList) {
            //判断必填项
            if(StringUtils.isEmpty(dto.getSupplierCode())||StringUtils.isEmpty(dto.getSupplierName())){
                continue;
            }
            Supplier supplier=dozerMapper.map(dto,Supplier.class);
            supplier.setDataSourceId(dto.getId().toString());
            List<Supplier> suppliers1=suppliers.stream().filter(sup ->sup.getSupplierCode().equals(dto.getSupplierCode())).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(suppliers1)){
                super.save(supplier);
            }else {
                supplier.setId(suppliers.get(0).getId());
                super.updateById(supplier);
            }
            dto.setId(supplier.getId());
            SupplierBank bank=new SupplierBank();
            bank.setSupplierId(supplier.getId());
            bank.setSupplierCode(supplier.getSupplierCode());
            bank.setBankName(dto.getBank());
            bank.setBankAccount(dto.getBanknumber());
            bankService.save(bank);
            List<SupplierBankDto> banks=new ArrayList<>();
            banks.add(dozerMapper.map(bank,SupplierBankDto.class));
            dto.setBankList(banks);
            list.add(dto);
        }
        mdmMqService.sendRocketMq(supplierTopics, MdmConstants.SUPPLIER, JSON.toJSONString(list));
        return true;
    }

    public void saveOther(SupplierDto supplierDto){
        //新增相关数据
        List<SupplierBank> banks=new ArrayList<>();
        List<SupplierBankDto> bankDtoList=supplierDto.getBankList();
        for (SupplierBankDto bankDto:bankDtoList) {
            SupplierBank bank=dozerMapper.map(bankDto,SupplierBank.class);
            bank.setSupplierId(supplierDto.getId());
            bank.setSupplierCode(supplierDto.getSupplierCode());
            banks.add(bank);
        }
        bankService.saveBatch(banks);

        List<SupplierContacts> contactsList=new ArrayList<>();
        List<SupplierContactsDto> supplierContactsDtos=supplierDto.getContactsList();
        for (SupplierContactsDto contactsDto:supplierContactsDtos) {
            SupplierContacts contacts=dozerMapper.map(contactsDto,SupplierContacts.class);
            contacts.setSupplierId(supplierDto.getId());
            contacts.setSupplierCode(supplierDto.getSupplierCode());
            contactsList.add(contacts);
        }
        supplierContactsService.saveBatch(contactsList);

    }

    public void deleteOther(SupplierDto supplierDto){
        LambdaQueryWrapper<SupplierBank> queryWrapperBank=new LambdaQueryWrapper<>();
        queryWrapperBank.eq(SupplierBank::getSupplierCode,supplierDto.getSupplierCode());
        bankService.remove(queryWrapperBank);

        LambdaQueryWrapper<SupplierContacts> contactsLambdaQueryWrapper=new LambdaQueryWrapper<>();
        contactsLambdaQueryWrapper.eq(SupplierContacts::getSupplierCode,supplierDto.getSupplierCode());
        supplierContactsService.remove(contactsLambdaQueryWrapper);
    }

    public void getOther(SupplierVo supplierVo){
        LambdaQueryWrapper<SupplierBank> queryWrapperBank=new LambdaQueryWrapper<>();
        queryWrapperBank.eq(SupplierBank::getSupplierCode,supplierVo.getSupplierCode());
        List<SupplierBank> banks=bankService.list(queryWrapperBank);
        List<SupplierBankVo> bankVos=new ArrayList<>();
        for (SupplierBank bank:banks) {
            SupplierBankVo bankVo=dozerMapper.map(bank,SupplierBankVo.class);
            bankVos.add(bankVo);
        }
        supplierVo.setBankList(bankVos);

        LambdaQueryWrapper<SupplierContacts> contactsLambdaQueryWrapper=new LambdaQueryWrapper<>();
        contactsLambdaQueryWrapper.eq(SupplierContacts::getSupplierCode,supplierVo.getSupplierCode());
        List<SupplierContacts> contactsList=supplierContactsService.list(contactsLambdaQueryWrapper);
        List<SupplierContactsVo> contactsVoList=new ArrayList<>();
        for (SupplierContacts contacts:contactsList
        ) {
            SupplierContactsVo vo=dozerMapper.map(contacts,SupplierContactsVo.class);
            contactsVoList.add(vo);
        }
        supplierVo.setContactsList(contactsVoList);
    }

}
