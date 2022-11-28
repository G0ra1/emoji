package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.ViewerDetail;
import com.netwisd.biz.asset.dto.ViewerDetailDto;
import com.netwisd.biz.asset.vo.ViewerDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 物资数据权限范围表 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-08-01 10:14:45
 */
@Mapper
public interface ViewerDetailMapper extends BaseMapper<ViewerDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param viewerDetailDto
     * @return
     */
    Page<ViewerDetailVo> getPageList(Page page, @Param("viewerDetailDto") ViewerDetailDto viewerDetailDto);
}
