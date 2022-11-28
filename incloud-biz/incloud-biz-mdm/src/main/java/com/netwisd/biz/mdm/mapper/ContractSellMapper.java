package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ContractSell;
import com.netwisd.biz.mdm.dto.ContractSellDto;
import com.netwisd.biz.mdm.vo.ContractSellVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 销售合同 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-06 11:07:24
 */
@Mapper
public interface ContractSellMapper extends BaseMapper<ContractSell> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param contractSellDto
     * @return
     */
    Page<ContractSellVo> getPageList(Page page, @Param("contractSellDto") ContractSellDto contractSellDto);

    List<ContractSellVo> outList();

}
