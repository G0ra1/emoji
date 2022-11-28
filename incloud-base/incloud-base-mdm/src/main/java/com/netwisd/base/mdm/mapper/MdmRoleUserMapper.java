package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.mdm.dto.MdmRoleUserDto;
import com.netwisd.base.common.mdm.vo.MdmRoleUserVo;
import com.netwisd.base.mdm.entity.MdmRoleUser;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 角色与用户关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:44:05
 */
@Mapper
public interface MdmRoleUserMapper extends BaseMapper<MdmRoleUser> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmRoleUserDto
     * @return
     */
    Page<MdmRoleUserVo> getPageList(Page page, @Param("mdmRoleUserDto") MdmRoleUserDto mdmRoleUserDto);
}
