package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialDistributeDetail;
import com.netwisd.biz.asset.dto.MaterialDistributeDetailDto;
import com.netwisd.biz.asset.vo.MaterialDistributeDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产派发资产详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 16:46:22
 */
@Mapper
public interface MaterialDistributeDetailMapper extends BaseMapper<MaterialDistributeDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialDistributeDetailDto
     * @return
     */
    Page<MaterialDistributeDetailVo> getPageList(Page page, @Param("materialDistributeDetailDto") MaterialDistributeDetailDto materialDistributeDetailDto);
}
