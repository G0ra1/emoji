package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.Warehouse;
import com.netwisd.biz.asset.dto.WarehouseDto;
import com.netwisd.biz.asset.vo.WarehouseVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 仓库 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-04-22 10:19:44
 */
@Mapper
public interface WarehouseMapper extends BaseMapper<Warehouse> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param warehouseDto
     * @return
     */
    Page<WarehouseVo> getPageList(Page page, @Param("warehouseDto") WarehouseDto warehouseDto);
}
