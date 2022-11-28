package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.CustomerContacts;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.CustomerContactsDto;
import com.netwisd.biz.mdm.vo.CustomerContactsVo;
/**
 * @Description 客户联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-03 17:07:37
 */
public interface CustomerContactsService extends IService<CustomerContacts> {
    Page list(CustomerContactsDto customerContactsDto);
    Page lists(CustomerContactsDto customerContactsDto);
    CustomerContactsVo get(Long id);
    Boolean save(CustomerContactsDto customerContactsDto);
    Boolean update(CustomerContactsDto customerContactsDto);
    Boolean delete(Long id);
}
