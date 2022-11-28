package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ContractPartyc;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ContractPartycDto;
import com.netwisd.biz.mdm.vo.ContractPartycVo;
/**
 * @Description 丙方 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-14 15:34:03
 */
public interface ContractPartycService extends IService<ContractPartyc> {
    Page list(ContractPartycDto contractPartycDto);
    Page lists(ContractPartycDto contractPartycDto);
    ContractPartycVo get(Long id);
    Boolean save(ContractPartycDto contractPartycDto);
    Boolean update(ContractPartycDto contractPartycDto);
    Boolean delete(Long id);
}
