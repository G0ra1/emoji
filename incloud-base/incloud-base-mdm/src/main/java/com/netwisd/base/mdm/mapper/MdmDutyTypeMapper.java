package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.mdm.entity.MdmDutyType;
import com.netwisd.base.mdm.dto.MdmDutyTypeDto;
import com.netwisd.base.mdm.vo.MdmDutyTypeVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 职务分类 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-27 16:00:30
 */
@Mapper
public interface MdmDutyTypeMapper extends BaseMapper<MdmDutyType> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmDutyTypeDto
     * @return
     */
    Page<MdmDutyTypeVo> getPageList(Page page, @Param("mdmDutyTypeDto") MdmDutyTypeDto mdmDutyTypeDto);
}
