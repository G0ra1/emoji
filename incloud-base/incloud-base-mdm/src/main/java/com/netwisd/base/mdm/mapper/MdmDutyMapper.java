package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.mdm.dto.MdmDutyDto;
import com.netwisd.base.mdm.entity.MdmDuty;
import com.netwisd.base.common.mdm.vo.MdmDutyVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Mapper
public interface MdmDutyMapper extends BaseMapper<MdmDuty> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmDutyDto
     * @return
     */
    Page<MdmDutyVo> getPageList(Page page, @Param("mdmDutyDto") MdmDutyDto mdmDutyDto);

    /**
     * 分页查
     * @param page
     * @param mdmDutyDto
     * @return
     */
    Page<MdmDutyVo> getList(Page page, @Param("mdmDutyDto") MdmDutyDto mdmDutyDto);
}
