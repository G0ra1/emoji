package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ContractPurchaseExecution;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractPurchaseExecutionDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseExecutionVo;
/**
 * @Description 执行范围 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-02 14:38:36
 */
public interface ContractPurchaseExecutionService extends IService<ContractPurchaseExecution> {
    Page list(ContractPurchaseExecutionDto contractPurchaseExecutionDto);
    Page lists(ContractPurchaseExecutionDto contractPurchaseExecutionDto);
    ContractPurchaseExecutionVo get(Long id);
    Boolean save(ContractPurchaseExecutionDto contractPurchaseExecutionDto);
    Boolean update(ContractPurchaseExecutionDto contractPurchaseExecutionDto);
    Boolean delete(Long id);
}
