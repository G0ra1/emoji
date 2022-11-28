package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.SuppliesSku;
import com.netwisd.biz.asset.dto.SuppliesSkuDto;
import com.netwisd.biz.asset.vo.SuppliesSkuVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 耗材库存sku信息 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-27 16:52:43
 */
@Mapper
public interface SuppliesSkuMapper extends BaseMapper<SuppliesSku> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param suppliesSkuDto
     * @return
     */
    Page<SuppliesSkuVo> getPageList(Page page, @Param("suppliesSkuDto") SuppliesSkuDto suppliesSkuDto);
}
