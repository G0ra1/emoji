package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.InventoryDetail;
import com.netwisd.biz.asset.dto.InventoryDetailDto;
import com.netwisd.biz.asset.vo.InventoryDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 盘点详情 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-04-21 09:55:49
 */
@Mapper
public interface InventoryDetailMapper extends BaseMapper<InventoryDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param inventoryDetailDto
     * @return
     */
    Page<InventoryDetailVo> getPageList(Page page, @Param("inventoryDetailDto") InventoryDetailDto inventoryDetailDto);
}
