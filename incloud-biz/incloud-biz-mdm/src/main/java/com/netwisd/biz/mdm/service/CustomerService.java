package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.Customer;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.CustomerDto;
import com.netwisd.biz.mdm.vo.CustomerVo;
import com.netwisd.biz.mdm.vo.SupplierVo;

import java.util.List;

/**
 * @Description 客户 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 16:45:51
 */
public interface CustomerService extends IService<Customer> {
    Page list(CustomerDto customerDto);
    List<CustomerVo> lists(CustomerDto customerDto);
    CustomerVo get(Long id);
    Boolean save(CustomerDto customerDto);
    Boolean update(CustomerDto customerDto);
    Boolean delete(Long id);
    Boolean saveList(List<CustomerDto> dtos);
    Boolean delList(List<CustomerDto> dtos);
}