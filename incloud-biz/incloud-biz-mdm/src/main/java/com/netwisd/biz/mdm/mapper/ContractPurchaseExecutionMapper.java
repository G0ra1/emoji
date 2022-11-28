package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ContractPurchaseExecution;
import com.netwisd.biz.mdm.dto.ContractPurchaseExecutionDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseExecutionVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 执行范围 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-02 14:38:36
 */
@Mapper
public interface ContractPurchaseExecutionMapper extends BaseMapper<ContractPurchaseExecution> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param contractPurchaseExecutionDto
     * @return
     */
    Page<ContractPurchaseExecutionVo> getPageList(Page page, @Param("contractPurchaseExecutionDto") ContractPurchaseExecutionDto contractPurchaseExecutionDto);
}
