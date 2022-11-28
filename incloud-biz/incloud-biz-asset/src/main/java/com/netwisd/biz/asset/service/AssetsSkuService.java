package com.netwisd.biz.asset.service;

import com.netwisd.biz.asset.constants.AssetsConditions;
import com.netwisd.biz.asset.dto.AssetsDetailDto;
import com.netwisd.biz.asset.vo.AssetsDetailVo;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.AssetsSku;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.AssetsSkuDto;
import com.netwisd.biz.asset.vo.AssetsSkuVo;
/**
 * @Description 资产sku信息 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-27 13:57:50
 */
public interface AssetsSkuService extends BatchService<AssetsSku> {
    Page list(AssetsSkuDto assetsSkuDto);
    Page lists(AssetsSkuDto assetsSkuDto);
    AssetsSkuVo get(Long id);
    Boolean save(AssetsSkuDto assetsSkuDto);
    Boolean saveList(List<AssetsSkuDto> assetsSkuDtos);
    Boolean update(AssetsSkuDto assetsSkuDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<AssetsSkuVo> getByFkIdVo(Long id);
/*
    Page<AssetsDetailVo> getAssetsSku(AssetsDetailDto assetsDetailDto, AssetsConditions... conditions);
*/
}
