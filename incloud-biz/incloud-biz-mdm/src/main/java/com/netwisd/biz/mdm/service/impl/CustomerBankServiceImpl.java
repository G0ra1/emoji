package com.netwisd.biz.mdm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.netwisd.biz.mdm.entity.CustomerBank;
import com.netwisd.biz.mdm.mapper.CustomerBankMapper;
import com.netwisd.biz.mdm.service.CustomerBankService;
import org.springframework.stereotype.Service;
import com.github.dozermapper.core.Mapper;
import com.netwisd.common.core.util.DozerUtils;
import com.netwisd.biz.mdm.dto.CustomerBankDto;
import com.netwisd.biz.mdm.vo.CustomerBankVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
/**
 * @Description 客户银行账号 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 17:06:29
 */
@Service
@Slf4j
public class CustomerBankServiceImpl extends ServiceImpl<CustomerBankMapper, CustomerBank> implements CustomerBankService {
    @Autowired
    private Mapper dozerMapper;

    @Autowired
    private CustomerBankMapper customerBankMapper;

    /**
    * 单表简单查询操作
    * @param customerBankDto
    * @return
    */
    @Override
    public Page list(CustomerBankDto customerBankDto) {
        LambdaQueryWrapper<CustomerBank> queryWrapper = new LambdaQueryWrapper<>();
        //根据实际业务构建具体的参数做查询

        Page<CustomerBank> page = customerBankMapper.selectPage(customerBankDto.getPage(),queryWrapper);
        Page<CustomerBankVo> pageVo = DozerUtils.mapPage(dozerMapper, page, CustomerBankVo.class);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 自定义查询操作
    * @param customerBankDto
    * @return
    */
    @Override
    public Page lists(CustomerBankDto customerBankDto) {
        Page<CustomerBankVo> pageVo = customerBankMapper.getPageList(customerBankDto.getPage(),customerBankDto);
        log.debug("查询条数:"+pageVo.getTotal());
        return pageVo;
    }

    /**
    * 通过ID查询实体
    * @param id
    * @return
    */
    @Override
    public CustomerBankVo get(Long id) {
        CustomerBank customerBank = super.getById(id);
        CustomerBankVo customerBankVo = null;
        if(customerBank !=null){
            customerBankVo = dozerMapper.map(customerBank,CustomerBankVo.class);
        }
        log.debug("查询成功");
        return customerBankVo;
    }

    /**
    * 保存实体
    * @param customerBankDto
    * @return
    */
    @Transactional
    @Override
    public Boolean save(CustomerBankDto customerBankDto) {
        CustomerBank customerBank = dozerMapper.map(customerBankDto,CustomerBank.class);
        boolean result = super.save(customerBank);
        if(result){
            log.debug("保存成功");
        }
        return result;
    }

    /**
    * 修改实体
    * @param customerBankDto
    * @return
    */
    @Transactional
    @Override
    public Boolean update(CustomerBankDto customerBankDto) {
        customerBankDto.setUpdateTime(LocalDateTime.now());
        CustomerBank customerBank = dozerMapper.map(customerBankDto,CustomerBank.class);
        boolean result = super.updateById(customerBank);
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
