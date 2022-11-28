package com.netwisd.base.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.common.mdm.dto.MdmRolePostDto;
import com.netwisd.base.common.mdm.vo.MdmRolePostVo;
import com.netwisd.base.mdm.entity.MdmRolePost;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 角色与岗位关系 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-09-18 12:19:56
 */
@Mapper
public interface MdmRolePostMapper extends BaseMapper<MdmRolePost> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param mdmRolePostDto
     * @return
     */
    Page<MdmRolePostVo> getPageList(Page page, @Param("mdmRolePostDto") MdmRolePostDto mdmRolePostDto);
}
