package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialStorage;
import com.netwisd.biz.asset.dto.MaterialStorageDto;
import com.netwisd.biz.asset.vo.MaterialStorageVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产入库管理 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:44:47
 */
@Mapper
public interface MaterialStorageMapper extends BaseMapper<MaterialStorage> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialStorageDto
     * @return
     */
    Page<MaterialStorageVo> getPageList(Page page, @Param("materialStorageDto") MaterialStorageDto materialStorageDto);

    /**
     * 查询当日最大申请编号
     * @return
     */
    String getMaxCode();
}
