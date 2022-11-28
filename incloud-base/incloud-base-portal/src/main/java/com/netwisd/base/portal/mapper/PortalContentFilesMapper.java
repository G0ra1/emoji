package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentFiles;
import com.netwisd.base.portal.dto.PortalContentFilesDto;
import com.netwisd.base.portal.vo.PortalContentFilesVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-18 14:46:53
 */
@Mapper
public interface PortalContentFilesMapper extends BaseMapper<PortalContentFiles> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentFilesDto
     * @return
     */
    Page<PortalContentFilesVo> getPageList(Page page, @Param("portalContentFilesDto") PortalContentFilesDto portalContentFilesDto);

    /**
     * 获取最大的bizKey  只适用于  **-201808280001这种情况的code
     *
     * @return
     */
    @Select("SELECT MAX(SUBSTRING_INDEX(biz_key,'-',-1)) AS maxBizKey FROM incloud_base_portal_content_files where DATEDIFF(create_time,now())=0")
    String getMaxBizKey();
}
