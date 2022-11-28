package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ContractPartyaContact;
import com.netwisd.biz.mdm.dto.ContractPartyaContactDto;
import com.netwisd.biz.mdm.vo.ContractPartyaContactVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 甲方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:31:37
 */
@Mapper
public interface ContractPartyaContactMapper extends BaseMapper<ContractPartyaContact> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param contractPartyaContactDto
     * @return
     */
    Page<ContractPartyaContactVo> getPageList(Page page, @Param("contractPartyaContactDto") ContractPartyaContactDto contractPartyaContactDto);
}
