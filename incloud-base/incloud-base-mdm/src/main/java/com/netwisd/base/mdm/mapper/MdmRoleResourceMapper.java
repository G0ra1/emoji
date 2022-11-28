package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.mdm.entity.MdmRoleResource;
import com.netwisd.base.mdm.dto.MdmRoleResourceDto;
import com.netwisd.base.mdm.vo.MdmRoleResourceVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 角色与资源关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-17 17:27:09
 */
@Mapper
public interface MdmRoleResourceMapper extends BaseMapper<MdmRoleResource> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmRoleResourceDto
     * @return
     */
    Page<MdmRoleResourceVo> getPageList(Page page, @Param("mdmRoleResourceDto") MdmRoleResourceDto mdmRoleResourceDto);
}
