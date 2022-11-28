package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentLoginCorporateSon;
import com.netwisd.base.portal.dto.PortalContentLoginCorporateSonDto;
import com.netwisd.base.portal.vo.PortalContentLoginCorporateSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 登录页-子表（企业文化轮播图） 功能描述...
 * @author 云数网讯 cuiran@netwisd.com
 * @date 2021-12-27 17:22:50
 */
@Mapper
public interface PortalContentLoginCorporateSonMapper extends BaseMapper<PortalContentLoginCorporateSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentLoginCorporateSonDto
     * @return
     */
    Page<PortalContentLoginCorporateSonVo> getPageList(Page page, @Param("portalContentLoginCorporateSonDto") PortalContentLoginCorporateSonDto portalContentLoginCorporateSonDto);
}
