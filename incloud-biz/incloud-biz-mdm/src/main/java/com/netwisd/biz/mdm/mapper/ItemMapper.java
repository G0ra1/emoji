package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.Item;
import com.netwisd.biz.mdm.dto.ItemDto;
import com.netwisd.biz.mdm.vo.ItemVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 物资 功能描述...
 * @author 云数网讯 XHL@netwisd.com
 * @date 2021-08-19 15:18:00
 */
@Mapper
public interface ItemMapper extends BaseMapper<Item> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param itemDto
     * @return
     */
    Page<ItemVo> getPageList(Page page, @Param("itemDto") ItemDto itemDto);
}
