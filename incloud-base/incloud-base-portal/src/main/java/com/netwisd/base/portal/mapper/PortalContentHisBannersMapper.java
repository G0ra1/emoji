package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentHisBanners;
import com.netwisd.base.portal.dto.PortalContentHisBannersDto;
import com.netwisd.base.portal.vo.PortalContentHisBannersVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description banner类内容-历史表 功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-08-30 03:09:59
 */
@Mapper
public interface PortalContentHisBannersMapper extends BaseMapper<PortalContentHisBanners> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentHisBannersDto
     * @return
     */
    Page<PortalContentHisBannersVo> getPageList(Page page, @Param("portalContentHisBannersDto") PortalContentHisBannersDto portalContentHisBannersDto);
}
