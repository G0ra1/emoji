package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentAdjFiles;
import com.netwisd.base.portal.dto.PortalContentAdjFilesDto;
import com.netwisd.base.portal.vo.PortalContentAdjFilesVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description 调整 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:20:54
 */
@Mapper
public interface PortalContentAdjFilesMapper extends BaseMapper<PortalContentAdjFiles> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentAdjFilesDto
     * @return
     */
    Page<PortalContentAdjFilesVo> getPageList(Page page, @Param("portalContentAdjFilesDto") PortalContentAdjFilesDto portalContentAdjFilesDto);

    /**
     * 获取最大的bizKey  只适用于  **-201808280001这种情况的code
     *
     * @return
     */
    @Select("SELECT MAX(SUBSTRING_INDEX(biz_key,'-',-1)) AS maxBizKey FROM incloud_base_portal_content_adj_files where DATEDIFF(create_time,now())=0")
    String getMaxBizKey();
}
