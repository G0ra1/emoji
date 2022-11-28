package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ContractPurchaseFile;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractPurchaseFileDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseFileVo;
/**
 * @Description 采购合同附件 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-08 10:22:57
 */
public interface ContractPurchaseFileService extends IService<ContractPurchaseFile> {
    Page list(ContractPurchaseFileDto contractPurchaseFileDto);
    Page lists(ContractPurchaseFileDto contractPurchaseFileDto);
    ContractPurchaseFileVo get(Long id);
    Boolean save(ContractPurchaseFileDto contractPurchaseFileDto);
    Boolean update(ContractPurchaseFileDto contractPurchaseFileDto);
    Boolean delete(Long id);
}
