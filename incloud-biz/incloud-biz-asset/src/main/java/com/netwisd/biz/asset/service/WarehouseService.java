package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.entity.Warehouse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.WarehouseDto;
import com.netwisd.biz.asset.vo.WarehouseVo;

import java.util.List;

/**
 * @Description 仓库 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-22 10:19:44
 */
public interface WarehouseService extends IService<Warehouse> {
    Page list(WarehouseDto warehouseDto);
    List<WarehouseVo> lists(WarehouseDto warehouseDto);
    WarehouseVo get(Long id);
    Boolean save(WarehouseDto warehouseDto);
    Boolean update(WarehouseDto warehouseDto);
    Boolean delete(Long id);
}
