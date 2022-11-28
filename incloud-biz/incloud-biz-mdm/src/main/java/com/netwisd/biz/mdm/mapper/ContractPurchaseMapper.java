package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ContractPurchase;
import com.netwisd.biz.mdm.dto.ContractPurchaseDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 采购合同 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-26 14:33:08
 */
@Mapper
public interface ContractPurchaseMapper extends BaseMapper<ContractPurchase> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param contractPurchaseDto
     * @return
     */
    Page<ContractPurchaseVo> getPageList(Page page, @Param("contractPurchaseDto") ContractPurchaseDto contractPurchaseDto);
}
