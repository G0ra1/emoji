package com.netwisd.biz.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.netwisd.biz.asset.entity.MaterialDelivery;
import com.netwisd.biz.asset.dto.MaterialDeliveryDto;
import com.netwisd.biz.asset.vo.MaterialDeliveryVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
/**
 * @Description 资产出库管理 功能描述...
 * @author 云数网讯 chaixinkai@netwisd.com
 * @date 2022-04-29 10:45:38
 */
@Mapper
public interface MaterialDeliveryMapper extends BaseMapper<MaterialDelivery> {
    /**
     * 自定义mapper查询方法，如：进行多表联查去组装VO等
     * @param page
     * @param materialDeliveryDto
     * @return
     */
    Page<MaterialDeliveryVo> getPageList(Page page, @Param("materialDeliveryDto") MaterialDeliveryDto materialDeliveryDto);

    /**
     * 查询当日最大申请编号
     * @return
     */
    String getMaxCode();
}
