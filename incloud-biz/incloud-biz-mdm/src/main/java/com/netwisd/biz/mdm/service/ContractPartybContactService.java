package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ContractPartybContact;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractPartybContactDto;
import com.netwisd.biz.mdm.vo.ContractPartybContactVo;
/**
 * @Description 乙方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:39:08
 */
public interface ContractPartybContactService extends IService<ContractPartybContact> {
    Page list(ContractPartybContactDto contractPartybContactDto);
    Page lists(ContractPartybContactDto contractPartybContactDto);
    ContractPartybContactVo get(Long id);
    Boolean save(ContractPartybContactDto contractPartybContactDto);
    Boolean update(ContractPartybContactDto contractPartybContactDto);
    Boolean delete(Long id);
}
