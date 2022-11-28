package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.Supplies;
import com.netwisd.biz.asset.dto.SuppliesDto;
import com.netwisd.biz.asset.vo.SuppliesVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 耗材库存台账 功能描述...
 * @author 云数网讯 hansongpeng@netwisd.com
 * @date 2022-05-25 15:28:53
 */
@Mapper
public interface SuppliesMapper extends BaseMapper<Supplies> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param suppliesDto
     * @return
     */
    Page<SuppliesVo> getPageList(Page page, @Param("suppliesDto") SuppliesDto suppliesDto);
}
