package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalAppMsg;
import com.netwisd.base.portal.dto.PortalAppMsgDto;
import com.netwisd.base.portal.vo.PortalAppMsgVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 应用市场app信息表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-12-23 15:07:50
 */
@Mapper
public interface PortalAppMsgMapper extends BaseMapper<PortalAppMsg> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalAppMsgDto
     * @return
     */
    Page<PortalAppMsgVo> getPageList(Page page, @Param("portalAppMsgDto") PortalAppMsgDto portalAppMsgDto);
}
