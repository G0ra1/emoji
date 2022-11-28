package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ContractPartyaContact;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractPartyaContactDto;
import com.netwisd.biz.mdm.vo.ContractPartyaContactVo;
/**
 * @Description 甲方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:31:37
 */
public interface ContractPartyaContactService extends IService<ContractPartyaContact> {
    Page list(ContractPartyaContactDto contractPartyaContactDto);
    Page lists(ContractPartyaContactDto contractPartyaContactDto);
    ContractPartyaContactVo get(Long id);
    Boolean save(ContractPartyaContactDto contractPartyaContactDto);
    Boolean update(ContractPartyaContactDto contractPartyaContactDto);
    Boolean delete(Long id);
}
