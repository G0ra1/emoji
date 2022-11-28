package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalPartType;
import com.netwisd.base.portal.dto.PortalPartTypeDto;
import com.netwisd.base.portal.vo.PortalPartTypeVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 栏目类型字典 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-12 17:20:18
 */
@Mapper
public interface PortalPartTypeMapper extends BaseMapper<PortalPartType> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalPartTypeDto
     * @return
     */
    Page<PortalPartTypeVo> getPageList(Page page, @Param("portalPartTypeDto") PortalPartTypeDto portalPartTypeDto);
}
