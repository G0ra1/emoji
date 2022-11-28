package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.SuppliesDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.SuppliesDetailDto;
import com.netwisd.biz.asset.vo.SuppliesDetailVo;
/**
 * @Description 耗材库存明细 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 15:28:53
 */
public interface SuppliesDetailService extends BatchService<SuppliesDetail> {
    Page list(SuppliesDetailDto suppliesDetailDto);
    Page lists(SuppliesDetailDto suppliesDetailDto);
    SuppliesDetailVo get(Long id);
    Boolean save(SuppliesDetailDto suppliesDetailDto);
    Boolean saveList(List<SuppliesDetailDto> suppliesDetailDtos);
    Boolean update(SuppliesDetailDto suppliesDetailDto);
    Boolean delete(Long id);
    Boolean deleteByAssetsId(Long id);
    List<SuppliesDetailVo> getByAssetsId(Long id);
    List<SuppliesDetailVo> getByAssetsSkuId(Long id);
}
