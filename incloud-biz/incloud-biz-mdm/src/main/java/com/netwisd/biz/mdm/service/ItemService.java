package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.Item;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ItemDto;
import com.netwisd.biz.mdm.vo.ItemVo;
import java.util.List;

/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
public interface ItemService extends IService<Item> {
    Page list(ItemDto itemDto);
    List<ItemVo> lists(ItemDto itemDto);
    ItemVo get(Long id);
    Boolean save(ItemDto itemDto);
    Boolean update(ItemDto itemDto);
    Boolean delete(Long id);
    Boolean saveList(List<ItemDto> itemDtos);

    /**
     * 处理物资数据
     */
    void dealWithItem();

    /**
     * 批量处理
     */
    void checkItem();
}
