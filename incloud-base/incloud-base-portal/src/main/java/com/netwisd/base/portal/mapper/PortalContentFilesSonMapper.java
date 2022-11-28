package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentFilesSon;
import com.netwisd.base.portal.dto.PortalContentFilesSonDto;
import com.netwisd.base.portal.vo.PortalContentFilesSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 10:20:31
 */
@Mapper
public interface PortalContentFilesSonMapper extends BaseMapper<PortalContentFilesSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentFilesSonDto
     * @return
     */
    Page<PortalContentFilesSonVo> getPageList(Page page, @Param("portalContentFilesSonDto") PortalContentFilesSonDto portalContentFilesSonDto);
}
