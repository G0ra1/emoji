package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.dto.PortalContentBannersDto;
import com.netwisd.base.portal.entity.PortalContentBanners;
import com.netwisd.base.portal.vo.PortalContentBannersVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description    banner类内容发布 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-19 10:17:19
 */
@Mapper
public interface PortalContentBannersMapper extends BaseMapper<PortalContentBanners> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentBannersDto
     * @return
     */
    Page<PortalContentBannersVo> getPageList(Page page, @Param("portalContentBannersDto") PortalContentBannersDto portalContentBannersDto);
    /**
     * 获取最大的bizKey  只适用于  **-201808280001这种情况的code
     *
     * @return
     */
    @Select("SELECT MAX(SUBSTRING_INDEX(biz_key,'-',-1)) AS maxBizKey FROM incloud_base_portal_content_banners where DATEDIFF(create_time,now())=0")
    String getMaxBizKey();
}
