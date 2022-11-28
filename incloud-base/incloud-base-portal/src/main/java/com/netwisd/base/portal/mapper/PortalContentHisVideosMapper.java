package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentHisVideos;
import com.netwisd.base.portal.dto.PortalContentHisVideosDto;
import com.netwisd.base.portal.vo.PortalContentHisVideosVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description  视频类内容发布-历史表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-31 02:45:42
 */
@Mapper
public interface PortalContentHisVideosMapper extends BaseMapper<PortalContentHisVideos> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentHisVideosDto
     * @return
     */
    Page<PortalContentHisVideosVo> getPageList(Page page, @Param("portalContentHisVideosDto") PortalContentHisVideosDto portalContentHisVideosDto);
}
