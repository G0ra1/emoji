package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ItemSku;
import com.netwisd.biz.mdm.dto.ItemSkuDto;
import com.netwisd.biz.mdm.vo.ItemSkuVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 物资分类sku 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-25 13:13:51
 */
@Mapper
public interface ItemSkuMapper extends BaseMapper<ItemSku> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param itemSkuDto
     * @return
     */
    Page<ItemSkuVo> getPageList(Page page, @Param("itemSkuDto") ItemSkuDto itemSkuDto);
}
