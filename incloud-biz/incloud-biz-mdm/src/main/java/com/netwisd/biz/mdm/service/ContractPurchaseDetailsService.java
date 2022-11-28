package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ContractPurchaseDetails;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractPurchaseDetailsDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseDetailsVo;

import java.util.List;

/**
 * @Description 采购合同清单 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:49:25
 */
public interface ContractPurchaseDetailsService extends IService<ContractPurchaseDetails> {
    Page list(ContractPurchaseDetailsDto contractPurchaseDetailsDto);
    List<ContractPurchaseDetailsVo> lists(ContractPurchaseDetailsDto contractPurchaseDetailsDto);
    ContractPurchaseDetailsVo get(Long id);
    Boolean save(ContractPurchaseDetailsDto contractPurchaseDetailsDto);
    Boolean update(ContractPurchaseDetailsDto contractPurchaseDetailsDto);
    Boolean delete(Long id);
    public void saveTransmitAll(List<ContractPurchaseDetailsDto> list);
}
