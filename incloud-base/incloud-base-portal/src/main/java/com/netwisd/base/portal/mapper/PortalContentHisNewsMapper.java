package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentHisNews;
import com.netwisd.base.portal.dto.PortalContentHisNewsDto;
import com.netwisd.base.portal.vo.PortalContentHisNewsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 历史 新闻通告类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-29 19:26:33
 */
@Mapper
public interface PortalContentHisNewsMapper extends BaseMapper<PortalContentHisNews> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentHisNewsDto
     * @return
     */
    Page<PortalContentHisNewsVo> getPageList(Page page, @Param("portalContentHisNewsDto") PortalContentHisNewsDto portalContentHisNewsDto);
}
