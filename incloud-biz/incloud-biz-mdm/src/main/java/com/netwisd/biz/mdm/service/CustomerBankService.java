package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.CustomerBank;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.CustomerBankDto;
import com.netwisd.biz.mdm.vo.CustomerBankVo;
/**
 * @Description 客户银行账号 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 17:06:29
 */
public interface CustomerBankService extends IService<CustomerBank> {
    Page list(CustomerBankDto customerBankDto);
    Page lists(CustomerBankDto customerBankDto);
    CustomerBankVo get(Long id);
    Boolean save(CustomerBankDto customerBankDto);
    Boolean update(CustomerBankDto customerBankDto);
    Boolean delete(Long id);
}
