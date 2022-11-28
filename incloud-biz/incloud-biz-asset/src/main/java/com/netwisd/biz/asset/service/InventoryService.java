package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.common.db.data.BatchService;
import java.util.List;
import com.netwisd.biz.asset.entity.Inventory;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.InventoryDto;
import com.netwisd.biz.asset.vo.InventoryVo;
/**
 * @Description 物资盘点 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-21 09:55:49
 */
public interface InventoryService extends IService<Inventory> {
    Page list(InventoryDto inventoryDto);
    Page lists(InventoryDto inventoryDto);
    InventoryVo get(Long id);
    Boolean save(InventoryDto inventoryDto);
    Boolean update(InventoryDto inventoryDto);
    Boolean delete(Long id);
    List<InventoryVo> getByFkIdVo(Long id);
    InventoryVo procDetail(String procInstId);
    InventoryVo procSaveOrUpdate(InventoryDto inventoryDto);
}
