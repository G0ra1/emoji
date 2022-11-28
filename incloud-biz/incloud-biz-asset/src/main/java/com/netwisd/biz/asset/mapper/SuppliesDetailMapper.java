package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.SuppliesDetail;
import com.netwisd.biz.asset.dto.SuppliesDetailDto;
import com.netwisd.biz.asset.vo.SuppliesDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description 耗材库存明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 15:28:53
 */
@Mapper
public interface SuppliesDetailMapper extends BaseMapper<SuppliesDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param suppliesDetailDto
     * @return
     */
    Page<SuppliesDetailVo> getPageList(Page page, @Param("suppliesDetailDto") SuppliesDetailDto suppliesDetailDto);

    List<SuppliesDetailVo> getDetailBySupplies();
}
