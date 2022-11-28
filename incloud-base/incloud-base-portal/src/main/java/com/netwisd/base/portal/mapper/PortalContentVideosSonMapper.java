package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentVideosSon;
import com.netwisd.base.portal.dto.PortalContentVideosSonDto;
import com.netwisd.base.portal.vo.PortalContentVideosSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description  视频类型内容发布子表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-05 21:48:10
 */
@Mapper
public interface PortalContentVideosSonMapper extends BaseMapper<PortalContentVideosSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentVideosSonDto
     * @return
     */
    Page<PortalContentVideosSonVo> getPageList(Page page, @Param("portalContentVideosSonDto") PortalContentVideosSonDto portalContentVideosSonDto);
}
