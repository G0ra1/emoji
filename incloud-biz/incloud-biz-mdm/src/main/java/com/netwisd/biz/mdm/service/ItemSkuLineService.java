package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ItemSkuLine;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ItemSkuLineDto;
import com.netwisd.biz.mdm.vo.ItemSkuLineVo;
/**
 * @Description 物资sku行 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 19:52:49
 */
public interface ItemSkuLineService extends IService<ItemSkuLine> {
    Page list(ItemSkuLineDto itemSkuLineDto);
    Page lists(ItemSkuLineDto itemSkuLineDto);
    ItemSkuLineVo get(Long id);
    Boolean save(ItemSkuLineDto itemSkuLineDto);
    Boolean update(ItemSkuLineDto itemSkuLineDto);
    Boolean delete(Long id);
}
