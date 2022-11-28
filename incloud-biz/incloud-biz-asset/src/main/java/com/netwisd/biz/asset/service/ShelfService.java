package com.netwisd.biz.asset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.asset.entity.Shelf;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.asset.dto.ShelfDto;
import com.netwisd.biz.asset.vo.ShelfVo;

import java.util.List;

/**
 * @Description 货架号 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-18 10:17:57
 */
public interface ShelfService extends IService<Shelf> {
    Page list(ShelfDto shelfDto);
    List<ShelfVo> lists(ShelfDto shelfDto);
    ShelfVo get(Long id);
    Boolean save(ShelfDto shelfDto);
    Boolean update(ShelfDto shelfDto);
    Boolean delete(Long id);
    Boolean saveList(List<ShelfDto> shelfDtoList);
    Boolean updateList(List<ShelfDto> shelfDtoList);
}
