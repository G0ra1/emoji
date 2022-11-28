package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.mdm.entity.MdmRoleSys;
import com.netwisd.base.mdm.dto.MdmRoleSysDto;
import com.netwisd.base.mdm.vo.MdmRoleSysVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 角色对应的功能权限子系统 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-23 19:12:14
 */
@Mapper
public interface MdmRoleSysMapper extends BaseMapper<MdmRoleSys> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmRoleSysDto
     * @return
     */
    Page<MdmRoleSysVo> getPageList(Page page, @Param("mdmRoleSysDto") MdmRoleSysDto mdmRoleSysDto);
}
