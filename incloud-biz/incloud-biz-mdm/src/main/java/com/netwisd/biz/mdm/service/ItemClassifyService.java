package com.netwisd.biz.mdm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.netwisd.biz.mdm.entity.ItemClassify;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netwisd.biz.mdm.dto.ItemClassifyDto;
import com.netwisd.biz.mdm.vo.ItemClassifyAllVo;
import com.netwisd.biz.mdm.vo.ItemClassifyVo;
import com.netwisd.biz.mdm.vo.ItemSkuVo;
import com.netwisd.biz.mdm.vo.ItemVo;

import java.io.IOException;
import java.util.List;

/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:14:41
 */
public interface ItemClassifyService extends IService<ItemClassify> {
    List<ItemClassifyAllVo> list(ItemClassifyDto itemClassifyDto);
    List<ItemClassifyAllVo> lists(ItemClassifyDto itemClassifyDto);
    ItemClassifyVo get(Long id);
    Boolean save(ItemClassifyDto itemClassifyDto);
    Boolean update(ItemClassifyDto itemClassifyDto);
    Boolean delete(Long id);
    Boolean saveList(List<ItemClassifyDto> itemClassifyDtos);
    List<ItemClassifyVo> getByCodeList(List<String> codes);
    List<ItemClassify> dealWithItemClassify();

    /**
     * 查询所有子节点
     * @param codeList
     * @param itemClassify
     */
    void getChildList(List<String> codeList,ItemClassify itemClassify);
}
