package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.PurchaseAcceptAsset;
import com.netwisd.biz.asset.dto.PurchaseAcceptAssetDto;
import com.netwisd.biz.asset.vo.PurchaseAcceptAssetVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 验收明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-17 15:54:14
 */
@Mapper
public interface PurchaseAcceptAssetMapper extends BaseMapper<PurchaseAcceptAsset> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param purchaseAcceptAssetDto
     * @return
     */
    Page<PurchaseAcceptAssetVo> getPageList(Page page, @Param("purchaseAcceptAssetDto") PurchaseAcceptAssetDto purchaseAcceptAssetDto);
}
