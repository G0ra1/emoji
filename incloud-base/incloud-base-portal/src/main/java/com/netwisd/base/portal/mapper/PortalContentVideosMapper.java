package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentVideos;
import com.netwisd.base.portal.dto.PortalContentVideosDto;
import com.netwisd.base.portal.vo.PortalContentVideosVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description  视频类内容发布 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-23 14:08:28
 */
@Mapper
public interface PortalContentVideosMapper extends BaseMapper<PortalContentVideos> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentVideosDto
     * @return
     */
    Page<PortalContentVideosVo> getPageList(Page page, @Param("portalContentVideosDto") PortalContentVideosDto portalContentVideosDto);
    /**
     * 获取最大的bizKey  只适用于  **-201808280001这种情况的code
     *
     * @return
     */
    @Select("SELECT MAX(SUBSTRING_INDEX(biz_key,'-',-1)) AS maxBizKey FROM incloud_base_portal_content_videos where DATEDIFF(create_time,now())=0")
    String getMaxBizKey();
    /**
     * 通过主表的所属栏目+所属门户+子表发布人+子表标题查询返回page对象
     *
     * @return
     */
    List<PortalContentVideosVo> getAll(@Param("portalContentVideosDto") PortalContentVideosDto portalContentVideosDto);
}

