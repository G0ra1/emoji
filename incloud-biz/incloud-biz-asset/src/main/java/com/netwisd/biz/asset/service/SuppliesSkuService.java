package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.SuppliesSku;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.SuppliesSkuDto;
import com.netwisd.biz.asset.vo.SuppliesSkuVo;
/**
 * @Description 耗材库存sku信息 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-27 16:52:43
 */
public interface SuppliesSkuService extends BatchService<SuppliesSku> {
    Page list(SuppliesSkuDto suppliesSkuDto);
    Page lists(SuppliesSkuDto suppliesSkuDto);
    SuppliesSkuVo get(Long id);
    Boolean save(SuppliesSkuDto suppliesSkuDto);
    Boolean saveList(List<SuppliesSkuDto> suppliesSkuDtos);
    Boolean update(SuppliesSkuDto suppliesSkuDto);
    Boolean delete(Long id);
    Boolean deleteByFkId(Long id);
    List<SuppliesSkuVo> getByFkIdVo(Long id);
}
