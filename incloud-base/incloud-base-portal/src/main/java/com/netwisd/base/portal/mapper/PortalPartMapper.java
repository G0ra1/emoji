package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalPart;
import com.netwisd.base.portal.dto.PortalPartDto;
import com.netwisd.base.portal.vo.PortalPartVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 栏目管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-13 19:27:46
 */
@Mapper
public interface PortalPartMapper extends BaseMapper<PortalPart> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalPartDto
     * @return
     */
    Page<PortalPartVo> getPageList(Page page, @Param("portalPartDto") PortalPartDto portalPartDto);
}
