package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ItemSkuColumn;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ItemSkuColumnDto;
import com.netwisd.biz.mdm.vo.ItemSkuColumnVo;
/**
 * @Description 物资sku列 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 19:54:39
 */
public interface ItemSkuColumnService extends IService<ItemSkuColumn> {
    Page list(ItemSkuColumnDto itemSkuColumnDto);
    Page lists(ItemSkuColumnDto itemSkuColumnDto);
    ItemSkuColumnVo get(Long id);
    Boolean save(ItemSkuColumnDto itemSkuColumnDto);
    Boolean update(ItemSkuColumnDto itemSkuColumnDto);
    Boolean delete(Long id);
}
