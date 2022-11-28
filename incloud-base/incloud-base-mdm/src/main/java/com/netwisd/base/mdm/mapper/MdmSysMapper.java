package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.mdm.vo.MdmSysVo;
import com.netwisd.base.mdm.entity.MdmSys;
import com.netwisd.base.mdm.dto.MdmSysDto;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 11:00:47
 */
@Mapper
public interface MdmSysMapper extends BaseMapper<MdmSys> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmSysDto
     * @return
     */
    Page<MdmSysVo> getPageList(Page page, @Param("mdmSysDto") MdmSysDto mdmSysDto);
}
