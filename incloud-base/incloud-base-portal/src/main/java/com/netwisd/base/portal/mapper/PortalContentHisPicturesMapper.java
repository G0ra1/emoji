package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentHisPictures;
import com.netwisd.base.portal.dto.PortalContentHisPicturesDto;
import com.netwisd.base.portal.vo.PortalContentHisPicturesVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 历史 图片轮播类内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-03 14:14:36
 */
@Mapper
public interface PortalContentHisPicturesMapper extends BaseMapper<PortalContentHisPictures> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentHisPicturesDto
     * @return
     */
    Page<PortalContentHisPicturesVo> getPageList(Page page, @Param("portalContentHisPicturesDto") PortalContentHisPicturesDto portalContentHisPicturesDto);
}
