package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ContractPartyc;
import com.netwisd.biz.mdm.dto.ContractPartycDto;
import com.netwisd.biz.mdm.vo.ContractPartycVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 丙方 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-09-14 15:34:03
 */
@Mapper
public interface ContractPartycMapper extends BaseMapper<ContractPartyc> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param contractPartycDto
     * @return
     */
    Page<ContractPartycVo> getPageList(Page page, @Param("contractPartycDto") ContractPartycDto contractPartycDto);
}
