package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ItemSkuColumn;
import com.netwisd.biz.mdm.dto.ItemSkuColumnDto;
import com.netwisd.biz.mdm.vo.ItemSkuColumnVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 物资sku列 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 19:54:39
 */
@Mapper
public interface ItemSkuColumnMapper extends BaseMapper<ItemSkuColumn> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param itemSkuColumnDto
     * @return
     */
    Page<ItemSkuColumnVo> getPageList(Page page, @Param("itemSkuColumnDto") ItemSkuColumnDto itemSkuColumnDto);
}
