package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.mdm.entity.MdmDutyGradeDict;
import com.netwisd.base.mdm.dto.MdmDutyGradeDictDto;
import com.netwisd.base.mdm.vo.MdmDutyGradeDictVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description $职务 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-09-27 10:45:35
 */
@Mapper
public interface MdmDutyGradeDictMapper extends BaseMapper<MdmDutyGradeDict> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmDutyGradeDictDto
     * @return
     */
    Page<MdmDutyGradeDictVo> getPageList(Page page, @Param("mdmDutyGradeDictDto") MdmDutyGradeDictDto mdmDutyGradeDictDto);
}
