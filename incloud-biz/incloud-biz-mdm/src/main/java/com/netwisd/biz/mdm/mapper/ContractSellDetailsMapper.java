package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ContractSellDetails;
import com.netwisd.biz.mdm.dto.ContractSellDetailsDto;
import com.netwisd.biz.mdm.vo.ContractSellDetailsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 销售合同清单 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-06 11:09:22
 */
@Mapper
public interface ContractSellDetailsMapper extends BaseMapper<ContractSellDetails> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param contractSellDetailsDto
     * @return
     */
    Page<ContractSellDetailsVo> getPageList(Page page, @Param("contractSellDetailsDto") ContractSellDetailsDto contractSellDetailsDto);
}
