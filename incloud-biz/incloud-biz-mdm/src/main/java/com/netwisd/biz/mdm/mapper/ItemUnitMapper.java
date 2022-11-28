package com.netwisd.biz.mdm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.mdm.entity.ItemUnit;
import com.netwisd.biz.mdm.dto.ItemUnitDto;
import com.netwisd.biz.mdm.vo.ItemUnitVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 物资辅计量单位 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-31 20:23:36
 */
@Mapper
public interface ItemUnitMapper extends BaseMapper<ItemUnit> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param itemUnitDto
     * @return
     */
    Page<ItemUnitVo> getPageList(Page page, @Param("itemUnitDto") ItemUnitDto itemUnitDto);
}
