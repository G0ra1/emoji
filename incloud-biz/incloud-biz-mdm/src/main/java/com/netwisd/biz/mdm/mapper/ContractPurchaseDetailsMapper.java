package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ContractPurchaseDetails;
import com.netwisd.biz.mdm.dto.ContractPurchaseDetailsDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseDetailsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 采购合同清单 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:49:25
 */
@Mapper
public interface ContractPurchaseDetailsMapper extends BaseMapper<ContractPurchaseDetails> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param contractPurchaseDetailsDto
     * @return
     */
    Page<ContractPurchaseDetailsVo> getPageList(Page page, @Param("contractPurchaseDetailsDto") ContractPurchaseDetailsDto contractPurchaseDetailsDto);
}
