package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.Shelf;
import com.netwisd.biz.asset.dto.ShelfDto;
import com.netwisd.biz.asset.vo.ShelfVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 货架号 功能描述...
 * @author 云数网讯 baiyulan@netwisd.com
 * @date 2022-05-18 10:17:57
 */
@Mapper
public interface ShelfMapper extends BaseMapper<Shelf> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param shelfDto
     * @return
     */
    Page<ShelfVo> getPageList(Page page, @Param("shelfDto") ShelfDto shelfDto);
}
