package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ItemSkuLine;
import com.netwisd.biz.mdm.dto.ItemSkuLineDto;
import com.netwisd.biz.mdm.vo.ItemSkuLineVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 物资sku行 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 19:52:49
 */
@Mapper
public interface ItemSkuLineMapper extends BaseMapper<ItemSkuLine> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param itemSkuLineDto
     * @return
     */
    Page<ItemSkuLineVo> getPageList(Page page, @Param("itemSkuLineDto") ItemSkuLineDto itemSkuLineDto);
}
