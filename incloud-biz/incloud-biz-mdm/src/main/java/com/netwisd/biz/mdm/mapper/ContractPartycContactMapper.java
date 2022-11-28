package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ContractPartycContact;
import com.netwisd.biz.mdm.dto.ContractPartycContactDto;
import com.netwisd.biz.mdm.vo.ContractPartycContactVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 丙方联系人 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com@netwisd.com
 * @date 2021-11-16 14:40:00
 */
@Mapper
public interface ContractPartycContactMapper extends BaseMapper<ContractPartycContact> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param contractPartycContactDto
     * @return
     */
    Page<ContractPartycContactVo> getPageList(Page page, @Param("contractPartycContactDto") ContractPartycContactDto contractPartycContactDto);
}
