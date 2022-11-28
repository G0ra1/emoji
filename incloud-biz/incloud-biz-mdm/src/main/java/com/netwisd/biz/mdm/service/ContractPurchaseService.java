package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.dto.ContractPurchaseThransmitDto;
import com.netwisd.biz.mdm.entity.ContractPurchase;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractPurchaseDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseVo;

import java.util.List;

/**
 * @Description 采购合同 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:33:08
 */
public interface ContractPurchaseService extends IService<ContractPurchase> {
    Page list(ContractPurchaseDto contractPurchaseDto);
    List<ContractPurchaseVo> lists(ContractPurchaseDto contractPurchaseDto);
    ContractPurchaseVo get(Long id);
    Boolean save(ContractPurchaseDto contractPurchaseDto);
    Boolean update(ContractPurchaseDto contractPurchaseDto);
    Boolean delete(Long id);
    Boolean saveTransmitAll(List<ContractPurchaseDto> list);
    Page getList(ContractPurchaseDto contractPurchaseDto);
}
