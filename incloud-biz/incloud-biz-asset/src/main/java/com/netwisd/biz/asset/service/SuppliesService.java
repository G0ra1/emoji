package com.netwisd.biz.asset.service;

import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.Supplies;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.SuppliesDto;
import com.netwisd.biz.asset.vo.SuppliesVo;
/**
 * @Description 耗材库存台账 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 15:28:53
 */
public interface SuppliesService extends BatchService<Supplies> {
    Page list(SuppliesDto suppliesDto);
    Page lists(SuppliesDto suppliesDto);
    SuppliesVo get(Long id);
    Boolean save(SuppliesDto suppliesDto);
    Boolean saveList(List<SuppliesDto> suppliesDtos);
    Boolean update(SuppliesDto suppliesDto);
    Boolean updateSub(SuppliesDto suppliesDto);
    Boolean delete(Long id);
}
