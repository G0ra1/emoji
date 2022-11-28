package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ItemUnit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ItemUnitDto;
import com.netwisd.biz.mdm.vo.ItemUnitVo;
/**
 * @Description 物资辅计量单位 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-31 20:23:36
 */
public interface ItemUnitService extends IService<ItemUnit> {
    Page list(ItemUnitDto itemUnitDto);
    ItemUnitVo get(Long id);
    //Boolean save(ItemUnitDto itemUnitDto);
    Boolean update(ItemUnitDto itemUnitDto);
    Boolean delete(Long id);
}
