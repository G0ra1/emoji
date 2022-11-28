package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentLogin;
import com.netwisd.base.portal.dto.PortalContentLoginDto;
import com.netwisd.base.portal.vo.PortalContentLoginVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 登录页设置表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2021-12-27 16:36:19
 */
@Mapper
public interface PortalContentLoginMapper extends BaseMapper<PortalContentLogin> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentLoginDto
     * @return
     */
    Page<PortalContentLoginVo> getPageList(Page page, @Param("portalContentLoginDto") PortalContentLoginDto portalContentLoginDto);
}
