package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.AssetsSku;
import com.netwisd.biz.asset.dto.AssetsSkuDto;
import com.netwisd.biz.asset.vo.AssetsSkuVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产sku信息 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-27 13:57:50
 */
@Mapper
public interface AssetsSkuMapper extends BaseMapper<AssetsSku> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param assetsSkuDto
     * @return
     */
    Page<AssetsSkuVo> getPageList(Page page, @Param("assetsSkuDto") AssetsSkuDto assetsSkuDto);
}
