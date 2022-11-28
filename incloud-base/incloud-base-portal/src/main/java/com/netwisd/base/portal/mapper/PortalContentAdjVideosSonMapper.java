package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentAdjVideosSon;
import com.netwisd.base.portal.dto.PortalContentAdjVideosSonDto;
import com.netwisd.base.portal.vo.PortalContentAdjVideosSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description  视频类型内容发布-调整表子表   功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-06 16:15:52
 */
@Mapper
public interface PortalContentAdjVideosSonMapper extends BaseMapper<PortalContentAdjVideosSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentAdjVideosSonDto
     * @return
     */
    Page<PortalContentAdjVideosSonVo> getPageList(Page page, @Param("portalContentAdjVideosSonDto") PortalContentAdjVideosSonDto portalContentAdjVideosSonDto);
}
