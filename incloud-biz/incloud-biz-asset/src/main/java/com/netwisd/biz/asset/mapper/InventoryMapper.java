package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.Inventory;
import com.netwisd.biz.asset.dto.InventoryDto;
import com.netwisd.biz.asset.vo.InventoryVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 物资盘点 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-21 09:55:49
 */
@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param inventoryDto
     * @return
     */
    Page<InventoryVo> getPageList(Page page, @Param("inventoryDto") InventoryDto inventoryDto);
    /**
     * 查询当天最大的申请编号
     */
    String getMaxCode();
}
