package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalDs;
import com.netwisd.base.portal.dto.PortalDsDto;
import com.netwisd.base.portal.vo.PortalDsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 数据源管理 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-10 19:25:49
 */
@Mapper
public interface PortalDsMapper extends BaseMapper<PortalDs> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalDsDto
     * @return
     */
    Page<PortalDsVo> getPageList(Page page, @Param("portalDsDto") PortalDsDto portalDsDto);
}
