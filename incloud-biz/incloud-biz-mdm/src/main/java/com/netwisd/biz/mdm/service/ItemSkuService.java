package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.dto.ItemClassifyDto;
import com.netwisd.biz.mdm.entity.ItemSku;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ItemSkuDto;
import com.netwisd.biz.mdm.vo.ItemSkuVo;

import java.util.List;

/**
 * @Description 物资分类sku 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 13:13:51
 */
public interface ItemSkuService extends IService<ItemSku> {
    Page list(ItemSkuDto itemSkuDto);
    ItemSkuVo get(Long id);
    Boolean save(ItemSkuDto itemSkuDto);
    Boolean update(ItemSkuDto itemSkuDto);
    Boolean delete(Long id);
    //分类id获取sku属性
    List<ItemSkuVo> getByClassifyId(Long id);
    //批量保存sku
    Boolean saveOrUpdateBatch(ItemClassifyDto itemClassifyDto);
}
