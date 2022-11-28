package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialSignDetail;
import com.netwisd.biz.asset.dto.MaterialSignDetailDto;
import com.netwisd.biz.asset.vo.MaterialSignDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 签收详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-06-24 11:09:16
 */
@Mapper
public interface MaterialSignDetailMapper extends BaseMapper<MaterialSignDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialSignDetailDto
     * @return
     */
    Page<MaterialSignDetailVo> getPageList(Page page, @Param("materialSignDetailDto") MaterialSignDetailDto materialSignDetailDto);
}
