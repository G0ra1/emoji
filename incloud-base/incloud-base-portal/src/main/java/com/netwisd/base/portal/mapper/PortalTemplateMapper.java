package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalTemplate;
import com.netwisd.base.portal.dto.PortalTemplateDto;
import com.netwisd.base.portal.vo.PortalTemplateVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 模板管理 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-08-11 00:09:45
 */
@Mapper
public interface PortalTemplateMapper extends BaseMapper<PortalTemplate> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalTemplateDto
     * @return
     */
    Page<PortalTemplateVo> getPageList(Page page, @Param("portalTemplateDto") PortalTemplateDto portalTemplateDto);
}
