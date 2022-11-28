package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ContractPartybContact;
import com.netwisd.biz.mdm.dto.ContractPartybContactDto;
import com.netwisd.biz.mdm.vo.ContractPartybContactVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 乙方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:39:08
 */
@Mapper
public interface ContractPartybContactMapper extends BaseMapper<ContractPartybContact> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param contractPartybContactDto
     * @return
     */
    Page<ContractPartybContactVo> getPageList(Page page, @Param("contractPartybContactDto") ContractPartybContactDto contractPartybContactDto);
}
