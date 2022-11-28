package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ContractPartya;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractPartyaDto;
import com.netwisd.biz.mdm.vo.ContractPartyaVo;
/**
 * @Description 甲方 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 15:34:54
 */
public interface ContractPartyaService extends IService<ContractPartya> {
    Page list(ContractPartyaDto contractPartyaDto);
    Page lists(ContractPartyaDto contractPartyaDto);
    ContractPartyaVo get(Long id);
    Boolean save(ContractPartyaDto contractPartyaDto);
    Boolean update(ContractPartyaDto contractPartyaDto);
    Boolean delete(Long id);
}
