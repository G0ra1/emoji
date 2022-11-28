package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ContractPurchaseFile;
import com.netwisd.biz.mdm.dto.ContractPurchaseFileDto;
import com.netwisd.biz.mdm.vo.ContractPurchaseFileVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 采购合同附件 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-08 10:22:57
 */
@Mapper
public interface ContractPurchaseFileMapper extends BaseMapper<ContractPurchaseFile> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param contractPurchaseFileDto
     * @return
     */
    Page<ContractPurchaseFileVo> getPageList(Page page, @Param("contractPurchaseFileDto") ContractPurchaseFileDto contractPurchaseFileDto);
}
