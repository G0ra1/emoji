package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentAdjNews;
import com.netwisd.base.portal.dto.PortalContentAdjNewsDto;
import com.netwisd.base.portal.vo.PortalContentAdjNewsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description 调整 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-27 15:26:39
 */
@Mapper
public interface PortalContentAdjNewsMapper extends BaseMapper<PortalContentAdjNews> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentAdjNewsDto
     * @return
     */
    Page<PortalContentAdjNewsVo> getPageList(Page page, @Param("portalContentAdjNewsDto") PortalContentAdjNewsDto portalContentAdjNewsDto);

    /**
     * 获取最大的bizKey  只适用于  **-201808280001这种情况的code
     *
     * @return
     */
    @Select("SELECT MAX(SUBSTRING_INDEX(biz_key,'-',-1)) AS maxBizKey FROM incloud_base_portal_content_adj_news where DATEDIFF(create_time,now())=0")
    String getMaxBizKey();
}
