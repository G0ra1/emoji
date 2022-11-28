package com.netwisd.base.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.base.portal.entity.PortalContentHisFiles;
import com.netwisd.base.portal.dto.PortalContentHisFilesDto;
import com.netwisd.base.portal.vo.PortalContentHisFilesVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 历史 文件下载类型内容发布 功能描述...
 * @author 云数网讯 lhy@netwisd.com
 * @date 2021-09-02 15:24:56
 */
@Mapper
public interface PortalContentHisFilesMapper extends BaseMapper<PortalContentHisFiles> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param portalContentHisFilesDto
     * @return
     */
    Page<PortalContentHisFilesVo> getPageList(Page page, @Param("portalContentHisFilesDto") PortalContentHisFilesDto portalContentHisFilesDto);
}
