package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentAdjFilesSon;
import com.netwisd.base.portal.dto.PortalContentAdjFilesSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjFilesSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 调整 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:23:45
 */
@Mapper
public interface PortalContentAdjFilesSonMapper extends BaseMapper<PortalContentAdjFilesSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentAdjFilesSonDto
     * @return
     */
    Page<PortalContentAdjFilesSonVo> getPageList(Page page, @Param("portalContentAdjFilesSonDto") PortalContentAdjFilesSonDto portalContentAdjFilesSonDto);
}
