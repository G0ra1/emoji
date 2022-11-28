package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.mdm.dto.MdmRoleDto;
import com.netwisd.base.common.mdm.vo.MdmRoleVo;
import com.netwisd.base.mdm.entity.MdmRole;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 角色 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-03 18:05:58
 */
@Mapper
public interface MdmRoleMapper extends BaseMapper<MdmRole> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmRoleDto
     * @return
     */
    Page<MdmRoleVo> getPageList(Page page, @Param("mdmRoleDto") MdmRoleDto mdmRoleDto);
}
