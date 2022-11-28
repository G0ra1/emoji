package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.Viewer;
import com.netwisd.biz.asset.dto.ViewerDto;
import com.netwisd.biz.asset.vo.ViewerVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 物资数据权限人员表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-08-01 10:14:45
 */
@Mapper
public interface ViewerMapper extends BaseMapper<Viewer> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param viewerDto
     * @return
     */
    Page<ViewerVo> getPageList(Page page, @Param("viewerDto") ViewerDto viewerDto);
}
