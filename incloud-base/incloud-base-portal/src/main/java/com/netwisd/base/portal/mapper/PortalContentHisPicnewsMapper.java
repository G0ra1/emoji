package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentHisPicnews;
import com.netwisd.base.portal.dto.PortalContentHisPicnewsDto;
import com.netwisd.base.portal.vo.PortalContentHisPicnewsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 历史 图片新闻类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-31 04:24:33
 */
@Mapper
public interface PortalContentHisPicnewsMapper extends BaseMapper<PortalContentHisPicnews> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentHisPicnewsDto
     * @return
     */
    Page<PortalContentHisPicnewsVo> getPageList(Page page, @Param("portalContentHisPicnewsDto") PortalContentHisPicnewsDto portalContentHisPicnewsDto);
}
