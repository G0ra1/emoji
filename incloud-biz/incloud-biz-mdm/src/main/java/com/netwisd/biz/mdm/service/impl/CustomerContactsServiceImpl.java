package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.CustomerContacts;
import com.netwisd.biz.mdm.mapper.CustomerContactsMapper;
import com.netwisd.biz.mdm.service.CustomerContactsService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.CustomerContactsDto;
import com.netwisd.biz.mdm.vo.CustomerContactsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 客户联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 17:07:37
 */
@Service
@Slf4j
public class CustomerContactsServiceImpl extends ServiceImpl<CustomerContactsMapper, CustomerContacts> implements CustomerContactsService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private CustomerContactsMapper customerContactsMapper;

    /**
    * 单表简单查询操作
    * @param customerContactsDto
    * @return
    */
    @Override
    public Page list(CustomerContactsDto customerContactsDto) {
        LambdaQueryWrapper<CustomerContacts> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<CustomerContacts> page = customerContactsMapper.selectPage(customerContactsDto.getPage(),queryWrapper);
        Page<CustomerContactsVo> pageVo = DozerUtils.mapPage(dozerMapper, page, CustomerContactsVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param customerContactsDto
    * @return
    */
    @Override
    public Page lists(CustomerContactsDto customerContactsDto) {
        Page<CustomerContactsVo> pageVo = customerContactsMapper.getPageList(customerContactsDto.getPage(),customerContactsDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public CustomerContactsVo get(Long id) {
        CustomerContacts customerContacts = super.getById(id);
        CustomerContactsVo customerContactsVo = null;
        if(customerContacts !=null){
            customerContactsVo = dozerMapper.map(customerContacts,CustomerContactsVo.class);
        }
        log.debug("查询成功");
        return customerContactsVo;
    }

    /**
    * 保存实体
    * @param customerContactsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(CustomerContactsDto customerContactsDto) {
        CustomerContacts customerContacts = dozerMapper.map(customerContactsDto,CustomerContacts.class);
        boolean result = super.save(customerContacts);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param customerContactsDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(CustomerContactsDto customerContactsDto) {
        customerContactsDto.setUpdateTime(LocalDateTime.now());
        CustomerContacts customerContacts = dozerMapper.map(customerContactsDto,CustomerContacts.class);
        boolean result = super.updateById(customerContacts);
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
        if(result){
            log.debug("删除成功");
        }
        return result;
    }
}
