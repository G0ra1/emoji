package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ContractPartyb;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractPartybDto;
import com.netwisd.biz.mdm.vo.ContractPartybVo;
/**
 * @Description 合同乙方 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-30 11:09:06
 */
public interface ContractPartybService extends IService<ContractPartyb> {
    Page list(ContractPartybDto contractPartybDto);
    Page lists(ContractPartybDto contractPartybDto);
    ContractPartybVo get(Long id);
    Boolean save(ContractPartybDto contractPartybDto);
    Boolean update(ContractPartybDto contractPartybDto);
    Boolean delete(Long id);
}
