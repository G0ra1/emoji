package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalPortal;
import com.netwisd.base.portal.dto.PortalPortalDto;
import com.netwisd.base.portal.vo.PortalPortalVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 门户维护 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-11 09:50:22
 */
@Mapper
public interface PortalPortalMapper extends BaseMapper<PortalPortal> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalPortalDto
     * @return
     */
    Page<PortalPortalVo> getPageList(Page page, @Param("portalPortalDto") PortalPortalDto portalPortalDto);
}
