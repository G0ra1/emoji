package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalPartDs;
import com.netwisd.base.portal.dto.PortalPartDsDto;
import com.netwisd.base.portal.vo.PortalPartDsVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 栏目管理 功能描述...
 * @author 云数网讯 limengzheng@netwisd.com
 * @date 2021-08-13 20:07:37
 */
@Mapper
public interface PortalPartDsMapper extends BaseMapper<PortalPartDs> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalPartDsDto
     * @return
     */
    Page<PortalPartDsVo> getPageList(Page page, @Param("portalPartDsDto") PortalPartDsDto portalPartDsDto);
}
