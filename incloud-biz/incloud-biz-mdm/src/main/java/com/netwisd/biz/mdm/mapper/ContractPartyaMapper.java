package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ContractPartya;
import com.netwisd.biz.mdm.dto.ContractPartyaDto;
import com.netwisd.biz.mdm.vo.ContractPartyaVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 甲方 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 15:34:54
 */
@Mapper
public interface ContractPartyaMapper extends BaseMapper<ContractPartya> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param contractPartyaDto
     * @return
     */
    Page<ContractPartyaVo> getPageList(Page page, @Param("contractPartyaDto") ContractPartyaDto contractPartyaDto);
}
