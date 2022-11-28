package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialStorageDetail;
import com.netwisd.biz.asset.dto.MaterialStorageDetailDto;
import com.netwisd.biz.asset.vo.MaterialStorageDetailVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产入库明细 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:44:47
 */
@Mapper
public interface MaterialStorageDetailMapper extends BaseMapper<MaterialStorageDetail> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialStorageDetailDto
     * @return
     */
    Page<MaterialStorageDetailVo> getPageList(Page page, @Param("materialStorageDetailDto") MaterialStorageDetailDto materialStorageDetailDto);
}
