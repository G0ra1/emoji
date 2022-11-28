package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ContractPartycContact;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractPartycContactDto;
import com.netwisd.biz.mdm.vo.ContractPartycContactVo;
/**
 * @Description 丙方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:40:00
 */
public interface ContractPartycContactService extends IService<ContractPartycContact> {
    Page list(ContractPartycContactDto contractPartycContactDto);
    Page lists(ContractPartycContactDto contractPartycContactDto);
    ContractPartycContactVo get(Long id);
    Boolean save(ContractPartycContactDto contractPartycContactDto);
    Boolean update(ContractPartycContactDto contractPartycContactDto);
    Boolean delete(Long id);
}
