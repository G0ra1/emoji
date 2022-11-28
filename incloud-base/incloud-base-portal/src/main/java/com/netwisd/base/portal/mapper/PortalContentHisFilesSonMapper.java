package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentHisFilesSon;
import com.netwisd.base.portal.dto.PortalContentHisFilesSonDto;
import com.netwisd.base.portal.vo.PortalContentHisFilesSonVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 历史 文件下载类型内容发布子表 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:26:04
 */
@Mapper
public interface PortalContentHisFilesSonMapper extends BaseMapper<PortalContentHisFilesSon> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentHisFilesSonDto
     * @return
     */
    Page<PortalContentHisFilesSonVo> getPageList(Page page, @Param("portalContentHisFilesSonDto") PortalContentHisFilesSonDto portalContentHisFilesSonDto);
}
