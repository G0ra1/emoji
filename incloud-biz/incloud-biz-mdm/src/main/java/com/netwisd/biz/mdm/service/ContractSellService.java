package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ContractSell;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractSellDto;
import com.netwisd.biz.mdm.vo.ContractSellVo;

import java.util.List;

/**
 * @Description 销售合同 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-06 11:07:24
 */
public interface ContractSellService extends IService<ContractSell> {
    Page list(ContractSellDto contractSellDto);
    List<ContractSellVo> lists(ContractSellDto contractSellDto);
    ContractSellVo get(Long id);
    Boolean save(ContractSellDto contractSellDto);
    Boolean update(ContractSellDto contractSellDto);
    Boolean delete(Long id);
    Boolean saveList(List<ContractSellDto> dtoList);
    Boolean delList(List<ContractSellDto> dtoList);
}
