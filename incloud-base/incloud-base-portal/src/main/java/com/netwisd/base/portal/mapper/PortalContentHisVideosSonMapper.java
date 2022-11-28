package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentHisVideosSon;
import com.netwisd.base.portal.dto.PortalContentHisVideosSonDto;
import com.netwisd.base.portal.vo.PortalContentHisVideosSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description  视频类型内容发布-历史表子表  功能描述...
 * @author 云数网讯 cuiran@netwisd.com@netwisd.com
 * @date 2021-09-06 14:08:44
 */
@Mapper
public interface PortalContentHisVideosSonMapper extends BaseMapper<PortalContentHisVideosSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentHisVideosSonDto
     * @return
     */
    Page<PortalContentHisVideosSonVo> getPageList(Page page, @Param("portalContentHisVideosSonDto") PortalContentHisVideosSonDto portalContentHisVideosSonDto);
}
