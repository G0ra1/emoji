package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ContractPartyb;
import com.netwisd.biz.mdm.dto.ContractPartybDto;
import com.netwisd.biz.mdm.vo.ContractPartybVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 合同乙方 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-30 11:09:06
 */
@Mapper
public interface ContractPartybMapper extends BaseMapper<ContractPartyb> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param contractPartybDto
     * @return
     */
    Page<ContractPartybVo> getPageList(Page page, @Param("contractPartybDto") ContractPartybDto contractPartybDto);
}
