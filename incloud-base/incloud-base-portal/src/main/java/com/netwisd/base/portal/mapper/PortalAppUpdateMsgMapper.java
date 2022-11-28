package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalAppUpdateMsg;
import com.netwisd.base.portal.dto.PortalAppUpdateMsgDto;
import com.netwisd.base.portal.vo.PortalAppUpdateMsgVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description app安装包更新记录表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2022-01-06 10:16:32
 */
@Mapper
public interface PortalAppUpdateMsgMapper extends BaseMapper<PortalAppUpdateMsg> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalAppUpdateMsgDto
     * @return
     */
    Page<PortalAppUpdateMsgVo> getPageList(Page page, @Param("portalAppUpdateMsgDto") PortalAppUpdateMsgDto portalAppUpdateMsgDto);
}
