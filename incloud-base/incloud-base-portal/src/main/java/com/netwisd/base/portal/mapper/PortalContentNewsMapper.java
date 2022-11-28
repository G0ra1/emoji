package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentNews;
import com.netwisd.base.portal.dto.PortalContentNewsDto;
import com.netwisd.base.portal.vo.PortalContentNewsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Description 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-16 17:58:47
 */
@Mapper
public interface PortalContentNewsMapper extends BaseMapper<PortalContentNews> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentNewsDto
     * @return
     */
    Page<PortalContentNewsVo> getPageList(Page page, @Param("portalContentNewsDto") PortalContentNewsDto portalContentNewsDto);

    /**
     * 获取最大的bizKey  只适用于  **-201808280001这种情况的code
     *
     * @return
     */
    @Select("SELECT MAX(SUBSTRING_INDEX(biz_key,'-',-1)) AS maxBizKey FROM incloud_base_portal_content_news where DATEDIFF(create_time,now())=0")
    String getMaxBizKey();

    List<Long> getRepeatCreateUserId();
}
