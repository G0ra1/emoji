package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaintainRegDetail;
import com.netwisd.biz.asset.dto.MaintainRegDetailDto;
import com.netwisd.biz.asset.vo.MaintainRegDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 维修登记详情表 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-28 17:12:23
 */
@Mapper
public interface MaintainRegDetailMapper extends BaseMapper<MaintainRegDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param maintainRegDetailDto
     * @return
     */
    Page<MaintainRegDetailVo> getPageList(Page page, @Param("maintainRegDetailDto") MaintainRegDetailDto maintainRegDetailDto);
}
