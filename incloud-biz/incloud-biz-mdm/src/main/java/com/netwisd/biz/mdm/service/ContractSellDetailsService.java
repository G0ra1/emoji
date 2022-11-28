package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ContractSellDetails;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractSellDetailsDto;
import com.netwisd.biz.mdm.vo.ContractSellDetailsVo;
/**
 * @Description 销售合同清单 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-06 11:09:22
 */
public interface ContractSellDetailsService extends IService<ContractSellDetails> {
    Page list(ContractSellDetailsDto contractSellDetailsDto);
    Page lists(ContractSellDetailsDto contractSellDetailsDto);
    ContractSellDetailsVo get(Long id);
    Boolean save(ContractSellDetailsDto contractSellDetailsDto);
    Boolean update(ContractSellDetailsDto contractSellDetailsDto);
    Boolean delete(Long id);
}
