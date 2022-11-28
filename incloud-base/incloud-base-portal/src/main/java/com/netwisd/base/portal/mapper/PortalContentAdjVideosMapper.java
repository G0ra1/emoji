package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentAdjVideos;
import com.netwisd.base.portal.dto.PortalContentAdjVideosDto;
import com.netwisd.base.portal.vo.PortalContentAdjVideosVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description  视频类内容发布-调整表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-31 01:42:07
 */
@Mapper
public interface PortalContentAdjVideosMapper extends BaseMapper<PortalContentAdjVideos> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentAdjVideosDto
     * @return
     */
    Page<PortalContentAdjVideosVo> getPageList(Page page, @Param("portalContentAdjVideosDto") PortalContentAdjVideosDto portalContentAdjVideosDto);
    /**
     * 获取最大的bizKey  只适用于  **-201808280001这种情况的code
     *
     * @return
     */
    @Select("SELECT MAX(SUBSTRING_INDEX(biz_key,'-',-1)) AS maxBizKey FROM incloud_base_portal_content_adj_videos where DATEDIFF(create_time,now())=0")
    String getMaxBizKey();
}
