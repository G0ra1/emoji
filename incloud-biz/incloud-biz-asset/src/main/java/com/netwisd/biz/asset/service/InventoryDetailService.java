package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.InventoryDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.InventoryDetailDto;
import com.netwisd.biz.asset.vo.InventoryDetailVo;
/**
 * @Description 盘点详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-21 09:55:49
 */
public interface InventoryDetailService extends IService<InventoryDetail> {
    Page list(InventoryDetailDto inventoryDetailDto);
    Page lists(InventoryDetailDto inventoryDetailDto);
    InventoryDetailVo get(Long id);
    void save(InventoryDetailDto inventoryDetailDto);
    void saveList(List<InventoryDetailDto> inventoryDetailDtos);
    void update(InventoryDetailDto inventoryDetailDto);
    void updateSub(InventoryDetailDto inventoryDetailDto);
    void delete(Long id);
    void deleteByFkId(Long id);
    List<InventoryDetailVo> getByFkIdVo(Long id);
}
